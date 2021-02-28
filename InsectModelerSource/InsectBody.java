

////////////////////////////////////////////////////////////////////////
//
//  InsectModeler Program
//
//  Created by Greg Buron
//  Spring 2003
//  Western Washington University
//
//
//  InsectBody.java
//  
//  The <code>InsectBody</code> class is responsible for creating and
//  modifying the data structures that will represent the insect model's
//  body.
//
////////////////////////////////////////////////////////////////////////


package myprojects.insectmodeler;

import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import java.text.DecimalFormat;

import java.util.Vector;

import vtk.*;



public class InsectBody
{

	// CONSTRUCTOR
	//
	public InsectBody( int numRadialPoints, int numColumns )
	{
		form = new DecimalFormat( "#.##" );
		
		// Save the model creation parameters.
		columns = numColumns;
		radialPoints = numRadialPoints;
		
		// Actual x,y,z coordinates of the body cylinder.
		vertexData = new double[radialPoints*numColumns][3];
		 
		// connectivity for each facet of the body cylinder. 
		connectivityData = new vtkIdList[(radialPoints*(numColumns-1))+2]; 
		
		
		// Set up the containers for the data.
		data    = new vtkPolyData( );
		points  = new vtkPoints( );
		polys   = new vtkCellArray( );
		scalars = new vtkFloatArray( );
		
		
		// Set up the actual data points.
		for( int col=0; col<numColumns; ++col )
		{	
			for( int i=0; i<radialPoints; ++i )
			{
				// Set all of the point coordinates for this row.
				double angle   = 360.0 * (double)i/(double)(radialPoints);
				double radians = Math.toRadians( angle );
				
				vertexData[i+(radialPoints*col)][X] = 1.0 * Math.cos( radians );
				vertexData[i+(radialPoints*col)][Y] = 1.0 * Math.sin( radians );
				vertexData[i+(radialPoints*col)][Z] = numColumns/2 - col;
						
			}// end for( i )
			
		}// end for( row )


		// Set up the connectivity between points to create quads.
		for( int col=0; col<numColumns-1; ++col )
		{
			for( int i=0; i<radialPoints; ++i )
			{
				int index = i+(col*radialPoints);
				
				connectivityData[index] = new vtkIdList( );	
				
				// Create the connectivity for each of the quads for 
				// the columns up to the next to the last quad in the column.
				if( i < radialPoints-1 )
				{
					// Put coordinate references in counter-clockwise order.
					connectivityData[index].InsertNextId( index );
					connectivityData[index].InsertNextId( index + radialPoints );
					connectivityData[index].InsertNextId( index + radialPoints + 1 );
					connectivityData[index].InsertNextId( index + 1 );
				}
				
				// Connect the last facet in the column with the first.
				else if( i == radialPoints-1 )
				{
					// Put coordinate references in counter-clockwise order.		
					connectivityData[index].InsertNextId( index );
					connectivityData[index].InsertNextId( index + radialPoints );
					connectivityData[index].InsertNextId( index + 1 );
					connectivityData[index].InsertNextId( index - radialPoints + 1 );
				}						
				
			}// end for( i )
			
		}// end for( row )
		
		// Create connectivity to close the "ends" of the cylinder.
		connectivityData[(radialPoints*(numColumns-1))] = new vtkIdList( );
		connectivityData[(radialPoints*(numColumns-1))+1] = new vtkIdList( );
	
		int frontIndex = (radialPoints*(numColumns-1));
		int endIndex   = (radialPoints*(numColumns-1))+1;		
		
		for( int cap=0; cap<radialPoints; ++cap )
		{
			connectivityData[frontIndex].InsertNextId( cap );
			connectivityData[endIndex].InsertNextId( ((numColumns*radialPoints)-1) - cap ); 
		}// end for
		
				
		// Initialize the containers for the data.  Use the
		// updateData( ) method to assign the verticies into 
		// the vtk data containers.
		actor = new vtkActor( );
		actor.GetProperty( ).SetAmbient( 0.25 );
		actor.GetProperty( ).SetDiffuse( 0.25 );
		actor.GetProperty( ).SetAmbientColor( 1.0f, 1.0f, 1.0f );
		actor.GetProperty( ).SetDiffuseColor( 1.0f, 1.0f, 1.0f );
		actor.GetProperty( ).BackfaceCullingOn( );
		actor.GetProperty( ).FrontfaceCullingOff( );
		actor.GetProperty( ).SetInterpolationToGouraud( );
		


		
		// The modifiedIndexVector is a vector containing all of the 
		// radial indicies of the cylinder of the body of the model
		// that are modified during the registration process.  The 
		// creation of the model process can use this vector after all
		// of the images have been registered to determine which indicies
		// have not been modified in the registration process.  These
		// verticies that have not been modified can then be interpolated
		// between the verticies that have been modified.
		modifiedIndexVector = new Vector( );
		
	}// end constructor
	
	
	public vtkActor getActor( )
	// Return the insect body's vtk actor.
	{
		return actor;
	}
	
	public int getNumberOfColumns( )
	// Return the number of columns in the insect body data.
	{
		return columns;
	}
	
	public int getNumberOfRadialPoints( )
	// Return the number of radial points per body column.
	{
		return radialPoints;
	}
	
	public void setRenderMode( int mode )
	// Set the body to be rendered as points, wireframe or solid polys.
	{
		if( mode == InsectModel.POINT )
		{
			actor.GetProperty( ).SetRepresentationToPoints( );
		}// end if
		else if( mode == InsectModel.WIREFRAME )
		{
			actor.GetProperty( ).SetRepresentationToWireframe( );
		}// end else if
		else if( mode == InsectModel.POLYGON )
		{
			actor.GetProperty( ).SetRepresentationToSurface( );
		}// end else if	
	}
	
	public void displayBody( boolean display )
	{
		if( display == true )
		{
			actor.GetProperty( ).SetOpacity( 1.0 );
		}// end if
		else if( display == false )
		{
			actor.GetProperty( ).SetOpacity( 0.0 );
		}// end else
	}	
	
	public void createBody( Vector insectRecordVector, String texturePath )
	{
		// Save off the body's texture path.
		this.texturePath = texturePath;	
		
		// Apply all of the records to the insect body, which will
		// modify only the points that those records are outlining.
		registerBodyData( insectRecordVector );	
		
		//
		calculateBodyColumnCenters( insectRecordVector );
		
		//
		realignModifiedIndiciesToCenters( insectRecordVector );
		
		
		// Once all of the records have been applied to the body, 
		// then interpolate the points that were not modified to 
		// the points that the records did modify.  
		interpolateBodyData( );	
		
		// Use the InsectBody's internally generated 
		// data structures to update the vtk actor with
		// the newly generated values for all of the points.
		assembleBodyData( );	
	}
	
	
	private void registerBodyData( Vector insectBodyRecordVector )
	// The registerBodyData( ) method takes the vector of body image registration
	// records and modifies the body points accoriding to the values it finds for
	// those images.  This method sets the locations for each point in the body
	// that can be registered from the images, and then interpolates any radial 
	// points that are not directly modified from the registration process, to 
	// create a smooth curve between registered points.
	{
		// Start with a clean modified index vector.
		modifiedIndexVector.clear( );
		
		int size = insectBodyRecordVector.size( );
		
		for( int i=0; i<size; ++i )
		{
			// Get the record to access image and view angle information
			// for each element in the insectRecordVector.
			ImageRecord record = (ImageRecord)insectBodyRecordVector.elementAt( i );

			// Modify the locations of the points that this image record
			// outlines.  Any points that do not have an image record 
			// that bisects their plane will be interpolated.
			registerRecordWithBody( record );
			
		}// end for
	}
	
	
	private void registerRecordWithBody( ImageRecord record )
	// Register a specific record against the insect's body data structure.
	// This method determines what vertical columns should be used for 
	// each column of insect body data and where that data's center and
	// the average height of the data it finds for those columns.  This
	// information is given to the applyDistortionToBodyData( ) method which
	// modifies the internal data sturctures for each column in the data.
	{
		// Get the bounds of the search space of the image.
		int startX = record.getBodyDataStartColumn( );
		int startY = record.getBodyDataStartRow( );	
		int endX   = record.getBodyDataEndColumn( );
		int endY   = record.getBodyDataEndRow( );
		
		float verticalScanLinesPerModelColumn = 
		          (float)( endX - startX ) / (float)columns;
		          
		int totalLength = endX - startX;
		int imageVerticalCenter = record.getImageProcessor( ).getHeight( )/2;
		
		System.err.println( "verticalScanLinesPerModelColumn=" + 
							verticalScanLinesPerModelColumn );
							
		System.err.println( "totalLength=" + totalLength );
					
							
		// Go through each of the columns in the model and calculate
		// the average length of the 2D image's vertical scan lines
		// that will represent the size of this area of the model.
		for( int modelColumn=0; modelColumn<columns; ++modelColumn )
		{
			int startCol = startX + modelColumn * (int)verticalScanLinesPerModelColumn;
			int endCol   = startCol + (int)verticalScanLinesPerModelColumn;
			
			int totalHeight = 0;
			float averageHeight = 0.0f;
			
			int totalCenter = 0;
			float averageCenter = 0.0f;
			
			// Sum up the "heights" of the vertical columns which will 
			// represent the "height" for this modelColumn.
			for( int currentCol = startCol; currentCol < endCol; ++currentCol )
			{
				// Get the scan line data for this vertical column in 
				// the ImageRecords image file.  The tuple returned 
				// corresponds to the total number of pixels long the
				// insect data is, and the center of that data relative
				// to the top of the data.
				Vector4f scanLineData = record.getColumnData( currentCol );
				
				totalHeight += scanLineData.x;
				totalCenter += scanLineData.y;
				
			}// end for
			
			averageHeight = totalHeight/verticalScanLinesPerModelColumn;
			averageCenter = totalCenter/verticalScanLinesPerModelColumn;
			
			// Apply the values found in the image to the values in 
			// the insect model's body data structures.
			applyDistortionToBodyData( modelColumn, averageHeight, 
			                           totalLength, averageCenter, record );
			
		}// end for
	}	
	
	
	private void applyDistortionToBodyData( int modelColumn, float height, 
	                                        int totalLength, float relativeCenter, 
	                                        ImageRecord record )
	// The applyDistortionToBodyData( ) method modifies the width of the 
	// two opposing data points in the cylinder "body" column at modelColumn 
	// according to the height parameter and the viewer's location found in
	// the ImageRecord parameter.  	                                        
	{
		// Get the viewer angle.
		float angle = record.getAngle( );
		
		// The angle will tell you the plane that slices this model. 
		// If the angle is 0, then the data points 0 and radialPoints/2,
		// are the points that are in the plane.  We are interested 
		// in the points exactly perpendicular to those points.  
		// Therefore we need an index based on the ( angle + 90 ).
		// The index1 value indicates the index of the radial points that
		// are going to be modified by the "top" pixels of the image,
		// and the index2 is the index of the radial points that are 
		// going to be modified by the "bottom" pixels of the image.
		int index1 = (int)(((angle+90.0f)/360.0f)*(float)radialPoints);
		int index2 = (int)(((angle+270.0f)/360.0f)*(float)radialPoints);
		
		if( index1 >= radialPoints ) index1 = index1 - radialPoints;
		if( index2 >= radialPoints ) index2 = index2 - radialPoints;
		
		
		// Find the vector along the origin that this point normally
		// makes when the body is a cylinder.  This is the vector 
		// direction that this point should be moved along.
		Vector3f index1Vector = getStandardRadialVector( index1 );
		Vector3f index2Vector = getStandardRadialVector( index2 );


		// The distance between the two points should be the "height" parameter
		// to this function.  Correct for the model perhaps not being as many 
		// columns of data as are in the image (column collapsing/averaging).g  
		float normalizeFactor = ( (float)columns/(float)totalLength );
		
		
		float index1ModificationX = 
		       height/2.0f * normalizeFactor * index1Vector.x + 
		       relativeCenter * normalizeFactor * Math.abs( index1Vector.x );
		
		float index1ModificationY = 
		       height/2.0f * normalizeFactor * index1Vector.y + 
		       -1.0f * relativeCenter * normalizeFactor * Math.abs( index1Vector.y ); 
		       
		float index2ModificationX = 
			   height/2.0f * normalizeFactor * index2Vector.x + 
			   relativeCenter * normalizeFactor * Math.abs( index2Vector.x );
			   
		float index2ModificationY = 
		       height/2.0f * normalizeFactor * index2Vector.y + 
		       -1.0f * relativeCenter * normalizeFactor * Math.abs( index2Vector.y );
		       
		
		vertexData[index1+(radialPoints*modelColumn)][X] = index1ModificationX;
		vertexData[index1+(radialPoints*modelColumn)][Y] = index1ModificationY;
	    						     
		vertexData[index2+(radialPoints*modelColumn)][X] = index2ModificationX;
		vertexData[index2+(radialPoints*modelColumn)][Y] = index2ModificationY;	
		
		
		// Save off these indicies to let the algorithm know which points
		// were set and which points still need to be interpolated.
		if( modifiedIndexVector.indexOf( new Integer( index1 ) ) == -1 )
		{
			modifiedIndexVector.add( new Integer( index1 )  );
		}// end if
		if( modifiedIndexVector.indexOf( new Integer( index2 )  ) == -1 )
		{
			modifiedIndexVector.add( new Integer( index2 )  );
		}// end if		
	}
	
	
	private void calculateBodyColumnCenters( Vector insectRecordVector )
	{
		// The bodyColumnCenters is a point array which keeps track of the
		// center location for each column of points in the insect body.
		bodyColumnCenters = new Point3f[columns];	
		
		for( int i=0; i<columns; ++i )
		{
			bodyColumnCenters[i] = new Point3f( );
			
			float centroidX = 0.0f;
			float centroidY = 0.0f;
			float centroidZ = 0.0f;
			
			int modifiedPoints = 0;
			
			for( int j=0; j<radialPoints; ++j )
			{
				if( modifiedIndexVector.indexOf( new Integer( j ) ) != -1 )
				{
					centroidX += (float)vertexData[j+(radialPoints*i)][X];
					centroidY += (float)vertexData[j+(radialPoints*i)][Y];
					centroidZ += (float)vertexData[j+(radialPoints*i)][Z];
					
					modifiedPoints++;
					
				}// end if
				
			}// end for
			
			centroidX /= (float)(modifiedPoints/2); // -> modifiedPoints/insectRecordVector.size() ?
			centroidY /= (float)(modifiedPoints/2);
			centroidZ /= (float)(modifiedPoints);
			
			bodyColumnCenters[i].x = centroidX;
			bodyColumnCenters[i].y = centroidY;
			bodyColumnCenters[i].z = centroidZ;
			
		}// end for	
		
		// Debug: create a line actor along the center points of the body.
		createCenterLineActor( );	
	}
	
	
	private void realignModifiedIndiciesToCenters( Vector insectRecordVector )
	{
		for( int i=0; i<columns; ++i )
		{
			for( int j=0; j<insectRecordVector.size(); ++j )
			{
				// Get a handle to the record.
				ImageRecord record = (ImageRecord)insectRecordVector.elementAt( j );
				
				// Get the viewer angle.
				float angle = record.getAngle( );
				
				// Get the indicies of the modified points of this record.
				int index1 = (int)(((angle+90.0f)/360.0f)*(float)radialPoints);
				int index2 = (int)(((angle+270.0f)/360.0f)*(float)radialPoints);
				
				if( index1 >= radialPoints ) index1 = index1 - radialPoints;
				if( index2 >= radialPoints ) index2 = index2 - radialPoints;
				
				
				for( int k=0; k<modifiedIndexVector.size(); ++k )
				{
					int nextIndex = ((Integer)modifiedIndexVector.elementAt( k )).intValue();
					
					if( nextIndex != index1 && nextIndex != index2 )
					{
						float modifyIndexAngle = getStandardRadialAngle( nextIndex );
						
						double modifyFactorX = 
						       Math.abs( Math.sin( Math.toRadians( modifyIndexAngle ) ) );
						       
						double modifyFactorY = 
						       Math.abs( Math.cos( Math.toRadians( modifyIndexAngle ) ) );

						
						double diffX = vertexData[nextIndex+(radialPoints*i)][X] - bodyColumnCenters[i].x;
						double diffY = vertexData[nextIndex+(radialPoints*i)][Y] - bodyColumnCenters[i].y;
						
						vertexData[nextIndex+(radialPoints*i)][X] -= diffX * modifyFactorX;
						vertexData[nextIndex+(radialPoints*i)][Y] -= diffY * modifyFactorY;
						
					}// end if
					
				}// end for(k)
				
			}// end for(j)
			
		}// end for(i)
	}
	
					                    	
	private void interpolateBodyData( )
	{
		int start = -1;
		int end   = -1;
		
		for( int i=0; i<modifiedIndexVector.size( ); ++i )
		{
			// Get the next start and end points for the next interpolation.
			Vector2f points = getNextAdjacentModifiedIndicies( start );
			
			start = (int)points.x;
			end   = (int)points.y;
			
			// Interpolate the points between the start and end radial points.
			interpolateBodyDataColumns( start, end );
			
			// Set the new start point as the last end point.
			start = (int)points.y; 
			
			//return;

		}// end for
	}


	private void interpolateBodyDataColumns( int startIndex, int endIndex )
	{
		// Go down the columns and interpolate the data in between 
		// the start and end radial indicies for each column of data.
		for( int i=0; i<columns; ++i )
		{				
			double startX = vertexData[startIndex+(radialPoints*i)][X];
			double startY = vertexData[startIndex+(radialPoints*i)][Y];
			double endX   = vertexData[endIndex+(radialPoints*i)][X];
			double endY   = vertexData[endIndex+(radialPoints*i)][Y];
			
			double deltaX = Math.abs( endX - startX );
			double deltaY = Math.abs( endY - startY );
			
			int currentIndex = startIndex + 1;
			
			while( currentIndex != endIndex )
			{
				float angle = getStandardRadialAngle( currentIndex );
	
				double x = Math.cos( Math.toRadians( angle ) );
				double y = Math.sin( Math.toRadians( angle ) );
				
				x = x * deltaX;
				y = y * deltaY;

			    vertexData[currentIndex+(radialPoints*i)][X] = x + bodyColumnCenters[i].x;
			    vertexData[currentIndex+(radialPoints*i)][Y] = y + bodyColumnCenters[i].y;				
				
				currentIndex +=1;
				
				if( currentIndex >= radialPoints )  currentIndex = 0;
			}// end while

		}// end for	
	}


	private void assembleBodyData( )
	{
		// Empty the containers.
		points.Reset( );  // vtkPoints
		polys.Reset( );   // vtkCellArray
		
		// Load the point and cell attributes.
		for( int i=0; i<vertexData.length; ++i )      
			points.InsertPoint( i, vertexData[i] );
			
		for( int i=0; i<connectivityData.length; ++i )  
			polys.InsertNextCell( connectivityData[i] );
		
		
		// Create the texture coordinates.
		textureCoords = new vtkFloatArray( );
		textureCoords.SetNumberOfComponents( 2 ); 
		textureCoords.SetNumberOfTuples( vertexData.length );
		
		int columnCounter = 0;
		
		for( int i=0; i<vertexData.length; ++i )
		{
			double x = i % radialPoints;
			                    
			double tx = x / (double)radialPoints;
			double ty = 1.0 - (double)columnCounter / (double)(columns-1);
						
			textureCoords.SetComponent( i, 0, tx );
			textureCoords.SetComponent( i, 1, ty );
			
			if( x == radialPoints-1 ) columnCounter++;	                    				
		}      
		
		      
		// Fill the vtkPolyData container with the point, cell, 
		// and texture attributes.                   		
		data.SetPoints( points );
		data.SetPolys( polys );		
		data.GetPointData( ).SetTCoords( textureCoords );
		
		
		if( texturePath != null )
		{
			// Create the texture to apply to the body.
			vtkTIFFReader tiffReader = new vtkTIFFReader( );
			tiffReader.SetFileName( texturePath );
			
			vtkTexture texture = new vtkTexture( );
			texture.SetRepeat( 0 );
			texture.SetInput( tiffReader.GetOutput( ) );
			
			// Add the data and the texture to the insect body.
			dataMapper = new vtkPolyDataMapper( );
			dataMapper.SetInput( data );
			actor.SetMapper( dataMapper );		
			actor.SetTexture( texture );
			
		}// end if
		else
		{
			dataMapper = new vtkPolyDataMapper( );
			dataMapper.SetInput( data );
			actor.SetMapper( dataMapper );
		}// end else		
	}
	
	
	
////// original	
//
//		if( texturePath != null )
//		{
//			// Create the texture to apply to the body.
//			vtkTIFFReader tiffReader = new vtkTIFFReader( );
//			tiffReader.SetFileName( texturePath );
//			
//			vtkTexture texture = new vtkTexture( );
//			texture.SetRepeat( 0 );
//			texture.SetInput( tiffReader.GetOutput( ) );
//			
//			vtkTextureMapToCylinder tmapper = new vtkTextureMapToCylinder( );
//			tmapper.SetInput( data );	
//			tmapper.PreventSeamOff( );	
//	
////			vtkTextureMapToPlane tmapper = new vtkTextureMapToPlane( );
////			tmapper.SetInput( data );
//			
//			
//			// Transform the texture coordinates.
//			vtkTransformTextureCoords xform = new vtkTransformTextureCoords( );
//			xform.SetInput( tmapper.GetOutput( ) );
////			xform.SetScale( 0.5f, 0.5f, 1.0f );
//			
//			// Use a data set mapper to map the texture coordinates
//			// to the vertex coordinates.
//			vtkDataSetMapper mapper = new vtkDataSetMapper( );
//			mapper.SetInput( xform.GetOutput( ) );
//			
//			// Add the data and the texture to the insect body.
//			actor.SetMapper( mapper );	
//			actor.SetTexture( texture );
//		}// end if	


	private Vector2f getNextAdjacentModifiedIndicies( int start )
	{
		Vector2f points = new Vector2f( );
		
		int first = start;
		
		for( first=start; first<radialPoints; ++first )
			if( modifiedIndexVector.indexOf( new Integer( first ) ) != -1 )
				break;
		
		int next = first+1;
		
		for( next = first+1; next<radialPoints*2; ++next )
		{
			if( next >= radialPoints )
				next = 0;
				
			if( modifiedIndexVector.indexOf( new Integer( next ) ) != -1 )
				break;
		}
		
		if( next >= radialPoints ) 
			next -= radialPoints;
		
		points.x = first;
		points.y = next;
				
		return points;
	}
	
	
	private Vector3f getStandardRadialVector( int index )
	// The getStandardRadialVector( ) method returns a normalized vector 
	// of the vector from the origin to some angle in the x,y plane denoted
	// by the index parameter.  The index parameter indicates one of the 
	// radial points that makes up one of the columns of the insect body.
	// Thus the index creates an angle from which a vector is created and
	// returned to the calling method.
	{
		Vector3f vector = new Vector3f( );
		
		double angle   = 360.0 * (double)index/(double)(radialPoints);
		double radians = Math.toRadians( angle );
		
		vector.x = 1.0f * (float)Math.cos( radians );
		vector.y = 1.0f * (float)Math.sin( radians );
		vector.z = 0.0f;
		
		vector.normalize( );
		
		return vector;
	}
	
	private float getStandardRadialAngle( int index )
	{
		return (float)(360.0 * (double)index/(double)(radialPoints));
	}
	
	private int getIndexCount( int startIndex, int endIndex )
	// Returns the number of indicies between a start and end index 
	// of a column of radial points, end points inclusive.
	{
		int cur = startIndex;
		
		int indexCount = 0;
		
		while( cur != endIndex )
		{
			cur += 1;
			indexCount++;
			
			if( cur >= radialPoints )  
			    cur = 0;
		}// end while
		
		return indexCount;		
	}
	
	
	
	private void createCenterLineActor( )
	{
		centerLinePoints = new vtkPoints();
		centerLineLines = new vtkCellArray();
		centerLineData = new vtkPolyData();
		double[][] lineVertexData = new double[columns][3];
		for( int a=0; a<columns; ++a )
		{
			lineVertexData[a][X] = bodyColumnCenters[a].x;
			lineVertexData[a][Y] = bodyColumnCenters[a].y;
			lineVertexData[a][Z] = bodyColumnCenters[a].z;
		}
		vtkIdList[] lineSegmentConnectivityData = new vtkIdList[columns-1];
		for( int b=0; b<columns-1; ++b )
		{
			lineSegmentConnectivityData[b] = new vtkIdList();
			lineSegmentConnectivityData[b].InsertNextId(b);
			lineSegmentConnectivityData[b].InsertNextId(b+1);
		}
		for( int i=0; i<lineVertexData.length; ++i )      
			centerLinePoints.InsertPoint( i, lineVertexData[i] );
			
		for( int i=0; i<lineSegmentConnectivityData.length; ++i )  
			centerLineLines.InsertNextCell( lineSegmentConnectivityData[i] );
			
		centerLineData.SetPoints( centerLinePoints );
		centerLineData.SetLines( centerLineLines );			
		centerLineActor = new vtkActor( );	
		centerLineActor.GetProperty( ).SetAmbient( 0.25 );
		centerLineActor.GetProperty( ).SetDiffuse( 0.25 );
		centerLineActor.GetProperty( ).SetAmbientColor( 0.0f, 0.0f, 1.0f );
		centerLineActor.GetProperty( ).SetDiffuseColor( 0.0f, 0.0f, 1.0f );
		centerLineActor.GetProperty( ).BackfaceCullingOn( );
		centerLineActor.GetProperty( ).FrontfaceCullingOff( );
		centerLineActor.GetProperty( ).SetInterpolationToGouraud( );
		centerLineActor.GetProperty( ).SetLineWidth( 3.0f );
		centerLineDataMapper = new vtkPolyDataMapper( );
		centerLineDataMapper.SetInput( centerLineData );
		centerLineActor.SetMapper( centerLineDataMapper );
	}
		
	public vtkActor getCenterLineActor( )
	{
		return centerLineActor;
	}
	
	
	
			
		
	// ATTRIBUTES
	//
	private vtkActor          actor;
	private vtkPolyDataMapper dataMapper;	
	private vtkDataSetMapper  mapper;
	
	private double[][]     vertexData;
	private vtkIdList[]    connectivityData;
	
	private vtkPolyData    data;
	private vtkPoints      points;
	private vtkCellArray   polys;
	private vtkFloatArray  scalars;
	private vtkFloatArray  textureCoords;
	
	private int columns;
	private int radialPoints;
	
	private Point3f[] bodyColumnCenters;
	
	private Vector modifiedIndexVector;
	
	private String texturePath;
	
	private DecimalFormat form;
	
	
	private vtkActor centerLineActor;
	private vtkPolyDataMapper centerLineDataMapper;
	private vtkPolyData centerLineData;
	private vtkCellArray centerLineLines;
	private vtkPoints centerLinePoints;
	
	
	// STATICS
	//
	public static final int X = 0;
	public static final int Y = 1;
	public static final int Z = 2;	
	
}




















































/////////////////////////////////////////////////////////////////////////////////////////	
//	private void interpolateBodyDataColumns( int startIndex, int endIndex )
//	{
//		// Go down the columns and interpolate the data in between 
//		// the start and end radial indicies for each column of data.
//		for( int i=0; i<columns; ++i )
//		{				
//			double startX = vertexData[startIndex+(radialPoints*i)][X];
//			double startY = vertexData[startIndex+(radialPoints*i)][Y];
//			double endX   = vertexData[endIndex+(radialPoints*i)][X];
//			double endY   = vertexData[endIndex+(radialPoints*i)][Y];
//			
//			double deltaX = Math.abs( endX - startX );
//			double deltaY = Math.abs( endY - startY );
//			
//			int currentIndex = startIndex + 1;
//			
//			while( currentIndex != endIndex )
//			{
//				float angle = getStandardRadialAngle( currentIndex );
//	
//				double x = Math.cos( Math.toRadians( angle ) );
//				double y = Math.sin( Math.toRadians( angle ) );
//				
//				x = x * deltaX;
//				y = y * deltaY;
//
//			    vertexData[currentIndex+(radialPoints*i)][X] = x;// + bodyColumnCenters[i].x;
//			    vertexData[currentIndex+(radialPoints*i)][Y] = y;// + bodyColumnCenters[i].y;				
//				
//				currentIndex +=1;
//				
//				if( currentIndex >= radialPoints )  currentIndex = 0;
//			}// end while
//
//		}// end for	
//	}

////////////////////////////////////////////////////////////////////////////////////

//	private void calculateBodyColumnCenters( Vector insectRecordVector )
//	{
//		// The bodyColumnCenters is a point array which keeps track of the
//		// center location for each column of points in the insect body.
//		bodyColumnCenters = new Point3f[columns];
//		
//		for( int i=0; i<columns; ++i )
//		{
//			bodyColumnCenters[i] = new Point3f( );
//			
//			Point3f[] pointList = new Point3f[insectRecordVector.size()];
//			
//		
//			for( int j=0; j<insectRecordVector.size(); ++j )
//			{
//				ImageRecord record = (ImageRecord)insectRecordVector.elementAt( j );
//				
//				// Start a list of the points that will represent the centers
//				// of all the line segments in each column of each record according
//				// to the angle for that record.
//				pointList[j] = new Point3f();
//				
//				// Get the viewer angle.
//				float angle = record.getAngle( );
//				
//				// Get the indicies of the modified points.
//				int index1 = (int)(((angle+90.0f)/360.0f)*(float)radialPoints);
//				int index2 = (int)(((angle+270.0f)/360.0f)*(float)radialPoints);
//				
//				if( index1 >= radialPoints ) index1 = index1 - radialPoints;
//				if( index2 >= radialPoints ) index2 = index2 - radialPoints;
//				
//				// Now that we have the indicies of the modified points of the 
//				// current insect record, we can determine where those points lie
//				// in 3D space because we have already calculated their positions 
//				// in the image registration process.  Now we need to determine 
//				// where the center of that registered data is located.  
//				
//				Point3f index1Point = new Point3f( (float)vertexData[index1+(radialPoints*i)][X],
//				                                   (float)vertexData[index1+(radialPoints*i)][Y], 
//				                                   (float)vertexData[index1+(radialPoints*i)][Z] );
//				                                   
//				Point3f index2Point = new Point3f( (float)vertexData[index2+(radialPoints*i)][X], 
//				                                   (float)vertexData[index2+(radialPoints*i)][Y], 
//				                                   (float)vertexData[index2+(radialPoints*i)][Z] );
//				                                   
//				// Find the "center" point based on the start and end points
//				// of the line segment made between the point at index 1 and 
//				// the point at index 2.  The "center" value for this line segment
//				// is at pointList[j].  We will do a weighted average of the 
//				// points for each record vector to find the true center of this
//				// body column.                               
//				pointList[j].x = ( index1Point.x + index2Point.x ) / 2.0f;
//				pointList[j].y = ( index1Point.y + index2Point.y ) / 2.0f;
//				pointList[j].z = ( index1Point.z + index2Point.z ) / 2.0f;
//								
//			}// end if
//			
//			
//			// Now the point list has the x and y center values for each line 
//			// segment that was modified by the registration process.  We can 
//			// create a weighted average out the x's and y's to determine where 
//			// the actual center point of the body column is located.
//			float x = 0;
//			float y = 0;
//			float z = 0;
//			
//			for( int k=0; k<insectRecordVector.size(); ++k )
//			{
//				ImageRecord record = (ImageRecord)insectRecordVector.elementAt( k );
//				
//				float angle = record.getAngle( );
//				
//				float weightX = (float)Math.sin( Math.toRadians( angle ) );
//				float weightY = (float)Math.cos( Math.toRadians( angle ) );
//				
//
//				
//			}// end for
//	        
//	        
//	        //System.err.println( bodyColumnCenters[i] );
//			
//		}// end for
//	}
	


//////////////////////////////////////////////////////////////////////////

//	private void interpolateBodyDataColumns( int startIndex, int endIndex )
//	{
//		for( int i=0; i<columns; ++i )
//		{			
//			double startX = vertexData[startIndex+(radialPoints*i)][X];
//			double startY = vertexData[startIndex+(radialPoints*i)][Y];
//			double endX   = vertexData[endIndex+(radialPoints*i)][X];
//			double endY   = vertexData[endIndex+(radialPoints*i)][Y];
//			                     					
//			int currentIndex = startIndex + 1;
//			
//			int indexCounter = 1;
//			
//			while( currentIndex != endIndex )
//			{
//				currentIndex +=1;
//				
//				if( currentIndex >= radialPoints )  
//				    currentIndex = 0;
//				
//				indexCounter++;
//				
//			}// end while	
//		}		
//	}

//	private void assembleBodyData( )
//	{
//		// Load the point, cell and data attributes.
//		for( int i=0; i<vertexData.length; ++i )      
//			points.InsertPoint( i, vertexData[i] );
//			
//		for( int i=0; i<connectivityData.length; ++i )  
//			polys.InsertNextCell( connectivityData[i] );
//
////      // Set scalars for per vertex colors.			
////		for( int i=0; i<radialPoints*columns; ++i )      
////			scalars.InsertTuple1( i, radialPoints*columns/2 );  // ?	
//		
//		// Put the point data into the data container.
//		data.SetPoints( points );
//		data.SetPolys( polys );
////		data.GetPointData( ).SetScalars( scalars );
//		
//		dataMapper.SetInput( data );
//		dataMapper.SetScalarRange( 0, radialPoints*columns );
//		
//		actor.SetMapper( dataMapper );	
//	}


//////////////////////////////////////////////////////////////////////////////



//	private void interpolateBodyDataColumns( int startIndex, int endIndex )
//	{
//		System.err.println( "interpolating between " + 
//		                    startIndex + " and " + endIndex + "..." );
//		
//		// Go down the columns and interpolate the data in between 
//		// the start and end radial indicies.
//		for( int i=0; i<columns; ++i )
//		{
//			int oppositeStartIndex = startIndex + radialPoints/2;
//			int oppositeEndIndex = endIndex + radialPoints/2;
//			
//			if( oppositeStartIndex >= radialPoints ) 
//				oppositeStartIndex -= radialPoints;
//				
//			if( oppositeEndIndex >= radialPoints )
//				oppositeEndIndex -= radialPoints;	
//							
//			double startX = vertexData[startIndex+(radialPoints*i)][X];
//			double startY = vertexData[startIndex+(radialPoints*i)][Y];
//			double endX   = vertexData[endIndex+(radialPoints*i)][X];
//			double endY   = vertexData[endIndex+(radialPoints*i)][Y];
//			
//			
//			System.err.println( "startX=" + form.format( startX ) + " " + 
//			                    "startY=" + form.format( startY ) + " " + 
//			                    "endX=" + form.format( endX ) + " " + 
//			                    "endY=" + form.format( endY ) );
//			
////			double oppositeStartX = vertexData[oppositeStartIndex+(radialPoints*i)][X];
////			double oppositeStartY = vertexData[oppositeStartIndex+(radialPoints*i)][Y];
////			double oppositeEndX   = vertexData[oppositeEndIndex+(radialPoints*i)][X];
////			double oppositeEndY   = vertexData[oppositeEndIndex+(radialPoints*i)][Y];
//			
////			double centerX = ((endX + oppositeEndX)/2 + (startX + oppositeStartX)/2)/2;
////			double centerY = ((endY + oppositeEndY)/2 + (startY + oppositeStartY)/2)/2;
//
//
//			double deltaX = Math.abs( endX - startX );
//			double deltaY = Math.abs( endY - startY );
//			
//			float startAngle = getStandardRadialAngle( startIndex );
//			float endAngle = getStandardRadialAngle( endIndex );			
//			
//			System.err.println( "deltaX=" + form.format(deltaX) + " deltaY=" + form.format(deltaY) );
//			
//			int currentIndex = startIndex + 1;
//			
//			while( currentIndex != endIndex )
//			{
//				System.err.println( "\tcurrent index=" + currentIndex );
//				
//				float angle = getStandardRadialAngle( currentIndex );
//				
//				Vector3f radialVector = getStandardRadialVector( currentIndex );
//
//				float percent = angle / ( endAngle - startAngle );
//				
//				System.err.println( "\tangle=" + form.format( angle ) );
//				System.err.println( "\tpercent=" + form.format( percent ) );
//		
//				double x = Math.cos( Math.toRadians( angle ) );
//				double y = Math.sin( Math.toRadians( angle ) );
//				
//				System.err.println( "\t\tx=" + form.format( x ) + " y=" + form.format( y ) );
//				
//				x = x * ( startX + percent * deltaX );
//				y = y * ( startY + percent * deltaY );
//				
//				System.err.println( "\t\tmodified x=" + form.format( x ) + " modified y=" + form.format( y ) );
//				
//			    vertexData[currentIndex+(radialPoints*i)][X] = x;
//			    vertexData[currentIndex+(radialPoints*i)][Y] = y;				
//				
//				currentIndex +=1;
//				
//				if( currentIndex >= radialPoints )  currentIndex = 0;
//			}// end while
//			
//
//		}// end for	
//	}
			
////////////////////////////////////////////////////////////////////////////////

//	private void interpolateBodyDataColumns( int startIndex, int endIndex )
//	{
//		for( int i=0; i<columns; ++i )
//		{			
//			double startX = vertexData[startIndex+(radialPoints*i)][X];
//			double startY = vertexData[startIndex+(radialPoints*i)][Y];
//			double endX   = vertexData[endIndex+(radialPoints*i)][X];
//			double endY   = vertexData[endIndex+(radialPoints*i)][Y];
//			
//			float startAngle = getStandardRadialAngle( startIndex );
//			float endAngle = getStandardRadialAngle( endIndex );
//			
//			double startLength = Math.sqrt( Math.pow( ( startX - 0.0 ), 2.0 ) + 
//			                                Math.pow( ( startY - 0.0 ), 2.0 ) );	
//			
//			double endLength   = Math.sqrt( Math.pow( ( endX - 0.0 ), 2.0 ) + 
//			                                Math.pow( ( endY - 0.0 ), 2.0 ) );
//			                                
//			double deltaLength = endLength - startLength;
//			
//			int numIndicies = getIndexCount( startIndex, endIndex );
//			
//			System.err.println( "numIndicies=" + numIndicies );
//			
//			
//			
//			System.err.println( "startLength=" + form.format(startLength) + " " + 
//			                    "endLength=" + form.format(endLength) + " " + 
//			                    "deltaLength=" + form.format(deltaLength) );
//				
//			                     					
//			int currentIndex = startIndex + 1;
//			
//			int indexCounter = 1;
//			
//			while( currentIndex != endIndex )
//			{
//				float angle = getStandardRadialAngle( currentIndex );
//				
//				float percent = angle / ( endAngle - startAngle );
//								
//				double x = Math.cos( Math.toRadians( angle ) );
//				double y = Math.sin( Math.toRadians( angle ) );
//				
//				double radius = startLength + 
//				                deltaLength*(double)(indexCounter)/(double)(numIndicies);
//				
//				System.err.println( "index=" + currentIndex + 
//				                    " currentLength=" + form.format(radius) );
//				
//				x = x * radius;
//				y = y * radius;
//				
//			    vertexData[currentIndex+(radialPoints*i)][X] = x;
//			    vertexData[currentIndex+(radialPoints*i)][Y] = y;				
//				
//				currentIndex +=1;
//				
//				if( currentIndex >= radialPoints )  
//				    currentIndex = 0;
//				
//				indexCounter++;
//				
//			}// end while	
//		}		
//	}
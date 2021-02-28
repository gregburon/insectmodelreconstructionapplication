

////////////////////////////////////////////////////////////////////////
//
//  InsectModeler Program
//
//  Created by Greg Buron
//  Spring 2003
//  Western Washington University
//
//
//  InsectModel.java
//  
//  The <code>InsectModel</code> class is responsible for creating and 
//  maintaining the parts that create a 3D model of an insect.  This 
//  includes an InsectBody and six separate InsectLeg objects.
//
////////////////////////////////////////////////////////////////////////


package myprojects.insectmodeler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

import java.util.Vector;

import javax.vecmath.Color3f;

import vtk.*;


public class InsectModel
{

	// CONSTRUCTOR
	//
	public InsectModel( int numPoints, int numColumns )
	{
		// Generate the basic data for the body.
		insectBody = new InsectBody( numPoints, numColumns );
		
		// Set the reference point colors for each leg.
		leftFrontLegEndPointColors = new Color3f[4];
		leftFrontLegEndPointColors[0] = new Color3f( 1.0f, 0.0f, 0.0f );
		leftFrontLegEndPointColors[1] = new Color3f( 1.0f, 0.5f, 0.0f );
		leftFrontLegEndPointColors[2] = new Color3f( 1.0f, 1.0f, 0.0f );
		leftFrontLegEndPointColors[3] = new Color3f( 1.0f, 1.0f, 1.0f );
		
		rightFrontLegEndPointColors = new Color3f[4];
		rightFrontLegEndPointColors[0] = new Color3f( 0.0f, 0.0f, 1.0f );
		rightFrontLegEndPointColors[1] = new Color3f( 0.0f, 0.5f, 1.0f );
		rightFrontLegEndPointColors[2] = new Color3f( 0.0f, 0.5f, 0.5f );
		rightFrontLegEndPointColors[3] = new Color3f( 0.0f, 1.0f, 0.5f );		
		
		leftMiddleLegEndPointColors = new Color3f[4];
		leftMiddleLegEndPointColors[0] = new Color3f( 0.5f, 0.5f, 0.5f );
		leftMiddleLegEndPointColors[1] = new Color3f( 1.0f, 0.5f, 0.5f );
		leftMiddleLegEndPointColors[2] = new Color3f( 1.0f, 0.25f, 0.25f );
		leftMiddleLegEndPointColors[3] = new Color3f( 0.5f, 0.25f, 0.25f );
		
		rightMiddleLegEndPointColors = new Color3f[4];
		rightMiddleLegEndPointColors[0] = new Color3f( 1.0f, 1.0f, 0.5f );
		rightMiddleLegEndPointColors[1] = new Color3f( 1.0f, 0.75f, 0.5f );
		rightMiddleLegEndPointColors[2] = new Color3f( 1.0f, 0.75f, 0.75f );
		rightMiddleLegEndPointColors[3] = new Color3f( 0.75f, 0.75f, 0.75f );	
		
		leftBackLegEndPointColors = new Color3f[4];
		leftBackLegEndPointColors[0] = new Color3f( 0.25f, 0.25f, 1.0f );
		leftBackLegEndPointColors[1] = new Color3f( 0.25f, 0.25f, 0.75f );
		leftBackLegEndPointColors[2] = new Color3f( 0.25f, 0.25f, 0.25f );
		leftBackLegEndPointColors[3] = new Color3f( 0.25f, 0.25f, 0.0f );
		
		rightBackLegEndPointColors = new Color3f[4];
		rightBackLegEndPointColors[0] = new Color3f( 0.0f, 0.0f, 0.25f );
		rightBackLegEndPointColors[1] = new Color3f( 0.0f, 0.0f, 0.5f );
		rightBackLegEndPointColors[2] = new Color3f( 0.0f, 0.0f, 0.75f );
		rightBackLegEndPointColors[3] = new Color3f( 0.0f, 1.0f, 0.75f );	
		
						
		// Generate the basic legs of the insect.
		insectLeftFrontLeg  = new InsectLeg( 4, leftFrontLegEndPointColors );
		insectRightFrontLeg = new InsectLeg( 4, rightFrontLegEndPointColors );
		
		insectLeftMiddleLeg  = new InsectLeg( 4, leftMiddleLegEndPointColors );
		insectRightMiddleLeg = new InsectLeg( 4, rightMiddleLegEndPointColors );	

		insectLeftBackLeg  = new InsectLeg( 4, leftBackLegEndPointColors );
		insectRightBackLeg = new InsectLeg( 4, rightBackLegEndPointColors );
				
	}// end constructor	
	
		
	public void setBackfaceCulling( boolean cull )
	// The setBackfaceCulling( ) method sets the actor's backface drawing
	// property.  If backface culling is on, backfaces of polygons will not
	// be rendered.  This method assumes that the actor member variable of 
	// the class has been initialized.
	{
		if( cull == true )
		{
			insectBody.getActor( ).GetProperty( ).BackfaceCullingOn( );
		}// end if
		else
		{
			insectBody.getActor( ).GetProperty( ).BackfaceCullingOff( );
		}// end else
	}
	
	public void setRenderMode( int mode )
	// The setRenderMode( ) method sets the actor's render representation 
	// by the mode input parameter.  The actor can be rendered via modes of
	// POINT, WIREFRAME, or POLYGON representations.  This method assumes 
	// that the actor has been initialized.
	{
		insectBody.setRenderMode( mode );
		insectLeftFrontLeg.setRenderMode( mode );
		insectRightFrontLeg.setRenderMode( mode );
		insectLeftMiddleLeg.setRenderMode( mode );
		insectRightMiddleLeg.setRenderMode( mode );
		insectLeftBackLeg.setRenderMode( mode );
		insectRightBackLeg.setRenderMode( mode );
	}
	
	public void displayBody( boolean display )
	{
		insectBody.displayBody( display );	
	}
	
	public void displayRightFrontLeg( boolean display )
	{
		insectRightFrontLeg.displayLeg( display );	
	}
	
	public void displayRightMiddleLeg( boolean display )
	{
		insectRightMiddleLeg.displayLeg( display );	
	}
	
	public void displayRightBackLeg( boolean display )
	{
		insectRightBackLeg.displayLeg( display );	
	}

	public void displayLeftFrontLeg( boolean display )
	{
		insectLeftFrontLeg.displayLeg( display );		
	}
	
	public void displayLeftMiddleLeg( boolean display )
	{
		insectLeftMiddleLeg.displayLeg( display );	
	}
	
	public void displayLeftBackLeg( boolean display )
	{
		insectLeftBackLeg.displayLeg( display );	
	}
	
	public vtkActor getCenterLineActor( )
	{
		return insectBody.getCenterLineActor( );
	}
					
	public void createModel( Vector recordVector, String bodyTexturePath )
	// The registerModel method takes the vector of insect record objects
	// and creates the modifications to the model according to what the 
	// images contain.  The images should represent the template for the 
	// model from a certain angle.  This information is kept in each 
	// insect record object.
	{	
		insectBody.createBody( recordVector, bodyTexturePath );
		insectLeftFrontLeg.createLeg( recordVector, insectBody.getNumberOfColumns( ) );
		insectRightFrontLeg.createLeg( recordVector, insectBody.getNumberOfColumns( ) );
		insectLeftMiddleLeg.createLeg( recordVector, insectBody.getNumberOfColumns( ) );
		insectRightMiddleLeg.createLeg( recordVector, insectBody.getNumberOfColumns( ) );
		insectLeftBackLeg.createLeg( recordVector, insectBody.getNumberOfColumns( ) );
		insectRightBackLeg.createLeg( recordVector, insectBody.getNumberOfColumns( ) );
	}
	
	
	public void exportModelToVRML( String path, vtkRenderWindow window )
	// The exportModelToVRML( ) method writes a file to the path string location
	// the contents of the render window in the graphics engine.
	{
		vtkVRMLExporter exporter = new vtkVRMLExporter( );
		exporter.SetFileName( path );
		exporter.SetInput( window );
		exporter.Write( );
	}
	
	public vtkActor getBodyActor( )
	// Retrieve the actor for the insect body.
	{
		return insectBody.getActor( );
	}
	
	public vtkActor[] getLeftFrontLegSphereActors( )
	{
		return insectLeftFrontLeg.getLegSegmentActors( );
	}
	
	public vtkActor[] getLeftMiddleLegSphereActors( )
	{
		return insectLeftMiddleLeg.getLegSegmentActors( );
	}
	
	public vtkActor[] getLeftBackLegSphereActors( )
	{
		return insectLeftBackLeg.getLegSegmentActors( );
	}
					
	public vtkActor[] getRightFrontLegSphereActors( )
	{
		return insectRightFrontLeg.getLegSegmentActors( );
	}
	
	public vtkActor[] getRightMiddleLegSphereActors( )
	{
		return insectRightMiddleLeg.getLegSegmentActors( );
	}
	
	public vtkActor[] getRightBackLegSphereActors( )
	{
		return insectRightBackLeg.getLegSegmentActors( );
	}	
	
	
	// ATTRIBUTES
	//
	InsectBody insectBody;
	InsectLeg  insectLeftFrontLeg;
	InsectLeg  insectRightFrontLeg;
	InsectLeg  insectLeftMiddleLeg;
	InsectLeg  insectRightMiddleLeg;
	InsectLeg  insectLeftBackLeg;
	InsectLeg  insectRightBackLeg;
		
	Color3f[] leftFrontLegEndPointColors;
	Color3f[] rightFrontLegEndPointColors;
	Color3f[] leftMiddleLegEndPointColors;
	Color3f[] rightMiddleLegEndPointColors;
	Color3f[] leftBackLegEndPointColors;
	Color3f[] rightBackLegEndPointColors;
			
	// STATICS
	//
	public static final int POINT     = 0;
	public static final int WIREFRAME = 1;
	public static final int POLYGON   = 2;	
	
}







/////////////////////////////////////////////////////////////////////////////
//public class InsectModel
//{
//	// ATTRIBUTES
//	//
//	private vtkActor          actor;
//	private vtkPolyDataMapper dataMapper;	
//	
//	private double[][]     vertexData;
//	private vtkIdList[]    connectivityData;
//	
//	private vtkPolyData    data;
//	private vtkPoints      points;
//	private vtkCellArray   polys;
//	private vtkFloatArray  scalars;
//	
//	private int columns;
//	private int radialPoints;
//	
//	private Vector modifiedIndexVector;
//	
//	DecimalFormat form = new DecimalFormat( "0.000" );
//	
//	
//	// STATICS
//	//
//	public static final int X = 0;
//	public static final int Y = 1;
//	public static final int Z = 2;
//	
//	public static final int POINT     = 0;
//	public static final int WIREFRAME = 1;
//	public static final int POLYGON   = 2;	
//	
//
//	public InsectModel( int numPoints, int numColumns )
//	{
//		// Save the model creation parameters.
//		columns = numColumns;
//		radialPoints = numPoints;
//		
//		// Actual x,y,z coordinates of the cylinder.
//		vertexData = new double[numPoints*numColumns][3];
//		 
//		// connectivity for each facet of the cylinder. 
//		connectivityData = new vtkIdList[numPoints*(numColumns-1)]; 
//		
//		// Set up the containers for the data.
//		data    = new vtkPolyData( );
//		points  = new vtkPoints( );
//		polys   = new vtkCellArray( );
//		scalars = new vtkFloatArray( );
//		
//		
//		// Set up the actual data points.
//		for( int col=0; col<numColumns; ++col )
//		{	
//			for( int i=0; i<numPoints; ++i )
//			{
//				// Set all of the point coordinates for this row.
//				double angle   = 360.0 * (double)i/(double)(numPoints);
//				double radians = Math.toRadians( angle );
//				
//				vertexData[i+(numPoints*col)][X] = 1.0 * Math.cos( radians );
//				vertexData[i+(numPoints*col)][Y] = 1.0 * Math.sin( radians );
//				vertexData[i+(numPoints*col)][Z] = numColumns/2 - col;
//						
//			}// end for( i )
//			
//		}// end for( row )
//		
//		
//		// Set up the connectivity between points.
//		for( int col=0; col<numColumns-1; ++col )
//		{
//			for( int i=0; i<numPoints; ++i )
//			{
//				int index = i+(col*numPoints);
//				
//				connectivityData[index] = new vtkIdList( );	
//				
//				if( i < numPoints-1 )
//				{
//					// Put coordinate references in counter-clockwise order. ??
//					connectivityData[index].InsertNextId( index + 1 );
//					connectivityData[index].InsertNextId( index + numPoints + 1 );
//					connectivityData[index].InsertNextId( index + numPoints );
//					connectivityData[index].InsertNextId( index );
//	
//				}
//				else if( i == numPoints-1 )
//				{
//					// Put coordinate references in counter-clockwise order. ?? 		
//					connectivityData[index].InsertNextId( index - numPoints + 1 );
//					connectivityData[index].InsertNextId( index + 1 );	
//					connectivityData[index].InsertNextId( index + numPoints );
//					connectivityData[index].InsertNextId( index );		
//				}							
//				
//			}// end for( i )
//			
//		}// end for( row )
//		
//		
//		// Initialize the containers for the data.
//		actor = new vtkActor( );
//		actor.GetProperty( ).SetAmbient( 0.25 );
//		
//		dataMapper = new vtkPolyDataMapper( );
//
//		// Use the InsectModel's internally generated 
//		// data structures to update the vtk actor with
//		// the newly generated values for all of the points.		
//		updateModel( );
//		
//		// The modifiedIndexVector is a vector containing all of the 
//		// radial indicies of the cylinder of the body of the model
//		// that are modified during the registration process.  The 
//		// creation of the model process can use this vector after all
//		// of the images have been registered to determine which indicies
//		// have not been modified in the registration process.  These
//		// verticies that have not been modified can then be interpolated
//		// between the verticies that have been modified.
//		modifiedIndexVector = new Vector( );
//	
//	}// end constructor	
//	
//		
//	public void setBackfaceCulling( boolean cull )
//	// The setBackfaceCulling( ) method sets the actor's backface drawing
//	// property.  If backface culling is on, backfaces of polygons will not
//	// be rendered.  This method assumes that the actor member variable of 
//	// the class has been initialized.
//	{
//		if( cull == true )
//		{
//			actor.GetProperty( ).BackfaceCullingOn( );
//		}
//		else
//		{
//			actor.GetProperty( ).BackfaceCullingOff( );
//		}
//	}
//	
//	public void setRenderMode( int mode )
//	// The setRenderMode( ) method sets the actor's render representation 
//	// by the mode input parameter.  The actor can be rendered via modes of
//	// POINT, WIREFRAME, or POLYGON representations.  This method assumes 
//	// that the actor has been initialized.
//	{
//		if( mode == POINT )
//		{
//			actor.GetProperty( ).SetRepresentationToPoints( );
//		}
//		else if( mode == WIREFRAME )
//		{
//			actor.GetProperty( ).SetRepresentationToWireframe( );
//		}
//		else if( mode == POLYGON )
//		{
//			actor.GetProperty( ).SetRepresentationToSurface( );
//		}
//	}
//	
//	public vtkActor getActor( )
//	// Retrieve the actor member variable.
//	{
//		return actor;
//	}
//	
//	
//	public void registerModel( Vector insectRecordVector )
//	// The registerModel method takes the vector of insect record objects
//	// and creates the modifications to the model according to what the 
//	// images contain.  The images should represent the template for the 
//	// model from a certain angle.  This information is kept in each 
//	// insect record object.
//	{	
//		System.err.println( "Creating registration information..." );
//		
//		// Start with a clean modified index vector.
//		modifiedIndexVector.clear( );
//		
//		int size = insectRecordVector.size( );
//		
//		for( int i=0; i<size; ++i )
//		{
//			// Get the record to access image and view angle information
//			// for each element in the insectRecordVector.
//			ImageRecord record = (ImageRecord)insectRecordVector.elementAt( i );
//
//			// Modify the locations of the points that this image record
//			// outlines.  Any points that do not have an image record 
//			// that bisects their plane will be interpolated.
//			registerRecordWithModel( record );
//			
//		}// end for
//		
//		
//		// Once all of the records have been applied to the model, 
//		// then interpolate the points that were not modified to 
//		// the points that the records did modify.  
//		//interpolateModel( );
//		
//		
//		// Use the InsectModel's internally generated 
//		// data structures to update the vtk actor with
//		// the newly generated values for all of the points.
//		updateModel( );
//
//	}// end registerImage( )
//	
//	
//	
//	private void registerRecordWithModel( ImageRecord record )
//	// The registerRecordWithModel method takes an image record and calculates 
//	// the modifications necesary for the model from the data contained in the 
//	// image to make the model conform to the image from that record's view
//	// angle.
//	{
//		// Get the bounds of the search space of the image.
//		int startX = record.getImageDataStartColumn( );
//		int startY = record.getImageDataStartRow( );	
//		int endX   = record.getImageDataEndColumn( );
//		int endY   = record.getImageDataEndRow( );
//		
//		float verticalScanLinesPerModelColumn = 
//		          (float)( endX - startX ) / (float)columns;
//		          
//		int totalLength = endX - startX;
//		int imageVerticalCenter = record.getImageProcessor( ).getHeight( )/2;
//		
//		System.err.println( "verticalScanLinesPerModelColumn=" + 
//							verticalScanLinesPerModelColumn );
//							
//		System.err.println( "totalLength=" + totalLength );
//		
//		System.err.println( "imageVerticalCenter=" + imageVerticalCenter );
//							
//							
//		// Go through each of the columns in the model and calculate
//		// the average length of the 2D image's vertical scan lines
//		// that will represent the size of this area of the model.
//		for( int modelColumn=0; modelColumn<columns; ++modelColumn )
//		{
//			int startCol = startX + modelColumn * (int)verticalScanLinesPerModelColumn;
//			int endCol   = startCol + (int)verticalScanLinesPerModelColumn;
//			
//			int totalHeight = 0;
//			float averageHeight = 0.0f;
//			
//			int totalCenter = 0;
//			float averageCenter = 0.0f;
//			
//			// Sum up the "heights" of the vertical columns which will 
//			// represent the "height" for this modelColumn.
//			for( int currentCol = startCol; currentCol < endCol; ++currentCol )
//			{
//				// Get the scan line data for this vertical column in 
//				// the ImageRecords image file.  The tuple returned 
//				// corresponds to the total number of pixels long the
//				// insect data is, and the center of that data relative
//				// to the top of the data.
//				Vector2f scanLineData = record.getColumnData( currentCol );
//
//				
//				totalHeight += scanLineData.x;
//				totalCenter += scanLineData.y;
//				
//			}// end for
//			
//			averageHeight = totalHeight/verticalScanLinesPerModelColumn;
//			averageCenter = totalCenter/verticalScanLinesPerModelColumn;
//			
//			
//			applyDistortionToModelData( modelColumn, 
//			                            averageHeight, 
//			                            totalLength, 
//			                            averageCenter, 
//			                            record );
//			
//		}// end for
//		
//	}// end updateModel( )
//	
//	
//	private void applyDistortionToModelData( int   modelColumn, 
//											 float height, 
//											 float totalLength,
//											 float relativeCenter,
//											 ImageRecord record )
//	// The applyDistortionToModelData( ) method modifies the width of the 
//	// two opposing data points in the cylinder model column at modelColumn 
//	// according to the height parameter and the viewer's location found in
//	// the ImageRecord parameter.  
//	{
//		// Get the viewer angle.
//		float angle = record.getAngle( );
//		
//		// The angle will tell you the plane that slices this model. 
//		// If the angle is 0, then the data points 0 and radialPoints/2,
//		// are the points that are in the plane.  We are interested 
//		// in the points exactly perpendicular to those points.  
//		// Therefore we need an index based on the ( angle + 90 ).
//		// The index1 value indicates the index of the radial points that
//		// are going to be modified by the "top" pixels of the image,
//		// and the index2 is the index of the radial points that are 
//		// going to be modified by the "bottom" pixels of the image.
//		int index1 = (int)(((angle+90.0f)/360.0f)*(float)radialPoints);
//		int index2 = (int)(((angle+270.0f)/360.0f)*(float)radialPoints);
//		
//		if( index1 >= radialPoints ) index1 = index1 - radialPoints;
//		if( index2 >= radialPoints ) index2 = index2 - radialPoints;
//		
//		
//		// Find the vector along the origin that this point normally
//		// makes when the body is a cylinder.  This is the vector 
//		// direction that this point should be moved along.
//		Vector3f index1Vector = getStandardRadialVector( index1 );
//		Vector3f index2Vector = getStandardRadialVector( index2 );
//
//
//		// The distance between the two points should be the "height" parameter
//		// to this function.  
//		float normalizeFactor = ( (float)columns/(float)totalLength );
//		
//		
//		float index1ModificationX = 
//		       height/2.0f * normalizeFactor * index1Vector.x + 
//		       relativeCenter/2.0f * normalizeFactor * Math.abs( index1Vector.x );
//		
//		float index1ModificationY = 
//		       height/2.0f * normalizeFactor * index1Vector.y + 
//		       -1.0f * relativeCenter/2.0f * normalizeFactor * Math.abs( index1Vector.y ); 
//		       
//		float index2ModificationX = 
//			   height/2.0f * normalizeFactor * index2Vector.x + 
//			   relativeCenter/2.0f * normalizeFactor * Math.abs( index2Vector.x );
//			   
//		float index2ModificationY = 
//		       height/2.0f * normalizeFactor * index2Vector.y + 
//		       -1.0f * relativeCenter/2.0f * normalizeFactor * Math.abs( index2Vector.y );
//		       
//		
//		vertexData[index1+(radialPoints*modelColumn)][X] = index1ModificationX;
//		vertexData[index1+(radialPoints*modelColumn)][Y] = index1ModificationY;
//	    						     
//		vertexData[index2+(radialPoints*modelColumn)][X] = index2ModificationX;
//		vertexData[index2+(radialPoints*modelColumn)][Y] = index2ModificationY;	
//		
//	}
//	
//
//	private void updateModel( )
//	// The updateModel( ) method resets the model's geometry by reloading the 
//	// geometry points and connectivity data into the appropriate container
//	// objects and resets the actor's data mapper to these points' new locations.
//	// This method should be called when the points for the model have been 
//	// changed and the model should reflect all of the changes to the locations
//	// of new calculated points.
//	{
//		// Load the point, cell and data attributes.
//		for( int i=0; i<radialPoints*columns; ++i )      
//			points.InsertPoint( i, vertexData[i] );
//			
//		for( int i=0; i<radialPoints*(columns-1); ++i )  
//			polys.InsertNextCell( connectivityData[i] );
//			
//		for( int i=0; i<radialPoints*columns; ++i )      
//			scalars.InsertTuple1( i, radialPoints*columns/2 );  // ?	
//		
//		// Put the point data into the data container.
//		data.SetPoints( points );
//		data.SetPolys( polys );
//		data.GetPointData( ).SetScalars( scalars );
//		
//		dataMapper.SetInput( data );
//		dataMapper.SetScalarRange( 0, radialPoints*columns );
//		
//		actor.SetMapper( dataMapper );			
//	}	
//	
//	private Vector3f getStandardRadialVector( int index )
//	{
//		Vector3f vector = new Vector3f( );
//		
//		double angle   = 360.0 * (double)index/(double)(radialPoints);
//		double radians = Math.toRadians( angle );
//		
//		vector.x = 1.0f * (float)Math.cos( radians );
//		vector.y = 1.0f * (float)Math.sin( radians );
//		vector.z = 0.0f;
//		
//		return vector;
//	}
//	
//	private float getVectorXSign( Vector3f vector )
//	{
//		if( vector == null )    return 0.0f;
//		if( vector.x >= 0.0f )	return 1.0f;	
//		else return -1.0f;
//	}	
//	
//	private float getVectorYSign( Vector3f vector )
//	{ 
//		if( vector == null )    return 0.0f;
//		if( vector.y >= 0.0f )	return 1.0f;	
//		else return -1.0f;	
//	}	
//	
//	private Point3f getModifiedColumnCenter( int column )
//	// Go through the modified points in this slice of the model 
//	// and determine the center point based only on the points
//	// that were registered.  These modified indicies can be 
//	// found in the modifiedIndexVector.
//	{
//		Point3f center = new Point3f( );
//		
//		for( int i=0; i<radialPoints/2; ++i )
//		{
//			if( modifiedIndexVector.indexOf( new Integer(i) ) != -1 )
//			{
//				// This index has been modified.  The index directly 
//				// opposite has also been modified.  Determine the 
//				// x,y,z contribution of this slice to the center
//				// of this slice.
//				int index1 = i;
//				int index2 = i + radialPoints/2;
//				
//				double x1 = vertexData[index1+(radialPoints*column)][X];
//				double x2 = vertexData[index2+(radialPoints*column)][X];
//				
//				double y1 = vertexData[index1+(radialPoints*column)][Y];
//				double y2 = vertexData[index2+(radialPoints*column)][Y];
//				
//				double z  = vertexData[index1+(radialPoints*column)][Z];
//				
//			}
//			
//			
//		}// end for
//		
//		return center;
//	}
//}


///////////////////////////////////////////////////////////////////////////////



//		Point3f  eye   = record.getEyeLocation( );
//		Point3f  focus = record.getFocusLocation( );
//		Vector3f up    = record.getUpVector( );
//		
//		float x = eye.x - focus.x;
//		float y = eye.y - focus.y;
//		
//		float angle = (float)Math.toDegrees( Math.atan( y / x ) );


//	private void interpolateModel( )
//	{
//		for( int modelColumn=0; modelColumn<columns; ++modelColumn )
//		{
//			// Find the center of this slice, based only
//			// on the points that have been modified.
//			Point3f center = getModifiedColumnCenter( modelColumn );
//			
//			
//			for( int index=0; index<radialPoints; ++index )
//			{
//				if( modifiedIndexVector.indexOf( new Integer(index) ) == -1 )
//				{
//					// This point must be interpolated.
//					
//				}// end if
//				
//			}// end for
//			
//		}// end for
//	}




//		// Save off these indicies to let the algorithm know which points
//		// were set and which points still need to be interpolated.
//		if( modifiedIndexVector.indexOf( new Integer( index1 ) ) == -1 )
//		{
//			modifiedIndexVector.add( new Integer( index1 )  );
//		}// end if
//		if( modifiedIndexVector.indexOf( new Integer( index2 )  ) == -1 )
//		{
//			modifiedIndexVector.add( new Integer( index2 )  );
//		}// end if



//		float deltaX = width * vpn.y;
//		float deltaY = width * vpn.x;
//		
//		float centerX = centerOffset * vpn.y;
//		float centerY = centerOffset * vpn.x;
//		
//		// Modify the delta values to account for the total length of the 
//		// model's geometry.
//		deltaX = deltaX * ( (float)columns/(float)totalLength );
//		deltaY = deltaY * ( (float)columns/(float)totalLength );
//		
//		centerX = centerX * ( (float)columns/(float)totalLength );
//		centerY = centerY * ( (float)columns/(float)totalLength );
//		
//		System.err.println( "\tdeltaX=" + deltaX );
//		System.err.println( "\tdeltaY=" + deltaY );
//		System.err.println( "\tcenterX=" + centerX );
//		System.err.println( "\tcenterY=" + centerY );		
//		
//		
//		// Now we have the indicies into the geometry that are to be modified.
//		// Modify the vertex data with the "width" parameter ( how long that
//		// data was for this modelColumn ).
//		vertexData[index1+(radialPoints*modelColumn)][X] = 
//		           deltaX / 2.0f * getVectorXSign( index1Vector ) + 
//		           centerX / 2.0f;
//		           
//		vertexData[index1+(radialPoints*modelColumn)][Y] = 
//		           deltaY / 2.0f * getVectorYSign( index1Vector ) + 
//		           centerY / 2.0f;
//		           
//		vertexData[index2+(radialPoints*modelColumn)][X] = 
//		           deltaX / 2.0f * getVectorXSign( index2Vector ) + 
//		           centerX / 2.0f;
//		           
//		vertexData[index2+(radialPoints*modelColumn)][Y] = 
//		           deltaY / 2.0f * getVectorYSign( index2Vector ) + 
//		           centerY / 2.0f;	






//	private void registerRecordWithModel( ImageRecord record )
//	// The registerRecordWithModel method takes an image record and calculates 
//	// the modifications necesary for the model from the data contained in the 
//	// image to make the model conform to the image from that record's view
//	// angle.
//	{
//		// Get the bounds of the search space of the image.
//		int startX = record.getImageDataStartColumn( );
//		int startY = record.getImageDataStartRow( );	
//		int endX   = record.getImageDataEndColumn( );
//		int endY   = record.getImageDataEndRow( );
//		
//		float verticalScanLinesPerModelColumn = 
//		          (float)( endX - startX ) / (float)columns;
//		          
//		int totalLength = endX - startX;
//		int imageVerticalCenter = record.getImageProcessor( ).getHeight( )/2;
//		
//		System.err.println( "verticalScanLinesPerModelColumn=" + 
//							verticalScanLinesPerModelColumn );
//							
//		System.err.println( "totalLength=" + totalLength );
//		
//		System.err.println( "imageVerticalCenter=" + imageVerticalCenter );
//							
//							
//		// Go through each of the columns in the model and calculate
//		// the average length of the 2D image's vertical scan lines
//		// that will represent the size of this area of the model.
//		for( int modelColumn=0; modelColumn<columns; ++modelColumn )
//		{
//			int startCol = startX + modelColumn * (int)verticalScanLinesPerModelColumn;
//			int endCol   = startCol + (int)verticalScanLinesPerModelColumn;
//			
//			int total = 0;
//			int center = 0;
//			float averageLength = 0.0f;
//			float averageCenter = 0.0f;
//			float centerOffset  = 0.0f;
//			
//			// Sum up the lengths of the vertical column which will 
//			// represent the width for this modelColumn.
//			for( int currentCol = startCol; currentCol < endCol; ++currentCol )
//			{
//				//int verticalLength = record.getColumnDataLength( currentCol );
//				Vector2f scanlineData = record.getColumnData( currentCol );
//				
//				float verticalLength = scanlineData.x;
//				float verticalCenter = scanlineData.y;
//				
//				total += verticalLength;
//				center += verticalCenter;
//				
//			}// end for
//			
//			averageLength = (float)total/verticalScanLinesPerModelColumn;
//			averageCenter = (float)center/verticalScanLinesPerModelColumn;
//			centerOffset  = (float)imageVerticalCenter - averageCenter;
//		
//			
//			// The average width for this column of the model for this
//			// view angle has now been calculated.  This information can
//			// now be applied to the image data structure.
//			applyDistortionToModelData( modelColumn, averageLength, 
//			                            totalLength, centerOffset, record );
//			
//		}// end for
//		
//	}// end updateModel( )



//		// Now determine the contributions to the modifications of the 
//		// x and y component of the points at those indicies.  The contribution
//		// to the points at those locations are directly proportional to the 
//		// location of the eye for that image.
////		Vector3f vpn = new Vector3f( );
////		
////		vpn.x = -1.0f * (float)Math.cos( Math.toRadians( angle ) );
////		vpn.y = 1.0f * (float)Math.sin( Math.toRadians( angle ) );
////		vpn.z = 0.0f;
////			     
////		vpn.normalize( );
////		
////		System.err.println( "\tvpn=" + vpn.toString( ) );



////////////////////////////////////////////////////////////////////////
//
//  InsectModeler Program
//
//  Created by Greg Buron
//  Spring 2003
//  Western Washington University
//
//
//  InsectLeg.java
//  
//  The <code>InsectLeg</code> class is responsible for creating and
//  modifying the data structures that represent the insect model's 
//  legs.
//
////////////////////////////////////////////////////////////////////////


package myprojects.insectmodeler;

import javax.vecmath.AxisAngle4f;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector4f;


import java.text.DecimalFormat;

import java.util.Vector;

import vtk.*;


public class InsectLeg
{
	
	// CONSTRUCTOR
	//
	public InsectLeg( int segmentEndPoints, Color3f[] endPointColors )
	{
		this.segmentEndPoints = segmentEndPoints;
		this.endPointColors = endPointColors;
		
		form = new DecimalFormat( "#.##" );
		
		legSegmentActors = new vtkActor[segmentEndPoints-1];
	}// end constructor
	
	
	
	// METHODS
	//
	
	public void setRenderMode( int mode )
	// Set the visual representation of this leg as either point, wireframe, or
	// polygon mode.
	{
		if( legSegmentActors == null )
			return;
			
		if( mode == InsectModel.POINT )
		{
			for( int i=0; i<segmentEndPoints-1; ++ i )
			{
				if( legSegmentActors[i] != null )
					legSegmentActors[i].GetProperty( ).SetRepresentationToPoints( );
			}// end for
		}// end if
		else if( mode == InsectModel.WIREFRAME )
		{
			for( int i=0; i<segmentEndPoints-1; ++ i )
			{
				if( legSegmentActors[i] != null )
					legSegmentActors[i].GetProperty( ).SetRepresentationToWireframe( );
			}// end for
		}// end else if
		else if( mode == InsectModel.POLYGON )
		{
			for( int i=0; i<segmentEndPoints-1; ++ i )
			{
				if( legSegmentActors[i] != null )
					legSegmentActors[i].GetProperty( ).SetRepresentationToSurface( );
			}// end for			
		}// end else if	
	}	
	
	public void createLeg( Vector insectRecordVector, int bodyColumns )
	{
		System.err.println( "Creating insect leg..." );
		
		int numRecords = insectRecordVector.size( );
		
		imageLocationEndPoints = new Vector2f[numRecords][segmentEndPoints];
		
		// Save off the locations of the end points for each of the leg segments.
		for( int i=0; i<numRecords; ++i )
		{
			ImageRecord record = (ImageRecord)insectRecordVector.elementAt( i );
			
			for( int j=0; j<segmentEndPoints; ++j )
			{
				imageLocationEndPoints[i][j] = record.getLegDataPoint( endPointColors[j] );  
				
				if( imageLocationEndPoints[i][j].x == -1 || 
				    imageLocationEndPoints[i][j].y == -1 )
				{
				   // This record does not have enough information to 
				   // create this leg.  Do not attempt to create the leg
				   // with deficient information.
				   System.err.println( "Insufficient leg end point information." );
				   return;
				}// end if  
				               
			}// end for
			
		}// end for

		
		spatialEndPoints = initializeSpatialEndPoints( numRecords, segmentEndPoints );
		
		
		for( int i=0; i<numRecords; ++i )
		{
			ImageRecord record = (ImageRecord)insectRecordVector.elementAt( i );
			
			// Get the image data from the record.
			int startR = record.getBodyDataStartColumn( );
			int startS = record.getBodyDataStartRow( );
			int endR   = record.getBodyDataEndColumn( );
			int endS   = record.getBodyDataEndRow( );
			
			int imageWidth  = record.getImageWidth( );
			int imageHeight = record.getImageHeight( );	
			
			int totalLength = endR - startR;
			int totalHeight = endS - startS;			
			
			if( (int)record.getAngle( ) == 0 )
			{
				// This record has accurate y,z coordinates for each segment
				// end point.  Determine where the y,z coordinates are and 
				// populate the corresponding spatialEndPoints point with those
				// y,z coordinates.		
				for( int j=0; j<segmentEndPoints; ++j )
				{
					float normalizeFactor = ( (float)bodyColumns/(float)totalLength );
					
					float percentZ = ( imageLocationEndPoints[i][j].x - startR )/totalLength;
	
					float z = ( totalLength/2 - ( totalLength * percentZ ) ) * normalizeFactor;
	
					Vector4f columnData = 
					         record.getColumnData( (int)imageLocationEndPoints[i][j].x );
		
					float percentY = ( imageLocationEndPoints[i][j].y - columnData.z )/
					                 (columnData.w - columnData.z );
		
					float y = ( imageLocationEndPoints[i][j].y - 
					            imageHeight/2 - columnData.y ) * normalizeFactor + 
					          columnData.y * normalizeFactor;  
					
					spatialEndPoints[i][j].y = -1.0f * y;
					spatialEndPoints[i][j].z = z;
					
					
				}// end for			
				
			}// end if
			
			else if( (int)record.getAngle( ) == 90 )
			{
				// This record has accurate x,z coordinates for each segment
				// end point.  Determine where the x,z coordinates are and
				// populate the corresponding spatialEndPoints point with those
				// x,z coordinates.	
				for( int j=0; j<segmentEndPoints; ++j )
				{
					float normalizeFactor = ( (float)bodyColumns/(float)totalLength );
					
					float percentZ = ( imageLocationEndPoints[i][j].x - startR )/totalLength;
					
					float z = ( totalLength/2 - ( totalLength * percentZ ) ) * normalizeFactor;
					
					Vector4f columnData = 
					         record.getColumnData( (int)imageLocationEndPoints[i][j].x );
					
					float percentX = ( imageLocationEndPoints[i][j].y - columnData.z )/
					                 (columnData.w - columnData.z );

					float x = ( imageLocationEndPoints[i][j].y - 
					            imageHeight/2 - columnData.y ) * normalizeFactor + 
					          columnData.y * normalizeFactor;  
					
					spatialEndPoints[i][j].x = x;
					spatialEndPoints[i][j].z = z;				
					
				}// end for						
				
			}// end else if
			
		}// end for
							
		//
		//
		// Best to calculate actual locations as intersections of two vectors in space???
		// re-implement location calculation.
		//
		
		// Now that all of the points have been extracted as best they can from 
		// the image information, we have to create an actual set of points from 
		// that data that can be used to create the real end points for the insect
		// leg segments for this particular leg.  Go through the 
		actualSpatialEndPoints = new Point3f[segmentEndPoints];
		
		for( int i=0; i<segmentEndPoints; ++i )
			actualSpatialEndPoints[i] = new Point3f( );
		
		for( int i=0; i<segmentEndPoints; ++i )
		{
			float totalZ = 0.0f;
			
			for( int j=0; j<numRecords; ++j )
			{
				if( spatialEndPoints[j][i].x != Float.MAX_VALUE )
					actualSpatialEndPoints[i].x = spatialEndPoints[j][i].x;
				
				if( spatialEndPoints[j][i].y != Float.MAX_VALUE )
					actualSpatialEndPoints[i].y = spatialEndPoints[j][i].y;	
				
				totalZ += spatialEndPoints[j][i].z;	
			}// end for
			
			float averageZ = totalZ / numRecords;
			
			actualSpatialEndPoints[i].z = averageZ;
			
		}// end for
	}
	
	private Point3f[][] initializeSpatialEndPoints( int numRecords, int segmentEndPoints )
	{
		Point3f[][] endPoints = new Point3f[numRecords][segmentEndPoints];
			
		for( int i=0; i<numRecords; ++i )
		{
			for( int j=0; j<segmentEndPoints; ++j )
			{
				endPoints[i][j] =  
				   new Point3f( Float.MAX_VALUE, 
				                Float.MAX_VALUE, 
				                Float.MAX_VALUE );
			}// end for
		}// end for
		
		return endPoints;
	}	
	
	public vtkActor[] getLegSegmentActors( )
	{
		for( int segment=0; segment<segmentEndPoints-1; ++segment )
		{
			if( legSegmentActors[segment] == null )
			{
				int startIndex = segment;
				int endIndex = segment + 1;
				
				legSegmentActors[segment] = 
				       createLegSegmentActor( startIndex, endIndex );
			}// end if
		}// end for
		
		return legSegmentActors;
	}
	
	private vtkActor createLegSegmentActor( int startIndex, int endIndex )
	{
	    if( actualSpatialEndPoints == null )
	    {
	    	return null;
	    }// end if
	    	              
		double x = actualSpatialEndPoints[endIndex].x - 
		           actualSpatialEndPoints[startIndex].x;
		           
		double y = actualSpatialEndPoints[endIndex].y - 
		           actualSpatialEndPoints[startIndex].y;
		           
		double z = actualSpatialEndPoints[endIndex].z - 
		           actualSpatialEndPoints[startIndex].z;
		           
		double length = Math.sqrt( x*x + y*y + z*z );
		
		
		Vector3d segmentVector = new Vector3d( x, y, z );
		segmentVector.normalize( );
		
		Vector3d sphereVector = new Vector3d( 0.0, 0.0, 1.0 );
		sphereVector.normalize( );
		
		Vector3d axisVector = new Vector3d( );
		
		axisVector.cross( sphereVector, segmentVector );
		axisVector.normalize( );                                    
		
		double angle = segmentVector.angle( sphereVector );
		
		vtkTransform transform = new vtkTransform( );

		transform.Translate( ( actualSpatialEndPoints[endIndex].x + 
		                       actualSpatialEndPoints[startIndex].x )/2, 
		                     ( actualSpatialEndPoints[endIndex].y +
		                       actualSpatialEndPoints[startIndex].y )/2, 
		                     ( actualSpatialEndPoints[endIndex].z + 
		                       actualSpatialEndPoints[startIndex].z )/2 );	
		
		transform.RotateWXYZ( Math.toDegrees( angle ), 
		                      axisVector.x, 
		                      axisVector.y, 
		                      axisVector.z );
		
		transform.Scale( 0.15, 0.15, 0.90 );
		
		vtkActor sphereActor = new vtkActor( );
		vtkPolyDataMapper mapper = new vtkPolyDataMapper( );
		vtkSphereSource sphereSource = new vtkSphereSource( );
		sphereSource.SetRadius( length/2 + 0.1*length );
		sphereSource.SetPhiResolution( 12 );
		sphereSource.SetThetaResolution( 12 );		
		mapper.SetInput( sphereSource.GetOutput( ) );
		sphereActor.SetMapper( mapper );			
		sphereActor.SetUserMatrix( transform.GetMatrix( ) );
		
		sphereActor.GetProperty( ).SetAmbient( 0.25 );
		sphereActor.GetProperty( ).SetDiffuse( 0.25 );
		sphereActor.GetProperty( ).SetAmbientColor( 1.0f, 1.0f, 1.0f );
		sphereActor.GetProperty( ).SetDiffuseColor( 1.0f, 1.0f, 1.0f );
		sphereActor.GetProperty( ).BackfaceCullingOn( );
		sphereActor.GetProperty( ).FrontfaceCullingOff( );
		sphereActor.GetProperty( ).SetInterpolationToGouraud( );		
		
		return sphereActor;
	}
	
	public void displayLeg( boolean display )
	// Set the visibility for this leg.  All leg segments are either completely
	// visible or completely invisible, based on the display parameter sent to 
	// this method.
	{
		if( legSegmentActors == null ) 
			return;
			
		if( display == true )
		{
			for( int i=0; i<segmentEndPoints-1; ++ i )
			{
				if( legSegmentActors[i] != null )
					legSegmentActors[i].GetProperty( ).SetOpacity( 1.0 );
			}// end for
		}// end if
		else if( display == false )
		{
			for( int i=0; i<segmentEndPoints-1; ++ i )
			{
				if( legSegmentActors[i] != null )
					legSegmentActors[i].GetProperty( ).SetOpacity( 0.0 );
			}// end for
		}// end else
	}



	// ATTRIBUTES
	//
	
	private double[][] vertexData;
	private vtkIdList[] connectivityData;
	
	private vtkPolyData    data;
	private vtkPoints      points;
	private vtkCellArray   lines;
	private vtkFloatArray  scalars;
	
	private vtkActor[] legSegmentActors;
	
	private int segmentEndPoints;
	private Color3f[] endPointColors;
	private Vector2f[][] imageLocationEndPoints;
	private Point3f[][] spatialEndPoints;
	private Point3f[] actualSpatialEndPoints;
	
	private DecimalFormat form;
	
	// STATICS
	//
	public static final int X = 0;
	public static final int Y = 1;
	public static final int Z = 2;	
}


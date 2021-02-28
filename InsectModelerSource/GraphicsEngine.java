

////////////////////////////////////////////////////////////////////////
//
//  InsectModeler Program
//
//  Created by Greg Buron
//  Spring 2003
//  Western Washington University
//
//
//  GraphicsEngine.java
//  
//  The <code>GraphicsEngine</code> class is responsible for creating 
//  a VTK graphics environment in which an insect model can be viewed 
//  within a Java application.
//
////////////////////////////////////////////////////////////////////////


package myprojects.insectmodeler;


import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import vtk.*;
//import vtkPanel;

public class GraphicsEngine
{

	// CONSTRUCTOR
	//
	public GraphicsEngine( )
	{
		renderPanel  = new vtkPanel( );
		camera       = new vtkCamera( );
		renderer     = renderPanel.GetRenderer( );
		
		camera.SetClippingRange( 1.0, 1000.0 );
		
		renderer.SetActiveCamera( camera );
		renderer.SetAmbient( 0.5f, 0.5f, 0.5f );


		renderWindow = renderer.GetRenderWindow( );	
		
		initializeAxes( );
		
		renderPanel.GetRenderer( ).SetAmbient( 0.0, 0.0, 0.0 );
		
		addLight( new Point3f( 0.0f, 100.0f, 0.0f ), 
		          new Point3f( 0.0f, 0.0f, 0.0f ), 
		          new Color3f( 1.0f, 1.0f, 1.0f ) );  
		          
		addLight( new Point3f( 0.0f, -100.0f, 0.0f ), 
		          new Point3f( 0.0f, 0.0f, 0.0f ), 
		          new Color3f( 1.0f, 1.0f, 1.0f ) );
		          		          
		addLight( new Point3f( 100.0f, 0.0f, 0.0f ), 
		          new Point3f( 0.0f, 0.0f, 0.0f ), 
		          new Color3f( 1.0f, 1.0f, 1.0f ) );  
		          
		addLight( new Point3f( -100.0f, 0.0f, 0.0f ), 
		          new Point3f( 0.0f, 0.0f, 0.0f ), 
		          new Color3f( 1.0f, 1.0f, 1.0f ) );
		          	
	}// end constructor
	
	
	
	private void initializeAxes( )
	{
		xAxis = new vtkLineSource( );
		yAxis = new vtkLineSource( );
		zAxis = new vtkLineSource( );
		
		xAxis.SetPoint1( 0.0f, 0.0f, 0.0f );
		xAxis.SetPoint2( AXIS_LENGTH, 0.0f, 0.0f );
		xAxis.SetResolution( 15 );
		
		yAxis.SetPoint1( 0.0f, 0.0f, 0.0f );
		yAxis.SetPoint2( 0.0f, AXIS_LENGTH, 0.0f );
		yAxis.SetResolution( 15 );
		
		zAxis.SetPoint1( 0.0f, 0.0f, 0.0f );
		zAxis.SetPoint2( 0.0f, 0.0f, AXIS_LENGTH );
		yAxis.SetResolution( 15 );
		
		vtkPolyDataMapper xDataMapper = new vtkPolyDataMapper( );
		vtkPolyDataMapper yDataMapper = new vtkPolyDataMapper( );
		vtkPolyDataMapper zDataMapper = new vtkPolyDataMapper( );
		
		xDataMapper.SetInput( xAxis.GetOutput( ) );
		yDataMapper.SetInput( yAxis.GetOutput( ) );
		zDataMapper.SetInput( zAxis.GetOutput( ) );
		
		xAxisActor = new vtkActor( );
		yAxisActor = new vtkActor( );
		zAxisActor = new vtkActor( );
		
		xAxisActor.SetMapper( xDataMapper );
		xAxisActor.GetProperty( ).SetColor( 1.0f, 0.0f, 0.0f );
		renderer.AddActor( xAxisActor );
		
		yAxisActor.SetMapper( yDataMapper );
		yAxisActor.GetProperty( ).SetColor( 0.0f, 1.0f, 0.0f );
		renderer.AddActor( yAxisActor );
		
		zAxisActor.SetMapper( zDataMapper );
		zAxisActor.GetProperty( ).SetColor( 0.0f, 0.0f, 1.0f );
		renderer.AddActor( zAxisActor );		
	}
	
	public vtkPanel getRenderPanel( )
	{
		return renderPanel;
	}
	
	public vtkRenderWindow getRenderWindow( )
	{
		return renderWindow;
	}
	
	public void updateViewPort( )
	{
		renderWindow.Render( );
	}
	
	public void lookAt( Point3f eye, Point3f focus, Vector3f up )
	{
		camera.SetPosition( eye.x, eye.y, eye.z );
		camera.SetFocalPoint( focus.x, focus.y, focus.z );
		camera.SetViewUp( up.x, up.y, up.z );
	}
	
	public void setCameraPosition( Point3f position )
	{
		camera.SetPosition( position.x, position.y, position.z );
	}
	
	public void setCameraFocalPoint( Point3f focus )
	{
		camera.SetFocalPoint( focus.x, focus.y, focus.z );
	}
	
	public void setCameraUpVector( Vector3f up )
	{
		camera.SetViewUp( up.x, up.y, up.z );
	}
	
	public Point3f getCameraEyeLocation( )
	{
		Point3f eye        = new Point3f( );
		double  position[] = new double[3];
		
		position = camera.GetPosition( );
		
		eye.x = (float)position[0];
		eye.y = (float)position[1];
		eye.z = (float)position[2];
		
		return eye;		
	}
	
	public Point3f getCameraFocusLocation( )
	{
		Point3f focus      = new Point3f( );
		double  position[] = new double[3];
		
		position = camera.GetFocalPoint( );
		
		focus.x = (float)position[0];
		focus.y = (float)position[1];
		focus.z = (float)position[2];
		
		return focus;
	}
	
	public Vector3f getCameraUpVector( )
	{ 
		Vector3f up         = new Vector3f( );
		double   position[] = new double[3];
		
		position = camera.GetViewUp( );
		
		up.x = (float)position[0];
		up.y = (float)position[1];
		up.z = (float)position[2];
		
		return up;
	}
	
	public void addLight( Point3f location, Point3f focus, Color3f color )
	{
		vtkLight light = new vtkLight();
	    light.SetColor( color.x, color.y, color.z );
		light.SetFocalPoint( focus.x, focus.y, focus.z );
		light.SetPosition( location.x,  location.y, location.z );
		renderer.AddLight( light );
	}
	
	public void setBackgroundColor( Color3f color )
	{
		renderer.SetBackground( color.x, color.y, color.z );
	}
	
	public void enableAxes( boolean enable )
	{
		if( enable == false )
		{
			renderer.RemoveActor( xAxisActor );
			renderer.RemoveActor( yAxisActor );
			renderer.RemoveActor( zAxisActor );
		}// end if
		
		else if( enable == true )
		{
			renderer.AddActor( xAxisActor );
			renderer.AddActor( yAxisActor );
			renderer.AddActor( zAxisActor );	
		}// end else if
		
		updateViewPort( );
	}
	
	public void setInsectModel( InsectModel model )
	{
		insectModel = model;
	
		renderer.AddActor( model.getBodyActor( ) );
		
		
		renderer.AddActor( model.getCenterLineActor( ) );
			
			
		vtkActor[] sphereActors = model.getLeftFrontLegSphereActors( );
		
		if( sphereActors != null )
		{
			if( sphereActors[0] != null ) renderer.AddActor( sphereActors[0] );
			if( sphereActors[1] != null ) renderer.AddActor( sphereActors[1] );
			if( sphereActors[2] != null ) renderer.AddActor( sphereActors[2] );
		}
		
		sphereActors = model.getLeftMiddleLegSphereActors( );
		
		if( sphereActors != null )
		{
			if( sphereActors[0] != null ) renderer.AddActor( sphereActors[0] );
			if( sphereActors[1] != null ) renderer.AddActor( sphereActors[1] );
			if( sphereActors[2] != null ) renderer.AddActor( sphereActors[2] );
		}	
		
		sphereActors = model.getLeftBackLegSphereActors( );
		
		if( sphereActors != null )
		{
			if( sphereActors[0] != null ) renderer.AddActor( sphereActors[0] );
			if( sphereActors[1] != null ) renderer.AddActor( sphereActors[1] );
			if( sphereActors[2] != null ) renderer.AddActor( sphereActors[2] );
		}	
		
		sphereActors = model.getRightFrontLegSphereActors( );
		
		if( sphereActors != null )
		{
			if( sphereActors[0] != null ) renderer.AddActor( sphereActors[0] );
			if( sphereActors[1] != null ) renderer.AddActor( sphereActors[1] );
			if( sphereActors[2] != null ) renderer.AddActor( sphereActors[2] );
		}
		
		
		sphereActors = model.getRightMiddleLegSphereActors( );
		
		if( sphereActors != null )
		{
			if( sphereActors[0] != null ) renderer.AddActor( sphereActors[0] );
			if( sphereActors[1] != null ) renderer.AddActor( sphereActors[1] );
			if( sphereActors[2] != null ) renderer.AddActor( sphereActors[2] );
		}		
		
		sphereActors = model.getRightBackLegSphereActors( );
		
		if( sphereActors != null )
		{
			if( sphereActors[0] != null ) renderer.AddActor( sphereActors[0] );
			if( sphereActors[1] != null ) renderer.AddActor( sphereActors[1] );
			if( sphereActors[2] != null ) renderer.AddActor( sphereActors[2] );
		}					
										                    		
		updateViewPort( );
	}
	
	public void destroyInsectModel( )
	{
		// Remove the body actor from the engine.
		renderer.RemoveActor( insectModel.getBodyActor( ) );
		
		// Remove all of the leg actors from the engine.
		vtkActor[] leftFrontLegActors   = insectModel.getLeftFrontLegSphereActors( );
		vtkActor[] leftMiddleLegActors  = insectModel.getLeftMiddleLegSphereActors( );
		vtkActor[] leftBackLegActors    = insectModel.getLeftBackLegSphereActors( );
		vtkActor[] rightFrontLegActors  = insectModel.getRightFrontLegSphereActors( );
		vtkActor[] rightMiddleLegActors = insectModel.getRightMiddleLegSphereActors( );
		vtkActor[] rightBackLegActors   = insectModel.getRightBackLegSphereActors( );
		
		for( int a=0; a<leftFrontLegActors.length; ++a )
			if( leftFrontLegActors[a] != null )
			    renderer.RemoveActor( leftFrontLegActors[a] );
		
		for( int b=0; b<leftMiddleLegActors.length; ++b )
		    if( leftMiddleLegActors[b] != null )
			    renderer.RemoveActor( leftMiddleLegActors[b] );
			
		for( int c=0; c<leftBackLegActors.length; ++c )
		    if( leftBackLegActors[c] != null )
			    renderer.RemoveActor( leftBackLegActors[c] );
			
		for( int d=0; d<rightFrontLegActors.length; ++d )
		    if( rightFrontLegActors[d] != null )
			    renderer.RemoveActor( rightFrontLegActors[d] );
		
		for( int e=0; e<rightMiddleLegActors.length; ++e )
		    if( rightMiddleLegActors[e] != null )
			    renderer.RemoveActor( rightMiddleLegActors[e] );
			
		for( int f=0; f<rightBackLegActors.length; ++f )
		    if( rightBackLegActors[f] != null )
			    renderer.RemoveActor( rightBackLegActors[f] );				
				
		updateViewPort( );
	}
	
	
	
	// ATTRIBUTES
	//
	
	// Vtk rendering objects.
	private vtkRenderer     renderer;
	private vtkCamera       camera;
	private vtkPanel        renderPanel;
	
	private vtkLineSource   xAxis;
	private vtkLineSource   yAxis;
	private vtkLineSource   zAxis;
	
	private vtkActor        xAxisActor;
	private vtkActor        yAxisActor;
	private vtkActor        zAxisActor;
	
	private vtkRenderWindow renderWindow;
		
	// Data to be rendered.
	private InsectModel     insectModel;
	
	
	// STATICS
	//
	private static final float AXIS_LENGTH = 1000.0f;	

}


		
		
		

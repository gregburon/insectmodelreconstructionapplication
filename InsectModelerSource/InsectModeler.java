

////////////////////////////////////////////////////////////////////////
//
//  InsectModeler Program
//
//  Created by Greg Buron
//  Spring 2003
//  Western Washington University
//
//
//  InsectModeler.java
//  
//  The <code>InsectModeler</code> class is responsible for creating
//  the insect modeler program JFrame and coordinating all of the 
//  components and dialogs which allow the user to interact with the
//  insect modeler interface.
//
////////////////////////////////////////////////////////////////////////


package myprojects.insectmodeler;

import ij.ImagePlus;
import ij.io.Opener;
import ij.process.ImageProcessor;


import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Canvas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputListener;

import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;



public class InsectModeler extends JFrame
{     

	
	// CONSTRUCTOR
	//	
	public InsectModeler( ) 
	{
		// Load the VTK libraries.
		System.loadLibrary("vtkCommonJava"); 
		System.loadLibrary("vtkFilteringJava"); 
		System.loadLibrary("vtkIOJava"); 
		System.loadLibrary("vtkImagingJava"); 
		System.loadLibrary("vtkGraphicsJava"); 
		System.loadLibrary("vtkRenderingJava"); 
		System.loadLibrary("vtkHybridJava");
			
		// Instantiate objects for the application.
		graphicsEngine      = new GraphicsEngine( );
		modelerMenu         = new ModelerMenu( );
		destroyModelDialog  = new DestroyModelDialog( );
		controlsPanelDialog = new ControlsPanelDialog( );
		createModelDialog   = new CreateModelDialog( );
		
		// Set the defaults for the controls panel.
		controlsPanelDialog.setCameraEyeLocation( new Point3f( 0.0f, 0.0f, 20.0f ) );
		
		
		// Set up the graphics engine.		
		graphicsEngine.lookAt( controlsPanelDialog.getCameraEyeLocation( ), 
		                       controlsPanelDialog.getCameraFocusLocation( ), 
		                       controlsPanelDialog.getCameraUpVector( ) );
		                       
		graphicsEngine.setBackgroundColor( new Color3f( 1.0f, 1.0f, 1.0f ) );
		                       
			                            		     
		// Set up the controls panel listeners.
		controlsPanelDialog.addCameraPositionListener( new CameraPositionListener( ) );
		controlsPanelDialog.addResetEyeListener( new ResetEyeListener( ) );
		controlsPanelDialog.addResetFocusListener( new ResetFocusListener( ) );
		controlsPanelDialog.addResetUpListener( new ResetUpListener( ) ); 
		
		// Set up the menu listeners.
		modelerMenu.addCreateModelListener( new CreateModelDialogListener( ) );
		modelerMenu.addExportToVRMLListener( new ExportToVRMLListener( ) );
		modelerMenu.addCloseModelListener( new CloseModelListener( ) );
		modelerMenu.addExitListener( new ExitListener( ) );
		modelerMenu.addAxisListener( new AxisListener( ) ); 
		modelerMenu.addControlsPanelListener( new ControlsPanelListener( ) );
		modelerMenu.addPointModeListener( new PointModeListener( ) );
		modelerMenu.addWireframeModeListener( new WireframeModeListener( ) );
		modelerMenu.addPolygonModeListener( new PolygonModeListener( ) );
		modelerMenu.addDisplayBodyListener( new DisplayBodyListener( ) );
		modelerMenu.addDisplayRFLegListener( new DisplayRFLegListener( ) );
		modelerMenu.addDisplayRMLegListener( new DisplayRMLegListener( ) );
		modelerMenu.addDisplayRBLegListener( new DisplayRBLegListener( ) );
		modelerMenu.addDisplayLFLegListener( new DisplayLFLegListener( ) );
		modelerMenu.addDisplayLMLegListener( new DisplayLMLegListener( ) );
		modelerMenu.addDisplayLBLegListener( new DisplayLBLegListener( ) );		
		
		// Set up the dialog listeners.
		createModelDialog.addCreateModelButtonListener( new CreateModelButtonListener( ) );
		destroyModelDialog.addOkListener( new DestroyModelOkListener( ) );        
		
		
		// Add the components to the JFrame.
		Canvas canvas = graphicsEngine.getRenderPanel( );
		CanvasListener canvasListener = new CanvasListener( );
		canvas.addMouseListener( canvasListener );
		canvas.addMouseMotionListener( canvasListener );
		getContentPane( ).add( canvas, BorderLayout.CENTER );
		getContentPane( ).add( modelerMenu, BorderLayout.NORTH );
		
		dragging = false;
			
	}// end constructor

	
	// METHODS
	//
	
	public class CameraPositionListener implements ChangeListener
	// The CameraPositionListener is a listener for the camera position spinner 
	// controls.  Any time one of the camera position spinner controls is
	// modified, either by clicking on the arrows or by changing the number in 
	// the edit field, this listener is sent a message.  If the user is not 
	// dragging the mouse on the canvas, then the spinner message is for this 
	// method.  Get the position information from the spinners and update the 
	// camera with that information.  Also update the orbit controls due to any 
	// changes the position change may have caused.
	{
		// METHODS
		//		
		public void stateChanged( ChangeEvent event )
		{	
			if( dragging == false )
			{
				// The spinners have been modified, so get the values 
				// from the spinners and update the graphicsEngine's 
				// camera position.
				Point3f  eye   = controlsPanelDialog.getCameraEyeLocation( );
				Point3f  focus = controlsPanelDialog.getCameraFocusLocation( );
				Vector3f up    = controlsPanelDialog.getCameraUpVector( );
	
				graphicsEngine.lookAt( eye, focus, up );
				graphicsEngine.updateViewPort( );
				
			}// end if
			
		}// end stateChanged	
	}

		
	public class CanvasListener implements MouseInputListener
	// The CanvasListener is a listener for the vtkRenderPanel in the 
	// GraphicsEngine.  Any changes to the canvas via the mouse are notified
	// in this class.  Therefore, any time the user modifies the position of 
	// the camera with the mouse, the controls panel must be updated to reflect
	// the new position of the camera to keep the controls and the internal 
	// state of the vtkCamera in sync.  Get the coordinates from the graphics
	// engine and send the updated positions to the controls panel.
	{
		// METHODS
		//		
		public void mouseDragged( MouseEvent event ) 
		{
			// Invoked when a mouse button is pressed on a 
			// component and then dragged.
			Point3f  eye   = graphicsEngine.getCameraEyeLocation( );
			Point3f  focus = graphicsEngine.getCameraFocusLocation( );
			Vector3f up    = graphicsEngine.getCameraUpVector( );
			
			// Set all the controls spinners the correct values
			// of the locations to keep the canvas and the controls
			// panel in sync with each other.
			controlsPanelDialog.setCameraEyeLocation( eye );
			controlsPanelDialog.setCameraFocusLocation( focus );
			controlsPanelDialog.setCameraUpVector( up );
			
			graphicsEngine.updateViewPort( );		
		}// end mouseDragged

 		public void mouseMoved( MouseEvent event )
 		{
 			// Invoked when the mouse cursor has been moved onto  
 			// a component but no buttons have been pushed
 				
 		}// end mouseMoved 
 				
		public void mouseClicked( MouseEvent event ) 
		{
          	// Invoked when the mouse button has been clicked 
          	// (pressed and released) on a component. 
          
        }// end mouseClicked
 		
 		public void mouseEntered( MouseEvent event )
 		{ 
          	// Invoked when the mouse enters a component. 
          	
        }// end mouseEntered
        
 		public void mouseExited( MouseEvent event ) 
 		{
          	// Invoked when the mouse exits a component. 
          	
        }// end mouseExited
        
 		public void mousePressed( MouseEvent event ) 
 		{
          	// Invoked when a mouse button has been 
          	// pressed on a component. 
          	dragging = true;
        }// end mousePressed
        
 		public void mouseReleased( MouseEvent event )  
 		{
 			dragging = false;
 		}// end mouseReleased
	}
	
	
	private class AxisListener implements ActionListener
	// The AxisListener class listens for messages from the menu on whether
	// or not the axes should be displayed by the graphics engine.  The status 
	// of the check box menu item is discerned and the graphics engine is told 
	// whether or not to show the axes based on the status of the check box.
	{
		// METHODS
		//		
		public void actionPerformed( ActionEvent event )
		{
			// Handle menu events for the axis menu item.
			graphicsEngine.enableAxes( modelerMenu.getAxisEnable( ) );
		}// end actionPerformed
	}
	
	public class ControlsPanelListener implements ActionListener
	// The ControlsPanelListener class listens for messages from the menu on 
	// whether or not to display the controls panel modeless dialog box. The 
	// Dialog is shown whenever the user selects that menu item.
	{
		// METHODS
		//		
		public void actionPerformed( ActionEvent event )
		{
			controlsPanelDialog.show( true );
		}// end actionPerformed
	}
	
	private class CreateModelDialogListener implements ActionListener
	// The NewTemplateListener class listens for messages from the menu on 
	// when to display the NewTemplateDialog.  If a message is received then 
	// the NewTemplate dialog should be shown so the user can create a new
	// insect template.
	{
		// METHODS
		//		
		public void actionPerformed( ActionEvent event )
		{
			createModelDialog.show( true );
			
		}// end actionPerformed	
	}
	
	private class CreateModelButtonListener implements ActionListener
	// This class listens to the create model button on the create model 
	// dialog box.  The records created in that dialog are fetched and 
	// handed to the InsectModel class to create all of the body parts
	// of the insect.
	{
		// METHODS
		//		
		public void actionPerformed( ActionEvent event )
		{
			// Hide the dialog box.
			createModelDialog.show( false );
			
			// Get the values from the dialog box.
			int radialPoints = createModelDialog.getRadialPoints( );
			int columns = createModelDialog.getColumns( );
			
			// Create the basic insect model body and add it
			// to the graphics engine.
			insectModel = new InsectModel( radialPoints, columns );
			
			
			// Register the insect model from the images provided in the insect 
			// records in the create model dialog, and apply the textures for
			// each of the separate body parts being modeled.
			insectModel.createModel( createModelDialog.getInsectBodyRecordVector( ),
			                         createModelDialog.getInsectBodyTexturePath( ) );
			
			graphicsEngine.setInsectModel( insectModel );
			
			
			
			
			modelerMenu.setModelActiveButtonsEnable( true );
					
			
		}// end actionPerformed	
	}
	
	private class ExportToVRMLListener implements ActionListener 
	// The ExportToVRMLListener class is a listener for the menu item
	// which allows the user to export the current model to a VRML file.
	// The vtkRenderWindow cannot be exported unless the insect model has
	// been initialized.  The entire render window is exported to 
	// the file indicated by the Save File dialog.
	{
		// METHODS
		//		
		public void actionPerformed( ActionEvent event )
		{
			if( insectModel != null )
			{
				String savePath = ".";
				  
		        JFileChooser fileChooser = new JFileChooser( savePath );
		        fileChooser.setDialogTitle( "Export model to VRML" );
		        int result = fileChooser.showSaveDialog( null );
		
		        if( result == JFileChooser.APPROVE_OPTION )
		        {
		        	// Get the file path and populate the dialog box.
		        	File  file  = fileChooser.getSelectedFile();
		          	String path = file.getAbsolutePath();
		          	
					insectModel.exportModelToVRML( path, 
				                graphicsEngine.getRenderWindow( ) );
		        }// end if				

			}// end if
			
		}// end actionPerformed
	}
	
	
	private class CloseModelListener implements ActionListener
	// The CloseModelListener class listens for messages from the menu bar 
	// for when the application should destroy the current insect model.  If 
	// no insect model is active, then this class does nothing.  If an insect
	// model is active, that model is destroyed.
	{
		// METHODS
		//		
		public void actionPerformed( ActionEvent event )
		{
			// Seek user input on whether to destroy data.
			destroyModelDialog.show( true );
		}// end actionPerformed
	}
	
	private class DestroyModelOkListener implements ActionListener
	// The DestroyModelOkListener class listens for the messages coming from an 
	// "ok" button click from the destroy model data dialog box.  If the user 
	// has ok'ed the removal of the insect model data, then destroy that data 
	// and hide the dialog box.
	{
		// METHODS
		//		
		public void actionPerformed( ActionEvent event )
		{
			// Destroy the model data in the graphics engine.
			graphicsEngine.destroyInsectModel( );
			
			destroyModelDialog.show( false );
			
			modelerMenu.setModelActiveButtonsEnable( false );
			
			insectModel = null;
		}// end actionPerformed
	}
	
	private class ResetEyeListener implements ActionListener
	// The ResetEyeListener class listens for messages coming from the reset 
	// button on the camera controls panel.  If the eye position reset button 
	// is pressed, then the eye location for the default camera needs to be 
	// repositioned to the default location.
	{
		// METHODS
		//
		public void actionPerformed( ActionEvent event )
		{
			controlsPanelDialog.setCameraEyeLocation( eye );
		}// end actionPerformed	
		
		// ATTRIBUTES
		//
		Point3f eye = new Point3f( 0.0f, 0.0f, 20.0f );		
	}	
		
	private class ResetFocusListener implements ActionListener
	// The ResetFocusListener class listens for messages coming from the reset
	// button on the camera controls panel for the focus position of the camera.
	// If the reset button is pressed then the focal point of the camera is set
	// to its default location.
	{
		// METHODS
		//				
		public void actionPerformed( ActionEvent event )
		{
			controlsPanelDialog.setCameraFocusLocation( focus );
		}// end actionPerformed	
		
		// ATTRIBUTES
		//
		Point3f focus = new Point3f( 0.0f, 0.0f, 0.0f );		
	}
	
	private class ResetUpListener implements ActionListener
	// The ResetUpListener class listens for messages coming from the reset 
	// button for the up vector controls on the camera controls panel.  If a 
	// reset command is sent, then the up vector should be reset to its default
	// setting.
	{
		// METHODS
		//		
		public void actionPerformed( ActionEvent event )
		{
			controlsPanelDialog.setCameraUpVector( up );	
		}// end actionPerformed	
		
		// ATTRIBUTES
		//
		Vector3f up = new Vector3f( 0.0f, 1.0f, 0.0f );		
	}
	
	private class PointModeListener implements ActionListener
	{
		// METHODS
		//		
		public void actionPerformed( ActionEvent event )
		{
			modelerMenu.setPointModeRadioButton( true );
			modelerMenu.setWireframeModeRadioButton( false );
			modelerMenu.setPolygonModeRadioButton( false );
			
			if( insectModel != null )
			{
				insectModel.setRenderMode( InsectModel.POINT );
			}// end if
			
		}// end actionPerformed	
	}
	
	private class WireframeModeListener implements ActionListener
	{
		public void actionPerformed( ActionEvent event )
		{
			modelerMenu.setPointModeRadioButton( false );
			modelerMenu.setWireframeModeRadioButton( true );
			modelerMenu.setPolygonModeRadioButton( false );	
			
			if( insectModel != null )
			{
				insectModel.setRenderMode( InsectModel.WIREFRAME );
			}// end if
					
		}// end actionPerformed	
	}
	
	private class PolygonModeListener implements ActionListener
	{
		// METHODS
		//		
		public void actionPerformed( ActionEvent event )
		{
			modelerMenu.setPointModeRadioButton( false );
			modelerMenu.setWireframeModeRadioButton( false );
			modelerMenu.setPolygonModeRadioButton( true );	
			
			if( insectModel != null )
			{
				insectModel.setRenderMode( InsectModel.POLYGON );
			}// end if
				
		}// end actionPerformed	
	}
	
	public class DisplayBodyListener implements ActionListener
	{
		// METHODS
		//
		public void actionPerformed( ActionEvent event )
		{
			insectModel.displayBody( modelerMenu.getDisplayBody( ) );
		}
	}
	
	public class DisplayRFLegListener implements ActionListener
	{
		// METHODS
		//
		public void actionPerformed( ActionEvent event )
		{
			insectModel.displayRightFrontLeg( modelerMenu.getDisplayRFLeg( ) );
		}// end actionPerformed	
	}
	
	public class DisplayRMLegListener implements ActionListener
	{
		// METHODS
		//
		public void actionPerformed( ActionEvent event )
		{
			insectModel.displayRightMiddleLeg( modelerMenu.getDisplayRMLeg( ) );
		}// end actionPerformed	
	}
	
	public class DisplayRBLegListener implements ActionListener
	{
		// METHODS
		//
		public void actionPerformed( ActionEvent event )
		{
			insectModel.displayRightBackLeg( modelerMenu.getDisplayRBLeg( ) );
		}// end actionPerformed	
	}

	public class DisplayLFLegListener implements ActionListener
	{
		// METHODS
		//
		public void actionPerformed( ActionEvent event )
		{
			insectModel.displayLeftFrontLeg( modelerMenu.getDisplayLFLeg( ) );
		}// end actionPerformed	
	}
	
	public class DisplayLMLegListener implements ActionListener
	{
		// METHODS
		//
		public void actionPerformed( ActionEvent event )
		{
			insectModel.displayLeftMiddleLeg( modelerMenu.getDisplayLMLeg( ) );
		}// end actionPerformed	
	}
	
	public class DisplayLBLegListener implements ActionListener
	{
		// METHODS
		//
		public void actionPerformed( ActionEvent event )
		{
			insectModel.displayLeftBackLeg( modelerMenu.getDisplayLBLeg( ) );
		}// end actionPerformed	
	}
												
	private class ExitListener implements ActionListener
	// The ExitListener class listens to messages from the menu bar that notify 
	// the application that it is time to exit.  The default exit action is 
	// applied to the application.
	{
		public void actionPerformed( ActionEvent event )
		{
			// Exit program.
			System.exit(0);
			
		}// end actionPerformed
	}
	
	public static void main( String args[] ) 
	{
		JPopupMenu.setDefaultLightWeightPopupEnabled( false );
		
		System.err.println("Starting Insect Modeler Program...");
		
		InsectModeler insectModelerFrame = new InsectModeler( );
		insectModelerFrame.setSize( 600, 600 );
		insectModelerFrame.setTitle( "Insect Modeler" );
		insectModelerFrame.setVisible( true ); 
		insectModelerFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ); 	
	}// end main
	
	
	// ATTRIBUTES
	//
	private GraphicsEngine      graphicsEngine;
	private ModelerMenu         modelerMenu;
	private DestroyModelDialog  destroyModelDialog;
	private ControlsPanelDialog controlsPanelDialog;
	private CreateModelDialog   createModelDialog;
	private InsectModel         insectModel;
	private boolean             dragging;	
}

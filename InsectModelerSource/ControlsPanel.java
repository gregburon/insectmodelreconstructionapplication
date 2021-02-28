

////////////////////////////////////////////////////////////////////////
//
//  InsectModeler Program
//
//  Created by Greg Buron
//  Spring 2003
//  Western Washington University
//
//
//  *.java
//  
//  The <code></code> class is responsible for ...
//
////////////////////////////////////////////////////////////////////////

package myprojects.insectmodeler;



import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;


public class ControlsPanel extends JPanel 
{


	// CONSTRUCTOR
	//
	public ControlsPanel( )
	{
		gridBagLayout      = new GridBagLayout( );
		gridBagConstraints = new GridBagConstraints( );
		
		this.setLayout( gridBagLayout );
		
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		
		cameraPanel = new CameraPanel( " Camera Position controls: " );
		
		gridBagConstraints.weightx    = 1.0;
		gridBagConstraints.gridx      = 0;
		gridBagConstraints.gridy      = 0;
		gridBagConstraints.ipadx      = 10;
		gridBagConstraints.ipady      = 10;
		gridBagConstraints.anchor     = GridBagConstraints.NORTH;
		gridBagLayout.setConstraints( cameraPanel, gridBagConstraints );
		this.add( cameraPanel );		
	}
	
	// METHODS
	//
	public CameraPanel getCameraPanel( )
	{
		return cameraPanel;
	}
	
	public void addCameraPositionListener( ChangeListener listener )
	{
		cameraPanel.addCameraPositionListener( listener );
	}
	
	public void addResetEyeListener( ActionListener listener )
	{
		cameraPanel.addResetEyeListener( listener );
	}
	
	public void addResetFocusListener( ActionListener listener )
	{
		cameraPanel.addResetFocusListener( listener );
	}
	
	public void addResetUpListener( ActionListener listener )
	{
		cameraPanel.addResetUpListener( listener );
	}	
	
	public Point3f getCameraEyeLocation( )
	{
		return cameraPanel.getEyeLocation( );	
	}
	
	public Point3f getCameraFocusLocation( )
	{
		return cameraPanel.getFocusLocation( );
	}
	
	public Vector3f getCameraUpVector( )
	{
		return cameraPanel.getUpVector( );
	}
	
	public void setCameraEyeLocation( Point3f eye )
	{
		cameraPanel.setEyeLocation( eye );
	}
	
	public void setCameraFocusLocation( Point3f focus )
	{
		cameraPanel.setFocusLocation( focus );	
	}
	
	public void setCameraUpVector( Vector3f up )
	{
		cameraPanel.setUpVector( up );
	}

	// ATTRIBUTES
	//
	private CameraPanel        cameraPanel;
	private GridBagLayout      gridBagLayout;
	private GridBagConstraints gridBagConstraints;
}


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

import javax.swing.JDialog;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

public class ControlsPanelDialog extends JDialog
{


	// CONSTRUCTOR
	//	
	public ControlsPanelDialog( )
	{
		setTitle( "Controls Panel" );
		setSize( 280, 195 );
		setResizable( false );
		setLocation( 600, 0 );
		setModal( true );
		show( false );
		
		controlsPanel = new ControlsPanel( );	
		
		this.setModal( false );

		gridBagLayout      = new GridBagLayout( );
		gridBagConstraints = new GridBagConstraints( );
		this.getContentPane( ).setLayout( gridBagLayout);
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.ipadx     = 10;
		gridBagConstraints.ipady     = 10;
		gridBagConstraints.weightx   = 0.5;
		gridBagConstraints.gridx     = 0;
		gridBagConstraints.gridy     = 0;
		gridBagLayout.setConstraints( controlsPanel, gridBagConstraints );
		this.getContentPane( ).add( controlsPanel );				
	}
	
	
	// METHODS
	//
	public void addCameraPositionListener( ChangeListener listener )
	{
		controlsPanel.getCameraPanel( ).addCameraPositionListener( listener );
	}
	
	public void addResetEyeListener( ActionListener listener )
	{
		controlsPanel.getCameraPanel( ).addResetEyeListener( listener );
	}
	
	public void addResetFocusListener( ActionListener listener )
	{
		controlsPanel.getCameraPanel( ).addResetFocusListener( listener );
	}
	
	public void addResetUpListener( ActionListener listener )
	{
		controlsPanel.getCameraPanel( ).addResetUpListener( listener );
	}	
	
	public Point3f getCameraEyeLocation( )
	{
		return controlsPanel.getCameraPanel( ).getEyeLocation( );	
	}
	
	public Point3f getCameraFocusLocation( )
	{
		return controlsPanel.getCameraPanel( ).getFocusLocation( );
	}
	
	public Vector3f getCameraUpVector( )
	{
		return controlsPanel.getCameraPanel( ).getUpVector( );
	}
	
	public void setCameraEyeLocation( Point3f eye )
	{
		controlsPanel.getCameraPanel( ).setEyeLocation( eye );
	}
	
	public void setCameraFocusLocation( Point3f focus )
	{
		controlsPanel.getCameraPanel( ).setFocusLocation( focus );	
	}
	
	public void setCameraUpVector( Vector3f up )
	{
		controlsPanel.getCameraPanel( ).setUpVector( up );
	}
	
	// ATTRIBUTES
	//
	private ControlsPanel      controlsPanel;
	private GridBagLayout      gridBagLayout;
	private GridBagConstraints gridBagConstraints;	
}
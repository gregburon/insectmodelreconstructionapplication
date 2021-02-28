

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

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;



public class CameraPanel extends JPanel
{
	// CONSTRUCTOR
	//
	public CameraPanel( String title )
	{
		Dimension dim       = new Dimension( 35, 15 );
		Dimension labelDim  = new Dimension( 45, 15 );
		Dimension longDim   = new Dimension( 150, 15 );
		Dimension buttonDim = new Dimension( 30, 15 );
		
		// Initialize the gridbag.
		gridBagLayout      = new GridBagLayout( );
		gridBagConstraints = new GridBagConstraints( );
		
		this.setLayout( gridBagLayout);
		
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		
		
		// Initialize the models for each spinner.	
		SpinnerNumberModel eyeXModel = 
		   new SpinnerNumberModel( 0.0, -1000.0, 1000.0, 0.1 );
		SpinnerNumberModel eyeYModel = 
		   new SpinnerNumberModel( 0.0, -1000.0, 1000.0, 0.1 );
		SpinnerNumberModel eyeZModel = 
		   new SpinnerNumberModel( 0.0, -1000.0, 1000.0, 0.1 );	
		   
		SpinnerNumberModel focusXModel = 
		   new SpinnerNumberModel( 0.0, -1000.0, 1000.0, 0.1 );
		SpinnerNumberModel focusYModel = 
		   new SpinnerNumberModel( 0.0, -1000.0, 1000.0, 0.1 );
		SpinnerNumberModel focusZModel = 
		   new SpinnerNumberModel( 0.0, -1000.0, 1000.0, 0.1 );	
		   
		SpinnerNumberModel upXModel = 
		   new SpinnerNumberModel( 0.0, -1.0, 1.0, 0.1 );
		SpinnerNumberModel upYModel = 
		   new SpinnerNumberModel( 1.0, -1.0, 1.0, 0.1 );
		SpinnerNumberModel upZModel = 
		   new SpinnerNumberModel( 0.0, -1.0, 1.0, 0.1 );			   		   	   		   
		 	          
		           		           	
		// Initialize the labels.
		cameraLabel   = new JLabel( title );
		eyeLabel      = new JLabel( " Eye: " );
		focusLabel    = new JLabel( " Focus: " );
		upLabel       = new JLabel( " Up: " );
		xLabel        = new JLabel( " x" );
		yLabel        = new JLabel( " y" );
		zLabel        = new JLabel( " z" );
		
		
		// Initialize the spinners.  
		eyeXSpinner   = new JSpinner( eyeXModel );
		eyeYSpinner   = new JSpinner( eyeYModel );
		eyeZSpinner   = new JSpinner( eyeZModel );
		
		focusXSpinner = new JSpinner( focusXModel );
		focusYSpinner = new JSpinner( focusYModel );
		focusZSpinner = new JSpinner( focusZModel );
		
		upXSpinner    = new JSpinner( upXModel );
		upYSpinner    = new JSpinner( upYModel );
		upZSpinner    = new JSpinner( upZModel );
		
		// Initialize the buttons.
		resetEyeButton   = new JButton( "R" );
		resetFocusButton = new JButton( "R" );
		resetUpButton    = new JButton( "R" );								
						
		
		// Set maximum size of spinners.
		eyeXSpinner.setMaximumSize( dim );
		eyeYSpinner.setMaximumSize( dim );
		eyeZSpinner.setMaximumSize( dim );
		
		focusXSpinner.setMaximumSize( dim );
		focusYSpinner.setMaximumSize( dim );
		focusZSpinner.setMaximumSize( dim );
		
		upXSpinner.setMaximumSize( dim );
		upYSpinner.setMaximumSize( dim );
		upZSpinner.setMaximumSize( dim );				
	
	
		// Set preferred size of spinners.
		eyeXSpinner.setPreferredSize( dim );
		eyeYSpinner.setPreferredSize( dim );
		eyeZSpinner.setPreferredSize( dim );
		
		focusXSpinner.setPreferredSize( dim );
		focusYSpinner.setPreferredSize( dim );
		focusZSpinner.setPreferredSize( dim );
		
		upXSpinner.setPreferredSize( dim );
		upYSpinner.setPreferredSize( dim );
		upZSpinner.setPreferredSize( dim );			
		
		
		// Set the preferred size of the labels.
		cameraLabel.setPreferredSize( longDim );
		eyeLabel.setPreferredSize( labelDim );
		focusLabel.setPreferredSize( labelDim );
		upLabel.setPreferredSize( labelDim );
		xLabel.setPreferredSize( labelDim );
		yLabel.setPreferredSize( labelDim );
		zLabel.setPreferredSize( labelDim );

		
		// Set preferred size of buttons.
		resetEyeButton.setPreferredSize( buttonDim );
		resetFocusButton.setPreferredSize( buttonDim );
		resetUpButton.setPreferredSize( buttonDim );
		
		// Add the components to the grid bag.
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.ipadx     = 10;
		gridBagConstraints.ipady     = 10;
		gridBagConstraints.weightx   = 0.5;
		gridBagConstraints.gridx     = 0;
		gridBagConstraints.gridy     = 0;
		gridBagLayout.setConstraints( cameraLabel, gridBagConstraints );
		this.add( cameraLabel );

		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.ipadx     = 10;
		gridBagConstraints.ipady     = 10;
		gridBagConstraints.weightx   = 0.5;
		gridBagConstraints.gridx     = 1;
		gridBagConstraints.gridy     = 1;
		gridBagLayout.setConstraints( xLabel, gridBagConstraints );
		this.add( xLabel );
		
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.ipadx     = 10;
		gridBagConstraints.ipady     = 10;
		gridBagConstraints.weightx   = 0.5;
		gridBagConstraints.gridx     = 2;
		gridBagConstraints.gridy     = 1;
		gridBagLayout.setConstraints( yLabel, gridBagConstraints );
		this.add( yLabel );
		
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.ipadx     = 10;
		gridBagConstraints.ipady     = 10;
		gridBagConstraints.weightx   = 0.5;
		gridBagConstraints.gridx     = 3;
		gridBagConstraints.gridy     = 1;
		gridBagLayout.setConstraints( zLabel, gridBagConstraints );
		this.add( zLabel );	
						
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.ipadx     = 10;
		gridBagConstraints.ipady     = 10;
		gridBagConstraints.weightx   = 0.5;
		gridBagConstraints.gridx     = 0;
		gridBagConstraints.gridy     = 2;
		gridBagLayout.setConstraints( eyeLabel, gridBagConstraints );
		this.add( eyeLabel );
		
		gridBagConstraints.gridwidth = 1;				
		gridBagConstraints.ipadx     = 10;
		gridBagConstraints.ipady     = 10;
		gridBagConstraints.weightx   = 0.5;
		gridBagConstraints.gridx     = 1;
		gridBagConstraints.gridy     = 2;
		gridBagLayout.setConstraints( eyeXSpinner, gridBagConstraints );
		this.add( eyeXSpinner );	
		
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.ipadx     = 10;
		gridBagConstraints.ipady     = 10;
		gridBagConstraints.weightx   = 0.5;
		gridBagConstraints.gridx     = 2;
		gridBagConstraints.gridy     = 2;
		gridBagLayout.setConstraints( eyeYSpinner, gridBagConstraints );
		this.add( eyeYSpinner );
		
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.ipadx     = 10;
		gridBagConstraints.ipady     = 10;
		gridBagConstraints.weightx   = 0.5;
		gridBagConstraints.gridx     = 3;
		gridBagConstraints.gridy     = 2;
		gridBagLayout.setConstraints( eyeZSpinner, gridBagConstraints );
		this.add( eyeZSpinner );
		
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.ipadx     = 10;
		gridBagConstraints.ipady     = 10;
		gridBagConstraints.weightx   = 0.5;
		gridBagConstraints.gridx     = 4;
		gridBagConstraints.gridy     = 2;
		gridBagLayout.setConstraints( resetEyeButton, gridBagConstraints );
		this.add( resetEyeButton );
			
		
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.ipadx     = 10;
		gridBagConstraints.ipady     = 10;
		gridBagConstraints.weightx   = 0.5;
		gridBagConstraints.gridx     = 0;
		gridBagConstraints.gridy     = 3;
		gridBagLayout.setConstraints( focusLabel, gridBagConstraints );
		this.add( focusLabel );
		
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.ipadx     = 10;
		gridBagConstraints.ipady     = 10;
		gridBagConstraints.weightx   = 0.5;
		gridBagConstraints.gridx     = 1;
		gridBagConstraints.gridy     = 3;
		gridBagLayout.setConstraints( focusXSpinner, gridBagConstraints );
		this.add( focusXSpinner );	
		
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.ipadx     = 10;
		gridBagConstraints.ipady     = 10;
		gridBagConstraints.weightx   = 0.5;
		gridBagConstraints.gridx     = 2;
		gridBagConstraints.gridy     = 3;
		gridBagLayout.setConstraints( focusYSpinner, gridBagConstraints );
		this.add( focusYSpinner );	
		
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.ipadx     = 10;
		gridBagConstraints.ipady     = 10;
		gridBagConstraints.weightx   = 0.5;
		gridBagConstraints.gridx     = 3;
		gridBagConstraints.gridy     = 3;
		gridBagLayout.setConstraints( focusZSpinner, gridBagConstraints );
		this.add( focusZSpinner );
		
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.ipadx     = 10;
		gridBagConstraints.ipady     = 10;
		gridBagConstraints.weightx   = 0.5;
		gridBagConstraints.gridx     = 4;
		gridBagConstraints.gridy     = 3;
		gridBagLayout.setConstraints( resetFocusButton, gridBagConstraints );
		this.add( resetFocusButton );
				
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.ipadx     = 10;
		gridBagConstraints.ipady     = 10;
		gridBagConstraints.weightx   = 0.5;
		gridBagConstraints.gridx     = 0;
		gridBagConstraints.gridy     = 4;
		gridBagLayout.setConstraints( upLabel, gridBagConstraints );
		this.add( upLabel );
		
		gridBagConstraints.gridwidth = 1;		
		gridBagConstraints.ipadx     = 10;
		gridBagConstraints.ipady     = 10;
		gridBagConstraints.weightx   = 0.5;
		gridBagConstraints.gridx     = 1;
		gridBagConstraints.gridy     = 4;
		gridBagLayout.setConstraints( upXSpinner, gridBagConstraints );
		this.add( upXSpinner );	
		
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.ipadx     = 10;
		gridBagConstraints.ipady     = 10;
		gridBagConstraints.weightx   = 0.5;
		gridBagConstraints.gridx     = 2;
		gridBagConstraints.gridy     = 4;
		gridBagLayout.setConstraints( upYSpinner, gridBagConstraints );
		this.add( upYSpinner );
		
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.ipadx     = 10;
		gridBagConstraints.ipady     = 10;
		gridBagConstraints.weightx   = 0.5;
		gridBagConstraints.gridx     = 3;
		gridBagConstraints.gridy     = 4;
		gridBagLayout.setConstraints( upZSpinner, gridBagConstraints );
		this.add( upZSpinner );	

		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.ipadx     = 10;
		gridBagConstraints.ipady     = 10;
		gridBagConstraints.weightx   = 0.5;
		gridBagConstraints.gridx     = 4;
		gridBagConstraints.gridy     = 4;
		gridBagLayout.setConstraints( resetUpButton, gridBagConstraints );
		this.add( resetUpButton );				
				
	}// end constructor
	
	
	// METHODS
	//
	public Point3f getEyeLocation( )
	{
		Point3f eye = new Point3f( );
		
		eye.x = (float)((Double)(eyeXSpinner.getValue( ))).doubleValue( );
		eye.y = (float)((Double)(eyeYSpinner.getValue( ))).doubleValue( );
		eye.z = (float)((Double)(eyeZSpinner.getValue( ))).doubleValue( );
		
		return eye;
	}
	
	public Point3f getFocusLocation( )
	{
		Point3f focus = new Point3f( );
		
		focus.x = (float)((Double)(focusXSpinner.getValue( ))).doubleValue( );
		focus.y = (float)((Double)(focusYSpinner.getValue( ))).doubleValue( );
		focus.z = (float)((Double)(focusZSpinner.getValue( ))).doubleValue( );
		
		return focus;		
	}
	
	public Vector3f getUpVector( )
	{
		Vector3f up = new Vector3f( );
		
		up.x = (float)((Double)(upXSpinner.getValue( ))).doubleValue( );
		up.y = (float)((Double)(upYSpinner.getValue( ))).doubleValue( );
		up.z = (float)((Double)(upZSpinner.getValue( ))).doubleValue( );
		
		up.normalize( );
			
		return up;		
	}
	
	public void setEyeLocation( Point3f eye )
	{
		Double x = new Double( (double)eye.x );
		Double y = new Double( (double)eye.y );
		Double z = new Double( (double)eye.z );
		
		eyeXSpinner.setValue( x );
		eyeYSpinner.setValue( y );
		eyeZSpinner.setValue( z );	
	}
	
	public void setFocusLocation( Point3f focus )
	{
		Double x = new Double( (double)focus.x );
		Double y = new Double( (double)focus.y );
		Double z = new Double( (double)focus.z );
		
		focusXSpinner.setValue( x );
		focusYSpinner.setValue( y );
		focusZSpinner.setValue( z );
	}
	
	public void setUpVector( Vector3f up )
	{
		Double x = new Double( (double)up.x );
		Double y = new Double( (double)up.y );
		Double z = new Double( (double)up.z );
		
		upXSpinner.setValue( x );
		upYSpinner.setValue( y );
		upZSpinner.setValue( z );		
	}
	
	private float getSpinnerCurrentValue( JSpinner spinner )
	{
		return (((Float)spinner.getValue( )).floatValue( ));
	}
		
	private float getSpinnerMinValue( JSpinner spinner )
	{
		return (((Float)((SpinnerNumberModel)spinner.getModel( )).getMinimum( )).floatValue( ));
	}
	
	private float getSpinnerMaxValue( JSpinner spinner )
	{
		return (((Float)((SpinnerNumberModel)spinner.getModel( )).getMinimum( )).floatValue( ));
	}
	
	public void addCameraPositionListener( ChangeListener listener )
	{
		eyeXSpinner.addChangeListener( listener );	
		eyeYSpinner.addChangeListener( listener );	
		eyeZSpinner.addChangeListener( listener );
		
		focusXSpinner.addChangeListener( listener );
		focusYSpinner.addChangeListener( listener );
		focusZSpinner.addChangeListener( listener );
		
		upXSpinner.addChangeListener( listener );
		upYSpinner.addChangeListener( listener );
		upZSpinner.addChangeListener( listener );
	}
	
	public void addResetEyeListener( ActionListener listener )
	{
		resetEyeButton.addActionListener( listener );
	}
	
	public void addResetFocusListener( ActionListener listener )
	{
		resetFocusButton.addActionListener( listener );
	}
	
	public void addResetUpListener( ActionListener listener )
	{
		resetUpButton.addActionListener( listener );
	}
	
	
	// ATTRIBUTES
	//
	private JLabel   cameraLabel;
	private JLabel   eyeLabel;
	private JLabel   focusLabel;
	private JLabel   upLabel;
	private JLabel   xLabel;
	private JLabel   yLabel;
	private JLabel   zLabel;
	private JLabel   orbitLabel;
	private JLabel   panLabel;
	private JLabel   tiltLabel;
	private JLabel   rollLabel;
	private JLabel   distanceLabel;
	
	private JSpinner eyeXSpinner;
	private JSpinner eyeYSpinner;
	private JSpinner eyeZSpinner;
	private JSpinner focusXSpinner;
	private JSpinner focusYSpinner;
	private JSpinner focusZSpinner;
	private JSpinner upXSpinner;
	private JSpinner upYSpinner;
	private JSpinner upZSpinner;
	
	private JButton  resetEyeButton;
	private JButton  resetFocusButton;
	private JButton  resetUpButton;
	
	private GridBagLayout      gridBagLayout;
	private GridBagConstraints gridBagConstraints;	
	
}




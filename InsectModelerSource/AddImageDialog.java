

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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTextField;

import javax.swing.border.BevelBorder;


public class AddImageDialog extends JDialog
{
	// CONSTRUCTOR
	//
	public AddImageDialog( )
	{
		setSize( 500, 175 );
		setTitle( "Add Image" );
		setModal( true );
		setLocation( 250, 250 );
		setResizable( false );
		
		// Add all components to the master panel.
		JPanel masterPanel = new JPanel( );
		masterPanel.setLayout( new BoxLayout( masterPanel, BoxLayout.Y_AXIS ) );

		
		// Create the panel that houses the file controls.
		JLabel pathLabel = new JLabel( " Path: " );
		pathLabel.setPreferredSize( new Dimension( 50, 25 )  );
		pathLabel.setMinimumSize( new Dimension( 50, 25 )  );
		pathLabel.setMaximumSize( new Dimension( 50, 25 )  );
			
		imageFileName = new JTextField( "File name" );
		imageFileName.setPreferredSize( new Dimension( 300, 25 ) );
		imageFileName.setMinimumSize( new Dimension( 300, 25 ) );
		imageFileName.setMaximumSize( new Dimension( 300, 25 ) );
		imageFileName.setBorder( BorderFactory.createBevelBorder( BevelBorder.LOWERED ) );
		
		int buttonWidth = 100;
		int buttonHeight = 25;
		
		browseButton = new JButton( "Browse..." );
		browseButton.setPreferredSize( new Dimension( buttonWidth, buttonHeight )  );
		browseButton.setMinimumSize( new Dimension( buttonWidth, buttonHeight )  );
		browseButton.setMaximumSize( new Dimension( buttonWidth, buttonHeight )  );
		browseButton.setBorder( BorderFactory.createBevelBorder( BevelBorder.RAISED ) );
		
		JLabel spacerLabel = new JLabel( "   " );
		spacerLabel.setPreferredSize( new Dimension( 25, 25 )  );
		spacerLabel.setMinimumSize( new Dimension( 25, 25 )  );
		spacerLabel.setMaximumSize( new Dimension( 25, 25 )  );
			
		JPanel filePanel = new JPanel( );
		filePanel.setLayout( new BoxLayout( filePanel, BoxLayout.X_AXIS ) );
		filePanel.add( pathLabel );
		filePanel.add( imageFileName );
		filePanel.add( spacerLabel );
		filePanel.add( browseButton );
		filePanel.setPreferredSize( new Dimension( 500, 25 )  );
		filePanel.setMinimumSize( new Dimension( 500, 25 )  );
		filePanel.setMaximumSize( new Dimension( 500, 25 )  );	
		
		
		// Create the panel that houses the rotational controls.
		JLabel rotationLabel = new JLabel( " Rotational Angle: " );
		rotationLabel.setPreferredSize( new Dimension( 175, 25 )  );
		rotationLabel.setMinimumSize( new Dimension( 175, 25 )  );
		rotationLabel.setMaximumSize( new Dimension( 175, 25 )  );	
		
		SpinnerNumberModel model = new SpinnerNumberModel( 0.0, 0.0, 360.0, 1.0 );
		rotationSpinner = new JSpinner( model );
		rotationSpinner.setPreferredSize( new Dimension( 75, 25 )  );
		rotationSpinner.setMinimumSize( new Dimension( 75, 25 )  );
		rotationSpinner.setMaximumSize( new Dimension( 75, 25 )  );
		rotationSpinner.setBorder( BorderFactory.createBevelBorder( BevelBorder.LOWERED ) );	
		
		JLabel degreesLabel = new JLabel( " degrees" );
		degreesLabel.setPreferredSize( new Dimension( 75, 25 )  );
		degreesLabel.setMinimumSize( new Dimension( 75, 25 )  );
		degreesLabel.setMaximumSize( new Dimension( 75, 25 )  );
			
		JPanel rotationPanel = new JPanel( );
		rotationPanel.setLayout( new BoxLayout( rotationPanel, BoxLayout.X_AXIS ) );
		rotationPanel.add( rotationLabel );
		rotationPanel.add( rotationSpinner );
		rotationPanel.add( degreesLabel );
		rotationPanel.setPreferredSize( new Dimension( 500, 25 )  );
		rotationPanel.setMinimumSize( new Dimension( 500, 25 )  );
		rotationPanel.setMaximumSize( new Dimension( 500, 25 )  );	
		
		
		
		// Create the panel that controls the dialog (ok, cancel).
		JPanel buttonsPanel = new JPanel( );
		buttonsPanel.setLayout( new BoxLayout( buttonsPanel, BoxLayout.X_AXIS ) );
		
		okButton = new JButton( "Add" );
		okButton.setPreferredSize( new Dimension( 75, 25 )  );
		okButton.setMinimumSize( new Dimension( 75, 25 )  );
		okButton.setMaximumSize( new Dimension( 75, 25 )  );
		okButton.setBorder( BorderFactory.createBevelBorder( BevelBorder.RAISED ) );
		
		cancelButton = new JButton( "Cancel" );
		cancelButton.setPreferredSize( new Dimension( 75, 25 )  );
		cancelButton.setMinimumSize( new Dimension( 75, 25 )  );
		cancelButton.setMaximumSize( new Dimension( 75, 25 )  );
		cancelButton.setBorder( BorderFactory.createBevelBorder( BevelBorder.RAISED ) );
		
		buttonsPanel.add( okButton );
		buttonsPanel.add( cancelButton );
		buttonsPanel.setPreferredSize( new Dimension( 500, 25 )  );
		buttonsPanel.setMinimumSize( new Dimension( 500, 25 )  );
		buttonsPanel.setMaximumSize( new Dimension( 500, 25 )  );		
		
		// Add everything to the master panel.
		masterPanel.add( filePanel );
		masterPanel.add( new JLabel( " " ) );
		masterPanel.add( rotationPanel );
		masterPanel.add( new JLabel( " " ) );
		masterPanel.add( buttonsPanel );
		
		
		// Add the master panel to the dialog box.
		this.getContentPane( ).add( masterPanel );	

	}// end constructor	
	
	
	
	public String getPath( )
	// Return the path to the image in this dialog.
	{
		return imageFileName.getText( );	
	}
	
	public float getAngle( )
	// Return the viewing angle that this image represents.
	{
		return (float)((Double)(rotationSpinner.getValue( ))).doubleValue( );
	}
	
	
	public void setImageFileTextBox( String path )
	// Set the text in the image path text box.
	{
		imageFileName.setText( path );
	}
			
				
	public void addOkButtonListener( ActionListener listener )
	{
		okButton.addActionListener( listener );
	}
	
	public void addCancelButtonListener( ActionListener listener )
	{
		cancelButton.addActionListener( listener );
	}
	
	public void addBrowseButtonListener( ActionListener listener )
	{
		browseButton.addActionListener( listener );
	}	
	
	// ATTRIBUTES
	//	
	JButton okButton;
	JButton cancelButton;
	JButton browseButton;
	JTextField imageFileName;
	JSpinner rotationSpinner;	
}
	
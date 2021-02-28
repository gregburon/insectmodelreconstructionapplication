

////////////////////////////////////////////////////////////////////////
//
//  InsectModeler Program
//
//  Created by Greg Buron
//  Spring 2003
//  Western Washington University
//
//
//  TextureDialog.java
//  
//  The <code>TextureDialog</code> class is responsible for determining
//  which TIFF image(s) are to be used for texturing the insect model.
//
////////////////////////////////////////////////////////////////////////

package myprojects.insectmodeler;


import java.awt.BorderLayout;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTextField;
import javax.swing.JFileChooser;


import javax.swing.border.BevelBorder;


public class TextureDialog extends JDialog
{
	// CONSTRUCTOR
	//
	
	public TextureDialog( )
	{
		setSize( 540, 175 );
		setTitle( "Insect Model Textures" );
		setModal( true );
		setLocation( 250, 250 );
		setResizable( false );

		// The master panel holds all of the JPanels.
		JPanel masterPanel = new JPanel( );
		masterPanel.setLayout( 
			new BoxLayout( masterPanel, BoxLayout.Y_AXIS ) );

		JPanel bodyTexturePanel = new JPanel( );
		bodyTexturePanel.setLayout( new BoxLayout( bodyTexturePanel, BoxLayout.X_AXIS ) );
							
		JLabel bodyTextureLabel = new JLabel( " Body Texture: " );
		bodyTextureLabel.setPreferredSize( new Dimension( 100, 25 )  );
		bodyTextureLabel.setMinimumSize( new Dimension( 100, 25 )  );
		bodyTextureLabel.setMaximumSize( new Dimension( 100, 25 )  );

		bodyImageFileName = new JTextField( "File name" );
		bodyImageFileName.setPreferredSize( new Dimension( 300, 25 ) );
		bodyImageFileName.setMinimumSize( new Dimension( 300, 25 ) );
		bodyImageFileName.setMaximumSize( new Dimension( 300, 25 ) );
		bodyImageFileName.setBorder( BorderFactory.createBevelBorder( BevelBorder.LOWERED ) );

		int buttonWidth = 100;
		int buttonHeight = 25;
		
		bodyBrowseButton = new JButton( "Browse..." );
		bodyBrowseButton.setPreferredSize( new Dimension( buttonWidth, buttonHeight )  );
		bodyBrowseButton.setMinimumSize( new Dimension( buttonWidth, buttonHeight )  );
		bodyBrowseButton.setMaximumSize( new Dimension( buttonWidth, buttonHeight )  );
		bodyBrowseButton.setBorder( BorderFactory.createBevelBorder( BevelBorder.RAISED ) );
		
		
		// Add the components to select the body texture.
		bodyTexturePanel.add( bodyTextureLabel );
		bodyTexturePanel.add( bodyImageFileName );
		bodyTexturePanel.add( bodyBrowseButton );
		
		
		JPanel okCancelButtonPanel = new JPanel( );
		okCancelButtonPanel.setLayout( new BoxLayout( okCancelButtonPanel, BoxLayout.X_AXIS ) );
				
		JButton okButton = new JButton( "OK" );
		okButton.setPreferredSize( new Dimension( buttonWidth, buttonHeight )  );
		okButton.setMinimumSize( new Dimension( buttonWidth, buttonHeight )  );
		okButton.setMaximumSize( new Dimension( buttonWidth, buttonHeight )  );
		okButton.setBorder( BorderFactory.createBevelBorder( BevelBorder.RAISED ) );
		
		JButton cancelButton = new JButton( "Cancel" );
		cancelButton.setPreferredSize( new Dimension( buttonWidth, buttonHeight )  );
		cancelButton.setMinimumSize( new Dimension( buttonWidth, buttonHeight )  );
		cancelButton.setMaximumSize( new Dimension( buttonWidth, buttonHeight )  );
		cancelButton.setBorder( BorderFactory.createBevelBorder( BevelBorder.RAISED ) );		
				
		
		okCancelButtonPanel.add( okButton );
		okCancelButtonPanel.add( new JLabel( "          " ) );
		okCancelButtonPanel.add( cancelButton );
		
		// Add all of the components to the master panel.
		masterPanel.add( new JLabel( " " ) );
		masterPanel.add( bodyTexturePanel );
		masterPanel.add( new JLabel( " " ) );
		masterPanel.add( okCancelButtonPanel );
		
		// Add the master panel to the dialog box.
		this.getContentPane( ).add( masterPanel );
		
						
						
		bodyBrowseButton.addActionListener( new BodyBrowseButtonListener( ) );
		okButton.addActionListener( new OkButtonListener( this ) );
		cancelButton.addActionListener( new OkButtonListener( this ) );
		
	}// end constructor
	
	
	public String getBodyTexturePath( )
	{
		return bodyImageFileName.getText( );
	}
	
	private class BodyBrowseButtonListener implements ActionListener
	{
		public void actionPerformed( ActionEvent event )
		{
			String openPath = 
			  "C:/Documents and Settings/Owner/My Documents/Images/Insect Textures";
			  
	        JFileChooser fileChooser = new JFileChooser( openPath );
	        fileChooser.setDialogTitle( "Select body texture..." );
	        int result = fileChooser.showOpenDialog( null );
	
	        if( result == JFileChooser.APPROVE_OPTION )
	        {
	        	// Get the file path and populate the dialog box.
	        	File  file  = fileChooser.getSelectedFile();
	          	String path = file.getAbsolutePath();
				
				bodyImageFileName.setText( path );
	        }// end if			
			
		}// end actionPerformed
	}
	
	private class OkButtonListener implements ActionListener
	{
		private TextureDialog dlg;
		
		// CONSTRUCTOR
		//
		public OkButtonListener( TextureDialog dialog )
		{
			this.dlg = dialog;
		}// end constructor
		
		public void actionPerformed( ActionEvent event )
		{		
			dlg.show( false );
		}// end actionPerformed
	}
	
	private class CancelButtonListener implements ActionListener
	{
		private TextureDialog dlg;
		
		// CONSTRUCTOR
		//
		public CancelButtonListener( TextureDialog dialog )
		{
			this.dlg = dialog;
		}// end constructor
		
		public void actionPerformed( ActionEvent event )
		{		
			dlg.show( false );
		}// end actionPerformed
	}
		
			
	// ATTRIBUTES
	//
	
	// Text field for entering the path for the texture for the insect body.
	JTextField bodyImageFileName;
	
	// Browse button for opening a file dialog to select a texture file.
	JButton bodyBrowseButton;
	
}
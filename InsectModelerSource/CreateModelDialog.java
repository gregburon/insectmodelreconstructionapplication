

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

import ij.gui.ImageCanvas;
import ij.ImagePlus;
import ij.io.Opener;
import ij.process.ImageProcessor;

import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;

import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JScrollPane;
import javax.swing.JList;

import javax.swing.border.BevelBorder;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;


public class CreateModelDialog extends JDialog
{
	// CONSTRUCTOR
	//
	public CreateModelDialog( )
	{
		setTitle( "Create New Model" );
		setSize( 500, 250 );
		setModal( true );
		setLocation( 150, 150 );
		setResizable( false );
		
		// The master panel holds all of the JPanels.
		JPanel masterPanel = new JPanel( );
		masterPanel.setLayout( 
			new BoxLayout( masterPanel, BoxLayout.Y_AXIS ) );
			
		
		// The spinners panel holds the spinners for the size of the model.
		JPanel spinnersPanel = new JPanel( );
		spinnersPanel.setLayout( new BoxLayout( spinnersPanel, BoxLayout.X_AXIS ) );
		
		JLabel radialPointsLabel = new JLabel( " Radial Points: " );
		radialPointsLabel.setPreferredSize( new Dimension( 100, 25 )  );
		radialPointsLabel.setMinimumSize( new Dimension( 100, 25 )  );
		radialPointsLabel.setMaximumSize( new Dimension( 100, 25 )  );
		
		SpinnerNumberModel radialModel = new SpinnerNumberModel( 32, 4, 720, 1 );
		radialSpinner = new JSpinner( radialModel );
		radialSpinner.setPreferredSize( new Dimension( 75, 25 ) );
		radialSpinner.setMinimumSize( new Dimension( 75, 25 ) );
		radialSpinner.setMaximumSize( new Dimension( 75, 25 ) );
		
		JLabel columnsLabel = new JLabel( "     Columns: " );
		columnsLabel.setPreferredSize( new Dimension( 100, 25 )  );
		columnsLabel.setMinimumSize( new Dimension( 100, 25 )  );
		columnsLabel.setMaximumSize( new Dimension( 100, 25 )  );		
			
		SpinnerNumberModel columnsModel = new SpinnerNumberModel( 219, 2, 2000, 1 );
		columnsSpinner = new JSpinner( columnsModel );
		columnsSpinner.setPreferredSize( new Dimension( 75, 25 ) );
		columnsSpinner.setMinimumSize( new Dimension( 75, 25 ) );
		columnsSpinner.setMaximumSize( new Dimension( 75, 25 ) );
		
		spinnersPanel.add( radialPointsLabel );
		spinnersPanel.add( radialSpinner );
		spinnersPanel.add( columnsLabel );
		spinnersPanel.add( columnsSpinner );
		
				
		
		// The controls panel holds the list box and the buttons.
		JPanel controlsPanel = new JPanel( );
		controlsPanel.setLayout( new BoxLayout( controlsPanel, BoxLayout.X_AXIS ) );
  
		   
		int imagePanelWidth = 370;
		int imagePanelHeight = 125;
		int imageScrollBoxWidth = 365;
		int imageScrollBoxHeight = 100;
		 
		JPanel imageListPanel = new JPanel( );
		imageListPanel.setLayout( new BoxLayout( imageListPanel, BoxLayout.Y_AXIS ) );	
		JLabel imageListLabel = new JLabel( " Body Images (templates):" );
		imageListLabel.setPreferredSize( new Dimension( imagePanelWidth, 25 )  );
		imageListLabel.setMinimumSize( new Dimension( imagePanelWidth, 25 )  );
		imageListLabel.setMaximumSize( new Dimension( imagePanelWidth, 25 )  );	 
		   
		imageListBox = new JList( );
		imageListBox.setPreferredSize( new Dimension( imageScrollBoxWidth, imageScrollBoxHeight )  );
		imageListBox.setMinimumSize( new Dimension( imageScrollBoxWidth, imageScrollBoxHeight )  );
		imageListBox.setMaximumSize( new Dimension( imageScrollBoxWidth, imageScrollBoxHeight )  );		
		imageListBoxScrollPane = new JScrollPane( imageListBox );
		imageListBoxScrollPane.setWheelScrollingEnabled( true );
		imageListBoxScrollPane.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
		imageListBoxScrollPane.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		imageListBoxScrollPane.setPreferredSize( new Dimension( imageScrollBoxWidth, imageScrollBoxHeight ) );
		imageListBoxScrollPane.setMinimumSize( new Dimension( imageScrollBoxWidth, imageScrollBoxHeight ) );
		imageListBoxScrollPane.setMaximumSize( new Dimension( imageScrollBoxWidth, imageScrollBoxHeight ) );
		imageListPanel.add( imageListLabel );
		imageListPanel.add( imageListBoxScrollPane );
		imageListPanel.setPreferredSize( new Dimension( imagePanelWidth, imagePanelHeight )  );
		imageListPanel.setMinimumSize( new Dimension( imagePanelWidth, imagePanelHeight )  );
		imageListPanel.setMaximumSize( new Dimension( imagePanelWidth, imagePanelHeight )  );	 
		
		JPanel buttonsPanel = new JPanel( );
		buttonsPanel.setLayout( new BoxLayout( buttonsPanel, BoxLayout.Y_AXIS ) );
		
		int buttonWidth = 120;
		int buttonHeight = 25;
		int buttonPanelWidth = 125;
		int buttonPanelHeight = 125;
		
		JButton addImageButton = new JButton( "Add Image..." );
		addImageButton.setPreferredSize( new Dimension( buttonWidth, buttonHeight ) );
		addImageButton.setMinimumSize( new Dimension( buttonWidth, buttonHeight ) );
		addImageButton.setMaximumSize( new Dimension( buttonWidth, buttonHeight ) );
		addImageButton.setBorder( BorderFactory.createBevelBorder( BevelBorder.RAISED ) );
		addImageButton.addActionListener( new AddImageListener( this ) );	
		
		JButton removeImageButton = new JButton( "Remove Image" );
		removeImageButton.setPreferredSize( new Dimension( buttonWidth, buttonHeight ) );
		removeImageButton.setMinimumSize( new Dimension( buttonWidth, buttonHeight ) );
		removeImageButton.setMaximumSize( new Dimension( buttonWidth, buttonHeight ) );
		removeImageButton.setBorder( BorderFactory.createBevelBorder( BevelBorder.RAISED ) );
		removeImageButton.addActionListener( new RemoveImageListener( this ) );
		
		JButton texturesButton = new JButton( "Textures" );
		texturesButton.setPreferredSize( new Dimension( buttonWidth, buttonHeight ) );
		texturesButton.setMinimumSize( new Dimension( buttonWidth, buttonHeight ) );
		texturesButton.setMaximumSize( new Dimension( buttonWidth, buttonHeight ) );
		texturesButton.setBorder( BorderFactory.createBevelBorder( BevelBorder.RAISED ) );
		texturesButton.addActionListener( new TextureButtonListener( this ) );
				
		createModelButton = new JButton( "Create Model" );
		createModelButton.setPreferredSize( new Dimension( buttonWidth, buttonHeight ) );
		createModelButton.setMinimumSize( new Dimension( buttonWidth, buttonHeight ) );
		createModelButton.setMaximumSize( new Dimension( buttonWidth, buttonHeight ) );
		createModelButton.setBorder( BorderFactory.createBevelBorder( BevelBorder.RAISED ) );
		
		JButton cancelButton = new JButton( "Cancel" );
		cancelButton.setPreferredSize( new Dimension( buttonWidth, buttonHeight ) );
		cancelButton.setMinimumSize( new Dimension( buttonWidth, buttonHeight ) );
		cancelButton.setMaximumSize( new Dimension( buttonWidth, buttonHeight ) );
		cancelButton.setBorder( BorderFactory.createBevelBorder( BevelBorder.RAISED ) );	
		cancelButton.addActionListener( new CancelListener( this ) );			
		
		buttonsPanel.add( addImageButton );
		buttonsPanel.add( removeImageButton );
		buttonsPanel.add( texturesButton );
		buttonsPanel.add( createModelButton );
		buttonsPanel.add( cancelButton );
		buttonsPanel.setPreferredSize( new Dimension( buttonPanelWidth, buttonPanelHeight ) );
		buttonsPanel.setMinimumSize( new Dimension( buttonPanelWidth, buttonPanelHeight ) );
		buttonsPanel.setMaximumSize( new Dimension( buttonPanelWidth, buttonPanelHeight ) );				
		
		// Add the contents of the controls panel.
		controlsPanel.add( imageListPanel );
		controlsPanel.add( buttonsPanel );	
		
		// Add everything to the master panel.
		masterPanel.add( new JLabel( "  " ) );
		masterPanel.add( spinnersPanel );
		masterPanel.add( new JLabel( "  " ) );
		masterPanel.add( new JLabel( "  " ) );
		masterPanel.add( controlsPanel );
		
		// Add the master panel to the dialog box.
		this.getContentPane( ).add( masterPanel );
		
		
		addImageDialog = new AddImageDialog( );
		addImageDialog.show( false );
		addImageDialog.addOkButtonListener( new AddImageOkListener( this ) );
		addImageDialog.addCancelButtonListener( new AddImageCancelListener( this ) );
		addImageDialog.addBrowseButtonListener( new AddImageBrowseListener( this ) );
		
		textureDialog = new TextureDialog( );
		textureDialog.show( false );
		
		insectBodyRecordVector = new Vector( );

	}// end constructor
	
	
	public void populateListBox( )
	{
		Vector names = new Vector( );
		
		for( int i=0; i<insectBodyRecordVector.size( ); ++i )
		{
			ImageRecord record = 
			  ((ImageRecord)(insectBodyRecordVector.elementAt( i )));
			  
			// Create a string to represent this image in the
			// image list box.  The string is a representation 
			// of the camera position that was used to take this 
			// image of the insect.
			StringBuffer buffer = new StringBuffer( );
			
			int index = (record.getPath( )).lastIndexOf( '\\' );
			String fileName = (record.getPath( )).substring( index+1 );
			
			buffer.append( fileName );
			buffer.append( "    angle=" );
			buffer.append( record.getAngle( ) );
			
			names.add( buffer );
		}// end for
		
		imageListBox.setListData( names );	
	}
	
	

	public int getRadialPoints( )
	// The getRadialPoints( ) method returns the number of points that
	// comprise one column of points for the insect 
	{
		return (int)((Integer)(radialSpinner.getValue( ))).intValue( );	
	}
	
	public int getColumns( )
	// Return the number of columns of points to the caller.  The number 
	// of columns is the number of rings of points that make up the initial
	// insect body.
	{
		return (int)((Integer)(columnsSpinner.getValue( ))).intValue( );	
	}
	
	public Vector getInsectBodyRecordVector( )
	// Return the insectBodyRecordVector to the caller.  This list of image
	// records is used to register the model to the images, which are 
	// each contained as members in each image record.
	{
		return insectBodyRecordVector;
	}
	
	public String getInsectBodyTexturePath( )
	{
		return textureDialog.getBodyTexturePath( );
	}		
	
	public void addCreateModelButtonListener( ActionListener listener )
	{
		createModelButton.addActionListener( listener );
	}			
	
	
	private void removeRecord( int index )
	{
		insectBodyRecordVector.remove( index );
	}
	
	private class AddImageListener implements ActionListener
	{
		// Pointer to parent dialog.
		private CreateModelDialog dlg;
		
		// CONSTRUCTOR
		//
		public AddImageListener( CreateModelDialog dialog )
		{
			this.dlg = dialog;
		}// end constructor
		
				
		public void actionPerformed( ActionEvent event )
		// Create a new dialog to get the image information
		// to add to the images list.
		{
			addImageDialog.show( true );
	
		}// end actionPerformed	
	}
	
	
	
	private class RemoveImageListener implements ActionListener
	{
		// Pointer to parent dialog.
		private CreateModelDialog dlg;
		
		// CONSTRUCTOR
		//
		public RemoveImageListener( CreateModelDialog dialog )
		{
			this.dlg = dialog;
		}// end constructor	
			
				
		public void actionPerformed( ActionEvent event )
		{
			int index = imageListBox.getSelectedIndex( );
			
			if( index >= 0 && index < imageListBox.getModel( ).getSize( ) )
			{
				// Remove the record and update the list box.
				dlg.removeRecord( index );
				dlg.populateListBox( );

			}// end if
			
		}// end actionPerformed
	}	
	
	
	private class TextureButtonListener implements ActionListener
	{
		// Pointer to parent dialog.
		private CreateModelDialog dlg;
		
		// CONSTRUCTOR
		//
		public TextureButtonListener( CreateModelDialog dialog )
		{
			this.dlg = dialog;
		}// end constructor
		
		public void actionPerformed( ActionEvent event )
		{
			textureDialog.show( true );
		}// end actionPerformed
	}
	
	private class CancelListener implements ActionListener
	// The CancelListener class hides the dialog box. 
	{
		// Pointer to parent dialog.
		private CreateModelDialog dlg;
		
		// CONSTRUCTOR
		//
		public CancelListener( CreateModelDialog dialog )
		{
			this.dlg = dialog;
		}// end constructor
		
		public void actionPerformed( ActionEvent event )
		// Hide the CreateModelDialog.
		{
			dlg.show( false );
		}// end actionPerformed
	}
	
	private class AddImageOkListener implements ActionListener
	{
		// Pointer to parent dialog.
		private CreateModelDialog dlg;
		
		// CONSTRUCTOR
		//
		public AddImageOkListener( CreateModelDialog dialog )
		{
			this.dlg = dialog;
		}// end constructor
	
				
		public void actionPerformed( ActionEvent event )
		// Add the information found in the AddImageDialog to the
		// image list in the CreateModelDialog's image list.
		{
			addImageDialog.show( false );
			
			String path  = addImageDialog.getPath( );
			float  angle = addImageDialog.getAngle( );
			
			// Add the record to the image list.
			Opener imageJOpener;
		    ImagePlus imagePlus;		
		    imageJOpener = new Opener( );
		    imagePlus = imageJOpener.openImage( path ); 

			ImageRecord imageRecord = new ImageRecord( );
			imageRecord.setPath( path );
			imageRecord.setAngle( angle );
			imageRecord.setImagePlus( imagePlus );
			imageRecord.createImageProcessor( imagePlus );
			
			insectBodyRecordVector.add( imageRecord );			
		
			dlg.populateListBox( );
			
		}// end actionPerformed
	}
	
	
	private class AddImageCancelListener implements ActionListener
	{
		// Pointer to parent dialog.
		private CreateModelDialog dlg;
		
		// CONSTRUCTOR
		//
		public AddImageCancelListener( CreateModelDialog dialog )
		{
			this.dlg = dialog;
		}// end constructor	

		
		public void actionPerformed( ActionEvent event )
		// Hide the AddImageDialog.
		{
			addImageDialog.show( false );
		}// end actionPerformed
	}
	
	private class AddImageBrowseListener implements ActionListener
	{
		// Pointer to parent dialog.
		private CreateModelDialog dlg;
		
		// CONSTRUCTOR
		//
		public AddImageBrowseListener( CreateModelDialog dialog )
		{
			this.dlg = dialog;
		}// end constructor	
		
				
		public void actionPerformed( ActionEvent event )
		// Get the input from the dialog box.
		{
			String openPath = 
			  "C:/Documents and Settings/Owner/My Documents/Images/Insect Templates";
			  
	        JFileChooser fileChooser = new JFileChooser( openPath );
	        fileChooser.setDialogTitle( "Select image file..." );
	        int result = fileChooser.showOpenDialog( null );
	
	        if( result == JFileChooser.APPROVE_OPTION )
	        {
	        	// Get the file path and populate the dialog box.
	        	File  file  = fileChooser.getSelectedFile();
	          	String path = file.getAbsolutePath();
	          	addImageDialog.setImageFileTextBox( path );
	        }// end if
		
		}// end actionPerformed	
	}
	
	
	
	// ATTRIBUTES
	//
	
	private JSpinner radialSpinner;
	private JSpinner columnsSpinner;
	private JButton createModelButton;
	private JList imageListBox;
	private JScrollPane imageListBoxScrollPane;
	
	private AddImageDialog addImageDialog;
	private TextureDialog  textureDialog;
	
	private Vector insectBodyRecordVector;		
}
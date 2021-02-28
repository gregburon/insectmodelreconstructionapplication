

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
import javax.swing.JDialog;
import javax.swing.JLabel;



public class DestroyModelDialog extends JDialog
{
	
	// CONSTRUCTOR
	//
	public DestroyModelDialog( )
	{
		Dimension dim          = new Dimension( 75, 10 );
		Dimension messageDim   = new Dimension( 250, 10 );

		
		setTitle( "Destroy Data" );
		setSize( 300, 100 );
		setResizable( false );
		setLocation( 50, 50 );
		setModal( true );
		show( false );
	
		// Initialize the gridbag.
		gridBagLayout      = new GridBagLayout( );
		gridBagConstraints = new GridBagConstraints( );
		getContentPane( ).setLayout( gridBagLayout);
		gridBagConstraints.fill = GridBagConstraints.VERTICAL;
		
		
		// Initialize the buttons.		
		okButton       = new JButton( "Ok" );
		cancelButton   = new JButton( "Cancel" );
		
		okButton.setPreferredSize( dim );
		okButton.setMaximumSize( dim );
		cancelButton.setPreferredSize( dim );
		cancelButton.setMaximumSize( dim );
		
		message = new JLabel( "Really destroy the insect model data?" );
		message.setPreferredSize( messageDim );
		message.setMaximumSize( messageDim );

		
		// Add the components to the grid bag.
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.ipadx     = 10;
		gridBagConstraints.ipady     = 10;
		gridBagConstraints.weightx   = 0.5;
		gridBagConstraints.gridx     = 0;
		gridBagConstraints.gridy     = 0;
		gridBagLayout.setConstraints( message, gridBagConstraints );
		getContentPane( ).add( message );		
				
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.ipadx     = 10;
		gridBagConstraints.ipady     = 10;
		gridBagConstraints.weightx   = 0.5;
		gridBagConstraints.gridx     = 0;
		gridBagConstraints.gridy     = 1;
		gridBagLayout.setConstraints( okButton, gridBagConstraints );
		getContentPane( ).add( okButton );		

		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.ipadx     = 10;
		gridBagConstraints.ipady     = 10;
		gridBagConstraints.weightx   = 0.5;
		gridBagConstraints.gridx     = 1;
		gridBagConstraints.gridy     = 1;
		gridBagLayout.setConstraints( cancelButton, gridBagConstraints );
		getContentPane( ).add( cancelButton );
		
		cancelButton.addActionListener( new CancelListener( ) );
						
	}// end constructor
	
	
	// METHODS
	//
	public void addOkListener( ActionListener listener )
	{
		okButton.addActionListener( listener );
	}
	
	public class CancelListener implements ActionListener
	{
		public void actionPerformed( ActionEvent event )
		{
			show( false );	
		}// end actionPerformed
	}
	
	// ATTRIBUTES
	//
	private JLabel  message;
	private JButton okButton;
	private JButton cancelButton;
	private GridBagLayout      gridBagLayout;
	private GridBagConstraints gridBagConstraints;	
}
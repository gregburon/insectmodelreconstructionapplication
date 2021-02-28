

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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;


public class ModelerMenu extends JMenuBar
{
 
	// CONSTRUCTOR
	//
			
	public ModelerMenu( )
	{
		// Set up all of the menus and menu items
		// for the menu in this application.
		fileMenu         = new JMenu( "File" );
		fileCreate       = new JMenuItem( "Create new model..." );
		fileExportToVRML = new JMenuItem( "Export to VRML..." );
		fileClose        = new JMenuItem( "Close Model" );
		fileExit         = new JMenuItem( "Exit" );
		modelMenu        = new JMenu( "Model" );
		displayMenu      = new JMenu( "Display" );
		controlsPanel    = new JMenuItem( "Controls Panel..." );
		axesEnable       = new JCheckBoxMenuItem( "Display Coordinate Axis", true );
		pointMode        = new JRadioButtonMenuItem( "Model mode: Points", false );
		wireframeMode    = new JRadioButtonMenuItem( "Model mode: Wireframe", false );
		polygonMode      = new JRadioButtonMenuItem( "Model mode: Polygon", true );
		
		displayInsectBody   = new JCheckBoxMenuItem( "Display Insect Body", true );  
		displayInsectRFLeg  = new JCheckBoxMenuItem( "Display Insect RF Leg", true );   
		displayInsectRMLeg  = new JCheckBoxMenuItem( "Display Insect RM Leg", true );  
		displayInsectRBLeg  = new JCheckBoxMenuItem( "Display Insect RB Leg", true );  
		displayInsectLFLeg  = new JCheckBoxMenuItem( "Display Insect LF Leg", true );  
		displayInsectLMLeg  = new JCheckBoxMenuItem( "Display Insect LM Leg", true );   
		displayInsectLBLeg  = new JCheckBoxMenuItem( "Display Insect LB Leg", true );   		
		 
		
		fileMenu.add( fileCreate );
		fileMenu.add( fileExportToVRML );
		fileMenu.add( fileClose );
		fileMenu.add( fileExit );
		add( fileMenu );
		
		modelMenu.add( pointMode );
		modelMenu.add( wireframeMode );
		modelMenu.add( polygonMode );
		modelMenu.add( new JSeparator( ) );
		modelMenu.add( displayInsectBody );
		modelMenu.add( displayInsectRFLeg );
		modelMenu.add( displayInsectRMLeg );
		modelMenu.add( displayInsectRBLeg );
		modelMenu.add( displayInsectLFLeg);
		modelMenu.add( displayInsectLMLeg );
		modelMenu.add( displayInsectLBLeg );
		

		add( modelMenu );
		
		displayMenu.add( controlsPanel );
		displayMenu.add( axesEnable );
		add( displayMenu );
		
		setModelActiveButtonsEnable( false );
				
	}// end constructor



	// METHODS
	//
	
	// Add listener methods.
	public void addCreateModelListener( ActionListener listener )
	{
		fileCreate.addActionListener( listener );
	}
	
	public void addExportToVRMLListener( ActionListener listener )
	{
		fileExportToVRML.addActionListener( listener );
	}
	
	public void addCloseModelListener( ActionListener listener )
	{
		fileClose.addActionListener( listener );
	}
	
	public void addExitListener( ActionListener listener )
	{
		fileExit.addActionListener( listener );
	}
	
	public void addAxisListener( ActionListener listener )
	{
		axesEnable.addActionListener( listener );
	}
	
	public void addControlsPanelListener( ActionListener listener )
	{
		controlsPanel.addActionListener( listener );
	}
	
	public void addPointModeListener( ActionListener listener )
	{ 
		pointMode.addActionListener( listener );
	}

	public void addWireframeModeListener( ActionListener listener )
	{ 
		wireframeMode.addActionListener( listener );
	}	
	
	public void addPolygonModeListener( ActionListener listener )
	{ 
		polygonMode.addActionListener( listener );
	}
	
	public void addDisplayBodyListener( ActionListener listener )
	{
		displayInsectBody.addActionListener( listener );
	}
	
	public void addDisplayRFLegListener( ActionListener listener )
	{
		displayInsectRFLeg.addActionListener( listener );
	}
	
	public void addDisplayRMLegListener( ActionListener listener )
	{
		displayInsectRMLeg.addActionListener( listener );
	}
	
	public void addDisplayRBLegListener( ActionListener listener )
	{
		displayInsectRBLeg.addActionListener( listener );
	}
	
	public void addDisplayLFLegListener( ActionListener listener )
	{
		displayInsectLFLeg.addActionListener( listener );
	}
	
	public void addDisplayLMLegListener( ActionListener listener )
	{
		displayInsectLMLeg.addActionListener( listener );
	}
	
	public void addDisplayLBLegListener( ActionListener listener )
	{
		displayInsectLBLeg.addActionListener( listener );
	}		
	
	
					
	// Get status methods.
	public boolean getAxisEnable( )
	{
		return axesEnable.isSelected( );
	}
	
	public boolean getDisplayBody( )
	{
		return displayInsectBody.isSelected( );
	}
	
	public boolean getDisplayRFLeg( )
	{
		return displayInsectRFLeg.isSelected( );	
	}
	
	public boolean getDisplayRMLeg( )
	{
		return displayInsectRMLeg.isSelected( );	
	}
	
	public boolean getDisplayRBLeg( )
	{
		return displayInsectRBLeg.isSelected( );	
	}	

	public boolean getDisplayLFLeg( )
	{
		return displayInsectLFLeg.isSelected( );	
	}
	
	public boolean getDisplayLMLeg( )
	{
		return displayInsectLMLeg.isSelected( );	
	}
	
	public boolean getDisplayLBLeg( )
	{
		return displayInsectLBLeg.isSelected( );	
	}
		
		
		
	// Set methods.
	public void setPointModeRadioButton( boolean enable )
	{
		pointMode.setSelected( enable );
	}
	
	public void setWireframeModeRadioButton( boolean enable )
	{
		wireframeMode.setSelected( enable );
	}
	
	public void setPolygonModeRadioButton( boolean enable )
	{
		polygonMode.setSelected( enable );
	}
	
	public void setModelActiveButtonsEnable( boolean enable )
	{
		pointMode.setEnabled( enable );
		wireframeMode.setEnabled( enable );
		polygonMode.setEnabled( enable );
		fileExportToVRML.setEnabled( enable );
		displayInsectBody.setEnabled( enable );
		displayInsectRFLeg.setEnabled( enable ); 
		displayInsectRMLeg.setEnabled( enable );  
		displayInsectRBLeg.setEnabled( enable ); 
		displayInsectLFLeg.setEnabled( enable );  
		displayInsectLMLeg.setEnabled( enable );  
		displayInsectLBLeg.setEnabled( enable ); 		
	}
	
	
	// ATTRIBUTES
	//
	JMenu fileMenu;
	JMenuItem fileCreate;
	JMenuItem fileExportToVRML;
	JMenuItem fileClose;
	JMenuItem fileExit;
	JMenu modelMenu;
	JMenuItem registerModel;
	JMenuItem controlsPanel;
	JMenu displayMenu;
	JCheckBoxMenuItem axesEnable;  
	JRadioButtonMenuItem pointMode;
	JRadioButtonMenuItem wireframeMode;
	JRadioButtonMenuItem polygonMode;
	JCheckBoxMenuItem displayInsectBody;  
	JCheckBoxMenuItem displayInsectRFLeg;  
	JCheckBoxMenuItem displayInsectRMLeg;  
	JCheckBoxMenuItem displayInsectRBLeg;  
	JCheckBoxMenuItem displayInsectLFLeg;  
	JCheckBoxMenuItem displayInsectLMLeg;  
	JCheckBoxMenuItem displayInsectLBLeg;  		
}
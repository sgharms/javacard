package com.stevengharms.javacard;

import javax.swing.*;
import java.awt.*;

public class JavaCardView{
	public static String[] EAST_PANEL_BUTTON_TITLES = {"Add","Delete"};
	public static String[] WEST_PANEL_LABEL_TITLES = {"Question:","Answer:"};
	public static String[] SOUTH_PANEL_BUTTON_TITLES = {"Back", "Correct", "Incorrect", "Forward", "Quit"};
	
	public JavaCardView(){
		// Top level container
		JFrame frame = new JFrame();
		frame.setSize(500,500);
		
		// East container
		JPanel eastButtonPanel = new JPanel(new GridLayout(2,1));
		eastButtonPanel.setSize(20,20);
	    configureButtons(
	      eastButtonPanel, this.EAST_PANEL_BUTTON_TITLES);
		
		
		// West container
		JPanel westTextPanel = new JPanel(new GridLayout(2,1) );
		westTextPanel.setSize(200,200);
		configureTextAreas(
			westTextPanel, this.WEST_PANEL_LABEL_TITLES);
			
		//South container
		JPanel southNavigationPanel = new JPanel();
		configureButtons(
		  southNavigationPanel, this.SOUTH_PANEL_BUTTON_TITLES);
		
		// Attach the components 
		frame.getContentPane().add(BorderLayout.EAST, eastButtonPanel);
		frame.getContentPane().add(BorderLayout.CENTER, westTextPanel);
		frame.getContentPane().add(BorderLayout.SOUTH, southNavigationPanel);

		// Read 'em and weep
		frame.setVisible(true);
	}
	
	public Container configureButtons(Container c, String[] names){
		for (int i=0; i< names.length; i++){
			c.add(new JButton(names[i]));
		}
		return c;
	}
	public Container configureTextAreas(Container c, String[] names){
		for (int i=0; i< names.length; i++){
			JTextArea jta = new JTextArea();
			jta.setLineWrap(true);
			jta.setPreferredSize(new Dimension(10,10));
			jta.setBorder(BorderFactory.createLineBorder(Color.black));

			JLabel jl = new JLabel(names[i]);
			jl.setSize(5,1);
			jl.setLabelFor(jta);
			
			c.add(jl);
			c.add(jta);
		}
		return c;
	}
}

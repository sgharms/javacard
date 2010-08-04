package com.stevengharms.javacard;

import javax.swing.*;
import java.awt.*;

public class JavaCardView{
	public JavaCardView(){
		// Top level container
		JFrame frame = new JFrame();
		frame.setSize(500,500);
		
		// East container
		JPanel eastButtonPanel = new JPanel(new GridLayout(2,1) );
		eastButtonPanel.setSize(120,120);
	    configureButtons(
	      eastButtonPanel, new String[]{"Add","Delete"});
		
		
		// West container
		JPanel westTextPanel = new JPanel();
		westTextPanel.setSize(200,200);
		configureTextAreas(
			westTextPanel, new String[]{"Question","Answer"});
			
		//South container
		JPanel southNavigationPanel = new JPanel();
		configureButtons(
		  southNavigationPanel, new String[]{"Back", "Right", "Wrong", "Forward"});
		
		// Attach the components 
		frame.getContentPane().add(BorderLayout.EAST, eastButtonPanel);
		frame.getContentPane().add(BorderLayout.WEST, westTextPanel);
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
			c.add(new JTextArea(names[i]));
		}
		return c;
	}
}

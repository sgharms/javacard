package com.stevengharms.javacard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JavaCardView{
	
	// This makes the big box's, with the minimize, maximize, close buttons 
	// size change
	private static final Dimension FRAME_SIZE      = new Dimension(480,480);
	
	// This is the container for the text widgets
	private static final Dimension WEST_PANEL_SIZE = new Dimension(475,475);
	private static final Dimension TEXT_AREA_SIZE  = new Dimension(380,175);
	private static final Dimension LABEL_SIZE      = new Dimension(60,20);

	
	private JButton   button_add 	 = new JButton("Add");
	private JButton   button_del 	 = new JButton("Delete");	

	private JTextArea jtext_ques	 = new JTextArea();
	private JTextArea jtext_answer 	 = new JTextArea();

	private JButton   button_back 	 = new JButton("<");
    private JButton   button_right 	 = new JButton("Right!");
    private JButton   button_wrong 	 = new JButton("Wrong!");
	private JButton   button_forward = new JButton(">");
    private JButton   button_exit 	 = new JButton("Exit");

	/* Button Listeners */
	
	class ButtonBackListener implements ActionListener {
		public void actionPerformed (ActionEvent e){
			System.out.println("Back got clicked!");
		}
	}

	class ButtonRightListener implements ActionListener {
		public void actionPerformed (ActionEvent e){
			System.out.println("I got clicked!");
		}
	}

	class ButtonWrongListener implements ActionListener {
		public void actionPerformed (ActionEvent e){
			System.out.println("I got clicked!");
		}
	}

	class ButtonForwardListener implements ActionListener {
		public void actionPerformed (ActionEvent e){
			System.out.println("I got clicked!");
		}
	}

	class ButtonExitListener implements ActionListener {
		public void actionPerformed (ActionEvent e){
			System.out.println("I got clicked!");
		}
	}

	class ButtonAddListener implements ActionListener {
		public void actionPerformed (ActionEvent e){
			System.out.println("I got clicked!");
		}
	}

	class ButtonDelListener implements ActionListener {
		public void actionPerformed (ActionEvent e){
			System.out.println("I got clicked!");
		}
	}


	
	public JavaCardView(){
		// Top level container
		JFrame frame = new JFrame();
		frame.setSize(this.FRAME_SIZE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// West container
		JPanel westTextPanel = new JPanel();
		westTextPanel.setLayout(new FlowLayout());
		
		westTextPanel.setMaximumSize(this.WEST_PANEL_SIZE);
		westTextPanel.setPreferredSize(this.WEST_PANEL_SIZE);
		configureTextAreas(
			westTextPanel, new JTextArea[]{jtext_ques, jtext_answer},
						   new String[]{"Question", "Answer"});
			
		//South container
		JPanel southNavigationPanel = new JPanel();
		configureButtons(
			      southNavigationPanel, new JButton[]{button_back,button_add,button_del,button_forward} );
		
		// Attach the components 
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.getContentPane().add(westTextPanel, BorderLayout.WEST);
		frame.getContentPane().add(southNavigationPanel);


		// Assign listeners
		button_add.addActionListener(new ButtonAddListener());
		button_del.addActionListener(new ButtonDelListener());
		
		button_back.addActionListener(new ButtonBackListener());
		button_right.addActionListener(new ButtonRightListener());
		button_wrong.addActionListener(new ButtonWrongListener());
		button_forward.addActionListener(new ButtonAddListener());
		button_exit.addActionListener(new ButtonExitListener());

		// Read 'em and weep
		frame.setVisible(true);
	}
	
	private Container configureButtons(Container c, JButton[] jcomps){
		for (int i=0; i< jcomps.length; i++){
			c.add(jcomps[i]);
		}
		return c;
	}
	private Container configureTextAreas(Container c, JTextArea[] jcomps, String[] labels){
		for (int i=0; i< jcomps.length; i++){
			jcomps[i].setLineWrap(true);
			jcomps[i].setMaximumSize(this.TEXT_AREA_SIZE);
			jcomps[i].setPreferredSize(this.TEXT_AREA_SIZE);
			jcomps[i].setBorder(BorderFactory.createLineBorder(Color.black));
			
			// Oooh, hey isn't that fancy, an anonymous class that implements an interface?
			// Neat.
			jcomps[i].addKeyListener(
			  new KeyListener(){
				public void keyPressed(KeyEvent k){
				}
				public void keyReleased(KeyEvent k){
				}
				public void keyTyped(KeyEvent k){
				}
			  }
			);

			JLabel jl = new JLabel(labels[i],SwingConstants.RIGHT);
			jl.setPreferredSize(this.LABEL_SIZE); 
			
			c.add(jl);
			c.add(jcomps[i]);
		}
		return c;
	}
	
	public void setQuestion(String s){
		jtext_ques.setText(s);
	}
	
	public void setAnswer(String s){
		jtext_answer.setText(s);
	}
}

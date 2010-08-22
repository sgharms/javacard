package com.stevengharms.javacard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class JavaCardView{
	
	// Access to the controller
	JavaCardApp app = null;
	
	
	// This makes the big box's, with the minimize, maximize, close buttons 
	// size change
	private static final Dimension FRAME_SIZE      = new Dimension(480,480);
	
	// This is the container for the text widgets
	private static final Dimension WEST_PANEL_SIZE = new Dimension(475,475);
	private static final Dimension TEXT_AREA_SIZE  = new Dimension(380,175);
	private static final Dimension LABEL_SIZE      = new Dimension(60,20);

	
	private JButton   button_add 	 = new JButton("Add");
	private JButton   button_del 	 = new JButton("Delete");	
	private JButton   button_back 	 = new JButton("<");
	private JButton   button_forward = new JButton(">");
	private JButton   button_new     = new JButton("+");
	
	private JTextArea jtext_ques	 = new JTextArea();
	private JTextArea jtext_answer 	 = new JTextArea();

	/* Button Listeners */
	private class FocusPolicy{
		JComponent j;

		FocusPolicy(){
			super();
		}
		
		FocusPolicy(JComponent j){
			this.j=j;
		}
		
		public JComponent nextItem(){
			j = this.j;
			
			if (j.getName() == "jtext_ques")
			{
				return jtext_answer;
			}
			else if (j.getName() == "jtext_answer")
			{
				return button_add;
			}
			else
			{
				throw new IllegalArgumentException();
			}
		}
	}

	class ButtonNewListener implements ActionListener {
		JavaCardView viewClass;
		
		ButtonNewListener(){
			super();
		}
		
		ButtonNewListener(JavaCardView v){
			viewClass = v;
		}
		
		public void actionPerformed (ActionEvent e){
			System.out.println("New got clicked!");
			viewClass.prepForNewCard();
			viewClass.update();
		}
	}
		
	class ButtonBackListener implements ActionListener {
		JavaCardView viewClass;
		
		ButtonBackListener(){
			super();
		}
		
		ButtonBackListener(JavaCardView v){
			viewClass = v;
		}
		
		public void actionPerformed (ActionEvent e){
			System.out.println("Back got clicked!");
			app.goToPreviousCard();
			viewClass.update();
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
		JavaCardView viewClass;
		
		ButtonForwardListener(){
			super();
		}
		
		ButtonForwardListener(JavaCardView v){
			viewClass = v;
		}

		public void actionPerformed (ActionEvent e){
			System.out.println("Forward got clicked!");
			app.goToSubsequentCard();
			viewClass.update();
		}
		
	}

	class ButtonExitListener implements ActionListener {
		public void actionPerformed (ActionEvent e){
			System.out.println("I got clicked!");
		}
	}

	class ButtonAddListener implements ActionListener {
		JavaCardView viewClass;
		
		ButtonAddListener(){
			super();
		}
		
		ButtonAddListener(JavaCardView v){
			viewClass = v;
		}
		
		public void actionPerformed (ActionEvent e){
			System.out.println("Add clicked!");
			try{
				app.addNewCard(getQuestionAndAnswer());				
				viewClass.update();
			}
			catch (NullPointerException n){
				System.out.println("Caught a null exception!");
			}
		}
	}

	class ButtonDelListener implements ActionListener {
		JavaCardView viewClass;
		
		ButtonDelListener(){
			super();
		}
		
		ButtonDelListener(JavaCardView v){
			viewClass = v;
		}
		
		public void actionPerformed (ActionEvent e){
			System.out.println("Delete clicked!");
			try{
				viewClass.deleteCurrentCard();
				viewClass.update();
			}
			catch (NullPointerException n){
				System.out.println("DELETE caught a null exception!");
			}
		}		
	}


	public JavaCardView(JavaCardApp jca){
		this();
		this.app = jca;
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
		
		jtext_ques.setName("jtext_ques");
		jtext_answer.setName("jtext_answer");
		
		configureTextAreas(
			westTextPanel, new JTextArea[]{jtext_ques, jtext_answer},
						   new String[]{"Question", "Answer"});
			
		//South container
		JPanel southNavigationPanel = new JPanel();
		configureButtons(
			      southNavigationPanel, new JButton[]{button_back,button_add,button_del,button_forward,button_new} );
		
		// Attach the components 
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.getContentPane().add(westTextPanel, BorderLayout.WEST);
		frame.getContentPane().add(southNavigationPanel);


		// Assign listeners
		button_add.addActionListener(new ButtonAddListener(this));
		button_del.addActionListener(new ButtonDelListener(this));
		button_new.addActionListener(new ButtonNewListener(this));
		
		button_back.addActionListener(new ButtonBackListener(this));
		button_forward.addActionListener(new ButtonForwardListener(this));

		
		for (JTextArea j : new JTextArea[]{jtext_ques, jtext_answer}){
			j.setFocusTraversalKeysEnabled(true);
			j.addKeyListener(
			  new KeyListener(){
				public void keyPressed(KeyEvent k){
					if(k.getKeyCode() == KeyEvent.VK_TAB ){
						k.consume();
						new FocusPolicy((JComponent)k.getSource()).nextItem().requestFocus();
					}
				}
				public void keyReleased(KeyEvent k){
				}
				public void keyTyped(KeyEvent k){
				}				
			}
			);
		}
		

		// Read 'em and weep
		frame.setVisible(true);
		this.update();
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
	
	public String getQuestion(){
		return jtext_ques.getText();
	}
	
	public void setAnswer(String s){
		jtext_answer.setText(s);
	}
	
	public String getAnswer(){
		return jtext_answer.getText();
	}
	
	public void setQuestionAndAnswer(String q, String a){
		this.setQuestion(q);
		this.setAnswer(a);
	}
	
	public String[] getQuestionAndAnswer(){
		return new String[] {getQuestion(), getAnswer()};
	}

	protected void giveFocusToEmptyTextArea(){
		if (jtext_ques.getText().isEmpty()) jtext_ques.requestFocus();
		if (jtext_answer.getText().isEmpty()) jtext_answer.requestFocus();		
	}
	
	public void deleteCurrentCard(){
		System.out.println("Right now i have " + app.getCurrentCard());
		System.out.println("..and right now I have " + app.getDeck());
		app.deleteCurrentCard();
		jtext_ques.requestFocus();
	}
	
	public void update(){
		System.out.println("Update\n----------------------");
		
		Card curr = null;
		try{
			curr = app.getCurrentCard();
			/* OK, so we have a currentCard */
			this.setQuestion((String)curr.getFront());
			this.setAnswer((String)curr.getBack());
			
			/* Adjust the buttons as needed */
			/* It doesn't make sense for fwd or back, but new and delete should be enabled */
			if (app.getDeck().size() == 1){
				button_del.setEnabled(true);
				button_new.setEnabled(true);
			}
			
			if (app.getDeck().size() > 1){
				button_del.setEnabled(true);
				button_new.setEnabled(true);
				button_back.setEnabled(true);

				System.out.println("EXISTENCE TEST:  " + app.nextCardExists());

				if (app.nextCardExists()){
					System.out.println("Next Exists ");
					button_forward.setEnabled(true);
				}else{
					System.out.println("next does not exist");
					button_forward.setEnabled(false);					
				}
				
				if (app.priorCardExists()){
					System.out.println("Previous Exists");
					button_back.setEnabled(true);
				}else{
					System.out.println("Previous does not exist");
					button_back.setEnabled(false);
				}

			}

		}catch (NullPointerException e){
			System.out.println("There is no current card! " + e);
			this.setQuestionAndAnswer("","");
			this.button_del.setEnabled(false);
			this.button_forward.setEnabled(false);
			this.button_back.setEnabled(false);
			this.button_back.setEnabled(false);
		}
		catch (Exception e){
			System.out.println("Exception! " + e);
		}
	}
	
	public void prepForNewCard(){
		app.nullifyCurrentCard();
		jtext_ques.requestFocus();
	}
	
}

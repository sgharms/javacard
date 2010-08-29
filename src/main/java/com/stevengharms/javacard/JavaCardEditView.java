package com.stevengharms.javacard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class JavaCardEditView extends JavaCardViewParent {


    protected JButton   button_add 	 = new JButton("Add");
	private JButton   button_del 	 = new JButton("Delete");
    private JButton   button_new     = new JButton("+");

    class ButtonNewListener implements ActionListener {
		JavaCardViewParent viewParentClass;
		
		ButtonNewListener(){
			super();
		}
		
		ButtonNewListener(JavaCardViewParent v){
			viewParentClass = v;
		}
		
		public void actionPerformed (ActionEvent e){
			System.out.println("New got clicked!");
			viewParentClass.prepForNewCard();
			viewParentClass.update();
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


    class ButtonAddListener implements ActionListener {
		JavaCardViewParent viewParentClass;
		
		ButtonAddListener(){
			super();
		}
		
		ButtonAddListener(JavaCardViewParent v){
			viewParentClass = v;
		}
		
		public void actionPerformed (ActionEvent e){
			System.out.println("Add clicked!");
			try{
				app.addNewCard(getQuestionAndAnswer());				
				viewParentClass.update();
			}
			catch (NullPointerException n){
				System.out.println("Caught a null exception!");
			}
		}
	}

	class ButtonDelListener implements ActionListener {
		JavaCardViewParent viewParentClass;
		
		ButtonDelListener(){
			super();
		}
		
		ButtonDelListener(JavaCardViewParent v){
			viewParentClass = v;
		}
		
		public void actionPerformed (ActionEvent e){
			System.out.println("Delete clicked!");
			try{
				viewParentClass.deleteCurrentCard();
				viewParentClass.update();
			}
			catch (NullPointerException n){
				System.out.println("DELETE caught a null exception!");
			}
		}		
	}

     protected class FocusPolicy{
		JComponent j;

		FocusPolicy(){
			super();
		}

		FocusPolicy(JComponent j){
			this.j=j;
		}

		public JComponent nextItem(){
			j = this.j;

			if (j.getName().equals("jtext_ques"))
			{
				return jtext_answer;
			}
			else if (j.getName().equals("jtext_answer"))
			{
				return button_add;
			}
			else
			{
				throw new IllegalArgumentException();
			}
		}
	}

	public JavaCardEditView(JavaCardApp jca, JFrame jcf){
		this();
		app = jca;
        parentFrame = jcf;
		
		saveMenuItem.addActionListener(new SaveAction(app, frame));
        openMenuItem.addActionListener(new OpenAction(frame));
		quitMenuItem.addActionListener(new QuitAction(frame, parentFrame));
	}
	
	public JavaCardEditView(){
        super();
        // Top level container
        frame.setSize(FRAME_SIZE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// West container
		JPanel westTextPanel = new JPanel();
		westTextPanel.setLayout(new FlowLayout());
		
		westTextPanel.setMaximumSize(WEST_PANEL_SIZE);
		westTextPanel.setPreferredSize(WEST_PANEL_SIZE);
		
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
		
		/* Build in a funky, funky, menu */
		JMenuBar menuBar = new JMenuBar();
        menuBar.add(menu);

        menu.add(saveMenuItem);
		menu.add(openMenuItem);
		menu.add(quitMenuItem);
		


		frame.setJMenuBar(menuBar);
		

		// Read 'em and weep
		frame.setVisible(false);
	}

    @Override
    protected Container configureButtons(Container c, JButton[] jcomps){
		for (int i=0; i< jcomps.length; i++){
			c.add(jcomps[i]);
		}
		return c;
	}
	@Override
    protected Container configureTextAreas(Container c, JTextArea[] jcomps, String[] labels){
		for (int i=0; i< jcomps.length; i++){
			jcomps[i].setLineWrap(true);
			jcomps[i].setMaximumSize(TEXT_AREA_SIZE);
			jcomps[i].setPreferredSize(TEXT_AREA_SIZE);
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
			jl.setPreferredSize(LABEL_SIZE);
			
			c.add(jl);
			c.add(jcomps[i]);
		}
		return c;
	}

    @Override
    public void setVisible(boolean b){
        frame.setVisible(b);    
    }

    @Override
    public void update(){
		System.out.println("Update\n----------------------");
		
		Card curr = (app.getDeck().isEmpty()) ? null : app.getCurrentCard();

        if ( curr == null ){
        	System.out.println("There is no current card! ");
			this.setQuestionAndAnswer("","");
			this.button_del.setEnabled(false);
			this.button_forward.setEnabled(false);
			this.button_back.setEnabled(false);
			this.button_back.setEnabled(false);
            return;
        }

		try{
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

		}
		catch (Exception e){
			System.out.println("Exception! " + e);
		}
	}


}

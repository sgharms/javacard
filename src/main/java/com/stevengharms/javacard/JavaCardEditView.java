package com.stevengharms.javacard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;


public class JavaCardEditView implements ActionListener{
	
	// Access to the controller
	JavaCardApp app = null;
	
	// Root frame
	private JFrame frame, parentFrame;
	
	
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

	JMenu menu;
	JMenuItem menuItem, saveMenuItem, openMenuItem, quitMenuItem;
    private JPanel parentPanel;

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

	class ButtonNewListener implements ActionListener {
		JavaCardEditView editViewClass;
		
		ButtonNewListener(){
			super();
		}
		
		ButtonNewListener(JavaCardEditView v){
			editViewClass = v;
		}
		
		public void actionPerformed (ActionEvent e){
			System.out.println("New got clicked!");
			editViewClass.prepForNewCard();
			editViewClass.update();
		}
	}
		
	class ButtonBackListener implements ActionListener {
		JavaCardEditView editViewClass;
		
		ButtonBackListener(){
			super();
		}
		
		ButtonBackListener(JavaCardEditView v){
			editViewClass = v;
		}
		
		public void actionPerformed (ActionEvent e){
			System.out.println("Back got clicked!");
			app.goToPreviousCard();
			editViewClass.update();
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
		JavaCardEditView editViewClass;
		
		ButtonForwardListener(){
			super();
		}
		
		ButtonForwardListener(JavaCardEditView v){
			editViewClass = v;
		}

		public void actionPerformed (ActionEvent e){
			System.out.println("Forward got clicked!");
			app.goToSubsequentCard();
			editViewClass.update();
		}
		
	}


	class ButtonAddListener implements ActionListener {
		JavaCardEditView editViewClass;
		
		ButtonAddListener(){
			super();
		}
		
		ButtonAddListener(JavaCardEditView v){
			editViewClass = v;
		}
		
		public void actionPerformed (ActionEvent e){
			System.out.println("Add clicked!");
			try{
				app.addNewCard(getQuestionAndAnswer());				
				editViewClass.update();
			}
			catch (NullPointerException n){
				System.out.println("Caught a null exception!");
			}
		}
	}

	class ButtonDelListener implements ActionListener {
		JavaCardEditView editViewClass;
		
		ButtonDelListener(){
			super();
		}
		
		ButtonDelListener(JavaCardEditView v){
			editViewClass = v;
		}
		
		public void actionPerformed (ActionEvent e){
			System.out.println("Delete clicked!");
			try{
				editViewClass.deleteCurrentCard();
				editViewClass.update();
			}
			catch (NullPointerException n){
				System.out.println("DELETE caught a null exception!");
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
		// Top level container
		frame = new JFrame();
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
		menu = new JMenu("File");
		menuBar.add(menu);

		saveMenuItem = new JMenuItem("Save");
		openMenuItem = new JMenuItem("Open");
		quitMenuItem = new JMenuItem("Close");
		
		menu.add(saveMenuItem);
		menu.add(openMenuItem);
		menu.add(quitMenuItem);
		


		frame.setJMenuBar(menuBar);
		

		// Read 'em and weep
		frame.setVisible(false);
	}
	
	private class QuitAction implements ActionListener{
		private JFrame frame;
        public JFrame parentFrame;
        
		QuitAction(){
			super();
		}
		QuitAction(JFrame f, JFrame pf){
            this();
            frame = f;
            parentFrame = pf;
		}
		public void actionPerformed(ActionEvent e){
            frame.setVisible(false);
            parentFrame.setVisible(true);
			/*
			WindowEvent windowClosing = new WindowEvent(frame, WindowEvent.WINDOW_CLOSING);
			Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(windowClosing);
			*/
		}
	}

	private class OpenAction implements ActionListener{
		private JFrame frame;
		OpenAction(){
			super();
		}
		OpenAction(JFrame frame){
			this.frame = frame;
		}
		public void actionPerformed(ActionEvent e){
			System.out.println("Open a file");

			/* REFERENCE
			http://download.oracle.com/javase/tutorial/uiswing/components/filechooser.html
			*/
			
			final JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(frame);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fc.getSelectedFile();
	            System.out.println("Opening: " + file.getName() + "." );
				app.open(file.getAbsolutePath());
	        } else {
	            System.out.println("Open command cancelled by user." );
	        }			
		}
	}

	private class SaveAction implements ActionListener{
		private JavaCardApp jcController;
		private JFrame      parent;
		SaveAction(){
			super();
		}
		SaveAction(JavaCardApp j, JFrame parent){
			this.jcController = j;
			this.parent = parent;
		}
		public void actionPerformed(ActionEvent e){
			try{
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showSaveDialog(parent);
	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	                File file = fc.getSelectedFile();
	                jcController.save(file.toString());
	            } else {
	                // log.append("Save command cancelled by user." + newline);
	            }
				
			}catch (Exception ex){
				System.out.println("Couldn't save because: " + ex);
			}
		}
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

    public void setVisible(boolean b){
        frame.setVisible(b);    
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
	
	public void prepForNewCard(){
		app.nullifyCurrentCard();
		jtext_ques.requestFocus();
	}
	
	public void actionPerformed(ActionEvent e){
		System.out.println("I saw a " + e);
	}
	
	
}

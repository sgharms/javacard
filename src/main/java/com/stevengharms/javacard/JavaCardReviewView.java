package com.stevengharms.javacard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by IntelliJ IDEA.
 * User: stharms
 * Date: Aug 29, 2010
 * Time: 1:04:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class JavaCardReviewView extends JavaCardViewParent{
    JButton showButton;   

        class ButtonBackListener implements ActionListener {
        JavaCardViewParent viewParentClass;

        ButtonBackListener(){
            super();
        }

        ButtonBackListener(JavaCardViewParent v){
            viewParentClass = v;
        }

        public void actionPerformed (ActionEvent e){
            System.out.println("Back got clicked!");
            app.goToPreviousCard();
            viewParentClass.update();
            jtext_answer.setText("");
        }
    }

    class ButtonForwardListener implements ActionListener {
        JavaCardViewParent viewParentClass;

        ButtonForwardListener(){
            super();
        }

        ButtonForwardListener(JavaCardViewParent v){
            viewParentClass = v;
        }

        public void actionPerformed (ActionEvent e){
            System.out.println("Forward got clicked!");
            app.goToSubsequentCard();
            viewParentClass.update();
            jtext_answer.setText("");
        }

    }
    
    @Override
    public void update() {
        System.out.println("The length is " + app.getDeck().length())  ;
        int deckLength = app.getDeck().length();
        if ( deckLength == 0 ){
            button_forward.setEnabled(false);
            button_back.setEnabled(false);
            jtext_answer.setEnabled(false);
            showButton.setEnabled(false);
        }else if (deckLength == 1){
            // get the current card and apply it
        }else if (deckLength > 1){
            button_forward.setEnabled(true);
            button_back.setEnabled(true);
            showButton.setEnabled(true);
            System.out.println("This a one: " + app.getCurrentCard());
            jtext_ques.setText( app.getCurrentCard().getFront().toString());

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

            // jtext_answer.setText( app.getCurrentCard().getBack().toString());

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
				return jtext_ques;
			}
			else
			{
				throw new IllegalArgumentException();
			}
		}
	}
    
    public JavaCardReviewView(JavaCardApp jca, JFrame jcf){
		this();
		app = jca;
        parentFrame = jcf;

		saveMenuItem.addActionListener(new SaveAction(app, frame));
        openMenuItem.addActionListener(new OpenAction(frame));
		quitMenuItem.addActionListener(new QuitAction(frame, parentFrame));

        update();
	}

	public JavaCardReviewView(){
        super();
        // Top level container
        frame.setSize(FRAME_SIZE);
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
			      southNavigationPanel, new JButton[]{button_back,button_forward} );

        showButton = new JButton("Show");
        showButton.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent ae ){
                jtext_answer.setText(app.getCurrentCard().getBack().toString());
            }
        }
        );

        southNavigationPanel.add(showButton);

		// Attach the components
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.getContentPane().add(westTextPanel, BorderLayout.WEST);
		frame.getContentPane().add(southNavigationPanel);


		button_back.addActionListener(new ButtonBackListener(this));
		button_forward.addActionListener(new ButtonForwardListener(this));


		for (JComponent j : new JComponent[]{jtext_ques, jtext_answer, button_forward, button_back, showButton}){
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

		frame.setVisible(false);
	}
}


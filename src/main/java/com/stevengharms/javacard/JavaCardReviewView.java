package com.stevengharms.javacard;

import javax.swing.*;
import java.awt.*;
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
    @Override
    protected Container configureButtons(Container c, JButton[] jcomps) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected Container configureTextAreas(Container c, JTextArea[] jcomps, String[] labels) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    

    @Override
    public void update() {
        System.out.println("I am updating");
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

		// Attach the components
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.getContentPane().add(westTextPanel, BorderLayout.WEST);
		frame.getContentPane().add(southNavigationPanel);


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
}


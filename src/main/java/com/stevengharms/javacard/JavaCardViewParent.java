package com.stevengharms.javacard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: stharms
 * Date: Aug 29, 2010
 * Time: 12:50:57 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class JavaCardViewParent implements ActionListener {
    // Access to the controller
    protected JavaCardApp app = null;
    // Root frame
    protected JFrame frame;
    protected JFrame parentFrame;
    // This makes the big box's, with the minimize, maximize, close buttons
    // size change
    protected static final Dimension FRAME_SIZE      = new Dimension(480,480);
    // This is the container for the text widgets
    protected static final Dimension WEST_PANEL_SIZE = new Dimension(475,475);
    protected static final Dimension TEXT_AREA_SIZE  = new Dimension(380,175);
    protected static final Dimension LABEL_SIZE      = new Dimension(60,20);
    protected JButton   button_back 	 = new JButton("<");
    protected JButton   button_forward = new JButton(">");
    JTextArea jtext_ques	 = new JTextArea();
    JTextArea jtext_answer 	 = new JTextArea();
    JMenu menu;
    JMenuItem menuItem;
    JMenuItem saveMenuItem;
    JMenuItem openMenuItem;
    JMenuItem quitMenuItem;
    private JPanel parentPanel;

    public JavaCardViewParent() {
        quitMenuItem = new JMenuItem("Close");
        openMenuItem = new JMenuItem("Open");
        saveMenuItem = new JMenuItem("Save");
        frame = new JFrame();
        menu = new JMenu("File");
    }

    protected Container configureButtons(Container c, JButton[] jcomps){
		for (int i=0; i< jcomps.length; i++){
			c.add(jcomps[i]);
		}
		return c;
	}

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
        frame.setVisible(true);
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

    public abstract void update();

    public void prepForNewCard(){
        app.nullifyCurrentCard();
        jtext_ques.requestFocus();
    }

    public void actionPerformed(ActionEvent e){
        System.out.println("I saw a " + e);
    }

    /* Button Listeners */





    protected class QuitAction implements ActionListener{
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

    protected class OpenAction implements ActionListener{
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
                System.out.println(this + " is opening: " + file.getName() + "." );
                app.open(file.getAbsolutePath());
            } else {
                System.out.println("Open command cancelled by user." );
            }
        }
    }

    protected class SaveAction implements ActionListener{
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
}

package com.stevengharms.javacard;

import javax.swing.*;
import java.awt.*;

public class JavaCardView{
	public JavaCardView(){
		System.out.println("JC View");
		JFrame frame = new JFrame();
		JButton button = new JButton("Click me");
		frame.getContentPane().add(BorderLayout.EAST, button);
		frame.setSize(300,300);
		frame.setVisible(true);
	}
}

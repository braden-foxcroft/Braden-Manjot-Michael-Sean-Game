package com.tutorial.main;

import java.awt.Canvas;
import java.awt.Component;

import javax.swing.JFrame;

// The class that implements the window.
// Primarily copied/edited code.
// That said, there aren't that many ways to implement a window class.

public class Window extends Canvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = -240840600533728354L;
	public Component canvas;
	
	public Window(int width, int height, String title, Game game) {
		 JFrame frame = new JFrame(title);
		 
//		 frame.setPreferredSize(new Dimension(width, height));
//		 frame.setMaximumSize(new Dimension(width, height));
//		 frame.setMinimumSize(new Dimension(width, height));
		 frame.setSize(width, height);
		 
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 frame.setResizable(false);
		 frame.setLocationRelativeTo(null);
		 frame.add(game);
		 frame.setVisible(true);
		 canvas = frame.getComponents()[0];
		 // System.out.println(frame.getHeight());
		 game.start();
		 
	}

}

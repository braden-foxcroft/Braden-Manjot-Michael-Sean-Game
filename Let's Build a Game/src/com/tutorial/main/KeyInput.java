package com.tutorial.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
	
	private Handler handler;
	// note: All key input is stored in the handler class!
	// This is because that is where it is used.
	public KeyInput(Handler handler) {
		this.handler = handler;
	}
	
	public void keyPressed(KeyEvent e) {
		boolean s = true; // so that copy-pasting code is easier
		if (e.getKeyCode() == KeyEvent.VK_W) {
			handler.setW_Down(s);
		}
		else if (e.getKeyCode() == KeyEvent.VK_A) {
			handler.setA_Down(s);
		}
		else if (e.getKeyCode() == KeyEvent.VK_S) {
			handler.setS_Down(s);
		}
		else if (e.getKeyCode() == KeyEvent.VK_D) {
			handler.setD_Down(s);
		}
		else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			handler.player().velX = 0;
			handler.player().velY = 0;
			handler.player().x = 400;
			handler.player().y = 25;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		boolean s = false;
		if (e.getKeyCode() == KeyEvent.VK_W) {
			handler.setW_Down(s);
		}
		else if (e.getKeyCode() == KeyEvent.VK_A) {
			handler.setA_Down(s);
		}
		else if (e.getKeyCode() == KeyEvent.VK_S) {
			handler.setS_Down(s);
		}
		else if (e.getKeyCode() == KeyEvent.VK_D) {
			handler.setD_Down(s);
		}
		else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			//handler.player().velX = 4;
			//handler.player().velY = 10;
		}
		
	}
}

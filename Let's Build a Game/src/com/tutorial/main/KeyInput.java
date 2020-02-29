package com.tutorial.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// A class that manages keystroke events and updates the handler's keyStates
// Contains copied code.

public class KeyInput extends KeyAdapter {
	
	private Handler handler;
	// note: All key input is stored in the handler class!
	// This is because that is where it is used.
	public KeyInput(Handler handler) {
		this.handler = handler;
	}
	
//	Triggers when a key is depressed, and once every interval after.
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
			handler.setSpace_Down(s);
		}
		else if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
			handler.setShift_Down(s);
		}
		else if (e.getKeyCode() == KeyEvent.VK_1) {
			handler.setNum1_Down(s);
		}
		else if (e.getKeyCode() == KeyEvent.VK_2) {
			handler.setNum2_Down(s);
		}
		else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			handler.setEsc_Down(s);
		}
		else if (e.getKeyCode() == KeyEvent.VK_Q) {
			handler.setQ_Down(s);
		}
	}
	
//	Triggers when a key is released, and typically only once.
	public void keyReleased(KeyEvent e) {
		boolean s = false; // so that copy-pasting is faster.
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
			handler.setSpace_Down(s);
			handler.ignore_Space = false;
		}
		else if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
			handler.setShift_Down(s);
		}
		else if (e.getKeyCode() == KeyEvent.VK_1) {
			handler.setNum1_Down(s);
		}
		else if (e.getKeyCode() == KeyEvent.VK_2) {
			handler.setNum2_Down(s);
		}
		else if (e.getKeyCode() == KeyEvent.VK_Q) {
			handler.setQ_Down(s);
		}
	}
}

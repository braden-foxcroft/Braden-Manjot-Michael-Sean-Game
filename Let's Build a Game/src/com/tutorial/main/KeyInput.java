package com.tutorial.main;

import java.awt.Component;
import java.awt.MouseInfo;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
	
	private Handler handler;
	private Component canvas;
	
	public KeyInput(Handler handler, Component canvas) {
		this.handler = handler;
		this.canvas = canvas;
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
		else if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
			handler.player().velX = 0;
			handler.player().velY = 0;
			handler.player().x = MouseInfo.getPointerInfo().getLocation().x-canvas.getLocationOnScreen().x;
			handler.player().y = MouseInfo.getPointerInfo().getLocation().y-canvas.getLocationOnScreen().y;
		}
		else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			handler.player().velX = 4;
			handler.player().velY = 10;
		}
		
	}
}

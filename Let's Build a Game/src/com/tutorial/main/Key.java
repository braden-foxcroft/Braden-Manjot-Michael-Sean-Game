package com.tutorial.main;

import javafx.scene.input.KeyCode;

public class Key {
	private boolean pressed;
	private boolean justPressed;
	private KeyCode code;
	
//	Creates a key with a specific code
	public Key(KeyCode c) {
		this.code = c;
		this.release();
	}
	
//	Releases the current key
	public void release() {
		this.justPressed = false;
		this.pressed = false;
	}
	
//	Checks if it was just pressed.
//	Only returns true the first time checked during a press.
	public boolean justPressed() {
		boolean s = this.justPressed;
		this.justPressed = false;
		return s;
	}
	
//	Returns the key state
	public boolean isPressed() {
		return this.pressed;
	}
	
//	Presses it
	public void press() {
		if (!this.pressed) {
			this.justPressed = true;
			this.pressed = true;
		}
	}
	
//	Gets its code
	public KeyCode code() {
		return this.code;
	}
}

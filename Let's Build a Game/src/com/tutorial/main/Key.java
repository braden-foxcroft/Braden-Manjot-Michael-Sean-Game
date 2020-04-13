package com.tutorial.main;

import javafx.scene.input.KeyCode;

/**
 * An object representing the attributes of a single key
 */

public class Key {
	
	/**
	 * True if the key is currently pressed
	 */
	private boolean pressed;
	
	/**
	 * True if the key has been pressed, but it has not been checked whether the key was just pressed
	 */
	private boolean justPressed;
	
	/**
	 * The code of the key
	 */
	private KeyCode code;
	
	/**
	 * Creates a key with the given code
	 * @param c - the code of the created key
	 */
	public Key(KeyCode c) {
		this.code = c;
		this.release();
	}
	
	/**
	 * Informs the key that it was just released
	 */
	public void release() {
		this.justPressed = false;
		this.pressed = false;
	}
	
	/**
	 * Determines whether a key was just pressed. This will return true only when
	 * the key was just pressed, and
	 * justPressed() hasn't been called yet for that specific keypress
	 * @return
	 */
	public boolean justPressed() {
		boolean s = this.justPressed;
		this.justPressed = false;
		return s;
	}
	
	/**
	 * Checks if the key is currently pressed
	 * @return A boolean, true if the key is currently pressed
	 */
	public boolean isPressed() {
		return this.pressed;
	}
	
	/**
	 * Informs the key that it was just pressed
	 */
	public void press() {
		if (!this.pressed) {
			this.justPressed = true;
			this.pressed = true;
		}
	}
	
	/**
	 * Gets the code of the key
	 * @return The code of the key
	 */
	public KeyCode code() {
		return this.code;
	}
}

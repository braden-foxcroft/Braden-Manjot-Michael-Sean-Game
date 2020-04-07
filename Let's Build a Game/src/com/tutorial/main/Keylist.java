package com.tutorial.main;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * The key listener.
 * Contains a list of keys to store the state of.
 * This list is added to when the program checks the state of a new key.
 */

public class Keylist implements EventHandler<KeyEvent> {
	
	/**
	 * The list of keys whose states are being tracked
	 */
	ArrayList<Key> keys;
	
	/**
	 * Creates a new key listener
	 */
	public Keylist() {
		super();
		keys = new ArrayList<Key>();
	}
	
//	See the documentation for the implemented/overridden method
	public void handle(KeyEvent e) {
		if (e.getEventType() == KeyEvent.KEY_PRESSED) {
			press(e.getCode());
		} else if (e.getEventType() == KeyEvent.KEY_RELEASED) {
			release(e.getCode());
		}
	}
	
	/**
	 * Tries to inform the appropriate key that it has been pressed
	 * @param code - The code of the pressed key
	 */
	public void press(KeyCode code) {
		Key key = getKey(code);
		if (key != null) {
			key.press();
		}
	}
	
	/**
	 * Tries to inform the appropriate key that it has been released
	 * @param code - The code of the released key
	 */
	public void release(KeyCode code) {
		Key key = getKey(code);
		if (key != null) {
			key.release();
		}
	}
	
	/**
	 * Checks if a key has been pressed. If the key doesn't exist, it creates it.
	 * @param code - the code of the key to find
	 * @return A boolean, true if the key was found, and is pressed
	 */
	public boolean isPressed(KeyCode code) {
		Key k = getKey(code);
		if (k == null) {
			k = newKey(code);
		}
		return k.isPressed();
	}
	
	/**
	 * Checks if a key has just been pressed. If the key doesn't exist, it creates it.
	 * @param code - the code of the key to find
	 * @return A boolean, true if the key was found, and was just pressed
	 */
	public boolean justPressed(KeyCode code) {
		Key k = getKey(code);
		if (k == null) {
			k = newKey(code);
		}
		return k.justPressed();
	}
	
	/**
	 * Finds a key. If the key doesn't exist, it returns null.
	 * @param code - The code of the key to find
	 * @return The key found. Null if the key wasn't found
	 */
	public Key getKey(KeyCode code) {
		for (Key k:keys) {
			if (k.code() == code) {
				return k;
			}
		}
		return null;
	}
	
	/**
	 * Creates a new key, adds it to the list,
	 * and returns a pointer to it
	 * @param code - The code of the key to add
	 * @return The key that was created
	 */
	public Key newKey(KeyCode code) {
		Key k = new Key(code);
		keys.add(k);
		return k;
	}
	
}

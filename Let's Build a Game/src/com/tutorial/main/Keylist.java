package com.tutorial.main;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Keylist implements EventHandler<KeyEvent> {
	
	ArrayList<Key> keys;
	
//	Makes a new keylist
	public Keylist() {
		super();
		keys = new ArrayList<Key>();
	}
	
//	Handles key presses/releases
	public void handle(KeyEvent e) {
		if (e.getEventType() == KeyEvent.KEY_PRESSED) {
			press(e.getCode());
		} else if (e.getEventType() == KeyEvent.KEY_RELEASED) {
			release(e.getCode());
		}
	}
	
//	Presses a key
	public void press(KeyCode code) {
		Key key = getKey(code);
		if (key != null) {
			key.press();
		}
	}
	
//	Releases a key
	public void release(KeyCode code) {
		Key key = getKey(code);
		if (key != null) {
			key.release();
		}
	}
	
//	Checks if a key is pressed
	public boolean isPressed(KeyCode code) {
		Key k = getKey(code);
		if (k == null) {
			k = newKey(code);
		}
		return k.isPressed();
	}
	
//	Checks if a key has just been pressed
	public boolean justPressed(KeyCode code) {
		Key k = getKey(code);
		if (k == null) {
			k = newKey(code);
		}
		return k.justPressed();
	}
	
//	Finds the key in question.
//	If it doesn't exist, it returns null.
	public Key getKey(KeyCode code) {
		for (Key k:keys) {
			if (k.code() == code) {
				return k;
			}
		}
		return null;
	}
	
//	Makes a new key and adds it to the list.
	public Key newKey(KeyCode code) {
		Key k = new Key(code);
		keys.add(k);
		return k;
	}
	
}

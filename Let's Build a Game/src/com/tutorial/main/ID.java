package com.tutorial.main;

// An enumeration of different types of things that appear on screen.

/**
 * 
 * List of ID types that objects can be created as. 
 * Used to differentiate objects based on type for simplifying interactions
 *
 */
public enum ID {
	Player(),
	Enemy(),
	Obstacle(),
	Ball(),
	Bullet(),
	Item(),
	Trap(),
	Ally();
}

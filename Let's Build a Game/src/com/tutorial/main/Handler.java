package com.tutorial.main;

import java.awt.Component;
// import java.awt.Graphics;
import java.awt.MouseInfo;
import java.util.LinkedList;

// This class contains every GameObject, and the boolean state of each key.
// Its purpose is to handle interactions between objects.
// It also handles render and tick methods
// Contains copied code.

public class Handler {
	
	LinkedList<GameObject> object = new LinkedList<GameObject>();
	private int playerIndex = -1; // Used to track the index of the player. -1 means no player.
	private Component canvas; // Required to accurately get the mouse position.
	private boolean w_Down = false; // Key states. Stored for convenience.
	private boolean a_Down = false;
	private boolean s_Down = false;
	private boolean d_Down = false;
	private boolean space_Down = false;
	private static boolean check_Death = false; // a flag that means something is about to die.
	
//	Occurs every tick. Causes all objects to update and all collisions to occur.
	public void tick() {
//		Player actions
		if (playerIndex != -1) {
			if (this.w_Down) {this.player().accelY(-1);}
			if (this.a_Down) {this.player().accelX(-1);}
			if (this.s_Down) {this.player().accelY(1);}
			if (this.d_Down) {this.player().accelX(1);}
		}
//		update each object
		for (int i = 0; i < object.size(); i++)
		{
			GameObject tempObject = object.get(i);
			if (tempObject.id == ID.Ball) {tempObject.anchored = this.space_Down;}
			tempObject.tick(); // update all
		}
//		Begin collision checks
		for (int first = 0; first < object.size(); first++) {
			for (int second = first + 1; second < object.size(); second++) {
				GameObject a = object.get(first);
				GameObject b = object.get(second);
				if (a.distance(b) < (a.getRadius() + b.getRadius()))
				{
					if (a.anchored) {
						a = object.get(second);
						b = object.get(first);
					}
					a.hit(b);
					// collision checker.
				}
			}
		}
		// check for death, when needed
		if (check_Death) {
			for (int thing = 0; thing < object.size(); thing++) {
				GameObject a = object.get(thing);
				if (a.check_Death()) {
					removeObject(a);
					thing = 0;
				}
			}
		}
	}
	
//	Render each object.
	public void render(Display d) {
		for (int i = 0; i < object.size(); i++)
		{
			GameObject tempObject = object.get(i);
			tempObject.render(d);
		}
	}
	
//	Render each object for the text version.
	public void textRender(String[][] board) {
		if (TextGame.textGameActive) {
			System.out.println("hander Render");
			TextGame.padding++;
		}
		for (int i = 0; i < object.size(); i++)
		{
			GameObject tempObject = object.get(i);
			tempObject.textRender(board);
		}
	}
	
//	Create a new object. Block extra players from being made.
	public void addObject(GameObject o) {
		boolean debug = false;
		if (o.id == ID.Player) {
			if (this.playerIndex == -1) {
				this.object.add(o);
				this.playerIndex = this.object.size() - 1;
//				Only one player permitted!
//				Update the index of the player
				if (debug) {System.out.println("New " + o.id);}
			}
			else
			{
				if (debug) {System.out.println("Could not make new " + o.id);}
			}
		}
		else
		{
			this.object.add(o);
			if (debug) {System.out.println("New " + o.id);}
		}
	}
	
	
//	Removes an object from the game.
	public void removeObject(GameObject o) {
		this.object.remove(o);
		if (o.id == ID.Player) {
			this.playerIndex = -1;
		}
	}
	
//	Returns the player object, or null if it doesn't exist. Convenient.
	public GameObject player() {
		if (this.playerIndex == -1) {
			return null;
		}
		else
		{
			return this.object.get(playerIndex);
		}
	}

//	Getters and setters.
	public boolean isW_Down() {
		return w_Down;
	}

	public void setW_Down(boolean w_down) {
		this.w_Down = w_down;
	}

	public boolean isA_Down() {
		return a_Down;
	}

	public void setA_Down(boolean a_down) {
		this.a_Down = a_down;
	}

	public boolean isS_Down() {
		return s_Down;
	}

	public void setS_Down(boolean s_down) {
		this.s_Down = s_down;
	}

	public boolean isD_Down() {
		return d_Down;
	}

	public void setD_Down(boolean d_down) {
		this.d_Down = d_down;
	}
	
//	Get mouse info. Requires that canvas be properly set up.
	public int getMouseX() {
		return MouseInfo.getPointerInfo().getLocation().x
				- canvas.getLocationOnScreen().x;
		// gets the mouse position.
	}
	
	public int getMouseY() {
		return MouseInfo.getPointerInfo().getLocation().y
				- canvas.getLocationOnScreen().y;
		// gets the mouse position.
	}
	
//	Find the canvas. This should only be called once
	public void setCanvas(Component canvas) {
		this.canvas = canvas;
		// gets the canvas. Needed to get the mouse position.
	}

//	More getters and setters
	public boolean isSpace_Down() {
		return space_Down;
	}

	public void setSpace_Down(boolean space_Down) {
		this.space_Down = space_Down;
	}
	
//	Inform every handler that it is time to check for deaths. False positives are fine.
	public static void time_To_Die() {
		check_Death = true;
	}
	
}

package com.tutorial.main;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.util.LinkedList;

public class Handler {
	
	LinkedList<GameObject> object = new LinkedList<GameObject>();
	private int playerIndex = -1;
	private Component canvas;
	private boolean w_Down = false;
	private boolean a_Down = false;
	private boolean s_Down = false;
	private boolean d_Down = false;
	private boolean space_Down = false;
	
	
	public void tick() {
		if (playerIndex != -1) {
			if (this.w_Down) {this.player().accelY(-1);}
			if (this.a_Down) {this.player().accelX(-1);}
			if (this.s_Down) {this.player().accelY(1);}
			if (this.d_Down) {this.player().accelX(1);}
			// player actions
		}
		for (int i = 0; i < object.size(); i++)
		{
			GameObject tempObject = object.get(i);
			tempObject.tick(); // update all
		}
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
	}
	
	public void render(Graphics g) {
		for (int i = 0; i < object.size(); i++)
		{
			GameObject tempObject = object.get(i);
			tempObject.render(g);
		}
	}
	
	public void addObject(GameObject o) {
		if (o.id == ID.Player) {
			if (this.playerIndex == -1) {
				this.object.add(o);
				this.playerIndex = this.object.size() - 1;
				// only one player permitted!
				System.out.println("New " + o.id);
			}
			else
			{
				System.out.println("Could not make new " + o.id);
			}
		}
		else
		{
			this.object.add(o);
			System.out.println("New " + o.id);
		}
	}
	
	public void removeObject(GameObject o) {
		this.object.remove(o);
		if (o.id == ID.Player) {
			this.playerIndex = -1;
		}
	}
	
	public GameObject player() {
		if (this.playerIndex == -1) {
			return null;
		}
		else
		{
			return this.object.get(playerIndex);
		}
	}

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
	
	public void setCanvas(Component canvas) {
		this.canvas = canvas;
		// gets the canvas. Needed to get the mouse position.
	}
	
}

package com.tutorial.main;

import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {
	
	LinkedList<GameObject> object = new LinkedList<GameObject>();
	private int playerIndex = -1;
	private boolean w_Down = false;
	private boolean a_Down = false;
	private boolean s_Down = false;
	private boolean d_Down = false;
	
	
	public void tick() {
		if (playerIndex != -1) {
			if (this.w_Down) {this.player().accelY(-1);}
			if (this.a_Down) {this.player().accelX(-1);}
			if (this.s_Down) {this.player().accelY(1);}
			if (this.d_Down) {this.player().accelX(1);}
		}
		for(int i = 0; i < object.size(); i++)
		{
			GameObject tempObject = object.get(i);
			tempObject.tick();
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
		this.object.add(o);
		if (o.id == ID.Player) {
			this.playerIndex = this.object.size() - 1;
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
	
}

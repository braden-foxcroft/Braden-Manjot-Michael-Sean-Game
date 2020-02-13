package com.tutorial.main;

import java.awt.Graphics;

public abstract class GameObject {
	
	// abstract to represent all game objects (player and enemies)
	protected int x, y;
	protected ID id;
	protected int velX;
	protected int velY;
	
	public GameObject(int x, int y, ID id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	
	public void setX(int x) {
		this.setVelX(x);
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setId(ID id) {
		this.id = id;
	}
	public ID getId() {
		return id;
	}
	public int getVelX() {
		return velX;
	}
	public void setVelX(int velX) {
		this.velX = velX;
	}
	public int getVely() {
		return velY;
	}
	public void setVely(int vely) {
		velY = vely;
	}
	
}

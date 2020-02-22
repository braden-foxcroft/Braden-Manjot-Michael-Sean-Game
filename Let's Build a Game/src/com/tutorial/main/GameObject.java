package com.tutorial.main;

import java.awt.Graphics;

public abstract class GameObject {
	protected float x;
	protected float y;
	protected float velX;
	protected float velY;
	protected ID id;
	protected boolean anchored = false;
	protected int radius;
	
	public GameObject(int x, int y, ID id) {
		this.setX(x);
		this.setY(y);
		this.setId(id);
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	
	
	public void hit(GameObject other) {
		if (other.anchored) {
			
		}
		else
		{
			Vector vA = new Vector(this);
			Vector vB = new Vector(other);
			Vector dir = new Vector(this, other);
			vA.collide(vB, dir);
			System.out.println("collision!");
			this.setVelocity(vA);
			other.setVelocity(vB);
			System.out.println(vB);
		}
	}
	
	public float distance(GameObject other) {
		return (float)Math.sqrt(Math.pow(x - other.x,2) + Math.pow(y - other.y,2));
	}
	
	public void setVelocity(Vector v) {
		this.setVelX(v.x);
		this.setVelY(v.y);
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}
	
	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public float getVelX() {
		return velX;
	}

	public void setVelX(float velX) {
		this.velX = velX;
	}

	public float getVelY() {
		return velY;
	}

	public void setVelY(float velY) {
		this.velY = velY;
	}
	
	public void accelX(int x) {
		this.setVelX(this.getVelX()+x);
	}
	
	public void accelY(int y) {
		this.setVelY(this.getVelY()+y);
	}
	
	public int getRadius() {
		return this.radius;
	}
	
}

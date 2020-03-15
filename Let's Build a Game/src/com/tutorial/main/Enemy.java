package com.tutorial.main;

// import java.awt.Color;
// import java.awt.Graphics;

// An enemy. Moves toward you, tries to hit you. That is all, so far.
//Exclusively original code.

public class Enemy extends Character {
	
	private float accel = 0.2f; // how fast can it accelerate?
	
//	Makes an enemy.
	public Enemy(int x, int y, ID id, Handler handler) {
		super(x, y, id, handler);
		this.setVelX(0);
		this.setVelY(0);
		this.radius = 25;
	}
	
//	Acts once every tick
	public void tick() {
		this.drag();
		think();
		displace();
		this.constrain();
		this.skillUpdate();
	}
	
//	The AI of the enemy
	private void think() {
		Vector v;
		if (handler.player == null) {
			float xA = (float) (Game.WIDTH / 2) - x; // the direction the player is in
			float yA = (float) (Game.HEIGHT / 2) - y; // the direction the player is in
			v = new Vector(xA,yA);
		}
		else
		{
			v = new Vector(this,handler.player);
		}
		v = v.scaleAndCopy(0.01f);
		if (v.length() > 1) {
			v = v.scaleAndCopy(1 / v.length()); // limiting the acceleration.
		}
		this.accelX(v.x); // actually accelerating.
		this.accelY(v.y);
	}
	
//	Moves it, based on its velocity
	private void displace() {
		this.x += this.velX;
		this.y += this.velY;
	}
	
//	Slows it down proportional to its velocity
	private void drag() {
		if (!this.anchored) {
			this.setVelX(this.getVelX() * 0.99f);
			this.setVelY(this.getVelY() * 0.99f);
		}
	}
	
//	Accelerates in the x direction
	public void accelX(float multiple) {
		this.velX += multiple * this.getAccel();
	}

//	Accelerates in the y direction
	public void accelY(float multiple) {
		this.velY += multiple * this.getAccel();
	}
	
//	Get the acceleration constant
	public float getAccel() {
		return this.accel;
	}
	
	public void addTo(Handler h) {
//		TODO uncomment this to add enemies back in
		h.object.add(this);
		h.enemies.add(this);
	}
	
	public void removeFrom(Handler h) {
		h.object.remove(this);
		h.enemies.remove(this);
	}
	
//	Render it
	public void render(Display d) {
		if (this.invincible) {
			d.displayObject(DisplayID.EnemyInvincible, x, y, radius);
		} else {
			d.displayObject(DisplayID.Enemy, x, y, radius);
		}
		d.drawHealthBar(x, y, radius, health, Character.MAXHEALTH);
	}

}

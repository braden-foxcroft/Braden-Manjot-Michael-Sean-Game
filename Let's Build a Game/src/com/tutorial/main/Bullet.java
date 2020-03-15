package com.tutorial.main;

import java.util.Random;

//import java.awt.Color;
// import java.awt.Graphics;

// Creates a ball. This ball has friction, and can collide with stuff.
// Exclusively original code.

public class Bullet extends GameObject {
	
	private int lifeSpan;
	
//	creates a ball of radius 40. The handler should be the original handler you use.
	public Bullet(int x, int y, ID id, Handler handler) {
		super(x, y, id, handler);
		this.radius = 5;
		this.lifeSpan = 60 * 3;
	}
//	A routine that acts once a tick.
	public void tick() {
		this.drag();
		displace();
		this.constrain();
		this.lifeSpan--;
		if (check_Death()) {
			Handler.time_To_Die();
		}
	}
	
//	Overwrite the default behavior
	public boolean check_Death() {
		return this.lifeSpan < 0;
	}
	
	
//	display the object
	public void render(Display d) {
		d.displayObject(DisplayID.Enemy, x, y, radius);
	}
//	Fly around hitting stuff
	public void launchAround() {
		this.anchored = false;
		int max = 10;
		Random r = new Random();
		Vector v = new Vector(r.nextInt(max * 2) - max, r.nextInt(max * 2) - max);
		this.setVelocity(v);
	}
//	move the object according to its velocity
	private void displace() {
		this.x += this.velX;
		this.y += this.velY;
	}
//	slow the object proportional to its velocity
	private void drag() {
		if (!this.anchored) {
			float drag = 0.99f;
			this.setVelX(this.getVelX() * drag);
			this.setVelY(this.getVelY() * drag);
		}
	}
	
	public void onCollision(GameObject other) {
		
	}
	
//	does nothing, there because it's required.
	public void hitWall() {
		// do nothing
	}
	
}

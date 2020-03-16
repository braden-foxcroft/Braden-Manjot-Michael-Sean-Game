package com.tutorial.main;

//import java.awt.Color;
// import java.awt.Graphics;

// Creates a ball. This ball has friction, and can collide with stuff.
// Exclusively original code.

public class Ball extends GameObject {
	
	private int lifeSpan;
	
//	creates a ball of radius 40. The handler should be the original handler you use.
	public Ball(int x, int y, ID id, Handler handler) {
		super(x, y, id, handler);
		this.radius = 40;
		int lifeSpan = 30; // LifeSpan in seconds
		this.lifeSpan = 60 * lifeSpan;
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
	
	public void inheritRadius(GameObject other) {
		this.radius = other.radius;
	}
	
//	display the object
	public void render(Display d) {
		d.displayObject(DisplayID.Ball, x, y, radius);
	}
	
//	Fly around hitting stuff
	public void launchAround(Vector dir) {
		this.anchored = true;
		this.setVelocity(dir);
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
	
	public void addTo(Handler h) {
		h.object.add(this);
		h.movingStuff.add(this);
	}
	
	public void removeFrom(Handler h) {
		h.object.remove(this);
		h.movingStuff.remove(this);
	}
	
	
	public void onCollision(GameObject other) {
		
	}
	
//	does nothing; there because it's required.
	public void hitWall() {
		// do nothing
	}
	
}

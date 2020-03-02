package com.tutorial.main;

//import java.awt.Color;
// import java.awt.Graphics;

// Creates a ball. This ball has friction, and can collide with stuff.
// Exclusively original code.

public class Ball extends GameObject {
	
//	creates a ball of radius 40. The handler should be the original handler you use.
	public Ball(int x, int y, ID id, Handler handler) {
		super(x, y, id, handler);
		this.radius = 40;
	}
//	A routine that acts once a tick.
	public void tick() {
		this.drag();
		displace();
		this.constrain();
	}
//	display the object
	public void render(Display d) {
		d.displayObject(DisplayID.Enemy, x, y, radius);
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

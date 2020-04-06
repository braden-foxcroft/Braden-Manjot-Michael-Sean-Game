package com.tutorial.main;

import com.tutorial.display.Display;
import com.tutorial.display.DisplayID;

//import java.awt.Color;
// import java.awt.Graphics;

// Creates a ball. This ball has friction, and can collide with stuff.
// Exclusively original code.

// TODO Comments by Sean

public class Ball extends GameObject {
	
	private int lifeSpan;
	
/**	
 * Creates a ball of radius 40. The handler should be the original handler you use.
 * @param x The x coordinate to create it at
 * @param y The y coordinate to create it at
 * @param id The id (enemy type) of the object
 * @param handler The instance of the handler the game uses
 */
	public Ball(int x, int y, ID id, Handler handler) {
		super(x, y, id, handler);
		this.radius = 40;
		int lifeSpan = 30; // LifeSpan in seconds
		this.lifeSpan = 60 * lifeSpan;
	}
	
	public void tick() {
		this.drag();
		displace();
		this.constrain();
		this.lifeSpan--;
		if (check_Death()) {
			Handler.time_To_Die();
		}
	}
	
	public boolean check_Death() {
		return this.lifeSpan < 0;
	}
	
	/**
	 * Sets the radius to the radius of other
	 * @param other The object to inherit the radius from
	 */
	public void inheritRadius(GameObject other) {
		this.radius = other.radius;
	}
	
	public void render(Display d) {
		d.displayObject(DisplayID.Ball, x, y, radius);
	}
	
	/**	
	 * Gives the Ball a direction and a velocity, and anchors it. 
	 * @param dir Direction of the vector being passed in
	 */
	public void launchAround(Vector dir) {
		this.anchored = true;
		this.setVelocity(dir);
	}
	
	/**
	 * Offsets position based on current velocity
	 */
	private void displace() {
		this.x += this.velX;
		this.y += this.velY;
	}
	
	/**
	 *  Applies a drag force to the object
	 */
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
	
	/**	removes objects from their respective / appropriate lists in the handler
	 * 
	 */
	public void removeFrom(Handler h) {
		h.object.remove(this);
		h.movingStuff.remove(this);
	}
	
	
	public void onCollision(GameObject other) {
		
	}
	
	public void hitWall() {
		// do nothing
	}
	
}

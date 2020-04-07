package com.tutorial.main;

import com.tutorial.display.Display;
import com.tutorial.display.DisplayID;

/**
 * A ball, which can move around the screen and hit stuff.
 */

public class Ball extends GameObject {
	
	/**
	 * An integer value representing the number of ticks that an object will remain on-screen for.
	 */
	private int lifeSpan;
	
	/**	
	 * Creates a ball of radius 40. The handler should be the original handler you use.
	 * @param x - The x coordinate to create it at
	 * @param y - The y coordinate to create it at
	 * @param id - The id (enemy type) of the object
	 * @param handler - The instance of the handler the game uses
	 */
	public Ball(int x, int y, ID id, Handler handler) {
		super(x, y, id, handler);
		this.radius = 40;
		int lifeSpan = 30; // LifeSpan in seconds
		this.lifeSpan = 60 * lifeSpan;
	}
	
//	See the documentation for the implemented/overridden method
	public void tick() {
		this.drag();
		displace();
		this.constrain();
		this.lifeSpan--;
		if (check_Death()) {
			Handler.time_To_Die();
		}
	}

//	See the documentation for the implemented/overridden method
	public boolean check_Death() {
		return this.lifeSpan < 0;
	}
	
	/**
	 * Sets the radius of this to the radius of other
	 * @param other - The object to inherit the radius from
	 */
	public void inheritRadius(GameObject other) {
		this.radius = other.radius;
	}
	
//	See the documentation for the implemented/overridden method
	public void render(Display d) {
		d.displayObject(DisplayID.Ball, x, y, radius);
	}
	
	/**	
	 * Gives the ball a velocity, and anchors it. 
	 * @param dir - Direction of the vector being passed in
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
	 * Applies a drag force to the object
	 */
	private void drag() {
		if (!this.anchored) {
			float drag = 0.99f;
			this.setVelX(this.getVelX() * drag);
			this.setVelY(this.getVelY() * drag);
		}
	}
	
//	See the documentation for the implemented/overridden method
	public void addTo(Handler h) {
		h.object.add(this);
		h.movingStuff.add(this);
	}
	
//	See the documentation for the implemented/overridden method
	public void removeFrom(Handler h) {
		h.object.remove(this);
		h.movingStuff.remove(this);
	}
	
//	See the documentation for the implemented/overridden method
	public void onCollision(GameObject other) {
		// Do nothing
	}
	
//	See the documentation for the implemented/overridden method
	public void hitWall() {
		// Do nothing
	}
	
}

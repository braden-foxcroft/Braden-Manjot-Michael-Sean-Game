package com.tutorial.main;

import com.tutorial.display.Display;
import com.tutorial.display.DisplayID;

/**
 * A bullet, with a finite lifespan, that cannot collide with anything
 * for the first few moments of its life.
 */

public class Bullet extends GameObject {
	
	/**
	 * an integer value representing the time that an object will remain on screen for.
	 */	
	protected int lifeSpan;
	
	/**	
	 * Creates a bullet of radius 5.
	 * @param x - The x coordinate to create it at
	 * @param y - The y coordinate to create it at
	 * @param id - The id (enemy type) of the object
	 * @param handler - The instance of the handler the game uses
	 */
	public Bullet(int x, int y, ID id, Handler handler) {
		super(x, y, id, handler);
		this.radius = 5;
		this.lifeSpan = (60) * 5; // 5 second lifespan, invisible for the first second
		this.canTouch = false;
	}
	
//	See the documentation for the implemented/overridden method
	public void tick() {
		displace();
		this.constrain();
		this.lifeSpan--;
		if (this.lifeSpan < (60) * 4.5)
		{
			this.canTouch = true;
		}
		if (check_Death()) {
			Handler.time_To_Die();
		}
	}
	
//	See the documentation for the implemented/overridden method
	public boolean check_Death() {
		return this.lifeSpan < 0;
	}
	
//	See the documentation for the implemented/overridden method
	public void render(Display d) {
		if (this.canTouch) {
			d.displayObject(DisplayID.Bullet, x, y, radius);
		} else {
			d.displayObject(DisplayID.BulletUntouchable, x, y, radius);
		}
	}
	
	/**	
	 * Gives the Bullet a velocity.
	 * @param dir - direction of the vector to use
	 */
	public void launchAround(Vector dir) {
		this.setVelocity(dir);
	}
	
	/**
	 * Updates its position
	 */
	protected void displace() {
		this.x += this.velX;
		this.y += this.velY;
	}
	
	/**
	 * Applies a drag force
	 */
	protected void drag() {
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
		// do nothing
	}
	
}

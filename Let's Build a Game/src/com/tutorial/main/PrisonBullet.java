package com.tutorial.main;

import com.tutorial.display.Display;
import com.tutorial.display.DisplayID;

/**
 * A bullet that is untouchable for about a second,
 * and then becomes an immovable obstacle for a few seconds
 *
 */

public class PrisonBullet extends Bullet {
	
	/**
	 * An integer representing the time that this object will be rendered for.
	 * Decrements in tick() method
	 */
	private int maxLife = (60) * 8;
	
	/**
	 * Creates a prison bullet
	 * @param x - The x value to create the bullet at
	 * @param y - The y value to create the bullet at
	 * @param id - The ID of the bullet
	 * @param handler - The handler that contains the PrisonBullet
	 */
	public PrisonBullet(int x, int y, ID id, Handler handler) {
		super(x, y, id, handler);
		this.lifeSpan = maxLife;
	}
	
//	See the documentation for the implemented/overridden method
	public void tick() {
		displace();
		this.constrain();
		this.lifeSpan--;
		if (this.lifeSpan < maxLife - 30)
		{
			this.canTouch = true;
			this.anchored = true;
			this.damaging = true;
			this.setVelX(0);
			this.setVelY(0);
		}
		if (check_Death()) {
			Handler.time_To_Die();
		}
	}
	
//	See the documentation for the implemented/overridden method
	public void addTo(Handler h) {
		h.obstacles.add(this);
		h.object.add(this);
	}
	
//	See the documentation for the implemented/overridden method
	public void removeFrom(Handler h) {
		h.obstacles.remove(this);
		h.object.remove(this);
	}

//	See the documentation for the implemented/overridden method
	public void render(Display d) {
		if (this.canTouch) {
			d.displayObject(DisplayID.PrisonBullet, x, y, radius);
		} else {
			d.displayObject(DisplayID.PrisonBulletUntouchable, x, y, radius);
		}
	}
}

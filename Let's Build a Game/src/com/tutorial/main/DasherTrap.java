package com.tutorial.main;

import java.util.Random;

import com.tutorial.display.Display;
import com.tutorial.display.DisplayID;

/**
 * A neutral object that will target the character who 
 * activated it, wait, then dash at said character. This object only takes damage
 * once per use of its dash skill.
 */

public class DasherTrap extends Trap {
	
	/**
	 * The game object that the DasherTrap will aim at
	 */
	private GameObject target;
	
	/**
	 * The max length that the trap will dash forward
	 */
	private int range = 1000;
	
	/**
	 * an integer value representing the time this trap is currently waiting
	 * before dashing again
	 */
	
	private int wait = 0;
	
	/**
	 * An instance of the random class
	 */
	private Random r = new Random();
	
	/** 
	 * Constructor for DashTrap
	 * 
	 * @param x - initial X coordinate on the canvas
	 * @param y - initial Y coordinate on the canvas
	 * @param id - Identifier for use in display and handler
	 * @param handler - the list that will be used to render and display the object
	 * @param aRadius - The radius of the DashTrap object that is spawned
	 * @param target - The character that activated the trap
	 */
	public DasherTrap(int x, int y, ID id, Handler handler, int aRadius, GameObject target) {
		super(x, y, id, handler, aRadius);
		this.target = target;
		health = r.nextInt(5) + 1;
		wait = (60) * 3;
		this.damaging = false;
		this.dashSpeed = 10;
	}

//	See the documentation for the implemented/overridden method
	public void tick() {
		think();
		displace();
		constrain();
		skillUpdate();
	}
	
	/**	
	 * Offsets position based on current velocity
	 */	
	public void displace() {
		this.x += velX;
		this.y += velY;
	}
	
//	See the documentation for the implemented/overridden method
	public void onCollision(GameObject other) {
		
	}
	
//	See the documentation for the implemented/overridden method
	public void hitWall() {
		this.skillReset();
	}
	
//	See the documentation for the implemented/overridden method
	public void hurt(int damage) {
//		DashTraps should not take damage in this way.
	}
	
	/** 
	 * Completes its wait period, then triggers a dash of randomized length toward the target
	 */
	public void think() {
		if (this.currentSkill.equals("dash")) {
			return;
		}
		if (this.wait > 0)
		{
			this.wait--;
			return;
		}
//		Start next dash
		this.wait = (60) * 3;
		Vector v = new Vector(this, target);
		v = v.unitVector(r.nextInt(range) + 500);
		v = v.add(new Vector(this, 0));
		this.startSkill("dash", v.x, v.y);
		health--;
		if (check_Death()) {
			Handler.time_To_Die();
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
	public boolean check_Death() {
		return this.health <= 0;
	}
	
//	See the documentation for the implemented/overridden method
	public float getDashSpeed() {
		return this.dashSpeed;
	}
	
//	See the documentation for the implemented/overridden method
	public void render(Display d) {
		d.displayObject(DisplayID.DasherTrap, x, y, radius);
		d.drawHealthBar(x, y, radius, health, MAXHEALTH);
	}
	
}

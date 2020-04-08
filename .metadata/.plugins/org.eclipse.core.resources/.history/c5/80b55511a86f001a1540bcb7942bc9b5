package com.tutorial.main;

import java.util.Random;

/**
 * 
 * Dasher Trap class Spawns a neutral object that will target the character who 
 * activated it, wait, then dash at said character. This object only takes damage
 * when it uses its dash skill. 
 *
 */
public class DasherTrap extends Trap {
	
	private GameObject target;
	private int range = 1000;
	private int wait = 0;
	private java.util.Random r = new Random();
	
	/** Constructor for DashTrap
	 * 
	 * @param x = initial X coordinate on the canvas
	 * @param y = initial Y coordinate on the canvas
	 * @param id = Identifier for use in display and handler
	 * @param handler = the list that will be used to render and display the object
	 * @param aRadius = The radius of the DashTrap object that is spawned
	 * @param target = The character that activated the trap
	 */
	public DasherTrap(int x, int y, ID id, Handler handler, int aRadius, GameObject target) {
		super(x, y, id, handler, aRadius);
		this.target = target;
		health = r.nextInt(5) + 1;
		wait = (60) * 3;
		this.damaging = false;
		this.dashSpeed = 10;
	}
/**	A routine that acts once a tick.
 * All of the calls that must be called per tick of the game.
 */	
	public void tick() {
		think();
		displace();
		constrain();
		skillUpdate();
	}
	
	public void displace() {
		this.x += velX;
		this.y += velY;
	}
	
	public void onCollision(GameObject other) {
		
	}
	
	public void hitWall() {
		this.skillReset();
	}
	
	public void hurt(int damage) {
//		Nope, no damage here!
	}
	
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
	
//	adds objects to their respective / appropriate lists in the handler
	public void addTo(Handler h) {
		h.object.add(this);
		h.movingStuff.add(this);
	}
	
//	removes objects from their respective / appropriate lists in the handler	
	public void removeFrom(Handler h) {
		h.object.remove(this);
		h.movingStuff.remove(this);
	}
	
	public boolean check_Death() {
		return this.health <= 0;
	}
	
	public float getDashSpeed() {
		return this.dashSpeed;
	}
	
	public void render(Display d) {
		d.displayObject(DisplayID.DasherTrap, x, y, radius);
		d.drawHealthBar(x, y, radius, health, MAXHEALTH);
	}
	
}

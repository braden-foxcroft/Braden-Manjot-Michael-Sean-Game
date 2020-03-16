package com.tutorial.main;

import java.util.Random;

public class DasherTrap extends Trap {
	
	private GameObject target;
	private int range = 1000;
	private int wait;
	private java.util.Random r = new Random();
	
	public DasherTrap(int x, int y, ID id, Handler handler, int aRadius, GameObject target) {
		super(x, y, id, handler, aRadius);
		this.target = target;
		health = r.nextInt(5) + 1;
		wait = (60) * 3;
		this.damaging = false;
		this.dashSpeed = 10;
	}
	
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

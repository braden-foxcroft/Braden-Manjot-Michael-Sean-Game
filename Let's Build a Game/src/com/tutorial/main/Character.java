package com.tutorial.main;
// And abstract class for all characters. Each character can:
// Move itself around
// Use skills
// Die and take damage
// Exclusively original code.

public abstract class Character extends GameObject {
	
	public static final int MAXHEALTH = 5;
	public int explodeRadius = 30; // The radius of explosions
	private String currentSkill = "";
	private float currentParam1 = 0f;
	private float currentParam2 = 0f;
	
	
//	Implement a character.
	public Character(int x, int y, ID id, Handler handler) {
		super(x, y, id, handler);
	}

	protected long struck = 0; // The point in time when being invincible wears off
	protected boolean invincible = false; // whether you are invincible after taking damage
	protected int health = Character.MAXHEALTH; // your health. 0=death.
	
//	Checks if you should stop being invincible.
	public void invincibleUpdate() {
		if (this.invincible) {
			if (System.currentTimeMillis() >= this.struck) {
				this.struck = 0;
				this.invincible = false;
				if (this.check_Death()) {Handler.time_To_Die();}
			}
		}
	}
	
//	Executes skills
	public void skillUpdate() {
		this.invincibleUpdate();
		if (!this.currentSkill.equals("")) {
			doCurrentSkill();
		}
	}
	
	public void doCurrentSkill() {
		doSkill(currentSkill, currentParam1, currentParam2);
		if (currentSkill.equals("dash")) {
			Vector a = new Vector(this, 0);
			Vector b = new Vector(currentParam1,currentParam2);
			if (a.add(b.negate()).length() <= 20) {
				Vector vel = new Vector(this);
				this.setVelocity(vel.scaleAndCopy(0.4f));
				skillReset();
			}
		}
		else if (currentSkill.equals("stuff")) {
			// TODO add more skills
		}
		
	}
	
	public void startSkill(String skillName, float param1, float param2) {
		this.currentSkill = skillName;
		this.currentParam1 = param1;
		this.currentParam2 = param2;
	}
	
	public void skillReset() {
		this.currentSkill = "";
		this.currentParam1 = 0;
		this.currentParam2 = 0;
		this.anchored = false;
	}
	
//	Performs skills, as needed
//	Can be overridden, but overrides must call this.
	public void doSkill(String skillName, float param1, float param2) {
		// TODO Braden, do this.
		if (skillName.equals("heal")) {
			this.heal((int)param1);
		}
		else if (skillName.equals("maxHeal")) {
			this.maxHeal();
		}
		else if (skillName.equals("dash")) {
			this.dash(param1, param2);
		}
		else if (skillName.equals("explode")) {
			this.explode();
		}
		
		
	}

	public void onCollision(GameObject other) {
		if (this.currentSkill.equals("dash")) {
			this.skillReset();
			Vector o = new Vector(other);
			o = o.add(new Vector(this));
			other.setVelocity(o);
			this.setVelocity(new Vector(0,0));
		}
		
	}
	
	public void anyCollision() {
		if (this.currentSkill.equals("dash")) {
			this.skillReset();
		}
		
	}
	
//	Heals a character by 1 health, returns true if now at max health.
	public boolean heal(int healBy) {
		this.health += healBy;
		if (this.health >= Character.MAXHEALTH) {
			this.health = Character.MAXHEALTH;
			return true;
		}
		return false;
	}
	
//	
	public void maxHeal() {
		this.health = Character.MAXHEALTH;
	}
	
	public void dash(float x, float y) {
		
	}
	
	public void explode() {
		
	}
	
//	For when it hits something damaging
	public void hitWall() {
		if (!this.invincible) {
			if (TextGame.textGameActive) {
				System.out.println("damage taken by:" + this.id);
				TextGame.padding++;
			}
			this.struck = System.currentTimeMillis() + 1000;
			this.invincible = true;
			this.health -= 1;
		}
		this.anyCollision();
	}
	
//	Returns a boolean saying if the object should be removed this frame.
	public boolean check_Death() {
		return (this.health <= 0) && !this.invincible;
	}
	
	
}

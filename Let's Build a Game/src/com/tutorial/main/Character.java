package com.tutorial.main;


/**
 * An abstract class for all characters. Each character can 
 * move itself around,
 * use skills,
 * die,
 * and take damage.
 */

public abstract class Character extends GameObject {
	
	/**
	 * The maximum health any character can have
	 */
	public static final int MAXHEALTH = 5;
	
	/**
	 * The default explosion radius (unused)
	 */
	public int explodeRadius = 30;
	
	/**
	 * The default speed of dashing
	 */
	public float dashSpeed = 20;
	
	/**
	 * The skill currently in progress
	 */
	protected String currentSkill = "";
	
	/**
	 * The current param1 of the current skill
	 */
	protected float currentParam1 = 0f;
	
	/**
	 * The current param2 of the current skill
	 */
	protected float currentParam2 = 0f;
	
	/**
	 * The point in time when being invincible wears off
	 */
	protected long invulnTime = 0;
	
	/**
	 * Whether the character is currently invincible
	 */
	protected boolean invincible = false;
	
	/**
	 * The current health of the character.
	 */
	protected int health = Character.MAXHEALTH;
	
	/**	
	 * Create a character
	 * @param x - initial X coordinate of the Character being spawned
	 * @param y - initial Y coordinate of the Character being spawned
	 * @param id - The identifier tag for the handling and display / rendering
	 * @param handler - The list that will be used for handling, rending and display
	 */
	public Character(int x, int y, ID id, Handler handler) {
		super(x, y, id, handler);
	}
	
	/**
	 * Checks if you should stop being invincible, and enforces it when needed.
	 */
	public void invincibleUpdate() {
		if (this.invincible) {
			if (System.currentTimeMillis() >= this.invulnTime) {
				this.invulnTime = 0;
				this.invincible = false;
				if (this.check_Death()) {Handler.time_To_Die();}
			}
		}
	}
	
	/**	
	 * Executes the current skill
	 */
	public void skillUpdate() {
		this.invincibleUpdate();
		if (!this.currentSkill.equals("")) {
			doCurrentSkill();
		}
	}
	
	/** 
	 * Executes currentSkill.
	 * It will also end the current skill, when needed.
	 */
	public void doCurrentSkill() {
		doSkill(currentSkill, currentParam1, currentParam2);
		if (currentSkill.equals("dash")) {
			Vector a = new Vector(this, 0);
			Vector b = new Vector(currentParam1,currentParam2);
			if (a.add(b.negate()).length() <= dashSpeed + 1) {
				Vector vel = new Vector(this);
				this.setVelocity(vel.scaleAndCopy(0.4f));
				skillReset();
			}
		}
	}
	
	/** 
	 * startSkill starts the execution of a skill. This execution will continue to happen
	 * until the skill is instructed to stop.
	 * 
	 * @param skillName - The String representation of the skill being added
	 * @param param1 - differing float values, assigned differently by each skill
	 * @param param2 - differing float values, assigned differently by each skill
	 */
	public void startSkill(String skillName, float param1, float param2) {
		this.currentSkill = skillName;
		this.currentParam1 = param1;
		this.currentParam2 = param2;
	}
	
	/** 
	 * Ends the current skill.
	 */
	public void skillReset() {
		this.currentSkill = "";
		this.currentParam1 = 0;
		this.currentParam2 = 0;
		this.anchored = false;
	}
	
	/**	
	 * Performs the given skill.
	 * 
	 * @param skillName - The String representation of the skill being added
	 * @param param1 - differing float values, assigned differently by each skill
	 * @param param2 - differing float values, assigned differently by each skill
	 * 
	 */
	public void doSkill(String skillName, float param1, float param2) {
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
		else if (skillName.equals("hurt")) {
			this.hurt((int)param1);
		}
		else if (skillName.equals("invuln")) {
			this.invuln(param1);
		}
		
		
	}

//	See the documentation for the implemented/overridden method
	public void onCollision(GameObject other) {
		
	}
	
	/**
	 * Triggers whenever the character collides with something (including a wall)
	 */
	public void anyCollision() {
		
	}
	
	/**	
	 * Heals a character by the given number of health, returns true if now at max health.
	 * 
	 * @param healBy - integer value that the health will be increased by
	 * 
	 * @return Boolean value for if the character has full health or not
	 */
	public boolean heal(int healBy) {
		this.health += healBy;
		if (this.health >= Character.MAXHEALTH) {
			this.health = Character.MAXHEALTH;
			return true;
		}
		return false;
	}
	
	/** 
	 * Decrements the players remaining health, killing the character when health reaches zero.
	 * 
	 * @param damage - integer representation of the amount of health that will be lost
	 */
	public void hurt(int damage) {
		if (this.invincible)
		{
			return;
		}
		this.health -= damage;
		if (this.health <= 0)
		{
			Handler.time_To_Die();
		}
		return;
	}
	
	/** 
	 * Makes the character invincible for the given duration
	 * 	
	 * @param time - the amount of time your character will remain invulnerable
	 */
	public void invuln(float time) {
		if (invincible) {
			return;
		}
		this.invulnTime = (long) System.currentTimeMillis() + (long)time * 1000;
		this.invincible = true;
	}
	
	/**	
	 * restores a characters health to maximum
	 */
	public void maxHeal() {
		this.health = Character.MAXHEALTH;
	}
	
	/** 
	 * Triggers a dash skill which moves the character to the given coordinates
	 * 	
	 * @param x - X coordinate of dash destination
	 * @param y - Y coordinate of the dash destination
	 */
	public void dash(float x, float y) {
		this.anchored = true;
		Vector v = new Vector(x, y);
		Vector move = new Vector(this,v);
		move = move.unitVector(this.getDashSpeed());
		this.setVelocity(move);
	}
	
	/**
	 * Triggers an explosion
	 * @deprecated (But actually never implemented in the first place)
	 */
	public void explode() {
		
	}
	
//	See the documentation for the implemented/overridden method
	public void hitWall() {
		if (!this.invincible) {
			if (TextGame.textGameActive) {
				System.out.println("damage taken by:" + this.id);
				TextGame.padding++;
			}
			this.hurt(1);
			this.invuln(1.0f);
		}
		this.anyCollision();
		if (this.currentSkill.equals("dash")) {
			this.skillReset();
		}
	}
	
//	See the documentation for the implemented/overridden method
	public boolean check_Death() {
		return (this.health <= 0) && !this.invincible;
	}
	
	/**
	 * Gets the speed at which the character should dash.
	 * @return The dash speed
	 */
	public float getDashSpeed() {
		return this.dashSpeed;
	}
	
//	See the documentation for the implemented/overridden method
	public String toString() {
		String result = "";
		result += this.id; 
		result += ",";
		result += this.radius;
		result += ",";
		result += this.x;
		result += ",";
		result += this.y;
		result += ",";
		result += this.velX;
		result += ",";
		result += this.velY;
		result += ",";
		result += this.currentSkill;
		result += ",";
		result += this.health;
		result += ",";
		
		return result;
	}
	
}

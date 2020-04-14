package com.tutorial.main;
// And abstract class for all characters. Each character can:
// Move itself around
// Use skills
// Die and take damage
// Exclusively original code.

public abstract class Character extends GameObject {
	
	public static final int MAXHEALTH = 5;
	public int explodeRadius = 30; // The radius of explosions
	public float dashSpeed = 20; // The speed of dashing
	protected String currentSkill = "";
	protected float currentParam1 = 0f;
	protected float currentParam2 = 0f;
	protected long invulnTime = 0; // The point in time when being invincible wears off
	protected boolean invincible = false; // whether you are invincible after taking damage
	protected int health = Character.MAXHEALTH; // your health. 0=death.
	
/**	Implement a character.
 * 
 * @param x = initial X coordinate of the Character being spawned
 * @param y = initial Y coordinate of the Character being spawned
 * @param id = The identifier tag for the handling and display / rendering
 * @param handler = The list that will be used for handling, rending and display
 */
	public Character(int x, int y, ID id, Handler handler) {
		super(x, y, id, handler);
	}
	
/**	Checks if you should stop being invincible.
 * 
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
	
/**	Executes skills
 * 
 */
	public void skillUpdate() {
		this.invincibleUpdate();
		if (!this.currentSkill.equals("")) {
			doCurrentSkill();
		}
	}
/** doCurrentSkill will execute a skill based on the parameters currently being met in the doSkill method
 * 	
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
		else if (currentSkill.equals("stuff")) {
			// TODO Braden, add more skills
		}
		
	}
/** startSkill assigns an available skill as instance variables to be used 
 *  by the class during activation and execution of said skill.
 * 	
 * @param skillName = The String representation of the skill being added
 * @param param1 = differing float values, assigned differently by each skill
 * @param param2 = differing float values, assigned differently by each skill
 */
	public void startSkill(String skillName, float param1, float param2) {
		this.currentSkill = skillName;
		this.currentParam1 = param1;
		this.currentParam2 = param2;
	}
/** resets the instance variable values to empty / base, removing any skills that were previously in them.
 * 	
 */
	public void skillReset() {
		this.currentSkill = "";
		this.currentParam1 = 0;
		this.currentParam2 = 0;
		this.anchored = false;
	}
	
/**	Performs skills, as needed, can be overridden, but overrides must call this.
 * 
 * @param skillName = The String representation of the skill being added
 * @param param1 = differing float values, assigned differently by each skill
 * @param param2 = differing float values, assigned differently by each skill
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

	public void onCollision(GameObject other) {
		
	}
	
	public void anyCollision() {
		
	}
	
/**	Heals a character by 1 health, returns true if now at max health.
 * 
 * @param healBy = integer value that the health will be increased by
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
 * 	
 * @param damage
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
	
	public void invuln(float time) {
		if (invincible) {
			return;
		}
		this.invulnTime = (long) System.currentTimeMillis() + (long)time * 1000;
		this.invincible = true;
	}
	
//	full heal
	public void maxHeal() {
		this.health = Character.MAXHEALTH;
	}
	
	public void dash(float x, float y) {
		this.anchored = true;
		Vector v = new Vector(x, y);
		Vector move = new Vector(this,v);
		move = move.unitVector(this.getDashSpeed());
		this.setVelocity(move);
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
			this.hurt(1);
			this.invuln(1.0f);
		}
		this.anyCollision();
		if (this.currentSkill.equals("dash")) {
			this.skillReset();
		}
	}
	
//	Returns a boolean saying if the object should be removed this frame.
	public boolean check_Death() {
		return (this.health <= 0) && !this.invincible;
	}
	
	public float getDashSpeed() {
		return this.dashSpeed;
	}
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
		result += ",\n";
		
		return result;
	}
	
}

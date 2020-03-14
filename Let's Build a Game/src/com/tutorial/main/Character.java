package com.tutorial.main;
// And abstract class for all characters. Each character can:
// Move itself around
// Use skills
// Die and take damage
// Exclusively original code.

public abstract class Character extends GameObject {
	
	public static final int MAXHEALTH = 5;
	
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
		
	}
	
//	Performs skills, as needed
	public void doSkill(String skillname, float x, float y) {
		// TODO Braden, do this.
		
	}
	

	public void onCollision(GameObject other) {
		
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
		this.onCollision(null);
	}
	
//	Returns a boolean saying if the object should be removed this frame.
	public boolean check_Death() {
		return (this.health <= 0) && !this.invincible;
	}
	
	
}

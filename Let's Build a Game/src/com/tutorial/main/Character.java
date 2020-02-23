package com.tutorial.main;

public abstract class Character extends GameObject {
	
	public Character(int x, int y, ID id, Handler handler) {
		super(x, y, id, handler);
	}

	protected long struck = 0;
	protected boolean invincible = false;
	protected int health = 2;
	
	public void invincibleUpdate() {
		if (this.invincible) {
			if (System.currentTimeMillis() >= this.struck) {
				this.struck = 0;
				this.invincible = false;
				if (this.check_Death()) {Handler.time_To_Die();}
			}
		}
	}
	
	public void skillUpdate() {
		this.invincibleUpdate();
		
	}
	
	public void hitWall() {
		if (!this.invincible) {
			this.struck = System.currentTimeMillis() + 1000;
			this.invincible = true;
			this.health -= 1;
		}
	}
	
	public boolean check_Death() {
		return (this.health <= 0) && !this.invincible;
	}
	
	
}

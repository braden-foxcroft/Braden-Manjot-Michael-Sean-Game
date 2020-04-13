package com.tutorial.main;

import com.tutorial.display.Display;

import com.tutorial.display.DisplayID;

/**
 * A computer-controlled AI character, that tries to kill Allies and the Player
 */

public class Enemy extends Character {
	
	/**
	 * A constant representing how fast the character can accelerate
	 */
	private float accel = 0.2f;
	
	/**
	 * A pointer to the current target the bot is trying to kill
	 */
	private GameObject target;
	
	/**
	 * Makes an enemy.
	 * @param x - The x coordinate to create it at
	 * @param y - The y coordinate to create it at
	 * @param id - The id (enemy type) of the object
	 * @param handler - The instance of the handler the game uses
	 */
	public Enemy(int x, int y, ID id, Handler handler) {
		super(x, y, id, handler);
		this.setVelX(0);
		this.setVelY(0);
		this.radius = 25;
	}
	
	/**
	 * Makes an ally.
	 * @param x - The x coordinate to create it at
	 * @param y - The y coordinate to create it at
	 * @param id - The id (enemy type) of the object
	 * @param velX - The starting x velocity
	 * @param velY - The starting y velocity
	 * @param handler - The instance of the handler the game uses
	 */
	public Enemy(int x, int y, ID id, int velX, int velY, Handler handler) {
		super(x, y, id, handler);
		this.setVelX(velX);
		this.setVelY(velY);
		this.radius = 25;
	}
	
	/**
	 * Makes an ally.
	 * @param x - The x coordinate to create it at
	 * @param y - The y coordinate to create it at
	 * @param id - The id (enemy type) of the object
	 * @param velX - The starting x velocity
	 * @param velY - The starting y velocity
	 * @param handler - The instance of the handler the game uses
	 * @param health - The starting health of the ally
	 */
	public Enemy(int x, int y, ID id,int velX,int velY, Handler handler,int health) {
		super(x, y, id, handler);
		this.setVelX(velX);
		this.setVelY(velY);
		this.radius = 25;
		this.health = health;
	}
	
//	See the documentation for the implemented/overridden method
	public void tick() {
		this.drag();
		think();
		displace();
		this.constrain();
		this.skillUpdate();
	}
	
	/**
	 * Causes character to act in a reasonable way, given its surroundings
	 */
	private void think() {
//		Each drive vector represents a factor in deciding acceleration
		
		
		updateTarget();
		
//		targetDrive moves toward the target
//		alignDrive aligns the two velocities, to avoid orbiting
		Vector targetDrive;
		Vector alignDrive;
		if (target == null) {
			float xA = (float) (Game.WIDTH / 2) - x; // the direction the player is in
			float yA = (float) (Game.HEIGHT / 2) - y; // the direction the player is in
			targetDrive = new Vector(xA, yA);
			alignDrive = new Vector(0, 0);
		}
		else
		{
			targetDrive = new Vector(this, target);
			alignDrive = new Vector(target).add(new Vector(this).negate());
		}
		targetDrive = targetDrive.scaleAndCopy(0.01f);
		
		alignDrive = alignDrive.scaleAndCopy(0.1f);
		
//		fearDrive tries to avoid obstacles
		Vector fearDrive = new Vector(0,0);
		for (GameObject o: handler.obstacles) {
			Vector avoid = new Vector(o, this);
			if (avoid.length() < (this.radius + o.radius) * 4) {
				fearDrive.add(avoid);
			}
		}
		fearDrive.scaleAndCopy(10000f);
		
		
//		containDrive tries to stay within the borders of the map
		Vector center = new Vector(Game.arenaWidth / 2, Game.arenaHeight / 2);
		Vector containDrive = new Vector(this,0).add(center.negate());
		containDrive = containDrive.scaleAndCopy(0.001f);
		
		
		Vector drive = targetDrive.add(alignDrive).add(fearDrive).add(containDrive);
		if (drive.length() > 1) {
			drive = drive.unitVector(); // limiting the acceleration.
		}
		this.accelX(drive.x); // actually accelerating.
		this.accelY(drive.y);
	}
	
	/**
	 * Determines if the target should change, and changes it if needed
	 */
	public void updateTarget() {
		if (target != null) {
			maybeRemoveTarget();
		}
		else if (this.target == null) {
			chooseNewTarget();
		}
	}
	
	/**
	 * Selects the most reasonable target, when possible
	 */
	private void chooseNewTarget() {
		if (handler.allies.size() == 0) {
			return;
		}
		target = handler.allies.get(0);
		for (Character o : handler.allies) {
			float len = new Vector(this,o).length();
			float lenOld = new Vector(this, target).length();
			if (len < lenOld) {
				target = o;
			}
		}
	}

	/**
	 *	Decides if to remove the target, and removes it if needed
	 */
	private void maybeRemoveTarget() {
		if (target.check_Death()) {
			target = null;
		}
		else if (new Vector(this, target).length() > 1000) {
			target = null;
		}
	}

	/**
	 * Updates its position
	 */
	private void displace() {
		this.x += this.velX;
		this.y += this.velY;
	}
	
	/**
	 * Applies a drag force
	 */
	private void drag() {
		if (!this.anchored) {
			this.setVelX(this.getVelX() * 0.99f);
			this.setVelY(this.getVelY() * 0.99f);
		}
	}
	
//	See the documentation for the implemented/overridden method
	public void accelX(float multiple) {
		this.velX += multiple * this.getAccel();
	}

//	See the documentation for the implemented/overridden method
	public void accelY(float multiple) {
		this.velY += multiple * this.getAccel();
	}
	
	/**
	 * Get the acceleration constant
	 * @return The acceleration constant
	 */
	public float getAccel() {
		return this.accel;
	}
	
//	See the documentation for the implemented/overridden method
	public void addTo(Handler h) {
		h.object.add(this);
		h.enemies.add(this);
		h.movingStuff.add(this);
	}
	
//	See the documentation for the implemented/overridden method
	public void removeFrom(Handler h) {
		h.object.remove(this);
		h.enemies.remove(this);
		h.movingStuff.remove(this);
	}
	
	public void render(Display d) {
		if (this.invincible) {
			d.displayObject(DisplayID.EnemyInvincible, x, y, radius);
		} else {
			d.displayObject(DisplayID.Enemy, x, y, radius);
		}
		d.drawHealthBar(x, y, radius, health, Character.MAXHEALTH);
	}

}

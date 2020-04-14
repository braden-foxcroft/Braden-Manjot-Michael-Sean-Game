package com.tutorial.main;

import com.tutorial.display.Display;
import com.tutorial.display.DisplayID;

import javafx.scene.input.KeyCode;

// Contains only original code.

/**
 * The player that the user controls and plays as
 */
public class Player extends Character{
	
	/**
	 * A constant representing how fast the character can accelerate
	 */
	private float accel = 0.2f; // an acceleration multiplier.
	
	
	/**
	 * Makes a player.
	 * @param x - The x coordinate to create it at
	 * @param y - The y coordinate to create it at
	 * @param id - The id (player type) of the object
	 * @param handler - The instance of the handler the game uses
	 */
	public Player(int x, int y, ID id, Handler handler) {
		super(x, y, id, handler);
		this.setVelX(0);
		this.setVelY(0);
		this.radius = 25;
	}
	
	/**
	 * Makes an ally.
	 * @param x - The x coordinate to create it at
	 * @param y - The y coordinate to create it at
	 * @param id - The id (ally type) of the object
	 * @param velX - The starting x velocity
	 * @param velY - The starting y velocity
	 * @param handler - The instance of the handler the game uses
	 */
	public Player(int x, int y, ID id,int velX,int velY, Handler handler) {
		super(x, y, id, handler);
		this.setVelX(velX);
		this.setVelY(velY);
		this.radius = 25;
	}
	
	/**
	 * Makes an ally.
	 * @param x - The x coordinate to create it at
	 * @param y - The y coordinate to create it at
	 * @param id - The id (ally type) of the object
	 * @param velX - The starting x velocity
	 * @param velY - The starting y velocity
	 * @param handler - The instance of the handler the game uses
	 * @param health - The starting health of the ally
	 */
	public Player(int x, int y, ID id,int velX,int velY, Handler handler,int health) {
		super(x, y, id, handler);
		this.setVelX(velX);
		this.setVelY(velY);
		this.radius = 25;
		this.health = health;
	}

	/**
	 * Updates a player's state and updates any skills
	 */
	public void tick() {
		this.skillUpdate();
		displace();
		this.drag();
		this.constrain();
	}
	
	/**
	 * Move the player, based on its current velocity
	 */
	private void displace() {
		this.x += this.velX;
		this.y += this.velY;
	}
	
	/**
	 * Slow the player down, based on its current velocity, applying a drag force
	 */
	private void drag() {
		if (!this.anchored) {
			float drag = 0.99f;
			this.setVelX(this.getVelX() * drag);
			this.setVelY(this.getVelY() * drag);
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

	/**
	 * Updates when you hit a wall and take damage
	 */
	public void hitWall() {
		super.hitWall();
	}
	
	/**
	 * Creates the keys which the user uses to play as their character
	 * @param kL - Keys that are the controls of the player for the user
	 */
	public void processInput(Keylist kL) {
		if (kL.isPressed(KeyCode.W)) {accelY(-1);}
		if (kL.isPressed(KeyCode.A)) {accelX(-1);}
		if (kL.isPressed(KeyCode.S)) {accelY(1);}
		if (kL.isPressed(KeyCode.D)) {accelX(1);}
		if (kL.justPressed(KeyCode.SHIFT)) {
			if (handler.enemies.size() > 0) {
				float x = handler.enemies.get(0).x;
				float y = handler.enemies.get(0).y;
				this.startSkill("dash", x, y);
			}
		}
	}
	
	/**
	 * Click based skills
	 * @param x - x position of where mouse is clicking
	 * @param y - y position of where mouse is clicking
	 */
	public void doClick(double x, double y) {
		this.startSkill("dash", (float)x, (float)y);
	}
	
//	See the documentation for the implemented/overridden method
	public void addTo(Handler h) {
		if (h.player == null)
		{
			h.object.add(this);
			h.allies.add(this);
			h.player = this;
			h.movingStuff.remove(this);
		}
	}

//	See the documentation for the implemented/overridden method
	public void removeFrom(Handler h) {
		h.object.remove(this);
		h.allies.remove(this);
		h.movingStuff.remove(this);
		h.player = null;
	}

	/**
	 * Renders character
	 */
	public void render(Display d) {
		if (this.invincible) {
			d.displayObject(DisplayID.PlayerInvincible, x, y, radius);
		}else {
			d.displayObject(DisplayID.Player, x, y, radius);
		}
		d.drawHealthBar(x, y, radius, health, Character.MAXHEALTH);
	}
}

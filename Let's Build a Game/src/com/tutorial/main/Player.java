package com.tutorial.main;

import javafx.scene.input.KeyCode;

// import java.awt.Color;
// import java.awt.Graphics;
// import java.util.Random;

// The player character.
// Contains only original code.

public class Player extends Character{
	
	private float accel = 0.2f; // an acceleration multiplier.
	
//	create a player
	public Player(int x, int y, ID id, Handler handler) {
		super(x, y, id, handler);
		this.setVelX(0);
		this.setVelY(0);
		this.radius = 25;
	}
	public Player(int x, int y, ID id,int velX,int velY, Handler handler) {
		super(x, y, id, handler);
		this.setVelX(velX);
		this.setVelY(velY);
		this.radius = 25;
	}

//	Update the player's state
	public void tick() {
		this.skillUpdate();
		displace();
		this.drag();
		this.constrain();
	}
	
//	Move the player, based on its current velocity
	private void displace() {
		this.x += this.velX;
		this.y += this.velY;
	}
	
//	Slow the player down, based on its current velocity
	private void drag() {
		if (!this.anchored) {
			float drag = 0.99f;
			this.setVelX(this.getVelX() * drag);
			this.setVelY(this.getVelY() * drag);
		}
	}
	
//	Accelerate the character.
	public void accelX(float multiple) {
		this.velX += multiple * this.getAccel();
	}
	
	public void accelY(float multiple) {
		this.velY += multiple * this.getAccel();
	}
	
//	Get the acceleration factor
	public float getAccel() {
		return this.accel;
	}

//	For when you hit a wall, and take damage.
	public void hitWall() {
		super.hitWall();
	}
	
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
	
	public void doClick(double x, double y) {
		this.startSkill("dash", (float)x, (float)y);
	}
	
	public void addTo(Handler h) {
		if (h.player == null)
		{
			h.object.add(this);
			h.allies.add(this);
			h.player = this;
			h.movingStuff.remove(this);
		}
	}
	
	public void removeFrom(Handler h) {
		h.object.remove(this);
		h.allies.remove(this);
		h.movingStuff.remove(this);
		h.player = null;
	}

//	Should render the character
	public void render(Display d) {
		if (this.invincible) {
			d.displayObject(DisplayID.PlayerInvincible, x, y, radius);
		}else {
			d.displayObject(DisplayID.Player, x, y, radius);
		}
		d.drawHealthBar(x, y, radius, health, Character.MAXHEALTH);
	}
}

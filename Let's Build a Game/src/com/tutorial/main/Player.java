package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;
//import java.util.Random;

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

//	Update the player's state
	public void tick() {
		this.drag();
		displace();
		this.constrain();
		this.skillUpdate();
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

//	Should render the character
//	TODO Move all rendering to a designated class.
	public void render(Graphics g) {
		if (this.invincible) {
			// g.setColor(new Color(100, 0, 0));
			g.setColor(Color.green);
		}
		else
		{
			g.setColor(Color.blue);
		}
		// g.fillRect(x, y, 50, 50);
		g.fillOval((int)(x-radius), (int)(y-radius), 2 * radius, 2* radius);
	}
}

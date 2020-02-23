package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;
//import java.util.Random;

public class Player extends Character{
	
	private float accel = 0.2f;
	
	public Player(int x, int y, ID id, Handler handler) {
		super(x, y, id, handler);
		this.setVelX(0);
		this.setVelY(0);
		this.radius = 25;
	}

	public void tick() {
		this.drag();
		displace();
		this.constrain();
		this.skillUpdate();
	}
	
	private void displace() {
		this.x += this.velX;
		this.y += this.velY;
	}
	
	private void drag() {
		if (!this.anchored) {
			this.setVelX(this.getVelX() * 0.99f);
			this.setVelY(this.getVelY() * 0.99f);
		}
	}
	
	public void accelX(float multiple) {
		this.velX += multiple * this.getAccel();
	}
	
	public void accelY(float multiple) {
		this.velY += multiple * this.getAccel();
	}
	
	public float getAccel() {
		return this.accel;
	}

	public void hitWall() {
		super.hitWall();
	}

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

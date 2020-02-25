package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;

public class Enemy extends Character {
	
	private float accel = 0.2f;
	
	public Enemy(int x, int y, ID id, Handler handler) {
		super(x, y, id, handler);
		this.setVelX(0);
		this.setVelY(0);
		this.radius = 25;
	}
	
	public void tick() {
		this.drag();
		think();
		displace();
		this.constrain();
		this.skillUpdate();
	}
	
	private void think() {
		float playerX;
		float playerY;
		if (handler.player() == null) {
			playerX = Game.WIDTH / 2;
			playerY = Game.HEIGHT / 2;
		}
		else
		{
			playerX = handler.player().x;
			playerY = handler.player().y;
		}
		float xA = playerX - x;
		float yA = playerY - y;
		Vector v = new Vector(xA,yA);
		v = v.scaleAndCopy(0.01f);
		if (v.length() > 1) {
			v = v.scaleAndCopy(1 / v.length());
		}
		this.accelX(v.x);
		this.accelY(v.y);
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
	
	public void render(Display d) {
		if (invincible) {
			d.displayObject(DisplayID.EnemyInvincible, this.x, this.y, radius);
		}
		else {
			d.displayObject(DisplayID.Enemy, this.x, this.y, radius);
		}
	}

}

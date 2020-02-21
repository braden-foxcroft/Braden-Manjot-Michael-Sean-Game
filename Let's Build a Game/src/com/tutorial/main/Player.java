package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;
//import java.util.Random;

public class Player extends GameObject{

	private int radius;
	
	public Player(int x, int y, ID id) {
		super(x, y, id);
		this.setVelX(0);
		this.setVelY(0);
		this.radius = 25;
	}

	public void tick() {
		this.drag();
		displace();
		this.constrain();
	}

	private void displace() {
		this.x += this.velX;
		this.y += this.velY;
	}

	public void render(Graphics g) {
		g.setColor(Color.red);
		// g.fillRect(x, y, 50, 50);
		g.fillOval((int)(x-radius), (int)(y-radius), 2 * radius, 2* radius);
	}
	
	private void drag() {
		this.setVelX(this.getVelX() * 0.99f);
		this.setVelY(this.getVelY() * 0.99f);
	}
	
	private void constrain() {
		if (y > 690 - radius) {
			this.setVelY(-Math.abs(this.getVelY()));
			y = 690 - radius;
		}
		if (x > 960 + radius) {
			x = - radius;
		}
		if (x < 0 - radius) {
			x = 960 + radius;
		}
		if (y - radius < 0) {
			y = radius;
			setVelY(0);
		}
	}

	public void hit(boolean hurt) {
		// TODO if hurt, get hurt.
		// ensure appropriate skill cancels/triggers
	}

	public void push(int xForce, int yForce) {
		accelX(xForce);
		accelY(yForce);
	}
	
}

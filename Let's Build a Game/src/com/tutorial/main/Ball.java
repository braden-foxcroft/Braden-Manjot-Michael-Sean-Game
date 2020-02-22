package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;

public class Ball extends GameObject {
	
	public Ball(int x, int y, ID id) {
		super(x, y, id);
		this.radius = 40;
	}
	
	public void tick() {
		this.drag();
		displace();
		this.constrain();
	}

	public void render(Graphics g) {
		g.setColor(Color.blue);
		// g.fillRect(x, y, 50, 50);
		g.fillOval((int)(x-radius), (int)(y-radius), 2 * radius, 2* radius);
	}

	public void hit(GameObject other) {
		super.hit(other);
		// TODO Auto-generated method stub
		// same as other hit methods
	}
	
	private void displace() {
		this.x += this.velX;
		this.y += this.velY;
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
		/*
		if (y - radius < 0) {
			y = radius;
			setVelY(0);
		}
		*/
		if (y < radius) {
			this.setVelY(Math.abs(this.getVelY()));
			y = radius;
		}
	}
	
}

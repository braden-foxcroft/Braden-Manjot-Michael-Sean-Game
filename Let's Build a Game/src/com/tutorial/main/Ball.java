package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;

public class Ball extends GameObject {
	
	public Ball(int x, int y, ID id, Handler handler) {
		super(x, y, id, handler);
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

	public void hitWall() {
		// do nothing
	}
	
}

package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class ObstTrap extends GameObject {
	
	Random r = new Random();
	Handler handler;

	public ObstTrap(int x, int y, ID id, Handler handler, int aRadius) {
		super(x, y, id, handler);
		this.anchored = true;
		this.radius = aRadius;
		this.damaging = true;
	}

	
	public void tick() {
		
		
	}


	public void render(Graphics g) {
		
		// Must be changed to fit with Display d
		g.setColor(Color.white);
		g.fillOval((int)x-radius, (int)y-radius, radius*2, radius*2);
		
	}
	

	public void hitWall() {
		// do Nothing, this will be an anchored object.
		
	}

}

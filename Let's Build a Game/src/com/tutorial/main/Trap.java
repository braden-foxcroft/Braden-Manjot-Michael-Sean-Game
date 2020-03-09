package com.tutorial.main;

import java.util.Random;

public class Trap extends Character {
	
	Random r = new Random();
	

	public Trap(int x, int y, ID id, Handler handler, int aRadius) {
		super(x, y, id, handler);
		this.anchored = true;
		this.radius = aRadius;
		this.damaging = false;
	}

	
	public void tick() {
		
		
	}
	

	public void hitWall() {
		// do Nothing, this will be an anchored object.
		
	}

	public void constrain() {
		
	}


	public void onCollision(GameObject other) {
		if (this.health != 0) {
			int tempX = (int)this.getX();
			int tempY = (int)this.getY();
			
			this.health = 0;
			Handler.time_To_Die();
			
			Random r = new Random();
			int trapType = r.nextInt(3);
			if (trapType == 0) {handler.addObject(new Ball(tempX, tempY, ID.Ball, handler));}
			else if (trapType == 1) {handler.addObject(new Enemy(tempX,tempY,ID.Enemy, handler));}
			else if (trapType == 2) {/* I want this to set off an explosion as though it were the player using the skill */}
		}
	}
	
	public void render(Display d) {
		d.displayObject(DisplayID.Trap, this.x, this.y, radius);
	}



}

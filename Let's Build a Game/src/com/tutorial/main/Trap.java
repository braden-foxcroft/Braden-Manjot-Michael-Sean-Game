package com.tutorial.main;

import java.util.Random;

public class Trap extends GameObject {
	
	Random r = new Random();
	Handler handler;
	

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
		
		//this.hit(other);
		//handler.removeObject(this);
		//handler.addObject(new Ball((int) this.x,(int) this.y, ID.Ball, handler));
		
	}
	
	@Override
	public void render(Display d) {
		d.displayObject(DisplayID.Trap, this.x, this.y, radius);
	}



}

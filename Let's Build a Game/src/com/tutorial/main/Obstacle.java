package com.tutorial.main;

import java.util.Random;

public class Obstacle extends GameObject {
	
	Random r = new Random();

	public Obstacle(int x, int y, ID id, Handler handler, int aRadius) {
		super(x, y, id, handler);
		this.anchored = true;
		this.radius = aRadius;
		this.damaging = true;
	}

	
	public void tick() {
		
		
	}
	

	public void hitWall() {
		// do Nothing, this will be an anchored object.
		
	}

	public void constrain() {
		
	}


	public void onCollision(GameObject other) {
		
	}
	
	@Override
	public void render(Display d) {
		d.displayObject(DisplayID.Obstacle, this.x, this.y, radius);
	}
	
	public void addTo(Handler h) {
		h.obstacles.add(this);
		h.object.add(this);
	}
	
	public void removeFrom(Handler h) {
		h.obstacles.remove(this);
		h.object.remove(this);
	}



}


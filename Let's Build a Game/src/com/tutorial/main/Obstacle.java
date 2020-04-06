package com.tutorial.main;

import java.util.Random;
// Game object of obstacles, used to create a randomized map on the canvas
// Obstacles are anchored and deal damage when hit.

import com.tutorial.display.Display;
import com.tutorial.display.DisplayID;

// TODO Comments by Sean
public class Obstacle extends GameObject {
	
	Random r = new Random();
// parameters for the constructor passed in from handler in the setup method
	public Obstacle(int x, int y, ID id, Handler handler, int aRadius) {
		super(x, y, id, handler);
		this.anchored = true; // gives the obstacle infinite mass (for collisions)
		this.radius = aRadius;
		this.damaging = true;
	}

// required from abstract class GameObject
	public void tick() {
		
		
	}
	

	public void hitWall() {
		// do Nothing, this will be an anchored object.
		
	}
	// required from abstract class GameObject
	public void constrain() {
		
	}

	// required from abstract class GameObject
	public void onCollision(GameObject other) {
		
	}
	
// 	
	@Override
	public void render(Display d) {
		d.displayObject(DisplayID.Obstacle, this.x, this.y, radius);
	}
// adds Obstacles to their respective lists in the handler		
	public void addTo(Handler h) {
		h.obstacles.add(this);
		h.object.add(this);
	}
	// removes Obstacles from their respective lists in the handler	
	public void removeFrom(Handler h) {
		h.obstacles.remove(this);
		h.object.remove(this);
	}



}


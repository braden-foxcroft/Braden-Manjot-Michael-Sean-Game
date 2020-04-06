package com.tutorial.main;

import java.util.Random;
// Game object of obstacles, used to create a randomized map on the canvas
// Obstacles are anchored and deal damage when hit.

import com.tutorial.display.Display;
import com.tutorial.display.DisplayID;


public class Obstacle extends GameObject {
	
	Random r = new Random();
/**	
 * parameters for the constructor passed in from handler in the setup method
 * Creates an anchored obstacle on the canvas
 * @param x - The x coordinate to create it at
 * @param y - The y coordinate to create it at
 * @param id - The id (enemy type) of the object
 * @param handler - The instance of the handler the game uses
 * @param aRadius - The radius of the obstacle being created
 */
	public Obstacle(int x, int y, ID id, Handler handler, int aRadius) {
		super(x, y, id, handler);
		this.anchored = true; // gives the obstacle infinite mass (for collisions)
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


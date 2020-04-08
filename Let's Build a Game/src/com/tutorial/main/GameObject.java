package com.tutorial.main;

import com.tutorial.display.Display;

/**
 * An abstract class that represents every on-screen object that
 * can be rendered. It consists of behaviors that every object needs, or
 * of attributes that must exist.
 */

public abstract class GameObject {
	
	/**
	 * The x coordinate of the object (arena coordinates)
	 */
	protected float x;
	
	/**
	 * The y coordinate of the object (arena coordinates)
	 */
	protected float y;
	
	/**
	 * The x velocity
	 */
	protected float velX;
	
	/**
	 * The y velocity
	 */
	protected float velY;
	
	/**
	 * The ID of the object.
	 */
	protected ID id;
	
	/**
	 * The 'anchored' state of the object. Represents whether the object has functionally infinite mass.
	 */
	protected boolean anchored = false;
	
	/**
	 * The radius of the object. Used for rendering and calculating collisions.
	 */
	protected int radius;
	
	/**
	 * An instance of the handler the game is contained in
	 */
	protected Handler handler;
	
	/**
	 * Whether the object damages other objects on contact.
	 */
	protected boolean damaging = false;
	
	/**
	 * Whether or not the object is touchable
	 */
	protected boolean canTouch = true;
	
	/**
	 * Creates the object
	 * @param x - The x position to make it at
	 * @param y - The y position to make it at
	 * @param id - The ID of the object
	 * @param handler - The instance handler the object is contained in
	 */
	public GameObject(int x, int y, ID id, Handler handler) {
		this.setX(x);
		this.setY(y);
		this.setId(id);
		this.handler = handler;
	}
	

	/**
	 * Acts once every tick.
	 * Updates character.
	 */
	public abstract void tick();
	
	/**
	 * Displays the object using the display class
	 * @param d The instance of the display class that contains the graphicsContext.
	 */
	public abstract void render(Display d);
	
	/**
	 * Performs the events required when a wall is hit by the object
	 */
	public abstract void hitWall();
	
	/**
	 * 	Prevents objects from leaving the bounds of the arena
	 */
	protected void constrain() {
		int width = Game.arenaWidth;
		int height = Game.arenaHeight;
		if (y > height - radius) {
			this.setVelY(-Math.abs(this.getVelY()));
			y = height - radius;
			this.hitWall();
		}
		if (y < radius) {
			this.setVelY(Math.abs(this.getVelY()));
			y = radius;
			this.hitWall();
		}
		if (x < radius) {
			this.setVelX(Math.abs(this.getVelX()));
			x = radius;
			this.hitWall();
		}
		if (x > width - radius) {
			this.setVelX(-Math.abs(this.getVelX()));
			x = width - radius;
			this.hitWall();
		}
	}
	
	/**
	 * Determines whether the object should die.
	 * @return A boolean value, true when the object should die
	 */
	public boolean check_Death() {
		return false;
	}
	
	/**
	 * A method that triggers when 2 objects collide.
	 * Anchored objects should be 'other', when possible.
	 * @param other The object that this gameObject collided with
	 */
	public void hit(GameObject other) {
		if (!this.canTouch || !other.canTouch)
		{
			return;
		}
		this.onCollision(other);
		other.onCollision(this);
		if (TextGame.textGameActive) {
			System.out.println("collision");
			TextGame.padding++;
		}
		if (other.damaging) {
			this.doSkill("hurt",1);
			this.doSkill("invuln",1);
		}
		if (this.damaging) {
			other.doSkill("hurt",1);
			other.doSkill("invuln",1);
		}
		if (other.anchored) {
			if (!this.anchored) {
				Vector vA = new Vector(this);
				Vector vB = new Vector(other);
				Vector dir = new Vector(this, other);
				vA.collideAnchored(vB, dir);
				this.setVelocity(vA);
				other.setVelocity(vB);
			}
		}
		else
		{
			Vector vA = new Vector(this);
			Vector vB = new Vector(other);
			Vector dir = new Vector(this, other);
			vA.collide(vB, dir);
			this.setVelocity(vA);
			other.setVelocity(vB);
		}
	}
	
	/**
	 * Triggers actions on collision
	 * @param other - The object collided with
	 */
	public abstract void onCollision(GameObject other);
	
	/**
	 * Returns the distance to the center of another object.
	 * @param other - The object to measure distance to
	 * @return The distance between the centers of the 2 objects
	 */
	public float distance(GameObject other) {
		return (float)Math.sqrt(Math.pow(x - other.x,2) + Math.pow(y - other.y,2));
	}
	
	/**
	 * Sets the velocity of the object to v
	 * @param v - The velocity to set the object's velocity to.
	 */
	public void setVelocity(Vector v) {
		this.setVelX(v.x);
		this.setVelY(v.y);
	}
	
	/**
	 * Gets the x position
	 * @return the x position
	 */
	public float getX() {
		return x;
	}
	
	/**
	 * sets the x position
	 * @param x - the x position
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Gets the y position
	 * @return the y position
	 */
	public float getY() {
		return y;
	}

	/**
	 * Sets the y position
	 * @param y - the y position
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Gets the ID
	 * @return the ID
	 */
	public ID getId() {
		return id;
	}

	/**
	 * Sets the ID
	 * @param id - The ID to assign
	 */
	public void setId(ID id) {
		this.id = id;
	}

	/**
	 * Gets the x velocity
	 * @return the x velocity
	 */
	public float getVelX() {
		return velX;
	}

	/**
	 * Sets the x velocity
	 * @param velX - the x velocity to assign
	 */
	public void setVelX(float velX) {
		this.velX = velX;
	}

	/**
	 * Gets the y velocity
	 * @return the y velocity
	 */
	public float getVelY() {
		return velY;
	}

	/**
	 * Sets the y velocity
	 * @param velY - the y velocity to assign
	 */
	public void setVelY(float velY) {
		this.velY = velY;
	}
	
	/**
	 * Accelerates an object in the x direction
	 */
	public void accelX(float x) {
		this.setVelX(this.getVelX()+x);
	}
	
	/**
	 * Accelerates an object in the y direction
	 */
	public void accelY(float y) {
		this.setVelY(this.getVelY()+y);
	}
	
	/**
	 * Gets the object's radius
	 * @return the radius of the object
	 */
	public int getRadius() {
		return this.radius;
	}
	
	/**
	 * A wrapper for doSkill that takes a vector
	 * @param skillName - The skill to trigger
	 * @param v - the two parameters, stored in a vector
	 */
	public void doSkill(String skillName, Vector v) {
		doSkill(skillName, v.x, v.y);
	}
	
	/**
	 * A wrapper for doSkill that takes no parameters.
	 * @param skillName - The skill to trigger
	 */
	public void doSkill(String skillName) {
		doSkill(skillName, 0);
	}
	
	/**
	 * A wrapper for doSkill that takes 1 parameter
	 * @param skillName  - The skill to trigger
	 * @param param1 - The first parameter
	 */
	public void doSkill(String skillName, float param1) {
		doSkill(skillName, param1, 0);
	}
	
	/**
	 * Does nothing, but is overridden in various places
	 * @param skillname - does nothing
	 * @param x - does nothing
	 * @param y - does nothing
	 */
	public void doSkill(String skillname, float x, float y) {
		// Do nothing. It's really just here to be overridden.
	}
	
	/**
	 * Renders the object in the text-based version
	 * @param board - the board to draw objects on.
	 */
	public void textRender(String[][] board) {
		if (TextGame.textGameActive) {
			System.out.println("gameobject Render");
			TextGame.padding++;
		}
		String t = "?";
		if (this.id == ID.Player) {
			t = "O";
		} else if (this.id == ID.Enemy) {
			t = "X";
		} else if (this.id == ID.Ball) {
			t = "0";
		} else {
			t = "?";
		}
		int x =  (int)(this.x * ((float)TextGame.WIDTH / Game.WIDTH));
		int y =  (int)(this.y * ((float)TextGame.HEIGHT / Game.HEIGHT));
		try {
			board[x][y] = t;
		} catch (Error e) {
//			System.out.println("Could not display: " + this.id);
//			TextGame.padding++;
		}
	}
	
	/**
	 * Adds the object to the appropriate lists in handler
	 * @param h the handler to add the object into
	 */
	public void addTo(Handler h) {
		h.object.add(this);
	}
	
	/**
	 * Removes the object to the appropriate lists in handler
	 * @param h the handler to remove the object from
	 */
	public void removeFrom(Handler h) {
		h.object.remove(this);
	}
	
//	See the documentation for the implemented/overridden method
	public String toString() {
		String result = "";
		result += this.id; 
		result += ",";
		result += this.radius;
		result += ",";
		result += this.x;
		result += ",";
		result += this.y;
		result += ",";
		result += this.velX;
		result += ",";
		result += this.velY;
//		result += ",";
//		result += this.anchored;
//		result += ",";
//		result += this.damaging;
		result += ",";
		
		return result;
	}
}

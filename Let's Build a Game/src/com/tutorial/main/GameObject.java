package com.tutorial.main;

// import java.awt.Graphics;

// This class is an abstract class that represents every on-screen object that
// can be rendered. It consists of behaviors that every object needs, or
// of attributes that must exist, to prevent errors.
// Some of this code is copied (see the game file for sources)

public abstract class GameObject {
	protected float x;
	protected float y;
	protected float velX;
	protected float velY;
	protected ID id;
	protected boolean anchored = false;
	protected int radius;
	protected Handler handler; // an instance of the handler is required.
	protected boolean damaging = false;
	/**
	 * Contains whether or not the object is touchable
	 */
	protected boolean canTouch = true;
	
//	This creates an object.
	public GameObject(int x, int y, ID id, Handler handler) {
		this.setX(x);
		this.setY(y);
		this.setId(id);
		this.handler = handler;
	}
	
	public abstract void tick();
	public abstract void render(Display d);
	public abstract void hitWall();
	
//	Constrain prevents objects from leaving the bounds of the arena
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
	
//	Determines if an object should be removed. Meant to be overridden.
	public boolean check_Death() {
		return false;
	}
	
//	The behavior when two objects collide.
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
	
	public abstract void onCollision(GameObject other);
	
//	Returns the distance to the center of another object.
	public float distance(GameObject other) {
		return (float)Math.sqrt(Math.pow(x - other.x,2) + Math.pow(y - other.y,2));
	}
	
//	Sets the velocity
	public void setVelocity(Vector v) {
		this.setVelX(v.x);
		this.setVelY(v.y);
	}
	
//	Getter and setter methods.
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}
	
	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public float getVelX() {
		return velX;
	}

	public void setVelX(float velX) {
		this.velX = velX;
	}

	public float getVelY() {
		return velY;
	}

	public void setVelY(float velY) {
		this.velY = velY;
	}
	
	public void accelX(float x) {
		this.setVelX(this.getVelX()+x);
	}
	
	public void accelY(float y) {
		this.setVelY(this.getVelY()+y);
	}
	
	public int getRadius() {
		return this.radius;
	}
	
//	Wrapper that takes a vector
	public void doSkill(String skillName, Vector v) {
		doSkill(skillName, v.x, v.y);
	}
	
//	Wrapper that takes no parameters
	public void doSkill(String skillName) {
		doSkill(skillName, 0);
	}
	
//	Wrapper that takes 1 parameter
	public void doSkill(String skillName, float param1) {
		doSkill(skillName, param1, 0);
	}
	
//	This does nothing. It's there for the sake of brevity.
	public void doSkill(String skillname, float x, float y) {
		// Do nothing. It's really just here to be overridden.
	}
	
//	A method for rendering an object for the text-based version.
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
			t = "O";
		}
		int x =  (int)(this.x * ((float)TextGame.WIDTH / Game.WIDTH));
		int y =  (int)(this.y * ((float)TextGame.HEIGHT / Game.HEIGHT));
		try {
			board[x][y] = t;
		} catch (Error e) {
			System.out.println("Could not display: " + this.id);
			TextGame.padding++;
		}
	}
	
	public void addTo(Handler h) {
		h.object.add(this);
	}
	
	public void removeFrom(Handler h) {
		h.object.remove(this);
	}
	
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
		result += ",";
		result += this.anchored;
		result += ",";
		result += this.damaging;
		result += ",\n";
		
		return result;
	}
}

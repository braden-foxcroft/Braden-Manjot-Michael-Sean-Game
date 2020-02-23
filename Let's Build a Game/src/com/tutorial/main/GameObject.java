package com.tutorial.main;

import java.awt.Graphics;

public abstract class GameObject {
	protected float x;
	protected float y;
	protected float velX;
	protected float velY;
	protected ID id;
	protected boolean anchored = false;
	protected int radius;
	protected Handler handler;
	
	public GameObject(int x, int y, ID id, Handler handler) {
		this.setX(x);
		this.setY(y);
		this.setId(id);
		this.handler = handler;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract void hitWall();
	
	protected void constrain() {
		int width = Game.WIDTH - 6;
		int height = Game.HEIGHT - 30;
		
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
	
	public boolean check_Death() {
		return false;
	}
	
	public void hit(GameObject other) {
		System.out.println("collision");
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
	
	public float distance(GameObject other) {
		return (float)Math.sqrt(Math.pow(x - other.x,2) + Math.pow(y - other.y,2));
	}
	
	public void setVelocity(Vector v) {
		this.setVelX(v.x);
		this.setVelY(v.y);
	}
	
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
	
	public void textRender(String[][] board) {
		System.out.println("gameobject Render");
		String t = "?";
		if (this.id == ID.Player) {
			t = "@";
		} else if (this.id == ID.Enemy) {
			t = "0";
		} else if (this.id == ID.Ball) {
			t = "O";
		}
		int x =  (int)(this.x * ((float)TextGame.WIDTH / Game.WIDTH));
		int y =  (int)(this.y * ((float)TextGame.HEIGHT / Game.HEIGHT));
		try {
			board[x][y] = t;
		} catch (Error e) {
			System.out.println("Could not display: " + this.id);
		}
	}
	
	
	
	
}

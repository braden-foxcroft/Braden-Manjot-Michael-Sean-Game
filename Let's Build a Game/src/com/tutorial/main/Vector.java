package com.tutorial.main;

public class Vector {
	public float x;
	public float y;
	
	public Vector(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector(GameObject g) {
		this.x = g.getVelX();
		this.y = g.getVelY();
		// creates a velocity vector from a GameObject
	}
	
	public Vector(GameObject a, GameObject b) {
		this.x = b.getX() - a.getX();
		this.y = b.getY() - a.getY();
		// Creates a vector from the position of A
		// to the position of B
	}
	
	public float length() {
		return (float)Math.sqrt((x * x) + (y * y));
	}
	
	public Vector(Vector v) {
		this.x = v.x;
		this.y = v.y;
	}
	
	public void reverse(Vector other) {
		float tempX = other.x;
		float tempY = other.y;
		other.x = this.x;
		other.y = this.y;
		this.x = tempX;
		this.y = tempY;
	}
	
	public void collide(Vector other, Vector collideDir) {
		Vector vA = new Vector(this);
		Vector vB = new Vector(other);
		// change perspective
		Vector frameShift = new Vector(vB);
		vA = vA.add(frameShift.negate());
		vB = vB.add(frameShift.negate());
		//System.out.println(vA.toString());
		//System.out.println(vA.toString());
		// get force acting on object (based on some strange math)
		Vector force = vA.projection(collideDir);
		// apply force (only when diverging)
		if (force.aligns(collideDir)) {
			vA = vA.add(force.negate());
			vB = vB.add(force);
		}
		else
		{
			System.out.println("Ignored");
		}
		// remember to change back
		vA = vA.add(frameShift);
		vB = vB.add(frameShift);
		this.set(vA);
		other.set(vB);
	}
	
	public String toString() {
		return "[" + x + "," + y + "]";
	}
	
	public Vector add(Vector other) {
		return new Vector(x + other.x, y + other.y);
	}
	
	public void set(Vector other) {
		this.x = other.x;
		this.y = other.y;
	}
	
	public Vector negate() {
		return new Vector(-x,-y);
	}
	
	public boolean equals(Vector other) {
		return (other.x == x && other.y == y);
	}
	
	public boolean contains(Vector other) {
		boolean a = this.aligns(other);
		boolean b = Math.abs(other.x) < Math.abs(this.x);
		return a && b;
	}

	public boolean aligns(Vector other) {
		float a = other.x * this.y;
		float b = other.y * this.x;
		boolean c = Math.abs(a - b) < 0.0001;
		boolean d = Math.signum(other.x) == Math.signum(this.x);
		return c && d;
	}
	
	public Vector scaleAndCopy(float f) {
		return new Vector(x * f,y * f);
		// returns a multiple of the original vector.
	}
	
	public Vector projection(Vector other) {
		if (other.x == 0 && other.y == 0)
		{
			return new Vector(this); // error management. No good return value.
		}
		float numerator = (this.x * other.x) + (this.y * other.y);
		float denominator = (other.x * other.x) + (other.y * other.y);
		float ratio = (numerator) / (denominator);
		Vector result = other.scaleAndCopy(ratio);
		return result;
		// note: this is a vector>vector projection.
	}
	
	
}

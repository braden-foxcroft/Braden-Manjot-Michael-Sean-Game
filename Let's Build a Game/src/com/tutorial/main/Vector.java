package com.tutorial.main;

// Contains vectors. These are developed based on linear algebra rules,
// and applied based on mechanics rules.
// A vector consists of an x and y. This describes a velocity, position, and path.

public class Vector {
	public float x;
	public float y;
	
//	Create a vector from floating-point values
	public Vector(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
//	Extract a vector from a GameObject's velocity
	public Vector(GameObject g) {
		this.x = g.getVelX();
		this.y = g.getVelY();
		// creates a velocity vector from a GameObject
	}
	
	
//	Extract a vector representing the path between two objects.
	public Vector(GameObject a, int b) {
		this.x = a.getX();
		this.y = a.getY();
//		Creates a coordinate
	}
	
//	Extract a vector representing the path between two objects.
	public Vector(GameObject a, GameObject b) {
		this.x = b.getX() - a.getX();
		this.y = b.getY() - a.getY();
		// Creates a vector from the position of A
		// to the position of B.
	}
	
	public Vector(GameObject a, Vector b) {
		this.x = b.x - a.getX();
		this.y = b.y - a.getY();
		// Creates a vector from the position of A
		// to the position of B.
	}
	
//	Return the length of a vector.
	public float length() {
		return (float)Math.sqrt((x * x) + (y * y));
	}
	
//	Copy a vector.
	public Vector(Vector v) {
		this.x = v.x;
		this.y = v.y;
	}
	
//	Switch two vectors around.
	public void reverse(Vector other) {
		float tempX = other.x;
		float tempY = other.y;
		other.x = this.x;
		other.y = this.y;
		this.x = tempX;
		this.y = tempY;
	}
	
//	Perform a collision, assuming identical inertia.
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
		// apply force (only when force pushes apart)
		if (force.aligns(collideDir)) {
			vA = vA.add(force.negate());
			vB = vB.add(force);
		}
		// remember to change back
		vA = vA.add(frameShift);
		vB = vB.add(frameShift);
		this.set(vA);
		other.set(vB);
	}
	
//	Perform a collision, assuming 'other' has infinite inertia.
	public void collideAnchored(Vector other, Vector collideDir) {
		// 'Other' is the anchored vector.
		Vector vA = new Vector(this);
		Vector vB = new Vector(other);
		// change perspective
		Vector frameShift = new Vector(vB);
		vA = vA.add(frameShift.negate());
		vB = vB.add(frameShift.negate());
		//System.out.println(vA.toString());
		//System.out.println(vA.toString());
		// get force acting on object (based on some strange math)
		Vector force = vA.projection(collideDir).scaleAndCopy(2f);
		// force = force.add(vB.projection(collideDir));
		// apply force (only when force pushes apart)
		if (force.aligns(collideDir)) {
			vA = vA.add(force.negate());
		}
		// remember to change back
		vA = vA.add(frameShift);
		vB = vB.add(frameShift);
		this.set(vA);
		other.set(vB);
	}
	
//	for debugging purposes. '[x,y]'
	public String toString() {
		return "[" + x + "," + y + "]";
	}
	
//	Add two vectors, return the result
	public Vector add(Vector other) {
		return new Vector(x + other.x, y + other.y);
	}
	
//	Set a vector.
	public void set(Vector other) {
		this.x = other.x;
		this.y = other.y;
	}
	
//	Negate a vector. A vector plus its negation has a length of zero.
	public Vector negate() {
		return new Vector(-x,-y);
	}
	
//	Compare two vectors. Precise (no margin of error).
	public boolean equals(Vector other) {
		float a = Math.abs(other.x - x);
		float b = Math.abs(other.y - y);
		float e = 0.001f; // Very small
		return (a < e && b < e);
	}
	
//	Checks if a vector contains another vector. (Meaning v1 * (0 <= n < 1) = v2)
	public boolean contains(Vector other) {
		boolean a = this.aligns(other);
		boolean b = Math.abs(other.x) < Math.abs(this.x);
		return a && b;
	}

//	Checks if two vectors point the same way.
	public boolean aligns(Vector other) {
		float a = other.x * this.y;
		float b = other.y * this.x;
		boolean c = Math.abs(a - b) < 0.0001;
		boolean d = Math.signum(other.x) == Math.signum(this.x);
		return c && d;
	}
	
//	Create a scaled copy.
	public Vector scaleAndCopy(float f) {
		return new Vector(x * f,y * f);
		// returns a multiple of the original vector.
	}
	
//	Creates a unit vector
	public Vector unitVector() {
		if (this.length() == 0) {
			return this.scaleAndCopy(1);
		} else {
			return this.scaleAndCopy(1 / this.length());
		}
	}
	
//	Creates a unit vector
	public Vector unitVector(float length) {
		if (this.length() == 0) {
			return this.scaleAndCopy(1);
		} else {
			return this.scaleAndCopy(length / this.length());
		}
	}
	
//	Projection. Requires linear algebra knowledge to understand.
	public Vector projection(Vector other) {
		if (other.x == 0 && other.y == 0)
		{
			return new Vector(this);
//			Error management. Useful for the current applications
		}
		float numerator = (this.x * other.x) + (this.y * other.y);
		float denominator = (other.x * other.x) + (other.y * other.y);
		float ratio = (numerator) / (denominator);
		Vector result = other.scaleAndCopy(ratio);
		return result;
		// note: this is a vector>vector projection.
	}
	
	
}

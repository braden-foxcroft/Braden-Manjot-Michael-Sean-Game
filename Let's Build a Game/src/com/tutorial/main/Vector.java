package com.tutorial.main;

/**
 * An x and y coordinate for use in mathematics.
 * Used for vector arithmetic.
 * Vectors can represent a path, position, or velocity
 */

public class Vector {
	
	/**
	 * The x value encoded in the vector
	 */
	public float x;
	
	/**
	 * The y value encoded in the vector
	 */
	public float y;
	
	/**
	 * Creates a vector
	 * @param x - The x value of the vector to create
	 * @param y - The y value of the vector to create
	 */
	public Vector(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Create a vector that represents the velocity of a gameObject
	 * @param g - The gameobject to clone the velocity of
	 */
	public Vector(GameObject g) {
		this.x = g.getVelX();
		this.y = g.getVelY();
		// creates a velocity vector from a GameObject
	}
	
	/**
	 * Create a vector representing the path from the origin to an object
	 * @param a - The gameObject to start at
	 * @param b - An arbitrary integer
	 */
	public Vector(GameObject a, int b) {
		this.x = a.getX();
		this.y = a.getY();
	}
	
	/**
	 * Creates a vector describing the path between two objects
	 * @param a - The object to start the path at
	 * @param b - The object to end the path at
	 */
	public Vector(GameObject a, GameObject b) {
		this.x = b.getX() - a.getX();
		this.y = b.getY() - a.getY();
	}
	
	/**
	 * Creates a vector describing the path from an object to the end of another path
	 * @param a - The object to start the path at
	 * @param b - The path to end the path at
	 */
	public Vector(GameObject a, Vector b) {
		this.x = b.x - a.getX();
		this.y = b.y - a.getY();
		// Creates a vector from the position of A
		// to the position of B.
	}
	
	/**
	 * Get the length of the vector
	 * @return The calculated length of the vector
	 */
	public float length() {
		return (float)Math.sqrt((x * x) + (y * y));
	}
	
	/**
	 * Create a deep copy of a vector
	 * @param v - the vector to copy
	 */
	public Vector(Vector v) {
		this.x = v.x;
		this.y = v.y;
	}
	
	/**
	 * Switch two vector's values
	 * @param other - the vector to switch this with
	 */
	public void reverse(Vector other) {
		float tempX = other.x;
		float tempY = other.y;
		other.x = this.x;
		other.y = this.y;
		this.x = tempX;
		this.y = tempY;
	}
	
	/**
	 * Perform a collision, assuming identical inertia.
	 * @param other - The velocity of the object collided with
	 * @param collideDir - The vector describing the path from one object to the other.
	 */
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
	
	/**
	 * Perform a collision, assuming the other object has infinite inertia
	 * @param other - The velocity of the object collided with
	 * @param collideDir - The vector describing the path from one object to the other.
	 */
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
	
//	See the documentation for the implemented/overridden method
	public String toString() {
		return "[" + x + "," + y + "]";
	}
	
	/**
	 * Adds the x and y of 2 vectors
	 * @param other - the vector to add
	 * @return The result of the addition
	 */
	public Vector add(Vector other) {
		return new Vector(x + other.x, y + other.y);
	}
	
	/**
	 * A way to deep copy an existing vector to an existing vector
	 * @param other - The vector to copy
	 */
	public void set(Vector other) {
		this.x = other.x;
		this.y = other.y;
	}
	
	/**
	 * Negate a vector. A vector plus its negation has a length of zero.
	 * @return The negated vector
	 */
	public Vector negate() {
		return new Vector(-x,-y);
	}
	
	/**
	 * Compare two vectors. Approximate (a tiny margin of error).
	 * @param other the vector to compare this to
	 * @return A boolean, true when the 2 vectors are similar to within a margin of error
	 */
	public boolean equals(Vector other) {
		float a = Math.abs(other.x - x);
		float b = Math.abs(other.y - y);
		float e = 0.001f; // Very small
		return (a < e && b < e);
	}
	
	/**
	 * Checks if a vector contains another vector. (Meaning other * (0 <= n < 1) = this)
	 * @param other - The vector to compare
	 * @return True if this vector contains the other vector
	 */
	public boolean contains(Vector other) {
		boolean a = this.aligns(other);
		boolean b = Math.abs(other.x) < Math.abs(this.x);
		return a && b;
	}

	/**
	 * Checks if two vectors point the same way.
	 * @param other - The vector to compare
	 * @return A boolean, true if both vectors have the same angle.
	 */
	public boolean aligns(Vector other) {
		float a = other.x * this.y;
		float b = other.y * this.x;
		boolean c = Math.abs(a - b) < 0.0001;
		boolean d = Math.signum(other.x) == Math.signum(this.x);
		return c && d;
	}
	
	/**
	 * Creates a scaled copy
	 * @param f - The factor to scale it by
	 * @return The scaled vector
	 */
	public Vector scaleAndCopy(float f) {
		return new Vector(x * f,y * f);
		// returns a multiple of the original vector.
	}
	
	/**
	 * Produces a vector 1 unit long with the same direction
	 * @return The created vector
	 */
	public Vector unitVector() {
		if (this.length() == 0) {
			return this.scaleAndCopy(1);
		} else {
			return this.scaleAndCopy(1 / this.length());
		}
	}
	
	/**
	 * Creates a vector of the given length and the same direction
	 * @param length - The length to set the vector to
	 * @return The new vector
	 */
	public Vector unitVector(float length) {
		if (this.length() == 0) {
			return this.scaleAndCopy(1);
		} else {
			return this.scaleAndCopy(length / this.length());
		}
	}
	
	/**
	 * Projects this onto other
	 * @param other - The vector to project onto
	 * @return The result of the projection
	 */
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

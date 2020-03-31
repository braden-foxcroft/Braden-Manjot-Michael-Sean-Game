package com.tutorial.main;

import static org.junit.Assert.assertTrue;
// import static org.junit.Assert.fail;

import org.junit.Test;

// a test for vector arithmetic

// TODO Comments by Braden
public class VectorTest {

	/*
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	*/
	
	@Test
	public void newVectorTest() {
		for (int x = -10; x<10; x++) {
			for (int y = -10; y<10; y++) {
				Vector v = new Vector(x,y);
				Vector v2 = new Vector(v);
				v2.add(v);
				v2 = v2.add(v);
				v2 = v2.add(v2);
				assertTrue(v.equals(new Vector(x,y)));
				assertTrue(v2.equals(v.add(v).add(v).add(v)));
			}
		}
	}
	
	@Test
	public void lengthTest() {
		Vector v = new Vector(3,4);
		assertTrue(v.length() == 5);
	}
	
	@Test
	public void addTest() {
		Vector v = new Vector(6,-9);
		Vector t = new Vector(14,32);
		Vector q = v.add(t);
		Vector r = t.add(v);
		assertTrue(v.equals(new Vector(6,-9)));
		assertTrue(q.equals(r));
		assertTrue(r.equals(q));
		assertTrue(r.equals(new Vector(20,23)));
	}

	@Test
	public void copyTest() {
		Vector v = new Vector(5,6);
		Vector n = new Vector(v);
		assertTrue(v.equals(n));
		assertTrue(v.equals(v));
		n.x = 13;
		assertTrue(v.x == 5);
		assertTrue(n.x == 13);
	}

	@Test
	public void scaleAndCopyTest() {
		Vector v = new Vector(8,6);
		Vector n = v.scaleAndCopy(2);
		Vector q = v.scaleAndCopy(1);
		
		assertTrue(v.equals(q));
		assertTrue(v.length() * 2 == n.length());
		assertTrue(v != q);
		// Yeah, this is right. I'm checking pointers.
	}
	
	@Test
	public void projectionTest() {
		Vector v = new Vector(12,33);
		Vector w = new Vector(7,0);
		Vector p = v.projection(w);
		assertTrue(p.equals(new Vector(12,0)));
	}
	
	@Test
	public void projectionTest2() {
		Vector v = new Vector(14,0);
		Vector w = new Vector(0,14);
		Vector p = v.projection(w);
		assertTrue(p.equals(new Vector(0,0)));
	}
	

	@Test
	public void containsTest() {
		Vector v = new Vector(14,0);
		Vector w = new Vector(24,0);
		assertTrue(w.contains(v));
	}
}

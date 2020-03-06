package com.tutorial.main;

import static org.junit.Assert.*;

import org.junit.Test;

public class OurButtonTest {

	@Test
	public void testInside() {
		OurButton b = new OurButton(30, 60, 5, 3, null, "Sup");
		if (b.coordinatesAreInside(33, 62)) {
			assertTrue(true); // success!
		} else {
			fail("Checked coordinates that were inside, but returned false");
		}
	}
	
	@Test
	public void testToLeft() {
		OurButton b = new OurButton(30, 60, 5, 3, null, "Sup");
		if (!b.coordinatesAreInside(29, 61)) {
			assertTrue(true); // success!
		} else {
			fail("Checked coordinates that were to the left of it, but returned True");
		}
	}
	
	@Test
	public void testToRight() {
		OurButton b = new OurButton(30, 60, 5, 3, null, "Sup");
		if (!b.coordinatesAreInside(37, 62)) {
			assertTrue(true); // success!
		} else {
			fail("Checked coordinates that were to the right of it, but returned True");
		}
	}
	
	@Test
	public void testAbove() {
		OurButton b = new OurButton(30, 60, 5, 3, null, "Sup");
		if (!b.coordinatesAreInside(33, 59)) {
			assertTrue(true); // success!
		} else {
			fail("Checked coordinates that were to the above of it, but returned True");
		}
	}
	
	@Test
	public void testUpRight() {
		OurButton b = new OurButton(30, 60, 5, 3, null, "Sup");
		if (!b.coordinatesAreInside(45, 45)) {
			assertTrue(true); // success!
		} else {
			fail("Checked coordinates that were to the up-right of it, but returned True");
		}
	}
	
	@Test
	public void testDownRight() {
		OurButton b = new OurButton(30, 60, 5, 3, null, "Sup");
		if (!b.coordinatesAreInside(40, 69)) {
			assertTrue(true); // success!
		} else {
			fail("Checked coordinates that were to the down-right of it, but returned True");
		}
	}
	
	@Test
	public void testUpLeft() {
		OurButton b = new OurButton(30, 60, 5, 3, null, "Sup");
		if (!b.coordinatesAreInside(20, 40)) {
			assertTrue(true); // success!
		} else {
			fail("Checked coordinates that were to the below of it, but returned True");
		}
	}
	
	@Test
	public void testDownLeft() {
		OurButton b = new OurButton(30, 60, 5, 3, null, "Sup");
		if (!b.coordinatesAreInside(20, 90)) {
			assertTrue(true); // success!
		} else {
			fail("Checked coordinates that were to the below of it, but returned True");
		}
	}
	
	@Test
	public void testCorner() {
		OurButton b = new OurButton(30, 60, 5, 3, null, "Sup");
		if (b.coordinatesAreInside(30, 60)) {
			assertTrue(true); // success!
		} else {
			fail("Checked coordinates that were in the top-left of it, but returned false");
		}
	}
	
	@Test
	public void testOtherCorner() {
		OurButton b = new OurButton(30, 60, 5, 3, null, "Sup");
		if (b.coordinatesAreInside(35, 63)) {
			assertTrue(true); // success!
		} else {
			fail("Checked coordinates that were in the bottom-right, but returned false");
		}
	}
	
	
}

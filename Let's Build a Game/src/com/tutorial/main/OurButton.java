package com.tutorial.main;

import javafx.scene.paint.Color;

public class OurButton {
	private int x,y,width,height;
	private Color color;
	private String text;
	
	public OurButton(int x, int y, int width, int height, Color color, String text) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
		this.text = text;
	}
	
//	Check to see if the x and y are in the area of the button.
	public boolean coordinatesAreInside(int x, int y) {
		return false;
//		TODO Michael, make this work.
//		There are some test cases for your convenience. They are not complete, but still pretty thorough
//		The test cases are only for this function.
	}
	
	public void render (Display d) {
		d.displayButton(x, y, width, height, color, text);
	}
	
}

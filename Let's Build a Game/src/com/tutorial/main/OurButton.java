package com.tutorial.main;

import javafx.scene.paint.Color;

// TODO Comments by Michael
public class OurButton {
	private int x,y,width,height;
	private Color color;
	private String text;
	private MainMenu menu;
	private PauseMenu pause;
	
	public OurButton(int x, int y, int width, int height, Color color, String text) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
		this.text = text;
	}
	
	public void setMenu(MainMenu menu) {
		this.menu = menu;
	}
	public void setPauseMenu(PauseMenu pause) {
		this.pause = pause;
	}
	
	public void menuTriggerClick() {
		menu.menuClickHandler(this.text);
	}
	public void pauseTriggerClick() {
		pause.menuClickHandler(this.text);
	}
	
//	Check to see if the x and y are in the area of the button.
	public boolean coordinatesAreInside(int aX, int aY) {
		if (this.x + this.width >= aX && this.x <= aX && this.y + this.height >= aY && this.y <= aY ) {
			return true;
		}
		else {
		return false;
		}
	}
	
	public void render (Display d) {
		d.displayButton(x, y, width, height, color, text);
	}
	
}

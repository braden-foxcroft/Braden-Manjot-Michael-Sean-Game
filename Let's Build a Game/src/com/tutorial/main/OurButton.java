package com.tutorial.main;

import com.tutorial.display.Display;

import javafx.scene.paint.Color;

public class OurButton {
	/**
	 * Coordinates and size of the button
	 */
	private int x,y,width,height;
	/**
	 * color of the button
	 */
	private Color color;
	/**
	 * Name and text displayed by the button
	 */
	private String text;
	/**
	 * object of the Main menu
	 */
	private MainMenu menu;
	/**
	 * object of the Pause menu
	 */
	private PauseMenu pause;
	/**
	 * Constructor for our buttons
	 * @param x - coordinate of the button on the Horizontal plane
	 * @param y - coordinate of the button on the Vertical plane
	 * @param width - the length of the button on the Horizontal plane
	 * @param height - the length of the button on the Vertical plane
	 * @param color - The color of the button
	 * @param text - Name displayed on button
	 */
	public OurButton(int x, int y, int width, int height, Color color, String text) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
		this.text = text;
	}
	/**
	 * setter for MainMenu
	 * @param menu - MainMenu object
	 */
	public void setMenu(MainMenu menu) {
		this.menu = menu;
	}
	/**
	 * Setter for PauseMenu
	 * @param pause - Pause menu object
	 */
	public void setPauseMenu(PauseMenu pause) {
		this.pause = pause;
	}
	/**
	 * trigger for buttons on the MainMenu
	 */
	public void menuTriggerClick() {
		menu.menuClickHandler(this.text);
	}
	/**
	 * trigger for buttons on PauseMenu
	 */
	public void pauseTriggerClick() {
		pause.menuClickHandler(this.text);
	}
	/**
	 * Method that returns if the coordinates of the click are inside the button
	 * @param aX - coordinate of the click on the horizontal plane
	 * @param aY - coordinate of the click on the vertical plane
	 * @return
	 */
	public boolean coordinatesAreInside(int aX, int aY) {
		if (this.x + this.width >= aX && this.x <= aX && this.y + this.height >= aY && this.y <= aY ) {
			return true;
		}
		else {
		return false;
		}
	}
	/**
	 * Method that is used to display the button
	 * @param d - Display object
	 */
	public void render (Display d) {
		d.displayButton(x, y, width, height, color, text);
	}
	
}

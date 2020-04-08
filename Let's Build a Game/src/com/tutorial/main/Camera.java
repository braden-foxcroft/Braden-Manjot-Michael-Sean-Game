package com.tutorial.main;

/**
 * Creates a camera that follows the user's player and moves around if the map is bigger than the screen
 */
public class Camera {
	
	/**
	 * Top left x coordinate of camera's vision
	 */
	private int cameraX = 0;
	
	/**
	 * Top left y coordinate of camera's vision
	 */
	private int cameraY = 0;
	
	/**
	 * Gets the x-value of the top-left corner of the camera
	 * @return The leftmost x position
	 */
	public int getXPos() {
		return this.cameraX;
	}
	
	/**
	 * Gets the y-value of the top-left corner of the camera
	 * @return The topmost y position
	 */
	public int getYPos() {
		return this.cameraY;
	}
	
	/**
	 *  Takes x position of the object in the arena and returns the x positions of the object
	 *  on the screen
	 */
	public float placeXOnScreen(float x) {
		x -= this.getXPos();
		return x;
	}
	
	/**
	 * Takes y position of the object in the arena and returns the y positions of the object
	 * on the screen
	 */
	public float placeYOnScreen(float y) {
		y -= this.getYPos();
		return y;
	}
	
	/**
	 * Takes the x screen coordinate of a point, and returns the absolute coordinate
	 * @param x - The screen coordinate x
	 * @return The arena (absolute) coordinate x
	 */
	public float reverseEngineerX(float x) {
		return x + this.getXPos();
	}
	
	/**
	 * Takes the y screen coordinate of a point, and returns the absolute coordinate
	 * @param y - The screen coordinate y
	 * @return The arena (absolute) coordinate y
	 */
	public float reverseEngineerY(float y) {
		return y + this.getYPos();
	}

	/**
	 * Aligns the camera to center on the given object
	 * @param thing - The object to center on.
	 */
	public void centerCameraOn(GameObject thing) {
		this.cameraX = (int)(thing.getX() - Game.WIDTH / 2);
		this.cameraY = (int) (thing.getY() - Game.HEIGHT / 2);
	}
	
}

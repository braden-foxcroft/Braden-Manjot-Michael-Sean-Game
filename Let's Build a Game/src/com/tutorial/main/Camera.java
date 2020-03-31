package com.tutorial.main;

public class Camera {
	private int cameraX = 0;
	private int cameraY = 0;
//	These values mark the top-left corner of the camera's vision.
//	They do not mark the center of the camera's vision.
//	To get the width and height of the screen, Game.WIDTH and Game.HEIGHT is used
	
	public int getXPos() {
		return this.cameraX;
	}
	
	public int getYPos() {
		return this.cameraY;
	}
	
	/** Takes x position of the object in the arena and returns the x positions of the object
	 on the screen*/
	public float placeXOnScreen(float x) {
		x -= this.getXPos();
		return x;
	}
	/** Takes y position of the object in the arena and returns the y positions of the object
	 on the screen*/
	public float placeYOnScreen(float y) {
		y -= this.getYPos();
		return y;
	}
	
	public float reverseEngineerX(float x) {
		return x + this.getXPos();
	}
	
	public float reverseEngineerY(float y) {
		return y + this.getYPos();
	}
	//Centers the camera on the object that is passed in
	public void centerCameraOn(GameObject thing) {
		this.cameraX = (int)(thing.getX() - Game.WIDTH / 2);
		this.cameraY = (int) (thing.getY() - Game.HEIGHT / 2);
	}
	
}

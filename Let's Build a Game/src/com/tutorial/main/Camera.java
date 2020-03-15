package com.tutorial.main;

public class Camera {
	private int cameraX = 0;
	private int cameraY = 0;
//	These values mark the top-left corner of the camera's vision.
//	They do not mark the center of the camera's vision.
//	To get the width and height of the screen, use:
//	Game.WIDTH and Game.HEIGHT
	
	public int getXPos() {
		return this.cameraX;
	}
	
	public int getYPos() {
		return this.cameraY;
	}
	
	public float placeXOnScreen(float x) {
//		This function should take the x position of the object in the arena,
//		and return the x position of the object on the screen.
		x -= this.getXPos();
		return x;
	}
	
	public float placeYOnScreen(float y) {
//		This function should take the y position of the object in the arena,
//		and return the y position of the object on the screen.
		y -= this.getYPos();
		return y;
	}
	
	public float reverseEngineerX(float x) {
		return x + this.getXPos();
	}
	
	public float reverseEngineerY(float y) {
		return y + this.getYPos();
	}
	
	public void centerCameraOn(GameObject thing) {
//		This should center the camera on the object that is passed in.
		this.cameraX = (int)(thing.getX() - Game.WIDTH / 2);
		this.cameraY = (int) (thing.getY() - Game.HEIGHT / 2);
	}
	
}

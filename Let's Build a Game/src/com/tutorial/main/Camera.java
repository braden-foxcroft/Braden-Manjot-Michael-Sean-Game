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
//		TODO Manjot, make this.
//		This function should take the x position of the object in the arena,
//		and return the x position of the object on the screen.
		return x;
	}
	
	public float placeYOnScreen(float y) {
//		TODO Manjot, make this.
//		This function should take the y position of the object in the arena,
//		and return the y position of the object on the screen.
		return y;
	}
	
	public void centerCameraOn(GameObject thing) {
//		TODO Manjot, make this.
//		This should center the camera on the object that is passed in.
		this.cameraX = 0;
		this.cameraY = 0;
		
	}
	
}

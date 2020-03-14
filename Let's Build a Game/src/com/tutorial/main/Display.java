package com.tutorial.main;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Display {
	
	private GraphicsContext gC;
	private Camera cam;
	
	public Display(GraphicsContext gC) {
		this.gC = gC;
		this.cam = new Camera();
	}
	
	public void displayObject(DisplayID id, float x, float y, int radius) {
		if (id == DisplayID.Player) {
			gC.setFill(Color.BLUE);
		}
		else if(id == DisplayID.Enemy) {
			gC.setFill(Color.RED);
		}
		else if(id == DisplayID.Obstacle) {
			gC.setFill(Color.WHITE);
		}
		else if(id == DisplayID.Trap) {
			gC.setFill(Color.GREY);
		}
		else if(id == DisplayID.Ball) {
			gC.setFill(Color.GREY);
		}
		else if(id == DisplayID.Bullet) {
			gC.setFill(Color.YELLOW);
		}
		else if(id == DisplayID.EnemyInvincible) {
			gC.setFill(Color.GREEN);
		}
		else if(id == DisplayID.PlayerInvincible) {
			gC.setFill(Color.YELLOW);
		}
		x = cam.placeXOnScreen(x);
		y = cam.placeYOnScreen(y);
		gC.fillOval((int)(x-radius), (int)(y-radius), 2 * radius, 2* radius);
	}
	
//	Draws a button
	public void displayButton(int x, int y, int width, int height, Color color, String text) {
		gC.setFill(color);
		gC.fillRect(x, y, width, height);
		if (color.getBrightness() < Color.DARKGREY.getBrightness())
		{
			gC.setFill(Color.WHITE);
		}
		else
		{
			gC.setFill(Color.BLACK);
		}
		gC.fillText(text, x + (width/2), y + (height/2));
	}

	public void drawBorders() {
//		TODO Manjot, make this
//		Using the camera position, work out where to properly display the borders
//		Display them however you like, so long as you can see that you hit them
//		when you reach the edge of the map.
		
	}
	
	public void drawHealthBar(float x, float y, int radius, int health, int maxHealth) {
//		TODO Manjot, make this
//		Draw the health bar below the corresponding character
//		Use the x and y, and radius to place the bar below the character.
//		Leave a pixel or two between the bottom of the character and the health bar
//		Make the health bar have the same width as the character.
//		Display the bar as a series of segments, where you can clearly see
//		How much damage has been taken, and how much health remains.
		Color barBackgroundColor = Color.WHEAT;
		Color barColor = Color.LAWNGREEN;
		
		
	}
	
	public void updateCamera(GameObject thing) {
		cam.centerCameraOn(thing);
	}
	
//	Draw a rectangle
	public void displayRectangle(int x, int y, int width, int height, Color color) {
		gC.setFill(color);
		gC.fillRect(x, y, width, height);
	}
	
	public void setupNextFrame() {
		gC.setFill(Color.BLACK);
		gC.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
	}

}

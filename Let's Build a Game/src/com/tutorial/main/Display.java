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
		else if(id == DisplayID.Ally) {
			gC.setFill(Color.CYAN);
		}
		else if(id == DisplayID.Obstacle) {
			gC.setFill(Color.WHITE);
		}
		else if(id == DisplayID.Trap) {
			gC.setFill(Color.GREY);
		}
		else if(id == DisplayID.DasherTrap) {
			gC.setFill(Color.color(0.5, 0.3, 0.5));
		}
		else if(id == DisplayID.Ball) {
			gC.setFill(Color.DARKGOLDENROD.darker());
		}
		else if(id == DisplayID.Bullet) {
			gC.setFill(Color.YELLOW);
		}
		else if(id == DisplayID.PrisonBullet) {
			gC.setFill(Color.color(0.7, 0.3, 0.3));
		}
		else if(id == DisplayID.BulletUntouchable) {
			gC.setFill(Color.color(0.1, 0.4, 0.4));
		}
		else if(id == DisplayID.PrisonBulletUntouchable) {
			gC.setFill(Color.color(0.1, 0.1, 0.1));
		}
		else if(id == DisplayID.EnemyInvincible) {
			gC.setFill(Color.GREEN);
		}
		else if(id == DisplayID.AllyInvincible) {
			gC.setFill(Color.DARKSEAGREEN);
		}
		else if(id == DisplayID.PlayerInvincible) {
			gC.setFill(Color.YELLOW);
		} else {
			System.err.println("Tried to display object of unknown type. (" + id + ")");
			System.err.println("Please modify Display.java to add the display ID behavior");
			System.exit(0);
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
		Camera c = cam;
		int tlx = (int) c.placeXOnScreen(0);
		int tly = (int) c.placeYOnScreen(0);
		int width = Game.arenaWidth;
		int height = Game.arenaHeight;
		int brx = (int) c.placeXOnScreen(width);
		int bry = (int) c.placeYOnScreen(height);
		gC.setFill(Color.WHITE);
		//Top border
		gC.fillRect(tlx, tly - 5, width, 5);
		//Left border
		gC.fillRect(tlx - 5, tly - 5, 5, height + 10);
		//Bottom border
		gC.fillRect(tlx - 5, bry, width + 5, 5);
		//Right border
		gC.fillRect(brx, tly - 5, 5, height + 10);
		
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
		Color secondBarColor = Color.DARKGRAY;
		Camera c = cam;
		int hbx = (int) c.placeXOnScreen(x);
		int hby = (int) c.placeYOnScreen(y);
		gC.setFill(barBackgroundColor);
		//Top of health bar
		gC.fillRect(hbx - 25, hby + 30, 50, 5);
		//Left part of health bar
		gC.fillRect(hbx - 25, hby + 30, 5, 15);
		//Bottom part of health bar
		gC.fillRect(hbx - 25, hby + 45, 50, 5);
		//Right part of health bar
		gC.fillRect(hbx + 20, hby + 30, 5, 20);
		gC.setFill(secondBarColor);
		//Making the individual health bars inside the bigger health bar
		float segmentSize = (40 / maxHealth);
		for (int i = 1; i <= maxHealth; i++) {
			float segmentPlacement = (hbx - 20) + ((i - 1) * segmentSize);
			if (i <= health) {
				gC.setFill(barColor);
			}
			else {
				gC.setFill(secondBarColor);
			}
			gC.fillRect(segmentPlacement, hby + 35, segmentSize, 10);
		}
		
		
	}
	
	public void updateCamera(GameObject thing) {
		cam.centerCameraOn(thing);
	}
	
	public Camera getCamera() {
		return this.cam;
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

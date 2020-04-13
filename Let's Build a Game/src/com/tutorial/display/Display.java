package com.tutorial.display;


import com.tutorial.main.Camera;
import com.tutorial.main.Game;
import com.tutorial.main.GameObject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * This class displays everything on screen (the GUI). It is called in a lot of the other classes whenever anything
 * needs to be displayed
 */
public class Display {
	
	/**
	 * Instance variable to call on the canvas of javafx to draw objects
	 */
	private GraphicsContext gC;
	
	/**
	 * Instance variable that calls from the camera method to and display an object within the camera in this class
	 */
	private Camera cam;
	
	/**
	 * Constructor for display class
	 * @param gC - graphics from canvas that will be drawn
	 */
	public Display(GraphicsContext gC) {
		this.gC = gC;
		this.cam = new Camera();
	}
	
	/**
	 * Creates the graphics display of objects based on their enumeration from the DisplayID enumeration
	 * @param id - calls the id from DisplayID enumeration and depending on that ID, an object is drawn to
	 * represent the object for that ID
	 * @param x - the x coordinate of an object being called (from middle of object)
	 * @param y - the y coordinate of the object being called (from middle of object)
	 * @param radius - radius of object being called
	 */
	public void displayObject(DisplayID id, float x, float y, int radius) {
//		A bunch of conditionals which set the color of objects depending on what they represent (player,
//		enemies, allies, and traps)
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
//		When enemy takes damage they are invincible for a second, this is shown with a different color
		else if(id == DisplayID.EnemyInvincible) {
			gC.setFill(Color.GREEN);
		}
//		When ally takes damage they are invincible for a second, this is shown with a different color
		else if(id == DisplayID.AllyInvincible) {
			gC.setFill(Color.DARKSEAGREEN);
		}
//		When player takes damage they are invincible for a second, this is shown with a different color
		else if(id == DisplayID.PlayerInvincible) {
			gC.setFill(Color.YELLOW);
		} else {
			System.err.println("Tried to display object of unknown type. (" + id + ")");
			System.err.println("Please modify Display.java to add the display ID behavior");
			System.exit(0);
		}
//		Places objects on the screen using the Camera method
		x = cam.placeXOnScreen(x);
		y = cam.placeYOnScreen(y);
//		Sets the shape of the objects
		gC.fillOval((int)(x-radius), (int)(y-radius), 2 * radius, 2* radius);
	}
	
	/**
	 * Creates a display for a button of a specific size and color (called primarily in the menu class)
	 * @param x - the x coordinate of the top left corner of the button
	 * @param y - the y coordinate of the top left corner of the button
	 * @param width - The width of the button
	 * @param height - the height of the button
	 * @param color - the color of the button
	 * @param text - text that will show on the button
	 */
	public void displayButton(int x, int y, int width, int height, Color color, String text) {
		
		//Sets color
		gC.setFill(color);
		
		//Creates a rectangle of a specific width and height
		gC.fillRect(x, y, width, height);
		
//		Sets the color of the button depending on the brightness of the text so that a dark text and dark background
//		or light text and light background don't coincide and make things unreadable
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
	/**
	 * Creates a border to cover the edges of the screen where characters can move around in. Allows the user to
	 * see the edges of the map
	 */
	public void drawBorders() {
		
//		Creates a new Camera for the method
		Camera c = cam;
		
//		x coordinate of top left corner of the map
		int tlx = (int) c.placeXOnScreen(0);
		
//		y coordinate of top left corner of the map
		int tly = (int) c.placeYOnScreen(0);
		
//		Width of the arena
		int width = Game.arenaWidth;
		
//		Height of the arena
		int height = Game.arenaHeight;
		
//		x coordinate of bottom right corner of the map
		int brx = (int) c.placeXOnScreen(width);
		
//		y coordinate of bottom right corner of the map
		int bry = (int) c.placeYOnScreen(height);
		
//		Sets a color for the border
		gC.setFill(Color.WHITE);
		
//		Top of the border
		gC.fillRect(tlx, tly - 5, width, 5);
		
//		Left of the border
		gC.fillRect(tlx - 5, tly - 5, 5, height + 10);
		
//		Bottom of the border
		gC.fillRect(tlx - 5, bry, width + 5, 5);
		
//		Right of the border
		gC.fillRect(brx, tly - 5, 5, height + 10);
		
	}
	
	/**
	 * Creates a health bar display that will show below characters (player and enemy), indicating how much health
	 * they have
	 * @param x - x coordinate of the character (from middle of character)
	 * @param y - y coordinate of the character (from middle of character)
	 * @param radius - Radius of character
	 * @param health - Health remaining of character
	 * @param maxHealth - The max health the character can have
	 */
	public void drawHealthBar(float x, float y, int radius, int health, int maxHealth) {
		
//		Width of the edges of health bar
		int edge = 5;
		
//		Radius of characters
		radius = 25;
		
//		Border of health bar
		Color barBackgroundColor = Color.WHEAT;
		
//		Color in health bar for remaining health
		Color barColor = Color.LAWNGREEN;
		
//		Color in health bar for lost health
		Color secondBarColor = Color.DARKGRAY;
		
//		Creates new Camera from camera method
		Camera c = cam;
		
//		Places x coordinate of character on screen
		int hbx = (int) c.placeXOnScreen(x);
		
//		Places y coordinate of character on screen
		int hby = (int) c.placeYOnScreen(y);

//		Sets color of edges
		gC.setFill(barBackgroundColor);

//		Top part of health bar (adding a little bit to radius to make sure health bar is not touching the character
//		but is seen clearly underneath character, which is why the edge distance is subtracted)
		gC.fillRect(hbx - radius, hby + radius + edge, radius * 2, edge);
		
//		Left part of health bar (height does not count the space covered by upper and lower edges of health bar,
//		hence, 2 * edge is subtracted to only count that part within the health bar)
		gC.fillRect(hbx - radius, hby + radius + edge, edge, radius - (2 * edge));
		
//		Bottom part of health bar (Again, we have to consider that there is an edge to the health bar and the
//		health has to be displayed inside that which makes the math complicated)
		gC.fillRect(hbx - radius, hby +	(2 * radius - edge), radius * 2, edge);
		
//		Right part of health bar
		gC.fillRect(hbx + (radius - edge), hby + (radius + edge), edge, radius - edge);
		
//		Sets color for health that is lost
		gC.setFill(secondBarColor);
		
//		Making the individual health bars inside the bigger health bar (since the length inside the health bar
//		is 40 (removing the 5 length on each side for the edges), dividing that by the maxHealth gives how many
//		bars of health are needed.
		float segmentSize = (40 / maxHealth);
		for (int i = 1; i <= maxHealth; i++) {
//			Creates the width of each box that is made in the health bar (for each piece of health depending on
//			maxHealth
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
	
	/**
	 * Centers camera on the player
	 * @param thing - Object that gets centered on the camera (player for this game)
	 */
	public void updateCamera(GameObject thing) {
		cam.centerCameraOn(thing);
	}
	
	/**
	 * getter for Camera
	 * @return this.cam - Returns the camera to be called in Camera class
	 */
	public Camera getCamera() {
		return this.cam;
	}
	
	/**
	 * Draw a rectangle (used for menu)
	 * @param x - the x coordinate of the top left corner of the rectangle
	 * @param y - the y coordinate of the top left corner of the rectangle
	 * @param width - The width of the rectangle
	 * @param height - the height of the rectangle
	 * @param color - the color of the rectangle
	 */
	public void displayRectangle(int x, int y, int width, int height, Color color) {
		gC.setFill(color);
		gC.fillRect(x, y, width, height);
	}

	/**
	 * 
	 */
	public void setupNextFrame() {
		gC.setFill(Color.BLACK);
		gC.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
	}
	
}

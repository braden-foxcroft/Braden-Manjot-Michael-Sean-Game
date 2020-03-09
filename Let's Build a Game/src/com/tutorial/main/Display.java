package com.tutorial.main;

import java.awt.Graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Display {
	
	private Graphics g;
	private GraphicsContext gC;

	public Display(Graphics g) {
		this.g = g;
	}
	
	public Display(GraphicsContext gC) {
		this.gC = gC;
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
		else if(id == DisplayID.EnemyInvincible) {
			gC.setFill(Color.GREEN);
		}
		else if(id == DisplayID.PlayerInvincible) {
			gC.setFill(Color.YELLOW);
		}
		else if(id == DisplayID.DamagedEnemy){
			gC.setFill(Color.DARKRED);
		}
		else if(id == DisplayID.DamagedPlayer){
			gC.setFill(Color.DARKBLUE);
		}
		gC.fillOval((int)(x-radius), (int)(y-radius), 2 * radius, 2* radius);
	}
	
//	Draws a button
	public void displayButton(int x, int y, int width, int height, Color color, String text) {
		gC.setFill(color);
		gC.fillRect(x, y, width, height);
		gC.setFill(color);
		gC.fillText(text, x + (width/2), y + (height/2));
//		TODO Manjot, implement this
//		Make it so that the text changes color to make sure that it contrasts
	}

	// draw a rectangle
	public void displayRectangle(int x, int y, int width, int height, Color color) {
		gC.setFill(color);
		gC.fillRect(x, y, width, height);
	}
	
	public void screenBackground() {
		gC.setFill(Color.BLACK);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
	}
	
	public void setupNextFrame() {
		gC.setFill(Color.BLACK);
		gC.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
	}

}

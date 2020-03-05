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
		else if(id == DisplayID.ObstTrapBasic) {
			gC.setFill(Color.WHITE);
		}
		else if(id == DisplayID.EnemyInvincible) {
			gC.setFill(Color.GREEN);
		}
		else if(id == DisplayID.PlayerInvincible) {
			gC.setFill(Color.YELLOW);
		}
		gC.fillOval((int)(x-radius), (int)(y-radius), 2 * radius, 2* radius);
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
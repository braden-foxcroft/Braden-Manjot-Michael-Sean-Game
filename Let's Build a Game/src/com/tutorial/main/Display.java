package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Display {
	
	private Graphics g;

	public Display(Graphics g) {
		this.g = g;
	}
	
	public void displayObject(DisplayID id, float x, float y, int radius) {
		if (id == DisplayID.Player) {
			g.setColor(Color.blue);
		}
		else if(id == DisplayID.Enemy) {
			g.setColor(Color.red);
		}
		else if(id == DisplayID.ObstTrapBasic) {
			g.setColor(Color.white);
		}
		else if(id == DisplayID.EnemyInvincible) {
			g.setColor(Color.green);
		}
		else if(id == DisplayID.PlayerInvincible) {
			g.setColor(Color.yellow);
		}
		g.fillOval((int)(x-radius), (int)(y-radius), 2 * radius, 2* radius);
	}
	
	public void screenBackground() {
		g.setColor(Color.black);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
	}
	
	public void update(BufferStrategy bs) {
		g.dispose();
		bs.show();
	}

}

package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;

public class Player extends GameObject{

	private boolean goRight = true;
	private boolean goLeft = false;
	private int downH = 0;
	
	public Player(int x, int y, ID id) {
		super(x, y, id);
	}

	public void tick() {
		if (this.downH <= this.y && !this.goLeft && !this.goRight) {
			if (this.x <= 0) {
				this.goRight = true;
			}
			else
			{
				this.goLeft = true;
			}
		}
		else if (this.x >= 635-50 && this.goRight) {
			this.goRight = false;
			this.downH += 50;
		}
		else if (this.x < 0 && this.goLeft) {
			this.goLeft = false;
			this.downH += 50;
		}
		if (goRight) {
			this.x++;
		}
		else if(goLeft)
		{
			this.x--;
		}
		else
		{
			this.y++;
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(x, y, 50, 50);
	}
	
	
}

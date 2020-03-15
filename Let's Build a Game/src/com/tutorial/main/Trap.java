package com.tutorial.main;

import java.util.Random;

public class Trap extends Character {
	
	Random r = new Random();
	

	public Trap(int x, int y, ID id, Handler handler, int aRadius) {
		super(x, y, id, handler);
		this.anchored = true;
		this.radius = aRadius;
		this.damaging = false;
	}

	
	public void tick() {
		
		
	}
	

	public void hitWall() {
		// do Nothing, this will be an anchored object.
		
	}

	public void constrain() {
		
	}

	public void planDeath() {
		this.health = 0;
		Handler.time_To_Die();
	}

	public void onCollision(GameObject other) { // need to change this from GameObject to Character
		if (this.health != 0) {					// in order to use Heal?
			int tempX = (int)this.getX();
			int tempY = (int)this.getY();
			
			Random r = new Random();
			int trapType = r.nextInt(3); // change this range to enable later traps
			
//			TODO initialize trap type on creation
//			TODO Have multiple trap display types
			
			if (trapType == 0) {
//				Create an ball
				Ball bTemp = new Ball(tempX, tempY, ID.Ball, handler);
				handler.addObject(bTemp);
				Vector dir = new Vector(other,this);
				float speed = r.nextFloat() * 8;
				dir = dir.unitVector(speed);
				bTemp.launchAround(dir);
				bTemp.inheritRadius(this);
				this.planDeath();
			}
			else if (trapType == 1) {
//				Create an enemy
				handler.addObject(new Enemy(tempX,tempY,ID.Enemy, handler));
				this.planDeath();
			}
			
			else if (trapType == 2) {
//				Create bullets
				float speed = r.nextFloat() * 12;
				for (int i = -1; i <= 1 ; i++) {
					for (int j = -1; j <= 1; j++)
					{
						if (i != 0 || j != 0) {
							Bullet bulletBill = new Bullet(tempX + i,tempY + j,ID.Bullet, handler);
							handler.addObject(bulletBill);
							Vector dir = new Vector(i,j);
							dir = dir.unitVector(speed);
							bulletBill.launchAround(dir);
						}
					}
				}
				this.planDeath();
			}
			else if ((trapType == 3)||(trapType == 4)) {
//				Heal something
				
			}
			
		}
	}
	
	public void render(Display d) {
		d.displayObject(DisplayID.Trap, this.x, this.y, radius);
	}



}

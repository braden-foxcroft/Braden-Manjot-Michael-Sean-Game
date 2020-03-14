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


	public void onCollision(GameObject other) { // need to change this from GameObject to Character
		if (this.health != 0) {					// in order to use Heal?
			int tempX = (int)this.getX();
			int tempY = (int)this.getY();
			
			this.health = 0;
			Handler.time_To_Die();
			
			Random r = new Random();
			int trapType = r.nextInt(3); // change this range to enable later traps
			if (trapType == 0) {
				Ball bTemp = new Ball(tempX, tempY, ID.Ball, handler);
				handler.addObject(bTemp);
				bTemp.slowerLaunchAround();
			}
			else if (trapType == 1) {handler.addObject(new Enemy(tempX,tempY,ID.Enemy, handler));}
			
			else if (trapType == 2) {
				for (int i = 0;i<5;i++) {
					Bullet bulletBill = new Bullet(tempX,tempY,ID.Bullet, handler);
					handler.addObject(bulletBill);
					bulletBill.launchAround();
				}
			}
			else if ((trapType == 3)||(trapType == 4)) {
				Vector Vtemp = new Vector(this,other);
				if ((Vtemp.length() < 10.0)&&(other.getId()==ID.Player)||(other.getId()==ID.Enemy)) { 
					// TODO find out the appropriate AOE for heal
					// TODO get the right object healed
				}
			}
			
		}
	}
	
	public void render(Display d) {
		d.displayObject(DisplayID.Trap, this.x, this.y, radius);
	}



}

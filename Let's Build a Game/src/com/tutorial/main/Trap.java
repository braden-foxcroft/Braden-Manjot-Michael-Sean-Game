package com.tutorial.main;

import java.util.Random;
// Traps are a collision activated gameObject with a randomized variety of effects. 
// traps generation and placement done in the Handler class, setup method.

import com.tutorial.display.Display;
import com.tutorial.display.DisplayID;

// TODO Comments by Sean
public class Trap extends Character {
	
	Random r = new Random();
	
// parameters all passed from Handler setup method
	public Trap(int x, int y, ID id, Handler handler, int aRadius) {
		super(x, y, id, handler);
		this.anchored = true;
		this.radius = aRadius;
		this.damaging = false;
	}

/** A routine that acts once per tick of the game. 
 * 	
 */
	public void tick() {
		this.invincibleUpdate();
	}
/** adds objects to their respective lists in the handler	
 * 
 */
	public void addTo(Handler h) {
		h.obstacles.add(this);
		h.object.add(this);
	}
/**	removes objects from their respective lists in the handler	
 * 
 */
	public void removeFrom(Handler h) {
		h.obstacles.remove(this);
		h.object.remove(this);
	}

	public void hitWall() {
		// do Nothing, this will be an anchored object.
	}
/** Determines if the conditions for death of the object have been met,
 *  Calls for the Handler to remove it at the end of the tick. 
 */
	public void planDeath() {
		this.health = 0;
		Handler.time_To_Die();
	}

	public void onCollision(GameObject other) { 
		// This method is special because the trap actually triggers
		
		// avoids traps being set off by other traps
		if (other.id != ID.Enemy && other.id != ID.Player)
		{
			return;
		}
		// avoids traps being set off multiple times
		if (this.health < 1) {
			return;
		}
		// creates temporary place holders for the traps position
		int tempX = (int)this.getX();
		int tempY = (int)this.getY();
		
//		chooses the effect of the trap randomly based on a list of integers
//		each of which corresponds with a different trap effect
		
		Random r = new Random();
		int trapType = r.nextInt(6); // change this range to enable later traps
		
		
		if (trapType == 0) {
//			Creates an anchored ball and launches it in a randomized direction
//			Ball is given a set amount of time to exist in the Ball class.
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
//			Create an enemy (or ally)
			if (other.id == ID.Enemy) {
				handler.addObject(new Ally(tempX,tempY,ID.Ally, handler));
			} else {
				handler.addObject(new Enemy(tempX,tempY,ID.Enemy, handler));
			}
			this.planDeath();
		}
		else if (trapType == 2) {
//			Create bullets
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
		else if (trapType == 3) {
//			Heal whatever hit it
			other.doSkill("heal",1);
			this.planDeath();
		}
		else if (trapType == 4) {
			DasherTrap dT = new DasherTrap((int)x, (int)y, ID.Trap, handler, radius, other);
			handler.addObject(dT);
			this.planDeath();
		}
		else if (trapType == 5) {
//			Create nearly-invisible bullets,
//			Which materialize and then do damage
			other.setVelocity(new Vector(0,0));
			float speed = 8;
			int total = 3;
			for (int i = -total; i <= total ; i++) {
				for (int j = -total; j <= total; j++)
				{
					if (i != 0 || j != 0) {
						PrisonBullet b;
						b = new PrisonBullet(tempX + i,tempY + j,ID.Bullet, handler);
						handler.addObject(b);
						Vector dir = new Vector(i,j);
						dir = dir.unitVector(speed);
						b.launchAround(dir);
					}
				}
			}
			this.planDeath();
		}
		
	}
/**	display the object
 * passes the instance variable references to the display class
 */	
	public void render(Display d) {
		d.displayObject(DisplayID.Trap, this.x, this.y, radius);
		if (this.health < MAXHEALTH)
		{
			d.drawHealthBar(x, y, radius, health, MAXHEALTH);
		}
	}
/** toString method that displays the instance variables of the class
 * 	
 */
	public String toString() {
		String result = "";
		result += this.id; 
		result += ",";
		result += this.radius;
		result += ",";
		result += this.x;
		result += ",";
		result += this.y;
		result += ",";
		result += this.velX;
		result += ",";
		result += this.velY;
//		result += ",";
//		result += this.anchored;
//		result += ",";
//		result += this.damaging;
		result += ",";
		
		return result;
	}



}

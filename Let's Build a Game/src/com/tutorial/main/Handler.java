package com.tutorial.main;

import java.awt.Component;
// import java.awt.Graphics;
import java.awt.MouseInfo;
import java.util.LinkedList;
import java.util.Random;

// This class contains every GameObject, and the boolean state of each key.
// Its purpose is to handle interactions between objects.
// It also handles render and tick methods
// Contains copied code.

public class Handler {
	
	LinkedList<GameObject> object = new LinkedList<GameObject>();
	private int playerIndex = -1; // Used to track the index of the player. -1 means no player.
	private Component canvas; // Required to accurately get the mouse position.
	private boolean w_Down = false; // Key states. Stored for convenience.
	private boolean a_Down = false;
	private boolean s_Down = false;
	private boolean d_Down = false;
	private boolean shift_Down = false;
	private boolean num1_Down = false;
	private boolean q_Down = false;
	private boolean num2_Down = false;
	private boolean space_Down = false;
	private boolean esc_Down = false;
	public boolean ignore_Space = false;
	private static boolean check_Death = false; // a flag that means something is about to die.
	
//	Occurs every tick. Causes all objects to update and all collisions to occur.
	public void tick(){
//		Player actions
		if (playerIndex != -1) {
			if (this.isW_Down()) {this.player().accelY(-1);}
			if (this.isA_Down()) {this.player().accelX(-1);}
			if (this.isS_Down()) {this.player().accelY(1);}
			if (this.isD_Down()) {this.player().accelX(1);}
			if (this.isShift_Down()) {
				float cons = 15f;
				Vector start = new Vector(player());
				if (start.length() == 0) {
					start.set(new Vector(1,0));
				}
				Vector result = start.scaleAndCopy(cons / start.length());
				player().setVelocity(result);
				this.setShift_Down(false);
			}
			if (this.isQ_Down()) {
				for (GameObject o: this.object) {
					if (o.id == ID.Enemy) {
						Vector disp = new Vector(player(),o);
						disp = disp.scaleAndCopy(1 / disp.length() / disp.length());
						Vector push = disp.scaleAndCopy(3000f);
						Vector now = new Vector(o);
						now = now.add(push);
						o.setVelocity(now);
						
					}
				}
				this.setQ_Down(false);
			}
		}
		if (this.isSpace_Down() && !ignore_Space) {
			this.removeByID(ID.ObstTrap);
			this.setup();
			this.ignore_Space = true;
		}
		if (this.isEsc_Down()) {
			System.exit(1);
		}
//		update each object
		for (int i = 0; i < object.size(); i++)
		{
			GameObject tempObject = object.get(i);
			if (tempObject.id == ID.Ball) {tempObject.anchored = this.space_Down;}
			tempObject.tick(); // update all
		}
//		Begin collision checks
		for (int first = 0; first < object.size(); first++) {
			for (int second = first + 1; second < object.size(); second++) {
				GameObject a = object.get(first);
				GameObject b = object.get(second);
				if (isHitting(a,b))
				{
					if (a.anchored) {
						a = object.get(second);
						b = object.get(first);
					}
					a.hit(b);
					// collision checker.
				}
			}
		}
		// check for death, when needed
		if (check_Death) {
			for (int thing = 0; thing < object.size(); thing++) {
				GameObject a = object.get(thing);
				if (a.check_Death()) {
					removeObject(a);
					thing = 0;
				}
			}
		}
		
	}

	public boolean isHittingAnything(GameObject isThisOkay) {
		for (GameObject a:this.object) {
			if (isHitting(a, isThisOkay))
			{
				return true;
			}
		}
		return false;
	}
	
	private boolean isHitting(GameObject me, GameObject you) {
		return me.distance(you) < (me.getRadius() + you.getRadius());
	}
	
	public void setup() {
		this.addObject(new Player(320,300,ID.Player, this));
		// this.addObject(new Ball(200,200,ID.Ball, this));
		this.addObject(new Enemy(640,300,ID.Enemy, this));
		Random r = new Random();
		for(int i = 0 ; i < 8 ; i++) {
			ObstTrap o = new ObstTrap(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), ID.ObstTrap, this, r.nextInt(60)+5);
			if (!isHittingAnything(o)) {
				this.addObject(o);
			} else {
				i--;
			}
		}
	}
	
	public void removeAll() {
		while (objectCount() > 0) {
			this.removeObject(this.object.get(0));
		}
	}
	
	public int objectCount() {
		return this.object.size();
	}
	
	public void removeByID(ID id) {
		GameObject o;
		for (int i = 0; i<objectCount(); i++) {
			o = this.object.get(i);
			if (o.getId() == id) {
				this.removeObject(o);
				i--;
			}
		}
	}
	
	
	
//	Render each object.
	public void render(Display d) {
		for (int i = 0; i < object.size(); i++)
		{
			GameObject tempObject = object.get(i);
			tempObject.render(d);
		}
	}
	
//	Render each object for the text version.
	public void textRender(String[][] board) {
		if (TextGame.textGameActive) {
			System.out.println("hander Render");
			TextGame.padding++;
		}
		for (int i = 0; i < object.size(); i++)
		{
			GameObject tempObject = object.get(i);
			tempObject.textRender(board);
		}
	}
	
//	Create a new object. Block extra players from being made.
	public void addObject(GameObject o) {
		boolean debug = false;
		if (o.id == ID.Player) {
			if (this.playerIndex == -1) {
				this.object.add(o);
				this.playerIndex = this.object.size() - 1;
//				Only one player permitted!
//				Update the index of the player
				if (debug) {System.out.println("New " + o.id);}
			}
			else
			{
				if (debug) {System.out.println("Could not make new " + o.id);}
			}
		}
		else
		{
			this.object.add(o);
			if (debug) {System.out.println("New " + o.id);}
		}
	}
	
	
//	Removes an object from the game.
	public void removeObject(GameObject o) {
		if (o.id == ID.Player) {
			this.playerIndex = -1;
		}
		this.object.remove(o);
		updatePlayerID();
	}

	private void updatePlayerID() {
		this.playerIndex = -1;
		for (int i = 0; i < this.objectCount(); i++) {
			GameObject ob = this.object.get(i);
			if (ob.id == ID.Player) {
				this.playerIndex = i;
			}
		}
	}
	
//	Returns the player object, or null if it doesn't exist. Convenient.
	public GameObject player() {
		if (this.playerIndex == -1) {
			return null;
		}
		else
		{
			return this.object.get(playerIndex);
		}
	}

//	Getters and setters.
	public boolean isW_Down() {
		return w_Down;
	}

	public void setW_Down(boolean w_down) {
		this.w_Down = w_down;
	}

	public boolean isA_Down() {
		return a_Down;
	}

	public void setA_Down(boolean a_down) {
		this.a_Down = a_down;
	}

	public boolean isS_Down() {
		return s_Down;
	}

	public void setS_Down(boolean s_down) {
		this.s_Down = s_down;
	}

	public boolean isD_Down() {
		return d_Down;
	}

	public void setD_Down(boolean d_down) {
		this.d_Down = d_down;
	}
	
//	Get mouse info. Requires that canvas be properly set up.
	public int getMouseX() {
		return MouseInfo.getPointerInfo().getLocation().x
				- canvas.getLocationOnScreen().x;
		// gets the mouse position.
	}
	
	public int getMouseY() {
		return MouseInfo.getPointerInfo().getLocation().y
				- canvas.getLocationOnScreen().y;
		// gets the mouse position.
	}
	
//	Find the canvas. This should only be called once
	public void setCanvas(Component canvas) {
		this.canvas = canvas;
		// gets the canvas. Needed to get the mouse position.
	}

//	More getters and setters
	public boolean isSpace_Down() {
		return space_Down;
	}

	public void setSpace_Down(boolean space_Down) {
		this.space_Down = space_Down;
	}
	
//	Inform every handler that it is time to check for deaths. False positives are fine.
	public static void time_To_Die() {
		check_Death = true;
	}

	public boolean isShift_Down() {
		return shift_Down;
	}

	public void setShift_Down(boolean shift_Down) {
		this.shift_Down = shift_Down;
	}

	public boolean isNum1_Down() {
		return num1_Down;
	}

	public void setNum1_Down(boolean num1_Down) {
		this.num1_Down = num1_Down;
	}

	public boolean isNum2_Down() {
		return num2_Down;
	}

	public void setNum2_Down(boolean num2_Down) {
		this.num2_Down = num2_Down;
	}

	public boolean isEsc_Down() {
		return esc_Down;
	}

	public void setEsc_Down(boolean esc_Down) {
		this.esc_Down = esc_Down;
	}

	public boolean isQ_Down() {
		return q_Down;
	}

	public void setQ_Down(boolean q_Down) {
		this.q_Down = q_Down;
	}
	
}

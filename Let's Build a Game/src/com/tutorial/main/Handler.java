package com.tutorial.main;

// import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

import javafx.scene.input.KeyCode;

// This class contains every GameObject, and the boolean state of each key.
// Its purpose is to handle interactions between objects.
// It also handles render and tick methods
// Contains copied code.

public class Handler {
	
	LinkedList<GameObject> object = new LinkedList<GameObject>();
	private int playerIndex = -1; // Used to track the index of the player. -1 means no player.
	private Keylist kL;
	private static boolean check_Death = false; // a flag that means something is about to die.
	GameState state = GameState.Play;
	
//	Occurs every tick. Causes all objects to update and all collisions to occur.
	public void tick(){
		if (state == GameState.MainMenu) {
			//implement soon
		}
		else if (kL.justPressed(KeyCode.ESCAPE) && state == GameState.Pause) {
			System.exit(1);;
		}
		else if (state == GameState.Pause) {
			//more to implement
		}
		else if (state == GameState.Play) {
//		Player actions
		if (playerIndex != -1) {
			if (kL.isPressed(KeyCode.W)) {this.player().accelY(-1);}
			if (kL.isPressed(KeyCode.A)) {this.player().accelX(-1);}
			if (kL.isPressed(KeyCode.S)) {this.player().accelY(1);}
			if (kL.isPressed(KeyCode.D)) {this.player().accelX(1);}
			if (kL.isPressed(KeyCode.SHIFT)) {
				float cons = 15f;
				Vector start = new Vector(player());
				if (start.length() == 0) {
					start.set(new Vector(1,0));
				}
				Vector result = start.scaleAndCopy(cons / start.length());
				player().setVelocity(result);
			}
			if (kL.justPressed(KeyCode.Q)) {
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
			}
		}
		if (kL.justPressed(KeyCode.SPACE)) {
			this.removeByID(ID.ObstTrap);
			this.setup();
		}
		if (kL.isPressed(KeyCode.ESCAPE)) {
			state = GameState.Pause;
			//System.exit(1);
		}
//		update each object
		for (int i = 0; i < object.size(); i++)
		{
			GameObject tempObject = object.get(i);
			if (tempObject.id == ID.Ball) {tempObject.anchored = kL.isPressed(KeyCode.SPACE);}
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
//		this.addObject(new Ball(200,200,ID.Ball, this));
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
		d.setupNextFrame();
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
	
//	Add a keyList object
	public void addKeyboard(Keylist kL) {
		this.kL = kL;
	}
	
//	Get the keyBoard object
	public Keylist keys() {
		return this.kL;
	}
	
	
//	Inform every handler that it is time to check for deaths. False positives are fine.
	public static void time_To_Die() {
		check_Death = true;
	}

}

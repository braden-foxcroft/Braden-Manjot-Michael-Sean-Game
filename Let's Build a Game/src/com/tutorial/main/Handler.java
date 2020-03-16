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
	
//	A list of every game object
	public LinkedList<GameObject> object = new LinkedList<GameObject>();
//	A list of every ally
	public LinkedList<Character> allies = new LinkedList<Character>();
//	A list of every enemy
	public LinkedList<Character> enemies = new LinkedList<Character>();
//	The player
	public Player player = null;
	private Keylist kL;
	private static boolean check_Death = false; // a flag that means something is about to die.
	private GameState gState = GameState.MainMenu;
	private MainMenu menu = null;
	private PauseMenu pause = null;
	private MouseClickHandler clickHandler = null;
	
//	Occurs every tick. Causes all objects to update and all collisions to occur.
	public void tick(){
		if (kL.justPressed(KeyCode.ESCAPE) && gState == GameState.Pause) {
			System.exit(1);
		}
		if (gState == GameState.Play) {
//		Player actions
		if (player != null) {
			player.processInput(kL);
			this.player.anchored = kL.isPressed(KeyCode.E);
			if (kL.justPressed(KeyCode.Q)) {
				for (GameObject o: this.object) {
					if (o.id == ID.Enemy) {
						Vector disp = new Vector(player,o);
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
			this.removeByID(ID.Obstacle);
			this.removeByID(ID.Trap);
//			this.removeByID(ID.Ball);
			this.setup();
		}
		if (kL.isPressed(KeyCode.ESCAPE)) {
			gState = GameState.Pause;
			//System.exit(1);
		}
//		update each object
		for (int i = 0; i < object.size(); i++)
		{
			GameObject tempObject = object.get(i);
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
			Handler.check_Death = false;
		}}

		
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
		//this.addObject(new Enemy(640,300,ID.Enemy, this));
		Random r = new Random();
		for(int i = 0 ; i < 10 ; i++) {
			Obstacle o = new Obstacle(r.nextInt(Game.arenaWidth), r.nextInt(Game.arenaHeight), ID.Obstacle, this, r.nextInt(200)+50);
			if (!isHittingAnything(o)) {
				this.addObject(o);
			} else {
				i--;
			}
		}
		for(int i = 0 ; i < 20 ; i++) {
			Trap o = new Trap(r.nextInt(Game.arenaWidth), r.nextInt(Game.arenaHeight), ID.Trap, this, r.nextInt(60)+5);
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
		if (gState == GameState.MainMenu) {
			if (menu == null) {
				menu = new MainMenu(this);
			}
			menu.update();
			menu.render(d);
			//implement soon
		}
		else if (gState == GameState.Pause) {
			if (pause == null) {
				pause = new PauseMenu(this);
			}
			pause.update();
			pause.render(d);
			//PauseMenu pauseMenu = new PauseMenu();
			//pauseMenu.renderMainPause(null);
			//more to implement
		}
		else if (gState == GameState.Play)
		{
			if (this.player != null) {
				d.updateCamera(this.player);
			} else {
				d.updateCamera(this.object.get(0));
			}
			d.drawBorders();
			for (int i = 0; i < object.size(); i++)
			{
				GameObject tempObject = object.get(i);
				tempObject.render(d);
			}
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
		o.addTo(this);
	}
	
	
//	Removes an object from the game.
	public void removeObject(GameObject o) {
		o.removeFrom(this);
	}
	
//	Add a keyList object
	public void addKeyboard(Keylist kL) {
		this.kL = kL;
	}
	
//	Get the keyBoard object
	public Keylist keys() {
		return this.kL;
	}
	
	public void clickEvent(double x, double y) {
		if (this.gState == GameState.MainMenu)
		{
			menu.recieveClick(x, y);
		} else if(this.gState == GameState.Pause)
		{
			pause.recieveClick(x, y);
		} else {
			if (player != null) {
				player.doClick(x,y);
			}
		}
	}
	
	public MouseClickHandler setupClickHandler(Camera cam) {
		this.clickHandler = new MouseClickHandler(this);
		this.clickHandler.setCam(cam); //TODO fix this
		return this.clickHandler;
	}
	
	public String toString() {
		String result = "";
//		TODO Michael do this
//		Make sure that every variable of significance in the program
//		is being stored in this string.
//		Store it in the way that you would want a program to read it.
//		You'll have to be able to turn it back into data afterwards.
		
		result += "<Game properties go here>"; // Replace this with relevant code
		
		result += "\n";
		for (GameObject i: object) {
			result += i.toString(); // Append GameObject descriptor
			result += "\n"; // Add a linebreak.
		}
		
		result += "--------------LastLine---------";
//		Just a marker. Remove this if you want.
		
		return result;
	}
	
	
	
	
	// Setters for gameStates
	public void setGameStatePlay() {
		gState = GameState.Play;
	}
	public void setGameStateMainMenu() {
		gState = GameState.MainMenu;
	}
	public void setGameStatePause() {
		gState = GameState.Pause;
	}
	public LinkedList<GameObject> getObjects(){
		return object;
	}
	
//	Inform every handler that it is time to check for deaths. False positives are fine.
	public static void time_To_Die() {
		check_Death = true;
	}

}

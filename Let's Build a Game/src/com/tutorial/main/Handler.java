package com.tutorial.main;

// import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

// This class contains every GameObject, and the boolean state of each key.
// Its purpose is to handle interactions between objects.
// It also handles render and tick methods
// Contains copied code.

public class Handler {
	
//	A list of every game object
	public LinkedList<GameObject> object = new LinkedList<GameObject>();
//	A list of every ally
	public LinkedList<Character> allies = new LinkedList<Character>();
//	A list of every ally
	public LinkedList<GameObject> obstacles = new LinkedList<GameObject>();
//	A list of every obstacle or trap
	public LinkedList<Character> enemies = new LinkedList<Character>();
//	A list of every character
	public LinkedList<GameObject> movingStuff = new LinkedList<GameObject>();
//	The player
	public Player player = null;
	private Keylist kL;
	private static boolean check_Death = false; // a flag that means something is about to die.
	private GameState gState = GameState.MainMenu;
	private MainMenu menu = null;
	private PauseMenu pause = null;
	private MouseClickHandler clickHandler = null;
	private Camera cam;
	private Stage mainStage;
	private int numOfTraps = 20;
	private int numOfObstacles = 10;
	
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
		if (check_Death || true) {
			for (int thing = 0; thing < object.size(); thing++) {
				GameObject a = object.get(thing);
				if (a.check_Death()) {
					thing--;
					removeObject(a);
				}
			}
			Handler.check_Death = false;
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
// Method to make sure that the randomized location of the object spawning
//	does not overlap with any pre-existing game objects already on the canvas.
	private boolean isHitting(GameObject me, GameObject you) {
		return me.distance(you) < (me.getRadius() + you.getRadius());
	}
/** setup method spawns / creates the characters, enemies, obstacles and traps for each game.	
 *  See in-line commenting for in-depth breakdowns on individual actions within this method.
 */
	public void setup() {
		// creates the player at the specified starting location
		this.addObject(new Player(320,300,ID.Player, this));
//		this.addObject(new Ball(200,200,ID.Ball, this));
		// creates the enemy at the specified starting location
		this.addObject(new Enemy(640,300,ID.Enemy, this));
		
		// Set up for generating the randomized map / trap scheme
		Random r = new Random();
		// this will create 10 obstacles each with a radius of between 50 and 250
		// and with a location who's center is inside the bounds of the playing area. 
		// *done to increase variety in map generation
		// checks if the object overlaps with any pre-existing objects via 'isHitting' method.
		for(int i = 0 ; i < numOfObstacles ; i++) {
			Obstacle o = new Obstacle(r.nextInt(Game.arenaWidth), r.nextInt(Game.arenaHeight), ID.Obstacle, this, r.nextInt(200)+50);
			if (!isHittingAnything(o)) {
				this.addObject(o);
			} else {
				i--;
			}
		}
		// creates 20 traps each with a radius between 5 and 65. 
		// locations randomly generated on map so as not to overlap with any pre-existing
		// game objects.Trap effect decided on collision by randomized number generator.
		// checks if the object overlaps with any pre-existing objects via 'isHitting' method. 
		for(int i = 0 ; i < numOfTraps ; i++) {
			Trap o = new Trap(r.nextInt(Game.arenaWidth), r.nextInt(Game.arenaHeight), ID.Trap, this, r.nextInt(60)+5);
			if (!isHittingAnything(o)) {
				this.addObject(o);
			} else {
				i--;
			}
		}
	}
//	Method to remove all objects from the canvas via the handler list. 	
	public void removeAll() {
		while (objectCount() > 0) {
			this.removeObject(this.object.get(0));
		}
	}
//	returns an integer representation of the number of objects currently in the game	
	public int objectCount() {
		return this.object.size();
	}
//	removes objects of a particular type, specified by their ID. 	
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
				menu = new MainMenu(this, mainStage);
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
			}
			else if (this.enemies.size() > 0)
			{
				d.updateCamera(this.enemies.get(0));
			}
			else if (this.movingStuff.size() > 0)
			{
				d.updateCamera(this.movingStuff.get(0));
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
				player.doClick(cam.reverseEngineerX((float)x), cam.reverseEngineerY((float)y));
			}
		}
	}
	
	public MouseClickHandler setupClickHandler() {
		this.clickHandler = new MouseClickHandler(this);
		return this.clickHandler;
	}
	
	public void setCam(Camera cam) {
		this.cam = cam;
	}
	
	public void setMainStage(Stage mainStage) {
		this.mainStage = mainStage;
	}
	
	public String toString() {
		String result = "";
		
		result += Game.arenaHeight + "," + Game.arenaWidth + ","; // Replace this with relevant code
		
		//result += "\n";
		
		for (GameObject i: object) {
			result += i.toString();
		}

		System.out.println(result);
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
	public int getNumOfTraps() {
		return this.numOfTraps;
	}
	public int getNumOfObstacles() {
		return this.numOfObstacles;
	}
	public void setNumOfTraps(int num) {
		if (num >= 5) {
			this.numOfTraps = num;
		}
	}
	public void setNumOfObstacles(int num) {
		if (num >= 10) {
			this.numOfObstacles = num;
		}
	}
	
//	Inform every handler that it is time to check for deaths. False positives are fine.
	public static void time_To_Die() {
		check_Death = true;
	}

}

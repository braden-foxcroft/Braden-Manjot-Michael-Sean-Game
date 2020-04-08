package com.tutorial.main;

// import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

import com.tutorial.display.Display;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

/**
 * This class contains every GameObject, and references to every active class.
 * Its purpose is to handle interactions between objects.
 * It also handles render and tick methods
 */

public class Handler {
	
	/**
	 * A list of every game object
	 */
	public LinkedList<GameObject> object = new LinkedList<GameObject>();
	
	/**
	 * A list of every ally
	 */
	public LinkedList<Character> allies = new LinkedList<Character>();
	
	/**
	 * A list of every ally
	 */
	public LinkedList<GameObject> obstacles = new LinkedList<GameObject>();
	
	/**
	 * A list of every obstacle or trap
	 */
	public LinkedList<Character> enemies = new LinkedList<Character>();
	
	/**
	 * A list of every character
	 */
	public LinkedList<GameObject> movingStuff = new LinkedList<GameObject>();
	
	/**
	 * The player
	 */
	public Player player = null;
	
	/**
	 * The key handler
	 */
	private Keylist kL;
	
	/**
	 * A flag that instructs all handlers to garbageCollect dead objects at the end of the next tick
	 */
	private static boolean check_Death = false;
	
	/**
	 * The current game state
	 */
	private GameState gState = GameState.MainMenu;
	
	/**
	 * The instance of the menu
	 */
	private MainMenu menu = null;
	
	/**
	 * The instance of the pause menu
	 */
	private PauseMenu pause = null;
	
	/**
	 * The instance of the click handler
	 */
	private MouseClickHandler clickHandler = null;
	
	/**
	 * The instance of the camera
	 */
	private Camera cam;
	
	/**
	 * The instance of the main stage.
	 */
	private Stage mainStage;
	
	/**
	 * The number of traps to generate.
	 */
	private int numOfTraps = 20;
	
	/**
	 * The number of obstacles to generate
	 */
	private int numOfObstacles = 10;
	
	/**
	 * Occurs every tick. Causes all objects to update and all collisions to occur.
	 */
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

	/**
	 * Checks if two objects are colliding.
	 * @param me - object 1
	 * @param you - object 2
	 * @return - Boolean value, true if the two objects are colliding. 
	 */
	private boolean isHitting(GameObject me, GameObject you) {
		return me.distance(you) < (me.getRadius() + you.getRadius());
	}
	
	/**
	 * Resets the map.
	 */
	public void setup() {
		// creates the player at the specified starting location
		this.addObject(new Player(320,300,ID.Player, this));
//		this.addObject(new Ball(200,200,ID.Ball, this));
		// creates the enemy at the specified starting location
		this.addObject(new Enemy(640,300,ID.Enemy, this));
		
		// Set up for generating the randomized map / trap scheme
		Random r = new Random();
		// this will creates a number of obstacles each with a radius of between 50 and 250
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
		// creates a number of traps each with a radius between 5 and 65. 
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
	
	/**
	 * Removes every object from the game
	 */
	public void removeAll() {
		while (objectCount() > 0) {
			this.removeObject(this.object.get(0));
		}
	}

	/**
	 * Returns the number objects in the game
	 * @return the number of objects in the game
	 */
	public int objectCount() {
		return this.object.size();
	}

	/**
	 * Removes every object with the given ID
	 * @param id - The ID to remove.
	 */
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
	
	/**
	 * Renders every object
	 * @param d - the instance of the display class to use.
	 */
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
	
	/**
	 * Render each object to the text version
	 * @param board the board to render the objects to
	 */
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
	
	/**
	 * Adds the object to the handler
	 * @param o - the object to add
	 */
	public void addObject(GameObject o) {
		o.addTo(this);
	}
	
	/**
	 * Removes the object from the handler
	 * @param o - the object to remove
	 */
	public void removeObject(GameObject o) {
		o.removeFrom(this);
	}
	
	/**
	 * Adds a key listener
	 * @param kL - The instance of the key listener to add
	 */
	public void addKeyboard(Keylist kL) {
		this.kL = kL;
	}
	
	/**
	 * Returns the key listener
	 * @return The key listener
	 */
	public Keylist keys() {
		return this.kL;
	}
	
	/**
	 * Handles a click event
	 * @param x - the x coordinate of the click (on the window)
	 * @param y - the y coordinate of the click (on the window)
	 */
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
	
	/**
	 * Creates a new mouse event handler
	 * @return The handler created
	 */
	public MouseClickHandler setupClickHandler() {
		this.clickHandler = new MouseClickHandler(this);
		return this.clickHandler;
	}
	
	/**
	 * Sets the camera to use
	 * @param cam - the camera to use
	 */
	public void setCam(Camera cam) {
		this.cam = cam;
	}
	
	/**
	 * Sets the main stage
	 * @param mainStage - the main stage
	 */
	public void setMainStage(Stage mainStage) {
		this.mainStage = mainStage;
	}
	
//	See the documentation for the implemented/overridden method
	public String toString() {
		String result = "";
		
		result += Game.arenaHeight + "," + Game.arenaWidth + ","; 
		
		
		for (GameObject i: object) {
			result += i.toString();
		}

		//System.out.println(result);
		return result;
	}
	
	/**
	 * Sets the game state to play
	 */
	public void setGameStatePlay() {
		gState = GameState.Play;
	}
	
	/**
	 * Sets the game state to main menu
	 */
	public void setGameStateMainMenu() {
		gState = GameState.MainMenu;
	}
	
	/**
	 * Sets the game state to pause
	 */
	public void setGameStatePause() {
		gState = GameState.Pause;
	}
	
	/**
	 * Gets the objects in game
	 * @return The list of in-game objects
	 */
	public LinkedList<GameObject> getObjects(){
		return object;
	}
	
	/**
	 * Gets the number of traps to generate
	 * @return The number of traps to make
	 */
	public int getNumOfTraps() {
		return this.numOfTraps;
	}
	
	/**
	 * Gets the number of obstacles to generate
	 * @return The number of obstacles to generate
	 */
	public int getNumOfObstacles() {
		return this.numOfObstacles;
	}
	
	/**
	 * Sets the number of traps to generate
	 * @param num - The number of traps to make
	 */
	public void setNumOfTraps(int num) {
		if (num >= 5) {
			this.numOfTraps = num;
		}
	}
	
	/**
	 * Sets the number of obstacles to generate
	 * @param num - The number of obstacles to generate
	 */
	public void setNumOfObstacles(int num) {
		if (num >= 10) {
			this.numOfObstacles = num;
		}
	}
	
	/**
	 * Informs every handler that it is time to check for deaths.
	 * Calling this method causes no problems, but not calling this method can cause objects to persist after death.
	 */
//	
	public static void time_To_Die() {
		check_Death = true;
	}

}

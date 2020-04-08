package com.tutorial.main;

import com.tutorial.display.Display;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * The Game object. It instantiates many of the key classes.
 */

public class Game extends Application {
	
	/**
	 * The width of the window
	 */
	public static final int WIDTH = 960;
	
	/**
	 * The height of the window
	 */
	public static final int HEIGHT = WIDTH / 12 * 9; // 720;
	
	/**
	 * The width of the arena
	 */
	public static int arenaWidth = 2000;
	
	/**
	 * The height of the arena
	 */
	public static int arenaHeight = 1500;
	
	/**
	 * The canvas that fills the window
	 */
	private Canvas mainCanvas;
	
	/**
	 * The keyEvent handler
	 */
	private Keylist kL;
	
	/**
	 * The mouse event handler
	 */
	private MouseClickHandler mCH;
	
	/**
	 * The game's scene
	 */
	private Scene gameScene;
	
	/**
	 * Sets everything up, and starts the game.
	 * @param mainStage the stage to make things on.
	 */
	public void start(Stage mainStage) throws Exception {
		Handler handler = new Handler();
		
		setupWindow(mainStage);
		
		Display display = setupDisplay();
		
		handler.addKeyboard(kL);
		mCH = handler.setupClickHandler();
		handler.setCam(display.getCamera());
		gameScene.addEventHandler(MouseEvent.ANY, mCH);
		handler.setMainStage(mainStage);
		
		
		AnimationController aC = new AnimationController(handler, display);
		
		aC.start();
		mainStage.show();
		mainStage.centerOnScreen();
	}
	
	/**
	 * Creates a key listener
	 * @param gameScene The scene of the window
	 */
	public void setupKeylist(Scene gameScene) {
		kL = new Keylist();
		gameScene.addEventHandler(KeyEvent.KEY_PRESSED, kL);
		gameScene.addEventHandler(KeyEvent.KEY_RELEASED, kL);
	}
	
	/**
	 * Creates an instance of the Display class
	 * @return The created instance.
	 */
	public Display setupDisplay() {
		GraphicsContext gC = mainCanvas.getGraphicsContext2D();
		return new Display(gC);
	}
	
	/**
	 * Creates a window, with a canvas
	 * @param mainStage The mainstage to use.
	 */
	public void setupWindow(Stage mainStage) {
		Group root = new Group();
		mainCanvas = new Canvas(WIDTH,HEIGHT);
		root.getChildren().add(mainCanvas);
		
		
		gameScene = new Scene(root, 0, 0);
		setupKeylist(gameScene);
		
		
		mainStage.setWidth(Game.WIDTH);
		mainStage.setHeight(Game.HEIGHT);
		
		mainStage.setTitle("Bumper cars");
		mainStage.setScene(gameScene);
	}
	
	/**
	 * The main method.
	 * @param args The arguments passed to the program.
	 */
	public static void main(String[] args) {
		System.out.println("start");
		launch(args);
		System.out.println("done");
	}
}

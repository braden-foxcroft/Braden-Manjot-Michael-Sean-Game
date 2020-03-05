package com.tutorial.main;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Game extends Application {
	
	public static final int WIDTH = 960;
	public static final int HEIGHT = WIDTH / 12 * 9; // 720;
	private Canvas mainCanvas;
	private Keylist kL;
	
	public void start(Stage mainStage) throws Exception {
		Handler handler = new Handler();
		
		setupWindow(mainStage);
		
		handler.addKeyboard(kL);
		
		Display display = setupDisplay();
		
		AnimationController aC = new AnimationController(handler, display);
		
		aC.start();
		mainStage.show();
		mainStage.centerOnScreen();
	}
	
	public void setupKeylist(Scene gameScene) {
		kL = new Keylist();
		gameScene.addEventHandler(KeyEvent.KEY_PRESSED, kL);
		gameScene.addEventHandler(KeyEvent.KEY_RELEASED, kL);
	}
	
	public Display setupDisplay() {
		GraphicsContext gC = mainCanvas.getGraphicsContext2D();
		return new Display(gC);
	}
	
	public void setupWindow(Stage mainStage) {
		Group root = new Group();
		mainCanvas = new Canvas(WIDTH,HEIGHT);
		root.getChildren().add(mainCanvas);
		
		
		Scene gameScene = new Scene(root, 0, 0);
		setupKeylist(gameScene);
		
		mainStage.setWidth(Game.WIDTH);
		mainStage.setHeight(Game.HEIGHT);
		
		mainStage.setTitle("Bumper cars");
		mainStage.setScene(gameScene);
	}
	
	public static void main(String[] args) {
		System.out.println("start");
		launch(args);
		System.out.println("done");
	}
}

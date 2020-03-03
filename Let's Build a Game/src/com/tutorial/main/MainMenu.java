package com.tutorial.main;

import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class MainMenu {

	protected void initMainMenu() {
		Font font = Font.font(72);
		Button newGameMenu = new Button("Start Game");
		newGameMenu.setFont(font);
		newGameMenu.setOnAction(event -> newGameSettings());
		
		Button loadGameMenu = new Button("Load game");
		loadGameMenu.setFont(font);
		loadGameMenu.setOnAction(event -> loadGameSettings());
		
		Button settingsMenu = new Button("Settings");
		settingsMenu.setFont(font);
		settingsMenu.setOnAction(event_-> loadSettingsSettings());
		
		Button exitTime = new Button("Exit Game");
		exitTime.setFont(font);
		exitTime.setOnAction(event -> System.exit(1));
		
	}
	protected void newGameSettings() {
		
	}
	protected void loadGameSettings() {
		
	}
	protected void loadSettingsSettings() {
		
	}
	
}

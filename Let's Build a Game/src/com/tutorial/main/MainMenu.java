package com.tutorial.main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class MainMenu extends Handler {
	private static MenuState state = MenuState.playmenu;

	public void render(Display d,MenuState p) {
		if (state == MenuState.mainmenu) {
			renderMainMenu(d);
		}
		else if (state == MenuState.loadmenu) {
			renderLoadMenu(d);
		}
		else if (state == MenuState.optionsmenu) {
			renderOptionsMenu(d);
		}
		else if (state == MenuState.playmenu) {
			renderPlayMenu(d);
		}
	}
	public void renderPlayMenu(Display d) {
		setGameStatePlay();
		
	}
	public void renderOptionsMenu(Display d) {
		// TODO Auto-generated method stub
		
	}
	public void renderLoadMenu(Display d) {
		// TODO Auto-generated method stub
		
	}
	public void renderMainMenu(Display d) {
		HandleButtonClick buttonForPlay = new HandleButtonClick("buttonForPlay");
		Button playSetUp = new Button("Play Game");
		playSetUp.setOnAction(buttonForPlay);
		
		HandleButtonClick buttonForLoad = new HandleButtonClick("buttonForLoad");
		Button loadGame = new Button("Load Game");
		loadGame.setOnAction(buttonForLoad);
		
		HandleButtonClick buttonForOptions = new HandleButtonClick("buttonForOptions");
		Button options = new Button("Options");
		options.setOnAction(buttonForOptions);
		
		HandleButtonClick buttonForQuit = new HandleButtonClick("quit");
		Button quit = new Button("Quit Game");
		quit.setOnAction(buttonForQuit);
		
		
	}
}

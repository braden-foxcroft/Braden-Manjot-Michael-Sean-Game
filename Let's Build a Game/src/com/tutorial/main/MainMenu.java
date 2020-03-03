package com.tutorial.main;

import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class MainMenu {
	private MenuState state;

	public void render(Display d,GameState p) {
		if (state == MenuState.mainmenu) {
			renderMainMenu(d);
		}
		else if (state == MenuState.loadMenu) {
			renderLoadMenu(d);
		}
		else if (state == MenuState.optionsMenu) {
			renderOptionsMenu(d);
		}
		else if (state == MenuState.saveMenu) {
			renderSaveMenu(d);
		}
		else if (state == MenuState.pauseMenu) {
			renderPauseMenu(d);
		}
	}
	private void renderPauseMenu(Display d) {
		// TODO Auto-generated method stub
		
	}
	private void renderSaveMenu(Display d) {
		// TODO Auto-generated method stub
		
	}
	private void renderOptionsMenu(Display d) {
		// TODO Auto-generated method stub
		
	}
	private void renderLoadMenu(Display d) {
		// TODO Auto-generated method stub
		
	}
	private void renderMainMenu(Display d) {
		// TODO Auto-generated method stub
		
	}
}

package com.tutorial.main;

public class MainMenu {
	private MenuState state;

	public void render(Display d,GameState p) {
		if (state == MenuState.mainmenu) {
			renderMainMenu(d);
		}
		else if (state == MenuState.loadmenu) {
			renderLoadMenu(d);
		}
		else if (state == MenuState.optionsmenu) {
			renderOptionsMenu(d);
		}
		else if (state == MenuState.savemenu) {
			renderSaveMenu(d);
		}
	}
	public void renderSaveMenu(Display d) {
		// TODO Auto-generated method stub
		
	}
	public void renderOptionsMenu(Display d) {
		// TODO Auto-generated method stub
		
	}
	public void renderLoadMenu(Display d) {
		// TODO Auto-generated method stub
		
	}
	public void renderMainMenu(Display d) {
		// TODO Auto-generated method stub
		
	}
}

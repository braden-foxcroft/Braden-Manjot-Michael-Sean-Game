package com.tutorial.main;

import java.util.LinkedList;

import javafx.scene.paint.Color;

public class MainMenu extends Handler {
	private static MenuState state = MenuState.mainmenu;
	private static LinkedList<OurButton> buttonList;

	public void render(Display d) {
		for (OurButton b:buttonList) {
			b.render(d);
		}
	}
	
	public void update() {
		reset();
		if (state == MenuState.mainmenu) {
			renderMainMenu();
		}
		else if (state == MenuState.loadmenu) {
			renderLoadMenu();
		}
		else if (state == MenuState.optionsmenu) {
			renderOptionsMenu();
		}
		else if (state == MenuState.playmenu) {
			renderPlayMenu();
		}
	}
	
	public void reset() {
		while (buttonList.size() > 0) {
			buttonList.remove();
		}
	}
	
	public void addButton(OurButton b) {
		buttonList.add(b);
	}
	
	public void renderPlayMenu() {
		setGameStatePlay();
		
	}
	
	public void renderOptionsMenu() {
		// TODO Auto-generated method stub
		
	}
	
	public void renderLoadMenu() {
		// TODO Auto-generated method stub
		
	}
	
	public void renderMainMenu() {
		// TODO Add buttons to the list
//		addButton(new OurButton(x, y, width, height, Color.ANTIQUEWHITE, text));
		
	}
}

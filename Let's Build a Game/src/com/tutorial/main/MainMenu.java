package com.tutorial.main;

import java.util.LinkedList;

import javafx.scene.paint.Color;

// TODO Michael, do this
// Make all menus
// (In PauseMenu, too)
public class MainMenu extends Handler {
	private static MenuState state = MenuState.mainmenu;
	private static LinkedList<OurButton> buttonList;
	private Handler handler;
	
	
	public MainMenu(Handler h) {
		buttonList = new LinkedList<OurButton>();
		this.handler = h;
	}
	
	public void render(Display d) {
		for (OurButton b:buttonList) {
			b.render(d);
		}
	}
	
	public void recieveClick(double x, double y) {
		for (OurButton b:buttonList) {
			if (b.coordinatesAreInside((int)x, (int)y)) {
				b.menuTriggerClick();
			}
		}
	}
	
	public void menuClickHandler(String text) {

		if(text == "Play game") {
			state = MenuState.playmenu;
		}
		else if (text == "Load game") {
			state = MenuState.loadmenu;
		}
		else if (text == "Options") {
			state = MenuState.optionsmenu;
		}
		else if (text == "Quit") {
			System.exit(1);
		}
		//else if (text == "")
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
		b.setMenu(this);
		buttonList.add(b);
	}
	
	public void renderPlayMenu() {
		handler.setGameStatePlay();
		
	}
	
	public void renderOptionsMenu() {
		
		
	}
	
	public void renderLoadMenu() {
		
		
	}
	
	public void renderMainMenu() {
		Color c = Color.color(0, 0.5, 0.5);
		
		addButton(new OurButton(280,30,400,100,c,"Play game"));
		
		addButton(new OurButton(280,210,400,100,c,"Load game \n COMING SOON"));
		
		addButton(new OurButton(280,390,400,100,c,"Options \n COMING SOON"));
		
		addButton(new OurButton(280,570,400,100,c,"Quit"));
		
		
	}
}

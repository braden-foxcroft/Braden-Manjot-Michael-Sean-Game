package com.tutorial.main;

import java.util.LinkedList;

import javafx.scene.paint.Color;

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
				b.triggerClick();
			}
		}
	}
	
	public void menuClickHandler(String text) {
//		TODO Michael, use the text and the MenuState to make buttons do stuff
//		You are given the text of the pressed button, and you can use the gamestate.
//		All of the button clicks are handled by this one function,
//		So make sure there's an 'if, else if, else' for deciding what to do.
//		handler.doWhatever();
		System.out.println("'" + text + "' was pressed");
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
		setGameStatePlay();
		
	}
	
	public void renderOptionsMenu() {
		// TODO make this work
		
	}
	
	public void renderLoadMenu() {
		// TODO make this work
		
	}
	
	public void renderMainMenu() {
		Color c = Color.BLUE; // cuz why not
		addButton(new OurButton(50,20,400,100,c,"Play game"));
		
		addButton(new OurButton(50,130,400,100,c,"Load game"));
		
		addButton(new OurButton(50,240,400,100,c,"Options"));
		
		addButton(new OurButton(50,350,400,100,c,"Quit"));
		
		
	}
}

package com.tutorial.main;

import java.util.LinkedList;

import javafx.scene.paint.Color;

public class PauseMenu extends Handler{
	private static PauseState state = PauseState.mainPause;
	private static LinkedList<OurButton> buttonList;
	private Handler handler;
	
	public PauseMenu(Handler h) {
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
				b.pauseTriggerClick();
			}
		}
	}
	public void menuClickHandler(String text) {

		if(text == "Resume") {
			handler.setGameStatePlay();
		}
		else if (text == "Save") {
			state = PauseState.save;
		}
		else if (text == "Quit") {
			System.exit(1);
		}
	}
	public void update() {
		reset();
		if (state == PauseState.mainPause) {
			renderPauseMenu();
		}
		else if (state == PauseState.save) {
			renderSaveMenu();
		}
		else if (state == PauseState.quit) {
			
		}
	}
	public void reset() {
			while (buttonList.size() > 0) {
				buttonList.remove();
			}
	}
	
	public void addButton(OurButton b) {
		b.setPauseMenu(this);
		buttonList.add(b);
	}
	
	private void renderSaveMenu() {
		// TODO Auto-generated method stub
		
	}

	public void renderPauseMenu() {
		Color c = Color.BLUE;
		addButton(new OurButton(50,20,400,100,c,"Resume"));
		
		addButton(new OurButton(50,130,400,100,c,"Save \n COMMING SOON"));
		
		addButton(new OurButton(50,240,400,100,c,"Quit"));
		
	}
}

package com.tutorial.main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
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
	
//	A method that updates the buttons in the game menu
//	This method is called 60x a second, so don't put any
//	processor-intensive code in it.
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
		saveGame();
		state = PauseState.mainPause;
		// To prevent an endless loop of saves. Please, do not delete this line of code!
	}
	
	public void renderPauseMenu() {
		Color c = Color.BLUE;
		addButton(new OurButton(280,30,400,100,c,"Resume"));
		
		addButton(new OurButton(280,210,400,100,c,"Save"));
		
		addButton(new OurButton(280,390,400,100,c,"Quit"));
		
	}
	
	public void saveGame() {
		object = handler.getObjects();
		//String stringOfObjects;
		Calendar rightNow = Calendar.getInstance();
		try {
			FileWriter newSave = new FileWriter("C:\\Users\\mdbuc\\Desktop\\" + "test" + ".txt");
			//FileWriter test = new FileWriter("C:\\Users\\mdbuc\\Desktop\\File.txt");
			newSave.append(handler.toString());
			newSave.close();
		} catch (IOException e) {
			System.out.println("An error made you die");
			e.printStackTrace();
		}
		
		
	}
}

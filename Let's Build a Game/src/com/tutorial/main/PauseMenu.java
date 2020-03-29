package com.tutorial.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedList;

import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;

@SuppressWarnings("unused")
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
		else if (text == "Main Menu") {
			setGameStateMainMenu();
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
		String objects = handler.toString();
		Calendar rightNow = Calendar.getInstance();
		String userHomeFolder = System.getProperty("user.home");
		File newSave = new File(userHomeFolder,"Save" + rightNow.getTimeInMillis() + ".txt");
		//System.out.println(userHomeFolder);
		try {
			FileWriter save = new FileWriter(newSave);
			save.append(objects);
			//FileWriter newSave = new FileWriter("Save"+ rightNow.getTimeInMillis() + ".txt");
			
			save.close();
			
			
		} catch (IOException e) {
			System.out.println("Cannot Save at this time");
			e.printStackTrace();
		} 
		//System.out.println(userHomeFolder);
		state = PauseState.mainPause;
	}
	
	public void renderPauseMenu() {
		Color c = Color.BLUE;
		addButton(new OurButton(280,30,400,100,c,"Resume"));
		
		addButton(new OurButton(280,210,400,100,c,"Save"));
		
		addButton(new OurButton(280,390,400,100,c,"Quit"));
		
		//addButton(new OurButton(280,570,400,100,c,"Main Menu"));
		
	}
}

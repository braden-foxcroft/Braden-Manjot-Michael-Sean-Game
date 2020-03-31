package com.tutorial.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedList;

import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


// TODO Comments by Michael
public class PauseMenu extends Handler{
	private static PauseState state = PauseState.mainPause;
	private static LinkedList<OurButton> buttonList;
	private Handler handler;
	private Stage mainStage;
	/**
	 * Constructor for the pause menu.
	 * @param h - Handler for the PauseMenu
	 */
	public PauseMenu(Handler h) {
		buttonList = new LinkedList<OurButton>();
		this.handler = h;
	}
	/**
	 * Method that renders the buttons in the Pause Menu.
	 */
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
	
	/**
	 * This method controls what is shown on the screen, by interpreting the PauseMenu state.
	 */
	public void update() {
		reset();
		if (state == PauseState.mainPause) {
			renderPauseMenu();
		}
		else if (state == PauseState.save) {
			renderSaveMenu();
		}
	}
	/**
	 * Method that removes all the buttons from the button list.
	 */
	public void reset() {
			while (buttonList.size() > 0) {
				buttonList.remove();
			}
	}
	/**
	 * method that adds a new button to be rendered.
	 * @param b - Button to be added to the list
	 */
	public void addButton(OurButton b) {
		b.setPauseMenu(this);
		buttonList.add(b);
	}
	/**
	 * method that takes a created file and saves the list of the handler.
	 */
	private void renderSaveMenu() {
		String objects = handler.toString();

		try {
			FileWriter save = new FileWriter(pathSelecter());
			save.append(objects);
			save.close();
			
		} catch (IOException e) {
			System.out.println("Cannot Save at this time");
			e.printStackTrace();
		} 
	
		state = PauseState.mainPause;
	}
	/**
	 * Prompts the user for a file location and a name. 
	 * @return - Returns a newly created file, for saving
	 */
	public File pathSelecter() {
		Calendar rightNow = Calendar.getInstance();
		FileChooser fileSelecter = new FileChooser();
		fileSelecter.setTitle("Select File Path");
		fileSelecter.setInitialFileName("Save" + rightNow.getTimeInMillis() + ".txt");
		File selectedFile = fileSelecter.showSaveDialog(mainStage);
		return selectedFile;
	}
	/**
	 * Method that creates the buttons for the Pause Menu.
	 */
	public void renderPauseMenu() {
		Color c = Color.BLUE;
		addButton(new OurButton(280,30,400,100,c,"Resume"));
		
		addButton(new OurButton(280,210,400,100,c,"Save"));
		
		addButton(new OurButton(280,390,400,100,c,"Quit"));
		
		//addButton(new OurButton(280,570,400,100,c,"Main Menu"));
		
	}
}

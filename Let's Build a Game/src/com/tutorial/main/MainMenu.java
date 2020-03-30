package com.tutorial.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

// TODO Michael, do this
// Make all menus
// (In PauseMenu, too)
@SuppressWarnings("unused")
public class MainMenu extends Handler {
	private static MenuState state = MenuState.mainmenu;
	private static LinkedList<OurButton> buttonList;
	private Handler handler;
	private Stage mainStage;
	private boolean timeToUpdate = true;
	
	
	public MainMenu(Handler h, Stage mainStage) {
		buttonList = new LinkedList<OurButton>();
		this.handler = h;
		this.mainStage = mainStage;
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
			setState(MenuState.playmenu);
		}
		else if (text == "Load game") {
			setState(MenuState.loadmenu);
		}
		else if (text == "Options") {
			setState(MenuState.optionsmenu);
		}
		else if (text == "Quit") {
			System.exit(1);
		}
		else if (text == "Back") {
			setState(MenuState.mainmenu);
		}
		else if (text == "Pick File") {
			load();
		}
		else if (text == "^") {
			Game.arenaHeight = Game.arenaHeight + 100;
			Game.arenaWidth = Game.arenaWidth + 100;
		}
		else if (text == "v") {
			Game.arenaHeight = Game.arenaHeight - 100;
			Game.arenaWidth = Game.arenaWidth - 100;
		}
		else if (text == "Defaults") {
			Game.arenaHeight = 1500;
			Game.arenaWidth = 2000;
		}
		else if (text == "^ ") {
			handler.setNumOfTraps(handler.getNumOfTraps() + 5);
		}
		else if (text == "v ") {
			handler.setNumOfTraps(handler.getNumOfTraps() - 5);
		}
		else if (text == "Defaults ") {
			handler.setNumOfTraps(5);
		}
		else if (text == "^  ") {
			handler.setNumOfObsticles(handler.getNumOfObsticles() + 5);
		}
		else if (text == "v  ") {
			handler.setNumOfObsticles(handler.getNumOfObsticles() - 5);
		}
		else if (text == "Defaults  ") {
			handler.setNumOfObsticles(10);
		}
	}
	
	public void update() {
		if (!timeToUpdate)
		{
			return;
		}
		this.timeToUpdate = false;
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
	
	public void load() {
//		TODO Michael, do this
//		Make it load.
		String stringOfObjects = "";
		LinkedList<GameObject> loadedObjects;
		File f = filePicker();
		try {
			Scanner myReader = new Scanner(f);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				stringOfObjects += data;
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("an Error occored while loading");
			e.printStackTrace();
			}
		
		//System.out.println(stringOfObjects);
		
		int iIndex = 0;
		int fIndex = stringOfObjects.indexOf(',');
		
		//handler.removeAll();
		
		//System.out.println(stringOfObjects.subSequence(iIndex, fIndex));
		Game.arenaHeight = Integer.parseInt(stringOfObjects.subSequence(iIndex, fIndex).toString());
		iIndex = fIndex+1;
		//System.out.println(iIndex);
		//System.out.println(fIndex);
		fIndex = stringOfObjects.indexOf(',',fIndex + 1);
		
		Game.arenaWidth = Integer.parseInt(stringOfObjects.subSequence(iIndex, fIndex).toString());
		
		//System.out.println(iIndex);
		//System.out.println(fIndex);
		
		iIndex = fIndex+1;
		fIndex = stringOfObjects.indexOf(',',iIndex);
		
		//System.out.println(stringOfObjects.subSequence(iIndex, fIndex));
		
		while (fIndex < stringOfObjects.length() -1) {
			
			//System.out.println(stringOfObjects.subSequence(iIndex, fIndex));
			String object = stringOfObjects.substring(iIndex, fIndex).toString();
			System.out.println(object);
			if (object == "Player") {
				System.out.println("Im a Player!!");
				iIndex = fIndex+1;
				fIndex = stringOfObjects.indexOf(',',iIndex);
				float x = Float.parseFloat(stringOfObjects.subSequence(iIndex, fIndex).toString());
				
				iIndex = fIndex+1;
				fIndex = stringOfObjects.indexOf(',',iIndex);
				float y = Float.parseFloat(stringOfObjects.subSequence(iIndex, fIndex).toString());
				
				iIndex = fIndex+1;
				fIndex = stringOfObjects.indexOf(',',iIndex);
				float velX = Float.parseFloat(stringOfObjects.subSequence(iIndex, fIndex).toString());
				
				iIndex = fIndex+1;
				fIndex = stringOfObjects.indexOf(',',iIndex);
				float velY = Float.parseFloat(stringOfObjects.subSequence(iIndex, fIndex).toString());
				
				iIndex = fIndex+1;
				fIndex = stringOfObjects.indexOf(',',iIndex);
				//float velY = Float.parseFloat(stringOfObjects.subSequence(iIndex, fIndex).toString());(since skills arnt implemented yet
				
				iIndex = fIndex+1;
				fIndex = stringOfObjects.indexOf(',',iIndex);
				int health = Integer.parseInt(stringOfObjects.subSequence(iIndex, fIndex).toString());
				
				//handler.addObject(new Player((int)x,(int)y,ID.Player,(int)velX,(int)velY, handler,health));
			}
			else if (object == "Enemy") {
				System.out.println("Im a Enemy!!");
				iIndex = fIndex+1;
				fIndex = stringOfObjects.indexOf(',',iIndex);
				float x = Float.parseFloat(stringOfObjects.subSequence(iIndex, fIndex).toString());
				
				iIndex = fIndex+1;
				fIndex = stringOfObjects.indexOf(',',iIndex);
				float y = Float.parseFloat(stringOfObjects.subSequence(iIndex, fIndex).toString());
				
				iIndex = fIndex+1;
				fIndex = stringOfObjects.indexOf(',',iIndex);
				float velX = Float.parseFloat(stringOfObjects.subSequence(iIndex, fIndex).toString());
				
				iIndex = fIndex+1;
				fIndex = stringOfObjects.indexOf(',',iIndex);
				float velY = Float.parseFloat(stringOfObjects.subSequence(iIndex, fIndex).toString());
				
				iIndex = fIndex+1;
				fIndex = stringOfObjects.indexOf(',',iIndex);
				//float velY = Float.parseFloat(stringOfObjects.subSequence(iIndex, fIndex).toString());(since skills arnt implemented yet
				
				iIndex = fIndex+1;
				fIndex = stringOfObjects.indexOf(',',iIndex);
				int health = Integer.parseInt(stringOfObjects.subSequence(iIndex, fIndex).toString());
				
				//handler.addObject(new Enemy((int)x,(int)y,ID.Enemy,(int)velX,(int)velY, handler,health));
			}
			else if (object == "Ally") {
				System.out.println("Im a ally!!");
				iIndex = fIndex+1;
				fIndex = stringOfObjects.indexOf(',',iIndex);
				float x = Float.parseFloat(stringOfObjects.subSequence(iIndex, fIndex).toString());
				
				iIndex = fIndex+1;
				fIndex = stringOfObjects.indexOf(',',iIndex);
				float y = Float.parseFloat(stringOfObjects.subSequence(iIndex, fIndex).toString());
				
				iIndex = fIndex+1;
				fIndex = stringOfObjects.indexOf(',',iIndex);
				float velX = Float.parseFloat(stringOfObjects.subSequence(iIndex, fIndex).toString());
				
				iIndex = fIndex+1;
				fIndex = stringOfObjects.indexOf(',',iIndex);
				float velY = Float.parseFloat(stringOfObjects.subSequence(iIndex, fIndex).toString());
				
				iIndex = fIndex+1;
				fIndex = stringOfObjects.indexOf(',',iIndex);
				//float Skills = Float.parseFloat(stringOfObjects.subSequence(iIndex, fIndex).toString());(since skills arnt implemented yet
				
				iIndex = fIndex+1;
				fIndex = stringOfObjects.indexOf(',',iIndex);
				int health = Integer.parseInt(stringOfObjects.subSequence(iIndex, fIndex).toString());
				
				handler.addObject(new Ally((int)x,(int)y,ID.Ally,(int)velX,(int)velY, handler,health));
			}
			else if (object == "Obstacle") {
				System.out.println("Im an obstacle!!");
				iIndex = fIndex+1;
				fIndex = stringOfObjects.indexOf(',',iIndex);
				int radius = Integer.parseInt(stringOfObjects.subSequence(iIndex, fIndex).toString());
				
				iIndex = fIndex+1;
				fIndex = stringOfObjects.indexOf(',',iIndex);
				float x = Float.parseFloat(stringOfObjects.subSequence(iIndex, fIndex).toString());
				
				iIndex = fIndex+1;
				fIndex = stringOfObjects.indexOf(',',iIndex);
				float y = Float.parseFloat(stringOfObjects.subSequence(iIndex, fIndex).toString());
				
			}
			else if (object == "Trap") {
				System.out.println("Im a trap!!");
				iIndex = fIndex+1;
				fIndex = stringOfObjects.indexOf(',',iIndex);
				int radius = Integer.parseInt(stringOfObjects.subSequence(iIndex, fIndex).toString());
				
				iIndex = fIndex+1;
				fIndex = stringOfObjects.indexOf(',',iIndex);
				float x = Float.parseFloat(stringOfObjects.subSequence(iIndex, fIndex).toString());
				
				iIndex = fIndex+1;
				fIndex = stringOfObjects.indexOf(',',iIndex);
				float y = Float.parseFloat(stringOfObjects.subSequence(iIndex, fIndex).toString());
				
			}
			else if (object == "Bullet") {
				System.out.println("Im a bullet!!");
				
				iIndex = fIndex+1;
				fIndex = stringOfObjects.indexOf(',',iIndex);
				int radius = Integer.parseInt(stringOfObjects.subSequence(iIndex, fIndex).toString());
				
				iIndex = fIndex+1;
				fIndex = stringOfObjects.indexOf(',',iIndex);
				float x = Float.parseFloat(stringOfObjects.subSequence(iIndex, fIndex).toString());
				
				iIndex = fIndex+1;
				fIndex = stringOfObjects.indexOf(',',iIndex);
				float y = Float.parseFloat(stringOfObjects.subSequence(iIndex, fIndex).toString());
			}
			iIndex = fIndex+1;
			fIndex = stringOfObjects.indexOf(',',iIndex);
			//System.out.println("lenght is " + stringOfObjects.length());
			//System.out.println("index is " + fIndex);
			
		}
		
	
	}
	
	public File filePicker() {
		FileChooser fileChooser = new FileChooser();
		 fileChooser.setTitle("Open Resource File");
		 fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("Text Files", "*.txt"),
		         new ExtensionFilter("All Files", "*.*"));
		 File selectedFile = fileChooser.showOpenDialog(mainStage);
		 return selectedFile;
	}
	
	public void renderOptionsMenu() {
		Color cc = Color.color(0, 0, 0.5);
		
		addButton(new OurButton(50,30,400,100,cc,"Change Arena Size"));
		
		addButton(new OurButton(50,30,100,100,cc, Game.arenaHeight + "," + Game.arenaWidth));
		
		addButton(new OurButton(500,30,100,100,cc,"^"));
		
		addButton(new OurButton(610,30,100,100,cc,"v"));
		
		addButton(new OurButton(750,30,150,100,cc,"Defaults"));
		
		addButton(new OurButton(50,210,400,100,cc,"Amount of Traps"));
		
		addButton(new OurButton(50,210,100,100,cc, handler.getNumOfTraps() + ""));
		
		addButton(new OurButton(500,210,100,100,cc,"^ "));
		
		addButton(new OurButton(610,210,100,100,cc,"v "));
		
		addButton(new OurButton(750,210,150,100,cc,"Defaults "));
		
		addButton(new OurButton(50,390,400,100,cc,"Amount of Obsticles"));
		
		addButton(new OurButton(50,390,100,100,cc, handler.getNumOfObsticles() + ""));
		
		addButton(new OurButton(500,390,100,100,cc,"^  "));
		
		addButton(new OurButton(610,390,100,100,cc,"v  "));
		
		addButton(new OurButton(750,390,150,100,cc,"Defaults  "));

		addButton(new OurButton(280,570,400,100,cc,"Back"));

	}
	
	public void renderLoadMenu() {
		Color c = Color.color(0, 0.5, 0);
		
		addButton(new OurButton(280,30,400,100,c,"Pick File"));
		
		addButton(new OurButton(280,570,400,100,c,"Back"));
	}
	
	public void renderMainMenu() {
		Color c = Color.color(0, 0.5, 0.5);
		
		addButton(new OurButton(280,30,400,100,c,"Play game"));
		
		addButton(new OurButton(280,210,400,100,c,"Load game"));
		
		addButton(new OurButton(280,390,400,100,c,"Options"));
		
		addButton(new OurButton(280,570,400,100,c,"Quit"));
		
		
	}

	public void setState(MenuState state) {
		MainMenu.state = state;
		this.timeToUpdate = true;
	}
}

package com.tutorial.main;

import java.io.IOException;
import java.util.Scanner;

import javafx.scene.input.KeyCode;

// The executable class that manages the text-based version of the game.
// May not be compatible with all devices.

// TODO Comments by Braden
public class TextGame {
	
	public static final int WIDTH = 96; // these are arbitrary
	public static final int HEIGHT = 36;
	private String[][] board; // the board
	private Handler handler;
	private Scanner scanner;
	public static int padding = 0;
	public boolean clearScreen = false;
	public static boolean textGameActive = false;
	
//	Create a textGame
	public TextGame() {
		textGameActive = true;
		setupBoard();
		setupScanner();
		handler = new Handler();
		System.out.println("Welcome to the text version of our game!");
		System.out.println("Use W to go up, A to go left, S to go down, D to go right,"
				+ "and anything else to do nothing!");
		System.out.println("Press enter after each character to update!");
		System.out.println("(You are the 'O' character)");
		setAnimationMode();
		
	}
	
//	Setup the board with default values
	public void setupBoard() {
		this.board = new String[WIDTH][HEIGHT];
		this.cleanBoard();
	}
	
//	Cleanup the board to defualt values
	public void cleanBoard() {
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				board[x][y] = contents(x,y);
			}
		}
	}
	
	public void setAnimationMode() {
		System.out.println("Would you like to run in animation mode? (y|n)");
		System.out.print(">");
		String ans = scanner.nextLine();
		this.clearScreen = (ans == "y");
	}
	
//	This returns the default value at any given coordinate.
	public String contents(int x, int y) {
		String t = "";
		if (right(x)) {
			if (top(y)) {
				t = "┐";
			} else if (bottom(y)) {
				t = "┘";
			} else {
				t = "│";
			}
		} else if (left(x)) {
			if (top(y)) {
				t = "┌";
			} else if (bottom(y)) {
				t = "└";
			} else {
				t = "│";
			}
		} else {
			if (top(y)) {
				t = "─";
			} else if (bottom(y)) {
				t = "─";
			} else {
				t = " ";
			}
		}
		return t;
	}
	
//	Check if tile is on left wall
	public boolean left(int x) {
		return x == 0;
	}
	
//	Check if tile is on right wall
	public boolean right(int x) {
		return x == WIDTH - 1;
	}
	
//	Check is tile is on top wall
	public boolean top(int y) {
		return y == 0;
	}
	
//	Check if tile is on bottom wall
	public boolean bottom(int y) {
		return y == HEIGHT - 1;
	}
	
//	Setup the scanner for text entry
	public void setupScanner() {
		scanner = new Scanner(System.in);
	}
	
//	The main executable. 
//	Creates a game and handles the game updates.
	public static void main(String[] args) {
		TextGame g = new TextGame();
		g.handler.addObject(new Player(320,300,ID.Player, g.handler));
		g.handler.addObject(new Enemy(640,300,ID.Enemy, g.handler));
		while (g.handler.player != null) {
			g.showBoard(g);
			g.getInput();
		}
		System.out.println("You have died!");
		g.getInput();
	}
	
//	Gets input from the user. Updates key states
	public void getInput() {
		System.out.println("Next");
		System.out.print(">");
		String output = scanner.nextLine();
		updateKeys(output);
	}
	
//	Updates key states
	public void updateKeys(String output) {
		if (output.contains("w")) {
			handler.keys().press(KeyCode.W);
		} else {
			handler.keys().release(KeyCode.W);
		}
		if (output.contains("a")) {
			handler.keys().press(KeyCode.A);
		} else {
			handler.keys().release(KeyCode.A);
		}
		if (output.contains("s")) {
			handler.keys().press(KeyCode.S);
		} else {
			handler.keys().release(KeyCode.S);
		}
		if (output.contains("d")) {
			handler.keys().press(KeyCode.D);
		} else {
			handler.keys().release(KeyCode.D);
		}
	}
	
//	Update the game.
	public void update() {
		for (int a = 0; a < 10 ; a++) {
			handler.tick();
		}
	}
	
//	Clears the screen. Not compatible with every OS.
	public void clearScreen() throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }

	public void showBoard(TextGame g) {
		cleanBoard();
		if (this.clearScreen) {
			try {
				clearScreen();
			} catch (Error | IOException | InterruptedException e) {
				System.out.print("");
			}
		}
		g.update();
		handler.textRender(board);
		while (TextGame.padding < 6) {
			TextGame.padding++;
			System.out.println();
		}
		padding = 0;
		for (int a = 0; a < HEIGHT; a++) {
			for (int b = 0; b < WIDTH; b++) {
				System.out.print(board[b][a]);
			}
			System.out.println();
		}
	}

}

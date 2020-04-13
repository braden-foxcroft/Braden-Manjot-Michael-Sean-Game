package com.tutorial.main;

import java.io.IOException;
import java.util.Scanner;

import javafx.scene.input.KeyCode;

/**
 * The executable text-based version of the game. Meant to be run through the console.
 * This version of the code will not properly display a moving camera, because that would be visually confusing.
 * This version of the game no longer fully works, as it was written in the AWT framework
 * It still represents the game, and uses the same logic, however.
 */

public class TextGame {
	
	/**
	 * The width of the display area
	 */
	public static final int WIDTH = 96;
	
	/**
	 * The height of the display area
	 */
	public static final int HEIGHT = 36;
	
	/**
	 * The board to display
	 */
	private String[][] board;
	
	/**
	 * A reference to the handler
	 */
	private Handler handler;
	
	/**
	 * A scanner for input
	 */
	private Scanner scanner;
	
	/**
	 * A value that tracks screen padding
	 */
	public static int padding = 0;
	
	/**
	 * A boolean that determines if the screen should reset after each board is shown
	 */
	public boolean clearScreen = false;
	
	/**
	 * A boolean that is true whenever an instance of textGame exists
	 */
	public static boolean textGameActive = false;
	
	/**
	 * Create a new textGame
	 */
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
	
	/**
	 * Create a default board
	 */
	public void setupBoard() {
		this.board = new String[WIDTH][HEIGHT];
		this.cleanBoard();
	}
	
	/**
	 * Clears the values of the board
	 */
	public void cleanBoard() {
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				board[x][y] = contents(x,y);
			}
		}
	}
	
	/**
	 * Sets whether to run in animation mode. Currently does nothing
	 */
	public void setAnimationMode() {
		this.clearScreen = false;
		return;
//		System.out.println("Would you like to run in animation mode? (y|n)");
//		System.out.print(">");
//		String ans = scanner.nextLine();
//		this.clearScreen = (ans == "y");
	}
	
	/**
	 * This returns the default value at any given coordinate.
	 * @param x - the x to check
	 * @param y - the y to check
	 * @return The default character to draw at that position
	 */
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
	
	/**
	 * Check if tile is on left wall
	 * @param x - the x coordinate of the tile
	 * @return A boolean, true if the tile is on the left wall
	 */
	public boolean left(int x) {
		return x == 0;
	}
	
	/**
	 * Check if tile is on right wall
	 * @param x - the x coordinate of the tile
	 * @return A boolean, true if the tile is on the right wall
	 */
	public boolean right(int x) {
		return x == WIDTH - 1;
	}
	
	/**
	 * Check if tile is on top wall
	 * @param y - the y coordinate of the tile
	 * @return A boolean, true if the tile is on the top wall
	 */
	public boolean top(int y) {
		return y == 0;
	}
	
	/**
	 * Check if tile is on bottom wall
	 * @param y - the y coordinate of the tile
	 * @return A boolean, true if the tile is on the bottom wall
	 */
	public boolean bottom(int y) {
		return y == HEIGHT - 1;
	}
	
	/**
	 * Creates a scanner for text entry
	 */
	public void setupScanner() {
		scanner = new Scanner(System.in);
	}
	
	/**
	 * The main executable. 
	 * Creates a game and handles the game updates.
	 */
	public static void main(String[] args) {
		TextGame g = new TextGame();
		g.handler.addObject(new Player(320,300,ID.Player, g.handler));
		g.handler.addObject(new Enemy(640,300,ID.Enemy, g.handler));
		while (g.handler.player != null) {
			g.showBoard();
			g.getInput();
		}
		System.out.println("You have died!");
		g.getInput();
	}
	
	/**
	 * Gets input from the user. Updates key states
	 */
	public void getInput() {
		System.out.println("Next");
		System.out.print(">");
		String output = scanner.nextLine();
		updateKeys(output);
	}
	
	/**
	 * Updates key states
	 * @param output - The string the user entered
	 */
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
	
	/**
	 * Update the game. Performs the tick method 10 times, to produce a visual change to the screen
	 */
	public void update() {
		for (int a = 0; a < 10 ; a++) {
			handler.tick();
		}
	}
	
	/**
	 * Clears the screen.
	 * Not compatible with every OS, so not worth using
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void clearScreen() throws IOException, InterruptedException {
//        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }

	/**
	 * Shows the board on the console
	 */
	public void showBoard() {
		cleanBoard();
		if (this.clearScreen) {
			try {
				clearScreen();
			} catch (Error | IOException | InterruptedException e) {
				System.out.print("");
			}
		}
		this.update();
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

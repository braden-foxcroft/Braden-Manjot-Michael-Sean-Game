package com.tutorial.main;

import java.io.IOException;
import java.util.Scanner;

public class TextGame {
	
	public static final int WIDTH = 96;
	public static final int HEIGHT = 36;
	private String[][] board;
	private Handler handler;
	private Scanner scanner;
	public static int padding = 0;
	
	public TextGame() {
		setupBoard();
		setupScanner();
		handler = new Handler();
		System.out.println("Welcome to the text version of our game!");
		System.out.println("Use W to go up, A to go left, S to go down, D to go right,"
				+ "and anything else to do nothing!");
		System.out.println("Press enter after each character to update!");
		System.out.println("(You are the 'O' character)");
	}
	
	public void setupBoard() {
		this.board = new String[WIDTH][HEIGHT];
		this.cleanBoard();
	}
	
	public void cleanBoard() {
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				board[x][y] = contents(x,y);
			}
		}
	}
	
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
	
	public boolean left(int x) {
		return x == 0;
	}
	
	public boolean right(int x) {
		return x == WIDTH - 1;
	}
	
	public boolean top(int y) {
		return y == 0;
	}
	
	public boolean bottom(int y) {
		return y == HEIGHT - 1;
	}
	
	
	public void setupScanner() {
		scanner = new Scanner(System.in);
	}
	
	public static void main(String[] args) {
		TextGame g = new TextGame();
		g.handler.addObject(new Player(320,300,ID.Player, g.handler));
		g.handler.addObject(new Enemy(640,300,ID.Enemy, g.handler));
		while (g.handler.player() != null) {
			g.showBoard(g);
			g.getInput();
		}
		System.out.println("You have died!");
		g.getInput();
	}
	
	public void getInput() {
		System.out.println("Next");
		System.out.print(">");
		String output = scanner.nextLine();
		updateKeys(output);
	}
	
	public void updateKeys(String output) {
		if (output.contains("w")) {
			handler.setW_Down(true);
		} else {
			handler.setW_Down(false);
		}
		if (output.contains("a")) {
			handler.setA_Down(true);
		} else {
			handler.setA_Down(false);
		}
		if (output.contains("s")) {
			handler.setS_Down(true);
		} else {
			handler.setS_Down(false);
		}
		if (output.contains("d")) {
			handler.setD_Down(true);
		} else {
			handler.setD_Down(false);
		}
	}
	
	public void update() {
		for (int a = 0; a < 10 ; a++) {
			handler.tick();
		}
	}
	
	public void clearScreen() throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }

	public void showBoard(TextGame g) {
		cleanBoard();
		try {
			clearScreen();
		} catch (Error | IOException | InterruptedException e) {
			System.out.print("");
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

package com.tutorial.main;

import java.awt.Canvas;
// import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

//The main game class, run this to run the game.
// Contains code seen in:
// https://www.youtube.com/watch?v=0T1U0kbu1Sk&list=PLWms45O3n--6TvZmtFHaCWRZwEqnz2MHa&index=1
// TODO implement other character classes,
// implement skills and appropriate collision mechanics

public class Game extends Canvas implements Runnable {
	
	/**
	 * 
	 */
	// an arbitrary constant
	private static final long serialVersionUID = 1550691097823471818L;
	
	private Window win;
	public static final int WIDTH = 960, HEIGHT = WIDTH / 12 * 9; // 720
	// this will be a single threaded game, generally not
	// recommended but for the simplicity of this game it will be fine
	private Thread thread;
	private Handler handler;
	private boolean running = false;
	
//	Create a new Game object. Primarily copied code.
	public Game() {
		this.handler = new Handler();
		this.win = new Window(WIDTH, HEIGHT, "Our game", this);
		handler.setCanvas(win.canvas);
		this.addKeyListener(new KeyInput(this.handler));
		handler.addObject(new Player(320,300,ID.Player, handler));
		// handler.addObject(new Ball(200,200,ID.Ball));
		handler.addObject(new Enemy(640,300,ID.Enemy, handler));
	}
	
//	This function causes the game loop to start. Copied code.
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
//	This function causes the game loop to stop. Copied code.
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
//	This is the main game loop. It is mainly copied code.
	public void run() {
		//The Game loop! (not original code)
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0.0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >=1) {
				tick();
				delta--;
			}
			if(running)
				render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				if (frames > 1000) {
					System.out.println("FPS: " + frames);
				}
				frames = 0;
			}
		}
		stop();
		
	}
	
//	This causes all objects to update one 'frame'.
	private void tick() {
		handler.tick();
	}
	
//	This causes all objects to visually update.
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs==null) {
			this.createBufferStrategy(3);
				return;
		}
		
		Graphics g = bs.getDrawGraphics();
		Display d = new Display(g);
		
		d.screenBackground();
		
		handler.render(d);
		
		d.update(bs);
		
	}
	
//	When the program is run, it automatically creates a game.
	public static void main(String args []) {
		
		new Game();
		
		
	}

}

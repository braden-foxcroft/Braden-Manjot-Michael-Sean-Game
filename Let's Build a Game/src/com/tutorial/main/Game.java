package com.tutorial.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

// TODO implement other character classes,
// implement skills and appropriate collision mechanics
// When implementing collisions with dynamic objects, keep in mind that
// the force applied can only act along the direction that the two things collide.
// Also, the momentum in must match the momentum out. Finally, the kinetic energies must match.

public class Game extends Canvas implements Runnable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1550691097823471818L;
	
	private Window win;
	public static final int WIDTH = 960, HEIGHT = WIDTH / 12 * 9; // 720
	// this will be a single threaded game, generally not
	// recommended but for the simplicity of this game it will be fine
	private Thread thread;
	private Handler handler;
	private boolean running = false;
	public Game() {
		this.handler = new Handler();
		this.win = new Window(WIDTH, HEIGHT, "Our game", this);
		handler.setCanvas(win.canvas);
		this.addKeyListener(new KeyInput(this.handler));
		handler.addObject(new Player(100,100,ID.Player));;
		handler.addObject(new Ball(200,200,ID.Enemy));
	}
	
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		//The Game loop! (not original code)
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
	
	
	private void tick() {
		handler.tick();
	}
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs==null) {
			this.createBufferStrategy(3);
				return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		handler.render(g);
		
		g.dispose();
		bs.show();
		
	}
	public static void main(String args []) {
		
		new Game();
		
		
	}

}

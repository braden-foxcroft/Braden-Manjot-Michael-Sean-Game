package com.tutorial.main;

import com.tutorial.display.Display;

import javafx.animation.AnimationTimer;

/**
 * The class that manages screen updates, and the progression of time.
 */

public class AnimationController extends AnimationTimer{

	/**
	 * The instance of the handler containing every gameObject
	 */
	private Handler handler;
	
	/**
	 * The instance of the Display class that draws to the window.
	 */
	private Display display;
	
	/**
	 * The amount of time since the last time handle(long) was called.
	 */
	private double delta = 0.0;
	
	/**
	 * The absolute time handle(long) was last called
	 */
	private long lastTime = System.nanoTime();
	
	/**
	 * The number of times the screen should update a second. Represents the rate at which time elapses in-game
	 */
	private double amountOfTicks = 60.0;
	
	/**
	 * A coefficient that represents the number of nanoseconds per tick
	 */
	private double ns = 1000000000 / amountOfTicks;
	
	/**
	 * A timer that is used to track frame rate
	 */
	private long timer = System.currentTimeMillis();
	
	/**
	 * The number of frames that elapsed in the most recent second
	 */
	private int frames = 0;
	
	/**
	 * Creates an animationController
	 * @param handler - The instance of the handler the game uses
	 * @param d - The instance of the display the game uses
	 */
	public AnimationController(Handler handler, Display d) {
		super();
		this.handler = handler;
		handler.setup();
		this.display = d;
	}
	
	/**
	 * The game loop. Called every time the screen updates. (60/sec)
	 * @param now - The moment in time that the method was called.
	 */
	public void handle(long now) {
		delta += (now - lastTime) / ns;
		lastTime = now;
		while(delta >=1) {
			tick();
			delta--;
		}
		render();
		frames++;
		if(System.currentTimeMillis() - timer > 1000) {
			timer += 1000;
			if (frames < 40) {
				System.out.println("FPS: " + frames);
			} else if (frames < 3) {
				System.err.println("Framerate dropped below 3 per second.");
				System.err.println("Process killed.");
				System.err.println("This means you are probably doing something very");
				System.err.println("intense. Consult with Braden to make sure you aren't");
				System.err.println("causing an issue.");
				System.exit(0);
			}
			frames = 0;
		}
	}

	/**
	 * The method that causes every in-game object to render
	 */
	private void render() {
		handler.render(display);
	}
	
	/**
	 * The method that causes every in-game object to update its values
	 */
	private void tick() {
		handler.tick();
	}
	
}

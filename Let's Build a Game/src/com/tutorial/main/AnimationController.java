package com.tutorial.main;

import javafx.animation.AnimationTimer;

//Animation controller manages the frame updates. It's the new 'game loop'

public class AnimationController extends AnimationTimer{

	private Handler handler;
	private Display display;
	private double delta = 0.0;
	private long lastTime = System.nanoTime();
	private double amountOfTicks = 60.0;
	private double ns = 1000000000 / amountOfTicks;
	private long timer = System.currentTimeMillis();
	private int frames = 0;
	
//	Create the controller
	public AnimationController(Handler handler, Display d) {
		super();
		this.handler = handler;
		handler.setup();
		this.display = d;
	}
	
//	The game loop. Called every time the screen updates
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
			if (frames > 100) {
				System.out.println("FPS: " + frames);
			}
			frames = 0;
		}
	}

//	The render method
	private void render() {
		handler.render(display);
	}
	
//	The tick method
	private void tick() {
		handler.tick();
	}
	
}

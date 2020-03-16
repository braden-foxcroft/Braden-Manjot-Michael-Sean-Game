package com.tutorial.main;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class MouseClickHandler implements EventHandler<MouseEvent> {

	private Handler handler;
	private double mouseX = 0;
	private double mouseY = 0;
	
	public double x() {
		return this.mouseX;
	}
	
	public double y() {
		return this.mouseY;
	}
	
	public MouseClickHandler(Handler handler) {
		this.handler = handler;
	}
	
	public void handle(MouseEvent event) {
		if (event.getEventType() == MouseEvent.MOUSE_CLICKED)
		{
			this.mouseX = (float)event.getSceneX();
			this.mouseY = (float)event.getSceneY();
			handler.clickEvent(mouseX, mouseY);
		}
	}
	
	
}

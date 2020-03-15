package com.tutorial.main;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class MouseClickHandler implements EventHandler<MouseEvent> {

	private Handler handler;
	private double mouseX = 0;
	private double mouseY = 0;
	private Camera cam;
	
	public double x() {
		return this.mouseX;
	}
	
	public double y() {
		return this.mouseY;
	}
	
	public void setCam(Camera cam) {
		this.cam = cam;
	}
	
	public MouseClickHandler(Handler handler) {
		this.handler = handler;
	}
	
	public void handle(MouseEvent event) {
		if (event.getEventType() == MouseEvent.MOUSE_CLICKED)
		{
			this.mouseX = cam.reverseEngineerX((float)event.getSceneX());
			this.mouseY = cam.reverseEngineerY((float)event.getSceneY());
			handler.clickEvent(mouseX, mouseY);
		}
	}
	
	
}

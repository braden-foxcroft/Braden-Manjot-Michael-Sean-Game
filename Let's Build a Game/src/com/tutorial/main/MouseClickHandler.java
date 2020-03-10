package com.tutorial.main;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class MouseClickHandler implements EventHandler<MouseEvent> {

	private Handler handler;
	
	public MouseClickHandler(Handler handler) {
		this.handler = handler;
	}
	
	public void handle(MouseEvent event) {
		if (event.getEventType() == MouseEvent.MOUSE_CLICKED)
		{
			handler.clickEvent(event.getX(), event.getY());
		}
	}
	
	
}

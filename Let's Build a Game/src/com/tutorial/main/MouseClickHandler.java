package com.tutorial.main;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;


/**
 * A mouse click handler
 */

public class MouseClickHandler implements EventHandler<MouseEvent> {

	/**
	 * The handler to inform of click events
	 */
	private Handler handler;
	
	/**
	 * The current mouse x position
	 */
	private double mouseX = 0;
	
	/**
	 * The current mouse y position
	 */
	private double mouseY = 0;
	
	/**
	 * Gets the current mouse x position
	 * @return The current mouse x position
	 */
	public double x() {
		return this.mouseX;
	}
	
	/**
	 * Gets the current mouse y position
	 * @return The current mouse y position
	 */
	public double y() {
		return this.mouseY;
	}
	
	/**
	 * Sets the handler to inform of click events
	 * @param handler - the handler to inform
	 */
	public MouseClickHandler(Handler handler) {
		this.handler = handler;
	}
	
//	See the documentation for the implemented/overridden method
	public void handle(MouseEvent event) {
		if (event.getEventType() == MouseEvent.MOUSE_CLICKED)
		{
			this.mouseX = (float)event.getSceneX();
			this.mouseY = (float)event.getSceneY();
			handler.clickEvent(mouseX, mouseY);
		}
	}
	
	
}

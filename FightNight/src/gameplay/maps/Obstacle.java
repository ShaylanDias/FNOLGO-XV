package gameplay.maps;

import java.io.Serializable;
import processing.core.PApplet;


public class Obstacle implements Serializable{
	
	private double xPos, yPos, width, height;
	
	public Obstacle(double x, double y, double width, double height) {
		xPos = x;
		yPos =y;
		this.width = width;
		this.height = height; 
	}
	/**
	 * Based on an x, y position centered in the top left corner of the rectangle hitbox
	 * @param x
	 * @param y
	 * @return returns true if the 2 hitboxes run into each other, false otherwise. 
	 */
	public boolean checkIntersection(double x, double y, double width, double height) {
		if(x >= xPos && x <= xPos+this.width && y>=yPos && y<= yPos+this.height) {
			return true;
		}
		
		return false;
	}

}

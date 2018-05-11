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
	 * Based on an x, y position based off of the center of the rectangle hitbox
	 * @param x
	 * @param y
	 * @return returns true if the 2 hitboxes run into each other, false otherwise. 
	 */
	public boolean checkIntersection(double x, double y, double width, double height) {
		if(x+width/2 >= xPos-width/2 && x-width <= xPos+this.width/2 && y+height/2 >= yPos-height/2 && y-height/2<= yPos+this.height/2) {
			return true;
		}
		
		return false;
	}

}

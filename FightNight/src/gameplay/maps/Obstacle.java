package gameplay.maps;

import java.io.Serializable;
import processing.core.PApplet;

/**
 * Creates a circular obstacle
 * @author jason
 *
 */
public class Obstacle implements Serializable{
	
	private double xPos, yPos, radius;
	
	public Obstacle(double x, double y, double radius) {
		xPos = x;
		yPos =y;
		this.radius = radius;
	}
	/**
	 * Based on an x, y position based off of the center of the rectangle hitbox
	 * @param x
	 * @param y
	 * @return returns true if the 2 hitboxes run into each other, false otherwise. 
	 */
	public boolean checkIntersection(double x, double y, double width, double height) { //Rectangle and circle
		
//		double xDiff = radius + width/2;
//		double yDiff = radius + height/2;
//		
//		if(Math.abs(x-xPos)<xDiff && Math.abs(y-yPos) <yDiff) {
//			return true;
//		}
		//*** e.James stack overflow
		double xDistance = Math.abs(xPos-x);
		double yDistance = Math.abs(yPos-y);
		
		if(xDistance > (width/2+radius)) 
			return false;
		if(yDistance > (height/2+radius)) 
			return false;
		
		if(xDistance <= width/2)
			return true;
		if(yDistance <= height/2)
			return true;
		
		double cornerDistance = Math.pow(xDistance - width/2,2) + Math.pow(yDistance - height/2,2);
		
		
		return (cornerDistance <= radius*radius);
	}
	
	public boolean checkIntersection(double x, double y, double r) { //circle and circle
		
		double xDiff = radius + r;
		double yDiff = radius + r;
		
		if(Math.abs(x-xPos)<xDiff && Math.abs(y-yPos) <yDiff) {
			return true;
		}
		return false;
	}
}

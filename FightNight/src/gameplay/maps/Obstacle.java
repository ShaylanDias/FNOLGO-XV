package gameplay.maps;

import java.io.Serializable;
import processing.core.PApplet;


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
	public boolean checkIntersection(double x, double y, double width, double height) {
		
		double xDiff = radius + width/2;
		double yDiff = radius + height/2;
		//System.out.println(xDiff);
		//System.out.println(yDiff);
		
		if(Math.abs(x-xPos)<xDiff && Math.abs(y-yPos) <yDiff) {
			return true;
		}
		
//		if(x-width/2< xPos+this.width/2 && x+width/2 >xPos-width/2 && y-height/2 < yPos +this.height && y +height/2 > yPos -height/2) {
//			System.out.println("Intersects");
//			return true;
//		}
//		
		return false;
	}

}

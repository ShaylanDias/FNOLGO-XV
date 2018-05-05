package gameplay;

import java.awt.geom.Rectangle2D;
import processing.core.PApplet;
import processing.core.PImage;

/*
 * Represents a moving image.
 *
 * by: Shelby
 * on: 5/3/13
 */
 
/**
 * 
 * Shelby's MovingImage class for our convenience
 * 
 * @author shaylandias
 *
 */
public class MovingImage extends Rectangle2D.Double {
	
	// FIELDS
	private PImage image;
	
	/**
	 * 
	 * Creates a MovingImage with the given image and dimensions x, y, w, h
	 * 
	 * @param x The X-Coordinate of the upper left corner
	 * @param y The Y-Coordinate of the upper left corner
	 * @param w The width of the image
	 * @param h The height of the image
	 */
	public MovingImage(PImage img, int x, int y, int w, int h) {
		super(x,y,w,h);
		image = img;
	}
	
	
	// METHODS	
	public void moveToLocation(double x, double y) {
		super.x = x;
		super.y = y;
	}
	
	public void moveByAmount(double x, double y) {
		super.x += x;
		super.y += y;
	}
	
	public void applyWindowLimits(int windowWidth, int windowHeight) {
		x = Math.min(x,windowWidth-width);
		y = Math.min(y,windowHeight-height);
		x = Math.max(0,x);
		y = Math.max(0,y);
	}
	
	/**
	 * 
	 * Draws this image
	 * 
	 * @param g The surface to draw to
	 */
	public void draw(PApplet g) {
		g.image(image,(int)x,(int)y,(int)width,(int)height);
	}
	
}











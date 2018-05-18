package gameplay.maps;

import java.io.Serializable;

import processing.core.PApplet;

/**
 * 
 * Stores information for the game map
 * 
 * @author shaylandias
 *
 */
public abstract class Map implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1577851729546186971L;
	
	public void draw(PApplet surface) {

	}

	/**
	 * Checks if something, namely the Avatar, collides with a tree object 
	 * 
	 * @param x X coordinate of the character
	 * @param y Y coordinate of the character
	 * @param width character's width 
	 * @param height character's height 
	 * @return true if it does collide with a tree
	 */
	public abstract boolean hitTree(double x, double y, double width, double height);
	
	/**
	 * Checks if something, namely the Avatar, is exiting the Map
	 * 
	 * @param x X coordinate of the character
	 * @param y Y coordinate of the character
	 * @param width character's width 
	 * @param height character's height 
	 * @return True if it would leave the map
	 */
	public abstract boolean inBounds(double x, double y, double width, double height);
}

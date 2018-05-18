package gameplay.maps;

import clientside.gui.GamePanel;
import processing.core.PApplet;

public class Tree extends Obstacle{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8295756930657718993L;
	private static String imageKey = "SmallTree";
	private double xPos,yPos, radius;
	private boolean canDraw;
	
	/**
	 * 
	 * Instantiates a tree
	 * 
	 * @param x The x-coord
	 * @param y The y-coord
	 * @param radius The radius of the tree
	 */
	public Tree(double x, double y, double radius) {
		super(x,y,radius);
		xPos = x;
		yPos = y;
		this.radius = radius;
		canDraw = false;
	}
	
	/**
	 * 
	 * Draws the Tree
	 * 
	 * @param surface The surface to draw to
	 */
	public void draw(PApplet surface) {
		surface.image(GamePanel.resources.getImage(imageKey),(float) xPos,(float) yPos, (int) radius*2, (int) radius*2);

	}
	
	
	public double getRadius() {
		return radius;
	}
	public double getX() {
		return xPos;
	}
	public double getY() {
		return yPos;
	}
	/**
	 * makes the tree Visible
	 */
	public void doDraw() {
		canDraw = true;
	}
	/**
	 * 
	 * @return true if the tree should be drawn
	 */
	public boolean canDraw() {
		return canDraw;
	}
}

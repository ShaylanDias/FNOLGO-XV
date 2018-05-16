package gameplay.maps;

import clientside.gui.GamePanel;
import processing.core.PApplet;

public class Tree extends Obstacle{
	
	private static String imageKey = "SmallTree";
	private double xPos,yPos, radius;
	private boolean canDraw;
	public Tree(double x, double y, double radius) {
		super(x,y,radius);
		xPos = x;
		yPos = y;
		this.radius = radius;
		canDraw = false;
	}
	
	public void draw(PApplet tree) {
		tree.image(GamePanel.resources.getImage(imageKey),(float) xPos,(float) yPos, (int) radius*2, (int) radius*2);
		tree.ellipse((float)xPos, (float)yPos,(float) radius*2, (float)radius*2);

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

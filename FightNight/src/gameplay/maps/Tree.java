package gameplay.maps;

import java.io.Serializable;

import clientside.gui.GamePanel;
import processing.core.PApplet;

public class Tree extends Obstacle{
	
	private static String imageKey = "SmallTree";
	private double xPos,yPos, width, height;
	private boolean canDraw;
	public Tree(double x, double y, double width, double height) {
		super(x,y,width,height);
		xPos = x;
		yPos = y;
		this.width = width;
		this.height = height;
		canDraw = false;
	}
	
	public void draw(PApplet tree) {
		tree.image(GamePanel.resources.getImage(imageKey),(float) xPos,(float) yPos, (int) width, (int) height);
		tree.rectMode(PApplet.CENTER);
		tree.rect((float)xPos, (float)yPos,(float) width, (float)height);

	}
	
	public double getWidth() {
		return width;
	}
	public double getHeight() {
		return height;
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
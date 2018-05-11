package gameplay.maps;

import java.io.Serializable;

import clientside.gui.GamePanel;
import processing.core.PApplet;

public class Tree extends Obstacle{
	
	private static String imageKey = "SmallTree";
	private double xPos,yPos, width, height;
	public Tree(double x, double y, double width, double height) {
		super(x,y,width,height);
		xPos = x;
		yPos = y;
		this.width = width;
		this.height = height;
		
	}
	
	public void draw(PApplet tree) {
		tree.image(GamePanel.resources.getImage(imageKey),(float) xPos,(float) yPos, (int) width, (int) height);

	}
	
	public double getWidth() {
		return width;
	}
	public double getHeight() {
		return height;
	}
}

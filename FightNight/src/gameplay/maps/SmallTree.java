package gameplay.maps;

import clientside.gui.GamePanel;
import processing.core.PApplet;

public class SmallTree extends Obstacle{
	
	private static String imageKey = "SmallTree";
	private double xPos,yPos;
	public SmallTree(double x, double y) {
		super(x,y,120,120);
		xPos = x;
		yPos = y;
		
	}
	
	public void draw(PApplet tree) {
		tree.image(GamePanel.resources.getImage(imageKey),(float) xPos,(float) yPos, (int) 120, (int) 120);

	}
}

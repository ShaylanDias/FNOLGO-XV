package gameplay.maps;

import clientside.gui.GamePanel;
import processing.core.PApplet;

public class LargeTree extends Obstacle{
		
	private static String imageKey = "SmallTree";
	private double xPos,yPos;
	
	public LargeTree(double x, double y) {
		super(x,y,180,180);
		xPos = x;
		yPos = y;
		
	}
	
	public void draw(PApplet tree) {
		tree.image(GamePanel.resources.getImage(imageKey),(float) xPos,(float) yPos, (int) 120, (int) 120);

	}
	
}

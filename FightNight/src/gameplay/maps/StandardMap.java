package gameplay.maps;

import clientside.gui.GamePanel;
import processing.core.PApplet;

/**
 * 
 * The base Map
 * 
 * @author shaylandias
 *
 */
public class StandardMap extends Map{

	private static String imageKey = "FNOLGO MAP";

	public StandardMap() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void draw(PApplet surface) {
		super.draw(surface);
		surface.image(GamePanel.resources.getImage(imageKey), 0, 0, (int) 3000, (int) 3000);
		surface.noFill();
		surface.rect(-1500, -1500, 3000, 3000);

	}

}

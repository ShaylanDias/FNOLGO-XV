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
	private SmallTree[] sTree = new SmallTree[25];
	private LargeTree[] lTree = new LargeTree[25];

	
	
	public StandardMap() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void draw(PApplet surface) {
		super.draw(surface);
		surface.image(GamePanel.resources.getImage(imageKey), 0, 0, (int) 3000, (int) 3000);
		surface.noFill();
		surface.rect(-1500, -1500, 3000, 3000);
		
		
		//Left quadrant tree hitboxes going top down
//		surface.rect(-1440, -1080, 180, 180);
//		surface.rect(-1260, -720, 120, 120);
//		surface.rect(-1440, -540, 120, 120);
//		surface.rect(-1080, -540, 180, 180);
//		surface.rect(-1380, -240, 120, 120);
//		surface.rect(-960, -240, 120, 120);
//		surface.rect(-720, -300, 180, 180);
//		surface.rect(-420, -60, 120, 120);
//		surface.rect(-1140, 0, 180, 180);
//		surface.rect(-780, 180, 120, 120);
//		surface.rect(-1320, 360, 120, 120);
//		surface.rect(-960, 360, 120, 120);
//		surface.rect(-1200, 600, 120, 120);
//		surface.rect(-1380, 780, 120, 120);



		


		
		

	}

}

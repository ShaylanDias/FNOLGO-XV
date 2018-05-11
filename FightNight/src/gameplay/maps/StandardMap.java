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
	private Tree[] Tree = new Tree[100];

	
	
	public StandardMap() {
		super();
		// TODO Auto-generated constructor stub
		for(int i = 0; i<Tree.length;i++) {
			double size = Math.random()*250;
			if(size>100) {
				Tree[i] = new Tree(Math.random()*3000.0-1500,Math.random()*3000.0-1500, size, size);
			}	
			else
				i--;
		}
		for(int i = 0; i<Tree.length;i++) {
			for(int j = 0;j<Tree.length;j++) {
				
				//Some checks if the trees are interesecting each other. Check the code in trees. I think I might just be stupid. 
				if(!Tree[i].checkIntersection(Tree[j].getX(),Tree[j].getY() , Tree[j].getWidth(), Tree[j].getHeight())) {
					Tree[j].doDraw() ;
				}
			}
		}
	}
	
	public void setup() {
		
	}
	
	public void draw(PApplet surface) {
		super.draw(surface);
		surface.image(GamePanel.resources.getImage(imageKey), 0, 0, (int) 3000, (int) 3000);
		surface.noFill();
		surface.rect(-1500, -1500, 3000, 3000);
		
		for(int i = 0; i< Tree.length;i++) {
			if(Tree[i].canDraw())
				Tree[i].draw(surface);
		}
		
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

package gameplay.maps;

import clientside.gui.GamePanel;
import processing.core.PApplet;

/**
 * 
 * The base Map
 * 
 * @author Jason Zhu
 *
 */
public class StandardMap extends Map{

	private static String imageKey = "FNOLGO MAP";
	private Tree[] Tree = new Tree[100]; //optimization possiblity: arraylist instead of array, small improvement

	
	
	public StandardMap() {
		super();
		for(int i = 0; i<Tree.length;i++) {
			double size = Math.random()*250;
			if(size>100) {
				Tree[i] = new Tree(Math.random()*3000.0-1500,Math.random()*3000.0-1500, size/2);
			}	
			else
				i--;
		}
		int counter = 0;
		
		for(int i = 0; i<Tree.length;i++) {
			boolean Intersected = false;
			for(int j = i+1;j<Tree.length;j++) {
				
				//Some checks if the trees are interesecting each other. Check the code in trees. I think I might just be stupid. 
				if(Tree[i].checkIntersection(Tree[j].getX(),Tree[j].getY() , Tree[j].getRadius()*2, Tree[j].getRadius()*2)) {
					Intersected = true;
					break;					
				}
			}
			if(!Intersected) {
				Tree[i].doDraw() ;
				counter++;
			}

		}
	}
	
	
	
	public void draw(PApplet surface) {
		super.draw(surface);
		surface.image(GamePanel.resources.getImage(imageKey), 0, 0, (int) 3000, (int) 3000);
		surface.noFill();
		surface.rectMode(PApplet.CORNER);
		surface.rect(-1500, -1500, 3000, 3000);
		
		for(int i = 0; i< Tree.length;i++) {
			if(Tree[i].canDraw()) {
				Tree[i].draw(surface);
			}
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
	
	/**
	 * checks if something, namely the avatar, collides with a tree object 
	 * 
	 * @param x X coordinate of the character
	 * @param y Y coordinate of the character
	 * @param width character's width 
	 * @param height character's height 
	 * @return true if it does collide with a tree
	 */
	public boolean hitTree(double x, double y, double width, double height) { 
		
		for(int i = 0; i<Tree.length;i++) {
			if(Tree[i].canDraw() && Tree[i].checkIntersection(x,y,width,height)){
				return true;
			}
		}
		return false;
	}
	public boolean inBounds(double x, double y , double width, double height) {
		if(x+width/2>1500 || x-width/2< -1500 || y+height/2>1500 || y-height/2< -1500)
			return false;
		
		return true;
	}

}

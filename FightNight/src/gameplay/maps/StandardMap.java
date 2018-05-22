package gameplay.maps;

import clientside.gui.GamePanel;
import processing.core.PApplet;

/**
 * 
 * The base Map
 * 
 * @author jasonzu
 *
 */
public class StandardMap extends Map{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6466944145494309792L;
	private static String imageKey = "FNOLGO MAP";
	private Tree[] Tree = new Tree[100];

	
	/**
	 * Instantiates the standard map
	 */
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
		
		for(int i = 0; i<Tree.length;i++) {
			boolean Intersected = false;
			for(int j = i+1;j<Tree.length;j++) {
				
				//Some checks if the trees are interesecting each other. Check the code in trees. I think I might just be stupid. 
				if(Tree[i].checkIntersection(Tree[j].getX(),Tree[j].getY() , Tree[j].getRadius())) {
					Intersected = true;
					break;					
				}
			}
			if(!Intersected) {
				Tree[i].doDraw() ;
			}

		}
	}
	
	
	@Override
	public void draw(PApplet surface) {
		surface.image(GamePanel.resources.getImage(imageKey), 0, 0, (int) 3000, (int) 3000);
		surface.noFill();
		surface.rectMode(PApplet.CORNER);
		surface.rect(-1500, -1500, 3000, 3000);
		
		for(int i = 0; i< Tree.length;i++) {
			if(Tree[i].canDraw()) {
				Tree[i].draw(surface);
			}
		}

	}
	
	@Override
	public boolean hitTree(double x, double y, double width, double height) { 
		
		for(int i = 0; i<Tree.length;i++) {
			if(Tree[i].canDraw() && Tree[i].checkIntersection(x,y,width,height)){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean inBounds(double x, double y , double width, double height) {
		if(x+width/2>1500 || x-width/2< -1500 || y+height/2>1500 || y-height/2< -1500)
			return false;
		
		return true;
	}

}

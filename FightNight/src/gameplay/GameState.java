package gameplay;

import java.io.Serializable;
import java.util.ArrayList;

import gameplay.attacks.Attack;
import gameplay.avatars.Avatar;
import gameplay.maps.Map;
import gameplay.maps.StandardMap;
import processing.core.PApplet;

/**
 * 
 * Stores the gamedata to send from the server to the GamePanel to be drawn
 * 
 * @author shaylandias
 *
 */
public class GameState implements Serializable {

	private static final long serialVersionUID = 2671962781505513505L;

	private ArrayList<Avatar> avatars;
	private ArrayList<Attack> attacks;
	private Map map;

	/**
	 * Instantiates the ArrayLists in the GameState
	 */
	public GameState() {
		avatars = new ArrayList<Avatar>();
		attacks = new ArrayList<Attack>();
		map = new StandardMap();
	}

	/**
	 * 
	 * Draws the GameState to a PApplet
	 * 
	 * @param surface
	 *            The PApplet to draw to
	 * @param av The Avatar to draw with respect to           
	 *  
	 */
	public void draw(PApplet surface, Avatar av, float width, float height) {
		map.draw(surface);
		surface.translate((float) (-av.getX() + width / 2), (float) -av.getY() + height / 2);
		for (Avatar c : avatars)
			c.draw(surface);
		for (Attack a : attacks)
			a.draw(surface);
	}

//	public void drawMap() {
//		
//	}
//	
	/**
	 * 
	 * Gets the Avatars in the Game
	 * 
	 * @return ArrayList of Avatars
	 */
	public ArrayList<Avatar> getAvatars() {
		return avatars;
	}

	/**
	 * 
	 * Adds an Avatar to the Game
	 * 
	 * @param a
	 *            Avatar to add
	 */
	public void addAvatar(Avatar a) {
		avatars.add(a);
	}

	/**
	 * 
	 * Adds an Attack to the Game
	 * 
	 * @param a
	 *            Attack to add
	 */
	public void addAttack(Attack a) {
		attacks.add(a);
	}

	/**
	 * 
	 * Gets the Attacks in the Game
	 * 
	 * @return ArrayList of Attacks
	 */
	public ArrayList<Attack> getAttacks() {
		return attacks;
	}

	/**
	 * 
	 * Gets the Map
	 * 
	 * @return The Map
	 */
	public Map map() {
		return map;
	}

	public String toString() {
		return avatars.get(0).getX() + "";
	}

}

package gameplay;

import java.io.Serializable;
import java.util.ArrayList;

import gameplay.attacks.Attack;
import gameplay.avatars.Avatar;
import gameplay.avatars.Ranger;
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

	private long gameTime;
	
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
		gameTime = System.currentTimeMillis();
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
	public void draw(PApplet surface, Avatar av, float width, float height, String playerAddress) {
		surface.translate((float) (-av.getX() + width / 2), (float) -av.getY() + height / 2);
		map.draw(surface);
		for (Avatar c : avatars) {
			if(c instanceof Ranger) {
				if(((Ranger)c).isInvisible()) {
					if(c.getPlayer().equals(playerAddress))
						c.draw(surface, getGameTime());
					else {
						if(((Ranger) c).isSmoke())
							c.draw(surface, getGameTime());
					}
				} else
					c.draw(surface, getGameTime());
			} else
				c.draw(surface, getGameTime());
		}
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
	public void addAttacks(Attack[] a) {
		if(a != null) {
			for(int i = 0; i < a.length; i++) {
				attacks.add(a[i]);
			}
		}
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
	public Map getMap() {
		return map;
	}

	public long getGameTime() {
		return gameTime;
	}
	
	public void setGameTime(long time) {
		gameTime = time;
	}
	
	public String toString() {
		return avatars.get(0).getX() + "";
	}

}

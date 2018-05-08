package gameplay;

import java.io.Serializable;
import java.util.ArrayList;

import gameplay.attacks.Attack;
import gameplay.avatars.Avatar;
import gameplay.avatars.Brute;
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

	public GameState() {
		avatars = new ArrayList<Avatar>();
		attacks = new ArrayList<Attack>();
		map = new StandardMap();
//		avatars.add(new Brute());

	}

	public void draw(PApplet surface) {
		for (Avatar c : avatars)
			c.draw(surface);
		for (Attack a : attacks)
			a.draw(surface);
	}

	public ArrayList<Avatar> getAvatars() {
		return avatars;
	}

	public void addAvatar(Avatar a) {
		avatars.add(a);
	}

	public void addAttack(Attack a) {
		attacks.add(a);
	}

	public ArrayList<Attack> getAttacks() {
		return attacks;
	}

	public Map map() {
		return map;
	}

	public String toString() {
		return avatars.get(0).getX() + "";
	}

}

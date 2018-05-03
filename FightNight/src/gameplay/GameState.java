package gameplay;

import java.io.Serializable;
import java.util.ArrayList;

import processing.core.PApplet;

public class GameState implements Serializable {

	private static final long serialVersionUID = 2671962781505513505L;
	
	private ArrayList<Avatar> avatars;
	private ArrayList<Projectile> projectiles;
	private Map map;
	
	public GameState() {
		avatars = new ArrayList<Avatar>();
		projectiles = new ArrayList<Projectile>();
		map = new Map();

	}
	
	public void draw(PApplet surface) {
		for(Avatar c : avatars)
			c.draw(surface);
		for(Projectile p : projectiles)
			p.draw(surface);
	}

	public ArrayList<Avatar> getAvatars(){
		return avatars;
	}
	
	public ArrayList<Projectile> getProjectiles(){
		return projectiles;
	}
	
	public Map map() {
		return map;
	}
	
}

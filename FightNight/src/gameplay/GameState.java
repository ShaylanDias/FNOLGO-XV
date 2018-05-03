package gameplay;

import java.io.Serializable;
import java.util.ArrayList;

import processing.core.PApplet;

public class GameState implements Serializable {

	private ArrayList<Character> characters;
	private ArrayList<Projectile> projectiles;

	public void draw(PApplet surface) {
		for(Character c : characters)
			c.draw(surface);
		for(Projectile p : projectiles)
			p.draw(surface);
	}

}

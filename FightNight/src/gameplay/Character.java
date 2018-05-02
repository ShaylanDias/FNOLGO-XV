package gameplay;

import java.util.ArrayList;
import java.util.Timer;

/**
 * 
 * Represents an abstract player character in the game
 * 
 * @author shaylandias
 *
 */
public abstract class Character{

	private int playerNum;
	private double x, y;
	private double damage;
	private long timeStarted;
	private boolean stunned, midAction, superArmor, shielded;
	private ArrayList<MovingImage> hitboxes;

	/**
	 * Initializes a Character with default values
	 */
	public Character() {
		hitboxes = new ArrayList<MovingImage>();
		x = 100;
		y = 100;
		timeStarted = System.currentTimeMillis();
		shielded = false;
		superArmor = false;
		midAction = false;
		stunned = false;
		damage = 0;
	}
	
	/**
	 * 
	 * Initializes a Character at the given x and y
	 * 
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 */
	public Character(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	public void takeHit(Attack attack) {
		if(playerNum != attack.getPlayer()) {
			damage += attack.getDamage();
		}
	}

	public void moveBy(double x, double y) {
		this.x += x;
		this.y += y;
	}

	public void moveTo(double x, double y) {
		this.x = x;
		this.y = y;
	}

}

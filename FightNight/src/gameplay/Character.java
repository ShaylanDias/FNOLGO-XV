package gameplay;

import java.util.ArrayList;

import processing.core.PApplet;

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
	//Angle from vertical that Character is facing, 0-360 going right
	private double angle;
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
		angle = 0;
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
	 * @param angle The angle from vertical the Character is facing
	 */
	public Character(double x, double y, double angle) {
		super();
		this.x = x;
		this.y = y;
		this.angle = angle;
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
	
	public void draw(PApplet surface) {
		
	}

}

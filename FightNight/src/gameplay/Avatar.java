package gameplay;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * 
 * Represents an abstract player character in the game
 * 
 * @author shaylandias
 *
 */
public abstract class Avatar{

	public enum AttackType{P, DIRECTION, ATTACK};
	
	/**
	 * Contains the sprites for this Avatar, in subclasses define which spot in the
	 * array corresponds to what image.
	 */
	protected PImage[] sprites;
	
	private int playerNum;
	private double x, y;
	//Angle from vertical that Character is facing, 0-360 going right
	private double angle;
	private double damage;
	private long timeActionStarted;
	private boolean stunned, midAction, superArmor, shielded;
	private ArrayList<MovingImage> hitboxes;

	/**
	 * Initializes a Character with default values
	 */
	public Avatar() {
		hitboxes = new ArrayList<MovingImage>();
		x = 100;
		y = 100;
		angle = 0;
		timeActionStarted = System.currentTimeMillis();
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
	public Avatar(double x, double y, double angle) {
		super();
		this.x = x;
		this.y = y;
		this.angle = angle;
	}

	/**
	 * 
	 * Hits a player with an Attack
	 * 
	 * @param attack The attack that hits
	 * @return The result of the attack
	 */
	public AttackResult takeHit(Attack attack) {
		if(playerNum != attack.getPlayer()) {
			damage += attack.getDamage();
			return AttackResult.SUCCESS;
		} else {
			return AttackResult.MISSED;
		}
	}

	/**
	 * 
	 * Moves the Avatar by the input x and y values
	 * 
	 * @param x X to move
	 * @param y Y to move
	 */
	public void moveBy(double x, double y) {
		this.x += x;
		this.y += y;
	}

	/**
	 * 
	 * Moves Avatar to an x,y coordinate
	 * 
	 * @param x X-Coordinate
	 * @param y Y-Coordinate
	 */
	public void moveTo(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * 
	 * Turns by input angle;
	 * 
	 * @param angle Angle to turn by
	 */
	public void turn(double angle) {
		this.angle += angle;
	}
	
	/**
	 * 
	 * Turns to this angle, vertical is 0
	 * 
	 * @param angle Angle to turn to
	 */
	public void turnTo(double angle) {
		this.angle = angle;
	}
	
	/**
	 * 
	 * Gets the number of the Player that owns this Avatar
	 * 
	 * @return
	 */
	public int getPlayer() {
		return playerNum;
	}
	
	/**
	 * 
	 * Gets the X-Coordinate of the Avatar
	 * 
	 * @return The X-Coordinate of the Avatar
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * 
	 * Gets the Y-Coordinate of the Avatar
	 * 
	 * @return The Y-Coordinate of the Avatar
	 */
	public double getY() {
		return y;
	}
	
	public void draw(PApplet surface) {
		
	}

}

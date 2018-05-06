package gameplay.avatars;

import java.awt.Rectangle;
import java.util.ArrayList;

import clientside.Resources;
import clientside.gui.GamePanel;
import gameplay.Drawable;
import gameplay.attacks.Attack;
import gameplay.attacks.Attack.AttackResult;
import gameplay.attacks.MovingSprite;
import gameplay.attacks.StatusEffect;
import gameplay.attacks.StatusEffect.Effect;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * 
 * Represents an abstract player character in the game
 * 
 * @author shaylandias
 *
 */
public abstract class Avatar implements Drawable {

	public enum AttackType{P, DIRECTION, ATTACK};

	/**
	 * Contains the sprites for this Avatar, in subclasses define which spot in the
	 * array corresponds to what image.
	 */
	protected String spriteSheetKey;
	protected Rectangle[] sprites;
	protected int spriteInd;
	
	private int playerNum;
	private double x, y;
	private double w, h;
	//Angle from right horizontal that Character is facing, 0-360 going left
	private double angle;
	private double health;
	private long timeActionStarted;
	private StatusEffect status;
	protected boolean shielded, superArmor, dashing;
	//If the player can currently control movement (no if blocking, dashing, attack windup)
	private boolean movementControlled;
	protected double dashSpeed = 8, dashDistance = 24;
	private double dashTraveled, dashAngle;
	
	protected ArrayList<MovingSprite> hitboxes;

	/**
	 * Initializes a Character with default values
	 */
	public Avatar() {
		hitboxes = new ArrayList<MovingSprite>();
		x = 100;
		y = 100;
		w = 30;
		h = 40;
		angle = 90;
		timeActionStarted = System.currentTimeMillis();
		shielded = false;
		superArmor = false;
		dashing = false;
		status = new StatusEffect(StatusEffect.Effect.NONE, 0, 0);
		health = 0;
		spriteInd = 0;
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
		if(playerNum != attack.getPlayer() && attack.isActive()) {
			if(shielded)
				return AttackResult.BLOCKED;
			if(!superArmor) {
				status = attack.getEffect();
			}
			health += attack.getDamage();
			return AttackResult.SUCCESS;
		} 
		else if(playerNum == attack.getPlayer()) {
			return AttackResult.SAME_AVATAR;
		}
		else {
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
		if(!status.getEffect().equals(Effect.NONE) && status.isFinished()) {
			this.x += x;
			this.y += y;
		}
	}

	/**
	 * 
	 * Moves Avatar this distance along the direction it is facing
	 * 
	 * @param dist The distance to travel
	 */
	public void moveDistance(double dist) {
		moveBy(Math.cos(Math.toRadians(angle))*dist, Math.sin(Math.toRadians(angle))*dist);
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
	 * @return The player that owns this Avatar's number
	 */
	public int getPlayer() {
		return playerNum;
	}

	/**
	 * Starts the Character in a dash, enables superArmor
	 */
	public void dash() {
		movementControlled = false;
		dashing = true;
		dashTraveled = 0;
		superArmor = true;
		dashAngle = angle;
	}
	
	public void block() {
		
	}
	
	/**
	 * This should be called every round of the game loop
	 */
	public void act() {
		if(dashing)
			dashAct();
		else if(shielded) {
			return;
		}
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

	/**
	 * 
	 * Gets if the player can control movement right now, no if dashing, blocking, attack windup
	 * 
	 * @return
	 */
	public boolean movementControlled() {
		return movementControlled;
	}
	
	private void dashAct() {
		moveBy(Math.cos(Math.toRadians(dashAngle))*dashSpeed, Math.sin(Math.toRadians(dashAngle))*dashSpeed);
		dashTraveled += dashSpeed;
		if(dashDistance >= dashTraveled) {
			dashing = false;
			movementControlled = true;
			dashTraveled = 0;
			superArmor = false;
		}
	}
	
	public void draw(PApplet surface) {
		if(shielded) {
			//Add on block thing
		}
		if(!status.getEffect().equals(Effect.NONE)) {
			//Add on effect
		}
		
		int sx, sy, sw, sh;
		sx = (int)sprites[spriteInd].getX();
		sy = (int)sprites[spriteInd].getY();
		sw = (int)sprites[spriteInd].getWidth();
		sh = (int)sprites[spriteInd].getHeight();
		
		surface.image(GamePanel.resources.getImage(spriteSheetKey), (float)x, (float)y, (float)w, (float)h, sx, sy, sw, sh);
	}

}

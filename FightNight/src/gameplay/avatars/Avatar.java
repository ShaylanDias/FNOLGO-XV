package gameplay.avatars;

import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;

import clientside.gui.GamePanel;
import gameplay.Drawable;
import gameplay.attacks.Attack;
import gameplay.attacks.Attack.AttackResult;
import gameplay.attacks.StatusEffect;
import gameplay.attacks.StatusEffect.Effect;
import processing.core.PApplet;

/**
 * 
 * Represents an abstract player character in the game
 * 
 * @author shaylandias
 *
 */
public abstract class Avatar implements Drawable, Serializable {

	/*
	 * Movement, boolean keys, act method- gets called for each character when
	 * GameManager run is called
	 */

	public enum AttackType {
		P, DIRECTION, ATTACK
	};

	/**
	 * Contains the sprites for this Avatar, in subclasses define which spot in the
	 * array corresponds to what image. - Needs to create attacks - Needs to not be
	 * able to move while standing attacking or dashing or shielding
	 * (movementControlled)- also works with the StatusEffect - Make GamePanel run
	 * the movement
	 */
	protected String spriteSheetKey;
	/*
	 * 
	 */
	protected Rectangle[] sprites;
	protected int spriteInd;

	private boolean up, down, left, right;

	private int playerNum = 0;
	private double x, y;
	private double w, h;
	// Angle from right horizontal that Character is facing, 0-360 going left
	private double angle;
	private double health;
	private long timeActionStarted;
	private StatusEffect status;
	protected boolean shielded, superArmor, dashing;
	// If the player can currently control movement (no if blocking, dashing, attack
	// windup)
	private boolean movementControlled;
	protected double dashSpeed = 8, dashDistance = 24;
	private double dashTraveled, dashAngle;

	protected double moveSpeed = 10;
	
	protected Rectangle hitbox;

	/**
	 * Initializes a Character with default values
	 */
	public Avatar() {
		sprites = new Rectangle[] { new Rectangle(100, 100, 200, 200) };
		hitbox = sprites[0];
		x = 100;
		y = 100;
		w = 90;
		h = 100;
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
	 * @param x
	 *            X-coordinate
	 * @param y
	 *            Y-coordinate
	 * @param angle
	 *            The angle from vertical the Character is facing
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
	 * @param attack
	 *            The attack that hits
	 * @return The result of the attack
	 */
	public AttackResult takeHit(Attack attack) {
		if (playerNum != attack.getPlayer() && attack.isActive()) {
			if (shielded)
				return AttackResult.BLOCKED;
			if (!superArmor) {
				status = attack.getEffect();
			}
			health += attack.getDamage();
			return AttackResult.SUCCESS;
		} else if (playerNum == attack.getPlayer()) {
			return AttackResult.SAME_AVATAR;
		} else {
			return AttackResult.MISSED;
		}
	}

	/**
	 * 
	 * Moves the Avatar by the input x and y values
	 * 
	 * @param x
	 *            X to move
	 * @param y
	 *            Y to move
	 */
	public void moveBy(double x, double y) {
		if (status.getEffect().equals(Effect.NONE) || status.isFinished()) {
			this.x += x;
			this.y += y;
		}
	}

	/**
	 * 
	 * Moves Avatar this distance along the direction it is facing
	 * 
	 * @param dist
	 *            The distance to travel
	 */
	public void moveDistance(double dist) {
		moveBy(Math.cos(Math.toRadians(angle)) * dist, Math.sin(Math.toRadians(angle)) * dist);
	}

	/**
	 * 
	 * Moves Avatar to an x,y coordinate
	 * 
	 * @param x
	 *            X-Coordinate
	 * @param y
	 *            Y-Coordinate
	 */
	public void moveTo(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * 
	 * Turns by input angle;
	 * 
	 * @param angle
	 *            Angle to turn by
	 */
	public void turn(double angle) {
		this.angle += angle;
	}

	/**
	 * 
	 * Turns to this angle, vertical is 0
	 * 
	 * @param angle
	 *            Angle to turn to
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

		if (up)
			moveBy(0, -moveSpeed);
		if (right)
			moveBy(moveSpeed, 0);
		if (left)
			moveBy(-moveSpeed, 0);
		if (down)
			moveBy(0, moveSpeed);
		
		if (dashing)
			dashAct();
		else if (shielded) {
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
	 * Gets if the player can control movement right now, no if dashing, blocking,
	 * attack windup
	 * 
	 * @return
	 */
	public boolean movementControlled() {
		return movementControlled;
	}

	private void dashAct() {
		moveBy(Math.cos(Math.toRadians(dashAngle)) * dashSpeed, Math.sin(Math.toRadians(dashAngle)) * dashSpeed);
		dashTraveled += dashSpeed;
		if (dashDistance >= dashTraveled) {
			dashing = false;
			movementControlled = true;
			dashTraveled = 0;
			superArmor = false;
		}
	}

	public void draw(PApplet surface) {
		surface.pushMatrix();
		if (shielded) {
			// Add on block thing
		}
		if (!status.getEffect().equals(Effect.NONE)) {
			// Add on effect
		}

		int sx, sy, sw, sh;
		sx = (int) sprites[spriteInd].getX();
		sy = (int) sprites[spriteInd].getY();
		sw = (int) sprites[spriteInd].getWidth();
		sh = (int) sprites[spriteInd].getHeight();

		surface.image(GamePanel.resources.getImage(spriteSheetKey), (float) x, (float) y, sw, sh);

		surface.popMatrix();
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}
	
	public Rectangle getHitbox() {
		return hitbox;
	}

}

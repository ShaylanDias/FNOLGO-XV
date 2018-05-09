package gameplay.avatars;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import clientside.gui.GamePanel;
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
public abstract class Avatar implements Serializable {

	/*
	 * Movement, boolean keys, act method- gets called for each character when
	 * GameManager run is called
	 */

	/**
	 * 
	 * The Types of Attacks an Avatar can perform
	 * 
	 * @author shaylandias
	 *
	 */
	public enum AttackType {
		BASIC, RANGED, A1, A2, A3
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

	private String playerAddress = "";

	protected static final String blockImageKey = "Shield";

	protected Rectangle2D.Double hitbox; // The hitbox around this Avatar

	private double angle; // Angle from right horizontal that Avatar is facing, 0-360 going left from 0
	private double health;
	protected double moveSpeed = 10; // This Avatar's movement speed

	protected double basicCD, rangedCD,dashCD, a1CD, a2CD, a3CD; // Cooldowns on abilities to be set by subclasses
	protected long basicCDStart, rangedCDStart, dashCDStart, a1CDStart, a2CDStart, a3CDStart; // Time started

	private StatusEffect status; // Current StatusEffect applied to this Avatar

	protected boolean blocking, superArmor, dashing;

	private boolean movementControlled; // Can currently control movement (currently blocking or dashing)

	protected double dashSpeed = 20, dashDistance = 100; // Modifiable distance and speed of dash
	private double dashTraveled, dashAngle; // Distance traveled so far and angle to dash

	protected boolean currentlyAttacking; // Means cannot move, dash, or block while executing this attack
	protected long timeActionStarted; // Time is the time that the attack it is executing was started

	/**
	 * Initializes a Character with default values
	 */
	public Avatar() {
		sprites = new Rectangle[] { new Rectangle(100, 100, 200, 200) };
		hitbox = new Rectangle2D.Double(100, 100, 200, 200);
		angle = 90;
		timeActionStarted = System.currentTimeMillis();
		blocking = false;
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
		hitbox.x = (int) x;
		hitbox.y = (int) y;
		this.angle = angle;
	}

	// Bound to left click
	/**
	 * 
	 * Attack bound to left click
	 * 
	 * @param player
	 *            The player's IP address
	 * @param angle
	 *            Angle of the attack
	 * @return The Attack it performs
	 */
	public abstract Attack basicAttack(String player, double angle);

	// Bound to right click
	/**
	 * 
	 * Attack bound to right click
	 * 
	 * @return The Attack it performs
	 */
	public abstract Attack rangedAttack();

	// Bound to e
	/**
	 * 
	 * Attack bound to e
	 * 
	 * @return The Attack it performs
	 */
	public abstract Attack abilityOne();

	// Bound to r
	/**
	 * 
	 * Attack bound to r
	 * 
	 * @return The Attack it performs
	 */
	public abstract Attack abilityTwo();

	// Bound to f
	/**
	 * 
	 * Attack bound to f
	 * 
	 * @return The Attack it performs
	 */
	public abstract Attack abilityThree();

	/**
	 * 
	 * Hits a player with an Attack
	 * 
	 * @param attack
	 *            The attack that hits
	 * @return The result of the attack
	 */
	public AttackResult takeHit(Attack attack) {
		if (!playerAddress.equals(attack.getPlayer()) && attack.isActive()) {
			if (blocking)
				return AttackResult.BLOCKED;
			if (!superArmor) {
				status = attack.getEffect();
			}
			health += attack.getDamage();
			return AttackResult.SUCCESS;
		} else if (playerAddress.equals(attack.getPlayer())) {
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
			hitbox.x += x;
			hitbox.y += y;
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
		hitbox.x = (int) x;
		hitbox.y = (int) y;
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
	 * Gets the IP address of the Player that owns this Avatar
	 * 
	 * @return The player that owns this Avatar's IP address
	 */
	public String getPlayer() {
		return playerAddress;
	}

	public void setPlayer(String address) {
		playerAddress = address;
	}

	/**
	 * Starts the Character in a dash, enables superArmor
	 */
	public void dash(Double mouseAngle) {
		movementControlled = false;
		dashing = true;
		dashTraveled = 0;
		superArmor = true;
		dashAngle = mouseAngle;
	}

	/**
	 * Starts an Avatar's block
	 */
	public void block(boolean block) {
		blocking = block;
	}

	/**
	 * This should be called every round of the game loop
	 */
	public void act() {

		if(blocking)
			return;
		else if (dashing) {
			dashAct();
			return;
		}

		if (up)
			moveBy(0, -moveSpeed);
		if (right)
			moveBy(moveSpeed, 0);
		if (left)
			moveBy(-moveSpeed, 0);
		if (down)
			moveBy(0, moveSpeed);

	}

	/**
	 * 
	 * Gets the X-Coordinate of the Avatar
	 * 
	 * @return The X-Coordinate of the Avatar
	 */
	public double getX() {
		return hitbox.x;
	}

	/**
	 * 
	 * Gets the Y-Coordinate of the Avatar
	 * 
	 * @return The Y-Coordinate of the Avatar
	 */
	public double getY() {
		return hitbox.y;
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

	private void dashAct() { // Where the actual Dash action occurs
		moveBy(Math.cos(Math.toRadians(dashAngle)) * dashSpeed, -Math.sin(Math.toRadians(dashAngle)) * dashSpeed);
		dashTraveled += dashSpeed;
		if (dashTraveled >= dashDistance) {
			dashing = false;
			movementControlled = true;
			dashTraveled = 0;
			superArmor = false;
		}
	}

	/**
	 * 
	 * Draws this Avatar to a PApplet
	 * 
	 * @param surface
	 *            PApplet to draw to
	 */
	public void draw(PApplet surface) {
		surface.pushMatrix();
		surface.pushStyle();
		if (blocking) {
			//Draw block
		}
		if (!status.getEffect().equals(Effect.NONE)) {
			// Add on effect
		}

		int sx, sy, sw, sh;
		sx = (int) sprites[spriteInd].getX();
		sy = (int) sprites[spriteInd].getY();
		sw = (int) sprites[spriteInd].getWidth();
		sh = (int) sprites[spriteInd].getHeight();

		surface.imageMode(PApplet.CENTER);
		surface.image(GamePanel.resources.getImage(spriteSheetKey), (float) hitbox.x, (float) hitbox.y, sw, sh);

		if(blocking) {
			if (System.currentTimeMillis() / 250 % 5 == 0) {
				surface.tint(140);
			} else if (System.currentTimeMillis() / 250 % 5 == 1) {
				surface.tint(170);
			} else if (System.currentTimeMillis() / 250 % 5 == 2) {
				surface.tint(200);
			} else if (System.currentTimeMillis() / 250 % 5 == 3) {
				surface.tint(240);
			}
			surface.image(GamePanel.resources.getImage(blockImageKey), (float) hitbox.x, (float) hitbox.y, 1.5f * sw, 1.5f * sh);
		}


		surface.rectMode(PApplet.CENTER);
		surface.noFill();
		// surface.rect((float)hitbox.x, (float)hitbox.y, (float)sw, (float)sh);
		// surface.fill(Color.RED.getRGB());
		// surface.ellipseMode(PApplet.CENTER);
		// surface.ellipse((float)(hitbox.x), (float)(hitbox.y), 5f, 5f);

		surface.popMatrix();
		surface.popStyle();
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

	public double getWidth() {
		return hitbox.width;
	}

	public double getHeight() {
		return hitbox.height;
	}

	public Point2D.Double getCenter() {
		return new Point2D.Double(hitbox.x + hitbox.width / 2, hitbox.y + hitbox.height / 2);
	}

	public Rectangle2D.Double getHitbox() {
		return hitbox;
	}

}

package gameplay.avatars;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;

import clientside.gui.GamePanel;
import gameplay.attacks.Attack;
import gameplay.attacks.Attack.AttackResult;
import gameplay.attacks.Fireball;
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
		BASIC, RANGED, A1, A2, A3, NONE
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
	protected int spriteInd, numOfSpriteWalk;
	private ArrayList<String> spriteListWalk, spriteListAttack;

	protected boolean lastDir; //True if right, false if left

	private boolean up, down, left, right;

	private String playerAddress = "";

	protected static final String blockImageKey = "Shield";

	protected Rectangle2D.Double hitbox; // The hitbox around this Avatar

	private double angle; // Angle from right horizontal that Avatar is facing, 0-360 going left from 0
	protected double health, fullHealth;
	protected double moveSpeed = 10; // This Avatar's movement speed

	protected double basicCD, rangedCD, dashCD, a1CD, a2CD, a3CD; // Cooldowns on abilities to be set by subclasses
	protected long basicCDStart, rangedCDStart, dashCDStart, a1CDStart, a2CDStart, a3CDStart; // Time started

	private StatusEffect status; // Current StatusEffect applied to this Avatar

	protected boolean blocking, superArmor, dashing;
	protected double shieldHealth, fullShieldHealth;

	protected boolean movementControlled; // Can currently control movement (currently blocking or dashing)

	protected double dashSpeed = 20, dashDistance = 100; // Modifiable distance and speed of dash
	private double dashTraveled, dashAngle; // Distance traveled so far and angle to dash

	protected boolean currentlyAttacking; // Means cannot move, dash, or block while executing this attack
	protected long timeActionStarted; // Time is the time that the attack it is executing was started

	protected long deathTime = 0;

	/**
	 * Initializes a Character with default values
	 */
	public Avatar() {
		sprites = new Rectangle[] { new Rectangle(100, 100, 200, 200) };
		hitbox = new Rectangle2D.Double(0, 0, 200, 200);
		angle = 90;
		timeActionStarted = System.currentTimeMillis();
		blocking = false;
		superArmor = false;
		dashing = false;
		status = new StatusEffect(StatusEffect.Effect.NONE, 0, 0);
		shieldHealth = 125;
		fullShieldHealth = shieldHealth;
		spriteInd = 0;
		health = 200;
		fullHealth = health;
		deathTime = 0;
		spriteListWalk = new ArrayList<String>();
		spriteListAttack = new ArrayList<String>();
		numOfSpriteWalk = 0;
		movementControlled = true;
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
	public abstract Attack rangedAttack(String player, double angle);

	// Bound to e
	/**
	 * 
	 * Attack bound to e
	 * 
	 * @return The Attack it performs
	 */
	public abstract Attack abilityOne(String player, double angle);

	// Bound to r
	/**
	 * 
	 * Attack bound to r
	 * 
	 * @return The Attack it performs
	 */
	public abstract Attack abilityTwo(String player, double angle);

	// Bound to f
	/**
	 * 
	 * Attack bound to f
	 * 
	 * @return The Attack it performs
	 */
	public abstract Attack abilityThree(String player, double angle);

	public Attack attack(AttackType a, String player, double angle) {
		if(deathTime == 0 && !currentlyAttacking && movementControlled) {
			if(a.equals(AttackType.A1) ) {
				if(System.currentTimeMillis() > a1CDStart + a1CD * 1000)
					return abilityOne(player, angle);
				else
					return null;
			}
			else if(a.equals(AttackType.A2))
				if(System.currentTimeMillis() > a2CDStart + a2CD * 1000)
					return abilityTwo(player, angle);
				else
					return null;
			else if(a.equals(AttackType.A3))
				if(System.currentTimeMillis() > a3CDStart + a3CD * 1000)
					return abilityThree(player, angle);
				else
					return null;
			else if(a.equals(AttackType.RANGED)) {
				if(System.currentTimeMillis() > rangedCDStart + rangedCD * 1000) {
					System.out.println("a");
					return rangedAttack(player, angle);
				}
				else
					return null;		
			}
			else
				return basicAttack(player, angle);
		}
		else
			return null;
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
		if (!playerAddress.equals(attack.getPlayer()) && attack.isActive()) {
			if(blocking) {
				if(!attack.isShieldBreaker()) {
					if (blocking) {
						shieldHealth -= attack.getDamage()/2;
						if(shieldHealth <= 0) {
							health += shieldHealth;
							shieldHealth = 0;
							blocking = false;
						}
						return AttackResult.BLOCKED;
					}
				} else {
					shieldHealth -= 50;
					if(shieldHealth <= 0) {
						shieldHealth = 0;
						blocking = false;
					}
				}
			}
			if (!superArmor) {
				status = attack.getEffect();
			}
			health -= attack.getDamage();
			if(health <= 0 && deathTime == 0) {
				health = 0;
				deathTime = System.currentTimeMillis();
			}
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
	 * @param angle 
	 * 			  The angle to travel at
	 */
	public void moveDistance(double dist, double angle) {
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

		if(left) {
			if(up) {
				dashAngle = 135;
			} else if(down) {
				dashAngle = 225;
			} else {
				dashAngle = 180;
			}
		} else if(right){
			if(up) {
				dashAngle = 45;
			} else if(down) {
				dashAngle = 315;
			} else {
				dashAngle = 0;
			}
		} else if(down) {
			dashAngle = 270;
		} else if(up){
			dashAngle = 90;
		}


		movementControlled = false;
		dashing = true;
		dashTraveled = 0;
		superArmor = true;
		//		dashAngle = mouseAngle;
	}

	/**
	 * Starts an Avatar's block
	 */
	public void block(boolean block) {
		if(shieldHealth > 0) {
			if(block)
				movementControlled = false;
			else
				movementControlled = true;
			blocking = block;
		}
		else
			movementControlled = true;
	}

	/**
	 * This should be called every round of the game loop
	 */
	public void act() {

		double moveSpeed = this.moveSpeed;
		
		if(status.getEffect().equals(Effect.SLOWED)) {
			moveSpeed -= status.getValue();
		}
		
		if(health > 0) {
			if (blocking) {
				shieldHealth -= 1;
				up = false;
				down = false;
				left = false;
				right = false;
				if(shieldHealth <= 0) {
					shieldHealth = -50;
					blocking = false;
				}
				return;
			} else {
				shieldHealth += 1.5;
				if(shieldHealth > fullShieldHealth)
					shieldHealth = fullShieldHealth;
			}
			if (dashing) {
				dashAct();
				return;
			}
			if (up) {
				moveBy(0, -moveSpeed);
				walk(numOfSpriteWalk, 200);
			}
			if (right) {
				moveBy(moveSpeed, 0);
				walk(numOfSpriteWalk, 200);
			}
			if (left) {
				moveBy(-moveSpeed, 0);
				walk(numOfSpriteWalk, 200);
			}
			if (down) {
				moveBy(0, moveSpeed);
				walk(numOfSpriteWalk, 200);
			}
		}

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

	protected void drawHealthBar(PApplet surface) {
		double shield = shieldHealth;
		if(shield < 0)
			shield = 0;
		
		surface.rectMode(PApplet.CENTER);
		surface.fill(Color.BLACK.getRGB());
		surface.rect((float)(hitbox.x), (float)(hitbox.y - hitbox.height * 3/4), (float)hitbox.width * 0.7f, (float)hitbox.height/6);
		surface.rect((float)(hitbox.x), (float)(hitbox.y - hitbox.height * 4/5), (float)hitbox.width * 0.7f, (float)hitbox.height/6);
		surface.fill(Color.CYAN.getRGB());
		surface.rect((float)(hitbox.x), (float)(hitbox.y - hitbox.height * 4/5), (float)hitbox.width * 0.7f * (float)(shield/fullShieldHealth), (float)hitbox.height/6);
		surface.fill(Color.GREEN.getRGB());
		surface.rect((float)(hitbox.x), (float)(hitbox.y - hitbox.height * 3/4), (float)hitbox.width * 0.7f * (float)(health/fullHealth), (float)hitbox.height/6);
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

		drawHealthBar(surface);
		
		if(deathTime != 0) {
			drawDeath(surface);
			surface.popMatrix();
			surface.popStyle();
			return;
		}

		if (blocking) {
			// Draw block
		}
		if (!status.getEffect().equals(Effect.NONE)) {
			// Add on effect
		}

		int sw, sh;
		//		sx = (int) sprites[spriteInd].getX();
		//		sy = (int) sprites[spriteInd].getY();
		sw = (int) sprites[spriteInd].getWidth();
		sh = (int) sprites[spriteInd].getHeight();

		surface.imageMode(PApplet.CENTER);

		surface.pushMatrix();
		if (left || !lastDir) {
			surface.scale(-1, 1);
			surface.image(GamePanel.resources.getImage(spriteSheetKey), (float) -hitbox.x, (float) hitbox.y, -sw, sh);

		} else {
			surface.image(GamePanel.resources.getImage(spriteSheetKey), (float) hitbox.x, (float) hitbox.y, sw, sh);
		}
		surface.popMatrix();
		if (blocking) {
			if (System.currentTimeMillis() / 250 % 5 == 0) {
				surface.tint(140);
			} else if (System.currentTimeMillis() / 250 % 5 == 1) {
				surface.tint(170);
			} else if (System.currentTimeMillis() / 250 % 5 == 2) {
				surface.tint(200);
			} else if (System.currentTimeMillis() / 250 % 5 == 3) {
				surface.tint(240);
			}
			surface.image(GamePanel.resources.getImage(blockImageKey), (float) hitbox.x, (float) hitbox.y, 1.5f * sw,
					1.5f * sh);
		}

		// surface.rect((float)hitbox.x, (float)hitbox.y, (float)sw, (float)sh);
		// surface.fill(Color.RED.getRGB());
		// surface.ellipseMode(PApplet.CENTER);
		// surface.ellipse((float)(hitbox.x), (float)(hitbox.y), 5f, 5f);

		surface.popMatrix();
		surface.popStyle();
	}

	public void walk(int numOfSpriteWalk, int divideSpeed) {
		if (!dashing && !blocking) {
			for (int i = 0; i < numOfSpriteWalk; i++) {
				if (System.currentTimeMillis() / divideSpeed % numOfSpriteWalk == i) {
					spriteSheetKey = getSpriteListWalk().get(i);
				}
			}
		}
	}

	protected void drawDeath(PApplet surface) {
		surface.image(GamePanel.resources.getImage(spriteSheetKey), (float)hitbox.x, (float)hitbox.y, (float)hitbox.width, (float)hitbox.height);
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
		lastDir = false;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
		lastDir = true;
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

	public ArrayList<String> getSpriteListWalk() {
		return spriteListWalk;
	}

	public void setSpriteListWalk(ArrayList<String> spriteListWalk) {
		this.spriteListWalk = spriteListWalk;
	}

	public ArrayList<String> getSpriteListAttack() {
		return spriteListAttack;
	}

	public StatusEffect getStatus() {
		return status;
	}

	public boolean isMoveControlled() {
		return movementControlled;
	}

	public void setSpriteListAttack(ArrayList<String> spriteListAttack) {
		this.spriteListAttack = spriteListAttack;
	}

}

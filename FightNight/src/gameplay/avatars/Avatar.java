package gameplay.avatars;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;

import clientside.gui.GamePanel;
import gameplay.attacks.Attack;
import gameplay.attacks.Attack.AttackResult;
import gameplay.attacks.StatusEffect;
import gameplay.attacks.StatusEffect.Effect;
import gameplay.maps.Map;
import processing.core.PApplet;

/**
 * 
 * Represents an abstract player character in the game
 * 
 * @author shaylandias
 *
 */
public abstract class Avatar implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4879807335455459574L;

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
	/**
	 * The sprites of for this Avatar
	 */
	protected Rectangle[] sprites;
	protected int spriteInd, numOfSpriteWalk, numOfSpriteDeath, spriteSpeedWalk, spriteSpeedDeath;
	private ArrayList<String> spriteListWalk, spriteListAttack, spriteListDeath;;

	/**
	 * Last direction this AVatar was facing, true for right
	 */
	protected boolean lastDir;

	private boolean up, down, left, right;

	private String playerAddress = "";

	/**
	 * The key for the block image
	 */
	protected static final String blockImageKey = "Shield";

	/**
	 * The hitbox for this Avatar
	 */
	protected Rectangle2D.Double hitbox;

	/**
	 * The health of this Avatar
	 */
	protected double health, fullHealth;
	/**
	 * The moveSpeed of this Avatar
	 */
	protected double moveSpeed = 10;

	/**
	 * The cooldowns of this Avatar's attacks
	 */
	protected double basicCD, rangedCD, dashCD, a1CD, a2CD, a3CD;
	/**
	 * Cooldown start times
	 */
	protected long basicCDStart, rangedCDStart, dashCDStart, a1CDStart, a2CDStart, a3CDStart;

	private StatusEffect status; // Current StatusEffect applied to this Avatar

	/**
	 * If this Avatar is blocking
	 */
	protected boolean blocking, superArmor, dashing;
	/**
	 * The health of this Avatar's shield
	 */
	protected double shieldHealth, fullShieldHealth;

	/**
	 * Whether or not this Avatar can be controlled right now
	 */
	protected boolean movementControlled; // Can currently control movement (currently blocking or dashing)

	/**
	 * Dash speed and distance
	 */
	protected double dashSpeed = 20, dashDistance = 100; // Modifiable distance and speed of dash
	private double dashTraveled, dashAngle; // Distance traveled so far and angle to dash

	/**
	 * If this Avatar is currently attacking
	 */
	protected boolean currentlyAttacking; // Means cannot move, dash, or block while executing this attack
	/**
	 * The time this Avatar's current animation was started
	 */
	protected long timeActionStarted; // Time is the time that the attack it is executing was started

	private boolean dead, eliminated;
	private int lives;
	/**
	 * The time this Avatar died
	 */
	protected long deathTime = 0;

	/**
	 * Initializes a Character with default values
	 */
	public Avatar() {
		lives = 3;
		sprites = new Rectangle[] { new Rectangle(100, 100, 200, 200) };
		hitbox = new Rectangle2D.Double(-1000, -1000, 200, 200);
		timeActionStarted = 0;
		blocking = false;
		superArmor = false;
		numOfSpriteWalk = 5;
		dashing = false;
		status = new StatusEffect(StatusEffect.Effect.NONE, 0, 0);
		shieldHealth = 125;
		fullShieldHealth = shieldHealth;
		spriteInd = 0;
		health = 200;
		fullHealth = health;
		deathTime = -1;
		spriteListWalk = new ArrayList<String>();
		spriteListAttack = new ArrayList<String>();
		spriteListDeath = new ArrayList<String>();
		numOfSpriteWalk = 0;
		numOfSpriteDeath = 1;
		spriteSpeedWalk = 150;
		spriteSpeedDeath = 500;
		movementControlled = true;
		dead = true;
		dashCD = 1;
		eliminated = false;
		dead = true;
		deathTime = 0;
	}

	/**
	 * 
	 * Initializes a Character at the given x and y
	 * 
	 * @param x
	 *            X-coordinate
	 * @param y
	 *            Y-coordinate
	 */
	public Avatar(double x, double y) {
		super();
		hitbox.x = (int) x;
		hitbox.y = (int) y;
	}

	/**
	 * This should be called every round of the game loop
	 */
	public void act(Map map, long time) {

		double moveSpeed = this.moveSpeed;

		if (status.getEffect().equals(Effect.SLOWED)) {
			if (!status.started())
				status.startEffect(time);
			moveSpeed -= status.getValue();
			if (status.isFinished(time)) {
				status = new StatusEffect(Effect.NONE, 0, 0);
			}
		} else if (status.getEffect().equals(Effect.STUNNED)) {
			if (!status.started())
				status.startEffect(time);
			else {
				if (status.isFinished(time)) {
					status = new StatusEffect(Effect.NONE, 0, 0);
				}
				return;
			}
		}
		
		if (!dead && !currentlyAttacking) {
			if (blocking) {
				shieldHealth -= 1;
				up = false;
				down = false;
				left = false;
				right = false;
				if (shieldHealth <= 0) {
					shieldHealth = -50;
					blocking = false;
				}
				return;
			} else {
				shieldHealth += 1.5;
				if (shieldHealth > fullShieldHealth)
					shieldHealth = fullShieldHealth;
			}
			if (dashing) {
				dashAct(map, dashSpeed, time);
				return;
			}
			if (up) {
				moveBy(0, -moveSpeed, map, time);
				walk(numOfSpriteWalk, spriteSpeedWalk, time);
			}
			if (right) {
				moveBy(moveSpeed, 0, map, time);
				walk(numOfSpriteWalk, spriteSpeedWalk, time);
				if(!currentlyAttacking)
					lastDir = false;
			}
			if (left) {
				moveBy(-moveSpeed, 0, map, time);
				walk(numOfSpriteWalk, spriteSpeedWalk, time);
				if(!currentlyAttacking)
					lastDir = true;
			}
			if (down) {
				moveBy(0, moveSpeed, map, time);
				walk(numOfSpriteWalk, spriteSpeedWalk, time);
			}
		} else {
			if (dead && time > deathTime + 6 * 1000) {
				spawn(map);
			}
		}

	}

	/**
	 * 
	 * Draws this Avatar to a PApplet
	 * 
	 * @param surface
	 *            PApplet to draw to
	 * @param time The server time this is drawn
	 */
	public void draw(PApplet surface, long time) {
		surface.pushMatrix();
		surface.pushStyle();
		
		drawHealthBar(surface);
		
		if (dead) {
			drawDeath(numOfSpriteDeath, spriteSpeedDeath, time);
			surface.popMatrix();
			surface.popStyle();
			return;
		}

		surface.imageMode(PApplet.CENTER);

		if (status.getEffect().equals(Effect.STUNNED)) {
			surface.image(GamePanel.resources.getImage("Stun"), (float) hitbox.x, (float) hitbox.y);
		}

		int sw, sh;
		// sx = (int) sprites[spriteInd].getX();
		// sy = (int) sprites[spriteInd].getY();
		sw = (int) sprites[spriteInd].getWidth();
		sh = (int) sprites[spriteInd].getHeight();

		surface.pushMatrix();
		if (!lastDir) {
			surface.scale(-1, 1);
			surface.image(GamePanel.resources.getImage(spriteSheetKey), (float) -hitbox.x, (float) hitbox.y, -sw, sh);

		} else {
			surface.image(GamePanel.resources.getImage(spriteSheetKey), (float) hitbox.x, (float) hitbox.y, sw, sh);
		}
		surface.popMatrix();
		if (blocking) {
			if (time / 250 % 5 == 0) {
				surface.tint(140);
			} else if (time / 250 % 5 == 1) {
				surface.tint(170);
			} else if (time / 250 % 5 == 2) {
				surface.tint(200);
			} else if (time / 250 % 5 == 3) {
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

	/**
	 * 
	 * Spawns the Avatar at a random spot on the map
	 * 
	 * @param map  The Map to draw at
	 */
	public void spawn(Map map) {
		if (!eliminated && !(this instanceof Spectator)) {
			double x = 1500 - Math.random() * 3000;
			double y = 1500 - Math.random() * 3000;

			if (map.hitTree(x, y, hitbox.width, hitbox.height) || !map.inBounds(x, y, hitbox.width, hitbox.height)) {
				spawn(map);
			} else {
				hitbox.x = x;
				hitbox.y = y;
			}

			health = fullHealth;
			shieldHealth = fullShieldHealth;
			rangedCDStart = 0;
			basicCDStart = 0;
			a1CDStart = 0;
			a2CDStart = 0;
			a3CDStart = 0;
			deathTime = 0;
			stop();
			dead = false;
		}
	}

	/**
	 * 
	 * Generic attack from this Avatar
	 * 
	 * @param a The type of Attack
	 * @param player The player's IP address
	 * @param angle The angle of this attack
	 * @param time The server time this Attack was started
	 * @return The Attacks that result, null if cannot be performed
	 */
	public Attack[] attack(AttackType a, String player, double angle, long time) {
		if (deathTime == 0 && !currentlyAttacking && movementControlled && !status.getEffect().equals(Effect.STUNNED)) {
			if (a.equals(AttackType.A1)) {
				if (time > a1CDStart + a1CD * 1000) {
					return abilityOne(player, angle, time);
				} else
					return null;
			} else if (a.equals(AttackType.A2)) {
				if (time > a2CDStart + a2CD * 1000) {
					return abilityTwo(player, angle, time);
				} else
					return null;
			} else if (a.equals(AttackType.A3)) {
				if (time > a3CDStart + a3CD * 1000) {
					return abilityThree(player, angle, time);
				} else
					return null;
			} else if (a.equals(AttackType.RANGED)) {
				if (time > rangedCDStart + rangedCD * 1000) {
					return rangedAttack(player, angle, time);
				} else
					return null;
			} else
				return basicAttack(player, angle, time);
		} else
			return null;
	}

	/**
	 * 
	 * Hits a player with an Attack
	 * 
	 * @param attack
	 *            The attack that hits
	 * @param time The server time this occurs
	 * @return The result of the attack
	 */
	public AttackResult takeHit(Attack attack, long time) {
		if (!playerAddress.equals(attack.getPlayer()) && attack.isActive()) {
			if (blocking) {
				if (!attack.isShieldBreaker()) {
					if (blocking) {
						shieldHealth -= attack.getDamage() / 2;
						if (shieldHealth <= 0) {
							health += shieldHealth;
							shieldHealth = 0;
							blocking = false;
						}
						return AttackResult.BLOCKED;
					}
				} else {
					shieldHealth -= 50;
					if (shieldHealth <= 0) {
						shieldHealth = 0;
						blocking = false;
					}
				}
			}
			if (!superArmor) {
				if(attack.getEffect().getEffect().equals(Effect.STUNNED))
					status = attack.getEffect();
				else if(!status.getEffect().equals(Effect.STUNNED))
					status = attack.getEffect();
			}
			health -= attack.getDamage();
			if (health <= 0 && !dead) {
				die(time);
			}
			return AttackResult.SUCCESS;
		} else if (playerAddress.equals(attack.getPlayer())) {
			return AttackResult.SAME_AVATAR;
		} else {
			return AttackResult.MISSED;
		}
	}

	private void die(long time) {
		if (time * 1000 > this.deathTime) {
			//			health = 0;
			dead = true;
			lives--;

			if (lives <= 0)
				eliminated = true;
			deathTime = time;
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
	 * @return True if successfully could move
	 */
	public boolean moveBy(double x, double y, Map map, long time) {
		if (!status.getEffect().equals(Effect.STUNNED) || status.isFinished(time)) {

			if (!map.hitTree(hitbox.x + x, hitbox.y + y, hitbox.width, hitbox.height)
					&& map.inBounds(hitbox.x + x, hitbox.y + y, hitbox.width, hitbox.height)) {
				hitbox.x += x;
				hitbox.y += y;
				return true;
			} else
				return false;
		} else
			return false;
	}

	/**
	 * 
	 * Moves Avatar this distance along the direction it is facing
	 * 
	 * @param dist
	 *            The distance to travel
	 * @param angle
	 *            The angle to travel at
	 * @return True if successful
	 */
	public boolean moveDistance(double dist, double angle, Map map, long time) {
		return moveBy(Math.cos(Math.toRadians(angle)) * dist, Math.sin(Math.toRadians(angle)) * dist, map, time);
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

	private void stop() {
		left = false;
		right = false;
		up = false;
		down = false;
	}

	/**
	 * Starts the Character in a dash, enables superArmor
	 */
	public void dash(long time) {
		
		if(time> dashCDStart + dashCD * 1000) {

			if (left) {
				if (up) {
					dashAngle = 135;
				} else if (down) {
					dashAngle = 225;
				} else {
					dashAngle = 180;
				}
			} else if (right) {
				if (up) {
					dashAngle = 45;
				} else if (down) {
					dashAngle = 315;
				} else {
					dashAngle = 0;
				}
			} else if (down) {
				dashAngle = 270;
			} else if (up) {
				dashAngle = 90;
			}

			movementControlled = false;
			dashing = true;
			dashTraveled = 0;
			superArmor = true;

			dashCDStart = time;

		}
	}

	private void dashAct(Map map, double dist, long time) { // Where the actual Dash action occurs
		if (moveBy(Math.cos(Math.toRadians(dashAngle)) * dist, -Math.sin(Math.toRadians(dashAngle)) * dist, map, time))
			dashTraveled += dist;
		else if(dist < 5)
			dashTraveled = dashDistance + 1;
		else {
			dashAct(map, dist-10, time);
			dashTraveled = dashDistance + 1;
		}

		
		if (dashTraveled >= dashDistance) {
			dashing = false;
			movementControlled = true;
			dashTraveled = 0;
			superArmor = false;
		}
	}

	/**
	 * Starts an Avatar's block
	 */
	public void block(boolean block) {
		if (shieldHealth > 0 && !status.getEffect().equals(Effect.STUNNED)) {
			if (block)
				movementControlled = false;
			else
				movementControlled = true;
			blocking = block;
		} else
			movementControlled = true;
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

	/**
	 * 
	 * Sets the player controlling this Attack
	 * 
	 * @param address The IP address of the player
	 */
	public void setPlayer(String address) {
		playerAddress = address;
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

	/**
	 * 
	 * Draws the health bar
	 * 
	 * @param surface Surface to draw to
	 */
	protected void drawHealthBar(PApplet surface) {
		double shield = shieldHealth;
		double health = this.health;
		if (health < 0)
			health = 0;
		if (shield < 0)
			shield = 0;

		surface.rectMode(PApplet.CENTER);
		surface.rect((float) (hitbox.x), (float) (hitbox.y - hitbox.height * 3 / 4 - 10), (float) hitbox.width,
				(float) 8);
		surface.fill(Color.CYAN.getRGB());
		surface.rect((float) (hitbox.x), (float) (hitbox.y - hitbox.height * 3 / 4 - 10),
				(float) hitbox.width * (float) (shield / fullShieldHealth), (float) (8));
		surface.fill(Color.BLACK.getRGB());
		surface.rect((float) (hitbox.x), (float) (hitbox.y - hitbox.height * 3 / 4 - 2), (float) hitbox.width,
				(float) 10);
		surface.fill(Color.GREEN.getRGB());
		surface.rect((float) (hitbox.x), (float) (hitbox.y - hitbox.height * 3 / 4 - 2),
				(float) hitbox.width * (float) (health / fullHealth), (float) 10);
		surface.fill(Color.BLACK.getRGB());
		surface.pushStyle();
		surface.textSize(18);
		surface.text(lives+"", (float)(hitbox.x-hitbox.width/2-10), (float)(hitbox.y - hitbox.height * 3 / 4));
		surface.popStyle();
	}

	/**
	 * 
	 * Sets the sprite key for this Avatar's death animation
	 * 
	 * @param numOfSpriteDeath Number of death sprites
	 * @param spriteSpeedDeath Speed to cycle sprites
	 * @param time The server time of this draw
	 */
	protected void drawDeath(int numOfSpriteDeath, int spriteSpeedDeath, long time) {

		if(getSpriteListDeath().size() > 0) {
			double deathAnimationTime = 1.5;
			double step = deathAnimationTime/numOfSpriteDeath;
			spriteSheetKey = getSpriteListDeath().get(getSpriteListDeath().size()-1);
			for(int i = 1; i < numOfSpriteDeath+1; i++) {
				if ((time - deathTime) <= i*step * 1000) {
					spriteSheetKey = getSpriteListDeath().get(i-1);
					break;
				}
			}
		}
	}

	/**
	 * 
	 * Sets the sprite key for this Avatar's walk animation
	 * 
	 * @param numOfSpriteDeath Number of walk sprites
	 * @param spriteSpeedDeath Speed to cycle sprites
	 * @param time The server time of this draw
	 */
	public void walk(int numOfSpriteWalk, int spriteSpeedWalk, long time) {
		if (!dashing && !blocking) {
			for (int i = 0; i < numOfSpriteWalk; i++) {
				if (time / spriteSpeedWalk % numOfSpriteWalk == i) {
					spriteSheetKey = getSpriteListWalk().get(i);
				}
			}
		}
	}

	// Getters and Setters
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
		if (left && !currentlyAttacking)
			lastDir = true;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
		if (right && !currentlyAttacking)
			lastDir = false;
	}

	public double getWidth() {
		return hitbox.width;
	}

	public double getHeight() {
		return hitbox.height;
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

	public double getDashCooldown() {
		return dashCD;
	}
	
	public long getBasicCooldownLeft(long time) {
		return time - basicCDStart;
	}

	public long getRangedCooldownLeft(long time) {
		return time - rangedCDStart;
	}

	public long getA1CooldownLeft(long time) {
		return time - a1CDStart;
	}

	public long getA2CooldownLeft(long time) {
		return time - a2CDStart;
	}

	public long getA3CooldownLeft(long time) {
		return time - a3CDStart;
	}
	
	public long getDashCooldownLeft(long time) {
		return time - dashCDStart;
	}

	public double getA1Cooldown() {
		return a1CD;
	}

	public double getBasicCooldown() {
		return basicCD;
	}

	public double getA2Cooldown() {
		return a2CD;
	}

	public double getA3Cooldown() {
		return a3CD;
	}

	public double getRangedCooldown() {
		return rangedCD;
	}

	/**
	 * 
	 * Attack bound to left click
	 * 
	 * @param player The player's IP address
	 * @param angle The angle for this attack
	 * @param time The server time of this attack
	 * @return The Attack it performs
	 */
	public abstract Attack[] basicAttack(String player, double angle, long time);

	/**
	 * 
	 * Attack bound to right click
	 * 
	 * @param player The player's IP address
	 * @param angle The angle for this attack
	 * @param time The server time of this attack
	 * @return The Attack it performs
	 */
	public abstract Attack[] rangedAttack(String player, double angle, long time);

	/**
	 * 
	 * Attack bound to e
	 * 
	 * @param player The player's IP address
	 * @param angle The angle for this attack
	 * @param time The server time of this attack
	 * @return The Attack it performs
	 */
	public abstract Attack[] abilityOne(String player, double angle, long time);

	/**
	 * 
	 * Attack bound to r
	 * 
	 * @param player The player's IP address
	 * @param angle The angle for this attack
	 * @param time The server time of this attack 
	 * @return The Attack it performs
	 */
	public abstract Attack[] abilityTwo(String player, double angle, long time);

	/**
	 * 
	 * Attack bound to f
	 * 
	 * @param player The player's IP address
	 * @param angle The angle for this attack
	 * @param time The server time of this attack
	 * @return The Attack it performs
	 */
	public abstract Attack[] abilityThree(String player, double angle, long time);

	/**
	 * 
	 * Is the Avatar dead
	 * 
	 * @return True if dead
	 */
	public boolean isDead() {
		return dead;
	}

	/**
	 * 
	 * Set if the Avatar is dead
	 * 
	 * @param dead True for dead
	 */
	public void setDead(boolean dead) {
		this.dead = dead;
	}

	/**
	 * 
	 * Sets the Avatar's number of lives
	 * 
	 * @param x The number of lives
	 */
	public void setLives(int x) {
		lives = x;
	}

	public boolean isEliminated() {
		return eliminated;
	}

	/**
	 * 
	 * Gets the Avatar's lives
	 * 
	 * @return Number of lives left
	 */
	public int getLives() {
		return lives;
	}

	/**
	 * 
	 * Gets the list of keys for the death animation
	 * 
	 * @return The list of keys for the death animation
	 */
	public ArrayList<String> getSpriteListDeath() {
		return spriteListDeath;
	}

	/**
	 * 
	 * Sets the list of keys for the death animation
	 * 
	 * @param spriteListDeath The death animation keys
	 */
	public void setSpriteListDeath(ArrayList<String> spriteListDeath) {
		this.spriteListDeath = spriteListDeath;
	}

}
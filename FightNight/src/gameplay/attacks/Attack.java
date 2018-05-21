package gameplay.attacks;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import clientside.gui.GamePanel;
import gameplay.avatars.Avatar;
import processing.core.PApplet;

/**
 * 
 * A damaging or status-causing effect that an Avatar can create
 * 
 * @author shaylandias
 *
 */
public class Attack extends MovingSprite {

	/**
	 * 
	 */
	private static final long serialVersionUID = -452967349757366044L;

	/**
	 * 
	 * The possible results of an Attack when it hits an Avatar
	 * 
	 * @author shaylandias
	 *
	 */
	public enum AttackResult {
		SUCCESS, BLOCKED, MISSED, SAME_AVATAR
	}

	private String playerAddress;
	private StatusEffect effect;
	private boolean shieldBreaker;
	private double damage;
	private long startTime;
	/**
	 * The time that this Attack takes effect for
	 */
	protected double duration = 1;
	/**
	 * The direction this attack faces/rotates to
	 */
	protected double dir;
	private boolean active;

	/**
	 * 
	 * Creates an Attack with the specified properties
	 * 
	 * @param imageKey The key leading to the image in Resources
	 * @param x The starting x-coordinate
	 * @param y The starting y-coordinate
	 * @param w The width
	 * @param h The height
	 * @param playerAddress The IP address of the player
	 * @param damage Damage caused by this attack
	 * @param shieldBreaker True if this attack breaks through shields
	 * @param effect The StatusEffect that this Attack inflicts
	 * @param dir The direction of the Attack
	 * @param time The time of instantiation on the server
	 */
	public Attack(String imageKey, int x, int y, int w, int h, String playerAddress, double damage,
			boolean shieldBreaker, StatusEffect effect, double dir, long time) {
		super(imageKey, x, y, w, h);
		this.damage = damage;
		this.playerAddress = playerAddress;
		this.dir = dir;
		active = true;
		this.effect = effect;
		this.shieldBreaker = shieldBreaker;
		startTime = time;
	}

	/**
	 * 
	 * Gets the number of the Player who used this Attack
	 * 
	 * @return Player number
	 */
	public String getPlayer() {
		return playerAddress;
	}

	/**
	 * 
	 * Tells if this projectile is active
	 * 
	 * @return True if active, false if inactive
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * 
	 * Gets Damage of this attack
	 * 
	 * @return The damage, 0 if it is inactive
	 */
	public double getDamage() {
		if (active)
			return damage;
		else
			return 0;
	}

	/**
	 * 
	 * Checks if the Attack's end condition has been reached and sets it to inactive
	 * if it has
	 * 
	 * @param time The current server time
	 * @return True if ended
	 */
	protected boolean checkEnd(long time) {
		if (!active)
			return true;
		if (time > startTime + duration * 1000) {
			end();
			return true;
		} else
			return false;
	}

	/**
	 * 
	 * Gets the status effect caused by this Attack
	 * 
	 * @return The StatusEffect inflicted by this Attack
	 */
	public StatusEffect getEffect() {
		return effect;
	}

	/**
	 * 
	 * What this Attack should do each time the game loop moves forward
	 * 
	 * 
	 * @param avatars The Avatars that this Attack could possibly hit
	 * @param time The current server time
	 * @return Returns true if was successful, false if the projectile is now
	 *         inactive
	 */
	public boolean act(ArrayList<Avatar> avatars, long time) {
		if (!active || checkEnd(time)) {
			return false;
		}

		for (Avatar a : avatars) {
			if (a.getHitbox().intersects(getHitbox())) {
				AttackResult res = a.takeHit(this, time);
				if (res.equals(AttackResult.BLOCKED) || res.equals(AttackResult.SUCCESS)) {
					end();
				}
			}
		}

		return true;
	}

	/**
	 * 
	 * Gets the translated hitbox so it matches with the image
	 * 
	 * @return The Avatar's hitbox as a Rectangle
	 */
	protected Rectangle getHitbox() {
		return new Rectangle((int) (x), (int) (y - super.height/2), (int) super.width, (int) super.height);
	}

	/**
	 * Finds if this Attack intersects a Rectangle
	 */
	public boolean intersects(Rectangle2D other) {
		return other.intersects(getHitbox());
	}

	/**
	 * Draws this Attack
	 */
	@Override
	public void draw(PApplet surface, long time) {
		surface.pushMatrix();
		surface.imageMode(PApplet.CENTER);
		 surface.rectMode(PApplet.CENTER);
		// surface.noFill();
//		surface.rect((float)(getHitbox().x), (float)(getHitbox().y),
//		 (float)getHitbox().width, (float)getHitbox().height);
		surface.translate((float) x, (float) y);
		surface.rotate((PApplet.radians((float) (dir))));
		surface.image(GamePanel.resources.getImage(imageKey), 0, 0, (int) width, (int) height);
		surface.popMatrix();
	}
	
	/**
	 * 
	 * The start time of this attack
	 * 
	 * @return The start time of this attack as a long
	 */
	public long getStartTime() {
		return startTime;
	}
	
	/**
	 * 
	 * Gets if this attack breaks through shields
	 * 
	 * @return True if shield breaker
	 */
	public boolean isShieldBreaker() {
		return shieldBreaker;
	}
		
	/**
	 * 
	 * Sets this Attack as active
	 * 
	 * @param b True if active, false if inactive
	 */
	public void setActive(boolean b) {
		active = b;
	}
	
	/**
	 * Ends the Attack, makes it not a thing
	 */
	public void end() {
		active = false;
	}
}

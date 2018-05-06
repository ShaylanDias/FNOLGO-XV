package gameplay.attacks;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * 
 * A damaging or status-causing effect that an Avatar can create
 * 
 * @author shaylandias
 *
 */
public abstract class Attack extends MovingSprite{

	public enum AttackResult {SUCCESS, BLOCKED, MISSED, SAME_AVATAR}
	
	private int player;
	private StatusEffect effect;
	private boolean shieldBreaker;
	private double damage;
	//Angle with zero facing right
	protected double dir;
	protected boolean active;
	
	/**
	 * 
	 * Creates an Attack object
	 * 
	 * @param images Image to use for Attack
	 * @param x Starting X-Coordinate
	 * @param y Starting Y-Coordinate
	 * @param w Width of Attack
	 * @param h Height of Attack
	 * @param player The Player who created this Attack
	 * @param damage The damage of this Attack
	 * @param knockback The Knockback caused by this Attack
	 * @param dir The angle of this Attack
	 */
	public Attack(String imageKey, int x, int y, int w, int h, int player, double damage, boolean shieldBreaker, StatusEffect effect, double dir) {
		super(imageKey, x, y, w, h);
		this.damage = damage;
		this.player = player;
		this.dir = dir;
		active = true;
		this.effect = effect;
	}
		
	/**
	 * 
	 * Gets the number of the Player who used this Attack
	 * 
	 * @return Player number
	 */
	public int getPlayer() {
		return player;
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
		if(active)
			return damage;
		else
			return 0;
	}
	
	public StatusEffect getEffect() {
		return effect;
	}
	
	/**
	 * 
	 * What this Attack should do each time the game loop moves forward
	 * 
	 */
	public abstract void act();
		
	/**
	 * Draws this Attack
	 */
	public void draw(PApplet surface) {
		surface.pushMatrix();
		surface.rotate((float)Math.toRadians(dir));
		super.draw(surface);
		surface.popMatrix();
	}
	
	/**
	 * Makes this Attack inactive
	 */
	public void end() {
		active = false;
	}
}

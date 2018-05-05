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
public abstract class Attack extends MovingImage{

	private int player;
	public double damage;
	public double knockback;
	protected double dir;
	protected boolean active;
	
	/**
	 * 
	 * Creates an Attack object
	 * 
	 * @param img Image to use for Attack
	 * @param x Starting X-Coordinate
	 * @param y Starting Y-Coordinate
	 * @param w Width of Attack
	 * @param h Height of Attack
	 * @param player The Player who created this Attack
	 * @param damage The damage of this Attack
	 * @param knockback The Knockback caused by this Attack
	 * @param dir The angle of this Attack
	 */
	public Attack(PImage img, int x, int y, int w, int h, int player, double damage, double knockback, double dir) {
		super(img, x, y, w, h);
		this.damage = damage;
		this.player = player;
		this.dir = dir;
		this.knockback = knockback;
		active = true;
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
	 * Gets the Knockback of this Attack
	 * 
	 * @return The knockback, 0 if it is inactive
	 */
	public double getKnockback() {
		if(active)
			return knockback;
		else
			return 0;
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

package gameplay.attacks;

import java.util.ArrayList;

import gameplay.attacks.StatusEffect.Effect;
import gameplay.avatars.Avatar;
import processing.core.PApplet;

/**
 * A fireball projectile that can be created by an Avatar
 * 
 * @author bgu307
 *
 */
public class Fireball extends Projectile {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4721363189502301853L;
	private static final int W = 40, H = 40;
	private static final double SPEED = 25, RANGE = 500;
	private static final boolean SHIELD_BREAKER = false;
	private static StatusEffect EFFECT = new StatusEffect(Effect.NONE, 0, 0);
	private static final String imageKey = "Fireball1";
	private double delay = 0;
	private boolean drawDelay = false;
	private long startTime;

	/**
	 * 
	 * Creates a Fireball belonging to the given Avatar's address
	 * 
	 * @param x
	 *            The starting x-pos
	 * @param y
	 *            The starting y-pos
	 * @param playerAddress
	 *            The Avatar's Player's IP address
	 * @param dir
	 *            The direction of travel
	 * @param dmg The damage of this Fireball
	 * @param time The server time of instantiation 
	 */
	public Fireball(int x, int y, String playerAddress, double dir, double dmg, long time) {
		super(imageKey, x, y, W, H, playerAddress, dmg, SHIELD_BREAKER, EFFECT, dir, RANGE, SPEED, time);
	}
	
	/**
	 * 
	 * Creates a Fireball with a specified speed, image, and range
	 * 
	 * @param x The starting x-pos
	 * @param y The starting y-pos
	 * @param playerAddress The Avatar's Player's IP address
	 * @param dir The direction of travel
	 * @param imageKey The Resources image key
	 * @param range The range of this Attack
	 * @param speed The speed of movement
	 * @param dmg The damage of this Attack
	 * @param time The server time of instantiation 
	 */
	public Fireball(int x, int y, String playerAddress, double dir, String imageKey, double range, double speed, double dmg, long time) {
		super(imageKey, x, y, W, H, playerAddress, dmg, SHIELD_BREAKER, EFFECT, dir, range, speed, time);
	}
	
	/**
	 * 
	 * Creates a Fireball with a specific width and height
	 * 
	 * @param x The starting x-pos
	 * @param y The starting y-pos
	 * @param playerAddress The Avatar's Player's IP address
	 * @param dir The direction of travel
	 * @param imageKey The Resources image key
	 * @param range The range of this Attack
	 * @param speed The speed of movement
	 * @param w The width
	 * @param h The height
	 * @param dmg The damage of this Attack
	 * @param time The server time of instantiation 
	 */
	public Fireball(int x, int y, String playerAddress, double dir, String imageKey, double range, double speed, int w, int h, double dmg, long time) {
		super(imageKey, x, y, w, h, playerAddress, dmg, SHIELD_BREAKER, EFFECT, dir, range, speed, time);
	}
	
	/**
	 * 
	 * Creates a Fireball with a delay before activation
	 * 
	 * @param x The starting x-pos
	 * @param y The starting y-pos
	 * @param playerAddress The Avatar's Player's IP address
	 * @param dir The direction of travel
	 * @param imageKey The Resources image key
	 * @param range The range of this Attack
	 * @param speed The speed of movement
	 * @param w The width
	 * @param h The height
	 * @param delay The amount to delay this Attack's activation
	 * @param dmg The damage of this Attack
	 * @param time The server time of instantiation 
	 */
	public Fireball(int x, int y, String playerAddress, double dir, String imageKey, double range, double speed, int w, int h, double delay, double dmg, long time) {
		super(imageKey, x, y, w, h, playerAddress, dmg, SHIELD_BREAKER, EFFECT, dir, range, speed, time);
		this.delay = delay;
		this.startTime = time;
	}
	
	/**
	 * 
	 * Creates an Attack with a delay before drawing
	 * 
	 * @param x The starting x-pos
	 * @param y The starting y-pos
	 * @param playerAddress The Avatar's Player's IP address
	 * @param dir The direction of travel
	 * @param imageKey The Resources image key
	 * @param range The range of this Attack
	 * @param speed The speed of movement
	 * @param w The width
	 * @param h The height
	 * @param delay The amount to delay this Attack's activation
	 * @param drawDelay Whether or not to delay drawing the Attack
	 * @param dmg The damage of this Attack
	 * @param time The server time of instantiation 
	 */
	public Fireball(int x, int y, String playerAddress, double dir, String imageKey, double range, double speed, int w, int h, double delay, boolean drawDelay, double dmg, long time) {
		super(imageKey, x, y, w, h, playerAddress, dmg, SHIELD_BREAKER, EFFECT, dir, range, speed, time);
		this.delay = delay;
		this.drawDelay = drawDelay;
		this.startTime = time;
	}
	
	@Override
	public void draw(PApplet surface, long time) {
		if(drawDelay) {
			if(time > startTime + delay * 1000)
				super.draw(surface, time);
		} else
			super.draw(surface, time);
	}
	
	@Override
	public boolean act(ArrayList<Avatar> avatars, long time) {
		if(time > startTime + delay * 1000)
			return super.act(avatars, time);
		else
			return true;
	}

}

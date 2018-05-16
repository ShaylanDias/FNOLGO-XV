package gameplay.attacks;

import java.util.ArrayList;

import gameplay.attacks.StatusEffect.Effect;
import gameplay.avatars.Avatar;

/**
 * A fireballe projectile that can be created by an avatar
 * 
 * @author bgu307
 *
 */
public class Fireball extends Projectile {

	private static final int W = 40, H = 40;
	private static final double DMG = 15, SPEED = 25, RANGE = 500;
	private static final boolean SHIELD_BREAKER = false;
	private static StatusEffect EFFECT = new StatusEffect(Effect.NONE, 0, 0);
	private static final String imageKey = "Fireball1";
	private double delay = 0;
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
	 */
	public Fireball(int x, int y, String playerAddress, double dir) {
		super(imageKey, x, y, W, H, playerAddress, DMG, SHIELD_BREAKER, EFFECT, dir, RANGE, SPEED);
	}
	
	public Fireball(int x, int y, String playerAddress, double dir, String imageKey, double range, double speed) {
		super(imageKey, x, y, W, H, playerAddress, DMG, SHIELD_BREAKER, EFFECT, dir, range, speed);
	}
	
	public Fireball(int x, int y, String playerAddress, double dir, String imageKey, double range, double speed, int w, int h) {
		super(imageKey, x, y, w, h, playerAddress, DMG, SHIELD_BREAKER, EFFECT, dir, range, speed);
	}
	
	public Fireball(int x, int y, String playerAddress, double dir, String imageKey, double range, double speed, int w, int h, double delay) {
		super(imageKey, x, y, w, h, playerAddress, DMG, SHIELD_BREAKER, EFFECT, dir, range, speed);
		this.delay = delay;
		this.startTime = System.currentTimeMillis();
	}
	
	@Override
	public boolean act(ArrayList<Avatar> avatars) {
		if(System.currentTimeMillis() > startTime + delay * 1000)
			return super.act(avatars);
		else
			return true;
	}

}

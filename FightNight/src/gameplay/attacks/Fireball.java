package gameplay.attacks;

import java.util.ArrayList;

import gameplay.GameState;
import gameplay.attacks.StatusEffect.Effect;
import gameplay.avatars.Avatar;
import processing.core.PApplet;

/**
 * A fireballe projectile that can be created by an avatar
 * 
 * @author bgu307
 *
 */
public class Fireball extends Projectile {

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
	 */
	public Fireball(int x, int y, String playerAddress, double dir, double dmg, long time) {
		super(imageKey, x, y, W, H, playerAddress, dmg, SHIELD_BREAKER, EFFECT, dir, RANGE, SPEED, time);
	}
	
	public Fireball(int x, int y, String playerAddress, double dir, String imageKey, double range, double speed, double dmg, long time) {
		super(imageKey, x, y, W, H, playerAddress, dmg, SHIELD_BREAKER, EFFECT, dir, range, speed, time);
	}
	
	public Fireball(int x, int y, String playerAddress, double dir, String imageKey, double range, double speed, int w, int h, double dmg, long time) {
		super(imageKey, x, y, w, h, playerAddress, dmg, SHIELD_BREAKER, EFFECT, dir, range, speed, time);
	}
	
	public Fireball(int x, int y, String playerAddress, double dir, String imageKey, double range, double speed, int w, int h, double delay, double dmg, long time) {
		super(imageKey, x, y, w, h, playerAddress, dmg, SHIELD_BREAKER, EFFECT, dir, range, speed, time);
		this.delay = delay;
		this.startTime = time;
	}
	
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

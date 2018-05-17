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
	public Fireball(int x, int y, String playerAddress, double dir, double dmg) {
		super(imageKey, x, y, W, H, playerAddress, dmg, SHIELD_BREAKER, EFFECT, dir, RANGE, SPEED);
	}
	
	public Fireball(int x, int y, String playerAddress, double dir, String imageKey, double range, double speed, double dmg) {
		super(imageKey, x, y, W, H, playerAddress, dmg, SHIELD_BREAKER, EFFECT, dir, range, speed);
	}
	
	public Fireball(int x, int y, String playerAddress, double dir, String imageKey, double range, double speed, int w, int h, double dmg) {
		super(imageKey, x, y, w, h, playerAddress, dmg, SHIELD_BREAKER, EFFECT, dir, range, speed);
	}
	
	public Fireball(int x, int y, String playerAddress, double dir, String imageKey, double range, double speed, int w, int h, double delay, double dmg) {
		super(imageKey, x, y, w, h, playerAddress, dmg, SHIELD_BREAKER, EFFECT, dir, range, speed);
		this.delay = delay;
		this.startTime = GameState.getGameTime();
	}
	
	public Fireball(int x, int y, String playerAddress, double dir, String imageKey, double range, double speed, int w, int h, double delay, boolean drawDelay, double dmg) {
		super(imageKey, x, y, w, h, playerAddress, dmg, SHIELD_BREAKER, EFFECT, dir, range, speed);
		this.delay = delay;
		this.drawDelay = drawDelay;
		this.startTime = GameState.getGameTime();
	}
	
	@Override
	public void draw(PApplet surface) {
		if(drawDelay) {
			if(GameState.getGameTime() > startTime + delay * 1000)
				super.draw(surface);
		} else
			super.draw(surface);
	}
	
	@Override
	public boolean act(ArrayList<Avatar> avatars) {
		if(GameState.getGameTime() > startTime + delay * 1000)
			return super.act(avatars);
		else
			return true;
	}

}

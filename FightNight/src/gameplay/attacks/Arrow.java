package gameplay.attacks;

import gameplay.attacks.StatusEffect.Effect;

public class Arrow extends Projectile {

	private static final int W = 30, H = 30;
	private static final double DMG = 10, SPEED = 35, RANGE = 700;
	private static final boolean SHIELD_BREAKER = false;
	private static StatusEffect EFFECT = new StatusEffect(Effect.NONE, 0, 0);
	private static final String imageKey = "Arrow1";

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
	public Arrow(int x, int y, String playerAddress, double dir) {
		super(imageKey, x, y, W, H, playerAddress, DMG, SHIELD_BREAKER, EFFECT, dir, RANGE, SPEED);
	}

}
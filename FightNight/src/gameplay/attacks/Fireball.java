package gameplay.attacks;

import gameplay.attacks.StatusEffect.Effect;

public class Fireball extends Projectile{

	private static final int W = 10, H = 5;
	private static final double DMG = 15, SPEED = 8, RANGE = 200;
	private static final boolean SHIELD_BREAKER = false;
	private static StatusEffect EFFECT = new StatusEffect(Effect.NONE, 0, 0);
	public static final String imageKey = "Fireball";
	
	public Fireball(int x, int y, int player, double dir) {
		super(imageKey, x, y, W, H, player, DMG, SHIELD_BREAKER, EFFECT, dir, RANGE, SPEED);
	}

	
	
}

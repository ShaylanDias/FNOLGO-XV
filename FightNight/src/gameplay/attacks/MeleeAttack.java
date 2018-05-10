package gameplay.attacks;

public class MeleeAttack extends Attack{

	public MeleeAttack(String imageKey, int x, int y, int w, int h, String playerAddress, double damage,
			boolean shieldBreaker, StatusEffect effect, double dir, double duration) {
		super(imageKey, x, y, w, h, playerAddress, damage, shieldBreaker, effect, dir);
		super.duration = duration;
	}

}

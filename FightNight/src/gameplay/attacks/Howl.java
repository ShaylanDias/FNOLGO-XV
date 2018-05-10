package gameplay.attacks;

public class Howl extends Attack{

	public Howl(String imageKey, int x, int y, int w, int h, String playerAddress, double damage, boolean shieldBreaker,
			StatusEffect effect, double dir) {
		super(imageKey, x, y, w, h, playerAddress, damage, shieldBreaker, effect, dir);
		duration = 2;
	}

}

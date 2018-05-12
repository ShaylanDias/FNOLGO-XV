package gameplay.attacks;

public class Trap extends Attack{

	public Trap(String imageKey, int x, int y, int w, int h, String playerAddress, double damage, boolean shieldBreaker,
			StatusEffect effect, double duration) {
		super(imageKey, x, y, w, h, playerAddress, damage, shieldBreaker, effect, 0);
		this.duration = duration;
	}
	
}

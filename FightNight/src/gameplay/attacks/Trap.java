package gameplay.attacks;

public class Trap extends Attack{

	public Trap(String imageKey, int x, int y, int w, int h, String playerAddress, double damage,
			StatusEffect effect, double duration, long time) {
		super(imageKey, x, y, w, h, playerAddress, damage, false, effect, 0, time);
		this.duration = duration;
	}
	
}

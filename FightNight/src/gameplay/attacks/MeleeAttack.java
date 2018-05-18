package gameplay.attacks;

public class MeleeAttack extends Attack{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1518471030589179806L;

	/**
	 * 
	 * Creates a short range Attack that does not move
	 * 
	 * @param imageKey The Resources image key
	 * @param x The x-coordinate
	 * @param y The y-coordinate
	 * @param w The width 
	 * @param h The height
	 * @param playerAddress The player's IP address
	 * @param damage The damage caused
	 * @param shieldBreaker If this shield breaks
	 * @param effect The effect
	 * @param dir The angle
	 * @param duration The duration
	 * @param time The server time of instantiation
	 */
	public MeleeAttack(String imageKey, int x, int y, int w, int h, String playerAddress, double damage,
			boolean shieldBreaker, StatusEffect effect, double dir, double duration, long time) {
		super(imageKey, x, y, w, h, playerAddress, damage, shieldBreaker, effect, dir, time);
		super.duration = duration;
	}

}

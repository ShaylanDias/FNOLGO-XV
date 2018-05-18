package gameplay.attacks;

public class Trap extends Attack {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7148542094636911976L;

	/**
	 * 
	 * Creates a Trap
	 * 
	 * @param imageKey
	 *            The key leading to the image in Resources
	 * @param x
	 *            The starting x-coordinate
	 * @param y
	 *            The starting y-coordinate
	 * @param w
	 *            The width
	 * @param h
	 *            The height
	 * @param playerAddress
	 *            The IP address of the player
	 * @param damage
	 *            Damage caused by this attack
	 * @param shieldBreaker
	 *            True if this attack breaks through shields
	 * @param effect
	 *            The StatusEffect that this Attack inflicts
	 * @param dir
	 *            The direction of the Attack
	 * @param time
	 *            The time of instantiation on the server
	 */
	public Trap(String imageKey, int x, int y, int w, int h, String playerAddress, double damage, StatusEffect effect,
			double duration, long time) {
		super(imageKey, x, y, w, h, playerAddress, damage, false, effect, 0, time);
		this.duration = duration;
	}

}

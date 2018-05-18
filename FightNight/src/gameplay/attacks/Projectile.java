package gameplay.attacks;

import java.util.ArrayList;

import gameplay.avatars.Avatar;

/**
 * 
 * A moving Attack that moves on its own and does something on impact with an
 * Avatar
 * 
 * @author shaylandias
 *
 */
public abstract class Projectile extends Attack {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3028464151480348176L;
	private double range;
	private double speed;
	private double distTraveled;

	/**
	 * 
	 * Initializes a Projectile
	 * 
	 * @param imageKey
	 *            The Resources key to the image of this Projectile
	 * @param x
	 *            The x-coord
	 * @param y
	 *            THe y-coord
	 * @param w
	 *            Width
	 * @param h
	 *            Height
	 * @param playerAddress
	 *            The IP Address of the Player who launched this
	 * @param damage
	 * @param shieldBreaker
	 *            True if this Projectile breaks through shields
	 * @param effect
	 *            The StatusEffect caused by this Projectile
	 * @param dir
	 *            The angle
	 * @param range
	 *            The range
	 * @param speed
	 *            The speed
	 * @param time
	 *            The server time of instantiation
	 */
	public Projectile(String imageKey, int x, int y, int w, int h, String playerAddress, double damage,
			boolean shieldBreaker, StatusEffect effect, double dir, double range, double speed, long time) {
		super(imageKey, x + w / 2, y + h / 2, w, h, playerAddress, damage, shieldBreaker, effect, 360 - dir, time);
		this.range = range;
		this.speed = speed;
		distTraveled = 0;
	}

	/**
	 * Checks if this Projectile has ended its path
	 */
	protected boolean checkEnd() {
		if (!super.isActive())
			return true;
		if (distTraveled >= range) {
			return true;
		} else
			return false;
	}

	@Override
	public boolean act(ArrayList<Avatar> avatars, long time) {
		boolean ended = checkEnd();
		if (ended)
			return false;
		else {
			if (super.isActive()) {
				x += Math.cos(Math.toRadians(dir)) * speed;
				y += Math.sin(Math.toRadians(dir)) * speed;
				distTraveled += speed;
			}
			for (Avatar a : avatars) {

				if (a.getHitbox().intersects(this)) {
					AttackResult res = a.takeHit(this, time);
					if (res.equals(AttackResult.BLOCKED) || res.equals(AttackResult.SUCCESS)) {
						end();
					}
				}
			}
			return true;
		}
	}
}

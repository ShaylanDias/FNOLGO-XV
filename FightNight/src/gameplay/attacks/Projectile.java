package gameplay.attacks;

/**
 * 
 * A moving Attack that moves on its own and does something on impact with an Avatar
 * 
 * @author shaylandias
 *
 */
public class Projectile extends Attack{

	private double range;
	private double speed;
	private double distTraveled;

	public Projectile(String imageKey, int x, int y, int w, int h, int player, double damage, boolean shieldBreaker, StatusEffect effect, double dir, double range, double speed) {
		super(imageKey, x, y, w, h, player, damage, shieldBreaker, effect, dir);
		this.range = range;
		this.speed = speed;
		distTraveled = 0;
	}

	protected boolean checkEnd() {
		if(distTraveled >= range) {
			return true;
		}
		else
			return false;
	}
	
	@Override
	public boolean act() {
		boolean ended = checkEnd();
		if(ended)
			return false;
		else {
			if(super.isActive()) {
				x += Math.cos(Math.toRadians(super.dir)) * speed;
				y += Math.sin(Math.toRadians(dir)) * speed;
				distTraveled += speed;
			}
			return true;
		}
	}

}

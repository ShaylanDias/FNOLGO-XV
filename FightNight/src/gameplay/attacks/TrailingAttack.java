package gameplay.attacks;

import java.util.ArrayList;

import gameplay.avatars.Avatar;

/**
 * 
 * An Attack that follows an Avatar
 * 
 * @author shaylandias
 *
 */
public class TrailingAttack extends Attack{

	/**
	 * 
	 */
	private static final long serialVersionUID = 171241532021960293L;
	private Avatar trail;
	private int xOffset, yOffset;
	
	/**
	 * 
	 * Creates a TrailingAttack
	 * 
	 * @param imageKey The image to use from Resources
	 * @param xOffset The x offset from the center of the Avatar
	 * @param yOffset The y offset from the center of the Avatar
	 * @param w The width
	 * @param h The height
	 * @param playerAddress The player's IP address
	 * @param damage The damage
	 * @param effect The Effect caused by this Attack
	 * @param dir The angle of this Attack
	 * @param duration The duration of this Attack
	 * @param trail The Avatar that this Attack trails
	 * @param time The server time of instantiation
	 */
	public TrailingAttack(String imageKey, int xOffset, int yOffset, int w, int h, String playerAddress, double damage,
			StatusEffect effect, double dir, double duration, Avatar trail, long time) {
		super(imageKey, (int)(trail.getX() + xOffset), (int)(trail.getY() + yOffset), w, h, playerAddress, damage, false, effect, dir, time);
		super.duration = duration;
		this.trail = trail;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	@Override
	public boolean act(ArrayList<Avatar> avatars, long time) {
		if(time < super.getStartTime() + (duration) * 1000) {
			x = trail.getX() + xOffset * Math.cos(Math.toRadians(dir));
			y = trail.getY() - yOffset * Math.sin(Math.toRadians(dir));
		}
		
		for (Avatar a : avatars) {
			if (a.getHitbox().intersects(this)) {
				a.takeHit(this, time);
			}
		}
		return !checkEnd(time);

	}

	@Override
	protected boolean checkEnd(long time) {
		if (!super.isActive())
			return true;
		if (time > super.getStartTime() + duration * 1000) {
			end();
			return true;
		} else
			return false;
	}
	
}

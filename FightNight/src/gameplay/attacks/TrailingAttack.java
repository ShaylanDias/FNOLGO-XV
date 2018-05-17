package gameplay.attacks;

import java.util.ArrayList;

import gameplay.GameState;
import gameplay.avatars.Avatar;

public class TrailingAttack extends Attack{

	private Avatar trail;
	private int xOffset, yOffset;
	
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

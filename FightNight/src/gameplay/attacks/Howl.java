package gameplay.attacks;

import java.util.ArrayList;

import gameplay.attacks.StatusEffect.Effect;
import gameplay.avatars.Avatar;

public class Howl extends Attack{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4225007198557429236L;
	private static final String imageKey = "Howl";
	private static final double damage = 2.5, width = 150, height = 150;
	
	/**
	 * 
	 * Creates a Howl Attack surrounding the Avatar
	 * 
	 * @param x X-position
	 * @param y Y-Position
	 * @param playerAddress The player that created the Attack's address
	 * @param time The server time of instantiation
	 */
	public Howl(int x, int y, String playerAddress, long time) {
		super(imageKey,(int)( x), (int)(y), (int)width, (int)height, playerAddress, damage, false, new StatusEffect(Effect.SLOWED, 1, 7), 0, time);
		duration = 2;
	}
	
	@Override
	public boolean act(ArrayList<Avatar> avatars, long time) {
		if (!super.isActive()) {
			return false;
		}
		for (Avatar a : avatars) {
			if (a.getHitbox().intersects(this)) {
				System.out.println("Intesects");
				a.takeHit(this, time);
			}
		}
		
		if(time> super.getStartTime() + super.duration * 1000) {
			super.checkEnd(time);
		}
		
		if(time <= super.getStartTime() + 1000) {
			super.width += 10;
			super.height += 10;
		} else {
			super.width -= 4;
			super.height -= 4;
		}
		
		return true;
	}

}

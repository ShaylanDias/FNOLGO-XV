package gameplay.attacks;

import java.util.ArrayList;

import gameplay.GameState;
import gameplay.attacks.StatusEffect.Effect;
import gameplay.avatars.Avatar;

public class Howl extends Attack{

	private static final String imageKey = "Howl";
	private static final double damage = 2.5, width = 150, height = 140;
	
	public Howl(int x, int y, String playerAddress, long time) {
		super(imageKey, x, y, (int)width, (int)height, playerAddress, damage, false, new StatusEffect(Effect.SLOWED, 1, 7), 0, time);
		duration = 2;
	}
	
	public boolean act(ArrayList<Avatar> avatars, long time) {
		if (!super.isActive()) {
			return false;
		}
		for (Avatar a : avatars) {
			if (a.getHitbox().intersects(this)) {
				a.takeHit(this, time);
			}
		}
		
		if(time> super.getStartTime() + super.duration * 1000) {
			super.checkEnd(time);
		}
		
		if(time <= super.getStartTime() + 1000) {
			super.width += 6;
			super.height += 6;
		} else {
			super.width -= 4;
			super.height -= 4;
		}
		
		return true;
	}

}

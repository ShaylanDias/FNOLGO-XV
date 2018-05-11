package gameplay.attacks;

import java.util.ArrayList;

import gameplay.attacks.StatusEffect.Effect;
import gameplay.avatars.Avatar;

public class Howl extends Attack{

	private static final String imageKey = "Howl";
	private static final double damage = 1.5, width = 120, height = 110;
	
	public Howl(int x, int y, String playerAddress) {
		super(imageKey, x, y, (int)width, (int)height, playerAddress, damage, false, new StatusEffect(Effect.SLOWED, 0.5, 10), 0);
		duration = 2;
	}
	
	public boolean act(ArrayList<Avatar> avatars) {
		if (!super.isActive()) {
			return false;
		}
		for (Avatar a : avatars) {
			if (a.getHitbox().intersects(this)) {
				a.takeHit(this);
			}
		}
		
		if(System.currentTimeMillis() > super.getStartTime() + super.duration * 1000) {
			super.checkEnd();
		}
		
		if(System.currentTimeMillis() <= super.getStartTime() + 1000) {
			super.width += 6;
			super.height += 6;
		} else {
			super.width -= 4;
			super.height -= 4;
		}
		
		return true;
	}

}

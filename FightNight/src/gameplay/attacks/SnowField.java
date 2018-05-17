package gameplay.attacks;

import java.util.ArrayList;

import gameplay.GameState;
import gameplay.attacks.StatusEffect.Effect;
import gameplay.avatars.Avatar;

public class SnowField extends Attack{

	private static final int w = 200, h = 200, damage = 3;
	private static final boolean shieldBreaker = false;
	private int direction, direction2;
	
	public SnowField(int x, int y, String playerAddress, double dir, long time) {
		super("SnowField", x, y, w, h, playerAddress, damage, shieldBreaker, new StatusEffect(Effect.SLOWED, 1.5, 8), dir, time);
	
		direction = 1;
		direction2 = 1;
		if(Math.random() > 0.5)
			direction = -1;
		if(Math.random() > 0.5)
			direction2 = -1;
		duration = 2.5;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean act(ArrayList<Avatar> avatars, long time) {
		for (Avatar a : avatars) {
			if (a.getHitbox().intersects(this)) {
				AttackResult res = a.takeHit(this, time);
			}
		}
		
		x += 3 * Math.cos(Math.toRadians(dir)) * direction;
		y += 3 * Math.sin(Math.toRadians(dir)) * direction2;

		dir += 1.5;
		
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

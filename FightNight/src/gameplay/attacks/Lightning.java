package gameplay.attacks;

import java.util.ArrayList;

import gameplay.attacks.StatusEffect.Effect;
import gameplay.avatars.Avatar;
import processing.core.PApplet;

public class Lightning extends Attack{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2816025387162325526L;
	private double delay;
	private Lightning attached;
	/**
	 * Lightning standard width, height, and damage
	 */
	public static final int w = 80, h = 80, damage = 3;
	private boolean dead;
	
	/**
	 * 
	 * Creates a Lightning Attack which chains with others
	 * 
	 * @param imageKey The key to the image
	 * @param x X-position
	 * @param y Y-position
	 * @param playerAddress The player IP address
	 * @param dir Direction of the attack
	 * @param delay The delay before starting
	 * @param attached The Lightning object this one is attached to
	 * @param time The server start time
	 */
	public Lightning(String imageKey, int x, int y, String playerAddress, double dir, double delay, Lightning attached, long time) {
		super(imageKey, x, y, w, h, playerAddress, damage, true, new StatusEffect(Effect.STUNNED, 1.5, 1), dir, time);
		super.setActive(false);
		this.delay = delay;
		this.attached = attached;
		duration = 0.5;
		dead = false;
	}

	@Override
	public boolean act(ArrayList<Avatar> avatars, long time) {
		
		if(time > super.getStartTime() + delay * 1000)
			super.setActive(true);
		else
			return true;
		
		if (checkEnd(time)) {
			return false;
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
	
	@Override
	protected boolean checkEnd(long time) {
		if (time > super.getStartTime() + duration * 1000) {
			end();
			return true;
		} else if(attached != null && attached.isDead()) {
			end();
			return true;
		} else
			return false;
	}
	
	@Override
	public void end() {
		if(attached != null)
			attached.setActive(false);
		super.end();
	}
	
	@Override
	public void draw(PApplet surface, long time) {
		if(time > super.getStartTime() + delay * 1000)
			super.draw(surface, time);
	}
	
	/**
	 * 
	 * Is this attack over
	 * 
	 * @return True if the attack is ended
	 */
	public boolean isDead() {
		return dead;
	}
	
}

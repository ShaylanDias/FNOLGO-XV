package gameplay.attacks;

import java.util.ArrayList;

import gameplay.attacks.StatusEffect.Effect;
import gameplay.avatars.Avatar;
import processing.core.PApplet;

public class Lightning extends Attack{
	
	private double delay;
	private Lightning attached;
	public static final int w = 80, h = 80, damage = 20;
	private boolean dead;
	
	public Lightning(String imageKey, int x, int y, String playerAddress, double dir, double delay, Lightning attached, long time) {
		super(imageKey, x, y, w, h, playerAddress, damage, true, new StatusEffect(Effect.STUNNED, 1.5, 1), dir, time);
		super.setActive(false);
	
//		if(dir > 45 && dir < 135 || dir > 225 && dir < 315) {
//			width = h;
//			height = w;
//			flipDraw = true;
//		}
		this.delay = delay;
		this.attached = attached;
		duration = 0.5;
		dead = false;
	}

	
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
	
	public void end() {
		if(attached != null)
			attached.setActive(false);
		super.end();
	}
	
	public void draw(PApplet surface, long time) {
		if(time > super.getStartTime() + delay * 1000)
			super.draw(surface, time);
	}
	
	public boolean isDead() {
		return dead;
	}
	
}

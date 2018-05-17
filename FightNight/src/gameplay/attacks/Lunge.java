package gameplay.attacks;

import java.util.ArrayList;

import gameplay.GameState;
import gameplay.avatars.Avatar;
import gameplay.avatars.Brute;
import processing.core.PApplet;

public class Lunge extends Attack{

	private static double damage = 30;
	private static boolean shieldBreaker = true;
	private Brute attacker;
	private static String imageKey = "UpperCut";
	private double stopMoving;

	public Lunge(String playerAddress, double dir, Brute attacker, int x, int y, StatusEffect status, double duration, double stopMoving, long time) {
		super(imageKey, x, y, 50, 100, playerAddress, damage, shieldBreaker, status, dir, time);
		this.attacker = attacker;
		this.duration = duration;
		this.stopMoving = stopMoving;
	}
	
	
	@Override
	public void draw(PApplet surface, long time) {

		//		surface.pushMatrix();

		if(time > super.getStartTime() + 300) {
			super.draw(surface, time);
			//			surface.rectMode(PApplet.CENTER);
			//			 surface.noFill();
			//			 surface.rect((float)(getHitbox().x), (float)(getHitbox().y),
			//			 (float)getHitbox().width, (float)getHitbox().height);
		}

		//		surface.popMatrix();
	}

	@Override
	public boolean act(ArrayList<Avatar> avatars, long time) {
		if(time < super.getStartTime() + (duration-stopMoving) * 1000) {
			x = attacker.getX() + 100 * Math.cos(Math.toRadians(dir));
			y = attacker.getY() - 60 * Math.sin(Math.toRadians(dir));
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

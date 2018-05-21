package gameplay.attacks;

import java.util.ArrayList;

import gameplay.avatars.Avatar;
import gameplay.avatars.Brute;
import processing.core.PApplet;

public class Lunge extends Attack{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4689584839026758360L;
	private static double damage = 30;
	private static boolean shieldBreaker = true;
	private Brute attacker;
	private static String imageKey = "UpperCut";
	private double stopMoving;

	/**
	 * 
	 * Creates an Attack that moves in front of a Brute
	 * 
	 * @param playerAddress The player's IP address
	 * @param dir The direction of the lunge
	 * @param attacker The Brute that started this lunge
	 * @param x X-position
	 * @param y Y-position
	 * @param status Status effect this causes
	 * @param duration The duration of the attack
	 * @param stopMoving When to stop moving
	 * @param time Server time of instantiation
	 */
	public Lunge(String playerAddress, double dir, Brute attacker, int x, int y, StatusEffect status, double duration, double stopMoving, long time) {
		super(imageKey, x, y, 50, 100, playerAddress, damage, shieldBreaker, status, dir, time);
		this.attacker = attacker;
		this.duration = duration;
		this.stopMoving = stopMoving;
	}
	
	
	@Override
	public void draw(PApplet surface, long time) {
		if(time > super.getStartTime() + 300) {
			super.draw(surface, time);
		}
	}

	@Override
	public boolean act(ArrayList<Avatar> avatars, long time) {
		if(time < super.getStartTime() + (duration-stopMoving) * 1000) {
			x = attacker.getX() + 100 * Math.cos(Math.toRadians(dir));
			y = attacker.getY() - 60 * Math.sin(Math.toRadians(dir));
		}
		return super.act(avatars, time);

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

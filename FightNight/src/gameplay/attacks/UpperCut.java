package gameplay.attacks;

import java.util.ArrayList;

import gameplay.attacks.StatusEffect.Effect;
import gameplay.avatars.Avatar;
import gameplay.avatars.Brute;
import processing.core.PApplet;

public class UpperCut extends Attack{

	private static double damage = 30;
	private static boolean shieldBreaker = true;
	private static StatusEffect effect = new StatusEffect(Effect.STUNNED, 1d, 1d);
	private Brute attacker;
	private static String imageKey = "UpperCut";

	public UpperCut(String playerAddress, double dir, Brute attacker, int x, int y) {
		super(imageKey, x, y, 50, 20, playerAddress, damage, shieldBreaker, effect, dir);
		this.attacker = attacker;
		duration = 1.1;
	}

	public void draw(PApplet surface) {

		//		surface.pushMatrix();

		if(System.currentTimeMillis() > super.getStartTime() + 300) {
			super.draw(surface);
			//			surface.rectMode(PApplet.CENTER);
			//			 surface.noFill();
			//			 surface.rect((float)(getHitbox().x), (float)(getHitbox().y),
			//			 (float)getHitbox().width, (float)getHitbox().height);
		}

		//		surface.popMatrix();
	}

	@Override
	public boolean act(ArrayList<Avatar> avatars) {
		if(System.currentTimeMillis() < super.getStartTime() + 0.88 * 1000) {
			x = attacker.getX() + 100 * Math.cos(Math.toRadians(dir));
			y = attacker.getY() - 60 * Math.sin(Math.toRadians(dir));
		}
		return super.act(avatars);
	}


}

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
	private static String imageKey;
	
	public UpperCut(String playerAddress, double dir, Brute attacker, int x, int y) {
		super(imageKey, x, y, 10, 20, playerAddress, damage, shieldBreaker, effect, dir);
		this.attacker = attacker;
		duration = 1.1;
	}
	
	public void draw(PApplet surface) {
//		if(System.currentTimeMillis() > super.getStartTime() + 700)
//			super.draw(surface);
	}
	
	@Override
	public boolean act(ArrayList<Avatar> avatars) {
		x = attacker.getX();
		y = attacker.getY();
		width = attacker.getWidth();
		height = attacker.getHeight();
		if(System.currentTimeMillis() > super.getStartTime() + 300)
			return super.act(avatars);
		else
			return false;
	}
	
	
}

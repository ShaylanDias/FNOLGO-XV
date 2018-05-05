package gameplay.attacks;

import processing.core.PImage;

/**
 * 
 * A moving Attack that moves on its own and does something on impact with an Avatar
 * 
 * @author shaylandias
 *
 */
public class Projectile extends Attack{

	private double range;
	private double speed;
	private double distTraveled;
	
	public Projectile(PImage img, int x, int y, int w, int h, int player, double damage, double knockback, double dir, double range, double speed) {
		super(img, x, y, w, h, player, damage, knockback, dir);
		this.range = range;
		this.speed = speed;
		distTraveled = 0;
	}

	@Override
	public void act() {
		x += Math.cos(Math.toRadians(super.dir)) * speed;
		y += Math.sin(Math.toRadians(dir)) * speed;
		distTraveled += speed;
		if(distTraveled > range)
			super.end();
	}
	
}

package gameplay;

import processing.core.PImage;

public abstract class Attack extends MovingImage{

	private int player;
	private double damage;
	private double knockback;
	
	public Attack(PImage img, int x, int y, int w, int h) {
		super(img, x, y, w, h);
	}
	
	public int getPlayer() {
		return player;
	}
	
	public double getKnockback() {
		return knockback;
	}
	
	public double getDamage() {
		return damage;
	}
	
}

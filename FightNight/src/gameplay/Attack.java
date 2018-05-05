package gameplay;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * 
 * A damaging or status-causing effect that an Avatar can create
 * 
 * @author shaylandias
 *
 */
public abstract class Attack extends MovingImage{

	private int player;
	public double damage;
	public double knockback;
	protected double dir;
	protected boolean active;
	
	public Attack(PImage img, int x, int y, int w, int h, int player, double damage, double knockback, double dir) {
		super(img, x, y, w, h);
		this.damage = damage;
		this.player = player;
		this.dir = dir;
		this.knockback = knockback;
		active = true;
	}
		
	public int getPlayer() {
		return player;
	}
	
	public double getKnockback() {
		if(active)
			return knockback;
		else
			return 0;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public double getDamage() {
		if(active)
			return damage;
		else
			return 0;
	}
	
	public abstract void act();
		
	public void draw(PApplet surface) {
		surface.pushMatrix();
		surface.rotate((float)Math.toRadians(dir));
		super.draw(surface);
		surface.popMatrix();
	}
	
	public void end() {
		active = false;
	}
}

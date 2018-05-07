package gameplay.attacks;

import java.util.ArrayList;

import clientside.gui.GamePanel;
import gameplay.avatars.Avatar;
import processing.core.PApplet;

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

	public Projectile(String imageKey, int x, int y, int w, int h, int player, double damage, boolean shieldBreaker, StatusEffect effect, double dir, double range, double speed) {
		super(imageKey, x-w/2, y-h/2, w, h, player, damage, shieldBreaker, effect, 360-dir);
		this.range = range;
		this.speed = speed;
		distTraveled = 0;
	}

	protected boolean checkEnd() {
		if(!super.isActive())
			return true;
		if(distTraveled >= range) {
			return true;
		}
		else
			return false;
	}

	public void draw(PApplet surface) {
		surface.pushMatrix();
		
		surface.translate((float)x, (float)y);
		surface.rotate((float)Math.toRadians(dir));
		surface.image(GamePanel.resources.getImage(imageKey), (int) x, (int) y, (int) width, (int) height);
		surface.popMatrix();
	}
	
	@Override
	public boolean act(ArrayList<Avatar> avatars) {
		boolean ended = checkEnd();
		if(ended)
			return false;
		else {
			if(super.isActive()) {
				x += Math.cos(Math.toRadians(dir)) * speed;
				y += Math.sin(Math.toRadians(dir)) * speed;
				distTraveled += speed;
			}			
			for(Avatar a : avatars) {
				
				if(a.getHitbox().intersects(this)) {
					AttackResult res = a.takeHit(this);
					if(res.equals(AttackResult.BLOCKED) || res.equals(AttackResult.SUCCESS)) {
						end();
					}
				}
			}
			return true;
		}
	}
}

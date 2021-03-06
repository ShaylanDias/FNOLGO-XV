package gameplay.attacks;

import java.awt.Rectangle;
import java.util.ArrayList;

import clientside.gui.GamePanel;
import gameplay.attacks.StatusEffect.Effect;
import gameplay.avatars.Avatar;
import processing.core.PApplet;

public class Howl extends Attack{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4225007198557429236L;
	private static final String imageKey = "Howl";
	private static final double damage = 2.5, width = 150, height = 150;
	
	/**
	 * 
	 * Creates a Howl Attack surrounding the Avatar
	 * 
	 * @param x X-position
	 * @param y Y-Position
	 * @param playerAddress The player that created the Attack's address
	 * @param time The server time of instantiation
	 */
	public Howl(int x, int y, String playerAddress, long time) {
		super(imageKey,(int)(x - width/2), (int)(y - height/2), (int)width, (int)height, playerAddress, damage, false, new StatusEffect(Effect.SLOWED, 5, 1), 0, time);
		duration = 2;
	}
	
	
	/**
	 * 
	 * Gets the translated hitbox so it matches with the image
	 * 
	 * @return The Avatar's hitbox as a Rectangle
	 */
	protected Rectangle getHitbox() {
		return new Rectangle((int) (x - super.width), (int) (y + super.height), (int) super.width, (int) super.height);
	}
	
	@Override
	public boolean act(ArrayList<Avatar> avatars, long time) {
		if (!super.isActive()) {
			return false;
		}
		for (Avatar a : avatars) {
			if (a.getHitbox().intersects(this)) {
				a.takeHit(this, time);
			}
		}
		
		if(time> super.getStartTime() + super.duration * 1000) {
			super.checkEnd(time);
		}
		
		if(time <= super.getStartTime() + 1000) {
			super.width += 10;
			super.height += 10;
		} else {
			super.width -= 4;
			super.height -= 4;
		}
		
		return true;
	}

	/**
	 * Draws this Attack
	 */
	@Override
	public void draw(PApplet surface, long time) {
		surface.pushMatrix();
		surface.imageMode(PApplet.CORNER);
		surface.rectMode(PApplet.CENTER);
		// surface.noFill();
//		surface.rect((float)(getHitbox().x), (float)(getHitbox().y),
//				(float)getHitbox().width, (float)getHitbox().height);
		surface.translate((float) x, (float) y);
		surface.rotate((PApplet.radians((float) (dir))));
		surface.image(GamePanel.resources.getImage(imageKey), (float)(0 - super.width/4), (float)(0 - super.height/4), (int) super.width, (int) super.height);
		surface.popMatrix();
	}
	
}

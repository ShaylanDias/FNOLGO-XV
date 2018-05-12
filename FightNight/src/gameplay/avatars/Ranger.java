package gameplay.avatars;

import java.awt.Rectangle;

import clientside.gui.GamePanel;
import gameplay.attacks.Attack;
import gameplay.attacks.Fireball;
import gameplay.attacks.StatusEffect.Effect;
import processing.core.PApplet;

/**
 * A ranger class character 
 * @author hzhu684
 *
 */
public class Ranger extends Avatar {

	private AttackType currentAttack;

	/**
	 * Instantiates a Ranger
	 */
	public Ranger() {
		super();
		super.basicCD = 0.5;
		spriteSheetKey = "Ranger";
		sprites = new Rectangle[] { new Rectangle(92, 94, 52, 88) };
		hitbox.height = sprites[0].height;
		hitbox.width = sprites[0].width;
		dashCD = 0.5;	
		numOfSpriteWalk = 10;
		for(int i = 1; i < 11; i++) {
			getSpriteListWalk().add("Ranger"+i);
		}

	}
	/**
	 * Creates a Ranger at this x,y
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 */
	public Ranger(double x, double y) {
		this();
		this.hitbox.x = x;
	}



	// Knife, shank some fool
	@Override
	public Attack[] basicAttack(String player, double angle) {
		return null;

	}

	// Shoot bow, arrow goes until it hits a wall
	@Override
	public Attack[] rangedAttack(String player, double angle) {
		super.rangedCDStart = System.currentTimeMillis();
		currentlyAttacking = true;
		currentAttack = AttackType.RANGED;
		timeActionStarted = System.currentTimeMillis();
		if(angle > 90 && angle < 270)
			lastDir = false;
		else
			lastDir = true;
		return new Attack[] {new Fireball((int) hitbox.x, (int) hitbox.y, player, angle, "Rock", 400, 22)};

	}

	// PlaceTrap, place an invisible trap that expires after a certain amount of
	// time. The ranger can carry 3 at time
	@Override
	public Attack[] abilityOne(String player, double angle) {
		return null;

	}

	// Barrage - fire multiple arrows in an area
	@Override
	public Attack[] abilityTwo(String player, double angle) {
		return null;

	}

	// Smoke Bomb - Turns you invisible to other players for a few seconds
	@Override
	public Attack[] abilityThree(String player, double angle) {
		return null;

	}

	public void draw(PApplet surface) {
		surface.pushMatrix();
		surface.pushStyle();
	
		drawHealthBar(surface);
	
		if(deathTime != 0) {
			drawDeath(surface);
			surface.popMatrix();
			surface.popStyle();
			return;
		}
	
		if (blocking) {
			// Draw block
		}
	
		int sw, sh;
		//		sx = (int) sprites[spriteInd].getX();
		//		sy = (int) sprites[spriteInd].getY();
		sw = (int) sprites[spriteInd].getWidth();
		sh = (int) sprites[spriteInd].getHeight();
	
		surface.imageMode(PApplet.CENTER);
	
		if (super.getStatus().getEffect().equals(Effect.STUNNED)) {
			surface.image(GamePanel.resources.getImage("Stun"), (float)hitbox.x, (float)(hitbox.y - hitbox.height * 1.1), 30, 30);
		}
		
		surface.pushMatrix();
	
		float widthMod = 1f;
		if(currentlyAttacking)
			widthMod = 1.3f;
		
		if (lastDir) {
			surface.scale(-1, 1);
			surface.image(GamePanel.resources.getImage(spriteSheetKey), (float) -hitbox.x, (float) hitbox.y, -sw * widthMod, sh);
		} else {
			surface.image(GamePanel.resources.getImage(spriteSheetKey), (float) hitbox.x, (float) hitbox.y, sw * widthMod, sh);
		}
		surface.popMatrix();
		if (blocking) {
			if (System.currentTimeMillis() / 250 % 5 == 0) {
				surface.tint(140);
			} else if (System.currentTimeMillis() / 250 % 5 == 1) {
				surface.tint(170);
			} else if (System.currentTimeMillis() / 250 % 5 == 2) {
				surface.tint(200);
			} else if (System.currentTimeMillis() / 250 % 5 == 3) {
				surface.tint(240);
			}
			surface.image(GamePanel.resources.getImage(blockImageKey), (float) hitbox.x, (float) hitbox.y, 1.5f * sw,
					1.5f * sh);
		}
	
		// surface.rect((float)hitbox.x, (float)hitbox.y, (float)sw, (float)sh);
		// surface.fill(Color.RED.getRGB());
		// surface.ellipseMode(PApplet.CENTER);
		// surface.ellipse((float)(hitbox.x), (float)(hitbox.y), 5f, 5f);
	
		surface.popMatrix();
		surface.popStyle();
	}
	
	public void act() {
		super.act();
		if (!super.isLeft() && !super.isRight() && !super.isUp() && !super.isDown()) {
			spriteSheetKey = "Ranger";
		}
	}
	
}

package gameplay.avatars;

import java.awt.Rectangle;

import clientside.gui.GamePanel;
import gameplay.attacks.Attack;
import gameplay.attacks.Fireball;
import gameplay.attacks.Lightning;
import gameplay.attacks.MeleeAttack;
import gameplay.attacks.SnowField;
import gameplay.attacks.StatusEffect;
import gameplay.attacks.StatusEffect.Effect;
import gameplay.avatars.Avatar.AttackType;
import processing.core.PApplet;
/**
 * Creates a mage character
 * @author hzhu684
 *
 */
public class Mage extends Avatar {

	private AttackType currentAttack;

	/**
	 * Instantiates a Mage
	 */
	public Mage() {
		super();
		super.basicCD = 0.9;
		moveSpeed = 7;
		spriteSheetKey = "Mage";
		sprites = new Rectangle[] { new Rectangle(70, 94, 50, 90) };
		a3CD = 6;
		a2CD = 8;
		rangedCD = 0.5;
		hitbox.height = sprites[0].height;
		hitbox.width = sprites[0].width;
		dashCD = 2.0;
		numOfSpriteWalk = 3;
		for(int i = 1; i < 4; i++) {
			getSpriteListWalk().add("Mage"+i);
		}
	}
	/**
	 * Creats a Mage at this x,y
	 * 
	 * @param x The x position of the mage
	 * @param y The y position of the mage
	 */
	public Mage(double x, double y) {
		this();
		this.hitbox.x = x;
	}

	// Staff wack, it pushes them back
	@Override
	public Attack[] basicAttack(String player, double angle) {
		if (System.currentTimeMillis() > super.basicCDStart + super.basicCD * 1000 && !dashing && !blocking) {
			//			currentlyAttacking = true;
			basicCDStart = System.currentTimeMillis();
			currentAttack = AttackType.BASIC;
			timeActionStarted = System.currentTimeMillis();
			if(angle > 90 && angle < 270) 
				lastDir = true;
			else
				lastDir = false;
			return new Attack[] {new MeleeAttack("MageBasic", (int)( hitbox.x + 50 * Math.cos(Math.toRadians(angle))), (int)( hitbox.y - 75 * Math.sin(Math.toRadians(angle))), 40, 40, player, 20,
					false, new StatusEffect(Effect.NONE,0,0), angle, 0.15)};
		} else {
			return null;
		}
	}
	@Override
	public void dash(Double mouseAngle) {
		if (System.currentTimeMillis() > super.dashCDStart + super.dashCD * 1000) {
			super.dashCDStart = System.currentTimeMillis();
			super.dash(mouseAngle);
		} 
	}
	// Fireball, slow moving, and does a bunch of dmg, goes until it hits a wall.
	@Override
	public Attack[] rangedAttack(String player, double angle) {
		currentAttack = AttackType.RANGED;
		super.rangedCDStart = System.currentTimeMillis();
		if(angle > 90 && angle < 270) {
			lastDir = true;
			return new Attack[] {new Fireball((int) hitbox.x - 40, (int) hitbox.y - 40, player, angle)};
		}
		else {
			lastDir = false;
			return new Attack[] {new Fireball((int) hitbox.x + 10, (int) hitbox.y - 43, player, angle)};
		}
	}

	// Fire eruption. circular bust in an area that does dmg to anyone in them.
	@Override
	public Attack[] abilityOne(String player, double angle) {
		return null;
	}

	// Snow Storm - slows down everybody in an area
	@Override
	public Attack[] abilityTwo(String player, double angle) {
		//		currentlyAttacking = true;
		//		movementControlled = false;
		currentAttack = AttackType.A2;
		if(angle > 90 && angle < 270)
			lastDir = true;
		else
			lastDir = false;
		a2CDStart = System.currentTimeMillis();
		timeActionStarted = a2CDStart;	

		angle = 360 - angle;
		double x = super.getX() + 180 * Math.cos(Math.toRadians(angle));
		double y = super.getY() + 180 * Math.sin(Math.toRadians(angle));

		return new Attack[] {new SnowField((int)x, (int)y, player, angle)};
	}

	// Lightning blast, stands still and charges a kamehameha.
	@Override
	public Attack[] abilityThree(String player, double angle) {
		//		currentlyAttacking = true;
		//		movementControlled = false;
		currentAttack = AttackType.A3;
		if(angle > 90 && angle < 270)
			lastDir = true;
		else
			lastDir = false;
		a3CDStart = System.currentTimeMillis();
		timeActionStarted = a3CDStart;		
		double w, h;
		h = Lightning.h;
		w = Lightning.w;
		angle = randomLightningAngle(360-angle);

		double x = super.getX();
		double y = super.getY();
		y -= 20;
		if(lastDir)
			x -= 80;
		else
			x += 80;

		Lightning l1 = new Lightning("Lightning", (int)(x), (int)(y), player, randomLightningAngle(angle), 0.05, null);
		angle = randomLightningAngle(angle);
		x += w * Math.cos(Math.toRadians(angle));
		y +=  h * Math.sin(Math.toRadians(angle));
		Lightning l2 = new Lightning("Lightning1", (int)(x), (int)(y), player, randomLightningAngle(angle), 0.1, l1);
		angle = randomLightningAngle(angle);
		x += w * Math.cos(Math.toRadians(angle));
		y +=  h * Math.sin(Math.toRadians(angle));
		Lightning l3 = new Lightning("Lightning2", (int)(x), (int)(y), player, randomLightningAngle(angle), 0.15, l2);
		angle = randomLightningAngle(angle);
		x += w * Math.cos(Math.toRadians(angle));
		y +=  h * Math.sin(Math.toRadians(angle));
		Lightning l4 = new Lightning("Lightning3", (int)(x), (int)(y), player, randomLightningAngle(angle), 0.2, l3);
		angle = randomLightningAngle(angle);
		x += w * Math.cos(Math.toRadians(angle));
		y +=  h * Math.sin(Math.toRadians(angle));
		Lightning l5 = new Lightning("Lightning4", (int)(x), (int)(y), player, randomLightningAngle(angle), 0.25, l4);
		angle = randomLightningAngle(angle);
		x += w * Math.cos(Math.toRadians(angle));
		y +=  h * Math.sin(Math.toRadians(angle));
		Lightning l6 = new Lightning("Lightning5", (int)(x), (int)(y), player, randomLightningAngle(angle), 0.3, l5);
		return new Attack[] {l6, l5, l4, l3, l2, l1};
	}

	private double randomLightningAngle(double angle) {
		double diff = Math.random() * 45;
		diff = 22.5-diff;
		return angle + diff;
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

		surface.imageMode(PApplet.CENTER);

		if (super.getStatus().getEffect().equals(Effect.STUNNED)) {
			surface.image(GamePanel.resources.getImage("Stun"), (float)hitbox.x, (float)hitbox.y);
		}

		int sw, sh;
		//		sx = (int) sprites[spriteInd].getX();
		//		sy = (int) sprites[spriteInd].getY();
		sw = (int) sprites[spriteInd].getWidth();
		sh = (int) sprites[spriteInd].getHeight();


		surface.pushMatrix();
		if (!lastDir) {
			surface.image(GamePanel.resources.getImage(spriteSheetKey), (float) hitbox.x, (float) hitbox.y, sw, sh);

		} else {
			surface.scale(-1, 1);
			surface.image(GamePanel.resources.getImage(spriteSheetKey), (float) -hitbox.x, (float) hitbox.y, -sw, sh);
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

}

package gameplay.avatars;

import java.awt.Color;
import java.awt.Rectangle;

import clientside.gui.GamePanel;
import gameplay.attacks.Attack;
import gameplay.attacks.Fireball;
import gameplay.attacks.MeleeAttack;
import gameplay.attacks.StatusEffect;
import gameplay.attacks.StatusEffect.Effect;
import gameplay.attacks.UpperCut;
import processing.core.PApplet;

/**
 * Creates a specific brute type of character.
 * 
 * @author hzhu684
 *
 */
public class Brute extends Avatar {

	private String[] deathImageKeys;
	private String[] upperCutKeys;
	private AttackType currentAttack;
	private final double upperCutTime = 1.1;
	private double upperCutAngle;

	/**
	 * Instantiates a Brute
	 */
	public Brute() {
		super();
		super.basicCD = 0.8;
		spriteSheetKey = "WWDefault";
		sprites = new Rectangle[] { new Rectangle(62, 94, 85, 65) };
		hitbox.height = sprites[0].height;
		hitbox.width = sprites[0].width;
		dashCD = 1.0;
		dashDistance = 120;
		dashSpeed = 40;
		a1CD = 7;
		basicCD = 1.2;
		rangedCDStart = 0;
		rangedCD = 5;
		currentAttack = AttackType.NONE;

		deathImageKeys = new String[] {"WWDying", "WWDead"};
		upperCutKeys = new String[] {"UpperCut1", "UpperCut2", "UpperCut3", "UpperCut4", "UpperCut5", "UpperCut6", "UpperCut7", "UpperCut8"};
		getSpriteListWalk().add("WWWalk0");
		getSpriteListWalk().add("WWWalk1");
		getSpriteListWalk().add("WWWalk2");
		getSpriteListWalk().add("WWWalk3");
		numOfSpriteWalk = 4;
	}

	/**
	 * 
	 * Creates a Brute at this x,y
	 * 
	 * @param x
	 * @param y
	 */
	public Brute(double x, double y) {
		this();
		this.hitbox.x = x;
	}

	// Punch, slow but does a lot of dmg
	@Override
	public Attack basicAttack(String player, double angle) {
		if (System.currentTimeMillis() > super.basicCDStart + super.basicCD * 1000 && !dashing && !blocking) {
			super.rangedCDStart = System.currentTimeMillis();
			currentlyAttacking = true;
			basicCDStart = System.currentTimeMillis();
			currentAttack = AttackType.BASIC;
			timeActionStarted = System.currentTimeMillis();
			if(angle > 90 && angle < 270) 
				lastDir = false;
			else
				lastDir = true;
				return new MeleeAttack("WWBasic", (int)( hitbox.x + 50 * Math.cos(Math.toRadians(angle))), (int)( hitbox.y - 50 * Math.sin(Math.toRadians(angle))), 40, 40, player, 20,
						false, new StatusEffect(Effect.NONE,0,0), angle, 0.15);
		} else {
			return null;
		}
	}

	private void basicAct() {
		if(System.currentTimeMillis() < timeActionStarted + 0.06 * 1000) {
			spriteSheetKey = upperCutKeys[7];
		} else if(System.currentTimeMillis() < timeActionStarted +  0.15 * 1000 ) {
			spriteSheetKey = upperCutKeys[5];
		} else {
			currentlyAttacking = false;
			currentAttack = AttackType.NONE;
		}
	}
	
	@Override
	public void dash(Double mouseAngle) {
		if (System.currentTimeMillis() > super.dashCDStart + super.dashCD * 1000) {
			super.dashCDStart = System.currentTimeMillis();
			super.dash(mouseAngle);
		}
	}

	// Throws a slow moving projectile (Rock)
	@Override
	public Attack rangedAttack(String player, double angle) {
		if (System.currentTimeMillis() > super.rangedCDStart + super.rangedCD * 1000 && !dashing && !blocking) {
			super.rangedCDStart = System.currentTimeMillis();
			currentlyAttacking = true;
			currentAttack = AttackType.RANGED;
			timeActionStarted = System.currentTimeMillis();
			if(angle > 90 && angle < 270)
				lastDir = false;
			else
				lastDir = true;
			return new Fireball((int) hitbox.x, (int) hitbox.y, player, angle, "Rock", 400, 22);
		} else {
			return null;
		}

	}

	private void rangedAct() {
		if(System.currentTimeMillis() < timeActionStarted + 0.2 * 1000) {
			spriteSheetKey = upperCutKeys[7];
		} else if(System.currentTimeMillis() < timeActionStarted +  0.4 * 1000 ) {
			spriteSheetKey = upperCutKeys[6];
		} else {
			currentlyAttacking = false;
			currentAttack = AttackType.NONE;
		}

	}

	// UpperCut - dashes forwards and stuns someone
	@Override
	public Attack abilityOne(String player, double angle) {
		currentlyAttacking = true;
		movementControlled = false;
		currentAttack = AttackType.A1;
		timeActionStarted = System.currentTimeMillis();
		upperCutAngle = 360 - angle;
		if(angle > 90 && angle < 270)
			lastDir = false;
		else
			lastDir = true;
		a1CDStart = System.currentTimeMillis();
		return new UpperCut(this.getPlayer(), angle, this, (int)super.getX(), (int)super.getY());
	}

	private void actUpperCut() {
		if(System.currentTimeMillis() < timeActionStarted + 0.1 * 1000) {
			spriteSheetKey = upperCutKeys[0];
		} else if(System.currentTimeMillis() < timeActionStarted +  0.15 * 1000 ) {
			spriteSheetKey = upperCutKeys[1];
		} else if(System.currentTimeMillis() < timeActionStarted + 0.2 * 1000) {
			spriteSheetKey = upperCutKeys[2];
		} else if(System.currentTimeMillis() < timeActionStarted + 0.3 * 1000) {
			spriteSheetKey = upperCutKeys[3];
		} else if(System.currentTimeMillis() < timeActionStarted + 0.85 * 1000) {
			spriteSheetKey = upperCutKeys[4];
			super.moveDistance(30, upperCutAngle);
		} else if(System.currentTimeMillis() < timeActionStarted + 0.88 * 1000) {
			spriteSheetKey = upperCutKeys[5];
		} else if(System.currentTimeMillis() < timeActionStarted + 0.94 * 1000) {
			spriteSheetKey = upperCutKeys[6];
		} else if(System.currentTimeMillis() < timeActionStarted + 1000) {
			spriteSheetKey = upperCutKeys[7];
		} else if (System.currentTimeMillis() > timeActionStarted + 1000) {
			currentAttack = AttackType.NONE;
			movementControlled = true;
			currentlyAttacking = false;
		}
	}

	// GroundSmash, and aoe stun that does dmg
	@Override
	public Attack abilityTwo(String player, double angle) {
		return null;

	}

	// EatMutton - heals the dude
	@Override
	public Attack abilityThree(String player, double angle) {
		return null;
	}

	public String getSpriteSheetKey() {
		return spriteSheetKey;
	}

	public void setSpriteSheetKey(String spriteSheetKey) {
		this.spriteSheetKey = spriteSheetKey;
	}

	public void drawDeath(PApplet surface){
		if(System.currentTimeMillis() <= deathTime + 1000)
			surface.image(GamePanel.resources.getImage(deathImageKeys[0]), (float)hitbox.x, (float)hitbox.y, (float)hitbox.width, (float)hitbox.height);
		else {
			surface.image(GamePanel.resources.getImage(deathImageKeys[1]), (float)hitbox.x, (float)hitbox.y, (float)hitbox.width * 1.2f, (float)hitbox.height * 0.7f);
		}
	}

	public void act() {

		if(currentAttack == AttackType.A1)
			actUpperCut();
		else if(currentAttack == AttackType.RANGED)
			rangedAct();
		else if(currentAttack == AttackType.BASIC)
			basicAct();
		else {

			super.act();

			if (!super.isLeft() && !super.isRight() && !super.isUp() && !super.isDown()) {
				spriteSheetKey = "WWDefault";
			}
		}

	}


	public void draw(PApplet surface) {
		surface.pushMatrix();
		surface.pushStyle();


		//Health Bar
		surface.rectMode(PApplet.CORNER);
		surface.fill(Color.BLACK.getRGB());
		surface.rect((float)(hitbox.x - hitbox.width * 0.5), (float)(hitbox.y - hitbox.height * 0.7), (float)hitbox.width * 0.7f, (float)hitbox.height/6);
		surface.fill(Color.GREEN.getRGB());
		surface.rect((float)(hitbox.x - hitbox.width * 0.5), (float)(hitbox.y - hitbox.height * 0.7), (float)hitbox.width * 0.7f * (float)(health/fullHealth), (float)hitbox.height/6);


		if(deathTime != 0) {
			drawDeath(surface);
			surface.popMatrix();
			surface.popStyle();
			return;
		}

		if (blocking) {
			// Draw block
		}
		if (!super.getStatus().getEffect().equals(Effect.NONE)) {
			// Add on effect
		}

		int sw, sh;
		//		sx = (int) sprites[spriteInd].getX();
		//		sy = (int) sprites[spriteInd].getY();
		sw = (int) sprites[spriteInd].getWidth();
		sh = (int) sprites[spriteInd].getHeight();

		surface.imageMode(PApplet.CENTER);

		surface.pushMatrix();

		float widthMod = 1f;
		if(currentlyAttacking)
			widthMod = 1.3f;
		if (super.isRight() || lastDir) {
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


}

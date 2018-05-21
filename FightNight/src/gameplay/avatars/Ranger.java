package gameplay.avatars;

import java.awt.Rectangle;

import clientside.gui.GamePanel;
import gameplay.attacks.Attack;
import gameplay.attacks.Attack.AttackResult;
import gameplay.attacks.Fireball;
import gameplay.attacks.MeleeAttack;
import gameplay.attacks.StatusEffect;
import gameplay.attacks.StatusEffect.Effect;
import gameplay.attacks.Trap;
import gameplay.maps.Map;
import processing.core.PApplet;

/**
 * A ranger class character
 * 
 * @author hzhu684
 *
 */
public class Ranger extends Avatar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 469678249508257590L;
	private AttackType currentAttack;
	private boolean invisible;
	private long invisStartTime, smokeTime;
	private final double invisLength = 4.25;

	/**
	 * Instantiates a Ranger
	 */
	public Ranger() {
		super();
		super.basicCD = 0.5;
		a2CD = 15;
		a1CD = 10;
		a3CD = 15;
		spriteSheetKey = "Ranger";
		sprites = new Rectangle[] { new Rectangle(92, 94, 52, 88) };
		hitbox.height = sprites[0].height;
		hitbox.width = sprites[0].width;
		dashCD = 1.2;
		dashSpeed = 40;
		
		rangedCD = 0.75;
		numOfSpriteWalk = 10;
		health = 250;
		for (int i = 1; i < 11; i++) {
			getSpriteListWalk().add("Ranger" + i);
		}

		getSpriteListDeath().clear();
		getSpriteListDeath().add("RangerDying1");
		getSpriteListDeath().add("RangerDying2");
		getSpriteListDeath().add("RangerDying3");
		getSpriteListDeath().add("RangerDead");
		deathTime = 0;
		numOfSpriteDeath = getSpriteListDeath().size();
	}

	/**
	 * Creates a Ranger at this x,y
	 * 
	 * @param x
	 *            The x coordinate
	 * @param y
	 *            The y coordinate
	 */
	public Ranger(double x, double y) {
		this();
		this.hitbox.x = x;
	}

	// Knife, shank some fool
	@Override
	public Attack[] basicAttack(String player, double angle, long time) {
		if (time > super.basicCDStart + super.basicCD * 1000 && !dashing && !blocking) {
			currentlyAttacking = true;
			basicCDStart = time;
			currentAttack = AttackType.BASIC;
			timeActionStarted = time;
			if (angle > 90 && angle < 270)
				lastDir = true;
			else
				lastDir = false;
			return new Attack[] { new MeleeAttack("Knife", (int) (hitbox.x + 50 * Math.cos(Math.toRadians(angle))),
					(int) (hitbox.y - 75 * Math.sin(Math.toRadians(angle))), 40, 40, player, 20, false,
					new StatusEffect(Effect.NONE, 0, 0), angle, 0.15, time) };
		} else {
			return null;
		}
	}

	// Shoot bow, arrow goes until it hits a wall
	@Override
	public Attack[] rangedAttack(String player, double angle, long time) {
		currentAttack = AttackType.RANGED;
		currentlyAttacking = true;
		timeActionStarted = time;
		int damage = 20;
		super.rangedCDStart = timeActionStarted;
		if (angle > 90 && angle < 270) {
			lastDir = true;
			return new Attack[] { new Fireball((int) hitbox.x - 20, (int) hitbox.y - 10, player, angle, "Arrow", 600d, 40, 60, 14, 0.45, damage, time) };
		} else {
			lastDir = false;
			return new Attack[] { new Fireball((int) hitbox.x - 20, (int) hitbox.y - 10, player, angle, "Arrow", 600d, 40, 60, 14, 0.45, damage, time) };
		}
	}

	// Barrage - fire multiple arrows in an area
	@Override
	public Attack[] abilityOne(String player, double angle, long time) {
		currentAttack = AttackType.A1;
		currentlyAttacking = true;
		timeActionStarted = time;
		double damage = 10;
		super.a1CDStart = timeActionStarted;
		if (angle > 90 && angle < 270) {
			lastDir = true;
			return new Attack[] {
					new Fireball((int) hitbox.x - 20, (int) hitbox.y - 10, player, angle, "Arrow", 600, 40, 60, 30, 0.3,
							damage, time),
					new Fireball((int) hitbox.x - 20, (int) hitbox.y - 10, player, angle - 10, "Arrow", 600, 40, 60, 14,
							0.3, damage, time),
					new Fireball((int) hitbox.x - 20, (int) hitbox.y - 10, player, angle - 5, "Arrow", 600, 40, 60, 14,
							0.3, damage, time),
					new Fireball((int) hitbox.x - 20, (int) hitbox.y - 10, player, angle + 5, "Arrow", 600, 40, 60, 14,
							0.3, damage, time),
					new Fireball((int) hitbox.x - 20, (int) hitbox.y - 10, player, angle + 10, "Arrow", 600, 40, 60, 14,
							0.3, damage, time),
					new Fireball((int) hitbox.x - 20, (int) hitbox.y - 10, player, angle + 15, "Arrow", 600, 40, 60, 14,
							0.3, damage, time),
					new Fireball((int) hitbox.x - 20, (int) hitbox.y - 10, player, angle - 15, "Arrow", 600, 40, 60, 14,
							0.3, damage, time) };
		} else {
			lastDir = false;
			return new Attack[] {
					new Fireball((int) hitbox.x - 30, (int) hitbox.y - 10, player, angle, "Arrow", 600, 40, 60, 14, 0.3,
							damage, time),
					new Fireball((int) hitbox.x - 30, (int) hitbox.y - 10, player, angle - 10, "Arrow", 600, 40, 60, 14,
							0.3, damage, time),
					new Fireball((int) hitbox.x - 30, (int) hitbox.y - 10, player, angle - 5, "Arrow", 600, 40, 60, 14,
							0.3, damage, time),
					new Fireball((int) hitbox.x - 30, (int) hitbox.y - 10, player, angle + 5, "Arrow", 600, 40, 60, 14,
							0.3, damage, time),
					new Fireball((int) hitbox.x - 30, (int) hitbox.y - 10, player, angle + 10, "Arrow", 600, 40, 60, 14,
							0.3, damage, time),
					new Fireball((int) hitbox.x - 30, (int) hitbox.y - 10, player, angle + 15, "Arrow", 600, 40, 60, 14,
							0.3, damage, time),
					new Fireball((int) hitbox.x - 30, (int) hitbox.y - 10, player, angle - 15, "Arrow", 600, 40, 60, 14,
							0.3, damage, time) };

		}
	}

	// PlaceTrap, place an invisible trap that expires after a certain amount of
	// time. The ranger can carry 3 at time
	@Override
	public Attack[] abilityTwo(String player, double angle, long time) {
		currentAttack = AttackType.A2;
		double damage = 30;

		a2CDStart = time;
		timeActionStarted = a2CDStart;
		if (angle > 90 && angle < 270) {
			lastDir = true;
		} else {
			lastDir = false;
		}
		return new Attack[] { new Trap("Mushroom", (int) hitbox.x, (int) hitbox.y, 50, 50, player, damage,
				new StatusEffect(Effect.SLOWED, 4, 5), 60, time) };
	}

	// Smoke Bomb - Turns you invisible to other players for a few seconds
	@Override
	public Attack[] abilityThree(String player, double angle, long time) {
		invisible = true;
		invisStartTime = time;
		a3CDStart = invisStartTime;
		smokeTime = invisStartTime + 200;
		return null;

	}

	public boolean isInvisible() {
		return invisible;
	}

	public boolean isSmoke(long time) {
		return (invisible && time < smokeTime);
	}

	@Override
	public void draw(PApplet surface, long time) {
		surface.pushMatrix();
		surface.pushStyle();

		surface.textSize(14);
		surface.fill(255);
		surface.textAlign(PApplet.CENTER);
		surface.text(getUsername(), (float)hitbox.x, (float)(hitbox.y - hitbox.height + 2));
		
		if (super.isDead()) {
			int sw, sh;
			sw = (int) sprites[spriteInd].getWidth();
			sh = (int) sprites[spriteInd].getHeight();

			drawDeath(numOfSpriteDeath, spriteSpeedDeath, time);
			if (lastDir) {
				surface.scale(-1, 1);
				surface.image(GamePanel.resources.getImage(spriteSheetKey), (float) -hitbox.x, (float) hitbox.y, -sw,
						sh);
			} else {
				surface.image(GamePanel.resources.getImage(spriteSheetKey), (float) hitbox.x, (float) hitbox.y, sw, sh);
			}

			surface.popMatrix();
			surface.popStyle();
			return;
		} else {
			drawHealthBar(surface);
			int sw, sh;
			sw = (int) sprites[spriteInd].getWidth();
			sh = (int) sprites[spriteInd].getHeight();

			surface.imageMode(PApplet.CENTER);

			if (super.getStatus().getEffect().equals(Effect.STUNNED)) {
				surface.image(GamePanel.resources.getImage("Stun"), (float) hitbox.x,
						(float) (hitbox.y - hitbox.height * 1.1), 30, 30);
			}

			surface.pushMatrix();
			surface.pushStyle();
			float widthMod = 1f;

			if (invisible && time < smokeTime) {
				surface.image(GamePanel.resources.getImage("Smoke"), (float) hitbox.x, (float) hitbox.y, sw * widthMod,
						sh);
			}
			if (invisible)
				surface.tint(10);
			if (lastDir) {
				surface.scale(-1, 1);
				surface.image(GamePanel.resources.getImage(spriteSheetKey), (float) -hitbox.x, (float) hitbox.y,
						-sw * widthMod, sh);
			} else {
				surface.image(GamePanel.resources.getImage(spriteSheetKey), (float) hitbox.x, (float) hitbox.y,
						sw * widthMod, sh);
			}
			surface.popMatrix();
			surface.popStyle();
			if (blocking) {
				if (time / 250 % 5 == 0) {
					surface.tint(140);
				} else if (time / 250 % 5 == 1) {
					surface.tint(170);
				} else if (time / 250 % 5 == 2) {
					surface.tint(200);
				} else if (time / 250 % 5 == 3) {
					surface.tint(240);
				}
				surface.image(GamePanel.resources.getImage(blockImageKey), (float) hitbox.x, (float) hitbox.y,
						1.5f * sw, 1.5f * sh);
			}
		}

//		 surface.rect((float)hitbox.x, (float)hitbox.y, (float)sw, (float)sh);
//		 surface.fill(Color.RED.getRGB());
//		 surface.ellipseMode(PApplet.CENTER);
//		 surface.ellipse((float)(hitbox.x), (float)(hitbox.y), 5f, 5f);

		surface.popMatrix();
		surface.popStyle();
	}

	@Override
	public void act(Map map, long time) {

		if (invisible) {
			if (time > invisStartTime + invisLength * 1000)
				invisible = false;
		}

		if (currentlyAttacking) {
			if (currentAttack.equals(AttackType.BASIC))
				actBasic(time);
			else if (currentAttack.equals(AttackType.RANGED) || currentAttack.equals(AttackType.A1))
				actRanged(time);
			return;
		} else {
			super.act(map, time);
			if (!super.isLeft() && !super.isRight() && !super.isUp() && !super.isDown()) {
				spriteSheetKey = "Ranger";
			}
		}
	}

	@Override
	public AttackResult takeHit(Attack attack, long time) {
		AttackResult res = super.takeHit(attack, time);
		if (res.equals(AttackResult.SUCCESS))
			invisible = false;
		return res;
	}

	@Override
	public Attack[] attack(AttackType a, String player, double angle, long time) {
		Attack[] res = super.attack(a, player, angle, time);
		if (res != null)
			invisible = false;
		return res;
	}

	@Override
	public void spawn(Map map) {
		invisible = false;
		super.spawn(map);
	}

	private void actBasic(long time) {
		if (time < timeActionStarted + 0.06 * 1000) {
			spriteSheetKey = "RangerBasic1";
		} else if (time < timeActionStarted + 0.1 * 1000) {
			spriteSheetKey = "RangerBasic2";
		} else if (time < timeActionStarted + 0.16 * 1000) {
			spriteSheetKey = "RangerBasic3";
		} else if (time < timeActionStarted + 0.3 * 1000) {
			spriteSheetKey = "RangerBasicEnd1";
		} else if (time > timeActionStarted + 0.3 * 1000) {
			currentlyAttacking = false;
			currentAttack = AttackType.NONE;
		}
	}

	private void actRanged(long time) {
		if (time < timeActionStarted + 0.03 * 1000) {
			spriteSheetKey = "RangerRanged0";
		} else if (time < timeActionStarted + 0.08 * 1000) {
			spriteSheetKey = "RangerRanged1";
		} else if (time < timeActionStarted + 0.13 * 1000) {
			spriteSheetKey = "RangerRanged2";
		} else if (time < timeActionStarted + 0.18 * 1000) {
			spriteSheetKey = "RangerRanged3";
		} else if (time < timeActionStarted + 0.23 * 1000) {
			spriteSheetKey = "RangerRanged4";
		} else if (time < timeActionStarted + 0.28 * 1000) {
			spriteSheetKey = "RangerRanged5";
		} else if (time < timeActionStarted + 0.33 * 1000) {
			spriteSheetKey = "RangerRanged7";
		} else if (time < timeActionStarted + 0.38 * 1000) {
			spriteSheetKey = "RangerRanged8";
		} else if (time < timeActionStarted + 0.43 * 1000) {
			spriteSheetKey = "RangerRanged9";
		} else if (time > timeActionStarted + 0.48 * 1000) {
			currentlyAttacking = false;
			currentAttack = AttackType.NONE;
		}
	}

	@Override
	public String toString() {
		return "Ranger";
	}

}

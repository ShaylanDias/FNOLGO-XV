package gameplay.avatars;

import java.awt.Rectangle;
import gameplay.attacks.Attack;
import gameplay.attacks.Fireball;

public class Brute extends Avatar {

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
			super.basicCDStart = System.currentTimeMillis();
			return new Fireball((int) hitbox.x, (int) hitbox.y, player, angle);
		} else {
			Fireball f = new Fireball(0, 0, "", 0);
			f.end();
			return f;
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
	public Attack rangedAttack() {
		return null;

	}

	// UpperCut - dashes forwards and stuns someone
	@Override
	public Attack abilityOne() {
		return null;
	}

	// GroundSmash, and aoe stun that does dmg
	@Override
	public Attack abilityTwo() {
		return null;

	}

	// EatMutton - heals the dude
	@Override
	public Attack abilityThree() {
		return null;

	}

	public String getSpriteSheetKey() {
		return spriteSheetKey;
	}

	public void setSpriteSheetKey(String spriteSheetKey) {
		this.spriteSheetKey = spriteSheetKey;
	}

	public void act() {
		super.act();

		if (!super.isLeft() && !super.isRight() && !super.isUp() && !super.isDown()) {
			spriteSheetKey = "WWDefault";
		}
	}

}

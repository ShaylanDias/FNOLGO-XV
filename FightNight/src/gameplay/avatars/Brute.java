package gameplay.avatars;

import java.awt.Rectangle;
import java.util.ArrayList;

import gameplay.attacks.Attack;
import gameplay.attacks.Fireball;

public class Brute extends Avatar {

	private ArrayList<String> spriteListWalk, spriteListAttack;

	/**
	 * Instantiates a Brute
	 */
	public Brute() {
		super();
		super.basicCD = 0.8;
		spriteSheetKey = "BruteWalking1";
		sprites = new Rectangle[] { new Rectangle(62, 94, 85, 62) };
		hitbox.height = sprites[0].height;
		hitbox.width = sprites[0].width;
		dashCD = 1.0;
		dashDistance = 120;
		dashSpeed = 40;

		spriteListWalk = new ArrayList<String>();
		spriteListAttack = new ArrayList<String>();

		spriteListWalk.add("BruteWalking1");
		spriteListWalk.add("BruteWalking2");
		spriteListWalk.add("BruteWalking3");
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

	public void walk() {
		if (System.currentTimeMillis() / 250 % 3 == 0 && !dashing && !blocking) {
			setSpriteSheetKey(spriteListWalk.get(0));
		} else if (System.currentTimeMillis() / 250 % 3 == 1 && !dashing && !blocking) {
			setSpriteSheetKey(spriteListWalk.get(1));
		} else if (System.currentTimeMillis() / 250 % 3 == 2 && !dashing && !blocking) {
			setSpriteSheetKey(spriteListWalk.get(2));
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

		if (super.isRight())
			walk();
	}

}

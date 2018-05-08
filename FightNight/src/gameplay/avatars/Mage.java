package gameplay.avatars;

import java.awt.Rectangle;

import gameplay.attacks.Attack;
import gameplay.attacks.Fireball;

public class Mage extends Avatar {

	/**
	 * Instantiates a Mage
	 */
	public Mage() {
		super();
		super.basicCD = 0.9;
		spriteSheetKey = "Mage1";
		sprites = new Rectangle[] { new Rectangle(70, 94, 54, 90) };
		hitbox.height = sprites[0].height;
		hitbox.width = sprites[0].width;
		dashCD = 2.0;
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
	public Attack basicAttack(String player, double angle) {
		if (System.currentTimeMillis() > super.basicCDStart + super.basicCD * 1000) {
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
	// Fireball, slow moving, and does a bunch of dmg, goes until it hits a wall.
	@Override
	public Attack rangedAttack() {
		return null;

	}

	// Fire eruption. circular bust in an area that does dmg to anyone in them.
	@Override
	public Attack abilityOne() {
		return null;
	}

	// Snow Storm - slows down everybody in an area
	@Override
	public Attack abilityTwo() {
		return null;
	}

	// Lightning blast, stands still and charges a kamehameha.
	@Override
	public Attack abilityThree() {
		return null;

	}
}

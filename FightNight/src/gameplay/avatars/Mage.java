package gameplay.avatars;

import java.awt.Rectangle;

import gameplay.attacks.Attack;
import gameplay.attacks.Fireball;
/**
 * Creates a mage character
 * @author hzhu684
 *
 */
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
	public Attack[] basicAttack(String player, double angle) {
		return null;
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
		super.basicCDStart = System.currentTimeMillis();
		return new Attack[] {new Fireball((int) hitbox.x, (int) hitbox.y, player, angle)};
	}

	// Fire eruption. circular bust in an area that does dmg to anyone in them.
	@Override
	public Attack[] abilityOne(String player, double angle) {
		return null;
	}

	// Snow Storm - slows down everybody in an area
	@Override
	public Attack[] abilityTwo(String player, double angle) {
		return null;
	}

	// Lightning blast, stands still and charges a kamehameha.
	@Override
	public Attack[] abilityThree(String player, double angle) {
		return null;

	}
}

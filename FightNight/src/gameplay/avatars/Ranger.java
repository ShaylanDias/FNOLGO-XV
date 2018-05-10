package gameplay.avatars;

import java.awt.Rectangle;

import gameplay.attacks.Attack;

/**
 * A ranger class character 
 * @author hzhu684
 *
 */
public class Ranger extends Avatar {

	/**
	 * Instantiates a Ranger
	 */
	public Ranger() {
		super();
		super.basicCD = 0.5;
		spriteSheetKey = "Ranger1";
		sprites = new Rectangle[] { new Rectangle(92, 94, 52, 88) };
		hitbox.height = sprites[0].height;
		hitbox.width = sprites[0].width;
		dashCD = 0.5;	
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
	public Attack basicAttack(String player, double angle) {
		return null;

	}

	// Shoot bow, arrow goes until it hits a wall
	@Override
	public Attack rangedAttack(String player, double angle) {
		return null;
	}

	// PlaceTrap, place an invisible trap that expires after a certain amount of
	// time. The ranger can carry 3 at time
	@Override
	public Attack abilityOne(String player, double angle) {
		return null;

	}

	// Barrage - fire multiple arrows in an area
	@Override
	public Attack abilityTwo(String player, double angle) {
		return null;

	}

	// Grappling hook - hooks onto a wall in the map and pulls your towards it, dmgs
	// enemies that you run into, if hook enemy, it acts like a projectile.
	@Override
	public Attack abilityThree(String player, double angle) {
		return null;

	}

}

package gameplay.avatars;

import gameplay.attacks.Attack;

public class Ranger extends Avatar {

	/**
	 * Instantiates a Ranger
	 */
	public Ranger() {
		super();
	}

	// Knife, shank some fool
	@Override
	public Attack basicAttack(String player, double angle) {
		return null;

	}

	// Shoot bow, arrow goes until it hits a wall
	@Override
	public Attack rangedAttack() {
		return null;
	}

	// PlaceTrap, place an invisible trap that expires after a certain amount of
	// time. The ranger can carry 3 at time
	@Override
	public Attack abilityOne() {
		return null;

	}

	// Barrage - fire multiple arrows in an area
	@Override
	public Attack abilityTwo() {
		return null;

	}

	// Grappling hook - hooks onto a wall in the map and pulls your towards it, dmgs
	// enemies that you run into, if hook enemy, it acts like a projectile.
	@Override
	public Attack abilityThree() {
		return null;

	}

}

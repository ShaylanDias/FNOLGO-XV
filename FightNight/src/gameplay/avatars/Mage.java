package gameplay.avatars;

import gameplay.attacks.Attack;

public class Mage extends Avatar {

	/**
	 * Instantiates a Mage
	 */
	public Mage() {
		super();
	}

	// Staff wack, it pushes them back
	@Override
	public Attack basicAttack(String player, double angle) {
		return null;
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

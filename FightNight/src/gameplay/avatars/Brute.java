package gameplay.avatars;

import java.awt.Rectangle;

import gameplay.attacks.Attack;

public class Brute extends Avatar{

	public Brute() {
		super();
		spriteSheetKey = "Fighter1";
		sprites = new Rectangle[] {new Rectangle(62, 94, 62, 98)};
		hitbox.height = sprites[0].height;
		hitbox.width = sprites[0].width;
	}
	
	//Punch, slow but does a lot of dmg 
	public Attack basicAttack() {
		return null;
		
	}
	
	//Throws a slow moving projectile (Rock)
	public Attack rangedAttack() {
		return null;
		
	}
	//UpperCut - dashes forwards and stuns someone
	public Attack abilityOne() {
		return null;
	}
	//GroundSmash, and aoe stun that does dmg
	public Attack abilityTwo() {
		return null;
		
	}
	//EatMutton - heals the dude
	public Attack abilityThree() {
		return null;
		
	}
}

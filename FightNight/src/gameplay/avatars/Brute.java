package gameplay.avatars;

import java.awt.Rectangle;

import gameplay.attacks.Attack;
import gameplay.attacks.Fireball;

public class Brute extends Avatar{

	public Brute() {
		super();
		super.basicCD = 0.8;
		spriteSheetKey = "Fighter1";
		sprites = new Rectangle[] {new Rectangle(62, 94, 62, 98)};
		hitbox.height = sprites[0].height;
		hitbox.width = sprites[0].width;
	}
	
	//Punch, slow but does a lot of dmg 
	public Attack basicAttack(int player, double angle) {
		if(System.currentTimeMillis() > super.basicCDStart + super.basicCD * 1000) {
			super.basicCDStart = System.currentTimeMillis();
			return new Fireball(hitbox.x, hitbox.y , player, angle);
		} else {
			Fireball f = new Fireball(0, 0, 0, 0);
			f.end();
			return f;
		}
		
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

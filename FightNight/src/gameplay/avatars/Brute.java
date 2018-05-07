package gameplay.avatars;

import java.awt.Rectangle;

public class Brute extends Avatar{

	public Brute() {
		super();
		spriteSheetKey = "Fighter1";
		sprites = new Rectangle[] {new Rectangle(62, 94, 62, 98)};
		hitbox.height = sprites[0].height;
		hitbox.width = sprites[0].width;
	}
	//AOE with X radius that would temporarily stun enemies within that radius
	public void groundSmash() {
	}
	
	//Eats something and heals
	public void eatMutton() {
		
	}
	
	//
	public void upperCut() {
		
	}
}

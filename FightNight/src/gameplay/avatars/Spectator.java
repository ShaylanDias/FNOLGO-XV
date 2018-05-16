package gameplay.avatars;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import gameplay.attacks.Attack;
import gameplay.attacks.Attack.AttackResult;
import gameplay.maps.Map;
import processing.core.PApplet;

public class Spectator extends Avatar{

	public Spectator() {
		super();
		super.setLives(0);
		sprites = new Rectangle[] { new Rectangle(100, 100, 200, 200) };
		hitbox = new Rectangle2D.Double(-1000, -1000, 200, 200);
		timeActionStarted = System.currentTimeMillis();
		blocking = false;
		superArmor = false;
		numOfSpriteWalk = 5;
		dashing = false;
		shieldHealth = 125;
		fullShieldHealth = shieldHealth;
		spriteInd = 0;
		health = 200;
		fullHealth = health;
		deathTime = 1241241243;
		numOfSpriteWalk = 0;
		movementControlled = true;
		dashCD = 1;
		super.setDead(true);
	}
	
	public void draw(PApplet surface) {
		surface.pushStyle();
		surface.textAlign(PApplet.CENTER);
		surface.fill(0);
		surface.textSize(30);
		surface.text("Spectating", (float)(hitbox.x), (float)(hitbox.y - 250));
		surface.popStyle();
	}
	
	@Override
	public void dash(Double x) {

	}
	
	public boolean moveBy(double x, double y, Map map) {
		if(map.inBounds(hitbox.x + x, hitbox.y + y, hitbox.width, hitbox.height)) {
			hitbox.x += x;
			hitbox.y += y;
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public AttackResult takeHit(Attack attack) {
		return AttackResult.MISSED;
	}
	
	@Override
	public Attack[] basicAttack(String player, double angle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Attack[] rangedAttack(String player, double angle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Attack[] abilityOne(String player, double angle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Attack[] abilityTwo(String player, double angle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Attack[] abilityThree(String player, double angle) {
		// TODO Auto-generated method stub
		return null;
	}

}

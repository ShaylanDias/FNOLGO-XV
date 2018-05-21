package gameplay.avatars;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import clientside.gui.GamePanel;
import gameplay.attacks.Attack;
import gameplay.attacks.Attack.AttackResult;
import gameplay.maps.Map;
import processing.core.PApplet;

/**
 * 
 * An Avatar that can move but is invisible and cannot interact with the game.
 * 
 * @author sdias695
 *
 */
public class Spectator extends Avatar{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5806430450266637966L;

	/**
	 * Instantiates a Spectator
	 */
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
		spriteSheetKey = "Spectator";
		super.setDead(false);
	}
	
	@Override
	public void draw(PApplet surface, long time) {
		surface.pushStyle();
		surface.textAlign(PApplet.CENTER);
		surface.fill(0);
		surface.textSize(18);
		surface.tint(255, 50);
		surface.image(GamePanel.resources.getImage(spriteSheetKey), (float)hitbox.x, (float)hitbox.y, 50, 50);
		surface.popStyle();
	}
	
	@Override
	public void dash(long time) {

	}
	
	@Override
	public boolean moveBy(double x, double y, Map map, long time) {
		if(map.inBounds(hitbox.x + x, hitbox.y + y, hitbox.width, hitbox.height)) {
			hitbox.x += x;
			hitbox.y += y;
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public AttackResult takeHit(Attack attack, long time) {
		return AttackResult.MISSED;
	}
	
	@Override
	public Attack[] basicAttack(String player, double angle, long time) {
		return null;
	}

	@Override
	public Attack[] rangedAttack(String player, double angle, long time) {
		return null;
	}

	@Override
	public Attack[] abilityOne(String player, double angle, long time) {
		return null;
	}

	@Override
	public Attack[] abilityTwo(String player, double angle, long time) {
		return null;
	}

	@Override
	public Attack[] abilityThree(String player, double angle, long time) {
		return null;
	}

	@Override
	public String toString() {
		return "Spectator";
	}
	
}

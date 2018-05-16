package gameplay.avatars;

import java.awt.Rectangle;

import clientside.gui.GamePanel;
import gameplay.attacks.Attack;
import gameplay.attacks.Attack.AttackResult;
import gameplay.attacks.Fireball;
import gameplay.attacks.MeleeAttack;
import gameplay.attacks.StatusEffect;
import gameplay.attacks.StatusEffect.Effect;
import gameplay.attacks.Trap;
import gameplay.maps.Map;
import processing.core.PApplet;

/**
 * A ranger class character 
 * @author hzhu684
 *
 */
public class Ranger extends Avatar {

	private AttackType currentAttack;
	private boolean invisible;
	private long invisStartTime, smokeTime;
	private final double invisLength = 3.5;

	/**
	 * Instantiates a Ranger
	 */
	public Ranger() {
		super();
		super.basicCD = 0.5;
		moveSpeed = 8;
		a2CD = 10;
		a1CD = 6;
		a3CD = 8;
		spriteSheetKey = "Ranger";
		sprites = new Rectangle[] { new Rectangle(92, 94, 52, 88) };
		hitbox.height = sprites[0].height;
		hitbox.width = sprites[0].width;
		dashCD = 1.2;	
		rangedCD = 0.5;
		numOfSpriteWalk = 10;
		for(int i = 1; i < 11; i++) {
			getSpriteListWalk().add("Ranger"+i);
		}
		
		getSpriteListDeath().add("Ranger");

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
	public Attack[] basicAttack(String player, double angle) {
		if (System.currentTimeMillis() > super.basicCDStart + super.basicCD * 1000 && !dashing && !blocking) {
			currentlyAttacking = true;
			basicCDStart = System.currentTimeMillis();
			currentAttack = AttackType.BASIC;
			timeActionStarted = System.currentTimeMillis();
			if(angle > 90 && angle < 270) 
				lastDir = true;
			else
				lastDir = false;
			return new Attack[] {new MeleeAttack("Knife", (int)( hitbox.x + 50 * Math.cos(Math.toRadians(angle))), (int)( hitbox.y - 75 * Math.sin(Math.toRadians(angle))), 40, 40, player, 20,
					false, new StatusEffect(Effect.NONE,0,0), angle, 0.15)};
		} else {
			return null;
		}	
	}

	// Shoot bow, arrow goes until it hits a wall
	@Override
	public Attack[] rangedAttack(String player, double angle) {
		currentAttack = AttackType.RANGED;
		currentlyAttacking = true;
		timeActionStarted = System.currentTimeMillis();
		int damage = 15;
		super.rangedCDStart = timeActionStarted;
		if(angle > 90 && angle < 270) {
			lastDir = true;
			return new Attack[] {new Fireball((int) hitbox.x-20, (int) hitbox.y-10, player, angle, "Arrow", 600, 40, 60, 14, 0.3,damage)};
		}
		else {
			lastDir = false;
			return new Attack[] {new Fireball((int) hitbox.x-30, (int) hitbox.y-10, player, angle, "Arrow", 600, 40, 60, 14, 0.3,damage)};
		}
	}


	// Barrage - fire multiple arrows in an area
	@Override
	public Attack[] abilityOne(String player, double angle) {		
		currentAttack = AttackType.A1;
		currentlyAttacking = true;
		timeActionStarted = System.currentTimeMillis();
		double damage = 10;
		super.a1CDStart = timeActionStarted;
		if(angle > 90 && angle < 270) {
			lastDir = true;
			return new Attack[] {new Fireball((int) hitbox.x-20, (int) hitbox.y-10, player, angle, "Arrow", 600, 40, 60, 30, 0.3,damage), 
					new Fireball((int) hitbox.x-20, (int) hitbox.y-10, player, angle-10, "Arrow", 600, 40, 60, 14, 0.3,damage),
					new Fireball((int) hitbox.x-20, (int) hitbox.y-10, player, angle-5, "Arrow", 600, 40, 60, 14, 0.3,damage),
					new Fireball((int) hitbox.x-20, (int) hitbox.y-10, player, angle+5, "Arrow", 600, 40, 60, 14, 0.3,damage),
					new Fireball((int) hitbox.x-20, (int) hitbox.y-10, player, angle+10, "Arrow", 600, 40, 60, 14, 0.3,damage),
					new Fireball((int) hitbox.x-20, (int) hitbox.y-10, player, angle+15, "Arrow", 600, 40, 60, 14, 0.3,damage),
					new Fireball((int) hitbox.x-20, (int) hitbox.y-10, player, angle-15, "Arrow", 600, 40, 60, 14, 0.3,damage)};
		}
		else {
			lastDir = false;
			return new Attack[] {new Fireball((int) hitbox.x-30, (int) hitbox.y-10, player, angle, "Arrow", 600, 40, 60, 14, 0.3,damage),
					new Fireball((int) hitbox.x-30, (int) hitbox.y-10, player, angle-10, "Arrow", 600, 40, 60, 14, 0.3,damage),
					new Fireball((int) hitbox.x-30, (int) hitbox.y-10, player, angle-5, "Arrow", 600, 40, 60, 14, 0.3,damage),
					new Fireball((int) hitbox.x-30, (int) hitbox.y-10, player, angle+5, "Arrow", 600, 40, 60, 14, 0.3,damage),
					new Fireball((int) hitbox.x-30, (int) hitbox.y-10, player, angle+10, "Arrow", 600, 40, 60, 14, 0.3,damage),
					new Fireball((int) hitbox.x-30, (int) hitbox.y-10, player, angle+15, "Arrow", 600, 40, 60, 14, 0.3,damage),
					new Fireball((int) hitbox.x-30, (int) hitbox.y-10, player, angle-15, "Arrow", 600, 40, 60, 14, 0.3,damage)};

		}
	}

	// PlaceTrap, place an invisible trap that expires after a certain amount of
	// time. The ranger can carry 3 at time
	@Override
	public Attack[] abilityTwo(String player, double angle) {
		currentAttack = AttackType.A2;

		a2CDStart = System.currentTimeMillis();
		timeActionStarted = a2CDStart;		
		if(angle > 90 && angle < 270) {
			lastDir = true;
		}
		else {
			lastDir = false;			
		}
		return new Attack[] {new Trap("Mushroom",(int) hitbox.x, (int) hitbox.y, 50, 50, player, 10, new StatusEffect(Effect.SLOWED, 4, 5), 60)};
	}

	// Smoke Bomb - Turns you invisible to other players for a few seconds
	@Override
	public Attack[] abilityThree(String player, double angle) {
		invisible = true;
		invisStartTime = System.currentTimeMillis();
		a3CDStart = invisStartTime;
		smokeTime = invisStartTime + 200;
		return null;

	}

	public boolean isInvisible() {
		return invisible;
	}

	public boolean isSmoke() {
		return (invisible && System.currentTimeMillis() < smokeTime);
	}

	public void draw(PApplet surface) {
		surface.pushMatrix();
		surface.pushStyle();

		drawHealthBar(surface);

		if(super.isDead()) {
			int sw, sh;
			sw = (int) sprites[spriteInd].getWidth();
			sh = (int) sprites[spriteInd].getHeight();
			
			drawDeath(numOfSpriteDeath, spriteSpeedDeath);
			if (lastDir) {
				surface.scale(-1, 1);
				surface.image(GamePanel.resources.getImage(spriteSheetKey), (float) -hitbox.x, (float) hitbox.y, -sw, sh);
			} else {
				surface.image(GamePanel.resources.getImage(spriteSheetKey), (float) hitbox.x, (float) hitbox.y, sw, sh);
			}

			surface.popMatrix();
			surface.popStyle();
			return;
		}
		else {
		int sw, sh;
		sw = (int) sprites[spriteInd].getWidth();
		sh = (int) sprites[spriteInd].getHeight();

		surface.imageMode(PApplet.CENTER);



		if (super.getStatus().getEffect().equals(Effect.STUNNED)) {
			surface.image(GamePanel.resources.getImage("Stun"), (float)hitbox.x, (float)(hitbox.y - hitbox.height * 1.1), 30, 30);
		}

		surface.pushMatrix();
		surface.pushStyle();
		float widthMod = 1f;
		if(currentlyAttacking) {
			if(currentAttack.equals(AttackType.BASIC) && System.currentTimeMillis() > timeActionStarted +  0.25 * 1000 )
				widthMod = 1.2f;
		} else {

		if(invisible && System.currentTimeMillis() < smokeTime) {
			surface.image(GamePanel.resources.getImage("Smoke"), (float) hitbox.x, (float) hitbox.y, sw * widthMod, sh);
		}
			if(invisible)
				surface.tint(10);
			if (lastDir) {
				surface.scale(-1, 1);
				surface.image(GamePanel.resources.getImage(spriteSheetKey), (float) -hitbox.x, (float) hitbox.y, -sw * widthMod, sh);
			} else {
				surface.image(GamePanel.resources.getImage(spriteSheetKey), (float) hitbox.x, (float) hitbox.y, sw * widthMod, sh);
			}
		}
		surface.popMatrix();
		surface.popStyle();
		if (blocking) {
			if (System.currentTimeMillis() / 250 % 5 == 0) {
				surface.tint(140);
			} else if (System.currentTimeMillis() / 250 % 5 == 1) {
				surface.tint(170);
			} else if (System.currentTimeMillis() / 250 % 5 == 2) {
				surface.tint(200);
			} else if (System.currentTimeMillis() / 250 % 5 == 3) {
				surface.tint(240);
			}
			surface.image(GamePanel.resources.getImage(blockImageKey), (float) hitbox.x, (float) hitbox.y, 1.5f * sw,
					1.5f * sh);
		}
		}

		// surface.rect((float)hitbox.x, (float)hitbox.y, (float)sw, (float)sh);
		// surface.fill(Color.RED.getRGB());
		// surface.ellipseMode(PApplet.CENTER);
		// surface.ellipse((float)(hitbox.x), (float)(hitbox.y), 5f, 5f);

		surface.popMatrix();
		surface.popStyle();
	}

	public void act(Map map) {

		if(currentlyAttacking) {
			if(currentAttack.equals(AttackType.BASIC))
				actBasic();
			else if(currentAttack.equals(AttackType.RANGED) || currentAttack.equals(AttackType.A1))
				actRanged();
			return;
		} else {
			super.act(map);
			if (!super.isLeft() && !super.isRight() && !super.isUp() && !super.isDown()) {
				spriteSheetKey = "WWDefault";
			}		
		}


		if(invisible) {
			if(System.currentTimeMillis() > invisStartTime + invisLength * 1000)
				invisible = false;
		}

		if (!super.isLeft() && !super.isRight() && !super.isUp() && !super.isDown()) {
			spriteSheetKey = "Ranger";
		}
	}
	@Override
	public AttackResult takeHit(Attack attack) {
		AttackResult res = super.takeHit(attack);
		if(res.equals(AttackResult.SUCCESS))
			invisible = false;
		return res;
	}
	
	@Override
	public Attack[] attack(AttackType a, String player, double angle) {
		Attack[] res = super.attack(a, player, angle);
		if(res != null)
			invisible = false;
		return res;
	}

	@Override
	public void spawn(Map map) {
		invisible = false;
		super.spawn(map);
	}

	private void actBasic() {
		if(System.currentTimeMillis() < timeActionStarted + 0.06 * 1000) {
			spriteSheetKey = "RangerBasic1";
		} else if(System.currentTimeMillis() < timeActionStarted +  0.1 * 1000 ) {
			spriteSheetKey = "RangerBasic2";
		} else if(System.currentTimeMillis() < timeActionStarted +  0.16 * 1000 ) {
			spriteSheetKey = "RangerBasic3";
		} else if(System.currentTimeMillis() < timeActionStarted +  0.3 * 1000 ) {
			spriteSheetKey = "RangerBasicEnd1";
		} else if(System.currentTimeMillis() > timeActionStarted +  0.3 * 1000 ){
			currentlyAttacking = false;
			currentAttack = AttackType.NONE;
		}
	}

	private void actRanged() {
		if(System.currentTimeMillis() < timeActionStarted + 0.03 * 1000) {
			spriteSheetKey = "RangerRanged0";
		} else if(System.currentTimeMillis() < timeActionStarted +  0.06 * 1000 ) {
			spriteSheetKey = "RangerRanged1";
		} else if(System.currentTimeMillis() < timeActionStarted +  0.09 * 1000 ) {
			spriteSheetKey = "RangerRanged2";
		} else if(System.currentTimeMillis() < timeActionStarted +  0.12 * 1000 ) {
			spriteSheetKey = "RangerRanged3";
		} else if(System.currentTimeMillis() < timeActionStarted +  0.15 * 1000 ) {
			spriteSheetKey = "RangerRanged4";
		} else if(System.currentTimeMillis() < timeActionStarted +  0.18 * 1000 ) {
			spriteSheetKey = "RangerRanged5";
		} else if(System.currentTimeMillis() < timeActionStarted +  0.21 * 1000 ) {
			spriteSheetKey = "RangerRanged6";
		} else if(System.currentTimeMillis() < timeActionStarted +  0.24 * 1000 ) {
			spriteSheetKey = "RangerRanged7";
		} else if(System.currentTimeMillis() < timeActionStarted +  0.27 * 1000 ) {
			spriteSheetKey = "RangerRanged8";
		} else if(System.currentTimeMillis() < timeActionStarted +  0.30 * 1000 ) {
			spriteSheetKey = "RangerRanged9";
		} else if(System.currentTimeMillis() > timeActionStarted +  0.31 * 1000 ){
			currentlyAttacking = false;
			currentAttack = AttackType.NONE;
		}
	}

	public String toString() {
		return "Ranger";
	}

}

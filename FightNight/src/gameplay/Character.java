package gameplay;

import java.util.ArrayList;
import java.util.Timer;

public abstract class Character{

	private double x, y;
	private double damage;
	private boolean shielded;
	private Timer attackTimer;
	private ArrayList<Hitbox> hitboxes;
	
	public Character() {
		hitboxes = new ArrayList<Hitbox>();
		x = 100;
		y = 100;
		attackTimer = new Timer();
		shielded = false;
		damage = 0;
	}
	
	public abstract void act(ArrayList<Hitbox> hitboxes);
	
	public void moveBy(double x, double y) {
		
	}
	
	public void moveTo(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
}

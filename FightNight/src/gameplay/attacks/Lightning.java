package gameplay.attacks;

import java.util.ArrayList;

public class Lightning extends Attack{

	private ArrayList<Lightning> bolts;
	boolean chained;
	
	public Lightning(String imageKey, int x, int y, int w, int h, String playerAddress, double damage,
			boolean shieldBreaker, StatusEffect effect, double dir) {
		super(imageKey, x, y, w, h, playerAddress, damage, shieldBreaker, effect, dir);
	}

}

package gameplay.attacks;

/**
 * 
 * Represents a possible StatusEffect for an Attack to apply to an Avatar
 * 
 * @author shaylandias
 *
 */
public class StatusEffect {

	public enum Effect {NONE, STUNNED, KNOCKBACK, SLOWED, POISONED};
	
	//Time the effect lasts in seconds
	private double effectTime;
	private long startTime = 0;
	private Effect effect;
	private double value;
	
	/**
	 * 
	 * Creates a StatusEffect
	 * 
	 * @param effect The type of Effect
	 * @param value The value of the Effect
	 */
	public StatusEffect(Effect effect, double value, double time) {
		this.effect = effect;
		this.value = value;
		this.effectTime = time;
	}
	
	/**
	 * 
	 * Returns the Effect
	 * 
	 * @return The Effect 
	 */
	public Effect getEffect() {
		return effect;
	}
	
	public void startEffect() {
		startTime = System.currentTimeMillis();
	}
	
	public boolean isFinished() {
		if(startTime == 0)
			return false;
		else if(System.currentTimeMillis() > startTime + effectTime * 1000)
			return true;
		else
			return false;
	}
	
	/**
	 * 
	 * Returns the value of the Effect
	 * 
	 * @return Returns the value of the Effect
	 */
	public double getValue() {
		return value;
	}
	
}

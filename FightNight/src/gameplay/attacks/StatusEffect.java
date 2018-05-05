package gameplay.attacks;

/**
 * 
 * Represents a possible StatusEffect for an Attack to apply to an Avatar
 * 
 * @author shaylandias
 *
 */
public class StatusEffect {

	public enum Effect {NONE, STUNNED, SLOWED, POISONED};
	
	private Effect effect;
	private double value;
	
	/**
	 * 
	 * Creates a StatusEffect
	 * 
	 * @param effect The type of Effect
	 * @param value The value of the Effect
	 */
	public StatusEffect(Effect effect, double value) {
		this.effect = effect;
		this.value = value;
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

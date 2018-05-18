package gameplay.attacks;

import java.io.Serializable;

/**
 * 
 * Represents a possible StatusEffect for an Attack to apply to an Avatar
 * 
 * @author shaylandias
 *
 */
public class StatusEffect implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2493887560282405253L;

	/**
	 * 
	 * Possible Effects for a StatusEffect
	 * 
	 * @author shaylandias
	 */
	public enum Effect {
		NONE, STUNNED, KNOCKBACK, SLOWED, POISONED
	};

	// Time the effect lasts in seconds
	private double effectTime;
	private long startTime = 0;
	private Effect effect;
	private double value;

	/**
	 * 
	 * Creates a StatusEffect
	 * 
	 * @param effect
	 *            The type of Effect
	 * @param value
	 *            The value of the Effect
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

	/**
	 * Starts the timer on the status effect
	 */
	public void startEffect(long time) {
		startTime = time;
	}

	/**
	 * 
	 * Returns true if the status effect has been started and has completed
	 * 
	 * @return True if finished
	 */
	public boolean isFinished(long time) {
		if (startTime <= 0.001)
			return false;
		else if (time > startTime + effectTime * 1000)
			return true;
		else
			return false;
	}

	/**
	 * 
	 * If this StatusEffect has had its timer started
	 * 
	 * @return True if started
	 */
	public boolean started() {
		if (startTime != 0)
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

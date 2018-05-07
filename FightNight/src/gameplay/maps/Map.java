package gameplay.maps;

/**
 * 
 * Stores information for the game map
 * 
 * @author shaylandias
 *
 */
public abstract class Map {
	
	/**
	 * Checks if the player is within the bounds of play
	 * @param x The x coordinate that's being checked
	 * @param y The y coordinate that's being checked
	 * @return true if it's in bounds, return false if it isn't
	 */
	public abstract boolean isInBounds(double x, double y); 
	
	//public abstract isOnTrap(double x, double y); 
}

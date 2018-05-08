package clientside;

import gameplay.avatars.Avatar;
import gameplay.avatars.Brute;

/**
 * 
 * Represents clientside player information
 * 
 * @author shaylandias
 *
 */
public class Player {

	private String playerAddress;
	private Avatar avatar;

	/**
	 * Initializes playerAddress to "" and the Avatar to a Brute
	 */
	public Player() {
		playerAddress = "";
		avatar = new Brute();
	}

	/**
	 * 
	 * Initializes Player with a certain address and Avatar
	 * 
	 * @param playerAddress
	 *            The IP address of the Player
	 * @param av
	 *            The Avatar of the Player
	 */
	public Player(String playerAddress, Avatar av) {
		super();
		this.playerAddress = playerAddress;
		if (avatar != null)
			avatar = av;
	}

	/**
	 * 
	 * Sets the Player's Avatar
	 * 
	 * @param av
	 *            The new Avatar
	 */
	public void setAvatar(Avatar av) {
		avatar = av;
	}

	/**
	 * 
	 * Gets the Player's Avatar
	 * 
	 * @return The Player's Avatar
	 */
	public Avatar getAvatar() {
		return avatar;
	}

	/**
	 * 
	 * Sets the Player's IP address
	 * 
	 * @param address
	 *            The new IP address
	 */
	public void setPlayerAddress(String address) {
		for (int i = 0; i < address.length(); i++) {
			if (!Character.isDigit(address.charAt(i))) {
				address = address.substring(i + 1);
				i--;
			} else
				break;
		}
		playerAddress = address;
		avatar.setPlayer(address);
	}

	/**
	 * 
	 * Gets the Player's IP Address as a String
	 * 
	 * @return The IP Address of this Player
	 */
	public String getPlayerAddress() {
		return playerAddress;
	}

}

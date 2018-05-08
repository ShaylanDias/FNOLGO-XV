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
	
	public Player() {
		playerAddress = "";
		avatar = new Brute();
	}
	
	public Player(String playerAddress, Avatar av) {
		super();
		this.playerAddress = playerAddress;
		if(avatar!= null)
			avatar = av;
	}
	
	public void setAvatar(Avatar av) {
		avatar = av;
	}
	
	public Avatar getAvatar() {
		return avatar;
	}

	public void setPlayerAddress(String address) {
		playerAddress = address;
	}
	
	public String getPlayerAddress() {
		return playerAddress;
	}

}

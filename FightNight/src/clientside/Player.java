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
		for(int i = 0; i < address.length(); i++) {
			if(!Character.isDigit(address.charAt(i))) {
				address = address.substring(i+1);
				i--;
			} else
				break;
			System.out.println(address);
		}
		playerAddress = address;
		avatar.setPlayer(address);
	}
	
	public String getPlayerAddress() {
		return playerAddress;
	}

}

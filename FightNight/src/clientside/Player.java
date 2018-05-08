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

	private int playerNum;
	private Avatar avatar;
	
	public Player() {
		playerNum = 0;
		avatar = new Brute();
	}
	
	public Player(int playerNum, Avatar av) {
		this.playerNum = playerNum;
		avatar = av;
	}
	
	public void setAvatar(Avatar av) {
		avatar = av;
	}
	
	public Avatar getAvatar() {
		return avatar;
	}

	public void setPlayerNum(int num) {
		playerNum = num;
	}
	
	public int getNum() {
		return playerNum;
	}

}

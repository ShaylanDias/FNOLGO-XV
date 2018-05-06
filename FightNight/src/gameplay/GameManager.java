package gameplay;

import java.util.ArrayList;

import clientside.ControlType;
import gameplay.attacks.Attack;
import gameplay.avatars.Avatar;
import gameplay.avatars.Brute;
import networking.frontend.NetworkDataObject;

/**
 * 
 * Runs the game mechanics from the server side
 * 
 * @author shaylandias
 *
 */
public class GameManager {

	private boolean runningGame;
	private GameState state;
	private ArrayList<NetworkDataObject> commands;

	public GameManager() {
		state = new GameState();
		runningGame = true;
		new Thread() {

		}.start();
	}

	public void addPlayer() {
		state.addAvatar(new Brute()); //This is a placeholder for testing
	}
	
	public void addCommand(NetworkDataObject ndo) {
		commands.add(ndo);
	}

	public void run() {

		for(int i = 0; i < state.getAttacks().size(); i++) {
			Attack p = state.getAttacks().get(i);
			if(p.isActive())
				p.act();
			else {
				state.getAttacks().remove(i);
				i--;
			}
		}
		
		for(NetworkDataObject ndo : commands) {
			if(ndo.message[0] instanceof ControlType) {
				ControlType action = (ControlType)ndo.message[0];
				Avatar avatar = null;
				int playerNum = (int)ndo.message[1];
				for(Avatar x : state.getAvatars()) {
					if(x.getPlayer() == playerNum) {
						avatar = x;
						break;
					}
				}
				if(avatar != null) {
					if(action == ControlType.DIRECTION) {
						avatar.turn((double)ndo.message[2]);
					} else if(action == ControlType.MOVEMENT) {
						avatar.moveBy((double)ndo.message[2], (double)ndo.message[3]);
					} else if(action == ControlType.ATTACK) {

					}

				}
			}
		}
	}


}

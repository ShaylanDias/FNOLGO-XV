package gameplay;

import java.util.ArrayList;

import clientside.ControlType;
import gameplay.attacks.Attack;
import gameplay.avatars.Avatar;
import gameplay.avatars.Brute;
import networking.frontend.NetworkDataObject;
import networking.frontend.NetworkListener;
import networking.frontend.NetworkMessenger;

/**
 * 
 * Runs the game mechanics from the server side
 * 
 * @author shaylandias
 *
 */
public class GameManager implements NetworkListener{

	private boolean runningGame;
	private GameState state;
	private ArrayList<NetworkDataObject> commands;

	public GameManager() {
		state = new GameState();
		runningGame = true;
		commands = new ArrayList<NetworkDataObject>();
	}

	public GameState getState() {
		return state;
	}

	public void addPlayer() {
		state.addAvatar(new Brute()); //This is a placeholder for testing
	}

	public void addCommand(NetworkDataObject ndo) {
		synchronized(commands) {
			commands.add(ndo);
		}
	}

	public void run() {

		synchronized(state) {
			for(int i = 0; i < state.getAttacks().size(); i++) {
				Attack p = state.getAttacks().get(i);
				if(p.isActive())
					p.act();
				else {
					state.getAttacks().remove(i);
					i--;
				}
			}
		}
		synchronized(commands) {
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
//							avatar.turn((double)ndo.message[2]);
						} else if(action == ControlType.MOVEMENT) {
							System.out.println("move: " + ndo.message[2] + " " + ndo.message[3]);
							avatar.moveBy((double)ndo.message[2], (double)ndo.message[3]);
						} else if(action == ControlType.ATTACK) {

						}

					}
				}
			}
			commands.clear();
		}
	}

	@Override
	public void connectedToServer(NetworkMessenger nm) {

	}

	@Override
	public void networkMessageReceived(NetworkDataObject ndo) {
		if(ndo.message.length > 0 && ndo.message[0] instanceof ControlType)
			addCommand(ndo);
	}


}

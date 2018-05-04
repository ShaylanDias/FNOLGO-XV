package gameplay;

import java.util.ArrayList;

import clientside.ControlType;
import networking.frontend.NetworkDataObject;

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

	public void addCommand(NetworkDataObject ndo) {
		commands.add(ndo);
	}

	public void run() {

		for(int i = 0; i < state.getProjectiles().size(); i++) {
			Projectile p = state.getProjectiles().get(i);
			if(p.isActive())
				p.act();
			else {
				state.getProjectiles().remove(i);
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

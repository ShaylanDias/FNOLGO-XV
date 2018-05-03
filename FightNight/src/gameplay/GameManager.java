package gameplay;

import clientside.ControlType;
import networking.frontend.NetworkDataObject;

public class GameManager {

	private GameState state;

	public GameManager() {
		state = new GameState();
	}

	public void act(NetworkDataObject ndo) {
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

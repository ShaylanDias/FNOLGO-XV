package gameplay;

import java.util.ArrayList;

import clientside.ControlType;
import gameplay.attacks.Attack;
import gameplay.avatars.Avatar;
import gameplay.avatars.Avatar.AttackType;
import gameplay.avatars.Spectator;
import networking.frontend.NetworkDataObject;
import networking.frontend.NetworkListener;
import networking.frontend.NetworkMessenger;

/**
 * 
 * Runs the game mechanics from the server side, Listens to the network from the
 * server
 * 
 * @author shaylandias
 *
 */
public class GameManager implements NetworkListener {

	private GameState state;
	private ArrayList<NetworkDataObject> commands;
	private boolean gameEnded;
	private String winner;

	/**
	 * Initializes a GameManager
	 */
	public GameManager() {
		state = new GameState();
		gameEnded = false;
		commands = new ArrayList<NetworkDataObject>();
		gameEnded = false;
		winner = "";
	}

	/**
	 * 
	 * Returns the state of the game to be drawn
	 * 
	 * @return The Game State
	 */
	public GameState getState() {
		return state;
	}

	// public void addPlayer() {
	// state.addAvatar(new Brute()); // This is a placeholder for testing
	// }

	/**
	 * Runs one step of the game mechanics
	 */
	public void run() {

		if(state != null)
			state.setGameTime(System.currentTimeMillis());
		
		synchronized (commands) {
			for (NetworkDataObject ndo : commands) {
				if (ndo.message[0] instanceof ControlType) {
					ControlType action = (ControlType) ndo.message[0];
					Avatar avatar = null;
					String playerNum = ndo.getSourceIP();
					for (Avatar x : state.getAvatars()) {
						if (x.getPlayer().equals(playerNum)) {
							avatar = x;
							break;
						}
					}
					if (avatar != null) {
						if (action == ControlType.MOVEMENT && avatar.isMoveControlled()) {

							char dir = (char) ndo.message[1];
							boolean dir1 = (boolean) ndo.message[2];
							if (dir == 'w')
								avatar.setUp(dir1);
							else if (dir == 'a')
								avatar.setLeft(dir1);
							else if (dir == 's')
								avatar.setDown(dir1);
							else if (dir == 'd')
								avatar.setRight(dir1);

						} else if (action == ControlType.ATTACK) {
							if (ndo.message[1] == AttackType.BASIC) {
								state.addAttacks(avatar.attack(AttackType.BASIC, playerNum, (double) ndo.message[2], state.getGameTime()));
							} else if(ndo.message[1] == AttackType.A1) {
								state.addAttacks(avatar.attack(AttackType.A1, playerNum, (double) ndo.message[2], state.getGameTime()));
							} else if(ndo.message[1] == AttackType.RANGED) {
								state.addAttacks(avatar.attack(AttackType.RANGED, playerNum, (double) ndo.message[2], state.getGameTime()));
							} else if(ndo.message[1] == AttackType.A2) {
								state.addAttacks(avatar.attack(AttackType.A2, playerNum, (double) ndo.message[2], state.getGameTime()));
							} else if(ndo.message[1] == AttackType.A3) {
								state.addAttacks(avatar.attack(AttackType.A3, playerNum, (double) ndo.message[2], state.getGameTime()));
							}

						} else if (action == ControlType.DASH) {
							avatar.dash(state.getGameTime());
						} else if (action == ControlType.BLOCK) {
							avatar.block((boolean) ndo.message[1]);
						}

					}
				}
			}
			commands.clear();
		}

		synchronized (state) {
			for (int i = 0; i < state.getAttacks().size(); i++) {
				Attack p = state.getAttacks().get(i);
				if (p == null || !p.act(state.getAvatars(), state.getGameTime())) {
					state.getAttacks().remove(i);
					i--;
				}
			}
			for (int i = 0; i < state.getAvatars().size(); i++) {
				Avatar x = state.getAvatars().get(i);
				if(x.isEliminated() && !(x instanceof Spectator)) {
					state.getAvatars().remove(x);
					Avatar y = new Spectator();
					y.setPlayer(x.getPlayer());
					state.addAvatar(y);
				}
				x.act(state.getMap(), state.getGameTime());
			}
			
			Avatar av = null;
			if(state.getAvatars().size() > 1) {
				int spectators = 0;
				for(Avatar x : state.getAvatars()) {
					if(x instanceof Spectator) {
						spectators++;
					} else
						av = x;
				}
				if(spectators >= state.getAvatars().size()-1) {
					winner = av.getPlayer();
					gameEnded = true;
				}
			}
			
//			int x = state.getAvatars().size();
//			int spectators = 0;
//			for(Avatar a : state.getAvatars()) {
//				if(a instanceof Spectator)
//					x--;
//				spectators ++;
//			}
//			if(x > 1 && total > state.getAvatars().size() - 1 - spectators) {
//				 for(int i = 0; i < state.getAvatars().size(); i++) {
//					 if(!(state.getAvatars().get(i) instanceof Spectator) && !state.getAvatars().get(i).isEliminated()) {
//						 winner = state.getAttacks().get(i).getPlayer();
//						 break;
//					 }
//				 }
//				gameEnded = true;
//			}

		}

	}

	@Override
	public void connectedToServer(NetworkMessenger nm) {

	}

	/**
	 * Adds a NetworkDataObject to the list of commands to be run if it contains a
	 * ControlType
	 */
	@Override
	public void networkMessageReceived(NetworkDataObject ndo) {

		if (ndo.message.length > 0) {
			if (ndo.message[0] instanceof ControlType) {
				addCommand(ndo);
			}
		}
		if (ndo.message.length > 0 && ndo.message[0] instanceof String) {
			if (((String) ndo.message[0]).equals("INTIALIZATION")) {
				state.addAvatar((Avatar) ndo.message[1]);
			}
		}
	}

	// public void addPlayer() {
	// state.addAvatar(new Brute()); // This is a placeholder for testing
	// }

	private void addCommand(NetworkDataObject ndo) {
		synchronized (commands) {
			commands.add(ndo);
		}
	}

	/**
	 * 
	 * If this player won
	 * 
	 * @return True if won
	 */
	public String getWinner() {
		return winner;
	}
	
	/**
	 * 
	 * Has the game ended
	 * 
	 * @return True if ended
	 */
	public boolean isGameEnded() {
		return gameEnded;
	}
	
}

package clientside.gui;

import networking.frontend.NetworkManagementPanel;

/**
 * 
 * Contains main method
 * 
 * @author bgu307
 *
 */
public class Main {

	/*
	 * This needs to give the player a choice of hosting the game or setting up a
	 * server. From there this will let them either join a game or wait for someone
	 * to join and then start the game, adding the client to the server and then
	 * starting the GameManager on the host's side
	 */
	/**
	 * 
	 * Runs the Program
	 * 
	 * @param args
	 *            Args
	 */
	public static void main(String[] args) {
		GamePanel game = new GamePanel(false);
		NetworkManagementPanel nmp = new NetworkManagementPanel("Chat", 16, game);

	}

}

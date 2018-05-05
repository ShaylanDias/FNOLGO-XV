package clientside.gui;

import clientside.ControlType;
import clientside.Player;
import gameplay.GameState;
import gameplay.avatars.Avatar;
import networking.frontend.NetworkDataObject;
import networking.frontend.NetworkListener;
import networking.frontend.NetworkMessenger;
import processing.core.PApplet;

public class GamePanel extends PApplet implements NetworkListener{

	private Player player;
	private NetworkMessenger nm;

	
	private GameState currentState = null;
	
	
	public void draw() {
		clear();

		if(currentState != null)
			currentState.draw(this);
		//Starting Setup
		stroke(0, 0, 0);

		mousePressed();

		//Calls to draw

	}

	public void mousePressed() {
		
	}

	public void mouseMoved() {
		double angle = 0;
		Avatar av = null;
		for(Avatar x : currentState.getAvatars()) {
			if(x.getPlayer() == player.getNum()) {
				av = x;
				break;
			}
		}
		
		if(av != null) {
			angle = Math.atan((mouseY-av.getY())/(mouseX-av.getX()));
		}
		
		nm.sendMessage(NetworkDataObject.MESSAGE, new Object[] {ControlType.DIRECTION, angle});
	}

	public void keyPressed(){
		if(key == CODED) {
			
		}
		if(key == 'a') {
			/*
			 * Send a "Message" NetworkDataObject to the server with message array in format:
			 * [ControlType, arg, arg, ...]
			 */
		}
			
	}

	@Override
	public void connectedToServer(NetworkMessenger nm) {
		this.nm = nm;		
	}

	@Override
	public void networkMessageReceived(NetworkDataObject ndo) {
		if (ndo.messageType.equals(NetworkDataObject.MESSAGE)) {
			if(ndo.message[0] != null && ndo.message[0] instanceof GameState)
				currentState = (GameState) ndo.message[0];
		}
		else if (ndo.messageType.equals(NetworkDataObject.HANDSHAKE)) {
			System.out.println("\n" + ndo.dataSource + " connected. ");
		}
		else if (ndo.messageType.equals(NetworkDataObject.DISCONNECT)) {
			if (ndo.dataSource.equals(ndo.serverHost)) {
				System.out.println("Disconnected from server " + ndo.serverHost);
			}
			else {
				System.out.println("Disconected from server");
			}
		}		
	}
	
}

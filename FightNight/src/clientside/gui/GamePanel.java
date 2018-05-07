package clientside.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import clientside.ControlType;
import clientside.Player;
import clientside.Resources;
import gameplay.GameState;
import gameplay.avatars.Avatar;
import gameplay.avatars.Avatar.AttackType;
import networking.frontend.NetworkDataObject;
import networking.frontend.NetworkListener;
import networking.frontend.NetworkMessenger;
import processing.awt.PSurfaceAWT;
import processing.core.PApplet;

public class GamePanel extends PApplet implements NetworkListener {

	public static Resources resources = new Resources();

	// JPanel stuff
	private JFrame window;

	private Player player;
	private NetworkMessenger nm;

	private boolean isConnected;

	private GameState currentState = null;

	/**
	 * Initializes the GamePanel window
	 */
	public GamePanel(boolean isHost) {
		// Setting up the window
		PApplet.runSketch(new String[] { "" }, this);
		PSurfaceAWT surf = (PSurfaceAWT) this.getSurface();
		PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();

		JFrame window = (JFrame) canvas.getFrame();
		window.setSize(1200, 800);
		window.setLocation(100, 50);
		window.setMinimumSize(new Dimension(600, 400));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);

		window.setVisible(true);

		player = new Player();

	}

	public void setup() {
		// We are gonna load images before doing anything else on the clientside
		resources.loadImages(this);
	}

	public void draw() {
		clear();
		background(Color.WHITE.getRGB());

		color(Color.BLACK.getRGB());

		if (currentState != null) {
			currentState.draw(this);
		}

		// Starting Setup
		stroke(0, 0, 0);

		mousePressed();

	}

	public void mousePressed() {
		if(nm != null)
			nm.sendMessage(NetworkDataObject.MESSAGE, ControlType.ATTACK, player.getNum(), AttackType.BASIC, getAngleToMouse());

	}

	// Should change this so it sends the angle when it sends an attack command,
	// because constant sending creates lag
	public void mouseMoved() {

	}

	public void keyPressed() {
		/*
		 * Send a "Message" NetworkDataObject to the server with message array in
		 * format: [ControlType, arg, arg, ...]
		 */
		if (key == CODED) {

		}
		if (key == 'a') { // Set boolean in character to true
			nm.sendMessage(NetworkDataObject.MESSAGE, ControlType.MOVEMENT, player.getNum(), 'a', true);
		}
		if (key == 'w') {
			nm.sendMessage(NetworkDataObject.MESSAGE, ControlType.MOVEMENT, player.getNum(), 'w', true);
		}
		if (key == 's') {
			nm.sendMessage(NetworkDataObject.MESSAGE, ControlType.MOVEMENT, player.getNum(), 's', true);
		}
		if (key == 'd') {
			nm.sendMessage(NetworkDataObject.MESSAGE, ControlType.MOVEMENT, player.getNum(), 'd', true);
		}

	}

	public void keyReleased() {
		if (key == 'a') { // Set boolean in character to true
			nm.sendMessage(NetworkDataObject.MESSAGE, ControlType.MOVEMENT, player.getNum(), 'a', false);
		}
		if (key == 'w') {
			nm.sendMessage(NetworkDataObject.MESSAGE, ControlType.MOVEMENT, player.getNum(), 'w', false);
		}
		if (key == 's') {
			nm.sendMessage(NetworkDataObject.MESSAGE, ControlType.MOVEMENT, player.getNum(), 's', false);
		}
		if (key == 'd') {
			nm.sendMessage(NetworkDataObject.MESSAGE, ControlType.MOVEMENT, player.getNum(), 'd', false);
		}
	}

	@Override
	public void connectedToServer(NetworkMessenger nm) {
		this.nm = nm;
	}

	@Override
	public void networkMessageReceived(NetworkDataObject ndo) {
		if (ndo.messageType.equals(NetworkDataObject.MESSAGE)) {
			if (ndo.message[0] != null && ndo.message[0] instanceof GameState) {
				currentState = (GameState) ndo.message[0];
			}
			if (ndo.message[0] instanceof String) {
				//System.out.println(ndo.message[0]);
			}

		} else if (ndo.messageType.equals(NetworkDataObject.HANDSHAKE)) {
			System.out.println("\n" + ndo.dataSource + " connected. ");
		} else if (ndo.messageType.equals(NetworkDataObject.DISCONNECT)) {
			if (ndo.dataSource.equals(ndo.serverHost)) {
				System.out.println("Disconnected from server " + ndo.serverHost);
			} else {
				System.out.println("Disconected from server");
			}
		}
	}

	public boolean isConnected() {
		return isConnected;
	}

	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}

	private double getAngleToMouse() {
		double angle = 0;
		Avatar av = null;
		if (currentState != null) {
			for (Avatar x : currentState.getAvatars()) {
				if (x.getPlayer() == player.getNum()) {
					av = x;
					break;
				}
			}
		}
		if (av != null) {
			double centerX = av.getX();
			double centerY = av.getY();

		    angle =  (float) Math.toDegrees(Math.atan2(centerY - mouseY, mouseX-centerX));
		    if(angle < 0) {
		    		angle += 360;
		    }
		}
		return angle;
	}

}

package clientside.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Window;

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

	/**
	 * Contains the images to be preloaded and available
	 */
	public static Resources resources = new Resources();

	private Player player;
	private NetworkMessenger nm;

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

	/**
	 * Loads the images to resources
	 */
	public void setup() {
		// We are gonna load images before doing anything else on the clientside
		resources.loadImages(this);
	}

	/**
	 * Draws the game to the GamePanel
	 */
	public void draw() {
		clear();

		background(Color.WHITE.getRGB());

		color(Color.BLACK.getRGB());

		if (currentState != null) {
			pushMatrix();
			Avatar av = null;
			for (Avatar x : currentState.getAvatars()) {
				if (x.getPlayer().equals(player.getPlayerAddress())) {
					av = x;
					break;
				}
			}
			//Translating to center the Avatar
			translate((float)(-av.getX() + width/2), (float)-av.getY() + height/2);
			currentState.draw(this);
			popMatrix();
		}

		// Starting Setup
		stroke(0, 0, 0);

		mousePressed();

	}

	/**
	 * Detects mouse clicks to trigger abilities
	 */
	public void mousePressed() {
		if (nm != null) {
			if (mouseButton == LEFT)
				nm.sendMessage(NetworkDataObject.MESSAGE, ControlType.ATTACK, AttackType.BASIC, getAngleToMouse());
		}
	}

	/**
	 * Detects key presses to control the character, does not work properly on Mac
	 */
	public void keyPressed() {
		/*
		 * Send a "Message" NetworkDataObject to the server with message array in
		 * format: [ControlType, arg, arg, ...]
		 */
		if (key == CODED) {

		}

		if (key == ' ') {
			nm.sendMessage(NetworkDataObject.MESSAGE, ControlType.DASH, getAngleToMouse());
		}
		else if (key == 'q') {
			nm.sendMessage(NetworkDataObject.MESSAGE, ControlType.BLOCK, true);
		}
		
		if (key == 'a') {
			nm.sendMessage(NetworkDataObject.MESSAGE, ControlType.MOVEMENT, 'a', true);
		}
		if (key == 'w') {
			nm.sendMessage(NetworkDataObject.MESSAGE, ControlType.MOVEMENT, 'w', true);
		}
		if (key == 's') {
			nm.sendMessage(NetworkDataObject.MESSAGE, ControlType.MOVEMENT, 's', true);
		}
		if (key == 'd') {
			nm.sendMessage(NetworkDataObject.MESSAGE, ControlType.MOVEMENT, 'd', true);
		}

	}

	/**
	 * Detects key releases to trigger character abilities
	 */
	public void keyReleased() {
		if (key == 'a') { // Set boolean in character to true
			nm.sendMessage(NetworkDataObject.MESSAGE, ControlType.MOVEMENT, 'a', false);
		}
		if (key == 'w') {
			nm.sendMessage(NetworkDataObject.MESSAGE, ControlType.MOVEMENT, 'w', false);
		}
		if (key == 's') {
			nm.sendMessage(NetworkDataObject.MESSAGE, ControlType.MOVEMENT, 's', false);
		}
		if (key == 'd') {
			nm.sendMessage(NetworkDataObject.MESSAGE, ControlType.MOVEMENT, 'd', false);
		}
		if (key == 'q') {
			nm.sendMessage(NetworkDataObject.MESSAGE, ControlType.BLOCK, false);
		}
	}

	@Override
	public void connectedToServer(NetworkMessenger nm) {
		this.nm = nm;
		player.setPlayerAddress(nm.getHost().toString());
	}

	@Override
	public void networkMessageReceived(NetworkDataObject ndo) {
		if (ndo.messageType.equals(NetworkDataObject.MESSAGE)) {
			if (ndo.message[0] != null) {
				if (ndo.message[0] instanceof GameState) {
					currentState = (GameState) ndo.message[0];
				}
				// if (ndo.message[0] instanceof String) {
				//
				// }
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

	/**
	 * Sends the connect message to add this player's Avatar to the server
	 */
	public void sendConnectInit() {
		if (nm != null) {
			nm.sendMessage(NetworkDataObject.MESSAGE, "INTIALIZATION", player.getAvatar());
		}
	}

	private double getAngleToMouse() {
		double angle = 0;
		Avatar av = null;


		if (currentState != null) {
			for (Avatar x : currentState.getAvatars()) {
				if (x.getPlayer().equals(player.getPlayerAddress())) {
					av = x;
					break;
				}
			}
		}
		if (av != null) {

			//Untranslating coords
			double x = mouseX + av.getX() - width/2;
			double y = mouseY + av.getY() - height/2;

			double centerX = av.getX();
			double centerY = av.getY();

			angle = (float) Math.toDegrees(Math.atan2(centerY - y, x - centerX));
			if (angle < 0) {
				angle += 360;
			}
		}
		return angle;
	}

}

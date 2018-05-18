package clientside.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JFrame;

import clientside.ControlType;
import clientside.Player;
import clientside.Resources;
import gameplay.GameState;
import gameplay.avatars.Avatar;
import gameplay.avatars.Avatar.AttackType;
import gameplay.avatars.Brute;
import gameplay.avatars.Mage;
import gameplay.avatars.Ranger;
import gameplay.maps.Map;
import gameplay.maps.StandardMap;
import networking.frontend.NetworkDataObject;
import networking.frontend.NetworkListener;
import networking.frontend.NetworkMessenger;
import processing.awt.PSurfaceAWT;
import processing.core.PApplet;

/**
 * This class draws the game, takes input, and communicates with the server. It
 * additionally contains a method to calculate the angle of the mouse position
 * relatvie to the avatar
 * 
 * @author shaylandias, bgu307
 *
 */
public class GamePanel extends PApplet implements NetworkListener {

	/**
	 * Contains the images to be preloaded and available
	 */
	public static Resources resources = new Resources();

	private Player player;
	private Map map;
	private NetworkMessenger nm;
	private boolean connected;
	private boolean gameEnded;
	private boolean won;
	private JFrame window;

	private Rectangle brute, ranger, mage;

	private GameState currentState = null;

	/**
	 * Initializes the GamePanel window
	 */
	public GamePanel(boolean isHost) {
		// Setting up the window
		super();
		PApplet.runSketch(new String[] { "" }, this);
		PSurfaceAWT surf = (PSurfaceAWT) this.getSurface();
		PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();

		window = (JFrame) canvas.getFrame();
		window.setSize(1200, 800);
		window.setLocation(100, 50);
		window.setMinimumSize(new Dimension(600, 400));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		window.setVisible(true);
		player = new Player();
		map = new StandardMap();
		brute = new Rectangle(100, 100, 100, 100);
		mage = new Rectangle(210, 100, 100, 100);
		ranger = new Rectangle(320, 100, 100, 100);
		won = false;
		gameEnded = false;
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

		if (!connected) {

			if (!gameEnded) {
				fill(255);
				textAlign(CENTER);
				textSize(40);
				text("Waiting For Connection", width / 2, height / 2);
			}


		} else {
			
			System.out.println("draw called: " + System.currentTimeMillis());
			
			imageMode(CENTER);
			background(Color.BLACK.getRGB());

			color(Color.BLACK.getRGB());

			if (currentState != null) { // gets the avatar information from the currentState
				pushMatrix();
				Avatar av = null;
				map = currentState.getMap(); // gets the map that's made on the server.
				for (Avatar x : currentState.getAvatars()) {
					if (x.getPlayer().equals(player.getPlayerAddress())) {
						av = x;
						break;
					}

				}

				currentState.draw(this, av, width, height, player.getPlayerAddress(), currentState.getGameTime());
				

				
				if(gameEnded) {
					String text = "";
					if(won)
						text = "WINNER!";
					else
						text = "You lost lmao";
					text("WINNER!", (float)av.getX(), (float)av.getY() - 100f);
				}

				
				drawCooldowns(this, av);
				popMatrix();
			}
		}
	}

	/**
	 * Detects mouse clicks to trigger abilities
	 */
	public void mouseClicked() {

		if (!connected) {

			// if(brute.contains(new Point(mouseX, mouseY))) {
			// player.setAvatar(new Brute());
			// }
			// else if(mage.contains(new Point(mouseX, mouseY)))
			// player.setAvatar(new Mage());
			// else if(ranger.contains(new Point(mouseX, mouseY)))
			// player.setAvatar(new Ranger());

		} else {

			if (nm != null) {
				if (mouseButton == LEFT)
					nm.sendMessage(NetworkDataObject.MESSAGE, ControlType.ATTACK, AttackType.BASIC, getAngleToMouse());
				else if (mouseButton == RIGHT) {
					nm.sendMessage(NetworkDataObject.MESSAGE, ControlType.ATTACK, AttackType.RANGED, getAngleToMouse());
				}
			}
		}
	}

	public void mouseDragged() {
		if (!connected) {

			// if(brute.contains(new Point(mouseX, mouseY))) {
			// player.setAvatar(new Brute());
			// }
			// else if(mage.contains(new Point(mouseX, mouseY)))
			// player.setAvatar(new Mage());
			// else if(ranger.contains(new Point(mouseX, mouseY)))
			// player.setAvatar(new Ranger());

		} else {

			if (nm != null) {
				if (mouseButton == LEFT)
					nm.sendMessage(NetworkDataObject.MESSAGE, ControlType.ATTACK, AttackType.BASIC, getAngleToMouse());
				else if (mouseButton == RIGHT) {
					nm.sendMessage(NetworkDataObject.MESSAGE, ControlType.ATTACK, AttackType.RANGED, getAngleToMouse());
				}
			}
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

		if (currentState != null) {
			if (key == CODED) {

			}

			if (key == ' ') {
				nm.sendMessage(NetworkDataObject.MESSAGE, ControlType.DASH, getAngleToMouse());
			} else if (key == 'q') {
				nm.sendMessage(NetworkDataObject.MESSAGE, ControlType.BLOCK, true);
			}

			if (key == 'a') {
				nm.sendMessage(NetworkDataObject.MESSAGE, ControlType.MOVEMENT, 'a', true);
				nm.sendMessage(NetworkDataObject.MESSAGE, ControlType.MOVEMENT, 'd', false);
			}
			if (key == 'w') {
				nm.sendMessage(NetworkDataObject.MESSAGE, ControlType.MOVEMENT, 'w', true);
				nm.sendMessage(NetworkDataObject.MESSAGE, ControlType.MOVEMENT, 's', false);
			}
			if (key == 's') {
				nm.sendMessage(NetworkDataObject.MESSAGE, ControlType.MOVEMENT, 's', true);
				nm.sendMessage(NetworkDataObject.MESSAGE, ControlType.MOVEMENT, 'w', false);
			}
			if (key == 'd') {
				nm.sendMessage(NetworkDataObject.MESSAGE, ControlType.MOVEMENT, 'd', true);
				nm.sendMessage(NetworkDataObject.MESSAGE, ControlType.MOVEMENT, 'a', false);
			}
			if (key == 'e') {
				nm.sendMessage(NetworkDataObject.MESSAGE, ControlType.ATTACK, AttackType.A1, getAngleToMouse());
			}
			if (key == 'r') {
				nm.sendMessage(NetworkDataObject.MESSAGE, ControlType.ATTACK, AttackType.A2, getAngleToMouse());
			}
			if (key == 'f') {
				nm.sendMessage(NetworkDataObject.MESSAGE, ControlType.ATTACK, AttackType.A3, getAngleToMouse());
			}
		}
	}

	/**
	 * Detects key releases to trigger character abilities
	 */
	public void keyReleased() {

		if (currentState != null) {
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
	}

	@Override
	public void connectedToServer(NetworkMessenger nm) {
		this.nm = nm;
		gameEnded = false;
		won = false;
		player.setPlayerAddress(nm.getHost().toString());
	}

	@Override
	public void networkMessageReceived(NetworkDataObject ndo) {
		if (ndo.messageType.equals(NetworkDataObject.MESSAGE)) {
			if (ndo.message[0] != null) {
				if (ndo.message[0] instanceof GameState) {
					System.out.println("gamestate received: " + System.currentTimeMillis());
					currentState = (GameState) ndo.message[0];
				} else if (ndo.message[0] instanceof String) {
					if (ndo.message[0].equals("ENDED")) {
						gameEnded = true;
						if (player.getPlayerAddress().equals(ndo.message[1])) {
							won = true;
						}
					}
				}
			}

		} else if (ndo.messageType.equals(NetworkDataObject.HANDSHAKE)) {
			System.out.println("\n" + ndo.dataSource + " connected. ");
			setConnected(true);
		} else if (ndo.messageType.equals(NetworkDataObject.DISCONNECT)) {
			if (ndo.dataSource.equals(ndo.serverHost)) {
				System.out.println("Disconnected from server " + ndo.serverHost);
			} else {
				System.out.println("Disconected from server");
			}
			setConnected(false);
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

			// Untranslating coords
			double x = mouseX + av.getX() - width / 2;
			double y = mouseY + av.getY() - height / 2;

			double centerX = av.getX();
			double centerY = av.getY();

			angle = (float) Math.toDegrees(Math.atan2(centerY - y, x - centerX));
			if (angle < 0) {
				angle += 360;
			}
		}
		return angle;
	}

	private void drawCooldowns(PApplet surface, Avatar a) {
		surface.pushStyle();
		surface.ellipseMode(CENTER);
		surface.textAlign(CENTER);
		surface.textSize(16);

		double refX = a.getX();
		double refY = a.getY() + height / 2 - 60;

		drawTimer(surface, refX - 250, refY, a.getDashCooldownLeft(currentState.getGameTime()), a.getDashCooldown(), "DASH");
		drawTimer(surface, refX - 150, refY, a.getBasicCooldownLeft(currentState.getGameTime()), a.getBasicCooldown(), "BASIC");
		drawTimer(surface, refX - 50, refY, a.getRangedCooldownLeft(currentState.getGameTime()), a.getRangedCooldown(), "RANGED");
		drawTimer(surface, refX + 50, refY, a.getA1CooldownLeft(currentState.getGameTime()), a.getA1Cooldown(), "E");
		drawTimer(surface, refX + 150, refY, a.getA2CooldownLeft(currentState.getGameTime()), a.getA2Cooldown(), "R");
		drawTimer(surface, refX + 250, refY, a.getA3CooldownLeft(currentState.getGameTime()), a.getA3Cooldown(), "F");

		surface.popStyle();
	}

	public void setConnected(boolean connect) {
		connected = connect;
	}

	private void drawTimer(PApplet surface, double x, double y, long cdLeft, double cd, String name) {
		if (cdLeft < cd * 1000) {
			double percent = cdLeft / (cd * 1000);
			double angle = 2 * Math.PI * percent + 3 * Math.PI / 2;
			surface.fill(Color.BLACK.getRGB());
			surface.ellipse((float) (x), (float) (y), 100, 100);
			surface.fill(Color.GREEN.getRGB());
			surface.arc((float) (x), (float) (y), 100, 100, 3f * (float) Math.PI / 2, (float) angle, PIE);

		} else {
			surface.fill(Color.GREEN.getRGB());
			surface.ellipse((float) (x), (float) (y), 100, 100);
		}
		surface.fill(Color.RED.getRGB());
		surface.text(name, (float) x, (float) (y + 6));

	}

	public Player getPlayer() {
		return player;
	}

	public JFrame getFrame() {
		return window;
	}

	// public void runMe() {
	// super.setSize(800,600);
	//// super.sketchPath();
	//// super.initSurface();
	//// super.surface.startThread();
	//// System.out.println("init");
	// }
}

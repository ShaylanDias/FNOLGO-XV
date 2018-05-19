package clientside;

import java.util.Collection;
import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * 
 * Class that stores and preloads all of the images used by this class
 * 
 * @author shaylandias
 *
 */
public class Resources {

	private HashMap<String, ImageWrapper> images = new HashMap<String, ImageWrapper>();

	/**
	 * Initializes a resource object with all of the images in the game
	 */
	public Resources() {
		// Add all of the necessary images to HashMap
		images.put("Fireball", new ImageWrapper("Fireball.png"));

		// Brute
		images.put("WWDefault", new ImageWrapper("data/Brute/WW-Default.png"));
		images.put("WWWalk0", new ImageWrapper("data/Brute/WW-Walk1.png"));
		images.put("WWWalk1", new ImageWrapper("data/Brute/WW-Walk2.png"));
		images.put("WWWalk2", new ImageWrapper("data/Brute/WW-Walk3.png"));
		images.put("WWWalk3", new ImageWrapper("data/Brute/WW-Walk4.png"));
		images.put("WWDying", new ImageWrapper("data/Brute/WWDying.png"));
		images.put("WWDead", new ImageWrapper("data/Brute/WWDead.png"));
		images.put("UpperCut", new ImageWrapper("data/Brute/UpperCut.png"));
		images.put("UpperCut1", new ImageWrapper("data/Brute/UpperCut1.png"));
		images.put("UpperCut2", new ImageWrapper("data/Brute/UpperCut2.png"));
		images.put("UpperCut3", new ImageWrapper("data/Brute/UpperCut3.png"));
		images.put("UpperCut4", new ImageWrapper("data/Brute/UpperCut4.png"));
		images.put("UpperCut5", new ImageWrapper("data/Brute/UpperCut5.png"));
		images.put("UpperCut6", new ImageWrapper("data/Brute/UpperCut6.png"));
		images.put("UpperCut7", new ImageWrapper("data/Brute/UpperCut7.png"));
		images.put("UpperCut8", new ImageWrapper("data/Brute/UpperCut8.png"));
		images.put("Rock", new ImageWrapper("data/Brute/Rock.png"));
		images.put("WWBasic", new ImageWrapper("data/Brute/WWBasic.png"));
		images.put("Howl", new ImageWrapper("data/Brute/Howl.png"));
		images.put("Howl1", new ImageWrapper("data/Brute/Howl1.png"));
		images.put("Howl2", new ImageWrapper("data/Brute/Howl2.png"));
		images.put("Howl3", new ImageWrapper("data/Brute/Howl3.png"));
		images.put("Howl4", new ImageWrapper("data/Brute/Howl4.png"));
		images.put("Stun", new ImageWrapper("data/Stun.png"));

		// Mage 
		images.put("Mage", new ImageWrapper("data/Mage/Mage.png"));
		for (int i = 1; i < 9; i++) {
			images.put("Mage" + i, new ImageWrapper("data/Mage/Mage" + i + ".png"));
		}
		for (int i = 1; i < 8; i++) {
			images.put("MageDeath" + i, new ImageWrapper("data/Mage/MageDeath" + i + ".png"));
		}
		images.put("Lightning", new ImageWrapper("data/Mage/Lightning1.png"));
		images.put("Lightning1", new ImageWrapper("data/Mage/Lightning2.png"));
		images.put("Lightning2", new ImageWrapper("data/Mage/Lightning3.png"));
		images.put("Lightning3", new ImageWrapper("data/Mage/Lightning4.png"));
		images.put("Lightning4", new ImageWrapper("data/Mage/Lightning5.png"));
		images.put("Lightning5", new ImageWrapper("data/Mage/Lightning6.png"));
		images.put("SnowField", new ImageWrapper("data/Mage/SnowField.png"));
		images.put("MageBasic", new ImageWrapper("data/Mage/Basic.png"));

		// Ranger
		images.put("Ranger", new ImageWrapper("data/Ranger/Ranger.png"));
		for (int i = 1; i < 11; i++) {
			images.put("Ranger" + i, new ImageWrapper("data/Ranger/Ranger" + i + ".png"));
		}
		images.put("Arrow", new ImageWrapper("data/Ranger/Arrow.png"));
		images.put("Mushroom", new ImageWrapper("data/Ranger/MushroomTrap.png"));
		images.put("Knife", new ImageWrapper("data/Ranger/KnifeSlash.png"));
		images.put("Smoke", new ImageWrapper("data/Ranger/Smoke.png"));
		for (int i = 0; i < 10; i++) {
			images.put("RangerRanged" + i, new ImageWrapper("data/Ranger/Ranged" + i + ".png"));
		}
		for (int i = 1; i < 4; i++) {
			images.put("RangerBasic" + i, new ImageWrapper("data/Ranger/Basic" + i + ".png"));
		}
		images.put("RangerBasicEnd1", new ImageWrapper("data/Ranger/BasicEnd1.png"));
		images.put("RangerBasicEnd2", new ImageWrapper("data/Ranger/BasicEnd2.png"));
		images.put("RangerBasicEnd3", new ImageWrapper("data/Ranger/BasicEnd3.png"));
		
		images.put("RangerDying1", new ImageWrapper("data/Ranger/Dying1.png"));
		images.put("RangerDying2", new ImageWrapper("data/Ranger/Dying2.png"));
		images.put("RangerDying3", new ImageWrapper("data/Ranger/Dying3.png"));
		images.put("RangerDead", new ImageWrapper("data/Ranger/Dead.png"));

		images.put("Fireball1", new ImageWrapper("Fireball1.png"));
		images.put("Shield", new ImageWrapper("Shield.png"));

		images.put("FNOLGO MAP", new ImageWrapper("data/Map/map50x50.png"));
		images.put("SmallTree", new ImageWrapper("data/Map/SmallTree.png"));
		
		images.put("Spectator", new ImageWrapper("data/Spectator.png"));
		images.put("Background", new ImageWrapper("data/Background.jpg"));

	}

	/**
	 * 
	 * Loads all of the images into this object
	 * 
	 * @param applet
	 *            The drawing surface
	 */
	public void loadImages(PApplet applet) {
		Collection<ImageWrapper> c = images.values();
		for (ImageWrapper p : c) {
			p.setPImage(applet.loadImage(p.getFilename()));
		}

	}

	/**
	 * 
	 * Returns a PImage based on the given key
	 * 
	 * @param key
	 *            The String that represents the PImage
	 * @return The PImage
	 */
	public PImage getImage(String key) {
		return images.get(key).getPImage();
	}

}

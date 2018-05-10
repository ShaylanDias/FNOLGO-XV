package clientside;

import java.util.Collection;
import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * 
 * Class that stores all of the images used by this class
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
		
		images.put("Fighter", new ImageWrapper("data/FIghter/Fighter.png"));
		images.put("Fighter1", new ImageWrapper("data/FIghter/fighter1.png"));
		
		//Brute
		images.put("WWDefault", new ImageWrapper("data/Brute/WW-Default.png"));
		images.put("WWWalk0", new ImageWrapper("data/Brute/WW-Walk1.png"));
		images.put("WWWalk1", new ImageWrapper("data/Brute/WW-Walk2.png"));
		images.put("WWWalk2", new ImageWrapper("data/Brute/WW-Walk3.png"));
		images.put("WWWalk3", new ImageWrapper("data/Brute/WW-Walk4.png"));
		images.put("WWDying", new ImageWrapper("data/Brute/WWDying.png"));
		images.put("WWDead", new ImageWrapper("data/Brute/WWDead.png"));
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
		
		images.put("Fireball1", new ImageWrapper("Fireball1.png"));
		images.put("Arrow1", new ImageWrapper("arrow-projectile.jpg"));
		images.put("FNOLGO MAP", new ImageWrapper("FNOLGO MAP v2.png"));
		images.put("Shield", new ImageWrapper("Shield.png"));
		
		
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

		// System.out.println(images.get("Fighter1").getPImage().width);
		// System.out.println(images.get("Fighter1").getPImage().height);

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

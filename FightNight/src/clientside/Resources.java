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

	public static HashMap<String, ImageWrapper> images = new HashMap<String, ImageWrapper>();
	
	/**
	 * Initializes a resource object with all of the images in the game
	 */
	public Resources() {
		//Add all of the necessary images to HashMap
		images.put("Fireball", new ImageWrapper("Fireball.png"));
		images.put("Fighter", new ImageWrapper("Fighter.png"));
		images.put("Fighter1", new ImageWrapper("fighter1.png"));
	}
	
	/**
	 * 
	 * Loads all of the images into this object
	 * 
	 * @param applet The drawing surface
	 */
	public void loadImages(PApplet applet) {
		Collection<ImageWrapper> c = images.values();
		for(ImageWrapper p : c) {
			p.setPImage(applet.loadImage(p.getFilename()));
		}
		
		System.out.println(images.get("Fighter1").getPImage().width);
		System.out.println(images.get("Fighter1").getPImage().height);

	}
	
	/**
	 * 
	 * Returns a PImage based on the given key
	 * 
	 * @param key The String that represents the PImage
	 * @return The PImage
	 */
	public PImage getImage(String key) {
		return images.get(key).getPImage();
	}
	
}

package clientside;

import processing.core.PImage;

/**
 * 
 * Stores a PImage and the filename to load it from so they can both be stored
 * together
 * 
 * @author shaylandias
 *
 */
public class ImageWrapper {

	private String filename;
	private PImage image;

	/**
	 * 
	 * Sets the filename for the image
	 * 
	 * @param filename
	 *            The filename for the image
	 */
	public ImageWrapper(String filename) {
		this.filename = filename;
	}

	/**
	 * 
	 * Gets the name of the file for this Image
	 * 
	 * @return Filename as a String
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * 
	 * Directly sets the PImage
	 * 
	 * @param image
	 *            The new PImage
	 */
	public void setPImage(PImage image) {
		this.image = image;
	}

	/**
	 * 
	 * Gets the PImage
	 * 
	 * @return The PImage
	 */
	public PImage getPImage() {
		return image;
	}

}

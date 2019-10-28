package hr.fer.zemris.java.hw16.model;

import java.util.List;

/**
 * An interface that can be used to query information about images.
 * 
 * @author Patrik
 *
 */
public interface ImageInfoDB {

	/**
	 * Returns the list of all tags in all images
	 * @return the list of all tags in all images
	 */
	public List<String> getTags();
	
	/**
	 * Returns the list of images that have the given tag
	 * @param tag the tag
	 * @return the list of images
	 */
	public List<String> getImagesByTag(String tag);
	
	/**
	 * Returns the information about an image for the specified filename
	 * @param filename filename of the image
	 * @return the image information
	 */
	public ImageInfo getImageByFilename(String filename);
	
}

package hr.fer.zemris.java.hw16.model;

import java.util.List;

/**
 * Represents information about a single image.
 * 
 * @author Patrik
 *
 */
public class ImageInfo {

	/**
	 * Image filename
	 */
	private String filename;
	
	/**
	 * Image title
	 */
	private String title;
	
	/**
	 * Image tags
	 */
	private List<String> tags;
	
	/**
	 * Creates a new object
	 * @param filename filename
	 * @param title title
	 * @param tags tags
	 */
	public ImageInfo(String filename, String title, List<String> tags) {
		this.filename = filename;
		this.title = title;
		this.tags = tags;
	}

	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the tags
	 */
	public List<String> getTags() {
		return tags;
	}
	
	
	
}

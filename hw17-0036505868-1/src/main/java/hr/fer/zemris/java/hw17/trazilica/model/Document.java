package hr.fer.zemris.java.hw17.trazilica.model;

import java.nio.file.Path;

/**
 * Represents a single document that can also be linked to a file.
 * 
 * @author Patrik
 *
 */
public class Document {

	/**
	 * The tfidf vector representing this document
	 */
	private Vector tfidfVector;
	
	/**
	 * The path to this document
	 */
	private Path filepath;

	/**
	 * Creates a new document
	 * @param tfidfVector tfidf vector
	 * @param filepath path
	 */
	public Document(Vector tfidfVector, Path filepath) {
		this.tfidfVector = tfidfVector;
		this.filepath = filepath;
	}

	/**
	 * Creates a new temporary document without the path
	 * @param tfidfVector tfidf vector
	 */
	public Document(Vector tfidfVector) {
		this.tfidfVector = tfidfVector;
	}

	/**
	 * @return the tfidf vector
	 */
	public Vector getTfidfVector() {
		return tfidfVector;
	}

	/**
	 * @return the file path
	 */
	public Path getFilepath() {
		return filepath;
	}
	
	
	
}

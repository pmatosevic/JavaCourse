package hr.fer.zemris.java.hw17.trazilica.model;

/**
 * Represents a single similarity result.
 * 
 * @author Patrik
 *
 */
public class SimilarityResult {

	/**
	 * The document
	 */
	private Document document;
	
	/**
	 * The similarity score
	 */
	private double score;

	/**
	 * Creates a new result
	 * @param document document
	 * @param score score
	 */
	public SimilarityResult(Document document, double score) {
		this.document = document;
		this.score = score;
	}

	/**
	 * @return the document
	 */
	public Document getDocument() {
		return document;
	}

	/**
	 * @return the score
	 */
	public double getScore() {
		return score;
	}
	
	
	
}

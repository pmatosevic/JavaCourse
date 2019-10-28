package hr.fer.zemris.java.hw17.trazilica.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents the collection of multiple documents and is able to create new documents giving it a file path
 * or a list of words to build a document on.
 * 
 * @author Patrik
 *
 */
public class DocumentCollection {
	
	/**
	 * Epsilon error when measuring similarity
	 */
	private static double EPS_SIMILARITY = 1e-6;
	
	/**
	 * Map of all words in the vocabulary mapped to indexes of components in the vectors.
	 */
	private Map<String, Integer> vocabularyMapping;
	
	/**
	 * The idf vector
	 */
	private Vector idfVector;
	
	/**
	 * List of all documents
	 */
	private List<Document> documents = new ArrayList<>();
	
	/**
	 * Creates a new document collection
	 * @param vocabularyMapping vocabulary mapping
	 * @param idfVector idf vector
	 */
	public DocumentCollection(Map<String, Integer> vocabularyMapping, Vector idfVector) {
		this.vocabularyMapping = vocabularyMapping;
		this.idfVector = idfVector;
	}
	

	/**
	 * Creates and returns a new document by reading the file.
	 * @param path path to the file
	 * @return a new document
	 * @throws IOException in case of an IO error
	 */
	public Document createDocument(Path path) throws IOException {
		List<String> words = DocumentCollectionBuilder.parseWords(Files.readString(path));
		return new Document(createTfVector(words).multiply(idfVector), path);
	}
	
	/**
	 * Creates a new document from the given list of words
	 * @param words list of words
	 * @return a new document
	 */
	public Document createDocument(List<String> words) {
		return new Document(createTfVector(words).multiply(idfVector));
	}
	
	/**
	 * Creates the tf vector for the given list of words
	 * @param words list of words
	 * @return the tf vector
	 */
	public Vector createTfVector(List<String> words) {
		Vector tf = new Vector(vocabularyMapping.size());
		for (String word : words) {
			if (vocabularyMapping.containsKey(word)) {
				int index = vocabularyMapping.get(word);
				tf.setComponent(index, tf.getComponent(index) + 1);
			}
		}
		return tf;
	}
	
	/**
	 * Adds the document to this collection
	 * @param doc document
	 */
	public void addDocument(Document doc) {
		documents.add(doc);
	}
	
	/**
	 * Measures the similarity between given document and all documents in this collection
	 * @param doc document
	 * @return the 10 (or less) documents that are the most similar to the given one
	 */
	public List<SimilarityResult> measureSimilarity(Document doc) {
		return documents.stream()
			.map(d -> new SimilarityResult(d, d.getTfidfVector().cosAngle(doc.getTfidfVector())))
			.filter(r -> r.getScore() >= EPS_SIMILARITY)
			.sorted((r1, r2) -> Double.compare(r2.getScore(), r1.getScore()))
			.limit(10)
			.collect(Collectors.toList());
	}


	/**
	 * Returns the vocabulary
	 * @return the vocabulary
	 */
	public Set<String> getVocabulary() {
		return Collections.unmodifiableSet(vocabularyMapping.keySet());
	}
	
}

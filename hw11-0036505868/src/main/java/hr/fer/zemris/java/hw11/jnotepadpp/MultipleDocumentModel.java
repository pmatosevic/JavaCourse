package hr.fer.zemris.java.hw11.jnotepadpp;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Represents a model that stores references to multiple single documents and is
 * capable of opening and closing them.
 * 
 * @author Patrik
 *
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
	
	/**
	 * Creates a new document
	 * @return created document model
	 */
	SingleDocumentModel createNewDocument();

	/**
	 * Returns the current shown document.
	 * @return the current shown document
	 */
	SingleDocumentModel getCurrentDocument();

	/**
	 * Loads a new document from given path
	 * @param path file path
	 * @return loaded document
	 * @throws IOException in case of an IO error
	 */
	SingleDocumentModel loadDocument(Path path) throws IOException;

	/**
	 * Saves the given document model in the given path.
	 * @param model document model
	 * @param newPath file path where to save
	 * @throws IOException in case of an IO error
	 */
	void saveDocument(SingleDocumentModel model, Path newPath) throws IOException;

	/**
	 * Removes the document (without saving it)
	 * @param model document model
	 */
	void closeDocument(SingleDocumentModel model);

	/**
	 * Adds a multiple document listener
	 * @param l multiple document listener
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Removes the multiple document listener
	 * @param l multiple document listener
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Returns the number of opened documents.
	 * @return the number of opened documents
	 */
	int getNumberOfDocuments();

	/**
	 * Returns the document stored on given index
	 * @param index index
	 * @return document model
	 */
	SingleDocumentModel getDocument(int index);
	
}
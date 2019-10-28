package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Represents a listener that will be notified on changes of the model shown to the user.
 * @author Patrik
 *
 */
public interface MultipleDocumentListener {
	
	/**
	 * This method will be called when the current document is changed
	 * @param previousModel previous document
	 * @param currentModel new document
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);
	
	/**
	 * This method will be called when a new document is added.
	 * @param model document
	 */
	void documentAdded(SingleDocumentModel model);
	
	/**
	 * This method will be called when a document is removed.
	 * @param model document
	 */
	void documentRemoved(SingleDocumentModel model);
}
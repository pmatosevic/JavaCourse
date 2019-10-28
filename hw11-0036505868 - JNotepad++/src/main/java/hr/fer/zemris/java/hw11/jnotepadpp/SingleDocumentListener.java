package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Represents a listener that will get document modification events.
 * @author Patrik
 *
 */
public interface SingleDocumentListener {
	
	/**
	 * This method will be called on document modified status change.
	 * @param model document model
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);
	
	/**
	 * This method will be called on document file path change.
	 * @param model document model
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
}
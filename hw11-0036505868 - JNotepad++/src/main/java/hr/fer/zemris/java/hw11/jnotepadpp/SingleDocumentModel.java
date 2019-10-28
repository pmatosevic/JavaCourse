package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * Represents a single document opened in the editor and stores its GUI component and the path.
 * 
 * @author Patrik
 *
 */
public interface SingleDocumentModel {
	
	/**
	 * Returns the text component.
	 * @return the text component
	 */
	JTextArea getTextComponent();
	
	/**
	 * Returns the file path of {@code null} if not set.
	 * @return the file path of {@code null} if not set
	 */
	Path getFilePath();
	
	/**
	 * Sets the file path
	 * @param path file path
	 */
	void setFilePath(Path path);
	
	/**
	 * Returns whether the document was modified since the last save.
	 * @return whether the document was modified since the last save
	 */
	boolean isModified();
	
	/**
	 * Sets the modified status of the document.
	 * @param modified modified status
	 */
	void setModified(boolean modified);
	
	/**
	 * Adds a single document listener.
	 * @param l single document listener
	 */
	void addSingleDocumentListener(SingleDocumentListener l);
	
	/**
	 * Removes the single document listener.
	 * @param l the single document listener
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
}
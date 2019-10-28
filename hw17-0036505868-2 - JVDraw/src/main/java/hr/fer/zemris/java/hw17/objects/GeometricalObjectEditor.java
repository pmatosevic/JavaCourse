package hr.fer.zemris.java.hw17.objects;

import javax.swing.JPanel;

/**
 * An editor that can change properties of the already drawn objects.
 * 
 * @author Patrik
 *
 */
public abstract class GeometricalObjectEditor extends JPanel {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Checks whether the current editing is valid.
	 * @throws RuntimeException in case of an invalid editing
	 */
	public abstract void checkEditing();

	/**
	 * Saves the editing.
	 */
	public abstract void acceptEditing();
	
}
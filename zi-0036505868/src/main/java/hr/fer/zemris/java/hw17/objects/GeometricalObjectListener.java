package hr.fer.zemris.java.hw17.objects;

/**
 * A listener for the geometrical object changes.
 * @author Patrik
 *
 */
public interface GeometricalObjectListener {
	
	/**
	 * Method called on each change of the object
	 * @param o geometrical object
	 */
	public void geometricalObjectChanged(GeometricalObject o);
	
}
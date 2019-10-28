package hr.fer.zemris.java.hw17.model;

/**
 * Listener of the drawing model.
 * @author Patrik
 *
 */
public interface DrawingModelListener {
	
	/**
	 * Method that is called when the objects are added
	 * @param source drawing model
	 * @param index0 lower bound
	 * @param index1 upper bound
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * Method that is called when the objects are removed
	 * @param source drawing model
	 * @param index0 lower bound
	 * @param index1 upper bound
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);

	/**
	 * Method that is called when the objects are changed
	 * @param source drawing model
	 * @param index0 lower bound
	 * @param index1 upper bound
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);
	
}
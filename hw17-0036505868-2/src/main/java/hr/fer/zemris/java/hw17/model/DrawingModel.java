package hr.fer.zemris.java.hw17.model;

import hr.fer.zemris.java.hw17.objects.GeometricalObject;

/**
 * A model that hold references to the drawn objects and provides various operations on itself.
 * @author Patrik
 *
 */
public interface DrawingModel {
	
	/**
	 * Returns the number of objects drawn
	 * @return the number of objects drawn
	 */
	public int getSize();

	/**
	 * Returns the object at index
	 * @param index index
	 * @return the object at index
	 */
	public GeometricalObject getObject(int index);

	/**
	 * Adds the geometrical object
	 * @param object the geometrical object
	 */
	public void add(GeometricalObject object);

	/**
	 * Removes the geometrical object
	 * @param object the geometrical object
	 */
	public void remove(GeometricalObject object);

	/**
	 * Changes the order of the object
	 * @param object object
	 * @param offset offset
	 */
	public void changeOrder(GeometricalObject object, int offset);

	/**
	 * Returns the index of the geometrical object (or -1 if it does not exists)
	 * @param object the geometrical object
	 * @return the index
	 */
	public int indexOf(GeometricalObject object);

	/**
	 * Removes all objects
	 */
	public void clear();

	/**
	 * Clears the modified flag
	 */
	public void clearModifiedFlag();

	/**
	 * Returns the modified flag
	 * @return the modified flag
	 */
	public boolean isModified();

	/**
	 * Adds the model listener
	 * @param l the model listener
	 */
	public void addDrawingModelListener(DrawingModelListener l);

	/**
	 * Removes the model listener
	 * @param l the model listener
	 */
	public void removeDrawingModelListener(DrawingModelListener l);
	
}
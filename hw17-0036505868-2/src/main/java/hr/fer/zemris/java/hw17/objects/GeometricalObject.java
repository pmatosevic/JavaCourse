package hr.fer.zemris.java.hw17.objects;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import hr.fer.zemris.java.hw17.visitor.GeometricalObjectVisitor;

/**
 * Represents an abstract geometrical object
 * @author Patrik
 *
 */
public abstract class GeometricalObject {

	/**
	 * List of listeners
	 */
	private List<GeometricalObjectListener> listeners = new CopyOnWriteArrayList<>();
	
	/**
	 * Accepts the visitor
	 * @param v visitor
	 */
	public abstract void accept(GeometricalObjectVisitor v);
	
	/**
	 * Creates the editor for itself
	 * @return the editor
	 */
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();
	
	/**
	 * Adds the listener
	 * @param l the listener
	 */
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		if (l != null) {
			listeners.add(l);
		}
	}
	
	/**
	 * Removes the listener
	 * @param l the listener
	 */
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.remove(l);
	}
	
	/**
	 * Notifies all listeners about the change
	 */
	protected void notifyListeners() {
		listeners.forEach(l -> l.geometricalObjectChanged(this));
	}
	
}

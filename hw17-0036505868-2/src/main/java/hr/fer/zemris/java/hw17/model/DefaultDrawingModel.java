package hr.fer.zemris.java.hw17.model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CopyOnWriteArrayList;

import hr.fer.zemris.java.hw17.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.objects.GeometricalObjectListener;

/**
 * A default implementation of the {@link DrawingModel} interface.
 * @author Patrik
 *
 */
public class DefaultDrawingModel implements DrawingModel {

	/**
	 * List of listeners
	 */
	private List<DrawingModelListener> listeners = new CopyOnWriteArrayList<>();
	
	/**
	 * List of geometrical objects
	 */
	private List<GeometricalObject> list = new ArrayList<>();
	
	/**
	 * Modified flag
	 */
	private boolean modified = false;
	
	@Override
	public int getSize() {
		return list.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		return list.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		object.addGeometricalObjectListener(new GeometricalObjectListener() {
			@Override
			public void geometricalObjectChanged(GeometricalObject o) {
				int index = list.indexOf(o);
				listeners.forEach(l -> l.objectsChanged(DefaultDrawingModel.this, index, index));
			}
		});
		list.add(object);
		modified = true;
		listeners.forEach(l -> l.objectsAdded(this, list.size() - 1, list.size() - 1));
	}

	@Override
	public void remove(GeometricalObject object) {
		int index = list.indexOf(object);
		if (index == -1) return;
		
		list.remove(index);
		modified = true;
		listeners.forEach(l -> l.objectsRemoved(this, index, index));
	}

	@Override
	public void changeOrder(GeometricalObject object, int offset) {
		int index = list.indexOf(object);
		if (index == -1) {
			throw new NoSuchElementException("Element was not found.");
		}
		
		list.remove(index);
		list.add(index + offset, object);
		modified = true;
		int min = Math.min(index, index + offset);
		int max = Math.max(index, index + offset);
		listeners.forEach(l -> l.objectsChanged(this, min, max));
	}

	@Override
	public int indexOf(GeometricalObject object) {
		return list.indexOf(object);
	}

	@Override
	public void clear() {
		int size = list.size();
		if (size == 0) return;
		
		list.clear();
		modified = true;
		listeners.forEach(l -> l.objectsRemoved(this, 0, size - 1));
	}

	@Override
	public void clearModifiedFlag() {
		modified = false;
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		if (l != null) {
			listeners.add(l);
		}
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);
	}

}

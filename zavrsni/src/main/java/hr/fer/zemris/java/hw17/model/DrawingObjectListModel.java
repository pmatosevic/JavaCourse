package hr.fer.zemris.java.hw17.model;

import javax.swing.AbstractListModel;

import hr.fer.zemris.java.hw17.objects.GeometricalObject;

/**
 * The list model of geometrial objects.
 * @author Patrik
 *
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> {

	private static final long serialVersionUID = 1L;
	
	/**
	 * The actual drawing model
	 */
	private DrawingModel model;
	
	/**
	 * Creates a new list model.
	 * @param model drawing model
	 */
	public DrawingObjectListModel(DrawingModel model) {
		this.model = model;
		model.addDrawingModelListener(new DrawingModelListener() {
			@Override
			public void objectsRemoved(DrawingModel source, int index0, int index1) {
				fireIntervalRemoved(source, index0, index1);
			}
			@Override
			public void objectsChanged(DrawingModel source, int index0, int index1) {
				fireContentsChanged(source, index0, index1);
			}
			@Override
			public void objectsAdded(DrawingModel source, int index0, int index1) {
				fireIntervalAdded(source, index0, index1);
			}
		});
	}
	
	@Override
	public int getSize() {
		return model.getSize();
	}

	@Override
	public GeometricalObject getElementAt(int index) {
		return model.getObject(index);
	}

}

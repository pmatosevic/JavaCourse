package hr.fer.zemris.java.hw17.tools;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.JDrawingCanvas;
import hr.fer.zemris.java.hw17.color.IColorProvider;
import hr.fer.zemris.java.hw17.model.DrawingModel;
import hr.fer.zemris.java.hw17.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.visitor.GeometricalObjectPainter;
import hr.fer.zemris.java.hw17.visitor.GeometricalObjectVisitor;

/**
 * Partial implementation of the {@link Tool}.
 * 
 * @author Patrik
 *
 */
public abstract class AbstractTool implements Tool {

	/**
	 * Drawing model
	 */
	protected DrawingModel model;
	
	/**
	 * Foreground color provider
	 */
	protected IColorProvider fgColorProvider;
	
	/**
	 * Drawing canvas
	 */
	protected JDrawingCanvas canvas;
	
	/**
	 * Starting point
	 */
	protected Point startPoint;

	/**
	 * Current point
	 */
	protected Point currPoint;
	
	/**
	 * Creates a new abstract tool
	 * @param model model
	 * @param canvas canvas
	 * @param fgColorProvider foreground color provider
	 */
	public AbstractTool(DrawingModel model, JDrawingCanvas canvas, IColorProvider fgColorProvider) {
		this.model = model;
		this.fgColorProvider = fgColorProvider;
		this.canvas = canvas;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (startPoint == null) {
			startPoint = e.getPoint();
			currPoint = startPoint;
		} else {
			currPoint = e.getPoint();
			GeometricalObject object = createObject();
			model.add(object);
			startPoint = null;
			currPoint = null;
		}
	}
	
	/**
	 * Creates the object for the current point.
	 * @return the created geometrical object
	 */
	protected abstract GeometricalObject createObject();

	@Override
	public void mouseMoved(MouseEvent e) {
		if (startPoint == null) return;
		
		currPoint = e.getPoint();
		canvas.repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void paint(Graphics2D g2d) {
		if (currPoint == null) return;
		
		GeometricalObjectVisitor painter = new GeometricalObjectPainter(g2d);
		createObject().accept(painter);
	}

}

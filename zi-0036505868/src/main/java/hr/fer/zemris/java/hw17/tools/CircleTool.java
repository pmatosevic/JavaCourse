package hr.fer.zemris.java.hw17.tools;

import hr.fer.zemris.java.hw17.JDrawingCanvas;
import hr.fer.zemris.java.hw17.color.IColorProvider;
import hr.fer.zemris.java.hw17.model.DrawingModel;
import hr.fer.zemris.java.hw17.objects.Circle;
import hr.fer.zemris.java.hw17.objects.GeometricalObject;

/**
 * A tool for drawing circles.
 * 
 * @author Patrik
 *
 */
public class CircleTool extends AbstractTool {

	/**
	 * Creates a new circle tool.
	 * @param model model
	 * @param canvas canvas
	 * @param fgColorProvider foreground color provider
	 */
	public CircleTool(DrawingModel model, JDrawingCanvas canvas, IColorProvider fgColorProvider) {
		super(model, canvas, fgColorProvider);
	}

	@Override
	protected GeometricalObject createObject() {
		int radius = (int) currPoint.distance(startPoint);
		return new Circle(fgColorProvider.getCurrentColor(), startPoint, radius);
	}

}

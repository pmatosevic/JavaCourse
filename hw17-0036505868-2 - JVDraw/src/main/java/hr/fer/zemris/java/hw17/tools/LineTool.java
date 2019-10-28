package hr.fer.zemris.java.hw17.tools;

import hr.fer.zemris.java.hw17.JDrawingCanvas;
import hr.fer.zemris.java.hw17.color.IColorProvider;
import hr.fer.zemris.java.hw17.model.DrawingModel;
import hr.fer.zemris.java.hw17.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.objects.Line;

/**
 * A tool for drawing lines.
 * 
 * @author Patrik
 *
 */
public class LineTool extends AbstractTool {

	/**
	 * Creates a new line tool.
	 * @param model model
	 * @param canvas canvas
	 * @param fgColorProvider foreground color provider
	 */
	public LineTool(DrawingModel model, JDrawingCanvas canvas, IColorProvider fgColorProvider) {
		super(model, canvas, fgColorProvider);
	}

	@Override
	protected GeometricalObject createObject() {
		return new Line(fgColorProvider.getCurrentColor(), startPoint, currPoint);
	}


}

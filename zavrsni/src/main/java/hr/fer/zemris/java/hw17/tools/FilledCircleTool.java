package hr.fer.zemris.java.hw17.tools;

import hr.fer.zemris.java.hw17.JDrawingCanvas;
import hr.fer.zemris.java.hw17.color.IColorProvider;
import hr.fer.zemris.java.hw17.model.DrawingModel;
import hr.fer.zemris.java.hw17.objects.FilledCircle;
import hr.fer.zemris.java.hw17.objects.GeometricalObject;

/**
 * A tool for drawing filled circles.
 * 
 * @author Patrik
 *
 */
public class FilledCircleTool extends AbstractTool {

	/**
	 * Background color provider
	 */
	private IColorProvider bgColorProvider;

	/**
	 * Creates a new filled circle tool
	 * @param model model
	 * @param canvas canvas
	 * @param fgColorProvider foreground color provider
	 * @param bgColorProvider background color provider
	 */
	public FilledCircleTool(DrawingModel model, JDrawingCanvas canvas, IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
		super(model, canvas, fgColorProvider);
		this.bgColorProvider = bgColorProvider;
	}
	
	@Override
	protected GeometricalObject createObject() {
		int radius = (int) currPoint.distance(startPoint);
		return new FilledCircle(fgColorProvider.getCurrentColor(), startPoint, radius, bgColorProvider.getCurrentColor());
	}
	
}

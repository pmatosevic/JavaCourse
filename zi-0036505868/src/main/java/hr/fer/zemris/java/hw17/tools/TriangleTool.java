package hr.fer.zemris.java.hw17.tools;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw17.JDrawingCanvas;
import hr.fer.zemris.java.hw17.color.IColorProvider;
import hr.fer.zemris.java.hw17.model.DrawingModel;
import hr.fer.zemris.java.hw17.objects.Triangle;

public class TriangleTool implements Tool {

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

	protected IColorProvider bgColorProvider;
	
	
	protected Point currPoint;
	
	protected List<Point> points = new ArrayList<Point>();
	
	
	/**
	 * Creates a new abstract tool
	 * @param model model
	 * @param canvas canvas
	 * @param fgColorProvider foreground color provider
	 */
	public TriangleTool(DrawingModel model, JDrawingCanvas canvas, IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
		this.model = model;
		this.fgColorProvider = fgColorProvider;
		this.canvas = canvas;
		this.bgColorProvider = bgColorProvider;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (points.isEmpty()) {
			points.add(e.getPoint());
			currPoint = e.getPoint();
			return;
		}
		
		points.add(e.getPoint());
		if (points.size() == 3) {
			Triangle triangle = new Triangle(fgColorProvider.getCurrentColor(), bgColorProvider.getCurrentColor(), 
					points.get(0), points.get(1), points.get(2));
			model.add(triangle);
			points.clear();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (points.isEmpty()) return;
		
		currPoint = e.getPoint();
		canvas.repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void paint(Graphics2D g2d) {
		if (currPoint == null) return;
		
		int n = points.size();
		g2d.setColor(fgColorProvider.getCurrentColor());
		for (int i=0; i<points.size(); i++) {
			Point p1 = points.get(i);
			Point p2 = (i==n-1) ? currPoint : points.get(i+1);
			g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
		}
	}

}

package hr.fer.zemris.java.hw17.visitor;

import java.awt.Graphics2D;
import java.awt.Point;

import hr.fer.zemris.java.hw17.objects.Circle;
import hr.fer.zemris.java.hw17.objects.FilledCircle;
import hr.fer.zemris.java.hw17.objects.Line;

/**
 * Visitor that paints geometric objects to the graphics object
 * @author Patrik
 *
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor {

	/**
	 * Graphics object
	 */
	private Graphics2D g2d;

	/**
	 * Creates a new visitor
	 * @param g2d graphics object
	 */
	public GeometricalObjectPainter(Graphics2D g2d) {
		this.g2d = g2d;
	}
	
	@Override
	public void visit(Line line) {
		Point p1 = line.getStartPoint();
		Point p2 = line.getEndPoint();
		g2d.setColor(line.getColor());
		g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
	}

	@Override
	public void visit(Circle circle) {
		Point p = circle.getCenter();
		int r = circle.getRadius();
		g2d.setColor(circle.getOutlineColor());
		g2d.drawOval(p.x - r, p.y - r, 2*r, 2*r);
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		Point p = filledCircle.getCenter();
		int r = filledCircle.getRadius() - 1;
		g2d.setColor(filledCircle.getFillColor());
		g2d.fillOval(p.x - r, p.y - r, 2*r, 2*r);
		r++;
		g2d.setColor(filledCircle.getOutlineColor());
		g2d.drawOval(p.x - r, p.y - r, 2*r, 2*r);
	}

}

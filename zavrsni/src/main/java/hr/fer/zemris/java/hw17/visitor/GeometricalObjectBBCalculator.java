package hr.fer.zemris.java.hw17.visitor;

import java.awt.Point;
import java.awt.Rectangle;

import hr.fer.zemris.java.hw17.objects.Circle;
import hr.fer.zemris.java.hw17.objects.FilledCircle;
import hr.fer.zemris.java.hw17.objects.Line;
import hr.fer.zemris.java.hw17.objects.Triangle;

/**
 * A visitor that calculates the minimal bounding box for all objects.
 * 
 * @author Patrik
 *
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {

	/**
	 * Minimal X
	 */
	private int minX = Integer.MAX_VALUE;
	
	/**
	 * Maximal X
	 */
	private int maxX = -1;
	
	/**
	 * Minimal Y
	 */
	private int minY = Integer.MAX_VALUE;
	
	/**
	 * Maximal Y
	 */
	private int maxY = -1;
	
	/**
	 * Returns the calculated bounding box
	 * @return the calculated bounding box
	 */
	public Rectangle getBoundingBox() {
		if (maxX == -1) return new Rectangle();
		
		return new Rectangle(minX, minY, maxX - minX, maxY - minY);
	}
	
	/**
	 * Visits the point
	 * @param p point
	 */
	private void visitPoint(Point p) {
		minX = Math.min(minX, p.x);
		maxX = Math.max(maxX, p.x);
		minY = Math.min(minY, p.y);
		maxY = Math.max(maxY, p.y);
	}
	
	@Override
	public void visit(Line line) {
		Point p1 = line.getStartPoint();
		Point p2 = line.getEndPoint();
		visitPoint(p1);
		visitPoint(p2);
	}

	@Override
	public void visit(Circle circle) {
		Point p1 = new Point(circle.getCenter());
		Point p2 = new Point(circle.getCenter());
		p1.translate(-circle.getRadius(), -circle.getRadius());
		p2.translate(circle.getRadius(), circle.getRadius());
		visitPoint(p1);
		visitPoint(p2);
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		visit((Circle) filledCircle);
	}

	@Override
	public void visit(Triangle triangle) {
		for (Point p : triangle.getPoints()) {
			visitPoint(p);
		}
	}

}

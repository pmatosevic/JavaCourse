package hr.fer.zemris.java.hw17.visitor;

import hr.fer.zemris.java.hw17.objects.Circle;
import hr.fer.zemris.java.hw17.objects.FilledCircle;
import hr.fer.zemris.java.hw17.objects.Line;
import hr.fer.zemris.java.hw17.objects.Triangle;

/**
 * A visitor that visits the geometrical objects and calculates or does something with information about them.
 * @author Patrik
 *
 */
public interface GeometricalObjectVisitor {
	
	/**
	 * Visits the line
	 * @param line line
	 */
	public abstract void visit(Line line);

	/**
	 * Visits the circle
	 * @param circle circle
	 */
	public abstract void visit(Circle circle);

	/**
	 * Visits the filled circle
	 * @param filledCircle filled circle
	 */
	public abstract void visit(FilledCircle filledCircle);
	
	public abstract void visit(Triangle triangle);
	
}
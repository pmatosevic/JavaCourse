package hr.fer.zemris.java.hw17.visitor;

import java.awt.Color;
import java.awt.Point;

import hr.fer.zemris.java.hw17.objects.Circle;
import hr.fer.zemris.java.hw17.objects.FilledCircle;
import hr.fer.zemris.java.hw17.objects.Line;
import hr.fer.zemris.java.hw17.objects.Triangle;

/**
 * A visitor that saves information about every object and outputs that as a string that can be saved to a file.
 * @author Patrik
 *
 */
public class GeometricalObjectFileSaver implements GeometricalObjectVisitor {

	/**
	 * The string builder
	 */
	private StringBuilder sb;

	/**
	 * Creates a new visitor
	 */
	public GeometricalObjectFileSaver() {
		this.sb = new StringBuilder();
	}
	
	@Override
	public void visit(Line line) {
		Point p1 = line.getStartPoint();
		Point p2 = line.getEndPoint();
		sb.append(String.format("LINE %d %d %d %d %s", p1.x, p1.y, p2.x, p2.y, colorStr(line.getColor())));
		sb.append("\n");
	}

	@Override
	public void visit(Circle circle) {
		Point p = circle.getCenter();
		sb.append(String.format("CIRCLE %d %d %d %s", p.x, p.y, circle.getRadius(), 
				colorStr(circle.getOutlineColor())));
		sb.append("\n");
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		Point p = filledCircle.getCenter();
		sb.append(String.format("FCIRCLE %d %d %d %s %s", p.x, p.y, filledCircle.getRadius(), 
				colorStr(filledCircle.getOutlineColor()), colorStr(filledCircle.getFillColor())));
		sb.append("\n");
	}
	
	@Override
	public void visit(Triangle triangle) {
		sb.append("FTRIANGLE ");
		for (Point p : triangle.getPoints()) {
			sb.append("" + p.x + " " + p.y + " ");
		}
		sb.append(colorStr(triangle.getOutlineColor()) + " ");
		sb.append(colorStr(triangle.getFillColor()));
		sb.append("\n");
	}
	
	/**
	 * Returns the color string
	 * @param color color
	 * @return the color string
	 */
	private Object colorStr(Color color) {
		return String.format("%d %d %d", color.getRed(), color.getGreen(), color.getBlue());
	}
	
	/**
	 * Returns the output string
	 * @return the output string
	 */
	public String getOutputString() {
		return sb.toString();
	}

}

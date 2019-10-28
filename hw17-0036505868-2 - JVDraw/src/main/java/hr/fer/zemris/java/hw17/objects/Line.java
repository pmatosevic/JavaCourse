package hr.fer.zemris.java.hw17.objects;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.Util;
import hr.fer.zemris.java.hw17.color.JColorArea;
import hr.fer.zemris.java.hw17.visitor.GeometricalObjectVisitor;

/**
 * Represents a line.
 * 
 * @author Patrik
 *
 */
public class Line extends GeometricalObject {

	/**
	 * Line color
	 */
	private Color color;
	
	/**
	 * Starting point
	 */
	private Point startPoint;
	
	/**
	 * Ending point
	 */
	private Point endPoint;
	
	/**
	 * Creates a new line
	 * @param color color
	 * @param startPoint starting point
	 * @param endPoint ending point
	 */
	public Line(Color color, Point startPoint, Point endPoint) {
		this.color = color;
		this.startPoint = startPoint;
		this.endPoint = endPoint;
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}
	
	@Override
	public String toString() {
		return "Line " + String.format("(%d,%d)-(%d,%d)", startPoint.x, startPoint.y, endPoint.x, endPoint.y);
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @return the start point
	 */
	public Point getStartPoint() {
		return startPoint;
	}

	/**
	 * @return the end point
	 */
	public Point getEndPoint() {
		return endPoint;
	}
	
	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new LineEditor();
	}
	
	/**
	 * The editor for the line properties
	 * 
	 * @author Patrik
	 *
	 */
	private class LineEditor extends GeometricalObjectEditor {
		
		private static final long serialVersionUID = 1L;

		/**
		 * Text field for the start point
		 */
		private JTextField startText = new JTextField(startPoint.x + "," + startPoint.y);
		
		/**
		 * Text field for the end point
		 */
		private JTextField endText = new JTextField(endPoint.x + "," + endPoint.y);
		
		/**
		 * Color area for the line color
		 */
		private JColorArea colorChooser = new JColorArea(color);
		
		/**
		 * Creates a new editor
		 */
		public LineEditor() {
			setLayout(new GridLayout(0, 2));
			add(new JLabel("Start point:"));
			add(startText);
			add(new JLabel("End point:"));
			add(endText);
			add(new JLabel("Color:"));
			add(colorChooser);
		}
		
		@Override
		public void checkEditing() {
			Util.parsePoint(startText.getText());
			Util.parsePoint(endText.getText());
		}

		@Override
		public void acceptEditing() {
			startPoint = Util.parsePoint(startText.getText());
			endPoint = Util.parsePoint(endText.getText());
			color = colorChooser.getCurrentColor();
			notifyListeners();
		}
		
		
	}
	

}

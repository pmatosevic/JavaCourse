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
 * Represents a circle.
 * 
 * @author Patrik
 *
 */
public class Circle extends GeometricalObject {

	/**
	 * Outline color
	 */
	protected Color outlineColor;
	
	/**
	 * Circle center
	 */
	protected Point center;
	
	/**
	 * Circle radius
	 */
	protected int radius;
	
	/**
	 * Creates a new circle
	 * @param color color
	 * @param center center
	 * @param radius radius
	 */
	public Circle(Color outlineColor, Point center, int radius) {
		this.outlineColor = outlineColor;
		this.center = center;
		this.radius = radius;
	}


	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	
	@Override
	public String toString() {
		return "Circle " + String.format("(%d,%d), %d", center.x, center.y, radius);
	}
	

	/**
	 * @return the outline color
	 */
	public Color getOutlineColor() {
		return outlineColor;
	}


	/**
	 * @return the center
	 */
	public Point getCenter() {
		return center;
	}


	/**
	 * @return the radius
	 */
	public int getRadius() {
		return radius;
	}


	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new CircleEditor();
	}
	
	/**
	 * The editor for the circle properties
	 * 
	 * @author Patrik
	 *
	 */
	protected class CircleEditor extends GeometricalObjectEditor {

		private static final long serialVersionUID = 1L;

		/**
		 * Text field for the center
		 */
		private JTextField centerText = new JTextField(center.x + ", " + center.y);
		
		/**
		 * Text field for the radius
		 */
		private JTextField radiusText = new JTextField("" + radius);
		
		/**
		 * Color area for the outline color
		 */
		private JColorArea outlineChooser = new JColorArea(outlineColor);
		
		/**
		 * Creates a new editor
		 */
		public CircleEditor() {
			setLayout(new GridLayout(0, 2));
			add(new JLabel("Start point:"));
			add(centerText);
			add(new JLabel("Radius:"));
			add(radiusText);
			add(new JLabel("Outline:"));
			add(outlineChooser);
		}
		
		@Override
		public void checkEditing() {
			Util.parsePoint(centerText.getText());
			Integer.parseInt(radiusText.getText().trim());
		}

		/**
		 * Saves the changes
		 */
		protected void saveChanges() {
			center = Util.parsePoint(centerText.getText());
			radius = Integer.parseInt(radiusText.getText().trim());
			outlineColor = outlineChooser.getCurrentColor();
		}
		
		@Override
		public void acceptEditing() {
			saveChanges();
			notifyListeners();
		}
		
	}
	
	
	

}

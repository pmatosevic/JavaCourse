package hr.fer.zemris.java.hw17.objects;

import java.awt.Color;
import java.awt.Point;

import javax.swing.JLabel;

import hr.fer.zemris.java.hw17.color.JColorArea;
import hr.fer.zemris.java.hw17.visitor.GeometricalObjectVisitor;

/**
 * Represents a filled circle
 * @author Patrik
 *
 */
public class FilledCircle extends Circle {

	/**
	 * Fill color
	 */
	private Color fillColor;

	/**
	 * Creates a new filled circle
	 * @param outlineColor outline color
	 * @param center center
	 * @param radius radius
	 * @param fillColor fill color
	 */
	public FilledCircle(Color outlineColor, Point center, int radius, Color fillColor) {
		super(outlineColor, center, radius);
		this.fillColor = fillColor;
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}
	
	@Override
	public String toString() {
		return super.toString() + 
				String.format(", #%02X%02X%02X", fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue());  
	}

	/**
	 * @return the fill color
	 */
	public Color getFillColor() {
		return fillColor;
	}
	
	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new FilledCircleEditor();
	}
	
	/**
	 * The editor for the filled circle properties
	 * 
	 * @author Patrik
	 *
	 */
	protected class FilledCircleEditor extends Circle.CircleEditor {
		
		private static final long serialVersionUID = 1L;
		
		/**
		 * Color area for the fill color
		 */
		private JColorArea fillChooser = new JColorArea(fillColor);
		
		/**
		 * Creates a new editor
		 */
		public FilledCircleEditor() {
			super();
			add(new JLabel("Fill color:"));
			add(fillChooser);
		}
		
		@Override
		public void checkEditing() {
			super.checkEditing();
		}
		
		@Override
		public void acceptEditing() {
			super.saveChanges();
			fillColor = fillChooser.getCurrentColor();
			notifyListeners();
		}
		
	}

}

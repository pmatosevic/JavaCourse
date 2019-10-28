package hr.fer.zemris.java.hw17.objects;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;

import hr.fer.zemris.java.hw17.color.JColorArea;
import hr.fer.zemris.java.hw17.visitor.GeometricalObjectVisitor;

public class Triangle extends GeometricalObject {

	private Color outlineColor;
	private Color fillColor;
	private Point point1;
	private Point point2;
	private Point point3;
	
	
	/**
	 * @param outlineColor
	 * @param fillColor
	 * @param point1
	 * @param point2
	 * @param point3
	 */
	public Triangle(Color outlineColor, Color fillColor, Point point1, Point point2, Point point3) {
		this.outlineColor = outlineColor;
		this.fillColor = fillColor;
		this.point1 = point1;
		this.point2 = point2;
		this.point3 = point3;
	}
	
	
	
	/**
	 * @return the outlineColor
	 */
	public Color getOutlineColor() {
		return outlineColor;
	}



	/**
	 * @return the fillColor
	 */
	public Color getFillColor() {
		return fillColor;
	}



	/**
	 * @return the point1
	 */
	public Point getPoint1() {
		return point1;
	}



	/**
	 * @return the point2
	 */
	public Point getPoint2() {
		return point2;
	}



	/**
	 * @return the point3
	 */
	public Point getPoint3() {
		return point3;
	}
	
	public Point[] getPoints() {
		return new Point[] {point1, point2, point3};
	}


	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new TriangleEditor();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Triangle ");
		for (Point p : getPoints()) {
			sb.append(String.format("(%d, %d), ", p.x, p.y));
		}
		sb.append(String.format("#%02X%02X%02X", fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue()));
		return sb.toString();
	}
	
	
	
	private class TriangleEditor extends GeometricalObjectEditor {

		private JColorArea fgColorChooser = new JColorArea(outlineColor);
		
		private JColorArea bgColorChooser = new JColorArea(fillColor);
		
		public TriangleEditor() {
			setLayout(new GridLayout(0, 2));
			
			add(new JLabel("Outline color:"));
			add(fgColorChooser);
			add(new JLabel("Fill color:"));
			add(bgColorChooser);
		}
		
		@Override
		public void checkEditing() {
		}

		@Override
		public void acceptEditing() {
			outlineColor = fgColorChooser.getCurrentColor();
			fillColor = bgColorChooser.getCurrentColor();
			notifyListeners();
		}
		
	}
	
	
	

}

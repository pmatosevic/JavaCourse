package hr.fer.zemris.java.gui.charts;

/**
 * Represents a single value in the chart.
 * @author Patrik
 *
 */
public class XYValue {

	/**
	 * x value
	 */
	private int x; 
	
	/**
	 * y value
	 */
	private int y;

	/**
	 * Creates new object
	 * @param x x
	 * @param y y
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}
	
	
	
}

package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Represents a bar chart with values.
 * @author Patrik
 *
 */
public class BarChart {

	/**
	 * List of values
	 */
	private List<XYValue> values;
	
	/**
	 * x-axis description
	 */
	private String xText;
	
	/**
	 * y-axis description
	 */
	private String yText;
	
	/**
	 * minimum y
	 */
	private int yMin;
	
	/**
	 * maximum y
	 */
	private int yMax;
	
	/**
	 * The difference between shown y values
	 */
	private int yDiff;

	/**
	 * Creates a new bar chart.
	 * @param values list of values
	 * @param xText x-axis description
	 * @param yText y-axis description
	 * @param yMin minimum y
	 * @param yMax maximum y
	 * @param yDiff the difference between shown y values
	 * @throws IllegalArgumentException in case of an invalid {@code yMin} or @code yMax
	 */
	public BarChart(List<XYValue> values, String xText, String yText, int yMin, int yMax, int yDiff) {
		if (yMin < 0 || yMax <= yMin) {
			throw new IllegalArgumentException("illegal value of yMin.");
		}
		for (XYValue val : values) {
			if (val.getY() < yMin) {
				throw new IllegalArgumentException("Some values have y value smaller than ymin.");
			}
		}
		this.values = values;
		this.xText = xText;
		this.yText = yText;
		this.yMin = yMin;
		this.yMax = yMax;
		this.yDiff = yDiff;
	}

	/**
	 * @return the values
	 */
	public List<XYValue> getValues() {
		return values;
	}

	/**
	 * @return the xText
	 */
	public String getxText() {
		return xText;
	}

	/**
	 * @return the yText
	 */
	public String getyText() {
		return yText;
	}

	/**
	 * @return the yMin
	 */
	public int getyMin() {
		return yMin;
	}

	/**
	 * @return the yMax
	 */
	public int getyMax() {
		return yMax;
	}

	/**
	 * @return the yDiff
	 */
	public int getyDiff() {
		return yDiff;
	}
	
	
	
	
}

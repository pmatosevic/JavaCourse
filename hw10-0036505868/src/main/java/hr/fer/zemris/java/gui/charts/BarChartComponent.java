package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.JComponent;

/**
 * Car chart component.
 * 
 * @author Patrik
 *
 */
public class BarChartComponent extends JComponent {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Bottom margin
	 */
	private static final int MARGIN_BOTTOM = 5;
	
	/**
	 * Space between text at the bottom and on the left and the numbers
	 */
	private static final int SPACE1 = 20;
	
	/**
	 * Space between numbers and the axis
	 */
	private static final int SPACE2 = 10;
	
	/**
	 * Space on axis end
	 */
	private static final int AXIS_END_SPACE = 15;
	
	/**
	 * Size of small lines on axis
	 */
	private static final int AXIS_SMALL_LINE = 3;
	
	/**
	 * Axis arrow size
	 */
	private static final int AXIS_ARROW_SIZE = 3;
	
	/**
	 * Graph color
	 */
	private static final Color GRAPH_COLOR = new Color(244, 119, 72);
	
	/**
	 * Bar chart
	 */
	private BarChart barChart;
	
	/**
	 * Min y on the chart
	 */
	private int chartMinY;

	/**
	 * Creates a new component
	 * @param barChart bar chart data
	 */
	public BarChartComponent(BarChart barChart) {
		this.barChart = barChart;
		
		chartMinY = barChart.getyMin();
		while ((barChart.getyMax() - chartMinY) % barChart.getyDiff() != 0) {
			chartMinY++;
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		Dimension dim = getSize();
		Insets ins = getInsets();
		Rectangle r = new Rectangle(ins.left, ins.top, 
				dim.width-ins.left-ins.right, dim.height-ins.top-ins.bottom - MARGIN_BOTTOM);
		
		g2.setFont(g2.getFont().deriveFont(14f));
		Point coordStart = drawYAxis(g2, r);
		drawXAxis(g2, new Rectangle(coordStart.x, coordStart.y, (int)r.getMaxX() - coordStart.x, (int)r.getMaxY() - coordStart.y));
		
		int dataW = (int) (r.getMaxX() - coordStart.x);
		int dataH = (int) (coordStart.y - r.getMinY() - AXIS_END_SPACE);
		drawData(g2, barChart.getValues(), new Rectangle(coordStart.x, coordStart.y - dataH, dataW, dataH));
	}

	/**
	 * Draws the y axis
	 * @param g2 graphics object
	 * @param b rectangle where to draw
	 * @return point of axis intersection
	 */
	private Point drawYAxis(Graphics2D g2, Rectangle b) {
		FontMetrics fm = g2.getFontMetrics();
		int maxY = (int) b.getMaxY() - 2*fm.getAscent() - SPACE1 - SPACE2;
		int minY = (int) b.getMinY();
		
		AffineTransform at = g2.getTransform();
		AffineTransform newAt = new AffineTransform();
		newAt.concatenate(at);
		newAt.rotate(-Math.PI / 2);
		g2.setTransform(newAt);
		int descriptionW = g2.getFontMetrics().stringWidth(barChart.getyText());
		int tmpY = (maxY + descriptionW) / 2;
		g2.drawString(barChart.getyText(), -tmpY, fm.getAscent());
		g2.setTransform(at);
		
		int numVals = (barChart.getyMax() - chartMinY) / barChart.getyDiff() + 1;
		double availableHeight = maxY - minY - AXIS_END_SPACE;
		int maxW = fm.stringWidth("" + barChart.getyMax());
		
		int startX = b.x + fm.getAscent() + SPACE1;
		for (int i=0; i<numVals; i++) {
			int currY = (int) (maxY - (availableHeight / (numVals-1) * i));
			
			int lineEndX = startX + maxW + SPACE2;
			g2.setColor(Color.GRAY);
			g2.drawLine(lineEndX - AXIS_SMALL_LINE, currY, lineEndX, currY);
			
			String currNumber = Integer.toString(chartMinY + i * barChart.getyDiff());
			int currW = fm.stringWidth(currNumber);
			g2.setColor(Color.BLACK);
			g2.drawString(currNumber, startX + (maxW-currW), currY + fm.getAscent()/3);
		}
		int axisX = startX + maxW + SPACE2;
		g2.setColor(Color.GRAY);
		g2.drawLine(axisX, minY, axisX, maxY);
		
		g2.drawLine(axisX - AXIS_ARROW_SIZE, minY + AXIS_ARROW_SIZE, axisX, minY);
		g2.drawLine(axisX + AXIS_ARROW_SIZE, minY + AXIS_ARROW_SIZE, axisX, minY);
		
		g2.setColor(Color.BLACK);
		return new Point(axisX, maxY);
	}
	
	/**
	 * Draws the x axis
	 * @param g2 graphics object
	 * @param b rectangle where to draw
	 */
	private void drawXAxis(Graphics2D g2, Rectangle b) {
		FontMetrics fm = g2.getFontMetrics();
		
		int minX = (int) b.getMinX();
		int maxX = (int) b.getMaxX();
		int minY = (int) b.getMinY();
		
		int descriptionW = fm.stringWidth(barChart.getxText());
		g2.drawString(barChart.getxText(), (maxX + minX)/2 - descriptionW/2, (int)b.getMaxY());
		
		g2.setColor(Color.GRAY);
		g2.drawLine(minX, minY, maxX, minY);
		g2.drawLine(minX, minY, minX, minY  + AXIS_SMALL_LINE);
		
		int availableW = b.width - AXIS_END_SPACE;
		int lastX = minX;
		int numVals = barChart.getValues().size();
		int textY = minY + fm.getAscent() + SPACE2;
		for (int i=1; i <= numVals; i++) {
			int currX = minX + (int) (availableW / numVals * i);
			
			g2.setColor(Color.GRAY);
			g2.drawLine(currX, minY, currX, minY + AXIS_SMALL_LINE);
			
			g2.setColor(Color.BLACK);
			int num = barChart.getValues().get(i-1).getX();
			int textW = fm.stringWidth("" + num);
			g2.drawString("" + num, (lastX + currX) / 2 - textW / 2, textY);
			
			lastX = currX;
		}
		
		g2.setColor(Color.GRAY);
		g2.drawLine(maxX, minY, maxX - AXIS_ARROW_SIZE, minY - AXIS_ARROW_SIZE);
		g2.drawLine(maxX, minY, maxX - AXIS_ARROW_SIZE, minY + AXIS_ARROW_SIZE);
	}
	
	/**
	 * Draws the chart
	 * @param g2 graphics object
	 * @param data data to draw
	 * @param b rectangle where to draw
	 */
	private void drawData(Graphics2D g2, List<XYValue> data, Rectangle b) {
		int minX = (int) b.getMinX();
		//int maxX = (int) b.getMaxX();
		int maxY = (int) b.getMaxY();
		//int height = b.height;
		
		g2.setColor(GRAPH_COLOR);
		int lastX = minX + 1;
		int availableW = b.width - AXIS_END_SPACE;
		for (int i=0; i<data.size(); i++) {
			int newX = minX + (int) (availableW / data.size() * (i + 1));
			
			double yVal = Math.min(data.get(i).getY(), barChart.getyMax());
			int h = (int) (b.height * yVal / barChart.getyMax());
			int startY = maxY - h;
			//System.out.println(startY + " " + h);
			//g2.drawLine(lastX, startY, newX, startY);
			g2.fillRect(lastX, startY, newX-1 - lastX, h);
			
			lastX = newX;
		}
		
		g2.setColor(Color.BLACK);
	}
	
	
}

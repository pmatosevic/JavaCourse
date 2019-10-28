package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.awt.Rectangle;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import hr.fer.zemris.java.gui.calc.Calculator;

/**
 * A layout manager that puts elements in a grid and is used as a layout manager for {@link Calculator}.
 * 
 * @author Patrik
 *
 */
public class CalcLayout implements LayoutManager2 {

	/**
	 * Number of rows
	 */
	private static final int ROWS = 5;
	
	/**
	 * Number of columns
	 */
	private static final int COLS = 7;
	
	/**
	 * The space between rows and columns
	 */
	private int space;
	
	/**
	 * All elements
	 */
	private Component[][] grid = new Component[ROWS][COLS];
	
	/**
	 * Creates a new object with the space of 0.
	 */
	public CalcLayout() {
		this(0);
	}
	
	/**
	 * Creates a new object
	 * @param space space between rows and columns
	 */
	public CalcLayout(int space) {
		this.space = space;
	}
	
	
	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException("Operation is not supported.");
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		for (int i=0; i<ROWS; i++) {
			for (int j=0; j<COLS; j++) {
				if (grid[i][j] == comp) {
					grid[i][j] = null;
					return;
				}
			}
		}
	}
	
	/**
	 * Calculates the minimum or maximum size that is get using {@code dimGetter}
	 * @param dimGetter getter for the dimension
	 * @param minMax minimum or maximum operator
	 * @return the dimension
	 */
	private Dimension calcMinMaxSize(Function<Component, Dimension> dimGetter, BinaryOperator<Integer> minMax) {
		int width = -1;
		int height = -1;
		boolean notFound = true;
		for (int i=0; i<ROWS; i++) {
			for (int j=0; j<COLS; j++) {
				Component comp = grid[i][j];
				if (comp == null) continue;
				
				Dimension dim = dimGetter.apply(comp);
				if (dim == null) continue;
				
				int currWidth = (i==0 && j==0) ? (dim.width - 4*space)/5 : dim.width;
				
				if (notFound) {
					notFound = false;
					height = dim.height;
					width = currWidth;
				} else {
					height = minMax.apply(height, dim.height);
					width = minMax.apply(width, currWidth);
				}
				
			}
		}
		return notFound ? null :
			new Dimension((COLS-1)*space + COLS*width, (ROWS-1)*space + ROWS*height);
	}
	

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return calcMinMaxSize(Component::getPreferredSize, Math::max);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return calcMinMaxSize(Component::getMinimumSize, Math::max);
	}

	@Override
	public void layoutContainer(Container parent) {
		Insets ins = parent.getInsets();
		Dimension dim = parent.getSize();
		Rectangle r = new Rectangle(0, 0, 
				dim.width - ins.left - ins.right, dim.height - ins.top - ins.bottom);
		
		double availableW = r.width;
		double availableH = r.height;
		int lastX = 0;
		int lastY = 0;
		
		for (int row=0; row<ROWS; row++) {
			availableW = r.width;
			lastX = 0;
			
			double compHeight = (availableH - (ROWS-1 - row)*space) / (ROWS - row);
			int h = (int) compHeight;
			availableH -= h+space;
			
			for (int col=0; col<COLS; col++) {				
				double compWidth = (availableW - (COLS-1 - col)*space) / (COLS - col);
				if (row==0 && col==0) {
					compWidth = 5*compWidth + 4*space;
				}
				
				int w = (int) compWidth;
				availableW -= w+space;
				
				Component comp = grid[row][col];
				if (comp != null) {
					comp.setBounds(lastX, lastY, w, h);
				}
				lastX += w+space;
				
				if (row == 0 && col == 0) {
					col += 4;
				}
			}
			
			lastY += h+space;
		}
	}

	/**
	 * {@inheritDoc}
	 * @throws CalcLayoutException if an invalid position is provided
	 */
	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		RCPosition position;
		if (constraints instanceof String) {
			position = RCPosition.fromString((String) constraints);
		} else if (constraints instanceof RCPosition) {
			position = (RCPosition) constraints;
		} else {
			throw new CalcLayoutException("Invalid constraint.");
		}
		
		int row = position.getRow();
		int col = position.getColumn();
		
		if (row < 1 || row > 5 || col < 1 || col > 7
				|| (row == 1 && col > 1 && col < 6)) {
			throw new CalcLayoutException("Invalid requested position.");
		}
		if (grid[row-1][col-1] != null) {
			throw new CalcLayoutException("Component is already in requested position.");
		}
		
		grid[row-1][col-1] = comp;
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return calcMinMaxSize(Component::getMaximumSize, Math::min);
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
	}

	
	
	
	
}

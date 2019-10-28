package hr.fer.zemris.java.hw17.color;

import java.awt.Color;

/**
 * Interface that provides access to the current color.
 * 
 * @author Patrik
 *
 */
public interface IColorProvider {

	/**
	 * Returns the current color
	 * @return the current color
	 */
	public Color getCurrentColor();

	/**
	 * Adds the color change listener
	 * @param l the color change listener
	 */
	public void addColorChangeListener(ColorChangeListener l);

	/**
	 * Removes the color change listener
	 * @param l the color change listener
	 */
	public void removeColorChangeListener(ColorChangeListener l);
	
}
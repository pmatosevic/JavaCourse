package hr.fer.zemris.java.hw17.color;

import java.awt.Color;

/**
 * A listener for the color change event
 * @author Patrik
 *
 */
public interface ColorChangeListener {
	
	/**
	 * Method that is called on each color change
	 * @param source color provider
	 * @param oldColor old color
	 * @param newColor new color
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);

}
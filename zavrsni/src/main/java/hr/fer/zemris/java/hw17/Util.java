package hr.fer.zemris.java.hw17;

import java.awt.Point;

/**
 * Class with static utility methods.
 * 
 * @author Patrik
 *
 */
public class Util {

	/**
	 * Parses the string into a point.
	 * 
	 * @param text string
	 * @return point
	 */
	public static Point parsePoint(String text) {
		try {
			String[] parts = text.split(",", 2);
			return new Point(Integer.parseInt(parts[0].trim()), Integer.parseInt(parts[1].trim()));
		} catch (NumberFormatException | IndexOutOfBoundsException e) {
			throw new IllegalArgumentException("Illegal format of point: " + text);
		}
	}
	
}

package coloring.algorithms;

import java.util.Objects;

/**
 * Represents a pixel coordinates.
 * @author Patrik
 *
 */
public class Pixel {

	/**
	 * X coordinate
	 */
	private int x;
	
	/**
	 * Y coordinate
	 */
	private int y;

	/**
	 * Creates a new object
	 * @param x x
	 * @param y y
	 */
	public Pixel(int x, int y) {
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Pixel))
			return false;
		Pixel other = (Pixel) obj;
		return x == other.x && y == other.y;
	}
	
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
	
	
}

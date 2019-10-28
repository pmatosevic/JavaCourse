package hr.fer.zemris.math;

import java.util.Objects;

/**
 * Represents a 2-dimensional vector with horizontal and vertical component.
 * 
 * @author Patrik
 *
 */
public class Vector2D {

	
	private static final double EPS = 1e-6;
	
	/**
	 * Horizontal (x) component
	 */
	private double x;
	
	/**
	 * Vectical (y) component
	 */
	private double y;
	
	
	/**
	 * Creates a new {@code Vector2D} with given x and y components
	 * @param x x component
	 * @param y y component
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns the x component of the vector
	 * @return the x component of the vector
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Returns the y component of the vector
	 * @return the y component of the vector
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Translates this vector by {@code offset} vector
	 * @param offset offset to translate this vector
	 */
	public void translate(Vector2D offset) {
		x += offset.x;
		y += offset.y;
	}
	
	/**
	 * Returns a new vector as a result of translating this vector by {@code offset}
	 * @param offset offset to translate this vector
	 * @return a new vector as a result of translating this vector by {@code offset}
	 */
	public Vector2D translated(Vector2D offset) {
		return new Vector2D(this.x + offset.x, this.y + offset.y);
	}
	
	/**
	 * Rotates this vector by {@code angle} radians.
	 * @param angle angle in radians
	 */
	public void rotate(double angle) {
		double rotX = x * Math.cos(angle) - y * Math.sin(angle);
		double rotY = x * Math.sin(angle) + y * Math.cos(angle);
		x = rotX;
		y = rotY;
	}
	
	/**
	 * Returns a new vector as a result of rotating this vector by {@code angle} radians.
	 * @param angle angle in radians
	 * @return a new vector as a result of rotating this vector by {@code angle} radians
	 */
	public Vector2D rotated(double angle) {
		double rotX = x * Math.cos(angle) - y * Math.sin(angle);
		double rotY = x * Math.sin(angle) + y * Math.cos(angle);
		return new Vector2D(rotX, rotY);
	}
	
	/**
	 * Scales this vector by {@code scaler}
	 * @param scaler value to scale this vector by
	 */
	public void scale(double scaler) {
		x *= scaler;
		y *= scaler;
	}
	
	/**
	 * Returns a new vector as a result of scaling this vector by {@code scaler}.
	 * @param scaler value to scale this vector by
	 * @return a new vector as a result of scaling this vector by {@code scaler}
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(x * scaler, y * scaler);
	}
	
	/**
	 * Returns a new vector with the same values as this vector
	 * @return a new vector with the same values as this vector
	 */
	public Vector2D copy() {
		return new Vector2D(x, y);
	}

	
	
	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	/**
	 * {@inheritDoc}
	 * Two vectors are considered equal if their x and y parts differs at most 1E-6.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Vector2D))
			return false;
		Vector2D other = (Vector2D) obj;
		return Math.abs(this.x - other.x) < EPS && Math.abs(this.y - other.y) < EPS;
	}
	
	
	
	
	
}

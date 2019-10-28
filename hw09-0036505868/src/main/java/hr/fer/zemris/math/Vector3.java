package hr.fer.zemris.math;


/**
 * Represents a vector that stores 3 components.
 * 
 * @author Patrik
 *
 */
public class Vector3 {

	/**
	 * x-component
	 */
	private double x;
	
	/**
	 * y-component
	 */
	private double y;
	
	/**
	 * z-component
	 */
	private double z;

	/**
	 * Creates a new vector
	 * @param x x component
	 * @param y y component
	 * @param z z component
	 */
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Returns the norm of the vector.
	 * 
	 * @return norm
	 */
	public double norm() {
		return Math.sqrt(x*x + y*y + z*z);
	}
	
	/**
	 * Returns normalized version of current vector, as a new vector.
	 * 
	 * @return normalized vector
	 */
	public Vector3 normalized() {
		double norm = norm();
		return new Vector3(x / norm, y / norm, y / norm);
	}
	
	/**
	 * Returns a new vector as a result of addition this + other.
	 * @param other vector to add
	 * @return the sum
	 */
	public Vector3 add(Vector3 other) {
		return new Vector3(x + other.x, y + other.y, z + other.z);
	}
	
	/**
	 * Returns a new vector as a result of addition this and negate other.
	 * @param other vector to subtract
	 * @return the difference
	 */
	public Vector3 sub(Vector3 other) {
		return new Vector3(x - other.x, y - other.y, z - other.z);
	}
	
	/**
	 * Returns the dot product of this and other vector
	 * @param other vector
	 * @return the dot product
	 */
	public double dot(Vector3 other) {
		return x*other.x + y*other.y + z*other.z;
	}
	
	/**
	 * Returns a new vector, result of the cross product of this and other vector
	 * @param other vector
	 * @return the cross product
	 */
	public Vector3 cross(Vector3 other) {
		double resX = y * other.z - z * other.y;
		double resY = z * other.x - x * other.z;
		double resZ = x * other.y - y * other.x;
		return new Vector3(resX, resY, resZ);
	}
	
	/**
	 * Returns a new vector, this vector scaled
	 * @param s scale value
	 * @return scaled vector
	 */
	public Vector3 scale(double s) {
		return new Vector3(x*s, y*s, z*s);
	}
	
	/**
	 * Calculates the cosine of the angle between this and other vector
	 * @param other vector
	 * @return cosine of the angle
	 */
	public double cosAngle(Vector3 other) {
		return dot(other) / (this.norm() * other.norm());
	}

	/**
	 * @return the x component
	 */
	public double getX() {
		return x;
	}

	/**
	 * @return the y component
	 */
	public double getY() {
		return y;
	}

	/**
	 * @return the z component
	 */
	public double getZ() {
		return z;
	}
	
	/**
	 * Returns this vector as an array
	 * @return this vector as an array
	 */
	public double[] toArray() {
		return new double[] {x, y, z};
	}
	
	@Override
	public String toString() {
		return String.format("(%f, %f, %f)", x, y, z);
	}
	
}

package hr.fer.zemris.java.hw17.trazilica.model;

/**
 * Represents a vector in the N-dimensional space.
 * 
 * @author Patrik
 *
 */
public class Vector {

	/**
	 * Components of the vector
	 */
	private double[] components;
	
	/**
	 * Creates a new vector.
	 * 
	 * @param size vector dimension
	 */
	public Vector(int size) {
		components = new double[size];
	}
	
	/**
	 * Returns the vector dimension.
	 * 
	 * @return the vector dimension
	 */
	public int size() {
		return components.length;
	}
	
	/**
	 * Returns the component at the index.
	 * 
	 * @param index index
	 * @return the component at the index
	 */
	public double getComponent(int index) {
		return components[index];
	}
	
	/**
	 * Sets the component at the index.
	 * 
	 * @param index index
	 * @param value value to be set
	 */
	public void setComponent(int index, double value) {
		components[index] = value;
	}
	
	/**
	 * Returns the norm of the vector.
	 * 
	 * @return norm
	 */
	public double norm() {
		double sum = 0;
		for (double d : components) {
			sum += d*d;
		}
		return Math.sqrt(sum);
	}
	
	/**
	 * Returns the dot product of this and other vector.
	 * 
	 * @param other vector
	 * @return the dot product
	 */
	public double dot(Vector other) {
		if (other.size() != size()) {
			throw new IllegalArgumentException("Vectors must have the same size.");
		}
		
		double sum = 0;
		for (int i=0; i<components.length; i++) {
			sum += components[i] * other.components[i];
		}
		return sum;
	}
	
	/**
	 * Calculates the cosine of the angle between this and other vector.
	 * 
	 * @param other vector
	 * @return cosine of the angle
	 */
	public double cosAngle(Vector other) {
		return dot(other) / (this.norm() * other.norm());
	}
	
	/**
	 * Multiplies the vector by the other vector element-wise.
	 * 
	 * @param other vector
	 * @return the resulting vector
	 */
	public Vector multiply(Vector other) {
		if (other.size() != size()) {
			throw new IllegalArgumentException("Vectors must have the same size.");
		}
		
		Vector result = new Vector(components.length);
		for (int i=0; i<components.length; i++) {
			result.components[i] = components[i] * other.components[i];
		}
		return result;
	}
	
}

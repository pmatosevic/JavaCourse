package hr.fer.zemris.math;

import java.util.Arrays;

/**
 * Represents a complex polynomial in a rooted form.
 * 
 * @author Patrik
 *
 */
public class ComplexRootedPolynomial {
	
	/**
	 * The constant
	 */
	private Complex constant;
	
	/**
	 * The roots
	 */
	private Complex[] roots;
	
	
	/**
	 * Creates a new complex polynomial
	 * @param constant the constant
	 * @param roots the roots
	 */
	public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
		this.constant = constant;
		this.roots = Arrays.copyOf(roots, roots.length);
	}
	/**
	 * Computes polynomial value at given point z.
	 * 
	 * @param z complex number
	 * @return complex number
	 */
	public Complex apply(Complex z) {
		Complex result = constant;
		for (Complex root : roots) {
			result = result.multiply(z.sub(root));
		}
		return result;
	}
	/**
	 * Converts this representation to {@code ComplexPolynomial} type
	 * @return complex polynomial form of this polynomial
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial poly = new ComplexPolynomial(constant);
		for (Complex root : roots) {
			poly = poly.multiply(new ComplexPolynomial(root.negate(), Complex.ONE));
		}
		return poly;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(" + constant + ")");
		for (Complex root : roots) {
			sb.append("*(" + "z" + "-(" + root + "))");
		}
		return sb.toString();
	}
	
	
	/**
	 * Finds index of closest root for given complex number z that is within
	 * threshold; if there is no such root, returns -1.
	 * 
	 * @param z complex number
	 * @param treshold threshold
	 * @return index of the closest root
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int index = -1;
		double closest = treshold;
		
		for (int i = 0; i < roots.length; i++) {
			double dist = z.sub(roots[i]).module();
			if (dist < closest) {
				index = i;
				closest = dist;
			}
		}
		
		return index;
	}
}
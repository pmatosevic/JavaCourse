package hr.fer.zemris.math;

import java.util.Arrays;

/**
 * Represents a polynomial of a complex variable whose factors are complex numbers.
 * 
 * @author Patrik
 *
 */
public class ComplexPolynomial {
	
	/**
	 * The factors
	 */
	private Complex[] factors;
	
	/**
	 * The order
	 */
	private short n;
	
	/**
	 * Creates a new complex polynomial with given factors
	 * @param factors the factors
	 * @throws IllegalArgumentException if none factor is given
	 */
	public ComplexPolynomial(Complex ... factors) {
		if (factors.length == 0) {
			throw new IllegalArgumentException("Expected at least 1 factor.");
		}
		
		this.factors = Arrays.copyOf(factors, factors.length);
		this.n = (short) (factors.length - 1);
	}
	
	/**
	 * Returns the order of the polynomial
	 * @return the order of the polynomial
	 */
	public short order() {
		return n;
	}

	/**
	 * Computes a new polynomial, this polynomial multiplied by p
	 * 
	 * @param p polynomial to multiply with
	 * @return complex polynomial
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] mulFactors = new Complex[this.n + p.n + 1];
		Arrays.fill(mulFactors, Complex.ZERO);
		for (int i = 0; i <= this.n; i++) {
			for (int j = 0; j <= p.n; j++) {
				Complex tmp = this.factors[i].multiply(p.factors[j]);
				mulFactors[i + j] = mulFactors[i + j].add(tmp);
			}
		}
		return new ComplexPolynomial(mulFactors);
	}


	/**
	 * Computes the first derivative of this polynomial
	 * 
	 * @return complex polynomial
	 */
	public ComplexPolynomial derive() {
		if (n == 0) {
			return new ComplexPolynomial(Complex.ZERO);
		}
		
		Complex[] result = new Complex[n];
		for (int i=1; i<=n; i++) {
			result[i-1] = factors[i].multiply(new Complex(i, 0));
		}
		return new ComplexPolynomial(result);
	}
	
	/**
	 * Computes polynomial value at given point z
	 * @param z complex number
	 * @return complex number
	 */
	public Complex apply(Complex z) {
		Complex result = factors[0];
		Complex zPow = Complex.ONE;
		for (int i=1; i<=n; i++) {
			zPow = zPow.multiply(z);
			result = result.add(factors[i].multiply(zPow));	
		}
		return result;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i=n; i>0; i--) {
			sb.append("(" + factors[i] + ")" + "z^" + i + "+");
		}
		sb.append("(" + factors[0] + ")");
		return sb.toString();
	}
}

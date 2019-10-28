package hr.fer.zemris.java.hw02;

import java.util.Objects;


/**
 * Represents a complex number with real and imaginary part.
 * Two complex numbers are considered equal if their real and imaginary parts differs at most 1E-6.
 * 
 * 
 * @author Patrik
 *
 */
public class ComplexNumber {

	/**
	 * Maximal difference between real and between imaginary parts of two complex numbers
	 * to be considered equal
	 */
	private static final double EPS = 1e-6;
	
	/**
	 * Real part of the complex number
	 */
	private double real;
	
	/**
	 * Imaginary part of the complex number
	 */
	private double imaginary;

	
	
	/**
	 * Constructs a new {@code ComplexNumber} with given real and imaginary part.
	 * 
	 * @param real real part
	 * @param imaginary imaginary part
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	
	
	
	/**
	 * Returns a new {@code ComplexNumber} with given real part and 0 as imaginary part.
	 * 
	 * @param real real part
	 * @return a new {@code ComplexNumber}
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}
	
	
	
	/**
	 * Returns a new {@code ComplexNumber} with given imaginary part and 0 as real part.
	 * 
	 * @param imaginary imaginary part
	 * @return a new {@code ComplexNumber}
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}
	
	
	
	/**
	 * Returns a new {@code ComplexNumber} with given magnitude and angle.
	 * 
	 * @param magnitude magnitude
	 * @param angle angle in radians
	 * @return a new {@code ComplexNumber} with given magnitude and angle
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
	}
	
	
	
	/**
	 * Returns a new {@code ComplexNumber} by paring a string representation of a complex number.
	 * 
	 * @param s string representation of the complex number
	 * @return a new {@code ComplexNumber}
	 * @throws NumberFormatException if {@code s} is not in a valid string format of a complex number
	 */
	public static ComplexNumber parse(String s) {
		try {			
			int length = s.length();
			int bound = Math.max(s.lastIndexOf('+'), s.lastIndexOf('-'));
			String realPart = "0";
			String imagPart = "0";
			
			if (bound == -1 || bound == 0) {		// only real or only imaginary part
				if (s.indexOf('i') == -1) {			// only real
					realPart = s;
				} else {							// only imaginary
					imagPart = s.substring(0, length - 1);
					imagPart = imagPart.isEmpty() ? "+" : imagPart; 		// handling "i"
				}
				
			} else {								// both parts are in s
				if (s.charAt(length - 1) != 'i') {
					throw new NumberFormatException();
				}
				
				realPart = s.substring(0, bound);
				imagPart = s.substring(bound, length - 1);
			}
			
			if (imagPart.length() == 1 && (imagPart.charAt(0) == '+' || imagPart.charAt(0) == '-')) {		// handling "+i" and "-i"
				imagPart = imagPart + "1";
			}
			
			if (realPart.contains(" ") || imagPart.contains(" ")) {
				throw new NumberFormatException();
			}
			
			
			double re = Double.parseDouble(realPart);
			double im = Double.parseDouble(imagPart);
			return new ComplexNumber(re, im);
			
		} catch (NumberFormatException ex) {
			throw new ComplexNumberFormatException(s);
		}
	}
	
	
	
	
	
	/**
	 * Returns the real part of the complex number.
	 * 
	 * @return the real part of the complex number
	 */
	public double getReal() {
		return real;
	}

	
	
	/**
	 * Returns the imaginary part of the complex number.
	 * 
	 * @return the imaginary part of the complex number
	 */
	public double getImaginary() {
		return imaginary;
	}
	
	
	
	/**
	 * Returns the magnitude of the complex number.
	 * 
	 * @return the magnitude of the complex number
	 */
	public double getMagnitude() {
		return Math.sqrt(real * real + imaginary * imaginary);
	}
	
	
	
	/**
	 * Returns the angle in radians from interval [0, 2*<i>pi</i>> of the complex number.
	 * 
	 * @return the angle in radians of the complex number
	 */
	public double getAngle() {
		double atan = Math.atan2(imaginary, real);
		return atan >= 0 ? atan : atan + 2 * Math.PI;
	}
	
	
	
	/**
	 * Returns a new {@code ComplexNumber} as sum of this and given complex number.
	 * 
	 * @param c {@code ComplexNumber} to add
	 * @return a new {@code ComplexNumber} as sum of this and given complex number
	 * @throws NullPointerException if {@code c} is {@code null}
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(this.real + c.real, this.imaginary + c.imaginary);
	}
	
	
	
	/**
	 * Returns a new {@code ComplexNumber} as difference of this and given complex number.
	 * 
	 * @param c {@code ComplexNumber} to subtract
	 * @return a new {@code ComplexNumber} as difference of this and given complex number
	 * @throws NullPointerException if {@code c} is {@code null}
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(this.real - c.real, this.imaginary - c.imaginary);
	}
	
	
	
	/**
	 * Returns a new {@code ComplexNumber} as product of this and given complex number.
	 * 
	 * @param c {@code ComplexNumber} to multiply with
	 * @return a new {@code ComplexNumber} as product of this and given complex number
	 * @throws NullPointerException if {@code c} is {@code null}
	 */
	public ComplexNumber mul(ComplexNumber c) {
		double re = this.real * c.real - this.imaginary * c.imaginary;
		double im = this.real * c.imaginary + this.imaginary * c.real;
		return new ComplexNumber(re, im);
	}
	
	
	
	/**
	 * Returns a new {@code ComplexNumber} as division of this and given complex number.
	 * 
	 * @param c {@code ComplexNumber} to divide with
	 * @return a new {@code ComplexNumber} as division of this and given complex number
	 * @throws NullPointerException if {@code c} is {@code null}
	 */
	public ComplexNumber div(ComplexNumber c) {
		double re = this.real * c.real + this.imaginary * c.imaginary;
		double im = this.imaginary * c.real - this.real * c.imaginary;
		double denominator = square(c.real) + square(c.imaginary);
		return new ComplexNumber(re / denominator, im / denominator);
	}
	
	
	
	/**
	 * Returns a new {@code ComplexNumber} equal to this complex number to the power of {@code n}.
	 * 
	 * @param n power
	 * @return a new {@code ComplexNumber} equal to this complex number to the power of {@code n}.
	 * @throws IllegalArgumentException if the power ({@code n}) is negative. 
	 */
	public ComplexNumber power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Power should be >= 0!");
		}
		
		return fromMagnitudeAndAngle(Math.pow(getMagnitude(), n), n * getAngle());
	}
	
	
	
	/**
	 * Returns an array of {@code ComplexNumber} which are nth roots of this complex number.
	 * The resulting array is ordered by increasing angle (argument) of complex numbers.
	 * 
	 * @param n nth root
	 * @return an array of {@code ComplexNumber} which are nth roots of this complex number
	 * @throws IllegalArgumentException if {@code n} is not positive
	 */
	public ComplexNumber[] root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("Root should be > 0!");
		}
		
		ComplexNumber[] result = new ComplexNumber[n];
		double magnitude = Math.pow(getMagnitude(), 1.0 / n);
		double angle = getAngle() / n;
		double delta = 2 * Math.PI / n;
		for (int i = 0; i < n; i++) {
			result[i] = fromMagnitudeAndAngle(magnitude, angle + i * delta);
		}
		return result;
	}
	
	
	
	
	
	@Override
	public String toString() {
		return String.format("%.4f%+.4f", real, imaginary);
	}
	
	
	
	
	@Override
	public int hashCode() {
		return Objects.hash(imaginary, real);
	}

	/**
	 * {@inheritDoc}
	 * Two complex numbers are considered equal if their real and imaginary parts differs at most 1E-6.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ComplexNumber))
			return false;
		ComplexNumber other = (ComplexNumber) obj;
		if (Math.abs(this.real - other.real) > EPS) {
			return false;
		}
		if (Math.abs(this.imaginary - other.imaginary) > EPS) {
			return false;
		}
		return true;
	}
	
	
	/**
	 * Returns square of a number.
	 * @param a a number
	 * @return the square of the number
	 */
	private double square(double a) {
		return a * a;
	}
	
}

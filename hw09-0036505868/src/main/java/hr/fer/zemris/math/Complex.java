package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Represents a complex number with real and imaginary part.
 * Two complex numbers are considered equal if their real and imaginary parts differs at most 1E-6.
 * 
 * 
 * @author Patrik
 *
 */
public class Complex {

	
	/**
	 * 0 + 0i
	 */
	public static final Complex ZERO = new Complex(0,0);
	
	/**
	 * 1 + 0i
	 */
	public static final Complex ONE = new Complex(1,0);
	
	/**
	 * -1 + 0i
	 */
	public static final Complex ONE_NEG = new Complex(-1,0);
	
	/**
	 * 0 + 1i
	 */
	public static final Complex IM = new Complex(0,1);
	
	/**
	 * 0 - 1i
	 */
	public static final Complex IM_NEG = new Complex(0,-1);
	
	
	/**
	 * Maximal difference between real and between imaginary parts of two complex numbers
	 * to be considered equal
	 */
	private static final double EPS = 1e-6;
	
	/**
	 * Real part of the complex number
	 */
	private double re;
	
	/**
	 * Imaginary part of the complex number
	 */
	private double im;

	
	/**
	 * Constructs a complex number 0+0i
	 */
	public Complex() {
	}
	
	/**
	 * Constructs a new complex number with given real and imaginary part.
	 * 
	 * @param re real part
	 * @param im imaginary part
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}
	
	/**
	 * Returns the real part of the complex number.
	 * 
	 * @return the real part of the complex number
	 */
	public double getReal() {
		return re;
	}

	
	
	/**
	 * Returns the imaginary part of the complex number.
	 * 
	 * @return the imaginary part of the complex number
	 */
	public double getImaginary() {
		return im;
	}
	
	
	
	/**
	 * Returns the magnitude of the complex number.
	 * 
	 * @return the magnitude of the complex number
	 */
	public double module() {
		return Math.sqrt(re * re + im * im);
	}
	
	
	/**
	 * Returns a new complex number as sum of this and given complex number.
	 * 
	 * @param c complex number to add
	 * @return a new complex number as sum of this and given complex number
	 */
	public Complex add(Complex c) {
		return new Complex(this.re + c.re, this.im + c.im);
	}
	
	
	
	/**
	 * Returns a new complex number as difference of this and given complex number.
	 * 
	 * @param c complex number to subtract
	 * @return a new complex number as difference of this and given complex number
	 */
	public Complex sub(Complex c) {
		return new Complex(this.re - c.re, this.im - c.im);
	}
	
	
	
	/**
	 * Returns a new complex number as product of this and given complex number.
	 * 
	 * @param c complex number to multiply with
	 * @return a new complex number as product of this and given complex number
	 */
	public Complex multiply(Complex c) {
		double real = this.re * c.re - this.im * c.im;
		double imag = this.re * c.im + this.im * c.re;
		return new Complex(real, imag);
	}
	
	
	
	/**
	 * Returns a new complex number as division of this and given complex number.
	 * 
	 * @param c complex number to divide with
	 * @return a new complex number as division of this and given complex number
	 */
	public Complex divide(Complex c) {
		double real = this.re * c.re + this.im * c.im;
		double imag = this.im * c.re - this.re * c.im;
		double denominator = square(c.re) + square(c.im);
		return new Complex(real / denominator, imag / denominator);
	}
	
	
	/**
	 * Returns a new complex number, negated current complex number
	 * @return negated current complex number
	 */
	public Complex negate() {
		return new Complex(-re, -im);
	}
	
	
	
	/**
	 * Returns a new complex number equal to this complex number to the power of {@code n}.
	 * 
	 * @param n power
	 * @return a new complex number equal to this complex number to the power of {@code n}.
	 * @throws IllegalArgumentException if the power ({@code n}) is negative. 
	 */
	public Complex power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Power should be >= 0!");
		}
		
		return fromMagnitudeAndAngle(Math.pow(module(), n), n * getAngle());
	}
	
	
	
	/**
	 * Returns a list of complex numbers which are nth roots of this complex number.
	 * The resulting array is ordered by increasing angle (argument) of complex numbers.
	 * 
	 * @param n nth root
	 * @return an array of {@code ComplexNumber} which are nth roots of this complex number
	 * @throws IllegalArgumentException if {@code n} is not positive
	 */
	public List<Complex> root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("Root should be > 0!");
		}
		
		List<Complex> result = new ArrayList<>(n);
		double magnitude = Math.pow(module(), 1.0 / n);
		double angle = getAngle() / n;
		double delta = 2 * Math.PI / n;
		for (int i = 0; i < n; i++) {
			result.add(fromMagnitudeAndAngle(magnitude, angle + i * delta));
		}
		return result;
	}
	
	
	/**
	 * Returns a new complex number with given magnitude and angle.
	 * 
	 * @param magnitude magnitude
	 * @param angle angle in radians
	 * @return a new complex number with given magnitude and angle
	 */
	private static Complex fromMagnitudeAndAngle(double magnitude, double angle) {
		return new Complex(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
	}
	
	
	@Override
	public String toString() {
		return String.format("%.2f%+.2fi", re, im);
	}
	
	
	
	
	/**
	 * Returns a new complex number by paring a string representation of a complex number.
	 * Expects a complex number in the format "x+iy", and can contain arbitrary number of spaces
	 * between plus or minus sign or even digits.
	 * 
	 * @param s string representation of the complex number
	 * @return a new complex number
	 * @throws NumberFormatException if {@code s} is not in a valid string format of a complex number
	 */
	public static Complex parse(String s) {
		if (s.isBlank()) {
			throw new NumberFormatException("String cannot be empty.");
		}
		s = s.replaceAll("\\s+", "");
		
		String realPart = "0";
		String imagPart = "0";
		
		int bound = s.indexOf("i");
		if (bound == -1) {
			realPart = s;
		} else if (bound == 0) {
			imagPart = s.substring(1);
		} else if (bound == 1) {
			imagPart = s.substring(0, 1) + s.substring(2);
		} else {
			realPart = s.substring(0, bound - 1);
			imagPart = s.substring(bound - 1, bound) + s.substring(bound + 1);
		}
		
		if (imagPart.isEmpty() || imagPart.equals("+") || imagPart.equals("-")) {
			imagPart = imagPart + "1";
		}
		
		try {
			return new Complex(Double.parseDouble(realPart), Double.parseDouble(imagPart));
		} catch (NumberFormatException e) {
			throw new NumberFormatException("Invalid format: " + s);
		}
	}
	
	
	
	/**
	 * Returns the angle in radians from interval [0, 2*<i>pi</i>> of the complex number.
	 * 
	 * @return the angle in radians of the complex number
	 */
	private double getAngle() {
		double atan = Math.atan2(im, re);
		return atan >= 0 ? atan : atan + 2 * Math.PI;
	}
	

	
	@Override
	public int hashCode() {
		return Objects.hash(im, re);
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
		if (!(obj instanceof Complex))
			return false;
		Complex other = (Complex) obj;
		if (Math.abs(this.re - other.re) > EPS) {
			return false;
		}
		if (Math.abs(this.im - other.im) > EPS) {
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
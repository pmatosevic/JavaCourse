package hr.fer.zemris.java.hw02;


/**
 * Thrown to indicate that a method has been passed an invalid string of a complex number.
 * 
 * @author Patrik
 *
 */
public class ComplexNumberFormatException extends NumberFormatException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs an {@code ComplexNumberFormatException}.
	 * @param s given string representation of a complex number
	 */
	public ComplexNumberFormatException(String s) {
		super("Illegal format of complex number: " + s);
	}
	
}

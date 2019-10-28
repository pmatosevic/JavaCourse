package hr.fer.zemris.java.gui.layouts;

/**
 * An exception that is thrown when invalid layout position is provided.
 * 
 * @author Patrik
 *
 */
public class CalcLayoutException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new exception
	 */
	public CalcLayoutException() {
		super();
	}

	/**
	 * Creates a new exception with message
	 * @param message message
	 */
	public CalcLayoutException(String message) {
		super(message);
	}

	
	
}

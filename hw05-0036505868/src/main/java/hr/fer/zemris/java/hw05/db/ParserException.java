package hr.fer.zemris.java.hw05.db;

/**
 * Thrown to indicate an error during parsing a query.
 * 
 * @author Patrik
 *
 */
public class ParserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new {@code ParserException}
	 */
	public ParserException() {
		super();
	}
	
	/**
	 * Creates a new {@code ParserException} with given message
	 * @param s message
	 */
	public ParserException(String s) {
		super(s);
	}
	
	/**
	 * Creates a new {@code ParserException} with given throwable
	 * @param t throwable
	 */
	public ParserException(Throwable t) {
		super(t);
	}
	
}
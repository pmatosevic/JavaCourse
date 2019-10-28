package hr.fer.zemris.java.hw15.dao;

/**
 * Represents an exception in {@link DAO}.
 * 
 * @author Patrik
 *
 */
public class DAOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new exception
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates a new exception
	 * @param message message
	 */
	public DAOException(String message) {
		super(message);
	}
}
package hr.fer.zemris.java.hw14.dao;

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
	public DAOException() {
	}

	/**
	 * Creates a new exception
	 * @param message message
	 * @param cause cause
	 * @param enableSuppression enable suppression
	 * @param writableStackTrace writable stack trace
	 */
	public DAOException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Creates a new exception
	 * @param message message
	 * @param cause cause
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

	/**
	 * Creates a new exception
	 * @param cause cause
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}
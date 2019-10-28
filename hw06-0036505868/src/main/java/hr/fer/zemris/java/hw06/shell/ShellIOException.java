package hr.fer.zemris.java.hw06.shell;

/**
 * Represents a shell exception during reading or writing to the user.
 * 
 * @author Patrik
 *
 */
public class ShellIOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new {@code ShellIOException}
	 */
	public ShellIOException() {
	}

	/**
	 * Creates a new {@code ShellIOException}
	 * @param message message
	 */
	public ShellIOException(String message) {
		super(message);
	}

	/**
	 * Creates a new {@code ShellIOException}
	 * @param cause cause
	 */
	public ShellIOException(Throwable cause) {
		super(cause);
	}

	/**
	 * Creates a new {@code ShellIOException}
	 * @param message message
	 * @param cause cause
	 */
	public ShellIOException(String message, Throwable cause) {
		super(message, cause);
	}

}

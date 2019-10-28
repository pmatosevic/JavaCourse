package hr.fer.zemris.java.custom.scripting.exec;

/**
 * An exception that is thrown during the execution of SmartScripts.
 * 
 * @author Patrik
 *
 */
public class ExecutionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new exception
	 */
	public ExecutionException() {
		super();
	}

	/**
	 * Creates a new exception
	 * @param message message
	 */
	public ExecutionException(String message) {
		super(message);
	}

	
	
}

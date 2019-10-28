package hr.fer.zemris.java.custom.collections;


/**
 * 
 * Thrown to indicate that a method, that requires non empty stack, was passed an empty stack.
 * 
 * @author Patrik
 *
 */
public class EmptyStackException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new {@code EmptyStackException}
	 */
	public EmptyStackException() {
		super();
	}
	
	/**
	 * Constructs a new {@code EmptyStackException} with given message
	 * @param s message
	 */
	public EmptyStackException(String s) {
		super(s);
	}
	
}

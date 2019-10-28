package hr.fer.zemris.java.custom.collections.demo;

/**
 * 
 * Thrown to indicate that a method has been passed an invalid expression.
 * 
 * @author Patrik
 *
 */
public class ExpressionEvaluationException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs an {@code ExpressionEvaluationException} with the
     * specified detail message.
	 * @param s message
	 */
	public ExpressionEvaluationException(String s) {
		super(s);
	}
	
	/**
	 * Constructs an {@code ExpressionEvaluationException}
	 */
	public ExpressionEvaluationException() {
		super("Invalid expression.");
	}
	
}

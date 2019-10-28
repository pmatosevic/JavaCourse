package hr.fer.zemris.java.hw05.db.lexer;


/**
 * Represents an error in lexer.
 * 
 * @author Patrik
 *
 */
public class LexerException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;


	/**
	 * Creates a new {@code LexerException}
	 */
	public LexerException() {
		super(); 
	}
	
	
	/**
	 * Creates a new {@code LexerException} with message.
	 * @param s message
	 */
	public LexerException(String s) {
		super(s);
	}
	
}

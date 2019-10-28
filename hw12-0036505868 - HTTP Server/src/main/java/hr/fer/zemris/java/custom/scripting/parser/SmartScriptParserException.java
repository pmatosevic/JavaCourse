package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Thrown to indicate an error during parsing a document.
 * 
 * @author Patrik
 *
 */
public class SmartScriptParserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new {@code SmartScriptParserException}
	 */
	public SmartScriptParserException() {
		super();
	}
	
	/**
	 * Creates a new {@code SmartScriptParserException} with given message
	 * @param s message
	 */
	public SmartScriptParserException(String s) {
		super(s);
	}
	
}

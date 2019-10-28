package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * 
 * Represents a Token.
 * 
 * @author Patrik
 *
 */
public class Token {
	
	/**
	 * token type
	 */
	private TokenType type;
	
	/**
	 * token value
	 */
	private Object value;
	
	/**
	 * Creates a new {@code Token}
	 * @param type token type
	 * @param value value
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Returns the token value
	 * @return the token value
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * Returns the token type
	 * @return the token type
	 */
	public TokenType getType() {
		return type;
	}
	
	
	
	
}

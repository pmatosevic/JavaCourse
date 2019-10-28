package hr.fer.zemris.java.hw05.db.lexer;

/**
 * All token types for {@link Lexer}
 * 
 * @author Patrik
 *
 */
public enum TokenType {
	
	/** Marks no token left. */
	EOF, 
	
	/** Marks a symbol */
	SYMBOL,
	
	/** Marks a string literal */
	STRING,
	
	/** Marks a piece of letters */
	WORD
	
}

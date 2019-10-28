package hr.fer.zemris.java.hw03.prob1;

/**
 * All token types for {@link Lexer}
 * 
 * @author Patrik
 *
 */
public enum TokenType {
	
	/** Marks no token left. */
	EOF, 
	
	/** Marks a word */
	WORD, 
	
	/** Marks a number */
	NUMBER, 
	
	/** Marks a symbol */
	SYMBOL;
	
}

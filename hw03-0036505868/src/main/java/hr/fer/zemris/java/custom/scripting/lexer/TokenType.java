package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * All token types for {@link Lexer}
 * 
 * @author Patrik
 *
 */
public enum TokenType {
	
	/** Marks no token left. */
	EOF, 
	
	/** Marks plain text */
	TEXT,
	
	/** Marks a string */
	STRING, 
	
	/** Marks a whole number */
	INTEGER, 
	
	/** Marks a decimal number */
	DOUBLE,
	
	/** Marks a variable */
	VARIABLE,
	
	/** Marks a function */
	FUNCTION,
	
	/** Marks a operator */
	OPERATOR,
	
	/** Marks a "{$" */
	TAG_BEGIN,
	
	/** Marks a "$}" */
	TAG_END,
	
	/** Marks a "=" */
	EQUALS;
	
}

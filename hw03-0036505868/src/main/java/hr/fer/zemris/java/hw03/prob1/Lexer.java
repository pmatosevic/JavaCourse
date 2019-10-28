package hr.fer.zemris.java.hw03.prob1;

import java.util.Objects;

/**
 * 
 * Represents a lexer. Lazily reads given text and returns tokens.
 * 
 * @author Patrik
 *
 */
public class Lexer {
	
	/**
	 * Input text
	 */
	private char[] data;
	
	/**
	 * Current token
	 */
	private Token token;
	
	/**
	 * Index of the first non extracted character
	 */
	private int currentIndex;

	/**
	 * Current lexer state
	 */
	private LexerState state = LexerState.BASIC;
	
	
	/**
	 * Creates a new {@code Lexer} with given text to tokenize.
	 * 
	 * @param text text to tokenize
	 */
	public Lexer(String text) {
		this.data = text.toCharArray();
	}
	
	
	/**
	 * Sets the lexer state.
	 * @param state state
	 * @throws NullPointerException if {@code state} is {@code null}
	 */
	public void setState(LexerState state) {
		Objects.requireNonNull(state);
		this.state = state;
	}
	
	
	/**
	 * Extracts and returns the next token.
	 * 
	 * @return the next token
	 * @throws LexerException in case of an error
	 */
	public Token nextToken() {
		token = extractNextToken();
		return token;
	}

	/**
	 * Returns last extracted token, or {@code null} if no token has been extracted yet.
	 * 
	 * @return last extracted token, or {@code null} if no token has been extracted yet
	 */
	public Token getToken() {
		if (token == null) {
			throw new LexerException("No token has been read.");
		}
		return token;
	}
	
	/**
	 * Extracts next token from text.
	 * 
	 * @return the next token
	 * @throws LexerException in case of an error or invalid format of text
	 */
	private Token extractNextToken() {
		if (token != null && token.getType() == TokenType.EOF) {
			throw new LexerException("No tokens available.");
		}
		
		skipBlanks();
		if (currentIndex >= data.length) {
			return new Token(TokenType.EOF, null);
		}
		
		if (state == LexerState.BASIC) {
			return extractNextBasic();
		} else {
			return extractNextExtended();
		}
	}
	
	/**
	 * Extracts the next token in basic state.
	 * @return the next token
	 * @throws LexerException in case of an invalid format
	 */
	private Token extractNextBasic() {
		if (Character.isDigit(data[currentIndex])) {
			Long number = extractNumber();
			return new Token(TokenType.NUMBER, number);
		} else if (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
			String word = extractWord();
			return new Token(TokenType.WORD, word);
		} else {
			Token token = new Token(TokenType.SYMBOL, Character.valueOf(data[currentIndex]));
			currentIndex++;
			return token;
		}
	}
	
	/**
	 * Extracts the next token in extended state.
	 * @return the next token
	 * @throws LexerException in case of an invalid format
	 */
	private Token extractNextExtended() {
		if (data[currentIndex] == '#') {
			currentIndex++;
			return new Token(TokenType.SYMBOL, Character.valueOf('#'));
		}
		
		int start = currentIndex;
		while (currentIndex < data.length 
				&& !Character.isWhitespace(data[currentIndex]) 
				&& data[currentIndex] != '#') {
			currentIndex++;
		}
		return new Token(TokenType.WORD, new String(data, start, currentIndex - start));
	}
	
	
	
	/**
	 * Extracts the next word from text.
	 * @return next word
	 * @throws LexerException in case of an invalid format
	 */
	private String extractWord() {
		StringBuilder sb = new StringBuilder();
		
		while (currentIndex < data.length) {
			if (Character.isLetter(data[currentIndex])) {
				sb.append(data[currentIndex]);
				currentIndex++;
			} else if (data[currentIndex] == '\\') {
				if (currentIndex == data.length - 1) {
					throw new LexerException("No escape char.");
				}
				
				char c = data[currentIndex + 1];
				if (!Character.isDigit(c) && c != '\\') {
					throw new LexerException("Invalid escaped char.");
				}
				sb.append(c);
				currentIndex += 2;
			} else {
				break;
			}
		}
		
		return sb.toString();
	}
	
	
	
	/**
	 * Extracts the next number from text.
	 * @return next number
	 * @throws LexerException in case of an invalid format
	 */
	private Long extractNumber() {
		int start = currentIndex;
		while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
			currentIndex++;
		}
		
		try {
			return Long.parseLong(new String(data, start, currentIndex - start));
		} catch (NumberFormatException ex) {
			throw new LexerException("Invalid number format.");
		}
	}
	
	
	
	
	/**
	 * Skips blanks.
	 */
	private void skipBlanks() {
		while (currentIndex < data.length && Character.isWhitespace(data[currentIndex])) {
			currentIndex++;
		}
	}
	
	
}
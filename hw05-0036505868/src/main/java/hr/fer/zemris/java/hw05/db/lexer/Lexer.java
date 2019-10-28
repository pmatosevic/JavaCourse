package hr.fer.zemris.java.hw05.db.lexer;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw05.db.ComparisonOperators;

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
	private int curPos;
	
	/**
	 * 
	 */
	private List<Character> symbols = Arrays.asList('>', '<', '=', '!');
	
	
	/**
	 * Creates a new {@code Lexer} with given text to tokenize.
	 * @param text text to tokenize
	 */
	public Lexer(String text) {
		this.data = text.toCharArray();
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
		if (curPos >= data.length) {
			return new Token(TokenType.EOF, null);
		}
		
		char ch = data[curPos];
		if (Character.isLetter(ch)) {
			return new Token(TokenType.WORD, extractWord());
		} else if (ch == '\"') {
			curPos++;
			String str = extractString();
			if (curPos == data.length || data[curPos] != '\"') {
				throw new LexerException("Invalid quoting.");
			}
			curPos++;
			return new Token(TokenType.STRING, str);
		} else if (symbols.contains(ch)) {
			curPos++;
			String symbol = String.valueOf(ch);
			if (symbols.contains(data[curPos])) {
				symbol += String.valueOf(data[curPos]);
				curPos++;
			}
			return new Token(TokenType.SYMBOL, symbol);
		} else {
			throw new LexerException("Invalid character.");
		}
		
	}
	
	
	/**
	 * Extracts the next string literal.
	 * @return the next string literal
	 */
	private String extractString() {
		int start = curPos;
		while (curPos < data.length && (Character.isLetterOrDigit(data[curPos]) || data[curPos] == '*')) {
			curPos++;
		}
		
		return new String(data, start, curPos - start);
	}
	
	
	/**
	 * Extracts the next word.
	 * @return the next word
	 */
	private String extractWord() {
		int start = curPos;
		while (curPos < data.length && Character.isLetter(data[curPos])) {
			curPos++;
		}
		
		return new String(data, start, curPos - start);
	}

	
	
	/**
	 * Skips blanks.
	 */
	private void skipBlanks() {
		while (curPos < data.length && Character.isWhitespace(data[curPos])) {
			curPos++;
		}
	}
	
	
	
}
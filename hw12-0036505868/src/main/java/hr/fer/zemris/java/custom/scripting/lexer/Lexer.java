package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.Objects;

/**
 * 
 * Represents a lexer. Lazily reads given text and returns tokens.
 * Default lexer state is plain text state.
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
	 * Current lexer state
	 */
	private LexerState state = LexerState.PLAIN_TEXT;
	
	
	
	/**
	 * Creates a new {@code Lexer} with given text to tokenize.
	 * @param text text to tokenize
	 */
	public Lexer(String text) {
		this.data = text.toCharArray();
	}
	
	
	/**
	 * Sets the lexer state
	 * @param state lexer state
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
		
		if (state == LexerState.INSIDE_TAG) {
			skipBlanks();
		}
		
		if (curPos >= data.length) {
			return new Token(TokenType.EOF, null);
		}
		
		if (state == LexerState.PLAIN_TEXT) {
			return extractPlainText();
		} else {
			return extractInsideTag();
		}
	}
	
	
	/**
	 * Extracts the next token in plain text state.
	 * @return the next token
	 * @throws LexerException in case of an error
	 */
	private Token extractPlainText() {
		if (checkStartTag()) {
			curPos += 2;
			return new Token(TokenType.TAG_BEGIN, null);
		}
		
		StringBuilder sb = new StringBuilder();
		while (curPos < data.length) {
			if (checkStartTag()) {
				break;
			}
			
			// handling escape sequences
			if (data[curPos] == '\\') {
				if (curPos == data.length - 1) {
					throw new LexerException("No escaped char.");
				}
				
				char c = data[curPos + 1];
				if (c != '{' && c != '\\') {
					throw new LexerException("Invaid escaped char.");
				}
				curPos++;
			}
			sb.append(data[curPos]);
			curPos++;
		}
		
		return new Token(TokenType.TEXT, sb.toString());
	}
	
	/**
	 * Returns whether the next characters are the start of the tag
	 * @return whether the next characters are the start of the tag
	 */
	private boolean checkStartTag() {
		return curPos < (data.length - 1) && data[curPos] == '{' && data[curPos + 1] == '$';
	}
	
	/**
	 * Returns whether the next characters are the end of the tag
	 * @return whether the next characters are the end of the tag
	 */
	private boolean checkEndTag() {
		return curPos < (data.length - 1) && data[curPos] == '$' && data[curPos + 1] == '}';
	}
	
	
	
	/**
	 * Extracts the next token in inside tag state.
	 * @return the next token
	 * @throws LexerException in case of an error
	 */
	private Token extractInsideTag() {
		char ch = data[curPos];
		
		if (Character.isDigit(ch) || (curPos < (data.length - 1) && ch == '-' && Character.isDigit(data[curPos+1]))) {
			return extractNumberToken();
		} else if (ch == '"') {
			String str = extractString();
			return new Token(TokenType.STRING, str);
		} else if (ch == '@') {						// function name consists of @ and a variable name
			curPos++;
			String name = extractName();
			return new Token(TokenType.FUNCTION, name);
		} else if (ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '^') {
			curPos++;
			return new Token(TokenType.OPERATOR, String.valueOf(ch));
		} else if (ch == '=') {
			curPos++;
			return new Token(TokenType.EQUALS, null);
		} else if (Character.isLetter(ch)) {
			String name = extractName();
			return new Token(TokenType.VARIABLE, name);
		} else if (checkEndTag()) {
			curPos += 2;
			return new Token(TokenType.TAG_END, null);
		} else {
			throw new LexerException("Invalid character.");
		}
	}
	
	/**
	 * Extracts a variable name 
	 * (starts by letter and after follows zero or more letters, digits or underscores)
	 * @return the variable name
	 * @throws LexerException in case of an error
	 */
	private String extractName() {
		if (curPos == data.length || !Character.isLetter(data[curPos])) {
			throw new LexerException("Valid variable or function name expected.");
		}
		
		int start = curPos;
		while (curPos < data.length) {
			if (!Character.isLetter(data[curPos]) && !Character.isDigit(data[curPos]) && data[curPos] != '_') {
				break;
			}
			curPos++;
		}
		return new String(data, start, curPos - start);
	}
	
	/**
	 * Extracts a string
	 * @return the string
	 * @throws LexerException in case of an error
	 */
	private String extractString() {
		curPos++;
		
		StringBuilder sb = new StringBuilder();
		while (curPos < data.length) {
			if (data[curPos] == '"') {
				curPos++;
				return sb.toString();
			}
			
			if (data[curPos] == '\\') {
				
				if (curPos == data.length - 1) {
					throw new LexerException("No escaped char.");
				}
				curPos++;
				
				char ch;
				switch(data[curPos]) {						// handling escape sequences
				case '\\':
					ch = '\\';
					break;
				case '"':
					ch = '"';
					break;
				case 'n':
					ch = '\n';
					break;
				case 'r':
					ch = '\r';
					break;
				case 't':
					ch = '\t';
					break;
				default:
					throw new LexerException("Invalid escaped char.");
				}
				curPos++;
				sb.append(ch);
				
			} else {
				sb.append(data[curPos]);
				curPos++;
			}
		}
		
		throw new LexerException("Reached end while string was not closed.");
	}
	
	/**
	 * Extracts a integer or double number into token
	 * @return extracted token
	 * @throws LexerException in case of an error
	 */
	private Token extractNumberToken() {
		boolean seenPoint = false;				// if the number has a decimal point
		int start = curPos;
		if (data[curPos] == '-') {
			curPos++;
		}
		
		while (curPos < data.length) {
			if (data[curPos] == '.') {
				seenPoint = true;
				curPos++;
				continue;
			}
			if (!Character.isDigit(data[curPos])) {
				break;
			}
			curPos++;
		}
		
		if (data[curPos - 1] == '.') {
			throw new LexerException("Only digit-dot-digit decimal format is valid. Digit-dot is invalid.");
		}
		
		try {
			if (seenPoint) {
				Double d = Double.valueOf(new String(data, start, curPos - start));
				return new Token(TokenType.DOUBLE, d);
			} else {
				Integer i = Integer.valueOf(new String(data, start, curPos - start));
				return new Token(TokenType.INTEGER, i);
			}
		} catch (NumberFormatException ex) {
			throw new LexerException("Invalid number format.");
		}
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
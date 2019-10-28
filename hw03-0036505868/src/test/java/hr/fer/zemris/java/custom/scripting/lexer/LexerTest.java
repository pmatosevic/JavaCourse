package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;



public class LexerTest {

	
	@Test
	void testPlainTextSpaces() {
		Lexer lexer = new Lexer("   ");
		
		checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "   "));
		checkToken(lexer.nextToken(), new Token(TokenType.EOF, null));
	}
	
	@Test
	void testInsideTagSpaces() {
		Lexer lexer = new Lexer("   ");
		lexer.setState(LexerState.INSIDE_TAG);
		
		checkToken(lexer.nextToken(), new Token(TokenType.EOF, null));
	}
	
	@Test
	void testPlainTextWithNewlines() {
		Lexer lexer = new Lexer("text 123 text \n\n \t\n abc");
		
		checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "text 123 text \n\n \t\n abc"));
	}
	
	@Test
	void testPlainTextEscaping() {
		Lexer lexer = new Lexer("text111 \\\\test abc\\{def");
		
		checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "text111 \\test abc{def"));
	}
	
	@Test
	void testPlainTextInvalidEscaping() {
		Lexer lexer = new Lexer("text123 \\aaa");
		
		assertThrows(LexerException.class, () -> lexer.nextToken());
	}
	
	@Test
	void testPlainTextInvalidEscaping2() {
		Lexer lexer = new Lexer("text123 \\");
		
		assertThrows(LexerException.class, () -> lexer.nextToken());
	}
	
	@Test
	void testPlainTextBeginTag() {
		Lexer lexer = new Lexer("test123{$");
		
		checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "test123"));
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_BEGIN, null));
	}
	
	@Test
	void testInsideTagVariable() {
		Lexer lexer = new Lexer("for for123_234");
		lexer.setState(LexerState.INSIDE_TAG);
		
		checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE, "for"));
		checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE, "for123_234"));
		checkToken(lexer.nextToken(), new Token(TokenType.EOF, null));
	}
	
	@Test
	void testInsideTagString() {
		Lexer lexer = new Lexer("for \"str\"");
		lexer.setState(LexerState.INSIDE_TAG);
		
		checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE, "for"));
		checkToken(lexer.nextToken(), new Token(TokenType.STRING, "str"));
	}
	
	@Test
	void testInsideTagStringEscape() {
		Lexer lexer = new Lexer("\"str \\n \\r \\t \\\\ \\\" str\"");
		lexer.setState(LexerState.INSIDE_TAG);
		
		checkToken(lexer.nextToken(), new Token(TokenType.STRING, "str \n \r \t \\ \" str"));
	}
	
	@Test
	void testInsideTagStringEscapeInvalid() {
		Lexer lexer = new Lexer("\"str\\str\"");
		lexer.setState(LexerState.INSIDE_TAG);
		
		assertThrows(LexerException.class, () -> lexer.nextToken());
	}
	
	@Test
	void testInsideTagNumber() {
		Lexer lexer = new Lexer("for 123 10.5aaa");
		lexer.setState(LexerState.INSIDE_TAG);
		
		checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE, "for"));
		checkToken(lexer.nextToken(), new Token(TokenType.INTEGER, 123));
		checkToken(lexer.nextToken(), new Token(TokenType.DOUBLE, 10.5));
	}
	
	@Test
	void testInsideTagFunction() {
		Lexer lexer = new Lexer("@sin_123");
		lexer.setState(LexerState.INSIDE_TAG);
		
		checkToken(lexer.nextToken(), new Token(TokenType.FUNCTION, "sin_123"));
	}
	
	
	@Test
	void testInsideTagEndTag() {
		Lexer lexer = new Lexer("for $} text");
		lexer.setState(LexerState.INSIDE_TAG);
		
		checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE, "for"));
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_END, null));
		lexer.setState(LexerState.PLAIN_TEXT);
		checkToken(lexer.nextToken(), new Token(TokenType.TEXT, " text"));
	}
	
	@Test
	void testCombined1() {
		Lexer lexer = new Lexer("{$= i i * @sin \"0.000\" @decfmt $}");
		
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_BEGIN, null));
		lexer.setState(LexerState.INSIDE_TAG);
		checkToken(lexer.nextToken(), new Token(TokenType.EQUALS, null));
		checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE, "i"));
		checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE, "i"));
		checkToken(lexer.nextToken(), new Token(TokenType.OPERATOR, "*"));
		checkToken(lexer.nextToken(), new Token(TokenType.FUNCTION, "sin"));
		checkToken(lexer.nextToken(), new Token(TokenType.STRING, "0.000"));
		checkToken(lexer.nextToken(), new Token(TokenType.FUNCTION, "decfmt"));
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_END, null));
	}
	
	@Test
	void testCombined2() {
		Lexer lexer = new Lexer("{$ FOR i-1.35bbb\"1\" $}");
		
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_BEGIN, null));
		lexer.setState(LexerState.INSIDE_TAG);
		checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE, "FOR"));
		checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE, "i"));
		checkToken(lexer.nextToken(), new Token(TokenType.DOUBLE, -1.35));
		checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE, "bbb"));
		checkToken(lexer.nextToken(), new Token(TokenType.STRING, "1"));
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_END, null));
	}
	
	
	
	private void checkToken(Token actual, Token expected) {
		String msg = "Token are not equal.";
		assertEquals(expected.getType(), actual.getType(), msg);
		assertEquals(expected.getValue(), actual.getValue(), msg);
	}
	
}

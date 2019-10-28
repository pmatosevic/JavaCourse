package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.hw05.db.lexer.Lexer;
import hr.fer.zemris.java.hw05.db.lexer.LexerException;
import hr.fer.zemris.java.hw05.db.lexer.TokenType;


/**
 * A parser for the query command.
 * 
 * @author Patrik
 *
 */
public class QueryParser {

	/**
	 * Like operator
	 */
	private static final String LIKE_STR = "LIKE";
	
	/**
	 * AND string
	 */
	private static final String AND_STR = "AND";
	
	/**
	 * All attributes
	 */
	private static Map<String, IFieldValueGetter> attributes = new HashMap<>();
	
	/**
	 * All operators
	 */
	private static Map<String, IComparisonOperator> operators = new HashMap<>();
	
	
	/**
	 * Lexer used for lexing
	 */
	private Lexer lexer;
	
	/**
	 * list of all expressions in the query
	 */
	private List<ConditionalExpression> query;
	
	/**
	 * JMBAG from query
	 */
	private String queriedJmbag;
	
	
	static {
		attributes.put("jmbag", FieldValueGetters.JMBAG);
		attributes.put("lastName", FieldValueGetters.LAST_NAME);
		attributes.put("firstName", FieldValueGetters.FIRST_NAME);
		
		operators.put("<", ComparisonOperators.LESS);
		operators.put("<=", ComparisonOperators.LESS_OR_EQUALS);
		operators.put(">", ComparisonOperators.GREATER);
		operators.put(">=", ComparisonOperators.GREATER_OR_EQUALS);
		operators.put("=", ComparisonOperators.EQUALS);
		operators.put("!=", ComparisonOperators.NOT_EQUALS);
		operators.put("LIKE", ComparisonOperators.LIKE);
	}
	
	/**
	 * Creates a new {@code QueryParser} with given 
	 * @param text command parameters
	 */
	public QueryParser(String text) {
		lexer = new Lexer(text);
		parseQuery();
	}
	
	/**
	 * Returns whether the query is direct or not.
	 * @return whether the query is direct or not
	 */
	public boolean isDirectQuery() {
		return queriedJmbag != null;
	}
	
	/**
	 * Returns the queried JMBAG.
	 * @return the queried JMBAG
	 * @throws IllegalStateException if the query is not direct
	 */
	public String getQueriedJMBAG() {
		if (queriedJmbag == null) {
			throw new IllegalStateException("Query was not direct.");
		}
		return queriedJmbag;
	}
	
	/**
	 * Returns the query as a list of expressions.
	 * @return the query as a list of expressions
	 */
	public List<ConditionalExpression> getQuery() {
		return query;
	}
	
	/**
	 * Parses the query
	 */
	private void parseQuery() {
		query = new ArrayList<>();
		
		try {
			lexer.nextToken();
			
			if (getLastType() == TokenType.EOF) {
				return;
			}
			
			while (true) {				
				query.add(parseExpression());
				
				if (getLastType() == TokenType.EOF) {
					break;
				}
				
				if (!(getLastType() == TokenType.WORD && AND_STR.equalsIgnoreCase((String) getLastValue()))) {
					throw new ParserException("Expected AND");
				}
				lexer.nextToken();
			}
			
			if (query.size() == 1) {
				ConditionalExpression expr = query.get(0);
				if (expr.getFieldGetter() == FieldValueGetters.JMBAG
						&& expr.getComparisonOperator() == ComparisonOperators.EQUALS) {
					queriedJmbag = expr.getStringLiteral();
				}
			}
			
		} catch (LexerException ex) {
			throw new ParserException(ex);
		}
	}
	
	/**
	 * Parses one expression in the query
	 * @return the parsed expressions
	 */
	private ConditionalExpression parseExpression() {
		if (getLastType() != TokenType.WORD) {
			throw new ParserException("Expected a field name.");
		}
		
		IFieldValueGetter getter = attributes.get((String) getLastValue());
		if (getter == null) {
			throw new ParserException("Expected attribute.");
		}
		
		lexer.nextToken();
		IComparisonOperator operator = null;
		if (getLastType() == TokenType.SYMBOL) {
			operator = operators.get(getLastValue());
		} else if (getLastType() == TokenType.WORD && ((String) getLastValue()).equals(LIKE_STR)) {
			operator = ComparisonOperators.LIKE;
		}
		if (operator == null) {
			throw new ParserException("Expected operator.");
		}
		
		lexer.nextToken();
		if (getLastType() != TokenType.STRING) {
			throw new ParserException("Expected string literal.");
		}
		String literal = (String) getLastValue();
		
		if (operator == ComparisonOperators.LIKE && multipleStars(literal)) {
			throw new ParserException("LIKE operator does not support multiple *.");
		}
		
		lexer.nextToken();
		return new ConditionalExpression(getter, literal, operator);
	}
	
	
	/**
	 * Checks whether the string has multiple stars(*).
	 * @param literal string
	 * @return whether the string has multiple stars(*)
	 */
	private boolean multipleStars(String literal) {
		return literal.indexOf('*') != literal.lastIndexOf('*');
	}

	/**
	 * Returns the value of the last token.
	 * @return the value of the last token
	 */
	private Object getLastValue() {
		return lexer.getToken().getValue();
	}
	
	/**
	 * Returns the type of the last token.
	 * @return the type of the last token
	 */
	private TokenType getLastType() {
		return lexer.getToken().getType();
	}
	
	
}

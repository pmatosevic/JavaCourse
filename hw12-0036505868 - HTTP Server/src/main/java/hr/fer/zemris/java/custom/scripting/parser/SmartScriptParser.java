package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.lexer.LexerException;
import hr.fer.zemris.java.custom.scripting.lexer.LexerState;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

/**
 * Parser for the smart script language.
 * 
 * @author Patrik
 *
 */
public class SmartScriptParser {

	/**
	 * Tag name of for construct
	 */
	private static final String FOR_TAG = "FOR";
	
	/**s
	 * Tag name of end of for construct
	 */
	private static final String END_TAG = "END";
	
	/**
	 * Lexer for smart script
	 */
	private Lexer lexer;
	
	/**
	 * Root node of the document
	 */
	private DocumentNode documentNode;
	
	/**
	 * Stack used for storing document layout during parsing
	 */
	private ObjectStack nodeStack;
	
	/**
	 * Creates a new {@code SmartScriptParser} and parses given text.
	 * @param text text to parse
	 * @throws NullPointerException if {@code text} is {@code null}
	 * @throws SmartScriptParserException in case of an error during parsing
	 */
	public SmartScriptParser(String text) {
		lexer = new Lexer(text);
		documentNode = parseAll();
	}
	
	/**
	 * Returns the document node
	 * @return the document node
	 */
	public DocumentNode getDocumentNode() {
		return documentNode;
	}
	
	/**
	 * Parses the whole text and returns the root document node.
	 * @return the root document node
	 * @throws SmartScriptParserException in case of an error during parsing
	 */
	private DocumentNode parseAll() {
		nodeStack = new ObjectStack();
		nodeStack.push(new DocumentNode());
		
		try {
			
			lexer.nextToken();
			while (true) {
				if (getLastType() == TokenType.EOF) {
					break;
				}
				
				if (getLastType() == TokenType.TEXT) {					// read plain text
					Node top = (Node) nodeStack.peek();
					top.addChildNode(new TextNode((String) lexer.getToken().getValue()));
					lexer.nextToken();
					continue;
				}
				if (getLastType() != TokenType.TAG_BEGIN) {
					throw new SmartScriptParserException("Invalid format of text.");
				}
				
				//read the tag
				lexer.setState(LexerState.INSIDE_TAG);
				lexer.nextToken();
				
				parseTag();
				
				lexer.setState(LexerState.PLAIN_TEXT);
				lexer.nextToken();
			}
			if (nodeStack.size() == 1) {
				return (DocumentNode) nodeStack.pop();
			}
			throw new SmartScriptParserException("Invalid number of END tags.");
			
		} catch (LexerException ex) {
			throw new SmartScriptParserException("Exception during lexing: " + ex.getMessage());
		} catch (SmartScriptParserException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new SmartScriptParserException("Unexpected parsing error.");		// this should not happen
		}
	}
	
	/**
	 * Parses the next tag and adds it to as a child to the node on top of the stack
	 * @throws SmartScriptParserException in case of an error during parsing
	 */
	private void parseTag() {
		if (getLastType() == TokenType.EQUALS) {					// if it is an echo tag (starts with =)
			lexer.nextToken();
			EchoNode child = parseEchoNode();
			addAsChild(child);
		} else if (getLastType() == TokenType.VARIABLE) {			// if it is a FOR or END tag
			String tagName = (String) lexer.getToken().getValue();
			lexer.nextToken();
			
			if (tagName.equalsIgnoreCase(FOR_TAG)) {
				ForLoopNode forNode = parseForNode();
				addAsChild(forNode);
				nodeStack.push(forNode);
				
			} else if (tagName.equalsIgnoreCase(END_TAG)) {
				if (getLastType() != TokenType.TAG_END) {
					throw new SmartScriptParserException("Invalid end tag.");
				}
				
				if (nodeStack.size() <= 1) {
					throw new SmartScriptParserException("Invalid number of end tags.");
				}
				nodeStack.pop();
				
			} else {
				throw new SmartScriptParserException("Tag name not supported.");
			}
			
		} else {
			throw new SmartScriptParserException("Expected valid tag name.");
		}
	}
	
	/**
	 * Parses the elements of an for loop node
	 * @return for loop node
	 * @throws SmartScriptParserException in case of an error during parsing
	 */
	private ForLoopNode parseForNode() {
		if (getLastType() != TokenType.VARIABLE) {
			throw new SmartScriptParserException("Invalid for tag.");
		}
		
		ElementVariable variable = new ElementVariable((String) getLastValue());
		lexer.nextToken();
		
		Element start = parseElement(true);
		lexer.nextToken();
		
		Element end = parseElement(true);
		lexer.nextToken();
		
		ForLoopNode forNode;
		if (getLastType() != TokenType.TAG_END) {								// if it has step element
			forNode = new ForLoopNode(variable, start, end, parseElement(true));
			lexer.nextToken();
			
			if (getLastType() != TokenType.TAG_END) {
				throw new SmartScriptParserException("Expected the end of the FOR tag.");
			}
		} else {
			forNode = new ForLoopNode(variable, start, end);
		}
		return forNode;
	}
	
	
	
	/**
	 * Parses the elements of an echo node
	 * @return echo node
	 */
	private EchoNode parseEchoNode() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		while (true) {
			if (getLastType() == TokenType.TAG_END) {
				break;
			}
			
			Element el = parseElement(false);
			collection.add(el);
			
			lexer.nextToken();
		}
		
		Element[] elements = new Element[collection.size()];
		Object[] array = collection.toArray();
		for (int i=0; i<array.length; i++) {
			elements[i] = (Element) array[i];
		}
		return new EchoNode(elements);
	}
	
	/**
	 * Parses the last read token as an element.
	 * @param limit whether to limit element type to variable, number and string (used in for node)
	 * @return the parsed element
	 * @throws SmartScriptParserException in case of an error during parsing
	 */
	private Element parseElement(boolean limit) {
		switch(getLastType()) {
		case VARIABLE:
			return new ElementVariable((String) getLastValue());
		case STRING:
			return new ElementString((String) getLastValue());
		case INTEGER:
			return new ElementConstantInteger((Integer) getLastValue());
		case DOUBLE:
			return new ElementConstantDouble((Double) getLastValue());
		case FUNCTION:
			if (limit) {
				throw new SmartScriptParserException("Expected variable, number or string.");
			}
			return new ElementFunction((String) getLastValue());
		case OPERATOR:
			if (limit) {
				throw new SmartScriptParserException("Expected variable, number or string.");
			}
			return new ElementOperator((String) getLastValue());
		case EOF:
			throw new SmartScriptParserException("Unexpected end of stream.");
		default:
			throw new SmartScriptParserException("Expected element.");
		}
	}
	
	/**
	 * Adds a node as a child to the node on top of the stack
	 * @param child node to add as a child
	 */
	private void addAsChild(Node child) {
		Node top = (Node) nodeStack.peek();
		top.addChildNode(child);
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

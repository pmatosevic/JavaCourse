package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * An engine for execution of SmartScript documents.
 * 
 * @author Patrik
 *
 */
public class SmartScriptEngine {

	/**
	 * Root document node
	 */
	private DocumentNode documentNode;
	
	/**
	 * Request context
	 */
	private RequestContext requestContext;
	
	/**
	 * Multistack of values
	 */
	private ObjectMultistack multistack = new ObjectMultistack();
	
	/**
	 * The visitor for execution
	 */
	private INodeVisitor visitor = new INodeVisitor() {
		
		@Override
		public void visitTextNode(TextNode node) {
			writeToContext(node.getText());
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String variable = node.getVariable().asText();
			Object startExpr = node.getStartExpression().asText();
			Object endExpr = node.getEndExpression().asText();
			Object stepExpr = node.getStepExpression().asText();
			multistack.push(variable, new ValueWrapper(startExpr));
			
			while (multistack.peek(variable).numCompare(endExpr) <= 0) {
				int numChild = node.numberOfChildren();
				for (int i=0; i<numChild; i++) {
					node.getChild(i).accept(this);
				}
				multistack.peek(variable).add(stepExpr);
			}
			
			multistack.pop(variable);
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			Deque<Object> stack = new ArrayDeque<Object>();
			
			for (Element el : node.getElements()) {
				if (el instanceof ElementConstantInteger) {
					stack.push(((ElementConstantInteger) el).getValue());
				} else if (el instanceof ElementConstantDouble) {
					stack.push(((ElementConstantDouble) el).getValue());
				} else if (el instanceof ElementString) {
					stack.push(((ElementString) el).getValue());
				} else if (el instanceof ElementVariable) {
					try {
						Object value = multistack.peek(el.asText()).getValue();
						stack.push(value);
					} catch (NoSuchElementException e) {
						throw new ExecutionException("The variable was not defined: " + el.asText());
					}
				} else if (el instanceof ElementOperator) {
					try {
						operation(stack, (ElementOperator) el);
					} catch (NoSuchElementException e) {
						throw new ExecutionException("Empty stack.");
					}
				} else if (el instanceof ElementFunction) {
					try {
						functionImplementations.get(el.asText()).accept(stack);
					} catch (NoSuchElementException e) {
						throw new ExecutionException("Empty stack");
					}
				} else {
					throw new ExecutionException("Unsupported element.");
				}
			}
			
			Iterator<Object> it = stack.descendingIterator();
			while(it.hasNext()) {
				writeToContext(it.next().toString());
			}
			
		}
		

		@Override
		public void visitDocumentNode(DocumentNode node) {
			int numChild = node.numberOfChildren();
			for (int i=0; i<numChild; i++) {
				node.getChild(i).accept(this);
			}
		}
		
	};
	
	/**
	 * Writes the string to the request context
	 * @param str string to write
	 */
	private void writeToContext(String str) {
		try {
			requestContext.write(str);
		} catch (IOException e) {
			throw new ExecutionException("IO error");
		}
	}
	
	/**
	 * Map of all functions available in echo nodes
	 */
	private Map<String, Consumer<Deque<Object>>> functionImplementations = new HashMap<>();
	
	/**
	 * Performs arithmetic operation
	 * @param stack stack
	 * @param el element operator
	 */
	private void operation(Deque<Object> stack, ElementOperator el) {
		Number x = ValueWrapper.tryConvert(stack.pop());
		Number y = ValueWrapper.tryConvert(stack.pop());
		Number result;
		
		switch(el.getSymbol()) {
		case "+":
			result = ValueWrapper.performOperation((a, b) -> a + b, (a, b) -> a + b, x, y);
			break;
		case "-":
			result = ValueWrapper.performOperation((a, b) -> a - b, (a, b) -> a - b, x, y);
			break;
		case "*":
			result = ValueWrapper.performOperation((a, b) -> a * b, (a, b) -> a * b, x, y);
			break;
		case "/":
			result = ValueWrapper.performOperation((a, b) -> a / b, (a, b) -> a / b, x, y);
			break;
		default:
			throw new ExecutionException("Invalid operator");
		}
		
		stack.push(result);
	}
	
	/**
	 * Caluclates the sin value
	 */
	private Consumer<Deque<Object>> sin = (stack) -> {
		Number num = ValueWrapper.tryConvert(stack.pop());
		double result = Math.sin(Math.toRadians(num.doubleValue()));
		stack.push(result);
	};
	
	/**
	 * Formats the decimal number
	 */
	private Consumer<Deque<Object>> decfmt = (stack) -> {
		String pattern = (String) stack.pop();
		Number num = ValueWrapper.tryConvert(stack.pop());
		DecimalFormat f = new DecimalFormat(pattern);
		String result = f.format(num.doubleValue());
		stack.push(result);
	};
	
	/**
	 * Duplicates the last element
	 */
	private Consumer<Deque<Object>> dup = (stack) -> {
		Object x = stack.peek();
		stack.push(x);
	};
	
	/**
	 * Swaps the content on the stack
	 */
	private Consumer<Deque<Object>> swap = (stack) -> {
		Object a = stack.pop();
		Object b = stack.pop();
		stack.push(a);
		stack.push(b);
	};
	
	/**
	 * Sets the mime type
	 */
	private Consumer<Deque<Object>> setMimeType = (stack) -> {
		String type = (String) stack.pop();
		requestContext.setMimeType(type);
	};
	
	/**
	 * Gets the parameter
	 * @param stack stack
	 * @param getter actual getter
	 */
	private static void paramGet(Deque<Object> stack, Function<String, String> getter) {
		String defValue = stack.pop().toString();
		String name = stack.pop().toString();
		String value = getter.apply(name);
		stack.push(value == null ? defValue : value);
	}
	
	/**
	 * Sets the parameter
	 * @param stack stack
	 * @param setter actual setter
	 */
	private void paramSet(Deque<Object> stack, BiConsumer<String, String> setter) {
		String name = stack.pop().toString();
		String value = stack.pop().toString();
		setter.accept(name, value);
	}
	
	/**
	 * Removes the parameter
	 * @param stack stack
	 * @param remover actual remover
	 */
	private void paramDel(Deque<Object> stack, Consumer<String> remover) {
		String name = stack.pop().toString();
		remover.accept(name);
	}
	
	/**
	 * Puts the implementations of the functions to a map
	 */
	private void initializeFunctions() {
		functionImplementations.put("sin", sin);
		functionImplementations.put("decfmt", decfmt);
		functionImplementations.put("dup", dup);
		functionImplementations.put("swap", swap);
		functionImplementations.put("setMimeType", setMimeType);
		functionImplementations.put("paramGet", stack -> paramGet(stack, requestContext::getParameter));
		functionImplementations.put("pparamGet", stack -> paramGet(stack, requestContext::getPersistentParameter));
		functionImplementations.put("pparamSet", stack -> paramSet(stack, requestContext::setPersistentParameter));
		functionImplementations.put("pparamDel", stack -> paramDel(stack, requestContext::removePersistentParameter));
		functionImplementations.put("tparamGet", stack -> paramGet(stack, requestContext::getTemporaryParameter));
		functionImplementations.put("tparamSet", stack -> paramSet(stack, requestContext::setTemporaryParameter));
		functionImplementations.put("tparamDel", stack -> paramDel(stack, requestContext::removeTemporaryParameter));
	}
	
	/**
	 * Creates a new engine
	 * @param documentNode root document node
	 * @param requestContext request context
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;
		initializeFunctions();
	}
	
	/**
	 * Executes the document
	 */
	public void execute() {
		documentNode.accept(visitor);
	}
	
}

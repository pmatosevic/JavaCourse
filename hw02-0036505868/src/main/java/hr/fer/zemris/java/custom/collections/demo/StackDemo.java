package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Program that demonstrates using stack for evaluating postfix expressions.
 * Accepts 1 command-line argument: expression which should be evaluated. Expression must be in 
 * postfix representation. Numbers and operators should be separated by space.
 * Prints the result or an error message in case of an error during evaluation.
 * 
 * @author Patrik
 *
 */
public class StackDemo {

	/**
	 * Program entry point.
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Syntax: StackDemo \"postfix expression\"");
			return;
		}
		
		String[] parts = args[0].split(" ");
		try {
			int result = evaluate(parts);
			System.out.println("Expression evaluates to " + result + ".");
		} catch (ExpressionEvaluationException ex) {
			System.out.println("Error during evaluation of the expression. " + ex.getMessage());
		}
	}
	
	/**
	 * Evaluates a postfix expression
	 * @param parts parts of the expression
	 * @return the result
	 * @throws ExpressionEvaluationException in case of an error
	 */
	private static int evaluate(String[] parts) {
		ObjectStack stack = new ObjectStack();
		
		for (String part : parts) {
			try {
				int number = Integer.parseInt(part);
				stack.push(number);
				continue;
			} catch (NumberFormatException ex) { }
			
			if (part.length() != 1 || stack.size() < 2) {
				throw new ExpressionEvaluationException("Invalid format.");
			}
			
			int op2 = (Integer) stack.pop();
			int op1 = (Integer) stack.pop();
			int result;
			switch (part.charAt(0)) {
			case '+':
				result = op1 + op2;
				break;
			case '-':
				result = op1 - op2;
				break;
			case '*':
				result = op1 * op2;
				break;
			case '/':
				if (op2 == 0) {
					throw new ExpressionEvaluationException("Division by zero.");
				}
				result = op1 / op2;
				break;
			case '%':
				if (op2 == 0) {
					throw new ExpressionEvaluationException("Division by zero.");
				}
				result = op1 % op2;
				break;
			default:
				throw new ExpressionEvaluationException("Invalid format.");
			}
			
			stack.push(result);
		}
		
		if (stack.size() != 1) {
			throw new ExpressionEvaluationException("Invalid format.");
		}
		return (Integer) stack.pop();
	}
	
}

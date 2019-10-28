package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Represents a context used when drawing a fractal.
 * Stores multiple {@link TurtleState} in a stack.
 * 
 * @author Patrik
 *
 */
public class Context {

	/**
	 * The stack used to store states
	 */
	private ObjectStack<TurtleState> stack = new ObjectStack<>();
	
	
	/**
	 * Returns the last state without popping it.
	 * @return the last state
	 * @throws IllegalStateException if there is not states left
	 */
	public TurtleState getCurrentState() {
		if (stack.isEmpty()) {
			throw new IllegalStateException("No state available.");
		}
		return stack.peek();
	}
	
	/**
	 * Pushes the given state to the stack
	 * @param state state to push
	 */
	public void pushState(TurtleState state) {
		stack.push(state);
	}
	
	/**
	 * Removes one state on the top of the stack
	 */
	public void popState() {
		stack.pop();
	}
	
	
}

package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * Represent a stack - a LIFO structure.
 * Uses {@code ArrayIndexedCollection} as storage for elements.
 * Pushing and poping runs in constant time.
 * 
 * @author Patrik
 *
 * @param <T> type
 */
public class ObjectStack<T> {
	
	/**
	 * {@code ArrayIndexedCollection} used for storage of elements (adaptee)
	 */
	private ArrayIndexedCollection<T> arrayCollection;
	
	/**
	 * Constructs a new {@code ObjectStack}
	 */
	public ObjectStack() {
		arrayCollection = new ArrayIndexedCollection<>();
	}
	
	/**
	 * Returns whether the stack is empty or not.
	 * @return {@code true} if the stack is empty, {@code false} otherwise
	 */
	public boolean isEmpty() {
		return arrayCollection.isEmpty();
	}
	
	
	/**
	 * Returns the number of currently stored objects in the stack.
	 * @return the number of currently stored objects in the stack
	 */
	public int size() {
		return arrayCollection.size();
	}
	
	/**
	 * Pushes given value to the top of the stack.
	 * 
	 * @param value value to push to the stack
	 * @throws NullPointerException if {@code value} is {@code null}
	 */
	public void push(T value) {
		Objects.requireNonNull(value);
		arrayCollection.add(value);
	}
	
	/**
	 * Removes last value pushed on stack from stack and returns it.
	 * 
	 * @return the last value pushed on stack
	 * @throws EmptyStackException if the stack is empty
	 */
	public T pop() {
		T result = peek();
		arrayCollection.remove(size() - 1);
		return result;
	}
	
	/**
	 * Returns last value pushed on stack from stack without removing it.
	 * 
	 * @return the last value pushed on stack
	 * @throws EmptyStackException if the stack is empty
	 */
	public T peek() {
		if (size() <= 0) {
			throw new EmptyStackException("The stack is empty.");
		}
		return arrayCollection.get(size() - 1);
	}
	
	/**
	 * Removes all elements from the stack.
	 */
	public void clear() {
		arrayCollection.clear();
	}
	
}

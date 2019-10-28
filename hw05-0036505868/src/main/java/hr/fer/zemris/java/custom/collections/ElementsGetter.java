package hr.fer.zemris.java.custom.collections;



/**
 * 
 * Represents a getter that iterates over elements in the collection and returns
 * them by calling {@link ElementsGetter#getNextElement()}
 * 
 * @author Patrik
 *
 * @param <T> type
 */
public interface ElementsGetter<T> {


	/**
	 * Returns {@code true} if this {@code ElementGetter} has more elements.
	 * 
	 * @return {@code true} if this {@code ElementGetter} has more elements, {@code false} otherwise
	 */
	boolean hasNextElement();
	
	
	/**
	 * Returns the next element.
	 * 
	 * @return the next element
	 */
	T getNextElement();
	
	/**
	 * Calls {@link Processor#process(Object)} on remaining objects in the collection
	 * 
	 * @param p Processor object
	 */
	default void processRemaining(Processor<? super T> p) {
		while (hasNextElement()) {
			p.process(getNextElement());
		}
	}
	
	
}

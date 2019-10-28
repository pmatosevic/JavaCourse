package hr.fer.zemris.java.custom.collections;

/**
 * Represents a model of a collection with basic functionality of adding and removing elements.
 * 
 * @author Patrik
 *
 */
public interface Collection {
	
	/**
	 * Returns {@code true} if collection contains no objects and {@code false} otherwise
	 * @return {@code true} if collection contains no objects and {@code false} otherwise
	 */
	default boolean isEmpty() {
		return size() == 0;
	}
	
	
	/**
	 * Returns the number of currently stored objects in this collection.
	 * @return the number of currently stored objects in this collection.
	 */
	int size();
	
	
	/**
	 * Adds the given object to the collection.
	 * @param value object to add to the collection.
	 */
	void add(Object value);
	
	
	/**
	 * Returns {@code true} only if the collection contains given value, 
	 * as determined by {@code equals} method. 
	 * 
	 * @param value object to check for existence in this collection (can be null)
	 * @return {@code true} if the collection contains given value, {@code false} otherwise
	 */
	boolean contains(Object value);
	
	
	/**
	 * Returns {@code true} only if the collection contains given value 
	 * as determined by {@code equals} method and removes one occurrence of it.
	 * 
	 * @param value object to remove from the collection if it exists
	 * @return {@code true} if the collection contained given value, {@code false} otherwise
	 */
	boolean remove(Object value);
	
	
	/**
	 * Allocates new array with size equals to the size of this collections, 
	 * fills it with collection content and returns the array.
	 * This method never returns {@code null}
	 * 
	 * @return an array filled with content from the collection
	 */
	Object[] toArray();
	
	
	
	/**
	 * Calls processor.process(...) for each element in this collection.
	 * 
	 * @param processor Processor object used to process elements
	 */
	default public void forEach(Processor processor) {
		ElementsGetter getter = createElementsGetter();
		getter.processRemaining(processor);
	}
	
	
	
	/**
	 * Adds into the current collection all elements from the given collection. 
	 * The other collection remains unchanged.
	 * 
	 * @param other collection to copy elements from
	 */
	default void addAll(Collection other) {
		class AddingProcessor implements Processor {
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		Processor addingProcessor = new AddingProcessor();
		other.forEach(addingProcessor);
	}
	
	
	/**
	 * Removes all elements from the collection.
	 */
	void clear();
	
	
	/**
	 * Returns a new {@link ElementsGetter} for this collection
	 * 
	 * @return a new {@link ElementsGetter} for this collection
	 */
	ElementsGetter createElementsGetter();
	
	
	/**
	 * Adds all elements accepted by tester to the given collection.
	 * @param col collection
	 * @param tester tester
	 */
	default void addAllSatisfying(Collection col, Tester tester) {
		ElementsGetter getter = col.createElementsGetter();
		getter.processRemaining(obj -> {
			if (tester.test(obj)) {
				add(obj);
			}
		});
	}
	
	
}

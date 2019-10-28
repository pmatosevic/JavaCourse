package hr.fer.zemris.java.custom.collections;


/**
 * Represents an (abstract) model of a collection and cannot be instantiated.
 * When extending this class, methods {@code size}, {@code add}, {@code contains}, 
 * {@code remove}, {@code toArray}, {@code forEach} and {@code clear} have to be overridden
 * because they return or do nothing.
 * 
 * @author Patrik
 *
 */
public class Collection {

	/**
	 * Creates a new {@code Collection}
	 */
	protected Collection() {
	}
	
	
	/**
	 * Returns {@code true} if collection contains no objects and {@code false} otherwise
	 * @return {@code true} if collection contains no objects and {@code false} otherwise
	 */
	public boolean isEmpty() {
		return size() == 0;
	}
	
	
	/**
	 * Returns the number of currently stored objects in this collection.
	 * @return the number of currently stored objects in this collection.
	 */
	public int size() {
		return 0;
	}
	
	
	/**
	 * Adds the given object to the collection.
	 * @param value object to add to the collection.
	 */
	public void add(Object value) { }
	
	
	/**
	 * Returns {@code true} only if the collection contains given value, 
	 * as determined by {@code equals} method. 
	 * 
	 * @param value object to check for existence in this collection (can be null)
	 * @return {@code true} if the collection contains given value, {@code false} otherwise
	 */
	public boolean contains(Object value) {
		return false;
	}
	
	
	/**
	 * Returns {@code true} only if the collection contains given value 
	 * as determined by {@code equals} method and removes one occurrence of it.
	 * 
	 * @param value object to remove from the collection if it exists
	 * @return {@code true} if the collection contained given value, {@code false} otherwise
	 */
	public boolean remove(Object value) {
		return false;
	}
	
	
	/**
	 * Allocates new array with size equals to the size of this collections, 
	 * fills it with collection content and returns the array.
	 * This method never returns {@code null}
	 * 
	 * @return an array filled with content from the collection
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException("Not implemented.");
	}
	
	
	
	/**
	 * Calls processor.process(...) for each element in this collection.
	 * 
	 * @param processor Processor object used to process elements
	 */
	public void forEach(Processor processor) { }
	
	
	
	/**
	 * Adds into the current collection all elements from the given collection. 
	 * The other collection remains unchanged.
	 * 
	 * @param other collection to copy elements from
	 */
	public void addAll(Collection other) {
		class AddingProcessor extends Processor {
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
	public void clear() { }
}

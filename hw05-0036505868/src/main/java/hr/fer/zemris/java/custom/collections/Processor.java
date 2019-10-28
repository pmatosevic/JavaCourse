package hr.fer.zemris.java.custom.collections;


/**
 * 
 * Represents a class capable of performing some operation on the passed object.
 * 
 * @author Patrik
 *
 * @param <T> type
 */
@FunctionalInterface
public interface Processor<T> {

	/**
	 * Performs some operations on the passed value
	 * 
	 * @param value value to process
	 */
	void process(T value);
	
}

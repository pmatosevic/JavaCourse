package hr.fer.zemris.java.custom.collections;


/**
 * 
 * Represents a class capable of performing some operation on the passed object.
 * 
 * @author Patrik
 *
 */
public interface Processor {

	/**
	 * Performs some operations on the passed value
	 * 
	 * @param value value to process
	 */
	void process(Object value);
	
}

package hr.fer.zemris.java.custom.collections;

/**
 * 
 * Functional interface to implement testing of an object.
 * 
 * 
 * @author Patrik
 *
 * @param <T> type
 */
@FunctionalInterface
public interface Tester<T> {

	/**
	 * Returns whether given object is accepted or not.
	 * 
	 * @param obj object to test
	 * @return {@code true} if the given object is accepted, otherwise {@code false}
	 */
	boolean test(T obj);
	
}

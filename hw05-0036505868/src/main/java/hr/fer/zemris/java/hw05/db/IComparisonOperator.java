package hr.fer.zemris.java.hw05.db;

/**
 * An interface used to model the comparison of two strings.
 * 
 * @author Patrik
 *
 */
@FunctionalInterface
public interface IComparisonOperator {

	/**
	 * Returns whether the condition is satisfied or not.
	 * @param value1 the first value
	 * @param value2 the second value
	 * @return whether the condition is satisfied or not
	 */
	public boolean satisfied(String value1, String value2);
	
}

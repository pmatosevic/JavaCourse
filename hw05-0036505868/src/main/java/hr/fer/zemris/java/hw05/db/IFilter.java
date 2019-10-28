package hr.fer.zemris.java.hw05.db;

/**
 * An interface used to accept or reject a record.
 * 
 * @author Patrik
 *
 */
@FunctionalInterface
public interface IFilter {
	
	/**
	 * Returns whether the record is accepted or not.
	 * @param record student record
	 * @return whether the record is accepted or not
	 */
	public boolean accepts(StudentRecord record);
	
}

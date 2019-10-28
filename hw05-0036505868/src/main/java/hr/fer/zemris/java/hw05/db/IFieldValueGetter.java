package hr.fer.zemris.java.hw05.db;

/**
 * An interface used to model returning some attribute of a record.
 * @author Patrik
 *
 */
@FunctionalInterface
public interface IFieldValueGetter {

	/**
	 * Returns the attribute from the record.
	 * @param record student record
	 * @return the attribute from the record
	 */
	public String get(StudentRecord record);
	
}

package hr.fer.zemris.java.hw05.db;

/**
 * Class with static getters for attributes.
 * @author Patrik
 *
 */
public class FieldValueGetters {

	/**
	 * Getter for the first name
	 */
	public static final IFieldValueGetter FIRST_NAME = record -> record.getFirstName();
	
	/**
	 * Getter for the last name
	 */
	public static final IFieldValueGetter LAST_NAME = record -> record.getLastName();
	
	/**
	 * Getter for the last name
	 */
	public static final IFieldValueGetter JMBAG = record -> record.getJmbag();
	
}

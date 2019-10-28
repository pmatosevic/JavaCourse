package hr.fer.zemris.java.hw07.observer2;

/**
 * A class that stores information about a change in the subject {@link IntegerStorage}.
 * 
 * @author Patrik
 *
 */
public class IntegerStorageChange {

	/**
	 * The subject
	 */
	private IntegerStorage istorage;
	
	/**
	 * Old value
	 */
	private int oldValue;
	
	/**
	 * New value
	 */
	private int newValue;

	/**
	 * Creates a new {@code IntegerStorageChange}.
	 * @param istorage {@link IntegerStorage} object
	 * @param oldValue old value
	 * @param newValue new value
	 */
	public IntegerStorageChange(IntegerStorage istorage, int oldValue, int newValue) {
		this.istorage = istorage;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	/**
	 * @return the {@link IntegerStorage} subject
	 */
	public IntegerStorage getIstorage() {
		return istorage;
	}

	/**
	 * @return the old value
	 */
	public int getOldValue() {
		return oldValue;
	}

	/**
	 * @return the new value
	 */
	public int getNewValue() {
		return newValue;
	}
	
	
	
	
	
}

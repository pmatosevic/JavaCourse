package hr.fer.zemris.java.hw07.observer2;

/**
 * An observer that prints the new doubled value on every change.
 * After {@code limit} changes, it unregisters itself.
 * 
 * @author Patrik
 *
 */
public class DoubleValue implements IntegerStorageObserver {

	/**
	 * The number of changes to print
	 */
	private int limit;
	
	/**
	 * Creates a new {@code DoubleValue}
	 * @param limit the number of changes to print.
	 */
	public DoubleValue(int limit) {
		this.limit = limit;
	}
	
	@Override
	public void valueChanged(IntegerStorageChange isChange) {
		System.out.println("Double value: " + (2 * isChange.getNewValue()));
		limit--;
		
		if (limit <= 0) {
			isChange.getIstorage().removeObserver(this);
		}
	}

}

package hr.fer.zemris.java.hw07.observer2;

/**
 * An observer that counts the changes and prints that number on every change.
 * 
 * @author Patrik
 *
 */
public class ChangeCounter implements IntegerStorageObserver {

	/**
	 * Change count
	 */
	private int count = 0;
	
	@Override
	public void valueChanged(IntegerStorageChange isChange) {
		count++;
		System.out.println("Number of value changes since tracking: " + count);
	}

}

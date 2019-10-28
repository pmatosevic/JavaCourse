package hr.fer.zemris.java.hw07.observer2;

/**
 * An observer that prints the new value squared on every change.
 * 
 * @author Patrik
 *
 */
public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorageChange isChange) {
		int val = isChange.getNewValue();
		System.out.printf("Provided new value: %d, square is %d%n", val, val * val);
	}

}

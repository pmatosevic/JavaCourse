package hr.fer.zemris.java.hw07.observer1;

/**
 * An interface for observers of the class {@link IntegerStorage}.
 * 
 * @author Patrik
 *
 */
@FunctionalInterface
public interface IntegerStorageObserver {

	/**
	 * Does the defined action of this observer.
	 * 
	 * @param istorage integer storage object
	 */
	public void valueChanged(IntegerStorage istorage);
	
}

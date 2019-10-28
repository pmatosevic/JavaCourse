package hr.fer.zemris.java.hw07.observer2;

import java.util.ArrayList;
import java.util.List;

/**
 * A subject that remembers one integer value and notifies its observers on its change.
 * 
 * @author Patrik
 *
 */
public class IntegerStorage {
	
	/**
	 * Currently stored value
	 */
	private int value;
	
	/**
	 * The list of observers
	 */
	private List<IntegerStorageObserver> observers;

	/**
	 * Creates a new {@code IntegerStorage}
	 * @param initialValue initial value
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
	}

	/**
	 * Adds an observer if it is not registered.
	 * 
	 * @param observer observer to add
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if (observers != null && observers.contains(observer)) {
			return;
		}
		
		ArrayList<IntegerStorageObserver> observersCopy = new ArrayList<>();
		if (observers != null) {
			observersCopy.addAll(observers);
		}
		observersCopy.add(observer);
		observers = observersCopy;
	}

	/**
	 * Removes the observer if it is registered.
	 * 
	 * @param observer observer to remove
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		if (observers == null || !observers.contains(observer)) {
			return;
		}
		
		ArrayList<IntegerStorageObserver> observersCopy = new ArrayList<>(observers);
		observersCopy.remove(observer);
		observers = observersCopy;
	}

	/**
	 * Removes all observers.
	 */
	public void clearObservers() {
		if (observers == null) {
			return;
		}
		
		observers = new ArrayList<>();
	}

	/**
	 * Returns the value.
	 * 
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets the value and notifies all observers about that change.
	 * 
	 * @param value the value
	 */
	public void setValue(int value) {
		if (this.value != value) {
			IntegerStorageChange change = new IntegerStorageChange(this, this.value, value);
			this.value = value;
			if (observers != null) {
				for (IntegerStorageObserver observer : observers) {
					observer.valueChanged(change);
				}
			}
		}
	}
}

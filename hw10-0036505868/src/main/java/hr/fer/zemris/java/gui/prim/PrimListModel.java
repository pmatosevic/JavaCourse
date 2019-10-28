package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * A {@link ListModel} that adds prime numbers to its list by calling {@link PrimListModel#next()}
 * 
 * @author Patrik
 *
 */
public class PrimListModel implements ListModel<Integer> {

	/**
	 * List of listeners
	 */
	private List<ListDataListener> listeners = new ArrayList<>();
	
	/**
	 * List of listeners
	 */
	private List<Integer> primeNumbers = new ArrayList<>();
	
	/**
	 * Last prime
	 */
	private int lastPrime = 1;
	
	/**
	 * Creates a new object
	 */
	public PrimListModel() {
		primeNumbers.add(1);
	}
	
	/**
	 * Adds the next prime number to its internal list
	 */
	public void next() {
		lastPrime++;
		while (!isPrime(lastPrime)) {
			lastPrime++;
		}
		
		int pos = primeNumbers.size();
		primeNumbers.add(lastPrime);
		
		ListDataEvent evt = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, pos, pos);
		for (ListDataListener l : listeners) {
			l.intervalAdded(evt);
		}
	}
	
	/**
	 * Checks whether the number is prime or not.
	 * @param x number
	 * @return whether the number is prime or not
	 */
	private boolean isPrime(int x) {
		for (int div = 2; div*div <= x; div += 2) {
			if (x%div == 0) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public int getSize() {
		return primeNumbers.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return primeNumbers.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		if (!listeners.contains(l)) {
			listeners.add(l);
		}
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
	}
	
}

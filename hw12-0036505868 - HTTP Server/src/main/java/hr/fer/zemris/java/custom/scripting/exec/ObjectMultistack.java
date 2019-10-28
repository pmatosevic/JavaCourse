package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * A map that has string as keys and allows storing multiple values for the same key
 * storing them in a stack.
 * 
 * @author Patrik
 *
 */
public class ObjectMultistack {

	/**
	 * Underlying map
	 */
	private Map<String, MultistackEntry> map = new HashMap<>();
	
	/**
	 * Pushes the given key-value pair.
	 * 
	 * @param keyName key
	 * @param valueWrapper value
	 */
	public void push(String keyName, ValueWrapper valueWrapper) {
		map.compute(keyName, (key, old) -> old == null ? 
				new MultistackEntry(null, valueWrapper) : new MultistackEntry(old, valueWrapper));
	}
	
	/**
	 * Removes and returns the value on top of the stack for given key.
	 * 
	 * @param keyName key
	 * @return the value on top of the stack for given key
	 */
	public ValueWrapper pop(String keyName) {
		if (!map.containsKey(keyName)) {
			throw new NoSuchElementException("Empty stack for this key.");
		}
		
		MultistackEntry entry = map.get(keyName);
		if (entry.next == null) {
			map.remove(keyName);
		} else {
			map.put(keyName, entry.next);
		}
		return entry.value;
	}
	
	/**
	 * Returns the value on top of the stack for given key.
	 * 
	 * @param keyName key
	 * @return the value on top of the stack for given key
	 */
	public ValueWrapper peek(String keyName) {
		if (!map.containsKey(keyName)) {
			throw new NoSuchElementException("Empty stack for this key.");
		}
		return map.get(keyName).value;
	}
	
	/**
	 * Returns whether the map contains any value for given key.
	 * 
	 * @param keyName key
	 * @return whether the map contains any value for given key
	 */
	public boolean isEmpty(String keyName) {
		return !map.containsKey(keyName);
	}
	
	
	/**
	 * An entry of the stack.
	 * 
	 * @author Patrik
	 *
	 */
	private static class MultistackEntry {
		
		/**
		 * Reference to the next entry
		 */
		private MultistackEntry next;
		
		/**
		 * Value of this entry
		 */
		private ValueWrapper value;
		
		/**
		 * Creates a new {@code MultistackEntry}
		 * @param next reference to the next entry
		 * @param value value
		 */
		public MultistackEntry(MultistackEntry next, ValueWrapper value) {
			this.next = next;
			this.value = value;
		}
		
	}
	
}

package hr.fer.zemris.java.custom.collections;

import java.util.Objects;


/**
 * Models a dictionary that stores key-value pairs.
 * Storage of pairs with key {@code null} is not allowed.
 * Uses {@link ArrayIndexedCollection} for storage.
 * 
 * @author Patrik
 *
 * @param <K> key
 * @param <V> value
 */
public class Dictionary<K, V> {

	/**
	 * List used for storage of key-value pairs
	 */
	private List<Entry<K, V>> col = new ArrayIndexedCollection<>();
	
	
	/**
	 * Creates a new {@code Dictionary}
	 */
	public Dictionary() {
	}
	
	
	/**
	 * Returns {@code true} if dictionary contains no entries and {@code false} otherwise
	 * @return {@code true} if dictionary contains no entries and {@code false} otherwise
	 */
	public boolean isEmpty() {
		return col.isEmpty();
	}
	
	/**
	 * Returns the number of currently stored key-value pairs in the dictionary.
	 * @return the number of currently stored key-value pairs in the dictionary.
	 */
	public int size() {
		return col.size();
	}
	
	
	/**
	 * Removes all key-value pairs from the dictionary
	 */
	public void clear() {
		col.clear();
	}
	
	/**
	 * Puts a new key-value pair (or updates value if the key is present) into this dictionary. 
	 * Key cannot be {@code null}.
	 * @param key key
	 * @param value value
	 * @throws NullPointerException if {@code key} is {@code null}
	 */
	public void put(K key, V value) {
		Objects.requireNonNull(key);
		Entry<K, V> entry = getEntry(key);
		if (entry == null) {
			col.add(new Entry<K, V>(key, value));
		} else {
			entry.value = value;
		}
	}
	
	/**
	 * Searches for the pair with given key and returns its value or {@code null} if not found.
	 * @param key key of the pair
	 * @return value of given key
	 */
	public V get(Object key) {
		Entry<K, V> entry = getEntry(key);
		return (entry == null) ? null : entry.value;
	}
	
	/**
	 * Searches and returns the pair with given key or {@code null} if not found.
	 * @param key key to search for
	 * @return pair with given key or {@code null} if not found
	 */
	private Entry<K, V> getEntry(Object key) {
		ElementsGetter<Entry<K, V>> it = col.createElementsGetter();
		while (it.hasNextElement()) {
			Entry<K, V> curEntry = it.getNextElement();
			if (curEntry.key.equals(key)) {
				return curEntry;
			}
		}
		return null;
	}
	
	
	/**
	 * Represents an entry (key-value pair) in the dictionary
	 * @author Patrik
	 *
	 * @param <K> key
	 * @param <V> value
	 */
	private static class Entry<K, V> {
		
		/**
		 * Key
		 */
		K key;
		
		/**
		 * Value
		 */
		V value;
		
		/**
		 * Creates a new {@code Entry}
		 * @param key key
		 * @param value value
		 */
		public Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}
		
	}
	
}

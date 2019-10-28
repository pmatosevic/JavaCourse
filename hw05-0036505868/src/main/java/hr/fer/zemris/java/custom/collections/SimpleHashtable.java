package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * 
 * Data structure that stores pairs of a key and a value into the hash table.
 * Putting pairs with {@code null} keys is not allowed, while {@code null} values are allowed.
 * Getting and putting runs in constant time (if the hash codes of the keys have proper distribution).
 * The underlying table is doubled in size every time current load factor exceeds 75%.
 * 
 * 
 * @author Patrik
 *
 * @param <K> key type
 * @param <V> value type
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {
	
	/**
	 * Default initial number of slots in the table
	 */
	private static final int DEFAULT_CAPACITY = 16;
	
	/**
	 * Maximum load factor, after which the table gets reallocated to a bigger one
	 */
	private static final double MAX_LOAD_FACTOR = 0.75;
	
	/**
	 * The factor to multiply the current table length with when moving to a bigger table
	 */
	private static final int GROWTH_FACTOR = 2;
	
	/**
	 * The hash table
	 */
	private TableEntry<K, V>[] table;
	
	/**
	 * The number of entries in the hash table
	 */
	private int size;
	
	/**
	 * Number of structural modifications since construction
	 */
	private long modificationCount = 0;
	
	/**
	 * Creates a new {@code SimpleHashtable}
	 */
	public SimpleHashtable() {
		this(DEFAULT_CAPACITY);
	}
	
	
	/**
	 * Creates a new {@code SimpleHashtable} with given initial size of the table.
	 * If that number is not a power of 2, first bigger power of 2 is used.
	 * 
	 * @param capacity desired initial size of the table
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if (capacity < 1) {
			throw new IllegalArgumentException("Initial capacity has to be positive.");
		}
		int numSlots = 1;
		while (numSlots < capacity) {
			numSlots *= 2;
		}
		table = (TableEntry<K, V>[]) new TableEntry[numSlots];
	}
	
	/**
	 * Puts a new key-value pair in the hash table if the key is not present, 
	 * or updates given key with new value if the key is already present. 
	 * Key cannot be {@code null}.
	 * 
	 * @param key key
	 * @param value value
	 * @throws NullPointerException if {@code key} is {@code null}
	 */
	public void put(K key, V value) {
		Objects.requireNonNull(key);
		
		if (put(table, key, value, true)) {
			size++;
			modificationCount++;
			
			if ((double)size/table.length > MAX_LOAD_FACTOR) {
				grow();
			}
		}
	}
	
	
	/**
	 * Moves all entries to a new hash table with double capacity of the current one.
	 */
	private void grow() {
		modificationCount++;
		
		@SuppressWarnings("unchecked")
		TableEntry<K, V>[] newTable = (TableEntry<K, V>[]) new TableEntry[GROWTH_FACTOR * table.length];
		
		Iterator<TableEntry<K, V>> it = iterator();
		while (it.hasNext()) {
			TableEntry<K, V> ent = it.next();
			put(newTable, ent.key, ent.value, false);			// equality checking is not needed
		}
		
		table = newTable;
	}

	/**
	 * Searches for the pair with given key and returns its value or {@code null} if not found.
	 * @param key key of the pair
	 * @return value of given key
	 */
	public V get(Object key) {
		if (key == null) {
			return null;
		}
		
		TableEntry<K, V> entry = table[slotOf(key)];
		while (entry != null) {
			if (entry.key.equals(key)) {
				return entry.value;
			}
			entry = entry.next;
		}
		return null;
	}
	
	
	/**
	 * Returns the number of currently stored key-value pairs in the hash table.
	 * @return the number of currently stored key-value pairs in the hash table.
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Returns whether a pair with given key exists in the hash table or not.
	 * @param key key to search
	 * @return whether a pair with given key exists in the hash table or not
	 */
	public boolean containsKey(Object key) {
		if (key == null) {
			return false;
		}
		
		TableEntry<K, V> entry = table[slotOf(key)];
		while (entry != null) {
			if (entry.key.equals(key)) {
				return true;
			}
			entry = entry.next;
		}
		
		return false;
	}
	
	
	/**
	 * Returns whether a pair with given value exists in the hash table or not.
	 * @param key key to search
	 * @return whether a pair with given value exists in the hash table or not
	 */
	public boolean containsValue(Object value) {
		for (int slot = 0; slot < table.length; slot++) {
			TableEntry<K, V> entry = table[slot];
			while (entry != null) {
				if (Objects.equals(entry.value, value)) {
					return true;
				}
				entry = entry.next;
			}
		}
		return false;
	}
	
	/**
	 * Removes the pair with given key or does nothing if that pair does not exist.
	 * @param key key of the pair to remove
	 */
	public void remove(Object key) {
		if (key == null) {
			return;
		}
		
		int slot = slotOf(key);
		if (table[slot] == null) {
			return;
		}

		if (table[slot].key.equals(key)) {
			table[slot] = table[slot].next;
			modificationCount++;
			size--;
		} else {
			TableEntry<K, V> entry = table[slot];
			while (entry.next != null) {
				if (entry.next.key.equals(key)) {
					entry.next = entry.next.next;
					modificationCount++;
					size--;
					break;
				}
				entry = entry.next;
			}
		}
		
	}
	
	/**
	 * Returns {@code true} if hash table contains no entries and {@code false} otherwise
	 * @return {@code true} if hash table contains no entries and {@code false} otherwise
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * Removes all key-value pairs from the hash table
	 */
	public void clear() {
		modificationCount++;
		for (int slot = 0; slot < table.length; slot++) {
			table[slot] = null;
		}
		size = 0;
	}
	
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		
		boolean first = true;
		for (int slot = 0; slot < table.length; slot++) {
			TableEntry<K, V> entry = table[slot];
			while (entry != null) {
				if (first) {
					first = false;
				} else {
					sb.append(", ");
				}
				sb.append(entry.toString());
				entry = entry.next;
			}
		}
		
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * Returns the slot in the table of given object
	 * @param key key
	 * @return the slot in the table of given object
	 */
	private int slotOf(Object key) {
		return slotOf(key, table.length);
	}
	
	/**
	 * Returns the slot in the table of given object
	 * @param key key
	 * @param length length of the table
	 * @return the slot in the table of given object
	 */
	private int slotOf(Object key, int length) {
		int hash = Math.abs(key.hashCode()) % length;
		return hash >= 0 ? hash : (hash + length) % length;		// handling Integer.MIN_VALUE
	}
	
	
	/**
	 * Puts the given key-value pair in the {@code entries} table, 
	 * and if {@code checkEquals} is {@code true} also checks whether given key
	 * is already in the table.
	 * @param entries entries table
	 * @param key key
	 * @param value value
	 * @param checkEqual whether to check if given key already exists
	 * @return {@code true} if a new pair is inserted, or {@code false} if it is only updated
	 * @throws NullPointerException if {@code key} is {@code null}
	 */
	private boolean put(TableEntry<K, V>[] entries, K key, V value, boolean checkEqual) {
		Objects.requireNonNull(key);
		int slot = slotOf(key, entries.length);
		
		if (entries[slot] == null) {
			entries[slot] = new TableEntry<>(key, value);
			return true;
		} else if (checkEqual && entries[slot].key.equals(key)) {
			entries[slot].value = value;
			return false;
		}
		
		TableEntry<K, V> entry = entries[slot];
		while (entry.next != null) {
			if (checkEqual && entry.next.key.equals(key)) {
				entry.next.value = value;
				return false;
			}
			entry = entry.next;
		}
		
		entry.next = new TableEntry<>(key, value);
		return true;
	}
	
	
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new SimpleHashtableIter();
	}
	
	
	/**
	 * An entry in the {@link SimpleHashtable} with a key, a value 
	 * and reference to the next entry in the slot.
	 * @author Patrik
	 *
	 * @param <K> key type
	 * @param <V> value type
	 */
	public static class TableEntry<K, V> {
		
		/**
		 * Key
		 */
		private K key;
		
		/**
		 * Value
		 */
		private V value;
		
		/**
		 * Next table entry
		 */
		private TableEntry<K, V> next;

		/**
		 * Creates a new entry
		 * @param key key
		 * @param value value
		 */
		private TableEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}
		
		
		/**
		 * Returns the value
		 * @return the value
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Sets the value
		 * @param value value to set
		 */
		public void setValue(V value) {
			this.value = value;
		}

		/**
		 * Returns the key
		 * @return the key
		 */
		public K getKey() {
			return key;
		}
		
		@Override
		public String toString() {
			return String.valueOf(key) + "=" + String.valueOf(value);
		}
		
	}
	
	/**
	 * Implementation of the {@link Iterator} for the {@link Hashtable}
	 * @author Patrik
	 *
	 */
	private class SimpleHashtableIter implements Iterator<TableEntry<K, V>> {
		
		/**
		 * Last slot
		 */
		private int slot = -1;
		
		/**
		 * Next entry
		 */
		private TableEntry<K, V> nextEntry;
		
		/**
		 * Last entry
		 */
		private TableEntry<K, V> lastEntry;
		
		/**
		 * Copied modification counter of the hash table
		 */
		private long savedModificationCount;
		
		/**
		 * Create a new {@code SimpleHashtableIter}
		 */
		public SimpleHashtableIter() {
			savedModificationCount = modificationCount;
			findNext();
		}

		@Override
		public boolean hasNext() {
			if (savedModificationCount != modificationCount) {
				throw new ConcurrentModificationException("Hashtable was modified.");
			}
			return nextEntry != null;
		}

		@Override
		public TableEntry<K, V> next() {
			if (!hasNext()) {
				throw new NoSuchElementException("Already reached the end.");
			}
			TableEntry<K, V> result = nextEntry;
			lastEntry = nextEntry;
			findNext();
			return result;
		}
		
		/**
		 * Removes from the underlying collection the last element returned
		 * by this iterator (optional operation).  This method can be called
		 * only once per call to {@link #next}.
		 * 
		 * @throws IllegalStateException if the {@code next} method has not
		 * yet been called, or the {@code remove} method has already
		 * been called after the last call to the {@code next}
		 * method
		 */
		@Override
		public void remove() {
			if (lastEntry == null) {
				throw new IllegalStateException("No element to remove.");
			}
			
			SimpleHashtable.this.remove(lastEntry.key);
			lastEntry = null;
			savedModificationCount = modificationCount;
		}
		
		/**
		 * Finds the next entry
		 */
		private void findNext() {
			if (nextEntry != null) {
				nextEntry = nextEntry.next;
			}
			
			while (nextEntry == null) {
				slot++;
				if (slot == table.length) {
					return;
				}
				nextEntry = table[slot];
			}
		}
		
	}
	
	
}

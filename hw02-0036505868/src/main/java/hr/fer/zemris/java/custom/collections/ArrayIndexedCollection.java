package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
import java.util.Objects;

/**
 * 
 * Represents an array based collection. Uses an array for storage.
 * The size of this array is capacity. The default initial capacity is 16, 
 * and it is doubled every time the collection runs out of space when adding elements.
 * Accessing elements and adding them at the end runs in constant time, and adding them
 * at arbitrary position runs in linear time.
 * Storage of {@code null} references is not allowed.
 * 
 * @author Patrik
 *
 */
public class ArrayIndexedCollection extends Collection {

	/**
	 * Current size of collection (number of elements actually stored)
	 */
	private int size = 0;
	
	/**
	 * An array used to store references to objects in this collection
	 */
	private Object[] elements;
	
	/**
	 * Default initial capacity of the collection
	 */
	private static final int DEFAULT_CAPACITY = 16;
	
	
	/**
	 * Constructs a new {@code ArrayIndexedCollection} with default capacity of 16
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT_CAPACITY);
	}
	
	/**
	 * Constructs a new {@code ArrayIndexedCollection} with given initial capacity
	 * 
	 * @param initialCapacity initial capacity of the underlying array
	 * @throws IllegalArgumentException if {@code initialCapacity} is not positive
	 */
	public ArrayIndexedCollection(int initialCapacity)  {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException("Initial capacity should be greater than 0.");
		}
		this.elements = new Object[initialCapacity];
	}
	
	/**
	 * Constructs a new {@code ArrayIndexedCollection} and copies elements from given collection.
	 * The initial capacity will be the size of the given collection.
	 * 
	 * @param other collection whose elements are copied
	 * @throws NullPointerException if {@code other} is {@code null}
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other, other.size());
	}
	
	/**
	 * Constructs a new {@code ArrayIndexedCollection} and copies elements from given collection.
	 * If the {@code initialCapacity} is less than given collection size, then its size is used as
	 * an initial capacity.
	 * 
	 * @param other collection whose elements are copied
	 * @param initialCapacity initial capacity of the underlying array
	 * @throws IllegalArgumentException if {@code initialCapacity} is not positive
	 * @throws NullPointerException if {@code other} is {@code null}
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		Objects.requireNonNull(other, "Other collection cannot be null.");
		if (initialCapacity < 1) {
			throw new IllegalArgumentException("Initial capacity should be greater than 0.");
		}
		int capacity = initialCapacity >= other.size() ? initialCapacity : other.size();
		this.elements = new Object[capacity];
		addAll(other);
	}
	
	
	
	
	
	@Override
	public int size() {
		return size;
	}
	
	
	@Override
	public boolean contains(Object value) {
		return indexOf(value) != -1;
	}
	
	
	@Override
	public boolean remove(Object value) {
		int index = indexOf(value);
		
		if (index != -1) {
			remove(index);
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public Object[] toArray() {
		return Arrays.copyOf(elements, size);
	}
	
	
	/**
	 * {@inheritDoc}
	 * The elements are processed in the order they are in the collection
	 */
	@Override
	public void forEach(Processor processor) {
		for (int i = 0; i < size; i++) {
			processor.process(elements[i]);
		}
	}
	
	
	/**
	 * {@inheritDoc}
	 * Given object is added at the end of the collection.
	 * If the underlying array is full, then the capacity is doubled.
	 * Runs in constant time.
	 * 
	 * @throws NullPointerException if {@code value} is {@code null}
	 */
	@Override
	public void add(Object value) {
		insert(value, size);
	}
	
	
	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
	}
	
	
	
	/**
	 * Returns the object that is stored in backing array at position {@code index}.
	 * Valid indexes are from {@code 0} to  {@code size-1}.
	 * Runs in constant time.
	 * 
	 * @param index index of the wanted object
	 * @return the object at given {@code index}
	 * @throws IndexOutOfBoundsException if {@code index} is not valid
	 */
	public Object get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException(index);
		}
		return elements[index];
	}
	
	
	
	/**
	 * Inserts (does not overwrite) the given value at the specified position in array.
	 * The legal positions are 0 to size (both included).
	 * If the underlying array is full, then the capacity is doubled.
	 * Runs in linear time.
	 *  
	 * @param value object to insert to the collection
	 * @param position position in array at which given element should be
	 * @throws NullPointerException if {@code value} is {@code null}
	 * @throws IndexOutOfBoundsException if {@code position} is not valid
	 */
	public void insert(Object value, int position) {
		Objects.requireNonNull(value, "null elements are not supported.");
		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException(position);
		}
		
		if (size >= elements.length) {
			elements = Arrays.copyOf(elements, 2 * elements.length);
		}
		
		for (int i = size - 1; i >= position; i--) {
			elements[i+1] = elements[i];
		}
		elements[position] = value;
		size++;
	}
	
	
	/**
	 * Searches the collection and returns the index of the first occurrence of the given value 
	 * or -1 if it is not found, as determined by {@code equals} method. 
	 * Runs in linear time.
	 * 
	 * @param value object to search for in the collection (can be null)
	 * @return index of the first occurrence of the given value or -1 if not found
	 */
	public int indexOf(Object value) {
		if (value == null) {
			return -1;
		}
		
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}
	
	
	/**
	 * Removes element at specified {@code index} from collection and shifts all elements after
	 * the specified element to the left.
	 * Legal indexes are {@code 0} to {@code size-1}.
	 * 
	 * @param index index of the object to remove
	 * @throws IndexOutOfBoundsException if {@code index} is not valid
	 */
	public void remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException(index);
		}
		
		for (int i = index; i < size - 1; i++) {
			elements[i] = elements[i+1];
		}
		elements[size] = null;
		size--;
	}
	
}

package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * Represents an linked list based collection. Uses doubly linked nodes for storage.
 * Accessing arbitrary elements and adding them at the end runs in linear time, but
 * accessing or adding them at the start or at the end runs in constant time.
 * Storage of {@code null} references is not allowed.
 * 
 * @author Patrik
 *
 */
public class LinkedListIndexedCollection extends Collection {

	
	/**
	 * Current size of collection (number of elements actually stored)
	 */
	private int size = 0;
	
	/**
	 * Reference to the first node of the linked list
	 */
	private ListNode first = null;
	
	/**
	 * Reference to the last node of the linked list
	 */
	private ListNode last = null;
	
	
	/**
	 * Constructs a new empty {@code LinkedListCollection} 
	 */
	public LinkedListIndexedCollection() {
	}
	
	/**
	 * Constructs a new {@code LinkedListCollection} containing elements
	 * from given collection.
	 * 
	 * @param other collection to copy elements from
	 * @throws NullPointerException if {@code other} is {@code null}
	 */
	public LinkedListIndexedCollection(Collection other) {
		Objects.requireNonNull(other, "Other collection cannot be null.");
		addAll(other);
	}
	
	
	
	@Override
	public int size() {
		return size;
	}

	/**
	 * {@inheritDoc}
	 * Given object is added at the end of the collection.
	 */
	@Override
	public void add(Object value) {
		insert(value, size);
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
		Object[] array = new Object[size];
		ListNode node = first;
		for (int i = 0; i < size; i++) {
			array[i] = node.value;
			node = node.next;
		}
		return array;
	}
	
	/**
	 * {@inheritDoc}
	 * The elements are processed in the order they are in the collection
	 */
	@Override
	public void forEach(Processor processor) {
		ListNode node = first;
		while (node != null) {
			processor.process(node.value);
			node = node.next;
		}
	}

	@Override
	public void clear() {
		first = last = null;
		size = 0;
	}
	
	
	/**
	 * Returns the object that is stored in backing array at position {@code index}.
	 * Valid indexes are from {@code 0} to  {@code size-1}.
	 * Runs in linear time.
	 * 
	 * @param index index of the wanted object
	 * @return the object at given {@code index}
	 * @throws IndexOutOfBoundsException if {@code index} is not valid
	 */
	public Object get(int index) {
		return getNodeAt(index).value;
	}
	
	
	/**
	 * Inserts (does not overwrite) the given value at the specified position in linked-list.
	 * Elements starting from this position are shifted one position.
	 * The legal positions are 0 to size (both included).
	 * Runs in linear time (in general).
	 *  
	 * @param value object to insert to the collection
	 * @param position position in array at which given element should be
	 * @throws NullPointerException if {@code value} is {@code null}
	 * @throws IndexOutOfBoundsException if {@code position} is not valid
	 */
	public void insert(Object value, int position) {
		Objects.requireNonNull(value, "null values are not supported.");
		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException(position);
		}
		
		ListNode newNode = new ListNode(value);
		
		if (size == 0) {
			first = last = newNode;
		} else if (position == 0) {
			newNode.next = first;
			first.previous = newNode;
			first = newNode;
		} else if (position == size) {
			newNode.previous = last;
			last.next = newNode;
			last = newNode;
		} else {
			ListNode before = getNodeAt(position - 1);
			ListNode after = before.next;
			newNode.next = after;
			newNode.previous = before;
			before.next = newNode;
			after.previous = newNode;
		}
		
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
		
		ListNode node = first;
		int index = 0;
		while (node != null) {
			if (node.value.equals(value)) {
				return index;
			}
			index++;
			node = node.next;
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
		
		if (size == 1) {
			first = null;
			last = null;
		} else if (index == 0) {
			first = first.next;
			first.previous = null;
		} else if (index == size - 1) {
			last = last.previous;
			last.next = null;
		} else {
			ListNode node = getNodeAt(index);
			node.previous.next = node.next;
			node.next.previous = node.previous;
		}
		
		size--;
	}
	
	
	
	/**
	 * Returns the node at the specified {@code index}
	 * 
	 * @param index index of the wanted node
	 * @return {@code ListNode} at the specified {@code index}
	 * @throws IndexOutOfBoundsException if {@code index} is not valid.
	 */
	private ListNode getNodeAt(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException(index);
		}
		
		ListNode node;
		if (index < size/2) {
			node = first;
			for (int i = 0; i < index; i++) {
				node = node.next;
			}
		} else {
			node = last;
			for (int i = size - 1; i > index; i--) {
				node = node.previous;
			}
		}
		return node;
	}
	
	
	/**
	 * Represents a node in a linked list
	 * @author Patrik
	 *
	 */
	private static class ListNode {
		
		/**
		 * Reference to the previous node
		 */
		ListNode previous;
		
		/**
		 * Reference to the next node
		 */
		ListNode next;
		
		/**
		 * Value this node holds
		 */
		Object value;

		/**
		 * Constructs a new {@code ListNode}
		 * @param value object which this node should hold
		 */
		ListNode(Object value) {
			this.value = value;
		}
	}

	
	
}

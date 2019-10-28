package hr.fer.zemris.java.custom.collections;

/**
 * Represents a list data structure.
 * Each stored element has a position and can be accessed, inserted or removed given some position.
 * 
 * @author Patrik
 *
 * @param <T> type
 */
public interface List<T> extends Collection<T> {

	/**
	 * Returns the object that is stored at position {@code index}.
	 * 
	 * @param index index of the wanted object
	 * @return the object at given {@code index}
	 */
	T get(int index);
	
	/**
	 * Inserts (does not overwrite) the given value at the specified position in the list.
	 * 
	 * @param value object to insert to the list
	 * @param position position in array at which given element should be
	 */
	void insert(T value, int position);
	
	/**
	 * Searches the collection and returns the index of the first occurrence of the given value 
	 * or -1 if it is not found.
	 * 
	 * @param value object to search for in the collection
	 * @return index of the first occurrence of the given value or -1 if not found
	 */
	int indexOf(Object value);
	
	/**
	 * Removes element at specified {@code index} from the list.
	 * 
	 * @param index index of the object to remove
	 */
	void remove(int index);
	
}

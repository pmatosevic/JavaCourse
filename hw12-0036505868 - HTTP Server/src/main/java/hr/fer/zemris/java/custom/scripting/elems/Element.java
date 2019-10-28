package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Represents an element. Base class for all elements.
 * 
 * @author Patrik
 *
 */
public class Element {

	/**
	 * Returns the value stored in the element as text.
	 * 
	 * @return the value stored in the element as text
	 */
	public String asText() {
		return "";
	}
	
	/**
	 * Returns the plain text representation of the element (as would be read by parser).
	 * Escapes characters if needed.
	 * @return string representation
	 */
	public String toString() {
		return "";
	}
	
	
	
	
}

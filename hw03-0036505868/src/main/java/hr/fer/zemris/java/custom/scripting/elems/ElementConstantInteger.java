package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Represents an element containing an integer value.
 * 
 * @author Patrik
 *
 */
public class ElementConstantInteger extends Element {

	/**
	 * Stored integer value.
	 */
	private int value;
	
	/**
	 * Creates a new {@code ElementConstantInteger} with given value
	 * @param value value to store
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}

	
	


	/**
	 * Returns the value
	 * @return the value
	 */
	public int getValue() {
		return value;
	}





	@Override
	public String asText() {
		return String.valueOf(value);
	}
	
	@Override
	public String toString() {
		return String.valueOf(value);
	}





	@Override
	public int hashCode() {
		return Objects.hash(value);
	}





	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ElementConstantInteger))
			return false;
		ElementConstantInteger other = (ElementConstantInteger) obj;
		return value == other.value;
	}
	
	
	
	
}

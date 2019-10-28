package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Represents an element containing a double value.
 * 
 * @author Patrik
 *
 */
public class ElementConstantDouble extends Element {

	/**
	 * Stored double value
	 */
	private double value;
	
	/**
	 * Creates a new {@code ElementConstantDouble} with given value.
	 * @param value value to store
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}


	

	/**
	 * Returns the value
	 * @return the value
	 */
	public double getValue() {
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
		if (!(obj instanceof ElementConstantDouble))
			return false;
		ElementConstantDouble other = (ElementConstantDouble) obj;
		return Double.doubleToLongBits(value) == Double.doubleToLongBits(other.value);
	}
	
	
	
	
	
}

package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Represents an element containing a variable name
 * @author Patrik
 *
 */
public class ElementVariable extends Element {

	/**
	 * Variable name
	 */
	private String name;
	
	/**
	 * Creates a new {@code ElementVariable.java} with given variable name.
	 * @param name variable name
	 */
	public ElementVariable(String name) {
		this.name = name;
	}



	@Override
	public String asText() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}



	@Override
	public int hashCode() {
		return Objects.hash(name);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ElementVariable))
			return false;
		ElementVariable other = (ElementVariable) obj;
		return Objects.equals(name, other.name);
	}
	
	
	
	
	
	
	
}

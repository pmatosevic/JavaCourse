package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Represent an element containing a function name.
 * 
 * @author Patrik
 *
 */
public class ElementFunction extends Element {

	/**
	 * The function name
	 */
	private String name;

	/**
	 * Creates a new {@code ElementFunction.java} with given function name
	 * @param name function name
	 */
	public ElementFunction(String name) {
		this.name = name;
	}
	
	
	
	
	/**
	 * Returns the function name
	 * @return the function name
	 */
	public String getName() {
		return name;
	}

	@Override
	public String asText() {
		return name;
	}
	
	@Override
	public String toString() {
		return "@" + name;
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
		if (!(obj instanceof ElementFunction))
			return false;
		ElementFunction other = (ElementFunction) obj;
		return Objects.equals(name, other.name);
	}
	
	
	
	
}

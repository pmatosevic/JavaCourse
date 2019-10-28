package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;


/**
 * Represents an element containing a string.
 * @author Patrik
 *
 */
public class ElementString extends Element {

	/**
	 * The string
	 */
	private String value;
	
	/**
	 * Creates a new {@code ElementString.java} with given string
	 * @param value string to store
	 */
	public ElementString (String value) {
		this.value = value;
	}
	
	


	/**
	 * Returns the string value
	 * @return the string value
	 */
	public String getValue() {
		return value;
	}




	@Override
	public String asText() {
		return value;
	}
	
	@Override
	public String toString() {
		char[] data = value.toCharArray();
		StringBuilder sb = new StringBuilder();
		
		sb.append("\"");
		for (int pos = 0; pos < data.length; pos++) {
			switch(data[pos]) {
			case '\\':
				sb.append("\\\\");
				break;
			case '"':
				sb.append("\\\"");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			default:
				sb.append(data[pos]);
			}
		}
		sb.append("\"");
		
		return sb.toString();
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
		if (!(obj instanceof ElementString))
			return false;
		ElementString other = (ElementString) obj;
		return Objects.equals(value, other.value);
	}
	
	
	
	
	
	
	
}

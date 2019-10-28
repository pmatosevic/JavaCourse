package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Represent an element containing an operator symbol
 * @author Patrik
 *
 */
public class ElementOperator extends Element {

	/**
	 * Operator symbol
	 */
	private String symbol;

	/**
	 * Creates a new {@code ElementOperator} of given operator
	 * @param symbol operator symbol
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}
	
	
	
	
	
	/**
	 * Returns the symbol
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}



	@Override
	public String asText() {
		return symbol;
	}
	
	@Override
	public String toString() {
		return symbol;
	}





	@Override
	public int hashCode() {
		return Objects.hash(symbol);
	}





	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ElementOperator))
			return false;
		ElementOperator other = (ElementOperator) obj;
		return Objects.equals(symbol, other.symbol);
	}
	
	
	
	
	
	
}

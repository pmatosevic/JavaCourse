package hr.fer.zemris.java.custom.scripting.exec;

import java.util.Objects;
import java.util.function.BinaryOperator;

/**
 * A wrapper class that stores an object and can perform opertations on it.
 * 
 * @author Patrik
 *
 */
public class ValueWrapper {

	/**
	 * The stored object
	 */
	private Object value;

	/**
	 * Creates a new {@code ValueWrapper}
	 * @param value object to store
	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	
	/**
	 * Adds the given value to the stored value according to the conversion rules.
	 * 
	 * @param incValue value to increment with
	 * @throws IllegalArgumentException in case of an invalid type of the stored or given object
	 */
	public void add(Object incValue) {
		value = performOperation(Integer::sum, Double::sum, value, incValue);
	}
	
	/**
	 * Subtracts the given value from the stored value according to the conversion rules.
	 * 
	 * @param decValue value to decrement
	 * @throws IllegalArgumentException in case of an invalid type of the stored or given object
	 */
	public void subtract(Object decValue) {
		value = performOperation((a, b) -> a - b, (a, b) -> a - b, value, decValue);
	}
	
	/**
	 * Multiplies the stored value with given value according to the conversion rules.
	 * 
	 * @param mulValue value to multiply with
	 * @throws IllegalArgumentException in case of an invalid type of the stored or given object
	 */
	public void multiply(Object mulValue) {
		value = performOperation((a, b) -> a * b, (a, b) -> a * b, value, mulValue);
	}
	
	/**
	 * Divides the stored value with the given value according to the conversion rules.
	 * 
	 * @param divValue value to divide with
	 * @throws IllegalArgumentException in case of an invalid type of the stored or given object
	 * @throws ArithmeticException in case of an integer division by 0
	 */
	public void divide(Object divValue) {
		value = performOperation((a, b) -> a / b, (a, b) -> a / b, value, divValue);
	}
	
	/**
	 * Compares the stored and given value.
	 * 
	 * @param withValue given value
	 * @return negative number, 0 or positive number that represents less, equal or greater
	 */
	public int numCompare(Object withValue) {
		Number oper1 = tryConvert(value);
		Number oper2 = tryConvert(withValue);
		
		if (oper1 instanceof Double || oper2 instanceof Double) {
			return Double.compare(oper1.doubleValue(), oper2.doubleValue());
		} else {
			return Integer.compare(oper1.intValue(), oper2.intValue());
		}
	}
	
	/**
	 * Converts the object to a {@link Number} according to the conversion rules.
	 * 
	 * @param obj object
	 * @return converted value
	 * @throws IllegalArgumentException in case of an invalid type of the object
	 */
	public static Number tryConvert(Object obj) {
		if (obj == null) {
			return Integer.valueOf(0);
		}
		if (obj instanceof Integer || obj instanceof Double) {
			return (Number) obj;
		}
		if (!(obj instanceof String)) {
			throw new IllegalArgumentException("Illegal object type");
		}
		
		String str = (String) obj;
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
		}
		try {
			return Double.parseDouble(str);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Illegal foramt of string.");
		}
	}
	
	/**
	 * Performs the operation (using given operators) on the stored and given value.
	 * 
	 * @param intOper integer operator (if both operands are integers)
	 * @param doubleOper double operator (if either operand is real number)
	 * @param other given value
	 * @return the result of the performed operation
	 * @throws IllegalArgumentException in case of an invalid type of the stored or given object
	 * @throws ArithmeticException in case of an integer division by 0
	 */
	public static Number performOperation(BinaryOperator<Integer> intOper, BinaryOperator<Double> doubleOper, Object value, Object other) {
		Number oper1 = tryConvert(value);
		Number oper2 = tryConvert(other);
		
		if (oper1 instanceof Double || oper2 instanceof Double) {
			return doubleOper.apply(oper1.doubleValue(), oper2.doubleValue());
		} else {
			return intOper.apply(oper1.intValue(), oper2.intValue());
		}
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ValueWrapper))
			return false;
		ValueWrapper other = (ValueWrapper) obj;
		return Objects.equals(value, other.value);
	}
	
	
	
	
}

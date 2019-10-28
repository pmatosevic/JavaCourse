package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * Class that represents a conditional expressions.
 * @author Patrik
 *
 */
public class ConditionalExpression {

	/**
	 * Getter for the attribute
	 */
	private IFieldValueGetter fieldGetter;
	
	/**
	 * Pattern that will be used in comparison
	 */
	private String stringLiteral;
	
	/**
	 * Comparison operator
	 */
	private IComparisonOperator comparisonOperator;

	/**
	 * Creates a new {@code ConditionalExpression} with given parameters.
	 * @param fieldValueGetter getter for the attribute
	 * @param stringLiteral pattern that will be used in comparison
	 * @param comparisonOperator comparison operator
	 * @throws NullPointerException if any of the parameters is {@code null}
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral,
			IComparisonOperator comparisonOperator) {
		this.fieldGetter = Objects.requireNonNull(fieldGetter);
		this.stringLiteral = Objects.requireNonNull(stringLiteral);
		this.comparisonOperator = Objects.requireNonNull(comparisonOperator);
	}

	/**
	 * Returns the getter for the attribute
	 * @return the getter for the attribute
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 * Returns the string literal.
	 * @return the string literal
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * Returns the comparison operator.
	 * @return the comparison operator
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
	
	
	
	
}

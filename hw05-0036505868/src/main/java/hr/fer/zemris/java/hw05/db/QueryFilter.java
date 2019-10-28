package hr.fer.zemris.java.hw05.db;

import java.util.List;

/**
 * Represent a filter that accepts records based on given expressions.
 * 
 * @author Patrik
 *
 */
public class QueryFilter implements IFilter {
	
	/**
	 * List of expressions
	 */
	private List<ConditionalExpression> expressions;
	
	/**
	 * Creates a new {@code QueryFilter} with given expressions
	 * @param expressions list of expressions
	 */
	public QueryFilter(List<ConditionalExpression> expressions) {
		this.expressions = expressions;
	}
	
	@Override
	public boolean accepts(StudentRecord record) {
		for (ConditionalExpression expr : expressions) {
			if (!expr.getComparisonOperator().satisfied(
					expr.getFieldGetter().get(record), 
					expr.getStringLiteral())) {
				return false;
			}
		}
		return true;
	}

	
	
}

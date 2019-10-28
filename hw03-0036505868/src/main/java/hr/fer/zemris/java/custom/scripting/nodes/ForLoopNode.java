package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * A node representing a single for-loop construct.
 * @author Patrik
 *
 */
public class ForLoopNode extends Node {

	/**
	 * The variable
	 */
	private ElementVariable variable;
	
	/**
	 * The start expression
	 */
	private Element startExpression;
	
	/**
	 * The end expression
	 */
	private Element endExpression;
	
	/**
	 * The step expression (can be null)
	 */
	private Element stepExpression;

	/**
	 * Creates a new {@code ForLoopNode}
	 * 
	 * @param variable variable
	 * @param startExpression start expression
	 * @param endExpression end expression
	 * @param stepExpression step expression
	 * @throws NullPointerException if any of parameters (except {@code stepExpression}) is {@code null}
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		this.variable = Objects.requireNonNull(variable);
		this.startExpression = Objects.requireNonNull(startExpression);
		this.endExpression = Objects.requireNonNull(endExpression);
		this.stepExpression = stepExpression;
	}
	
	/**
	 * Creates a new {@code ForLoopNode}
	 * 
	 * @param variable variable
	 * @param startExpression start expression
	 * @param endExpression end expression
	 * @throws NullPointerException if any of parameters is {@code null}
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression) {
		this.variable = Objects.requireNonNull(variable);
		this.startExpression = Objects.requireNonNull(startExpression);
		this.endExpression = Objects.requireNonNull(endExpression);
	}

	/**
	 * Returns the variable
	 * @return the variable
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * Returns the start expression
	 * @return the start expression
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * Returns the end expression
	 * @return the end expression
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * Returns the step expression
	 * @return the step expression
	 */
	public Element getStepExpression() {
		return stepExpression;
	}
	
	
	@Override
	public String getAsPlainText() {
		StringBuilder sb = new StringBuilder();
		sb.append("{$FOR ");
		sb.append(variable + " ");
		sb.append(startExpression + " ");
		sb.append(endExpression + " ");
		sb.append(stepExpression == null ? "" : stepExpression + " ");
		sb.append("$}");
		sb.append(super.getAsPlainText());
		sb.append("{$END$}");
		return sb.toString();
	}


	@Override
	public int hashCode() {
		return Objects.hash(endExpression, startExpression, stepExpression, variable);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof ForLoopNode))
			return false;
		ForLoopNode other = (ForLoopNode) obj;
		return Objects.equals(endExpression, other.endExpression)
				&& Objects.equals(startExpression, other.startExpression)
				&& Objects.equals(stepExpression, other.stepExpression) && Objects.equals(variable, other.variable);
	}
	
	
	
	
	
}

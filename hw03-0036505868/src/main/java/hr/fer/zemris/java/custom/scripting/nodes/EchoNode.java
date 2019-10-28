package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Arrays;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * A node representing a command which generates some textual output dynamically
 * 
 * @author Patrik
 *
 */
public class EchoNode extends Node {

	/**
	 * Elements of the echo node.
	 */
	private Element[] elements;

	/**
	 * Creates a new {@code EchoNode}
	 * @param elements elements
	 */
	public EchoNode(Element[] elements) {
		this.elements = elements;
	}

	/**
	 * Returns the elements
	 * @return the elements
	 */
	public Element[] getElements() {
		return elements;
	}
	
	
	@Override
	public String getAsPlainText() {
		StringBuilder sb = new StringBuilder();
		sb.append("{$= ");
		for (Element el : elements) {
			sb.append(el.toString() + " ");
		}
		sb.append("$}");
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(elements);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof EchoNode))
			return false;
		EchoNode other = (EchoNode) obj;
		return Arrays.equals(elements, other.elements);
	}
	
	
	
	
	
}

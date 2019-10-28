package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.List;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * Base class for all graph nodes.
 * 
 * @author Patrik
 *
 */
public abstract class Node {

	/**
	 * Children of the node
	 */
	List nodesList = null;
	
	
	/**
	 * Adds a child node to this node.
	 * 
	 * @param child node to add
	 */
	public void addChildNode(Node child) {
		if (nodesList == null) {
			nodesList = new ArrayIndexedCollection();
		}
		nodesList.add(child);
	}
	
	/**
	 * Returns the number of children.
	 * @return the number of children
	 */
	public int numberOfChildren() {
		if (nodesList == null) {
			return 0;
		}
		return nodesList.size();
	}
	
	
	/**
	 * Returns child at specified index.
	 * 
	 * @param index index
	 * @return child at specified index
	 * @throws IndexOutOfBoundsException if {@code index} is invalid
	 */
	public Node getChild(int index) {
		return (Node) nodesList.get(index);
	}
	
	
	/**
	 * Returns the plain text representation (that {@link SmartScriptParser} can parse) 
	 * of the node escaping characters if necessary.
	 * 
	 * @return plain text representation of the node
	 */
	public String getAsPlainText() {
		if (nodesList == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		nodesList.forEach(p -> sb.append(((Node) p).getAsPlainText()));
		return sb.toString();
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(nodesList);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Node))
			return false;
		Node other = (Node) obj;
		return Objects.equals(nodesList, other.nodesList);
	}
	
	
	
}

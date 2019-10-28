package hr.fer.zemris.java.custom.scripting.nodes;


/**
 * A node representing an entire document
 * 
 * @author Patrik
 *
 */
public class DocumentNode extends Node {

	/**
	 * Creates a new {@code DocumentNode}
	 */
	public DocumentNode() {
		
	}
	
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof DocumentNode))
			return false;
		return true;
	}
	
	
	
	
}

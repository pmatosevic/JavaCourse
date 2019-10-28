package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Represents a visitor that can visit and process nodes.
 * @author Patrik
 *
 */
public interface INodeVisitor {
	
	/**
	 * Visits the text node
	 * @param node node
	 */
	public void visitTextNode(TextNode node);
	
	/**
	 * Visits the for loop node
	 * @param node node
	 */
	public void visitForLoopNode(ForLoopNode node);
	
	/**
	 * Visits the echo node
	 * @param node node
	 */
	public void visitEchoNode(EchoNode node);
	
	/**
	 * Visits the document node
	 * @param node node
	 */
	public void visitDocumentNode(DocumentNode node);
	
}

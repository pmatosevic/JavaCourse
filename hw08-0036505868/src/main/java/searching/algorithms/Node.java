package searching.algorithms;

/**
 * Represents a node with a state, parent node, and cost.
 * 
 * @author Patrik
 *
 * @param <S> state type
 */
public class Node<S> {
	
	/**
	 * The state
	 */
	private S state;
	
	/**
	 * The cost
	 */
	private double cost;
	
	/**
	 * The parent
	 */
	private Node<S> parent;
	
	/**
	 * Creates a new object.
	 * 
	 * @param state state
	 * @param cost cost
	 * @param parent parent node
	 */
	public Node(Node<S> parent, S state, double cost) {
		this.state = state;
		this.cost = cost;
		this.parent = parent;
	}

	/**
	 * @return the state
	 */
	public S getState() {
		return state;
	}

	/**
	 * @return the cost
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * @return the parent
	 */
	public Node<S> getParent() {
		return parent;
	}
	
	
	
}

package searching.algorithms;

/**
 * Represents a transition with a state and a cost.
 * @author Patrik
 *
 * @param <S> state type
 */
public class Transition<S> {

	/**
	 * The state
	 */
	private S state;
	
	/**
	 * The cost
	 */
	private double cost;

	/**
	 * Creates a new object
	 * @param state state
	 * @param cost cost
	 */
	public Transition(S state, double cost) {
		this.state = state;
		this.cost = cost;
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
	
	
	
}

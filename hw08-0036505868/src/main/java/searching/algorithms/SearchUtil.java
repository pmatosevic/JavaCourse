package searching.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * A class with static methods for searching state spaces.
 * 
 * @author Patrik
 *
 */
public class SearchUtil {

	/**
	 * A basic breadth-first search implementation of state space search.
	 * 
	 * @param s0 initial state
	 * @param succ successor function
	 * @param goal goal predicate
	 * @return the final goal node
	 */
	public static <S> Node<S> bfs(Supplier<S> s0, Function<S, 
			List<Transition<S>>> succ, Predicate<S> goal) {
		LinkedList<Node<S>> toExplore = new LinkedList<>();
		toExplore.add(new Node<>(null, s0.get(), 0));
		
		while (!toExplore.isEmpty()) {
			Node<S> node = toExplore.removeFirst();
			if (goal.test(node.getState())) {
				return node;
			}
			
			List<Transition<S>> transitions = succ.apply(node.getState());
			for (Transition<S> trans : transitions) {
				toExplore.addLast(new Node<>(node, trans.getState(), node.getCost() + trans.getCost()));
			}
		}
		
		return null;
	}
	
	/**
	 * A basic breadth-first search (with a visited list) implementation of state space search.
	 * 
	 * @param s0 initial state
	 * @param succ successor function
	 * @param goal goal predicate
	 * @return the final goal node
	 */
	public static <S> Node<S> bfsv(Supplier<S> s0, Function<S, 
			List<Transition<S>>> succ, Predicate<S> goal) {
		LinkedList<Node<S>> toExplore = new LinkedList<>();
		Set<S> explored = new HashSet<>();
		toExplore.add(new Node<>(null, s0.get(), 0));
		explored.add(s0.get());
		
		while (!toExplore.isEmpty()) {
			Node<S> node = toExplore.pop();
			if (goal.test(node.getState())) {
				return node;
			}
			
			List<Transition<S>> transitions = succ.apply(node.getState());
			for (Transition<S> trans : transitions) {
				if (explored.add(trans.getState())) {
					toExplore.addLast(new Node<>(node, trans.getState(), node.getCost() + trans.getCost()));
				}
			}
		}
		
		return null;
	}
	
}

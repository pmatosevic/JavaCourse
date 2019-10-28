package coloring.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;


/**
 * A class with static methods for searching state spaces.
 * 
 * @author Patrik
 *
 */
public class SubspaceExploreUtil {

	/**
	 * A basic breadth-first search implementation of state space search.
	 * 
	 * @param s0 initial state
	 * @param process processing function
	 * @param succ successor function
	 * @param acceptable acceptable predicate
	 */
	public static <S> void bfs(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {
		LinkedList<S> toExplore = new LinkedList<>();
		toExplore.add(s0.get());
		
		while (!toExplore.isEmpty()) {
			S state = toExplore.getFirst();
			toExplore.removeFirst();
			
			if (!acceptable.test(state)) continue;
			
			process.accept(state);
			toExplore.addAll(succ.apply(state));
		}
	}
	
	/**
	 * A basic depth-first search implementation of state space search.
	 * 
	 * @param s0 initial state
	 * @param process processing function
	 * @param succ successor function
	 * @param acceptable acceptable predicate
	 */
	public static <S> void dfs(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {
		LinkedList<S> toExplore = new LinkedList<>();
		toExplore.add(s0.get());
		
		while (!toExplore.isEmpty()) {
			S state = toExplore.getFirst();
			toExplore.removeFirst();
			
			if (!acceptable.test(state)) continue;
			
			process.accept(state);
			toExplore.addAll(0, succ.apply(state));
		}
	}
	
	/**
	 * A basic breadth-first search (with a visited list) implementation of state space search.
	 * 
	 * @param s0 initial state
	 * @param process processing function
	 * @param succ successor function
	 * @param acceptable acceptable predicate
	 */
	public static <S> void bfsv(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {
		LinkedList<S> toExplore = new LinkedList<>();
		toExplore.add(s0.get());
		Set<S> explored = new HashSet<>();
		explored.add(s0.get());
		
		while (!toExplore.isEmpty()) {
			S state = toExplore.getFirst();
			toExplore.removeFirst();
			
			if (!acceptable.test(state)) continue;
			
			process.accept(state);
			
			List<S> nextStates = succ.apply(state);
			for (S next : nextStates) {
				if (explored.add(next)) {
					toExplore.addLast(next);
				}
			}
		}
	}
	
}

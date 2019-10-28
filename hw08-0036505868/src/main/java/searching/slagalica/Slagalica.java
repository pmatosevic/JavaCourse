package searching.slagalica;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import searching.algorithms.Transition;

/**
 * Represent a puzzle game.
 * 
 * @author Patrik
 *
 */
public class Slagalica implements Supplier<KonfiguracijaSlagalice>,
		Function<KonfiguracijaSlagalice, List<Transition<KonfiguracijaSlagalice>>>, 
		Predicate<KonfiguracijaSlagalice> {

	/**
	 * The number of rows on the puzzle
	 */
	private static final int LAYOUT_ROWS = 3;
	
	/**
	 * Array of allowed moves
	 */
	private static final int[] DX = {1, -1, 0, 0};
	
	/**
	 * Array of allowed moves
	 */
	private static final int[] DY = {0, 0, 1, -1};
	
	/**
	 * Final (goal) layout of the puzzle.
	 */
	private static final int[] FINAL_LAYOUT = {1, 2, 3, 4, 5, 6, 7, 8, 0};
	
	/**
	 * Current puzzle configuration
	 */
	private KonfiguracijaSlagalice configuration;

	/**
	 * Creates a new object
	 * @param configuration puzzle configuation
	 */
	public Slagalica(KonfiguracijaSlagalice configuration) {
		this.configuration = configuration;
	}

	@Override
	public boolean test(KonfiguracijaSlagalice t) {
		return Arrays.equals(FINAL_LAYOUT, t.getPolje());
	}

	@Override
	public List<Transition<KonfiguracijaSlagalice>> apply(KonfiguracijaSlagalice t) {
		List<Transition<KonfiguracijaSlagalice>> list = new ArrayList<>();
		int spaceIndex = t.indexOfSpace();
		int row = spaceIndex / LAYOUT_ROWS;
		int col = spaceIndex % LAYOUT_ROWS;
		for (int i = 0; i < DX.length; i++) {
			int newRow = row + DX[i];
			int newCol = col + DY[i];
			if (newRow < 0 || newRow >= LAYOUT_ROWS || newCol < 0 || newCol >= LAYOUT_ROWS) {
				continue;
			}
			KonfiguracijaSlagalice conf = t.swap(newRow * LAYOUT_ROWS + newCol, row * LAYOUT_ROWS + col);
			list.add(new Transition<>(conf, 1));
		}
		return list;
	}

	@Override
	public KonfiguracijaSlagalice get() {
		return configuration;
	}

}

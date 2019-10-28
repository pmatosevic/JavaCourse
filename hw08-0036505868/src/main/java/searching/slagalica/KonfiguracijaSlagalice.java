package searching.slagalica;

import java.util.Arrays;

/**
 * Represents a puzzle configuration
 * 
 * @author Patrik
 *
 */
public class KonfiguracijaSlagalice {

	/**
	 * The number of rows on the puzzle
	 */
	private static final int LAYOUT_ROWS = 3;
	
	/**
	 * Array of numbers
	 */
	private int[] polje;

	/**
	 * Creates a new object
	 * 
	 * @param polje array of numbers
	 */
	public KonfiguracijaSlagalice(int[] polje) {
		this.polje = polje;
	}

	/**
	 * @return the array of numbers as a layout
	 */
	public int[] getPolje() {
		return Arrays.copyOf(polje, polje.length);
	}
	
	/**
	 * Returns the index of the space (element 0).
	 * @return the index of the space (element 0)
	 */
	public int indexOfSpace() {
		for (int i = 0; i < polje.length; i++) {
			if (polje[i] == 0) {
				return i;
			}
		}
		throw new IllegalStateException("No space found.");
	}
	
	/**
	 * Returns a new configuration with swapped indexes.
	 * @param ind1 index 1
	 * @param ind2 index 2
	 * @return a new configuration
	 */
	public KonfiguracijaSlagalice swap(int ind1, int ind2) {
		int[] arrCopy = Arrays.copyOf(polje, polje.length);
		int tmp = arrCopy[ind1];
		arrCopy[ind1] = arrCopy[ind2];
		arrCopy[ind2] = tmp;
		return new KonfiguracijaSlagalice(arrCopy);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < LAYOUT_ROWS; i++) {
			for (int j = 0; j < LAYOUT_ROWS; j++) {
				sb.append(polje[LAYOUT_ROWS*i+j] == 0 ? "*" : polje[LAYOUT_ROWS*i+j]);
				if (j != LAYOUT_ROWS - 1) sb.append(" ");
			}
			if (i != LAYOUT_ROWS - 1) sb.append("\n");
		}
		return sb.toString();
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(polje);
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof KonfiguracijaSlagalice))
			return false;
		KonfiguracijaSlagalice other = (KonfiguracijaSlagalice) obj;
		return Arrays.equals(polje, other.polje);
	}
	
	
	
}

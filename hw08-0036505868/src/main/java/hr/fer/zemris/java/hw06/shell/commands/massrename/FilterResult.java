package hr.fer.zemris.java.hw06.shell.commands.massrename;


/**
 * Represents a single resulting file entry when filtering.
 * 
 * @author Patrik
 *
 */
public class FilterResult {

	/**
	 * All groups
	 */
	private String[] groups;
	
	/**
	 * Creates a new object.
	 * 
	 * @param groups all groups
	 */
	public FilterResult(String[] groups) {
		this.groups = groups;
	}
	
	/**
	 * Returns the filename
	 * @return the filename
	 */
	public String toString() {
		return groups[0];
	}
	
	/**
	 * Returns the number of groups
	 * @return the number of groups
	 */
	public int numberOfGroups() {
		return groups.length;
	}
	
	/**
	 * Returns the group at specified index
	 * @param index index
	 * @return the group at specified index
	 * @throws IllegalArgumentException in case of an invalid index
	 */
	public String group(int index) {
		if (index >= groups.length) {
			throw new IllegalArgumentException("Index larger than the number of groups for: " + groups[0]);
		}
		return groups[index];
	}
	
	
	
}

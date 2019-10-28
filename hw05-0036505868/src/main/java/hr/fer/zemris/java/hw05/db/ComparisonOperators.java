package hr.fer.zemris.java.hw05.db;

/**
 * A class with static comparion operators.
 * 
 * @author Patrik
 *
 */
public class ComparisonOperators {

	/**
	 * Comparison operator for the less comparison.
	 */
	public static final IComparisonOperator LESS = (s1, s2) -> s1.compareTo(s2) < 0; 
	
	/**
	 * Comparison operator for the less-or-equals comparison.
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = (s1, s2) -> s1.compareTo(s2) <= 0; 
	
	/**
	 * Comparison operator for the greater comparison.
	 */
	public static final IComparisonOperator GREATER = (s1, s2) -> s1.compareTo(s2) > 0; 
	
	/**
	 * Comparison operator for the greater-or-equals comparison.
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = (s1, s2) -> s1.compareTo(s2) >= 0; 
	
	/**
	 * Comparison operator for the equal comparison.
	 */
	public static final IComparisonOperator EQUALS = (s1, s2) -> s1.compareTo(s2) == 0; 
	
	/**
	 * Comparison operator for the not-equal comparison.
	 */
	public static final IComparisonOperator NOT_EQUALS = (s1, s2) -> s1.compareTo(s2) != 0; 
	
	/**
	 * Comparison operator for the like comparison.
	 */
	public static final IComparisonOperator LIKE = new LikeComparisonOperator();
	
	
	
	/**
	 * Static inner class for like comparison.
	 * 
	 * @author Patrik
	 *
	 */
	private static class LikeComparisonOperator implements IComparisonOperator {

		/**
		 * Star in the pattern
		 */
		private static char STAR = '*';
		
		/**
		 * {@inheritDoc}
		 * @throws IllegalArgumentException if the pattern contains multiple stars
		 */
		@Override
		public boolean satisfied(String str, String pattern) {
			int pos = pattern.indexOf(STAR);
			if (pos == -1) {
				return str.equals(pattern);
			}
			
			if (pos != pattern.lastIndexOf(STAR)) {
				throw new IllegalArgumentException("Multiple stars are not supported.");
			}
			
			if (str.length() < pattern.length() - 1) {
				return false;
			}
			
			String prefix = pattern.substring(0, pos);
			String suffix = pattern.substring(pos + 1);
			
			return str.startsWith(prefix) && str.endsWith(suffix);
		}
		
	}
	
}

package hr.fer.zemris.java.hw06.shell.commands.massrename;

/**
 * Represents an builder that puts some text to a given {@link StringBuilder} 
 * and using given {@link FilterResult}.
 * 
 * @author Patrik
 *
 */
@FunctionalInterface
public interface NameBuilder {

	/**
	 * Appends the text to the given {@link StringBuilder}.
	 * 
	 * @param result filter entry
	 * @param sb string builder
	 */
	void execute(FilterResult result, StringBuilder sb);
	
	/**
	 * Returns a builder that first builds with itself, and then calls the second builder.
	 * 
	 * @param second the second builder
	 * @return the new builder
	 */
	default NameBuilder thenBuild(NameBuilder second) {
		return (result, sb) -> {
			execute(result, sb);
			second.execute(result, sb);
		};
	}
	
	/**
	 * Returns a builder that does nothing.
	 * @return a builder that does nothing.
	 */
	static NameBuilder emptyBuilder() {
		return (result, sb) -> { };
	}
	
}

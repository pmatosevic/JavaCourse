package hr.fer.zemris.java.hw06.shell.commands.massrename;

/**
 * A parser for the expression in the "massrename" command.
 * 
 * @author Patrik
 *
 */
public class NameBuilderParser {

	/**
	 * Text to parse
	 */
	private char[] data;
	
	/**
	 * Current position
	 */
	private int pos = 0;
	
	/**
	 * Builder to return at the end
	 */
	private NameBuilder builder = NameBuilder.emptyBuilder();

	/**
	 * Creates a new object
	 * 
	 * @param expression string to parse
	 */
	public NameBuilderParser(String expression) {
		data = expression.toCharArray();
		parseAll();
	}
	
	/**
	 * Parses the whole string
	 */
	private void parseAll() {
		while (pos < data.length) {
			NameBuilder nb;
			if (data[pos] == '$') {
				nb = parseGroup();
			} else {
				nb = parsePlain();
			}
			builder = builder.thenBuild(nb);
		}
	}

	/**
	 * Parses plain text
	 * @return a builder for the parsed text
	 */
	private NameBuilder parsePlain() {
		StringBuilder sb = new StringBuilder();
		while (pos < data.length && data[pos] != '$') {
			sb.append(data[pos]);
			pos++;
		}
		return text(sb.toString());
	}

	/**
	 * Parses the next group
	 * @return a builder for the parsed group
	 * @throws IllegalArgumentException in case of an error
	 */
	private NameBuilder parseGroup() {
		pos++;
		if (data[pos] != '{') {												// parse begin tag
			throw new IllegalArgumentException("Expected { after $.");
		}
		pos++;
		skipBlanks();
		int index = parseNumber();
		skipBlanks();
		
		if (data[pos] == '}') {
			pos++;
			return group(index);
		} 
		if (data[pos] != ',') {
			throw new IllegalArgumentException("Expected ',' or '}'.");
		}
		
		pos++;													// parses extended tag
		skipBlanks();
		char padding;
		if (data[pos] == '0') {
			padding = '0';
			//pos++;
		} else {
			padding = ' ';
		}
		int minWidth = parseNumber();
		
		skipBlanks();
		if (data[pos] != '}') {
			throw new IllegalArgumentException("Expected '}'.");
		}
		pos++;
		
		return group(index, padding, minWidth);
	}
	
	/**
	 * Parses a number
	 * @return parsed number
	 * @throws IllegalArgumentException in case of an error
	 */
	private int parseNumber() {
		int start = pos;
		while (pos < data.length && Character.isDigit(data[pos])) {
			pos++;
		}
		if (start == pos) {
			throw new IllegalArgumentException("Expected non-negative number.");
		}
		
		int num = Integer.parseInt(new String(data, start, pos - start));
		return num;
	}

	/**
	 * Skips blanks.
	 */
	private void skipBlanks() {
		while (pos < data.length && Character.isWhitespace(data[pos])) {
			pos++;
		}
	}

	/**
	 * Returns the builder for this expression
	 * @return the builder for this expression
	 */
	public NameBuilder getNameBuilder() {
		return builder;
	}
	
	/**
	 * Returns a builder that appends the text.
	 * @param t text
	 * @return a builder
	 */
	private static NameBuilder text(String t) { 
		return (result, sb) -> sb.append(t); 
	}
	
	/**
	 * Returns a builder that appends the group
	 * @param index group index
	 * @return a builder
	 */
	private static NameBuilder group(int index) { 
		return (result, sb) -> sb.append(result.group(index));
	}
	
	/**
	 * Returns a builder that appends the group
	 * @param index group index
	 * @param padding padding character
	 * @param minWidth minimal length
	 * @return a builder
	 */
	private static NameBuilder group(int index, char padding, int minWidth) {
		return (result, sb) -> sb.append(
				String.format("%" + (minWidth==0 ? "" : minWidth) + "s", result.group(index)).replace(' ', padding));
	}
	
}

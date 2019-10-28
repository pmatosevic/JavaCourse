package hr.fer.zemris.java.hw06.shell;

import java.util.ArrayList;
import java.util.List;

/**
 * A parser for the arguments of some commands.
 * @author Patrik
 *
 */
public class ArgumentParser {

	/**
	 * Text chars
	 */
	private char[] data;
	
	/**
	 * Current position
	 */
	private int pos = 0;
	
	/**
	 * List of parts of the argument.
	 */
	private List<String> result;

	/**
	 * Crates a new {@code ArgumentParser}
	 * @param arguments text
	 */
	private ArgumentParser(String arguments) {
		this.data = arguments.toCharArray();
		parse();
	}
	
	/**
	 * Parses the given arguments string and returns the split arguments.
	 * 
	 * @param arguments arguments
	 * @return split arguments
	 * @throws IllegalArgumentException in case of an invalid text
	 */
	public static String[] parseArguments(String arguments) {
		ArgumentParser parser = new ArgumentParser(arguments);
		return parser.result.toArray(new String[] {});
	}
	
	/**
	 * Parses the whole text.
	 * @throws IllegalArgumentException in case of an invalid text
	 */
	private void parse() {
		result = new ArrayList<>();
		while (true) {
			skipBlanks();
			if (pos == data.length) break;
			
			if (data[pos] == '\"') {
				pos++;
				result.add(parseEnclosed());
				if (pos != data.length && !Character.isWhitespace(data[pos])) {
					throw new IllegalArgumentException("Expected at least 1 space after quoted part.");
				}
			} else {
				result.add(parsePlain());
			}
		}
	}
	
	/**
	 * Parses and returns the text enclosed in quotes.
	 * @return parsed text
	 * @throws IllegalArgumentException in case of an invalid text
	 */
	private String parseEnclosed() {
		StringBuilder sb = new StringBuilder();
		while (pos < data.length) {
			if (data[pos] == '\"') {
				pos++;
				return sb.toString();
			} else if (data[pos] == '\\') {
				if (pos == data.length - 1) {
					throw new IllegalArgumentException("Path was not properly enclosed.");
				}
				
				char next = data[pos+1];
				if (next == '\\' || next == '\"') {
					sb.append(next);
					pos++;
				} else {
					sb.append(data[pos]);
				}
			} else {
				sb.append(data[pos]);
			}
			pos++;
		}
		throw new IllegalArgumentException("Path was not properly enclosed.");
	}
	
	/**
	 * Parses and returns plain text.
	 * 
	 * @return parsed text
	 */
	private String parsePlain() {
		StringBuilder sb = new StringBuilder();
		while (pos < data.length && !Character.isWhitespace(data[pos])) {
			sb.append(data[pos]);
			pos++;
		}
		return sb.toString();
	}
	
	/**
	 * Skips blanks.
	 */
	private void skipBlanks() {
		while (pos < data.length && Character.isWhitespace(data[pos])) {
			pos++;
		}
	}
	
	
	
}

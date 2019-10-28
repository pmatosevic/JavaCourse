package hr.fer.zemris.java.hw06.shell;

import java.nio.file.Path;
import java.util.SortedMap;

/**
 * Represents a shell environment with methods for reading and writing to the user.
 * 
 * @author Patrik
 *
 */
public interface Environment {

	/**
	 * Reads and returns the string from user input.
	 * @return user input
	 * @throws ShellIOException in case of an error
	 */
	String readLine() throws ShellIOException;
	
	/**
	 * Prints the text to the user.
	 * @param text text to print
	 * @throws ShellIOException in case of an error
	 */
	void write(String text) throws ShellIOException;
	
	/**
	 * Prints the text to the user and moves the output to a new line.
	 * @param text text to print
	 * @throws ShellIOException in case of an error
	 */
	void writeln(String text) throws ShellIOException;
	
	/**
	 * Returns the map of all available commands.
	 * @return the map of all available commands
	 */
	SortedMap<String, ShellCommand> commands();
	
	/**
	 * Returns the multiline symbol.
	 * @return the multiline symbol
	 */
	Character getMultilineSymbol();
	
	/**
	 * Sets the multiline symbol.
	 * @param symbol new symbol
	 */
	void setMultilineSymbol(Character symbol);
	
	/**
	 * Returns the prompt symbol.
	 * @return the prompt symbol
	 */
	Character getPromptSymbol();
	
	/**
	 * Sets the prompt symbol.
	 * @param symbol new symbol
	 */
	void setPromptSymbol(Character symbol);
	
	/**
	 * Returns the morelines symbol.
	 * @return the morelines symbol
	 */
	Character getMorelinesSymbol();
	
	/**
	 * Sets the morelines symbol.
	 * @param symbol new symbol
	 */
	void setMorelinesSymbol(Character symbol);
	
	/**
	 * Returns the current directory
	 * @return the current directory
	 */
	Path getCurrentDirectory();
	
	/**
	 * Sets the current directory
	 * @param path the current directory
	 */
	void setCurrentDirectory(Path path);
	
	/**
	 * Returns data for the key or {@code null} if no data is stored.
	 * @param key key
	 * @return data
	 */
	Object getSharedData(String key);
	
	/**
	 * Puts the data for the key
	 * @param key key
	 * @param value data to store
	 */
	void setSharedData(String key, Object value);
	
}

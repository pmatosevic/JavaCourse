package hr.fer.zemris.java.hw06.shell;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;


/**
 * Represents a default implementation of a shell environment.
 * 
 * @author Patrik
 *
 */
public class MyEnvironment implements Environment {

	/**
	 * Prompt symbol
	 */
	private Character promptSymbol = '>';
	
	/**
	 * Multiline symbol
	 */
	private Character multilineSymbol = '|';
	
	/**
	 * Morelines symbol
	 */
	private Character morelinesSymbol = '\\';
	
	/**
	 * Scanner object
	 */
	private Scanner sc;
	
	/**
	 * A map of all commands
	 */
	private SortedMap<String, ShellCommand> commands = new TreeMap<>();
	
	
	/**
	 * Creates a new {@code MyEnvironment} 
	 * @param sc scanner used to read user input
	 * @param commands map of all commands
	 */
	public MyEnvironment(Scanner sc, SortedMap<String, ShellCommand> commands) {
		this.sc = sc;
		this.commands = Collections.unmodifiableSortedMap(commands);
	}
	
	
	@Override
	public String readLine() throws ShellIOException {
		if (!sc.hasNextLine()) {
			throw new ShellIOException("No input available");
		}
		String line = sc.nextLine();
		return line;
	}

	@Override
	public void write(String text) throws ShellIOException {
		System.out.print(text);
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		System.out.println(text);
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return commands;
	}

	@Override
	public Character getMultilineSymbol() {
		return multilineSymbol;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		multilineSymbol = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return promptSymbol;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		promptSymbol = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return morelinesSymbol;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		morelinesSymbol = symbol;
	}
	
}

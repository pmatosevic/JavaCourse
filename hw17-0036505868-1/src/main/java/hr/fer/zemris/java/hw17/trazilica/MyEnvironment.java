package hr.fer.zemris.java.hw17.trazilica;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw17.trazilica.model.DocumentCollection;
import hr.fer.zemris.java.hw17.trazilica.model.SimilarityResult;


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
	 * The current working directory
	 */
	private Path currentDirectory;
	
	/**
	 * A map where shared data is stored
	 */
	private Map<String, Object> sharedData = new HashMap<>();

	/**
	 * The collection of documents
	 */
	private DocumentCollection docCollection;

	/**
	 * Results from the last query
	 */
	private List<SimilarityResult> results;
	
	
	/**
	 * Creates a new {@code MyEnvironment} 
	 * @param sc scanner used to read user input
	 * @param commands map of all commands
	 */
	public MyEnvironment(Scanner sc, SortedMap<String, ShellCommand> commands) {
		this.sc = sc;
		this.commands = Collections.unmodifiableSortedMap(commands);
		this.currentDirectory = Paths.get(".").toAbsolutePath().normalize();
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


	@Override
	public Path getCurrentDirectory() {
		return currentDirectory;
	}


	@Override
	public void setCurrentDirectory(Path path) {
		if (!Files.isDirectory(path)) {
			throw new IllegalArgumentException("Directory does not exist.");
		}
		currentDirectory = path.toAbsolutePath().normalize();
	}


	@Override
	public Object getSharedData(String key) {
		return sharedData.get(key);
	}


	@Override
	public void setSharedData(String key, Object value) {
		sharedData.put(key, value);
	}


	@Override
	public DocumentCollection getDocumentCollection() {
		return docCollection;
	}


	@Override
	public void setDocumentCollection(DocumentCollection docCollection) {
		this.docCollection = docCollection;
	}


	@Override
	public List<SimilarityResult> getResults() {
		return results;
	}


	@Override
	public void setResults(List<SimilarityResult> results) {
		this.results = results;
	}
	
}

package hr.fer.zemris.java.hw17.trazilica;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw17.trazilica.commands.ExitShellCommand;
import hr.fer.zemris.java.hw17.trazilica.commands.QueryCommand;
import hr.fer.zemris.java.hw17.trazilica.commands.ResultsCommand;
import hr.fer.zemris.java.hw17.trazilica.commands.TypeCommand;
import hr.fer.zemris.java.hw17.trazilica.model.DocumentCollection;
import hr.fer.zemris.java.hw17.trazilica.model.DocumentCollectionBuilder;

/**
 * A program that simulates a shell.
 * The shell exists on entering "exit".
 * 
 * @author Patrik
 *
 */
public class Konzola {
	
	/**
	 * The path to the stop words file
	 */
	private static final String STOPWORDS_FILEPATH = "hrvatski_stoprijeci.txt";
	
	/**
	 * A map of all commands
	 */
	private static SortedMap<String, ShellCommand> commands = new TreeMap<>();
	
	static {
		commands.put("exit", new ExitShellCommand());
		commands.put("query", new QueryCommand());
		commands.put("type", new TypeCommand());
		commands.put("results", new ResultsCommand());
	}
	
	
	/**
	 * Program entry point
	 * 
	 * @param args command-line arguments (not used)
	 * @throws IOException 
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Environment evt = new MyEnvironment(sc, commands);
		ShellStatus status = ShellStatus.CONTINUE;
		
		if (args.length != 1) {
			System.out.println("Expected path to the documents dir.");
			return;
		}
		Path docDir = Paths.get(args[0]);
		if (!Files.isDirectory(docDir)) {
			System.out.println("The documents directory does not exist.");
			return;
		}
		
		DocumentCollection docCollection;
		try {
			docCollection = DocumentCollectionBuilder.loadIntoDocumentCollection(
					Paths.get(STOPWORDS_FILEPATH), docDir);
		} catch (IOException e) {
			System.out.println("Error during loading files.");
			System.out.println("Check that the stopwords file and the documents are readable text files.");
			return;
		}
		evt.writeln("The size of the vocabulary is " + docCollection.getVocabulary().size() + " words.");
		evt.writeln("");
		evt.setDocumentCollection(docCollection);
		
		while (status != ShellStatus.TERMINATE) {
			try {
				String input = readLineOrLines(evt).trim();
				String[] parts = input.split("\\s+", 2);
				String commandName = parts[0];
				String arguments = parts.length == 1 ? "" : parts[1];
				
				ShellCommand command = commands.get(commandName);
				
				if (command != null) {
					status = command.executeCommand(evt, arguments);
				} else {
					evt.writeln("Invalid command.");
				}
				evt.writeln("");
			} catch (ShellIOException ex) {
				break;
			}
		} 
	}
	
	/**
	 * Reads the next command from user and handles multiple lines.
	 * 
	 * @param env shell environment
	 * @return the whole user input
	 */
	private static String readLineOrLines(Environment env) {
		StringBuilder sb = new StringBuilder();
		env.write("Enter command > ");
		while (true) {
			String input = env.readLine();
			if (input.length() > 0 && input.charAt(input.length() - 1) == env.getMorelinesSymbol()) {
				sb.append(input.substring(0, input.length() - 1));
				env.write(env.getMultilineSymbol() + " ");
			} else {
				sb.append(input);
				break;
			}
		}
		return sb.toString();
	}

	
	
}

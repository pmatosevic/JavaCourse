package hr.fer.zemris.java.hw17.trazilica.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import hr.fer.zemris.java.hw17.trazilica.Environment;
import hr.fer.zemris.java.hw17.trazilica.ShellCommand;
import hr.fer.zemris.java.hw17.trazilica.ShellStatus;
import hr.fer.zemris.java.hw17.trazilica.model.Document;
import hr.fer.zemris.java.hw17.trazilica.model.SimilarityResult;

/**
 * A command that displays the document with given index form results.
 * @author Patrik
 *
 */
public class TypeCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		int index;
		try {
			index = Integer.parseInt(arguments.trim());
		} catch (NumberFormatException e) {
			env.writeln("Invalid index.");
			return ShellStatus.CONTINUE;
		}
		List<SimilarityResult> results = env.getResults();
		if (results == null || index >= results.size()) {
			env.writeln("No query has been made or the result index does not exist.");
			return ShellStatus.CONTINUE;
		}
		
		Document doc = results.get(index).getDocument();
		env.writeln("----------------------------------------------------------------");
		env.writeln("Document: " + doc.getFilepath().toString());
		env.writeln("----------------------------------------------------------------");
		try {
			env.writeln(Files.readString(doc.getFilepath()));
		} catch (IOException e) {
			env.writeln("Error during reading file.");
		}
		env.writeln("----------------------------------------------------------------");
		
		return ShellStatus.CONTINUE;
	}

}

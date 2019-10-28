package hr.fer.zemris.java.hw17.trazilica.commands;

import java.util.List;

import hr.fer.zemris.java.hw17.trazilica.Environment;
import hr.fer.zemris.java.hw17.trazilica.ShellCommand;
import hr.fer.zemris.java.hw17.trazilica.ShellStatus;
import hr.fer.zemris.java.hw17.trazilica.model.SimilarityResult;

/**
 * A command that shows results from the last query.
 * @author Patrik
 *
 */
public class ResultsCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<SimilarityResult> results = env.getResults();
		if (results != null) {
			for (int i=0; i<results.size(); i++) {
				env.writeln(String.format("[%2d] (%.4f) %s", i, results.get(i).getScore(), 
						results.get(i).getDocument().getFilepath()
				));
			}
		} else {
			env.writeln("No query has been made.");
		}
		return ShellStatus.CONTINUE;
	}

}

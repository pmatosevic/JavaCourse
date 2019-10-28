package hr.fer.zemris.java.hw17.trazilica.commands;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import hr.fer.zemris.java.hw17.trazilica.Environment;
import hr.fer.zemris.java.hw17.trazilica.ShellCommand;
import hr.fer.zemris.java.hw17.trazilica.ShellStatus;
import hr.fer.zemris.java.hw17.trazilica.model.Document;
import hr.fer.zemris.java.hw17.trazilica.model.DocumentCollection;
import hr.fer.zemris.java.hw17.trazilica.model.SimilarityResult;

/**
 * A command that queries documents similar to the given words.
 * 
 * @author Patrik
 *
 */
public class QueryCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> words = new ArrayList<>(Arrays.asList(arguments.split("\\s+")));
		DocumentCollection docCol = env.getDocumentCollection();
		words.removeIf(word -> !docCol.getVocabulary().contains(word));
		Document doc = docCol.createDocument(words);
		
		env.write("Query is: [");
		Iterator<String> it = words.iterator();
		while (it.hasNext()) {
			env.write(it.next());
			if (it.hasNext()) env.write(", ");
		}
		env.writeln("]");
		
		List<SimilarityResult> results = env.getDocumentCollection().measureSimilarity(doc);
		env.setResults(results);
		env.writeln("The top 10 best results:");
		for (int i=0; i<results.size(); i++) {
			env.writeln(String.format("[%2d] (%.4f) %s", i, results.get(i).getScore(), 
					results.get(i).getDocument().getFilepath()
			));
		}
		
		return ShellStatus.CONTINUE;
	}

}

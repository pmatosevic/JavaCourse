package hr.fer.zemris.java.hw03;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Demonstration program for testing parsing of documents.
 * Tries to parse an example and prints if an exception is thrown.
 * The first command-line argument is the path to the document to parse.
 * 
 * @author Patrik
 *
 */
public class SmartScriptTester {

	/**
	 * Program entry point
	 * @param args command-line arguments
	 */
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.out.println("Path not specified.");
			return;
		}
		
		String docBody;
		try {
			docBody = new String(
					Files.readAllBytes(Paths.get(args[0])),
					StandardCharsets.UTF_8);
		} catch (IOException ex) {
			System.out.println("Error while reading file.");
			return;
		}
		
		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch (Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody);
	}
	
	
	/**
	 * Creates text representation of a {@link DocumentNode} and can be parsed again.
	 * 
	 * @param document {@link DocumentNode} to convert to plain text
	 * @return text representation of the given document
	 */
	private static String createOriginalDocumentBody(DocumentNode document) {
		return document.getAsPlainText();
	}
	
}

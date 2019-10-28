package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Program that supports querying the database of students.
 * Supports only query command in the following format:
 * "query attributeName operator "value" [AND attributeName operator "value" ...]"
 * Program exits when "exit" command is entered.
 * 
 * @author Patrik
 *
 */
public class StudentDB {

	/**
	 * Prompt symbol
	 */
	private static final String PROMPT_SYMBOL = "> ";
	
	/**
	 * Exit command
	 */
	private static final String EXIT_COMMAND = "exit";
	
	/**
	 * Query command
	 */
	private static final String QUERY_COMMAND = "query";
	
	
	/**
	 * Program entry point
	 * @param args command-line arguments (not used)
	 */
	public static void main(String[] args) {
		StudentDatabase database;
		try {
			database = new StudentDatabase(
					Files.readAllLines(Paths.get("database.txt"), StandardCharsets.UTF_8));
		} catch (IOException e) {
			System.out.println("Database could not be read.");
			return;
		} catch (IllegalArgumentException ex) {
			System.out.println("Database is not in valid format: " + ex.getMessage());
			return;
		}

		System.out.println("Database loaded successfully.");
		
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			System.out.print(PROMPT_SYMBOL);
			String command = sc.nextLine().trim();
			String[] parts = command.split("\\s+", 2);
			
			switch(parts[0]) {
			case EXIT_COMMAND:
				if (parts.length != 1) {
					System.out.println("Invalid parameters");
					break;
				}
				System.out.println("Goodbye!");
				sc.close();
				return;
			case QUERY_COMMAND:
				if (parts.length == 1) {
					System.out.println("Invalid parameters");
					break;
				}
				queryCommand(database, parts[1]);
				break;
			default:
				System.out.println("Command is not supported.");
				break;
			}
		}	
	}
	
	/**
	 * Executes the query command
	 * @param database database
	 * @param params command parameters
	 */
	private static void queryCommand(StudentDatabase database, String params) {
		QueryParser parser;
		try {
			parser = new QueryParser(params);
		} catch (ParserException ex) {
			System.out.println("Invalid query: " + ex.getMessage());
			return;
		}
		
		List<StudentRecord> records = selectBasedOnFiltering(database, parser);
		
		if (parser.isDirectQuery()) {
			System.out.println("Using index for record retrieval.");
		}
		
		if (records.size() > 0) {
			List<String> output = RecordFormatter.format(records);
			output.forEach(System.out::println);
		}
		System.out.println("Records selected: " + records.size());
	}
	
	/**
	 * Returns the list of queried records
	 * @param database database
	 * @param parser parser
	 * @return the list of queried records
	 */
	private static List<StudentRecord> selectBasedOnFiltering(StudentDatabase database, QueryParser parser) {
		if (parser.isDirectQuery()) {
			List<StudentRecord> records = new ArrayList<>();
			StudentRecord record = database.forJMBAG(parser.getQueriedJMBAG());
			if (record != null) {
				records.add(record);
			}
			return records;
		} else {
			return database.filter(new QueryFilter(parser.getQuery()));
		}
	}
	
	
	
}

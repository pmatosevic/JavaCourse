package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Represents a database of students records. Records are kept in memory.
 * @author Patrik
 *
 */
public class StudentDatabase {

	/**
	 * List of records;
	 */
	private List<StudentRecord> records = new ArrayList<>();
	
	/**
	 * Map of records indexed by JMBAG
	 */
	private Map<String, StudentRecord> indexedRecords = new HashMap<>();
	
	
	/**
	 * Creates a new {@code StudentDatabase} from given lines from file
	 * @param lines lines from file
	 */
	public StudentDatabase(List<String> lines) {
		for (String line : lines) {
			String[] parts = line.split("\\t");
			if (parts.length != 4) {
				throw new IllegalArgumentException("Invalid line: " + line + " " + parts.length);
			}
			
			String jmbag = parts[0];
			if (indexedRecords.containsKey(jmbag)) {
				throw new IllegalArgumentException("Database contains duplicate records.");
			}
			
			int grade = Integer.parseInt(parts[3]);
			
			StudentRecord record = new StudentRecord(jmbag, parts[1], parts[2], grade);
			records.add(record);
			indexedRecords.put(parts[0], record);
		}
	}
	
	/**
	 * Returns the record with given JMBAG or {@code null} if not exists
	 * @param jmbag JMBAG
	 * @return the record with given JMBAG or {@code null} if not exists
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return indexedRecords.get(jmbag);
	}
	
	/**
	 * Returns the list of all records satisfying the filter.
	 * @param filter filter
	 * @return the list of all records satisfying the filter
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> result = new ArrayList<>();
		for (StudentRecord record : records) {
			if (filter.accepts(record)) {
				result.add(record);
			}
		}
		return result;
	}
	
}

package hr.fer.zemris.java.hw05.db;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;


/**
 * Class with static methods for formatting the output for displaying it to the user.
 * 
 * @author Patrik
 *
 */
public class RecordFormatter {

	
	/**
	 * Converts the given student records to a list of lines that can be output to the user.
	 * @param records student records
	 * @return the list of lines to output
	 */
	public static List<String> format(List<StudentRecord> records) {
		List<String> lines = new ArrayList<>();
		int maxlenFirst = 0, maxlenLast = 0, maxlenJmbag = 0;
		for (StudentRecord record : records) {
			maxlenFirst = Math.max(maxlenFirst, record.getFirstName().length());
			maxlenLast = Math.max(maxlenLast, record.getLastName().length());
			maxlenJmbag = Math.max(maxlenJmbag, record.getJmbag().length());
		}
		
		lines.add(getBorder(maxlenFirst, maxlenLast, maxlenJmbag));
		for (StudentRecord record : records) {
			lines.add(getRecordLine(record, maxlenFirst, maxlenLast, maxlenJmbag));
		}
		lines.add(getBorder(maxlenFirst, maxlenLast, maxlenJmbag));
		return lines;
	}
	
	/**
	 * Formats the given record to a string
	 * @param record student record
	 * @param maxlenFirst maximal length of jmbag
	 * @param maxlenLast maximal length of last name
	 * @param maxlenJmbag maximal length of first name
	 * @return formatted line
	 */
	private static String getRecordLine(StudentRecord record, int maxlenFirst, int maxlenLast, int maxlenJmbag) {
		StringBuilder sb = new StringBuilder();
		sb.append("| ");
		sb.append(strWithSpaces(record.getJmbag(), maxlenJmbag));
		sb.append(" | ");
		sb.append(strWithSpaces(record.getLastName(), maxlenLast));
		sb.append(" | ");
		sb.append(strWithSpaces(record.getFirstName(), maxlenFirst));
		sb.append(" | ");
		sb.append(record.getFinalGrade());
		sb.append(" |");
		return sb.toString();
	}
	
	
	/**
	 * Returns the first line of the table
	 * @param maxlenFirst maximal length of jmbag
	 * @param maxlenLast maximal length of last name
	 * @param maxlenJmbag maximal length of first name
	 * @return the first line of the table
	 */
	private static String getBorder(int maxlenFirst, int maxlenLast, int maxlenJmbag) {
		StringBuilder sb = new StringBuilder();
		sb.append("+");
		sb.append(chars(maxlenJmbag + 2, '='));
		sb.append("+");
		sb.append(chars(maxlenLast + 2, '='));
		sb.append("+");
		sb.append(chars(maxlenFirst + 2, '='));
		sb.append("+");
		sb.append(chars(3, '='));
		sb.append("+");
		return sb.toString();
	}
	
	
	/**
	 * Returns the string of given string with spaces at the end to ahieve desired length.
	 * @param str given string
	 * @param len desired length
	 * @return string with spaces
	 */
	private static String strWithSpaces(String str, int len) {
		return str + chars(len - str.length(), ' ');
	}
	
	
	/**
	 * Returns the string of len same ch characters.
	 * @param len length
	 * @param ch character
	 * @return the string of len same ch characters
	 */
	private static String chars(int len, char ch) {
		return CharBuffer.allocate(len).toString().replace('\0', ch);
	}
	
}

package hr.fer.zemris.java.hw07.demo4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Program that demonstrates the usage of {@link Stream} for filtering data.
 * 
 * @author Patrik
 *
 */
public class StudentDemo {

	/**
	 * Program entry point
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		List<StudentRecord> records;
		try {
			records = convert(Files.readAllLines(Paths.get("studenti.txt")));
		} catch (IOException ex) {
			System.out.println("File does not exist.");
			return;
		} catch (IllegalArgumentException ex) {
			System.out.println("Error during reading file: " + ex.getMessage());
			return;
		}
		
		System.out.println("Zadatak 1");
		System.out.println("=========");
		long broj = vratiBodovaViseOd25(records);
		System.out.println(broj);
		
		System.out.println("Zadatak 1");
		System.out.println("=========");
		long broj5 = vratiBrojOdlikasa(records);
		System.out.println(broj5);
		
		System.out.println("Zadatak 3");
		System.out.println("=========");
		List<StudentRecord> odlikasi = vratiListuOdlikasa(records);
		odlikasi.forEach(System.out::println);
		
		System.out.println("Zadatak 4");
		System.out.println("=========");
		List<StudentRecord> odlikasiSortirano = vratiSortiranuListuOdlikasa(records);
		odlikasiSortirano.forEach(System.out::println);
		
		System.out.println("Zadatak 5");
		System.out.println("=========");
		List<String> nepolozeniJMBAGovi = vratiPopisNepolozenih(records);
		nepolozeniJMBAGovi.forEach(System.out::println);
		
		System.out.println("Zadatak 6");
		System.out.println("=========");
		Map<Integer, List<StudentRecord>> mapaPoOcjenama = razvrstajStudentePoOcjenama(records);
		printMap(mapaPoOcjenama);
		
		System.out.println("Zadatak 7");
		System.out.println("=========");
		Map<Integer, Integer> mapaPoOcjenama2 = vratiBrojStudenataPoOcjenama(records);
		mapaPoOcjenama2.forEach((k, v) -> System.out.println(k + " -> " + v));
		
		System.out.println("Zadatak 8");
		System.out.println("=========");
		Map<Boolean, List<StudentRecord>> prolazNeprolaz = razvrstajProlazPad(records);
		printMap(prolazNeprolaz);
		
	}
	
	/**
	 * Prints the map
	 * @param map map to print
	 */
	private static void printMap(Map<?, List<StudentRecord>> map) {
		map.forEach((k, v) -> {
			System.out.println(k + ": ");
			v.forEach(r -> System.out.println("  " + r.toString()));
		});
	}
	
	/**
	 * Returns the number of student with sum of all points greater than 25.
	 * 
	 * @param records list of student records
	 * @return the number of student with sum of all points greater than 25
	 */
	private static long vratiBodovaViseOd25(List<StudentRecord> records) {
		long broj = records.stream()
				.filter(s -> s.sumPoints() > 25)
				.count();
		return broj;
	}
	
	/**
	 * Returns the number of students that have received grade 5.
	 * 
	 * @param records list of student records
	 * @return the number of students that have received grade 5
	 */
	private static long vratiBrojOdlikasa(List<StudentRecord> records) {
		long broj5 = records.stream()
				.filter(s -> s.getGrade() == 5)
				.count();
		return broj5;
	}
	
	/**
	 * Returns the list of students which have received grade 5.
	 * 
	 * @param records list of student records
	 * @return the list of students which have received grade 5
	 */
	private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
		List<StudentRecord> odlikasi = records.stream()
				.filter(s -> s.getGrade() == 5)
				.collect(Collectors.toList());
		return odlikasi;
	}
	
	/**
	 * Returns the list of students which have received grade 5 sorted by their points.
	 * 
	 * @param records list of student records
	 * @return the list of students which have received grade 5 sorted by their points
	 */
	private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
		List<StudentRecord> odlikasiSortirano = records.stream()
				.filter(s -> s.getGrade() == 5)
				.sorted((s1, s2) -> Double.compare(s2.sumPoints(), s1.sumPoints()))
				.collect(Collectors.toList());
		return odlikasiSortirano;
	}
	
	/**
	 * Returns the list of students that have not passed the course.
	 * 
	 * @param records list of student records
	 * @return the list of students that have not passed the course
	 */
	private static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
		List<String> nepolozeniJMBAGovi = records.stream()
				.filter(s -> s.getGrade() == 1)
				.sorted((s1, s2) -> s1.getJmbag().compareTo(s2.getJmbag()))
				.map(StudentRecord::getJmbag)
				.collect(Collectors.toList());
		return nepolozeniJMBAGovi;
	}
		
	/**
	 * Returns the student mapped by the grade which they received.
	 * 
	 * @param records list of student records
	 * @return the student mapped by the grade which they received
	 */
	private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
		Map<Integer, List<StudentRecord>> mapaPoOcjenama = records.stream()
				.collect(Collectors.groupingBy(StudentRecord::getGrade));
		return mapaPoOcjenama;
	}
	
	/**
	 * Returns the map of grades and the number of students that received that grade.
	 * 
	 * @param records list of student records
	 * @return the map of grades and the number of students that received that grade
	 */
	private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
		Map<Integer, Integer> mapaPoOcjenama2 = records.stream()
				.collect(Collectors.toMap(StudentRecord::getGrade, s -> 1, Integer::sum));
		return mapaPoOcjenama2;
	}
	
	/**
	 * Returns the students mapped by whether they have passed the course or not.
	 * 
	 * @param records list of student records
	 * @return the students mapped by whether they have passed the course or not
	 */
	private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
		Map<Boolean, List<StudentRecord>> prolazNeprolaz = records.stream()
				.collect(Collectors.partitioningBy(s -> s.getGrade() != 1));
		return prolazNeprolaz;
	}
	
	/**
	 * Converts the lines from a text file into student records.
	 * 
	 * @param lines lines from file
	 * @return student records
	 * @throws IllegalArgumentException in case of an invalid line
	 */
	private static List<StudentRecord> convert(List<String> lines) {
		List<StudentRecord> records = new ArrayList<>();
		for (String line : lines) {
			if (line.trim().isBlank()) continue;
			
			String[] parts = line.split("\\t");
			if (parts.length != 7) {
				throw new IllegalArgumentException("Invalid line: " + line);
			}
			records.add(new StudentRecord(
					parts[0],
					parts[1], 
					parts[2], 
					Double.parseDouble(parts[3]), 
					Double.parseDouble(parts[4]),
					Double.parseDouble(parts[5]),
					Integer.parseInt(parts[6]))
			);
		}
		return records;
	}
	
}

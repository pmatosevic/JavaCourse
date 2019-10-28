package hr.fer.zemris.java.custom.collections.demo;

import java.util.Iterator;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.SimpleHashtable;

/**
 * Demonstration program for SimpleHashtable
 * @author Patrik
 *
 */
public class Demo1 {

	/**
	 * Program entry point
	 * @param args
	 */
	public static void main(String[] args) {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		for (SimpleHashtable.TableEntry<String, Integer> pair : examMarks) {
			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
		}
		
		
//		for (SimpleHashtable.TableEntry<String, Integer> pair1 : examMarks) {
//			for (SimpleHashtable.TableEntry<String, Integer> pair2 : examMarks) {
//				System.out.printf("(%s => %d) - (%s => %d)%n", pair1.getKey(), pair1.getValue(), pair2.getKey(),
//						pair2.getValue());
//			}
//		}
		
//		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
//		while (iter.hasNext()) {
//			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
//			if (pair.getKey().equals("Ivana")) {
//				iter.remove(); // sam iterator kontrolirano uklanja trenutni element
//			}
//		}
		
		
//		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter2 = examMarks.iterator();
//		while (iter2.hasNext()) {
//			SimpleHashtable.TableEntry<String, Integer> pair = iter2.next();
//			if (pair.getKey().equals("Ivana")) {
//				iter2.remove();
//				iter2.remove();
//			}
//		}
		
//		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter3 = examMarks.iterator();
//		while (iter3.hasNext()) {
//			SimpleHashtable.TableEntry<String, Integer> pair = iter3.next();
//			if (pair.getKey().equals("Ivana")) {
//				examMarks.remove("Ivana");
//			}
//		}
		
//		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter4 = examMarks.iterator();
//		while (iter4.hasNext()) {
//			SimpleHashtable.TableEntry<String, Integer> pair = iter4.next();
//			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
//			iter4.remove();
//		}
//		System.out.printf("Veličina: %d%n", examMarks.size());
//		System.out.println(examMarks);	
		
	}
	
}

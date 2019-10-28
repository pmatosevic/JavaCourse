package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.collections.SimpleHashtable.TableEntry;

public class SimpleHashtableTest {

	private SimpleHashtable<String, Integer> examMarks;
	
	@Test
	void testConstructor() {
		SimpleHashtable<String, Integer> ht = new SimpleHashtable<>(7);
		assertTrue(ht.isEmpty());
		assertThrows(IllegalArgumentException.class, () -> new SimpleHashtable<>(0));
		assertThrows(IllegalArgumentException.class, () -> new SimpleHashtable<>(-1));
	}
	
	@Test
	void testPutNull() {
		examMarks.put("aaa", null);
		assertThrows(NullPointerException.class, () -> examMarks.put(null, 5));
	}
	
	@Test
	void testGet() {
		assertEquals(5, examMarks.get(new String("Ivana")));
		assertEquals(null, examMarks.get(null));
		assertEquals(null, examMarks.get("aaa"));
	}
	
	@Test
	void testSize() {
		assertEquals(4, examMarks.size());
	}
	
	@Test
	void testContainsKey() {
		assertTrue(examMarks.containsKey(new String("Ante")));
		assertFalse(examMarks.containsKey(null));
	}
	
	@Test
	void testContainsValue() {
		assertTrue(examMarks.containsValue(5));
		assertFalse(examMarks.containsValue(100));
		assertFalse(examMarks.containsValue(null));
		examMarks.put("test", null);
		assertTrue(examMarks.containsValue(null));
	}
	
	@Test
	void testRemove() {
		examMarks.remove("Ivana");
		examMarks.remove("Ante");
		examMarks.remove("Kristina");
		assertEquals("[Jasna=2]", examMarks.toString());
		examMarks.remove("Jasna");
	}
	
	
	@Test
	void testIteration() {
		Iterator<TableEntry<String, Integer>> it = examMarks.iterator();
		assertEquals("Ante", it.next().getKey());
		assertEquals("Ivana", it.next().getKey());
		assertEquals("Jasna", it.next().getKey());
		assertEquals("Kristina", it.next().getKey());
		assertThrows(NoSuchElementException.class, () -> it.next());
	}
	
	@Test
	void testIterationModification() {
		Iterator<TableEntry<String, Integer>> it = examMarks.iterator();
		examMarks.put("Ivana", 5);
		it.next();
		examMarks.put("aaa", 5);
		assertThrows(ConcurrentModificationException.class, () -> it.next());
		
		Iterator<TableEntry<String, Integer>> it2 = examMarks.iterator();
		examMarks.remove("Ivana");
		assertThrows(ConcurrentModificationException.class, () -> it.next());
	}
	
	@Test
	void testIterationRemove() {
		Iterator<TableEntry<String, Integer>> it = examMarks.iterator();
		Iterator<TableEntry<String, Integer>> it2 = examMarks.iterator();
		assertThrows(IllegalStateException.class, () -> it.remove());
		it.next();
		it.remove();
		it.next();
		assertFalse(examMarks.containsKey("Ante"));
		assertThrows(ConcurrentModificationException.class, () -> it2.next());
	}
	
	@Test
	void testToString() {
		assertEquals("[Ante=2, Ivana=5, Jasna=2, Kristina=5]", examMarks.toString());
	}
	
	@Test
	void testStoringImplementation() {
		SimpleHashtable<String, Integer> ht = new SimpleHashtable<>(2);
		ht.put("Ivana", 2);
		assertEquals("[Ivana=2]", ht.toString());
		ht.put("Ante", 2);
		assertEquals("[Ante=2, Ivana=2]", ht.toString());
		ht.put("Jasna", 2);
		assertEquals("[Ante=2, Ivana=2, Jasna=2]", ht.toString());
		ht.put("Kristina", 5);
		assertEquals("[Ante=2, Ivana=2, Jasna=2, Kristina=5]", ht.toString());
		ht.put("Ivana", 5);
		assertEquals("[Ante=2, Ivana=5, Jasna=2, Kristina=5]", ht.toString());
	}
	
	@Test
	void testComplexExample() {
		SimpleHashtable<Integer, Integer> ht = new SimpleHashtable<>(8);
		ht.put(1, 0);
		ht.put(9, 0);
		ht.put(25, 0);
		ht.put(17, 0);
		ht.put(3, 0);
		Iterator<TableEntry<Integer, Integer>> it = ht.iterator();
		for (int i=0; i<3; i++) {
			it.next();
			it.remove();
		}
		assertEquals("[17=0, 3=0]", ht.toString());
		it.next();
		it.remove();
		it.next();
		it.remove();
		assertTrue(ht.isEmpty());
	}
	
	@Test
	void testComplexExample2() {
		Object obj = new Object() {
			@Override
			public int hashCode() { return Integer.MIN_VALUE; }
		};
		SimpleHashtable<Object, Object> ht = new SimpleHashtable<>(7);
		ht.put(obj, null);
	}
	
	
	
	
	@BeforeEach
	void createExample() {
		examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
	}
	
}

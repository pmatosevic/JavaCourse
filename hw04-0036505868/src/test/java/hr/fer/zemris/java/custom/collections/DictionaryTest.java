package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DictionaryTest {

	private Dictionary<String, Integer> empty;
	
	private Dictionary<String, Integer> dict;
	
	@Test
	void testConstruction() {
		Dictionary<String, String> d = new Dictionary<>();
		assertTrue(d.isEmpty());
	}
	
	@Test
	void testIsEmpty() {
		assertTrue(empty.isEmpty());
		assertFalse(dict.isEmpty());
	}
	
	
	@Test
	void testSize() {
		assertEquals(0, empty.size());
		assertEquals(4, dict.size());
	}
	
	@Test
	void testClear() {
		dict.clear();
		assertEquals(0, dict.size());
	}
	
	@Test
	void testGetExisting() {
		assertEquals(1, dict.get("key1"));
		assertEquals(2, dict.get(new String("key2")));
	}
	
	@Test
	void testGetNonExisting() {
		assertEquals(null, dict.get("not in dictionary"));
	}
	
	@Test
	void testGetNull() {
		assertEquals(null, dict.get(null));
	}
	
	@Test
	void testPut() {
		dict.put("key new", 100);
		assertEquals(5, dict.size());
		assertEquals(100, dict.get("key new"));
	}
	
	@Test
	void testPutNullValue() {
		dict.put("key new", null);
		assertEquals(5, dict.size());
		assertEquals(null, dict.get("key new"));
	}
	
	@Test
	void testPutUpdate() {
		dict.put("key3", 100);
		assertEquals(4, dict.size());
		assertEquals(100, dict.get("key3"));
	}
	
	@Test
	void testPutNullKey() {
		assertThrows(NullPointerException.class, () -> dict.put(null, 12));
	}
	
	
	
	@BeforeEach
	void createExample() {
		empty = new Dictionary<>();
		dict = new Dictionary<>();
		dict.put("key1", 1);
		dict.put("key2", 2);
		dict.put("key3", 3);
		dict.put("key4", 4);
	}
	
}

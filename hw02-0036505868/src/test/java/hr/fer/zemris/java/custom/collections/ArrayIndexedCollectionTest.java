package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ArrayIndexedCollectionTest {

	
	private ArrayIndexedCollection collection;
	
	@Test
	void testBasicConstructor() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();
		assertEquals(true, coll.isEmpty());
	}
	
	@Test
	void testConstructorWithCapacity() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection(1);
		assertEquals(true, coll.isEmpty());
	}
	
	@Test
	void testConstructorWithNegativeCapacity() {
		assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(-5));
	}
	
	
	@Test
	void testConstructorWithOther() {
		ArrayIndexedCollection collection2 = new ArrayIndexedCollection(collection);
		assertArrayEquals(collection.toArray(), collection2.toArray());
	}
	
	
	@Test
	void testConstructorWithOtherAndCapacity() {
		ArrayIndexedCollection collection2 = new ArrayIndexedCollection(collection, 1);
		assertArrayEquals(collection.toArray(), collection2.toArray());
	}
	
	@Test
	void testConstructorWithNull() {
		assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null));
	}
	
	
	@Test
	void testToArray() {
		Object[] array = collection.toArray();
		assertArrayEquals(new Object[] { "test1", "test2", "test3", "test4" }, array);
	}

	@Test
	void testSize() {
		assertEquals(4, collection.size());
		ArrayIndexedCollection empty = new ArrayIndexedCollection();
		assertEquals(0, empty.size());
	}
	
	@Test
	void testIsEmpty() {
		assertEquals(false, collection.isEmpty());
		ArrayIndexedCollection empty = new ArrayIndexedCollection();
		assertEquals(true, empty.isEmpty());
	}

	@Test
	void testContains() {
		assertEquals(true, collection.contains("test2"));
		assertEquals(true, collection.contains(new String("test2")));
		assertEquals(false, collection.contains("not found"));
	}
	
	@Test
	void testRemoveObject() {
		assertEquals(true, collection.remove("test3"));
		assertEquals(false, collection.remove("not found"));
		assertArrayEquals(new Object[] { "test1", "test2", "test4" }, collection.toArray());
	}
	
	
	@Test
	void testForEach() {
		Object[] array = collection.toArray();
		
		class TestingProcessor extends Processor {
			int index = 0;
			
			@Override
			public void process(Object value) {
				assertEquals(array[index], value);
				index++;
			}
		}
		
		TestingProcessor p = new TestingProcessor();
		collection.forEach(p);
	}
	
	
	@Test
	void testAdd() {
		collection.add("added");
		assertArrayEquals(new Object[] { "test1", "test2", "test3" , "test4", "added" }, collection.toArray());
	}
	
	@Test
	void testAddNull() {
		assertThrows(NullPointerException.class, () -> collection.add(null));
	}
	
	@Test
	void testClear() {
		collection.clear();
		assertEquals(0, collection.size());
		assertArrayEquals(new Object[0], collection.toArray());
	}
	
	@Test
	void testGetValidIndex() {
		assertEquals("test2", collection.get(1));
	}
	
	@Test
	void testGetInvalidIndex() {
		assertThrows(IndexOutOfBoundsException.class, () -> collection.get(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> collection.get(collection.size()));
	}
	
	@Test
	void testInsertBeginning() {
		collection.insert("added", 0);
		assertArrayEquals(new Object[] { "added", "test1", "test2", "test3" , "test4" }, collection.toArray());
	}
	
	@Test
	void testInsertMiddle() {
		collection.insert("added", 2);
		assertArrayEquals(new Object[] { "test1", "test2", "added" , "test3" , "test4" }, collection.toArray());
	}
	
	@Test
	void testInsertEnd() {
		collection.insert("added", collection.size());
		assertArrayEquals(new Object[] { "test1", "test2", "test3" , "test4", "added" }, collection.toArray());
	}
	
	@Test
	void testInsertBigCollection() {
		ArrayIndexedCollection coll = createBigCollection();
		coll.insert("added", 750);
		assertEquals("added", coll.get(750));
		assertEquals("test749", coll.get(749));
		assertEquals("test750", coll.get(751));
	}
	
	@Test
	void testInsertNull() {
		assertThrows(NullPointerException.class, () -> collection.insert(null, 0));
	}
	
	@Test
	void testInsertInvalidIndex() {
		assertThrows(IndexOutOfBoundsException.class, () -> collection.insert("added", -1));
		assertThrows(IndexOutOfBoundsException.class, () -> collection.insert("added", collection.size() + 1));
	}
	
	
	@Test
	void testIndexOfFound() {
		assertEquals(1, collection.indexOf("test2"));
	}
	
	@Test
	void testIndexOfNotFound() {
		assertEquals(-1, collection.indexOf("not found"));
	}
	
	@Test
	void testIndexOfNull() {
		assertEquals(-1, collection.indexOf(null));
	}
	
	@Test
	void testRemoveIndex() {
		collection.remove(0);
		collection.remove(collection.size() - 1);
		assertArrayEquals(new Object[] { "test2", "test3" }, collection.toArray());
	}
	
	@Test
	void testRemoveIndexBigCollection() {
		ArrayIndexedCollection coll = createBigCollection();
		coll.remove(750);
		assertEquals("test751", coll.get(750));
		assertEquals("test749", coll.get(749));
		assertEquals("test752", coll.get(751));
	}
	
	@Test
	void testRemoveIndexInvalidIndex() {
		assertThrows(IndexOutOfBoundsException.class, () -> collection.remove(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> collection.remove(collection.size()));
	}
	
	@Test
	void testAddAll() {
		Collection newColl = new ArrayIndexedCollection();
		newColl.add("added1");
		newColl.add("added2");
		collection.addAll(newColl);
		
		assertArrayEquals(new Object[] { "test1", "test2", "test3",
				"test4", "added1", "added2" },  collection.toArray());
	}
	
	@Test
	void testAddAllEmpty() {
		Collection newColl = new ArrayIndexedCollection();
		collection.addAll(newColl);
		
		assertArrayEquals(new Object[] { "test1", "test2", "test3", "test4" },  collection.toArray());
	}
	
	@Test
	void testAddAllNull() {
		assertThrows(NullPointerException.class, () -> collection.addAll(null));
	}
	
	
	@BeforeEach
	void create() {
		collection = new ArrayIndexedCollection();
		collection.add("test1");
		collection.add("test2");
		collection.add("test3");
		collection.add("test4");
	}
	
	private ArrayIndexedCollection createBigCollection() {
		ArrayIndexedCollection coll = new ArrayIndexedCollection();
		for (int i = 0; i < 1000; i++) {
			coll.add("test" + i);
		}
		return coll;
	}
	
	
}

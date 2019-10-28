package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

public class ObjectMultistackTest {

	@Test
	void testPush() {
		ObjectMultistack om = create();
		om.push("test2", new ValueWrapper(3));
		assertEquals(new ValueWrapper(3), om.peek("test2"));
	}
	
	@Test
	void testPeek() {
		ObjectMultistack om = create();
		assertEquals(new ValueWrapper(3), om.peek("test"));
	}
	
	@Test
	void testPop() {
		ObjectMultistack om = create();
		assertEquals(new ValueWrapper(3), om.peek("test"));
		assertEquals(new ValueWrapper(3), om.pop("test"));
		assertEquals(new ValueWrapper(2), om.peek("test"));
		assertEquals(new ValueWrapper(2), om.pop("test"));
		assertEquals(new ValueWrapper(1), om.peek("test"));
		assertEquals(new ValueWrapper(1), om.pop("test"));
		assertThrows(NoSuchElementException.class, () -> om.pop("test"));
		assertThrows(NoSuchElementException.class, () -> om.peek("test"));
		assertThrows(NoSuchElementException.class, () -> om.pop("aaa"));
	}
	
	@Test
	void testIsEmpty() {
		ObjectMultistack om = create();
		assertFalse(om.isEmpty("test"));
		assertTrue(om.isEmpty("not found"));
		om.pop("test");
		om.pop("test");
		om.pop("test");
		assertTrue(om.isEmpty("test"));
	}
	
	ObjectMultistack create() {
		ObjectMultistack om = new ObjectMultistack();
		om.push("test", new ValueWrapper(1));
		om.push("test", new ValueWrapper(2));
		om.push("test", new ValueWrapper(3));
		return om;
	}
	
}

package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ObjectStackTest {

	private ObjectStack stack;
	
	@Test
	void testIsEmpty() {
		assertEquals(false, stack.isEmpty());
		assertEquals(true, new ObjectStack().isEmpty());
	}
	
	@Test
	void testSize() {
		assertEquals(4, stack.size());
	}
	
	@Test
	void testPush() {
		stack.push("added");
		assertEquals("added", stack.peek());
	}
	
	@Test
	void testPushNull() {
		assertThrows(NullPointerException.class, () -> stack.push(null));
	}
	
	@Test
	void testPeek() {
		assertEquals("test4", stack.peek());
	}
	
	@Test
	void testPop() {
		assertEquals("test4", stack.pop());
		assertEquals("test3", stack.pop());
	}
	
	
	@Test
	void testClear() {
		stack.clear();
		assertEquals(0, stack.size());
	}
	
	@BeforeEach
	void create() {
		stack = new ObjectStack();
		stack.push("test1");
		stack.push("test2");
		stack.push("test3");
		stack.push("test4");
	}
	
}

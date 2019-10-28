package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ValueWrapperTest {

	@Test
	void testAddNull() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue()); // v1 now stores Integer(0); v2 still stores null.
		assertEquals(Integer.valueOf(0), v1.getValue());
		assertEquals(null, v2.getValue());
	}
	
	@Test
	void testAddStringAsDouble() {
		ValueWrapper v3 = new ValueWrapper("1.2E1");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));
		v3.add(v4.getValue()); // v3 now stores Double(13); v4 still stores Integer(1).
		assertEquals(Double.valueOf(13), v3.getValue());
		assertEquals(Integer.valueOf(1), v4.getValue());
	}
	
	@Test
	void testAddStringAsInt() {
		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
		v5.add(v6.getValue()); // v5 now stores Integer(13); v6 still stores Integer(1).
		assertEquals(Integer.valueOf(13), v5.getValue());
		assertEquals(Integer.valueOf(1), v6.getValue());
	}
	
	@Test
	void testInvalidFormat() {
		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
		assertThrows(RuntimeException.class, () -> v7.add(v8.getValue()));
		assertThrows(RuntimeException.class, () -> v7.subtract(v8.getValue()));
		assertThrows(RuntimeException.class, () -> v7.multiply(v8.getValue()));
		assertThrows(RuntimeException.class, () -> v7.divide(v8.getValue()));
	}
	
	@Test
	void testInvalidType() {
		ValueWrapper v7 = new ValueWrapper(true);
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
		assertThrows(RuntimeException.class, () -> v7.add(v8.getValue()));
		assertThrows(RuntimeException.class, () -> v7.subtract(v8.getValue()));
		assertThrows(RuntimeException.class, () -> v7.multiply(v8.getValue()));
		assertThrows(RuntimeException.class, () -> v7.divide(v8.getValue()));
	}
	
	@Test
	void testSubStringIntNull() {
		ValueWrapper v1 = new ValueWrapper("25");
		ValueWrapper v2 = new ValueWrapper(null);
		v1.subtract(v2.getValue());
		assertEquals(Integer.valueOf(25), v1.getValue());
	}
	
	@Test
	void testSubStringDoubleNull() {
		ValueWrapper v1 = new ValueWrapper("2.5E1");
		ValueWrapper v2 = new ValueWrapper(null);
		v1.subtract(v2.getValue());
		assertEquals(Double.valueOf(25), v1.getValue());
	}
	
	@Test
	void testSubStringsIntInt() {
		ValueWrapper v1 = new ValueWrapper("2");
		ValueWrapper v2 = new ValueWrapper("4");
		v1.subtract(v2.getValue());
		assertEquals(Integer.valueOf(-2), v1.getValue());
	}
	
	
	@Test
	void testMultiplyIntDouble() {
		ValueWrapper v1 = new ValueWrapper(10);
		ValueWrapper v2 = new ValueWrapper(Double.valueOf(20));
		v1.multiply(v2.getValue());
		assertEquals(Double.valueOf(200), v1.getValue());
	}
	
	@Test
	void testMultiplyIntInt() {
		ValueWrapper v1 = new ValueWrapper(10);
		ValueWrapper v2 = new ValueWrapper(20);
		v1.multiply(v2.getValue());
		assertEquals(Integer.valueOf(200), v1.getValue());
	}
	
	@Test
	void testDivisionIntStringInt() {
		ValueWrapper v1 = new ValueWrapper(10);
		ValueWrapper v2 = new ValueWrapper("2");
		v1.divide(v2.getValue());
		assertEquals(Integer.valueOf(5), v1.getValue());
	}
	
	@Test
	void testComparisonBasic() {
		ValueWrapper v1 = new ValueWrapper(10);
		Object v2 = "2";
		assertTrue(v1.numCompare(v2) > 0);
	}
	
	@Test
	void testComparisonNulls() {
		ValueWrapper v1 = new ValueWrapper(null);
		Object v2 = null;
		assertTrue(v1.numCompare(v2) == 0);
	}
	
	@Test
	void testComparisonDoubleNull() {
		ValueWrapper v1 = new ValueWrapper(Double.valueOf(-2.5));
		Object v2 = null;
		assertTrue(v1.numCompare(v2) < 0);
	}
	
	@Test
	void testComparisonStrings() {
		ValueWrapper v1 = new ValueWrapper("5.0");
		Object v2 = Integer.valueOf(10);
		assertTrue(v1.numCompare(v2) < 0);
	}
	
	@Test
	void testComparisonInvalid() {
		ValueWrapper v1 = new ValueWrapper("5.0");
		ValueWrapper v2 = new ValueWrapper("5.0");
		assertThrows(RuntimeException.class, () -> v1.numCompare(v2));
	}
	
	
	
	
}

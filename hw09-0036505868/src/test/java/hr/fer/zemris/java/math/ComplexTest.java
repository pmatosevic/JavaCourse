package hr.fer.zemris.java.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.math.Complex;

public class ComplexTest {

	@Test
	void testParse1() {
		assertEquals(new Complex(0, 1),  Complex.parse("i")); 
		assertEquals(new Complex(0, 1),  Complex.parse("+i")); 
		assertEquals(new Complex(0, -1),  Complex.parse(" - i")); 
		assertEquals(new Complex(0, -1),  Complex.parse("0 - i1")); 
	}
	
	@Test
	void testParse2() {
		assertEquals(new Complex(5, 0),  Complex.parse("+5")); 
		assertEquals(new Complex(-2.5, 0),  Complex.parse("-2.5 + i0")); 
	}
	
	@Test
	void testParse3() {
		assertEquals(new Complex(5, 2),  Complex.parse("+5 + i2")); 
		assertEquals(new Complex(2.5, -3.5),  Complex.parse("2.5 - i3.5")); 
	}
	
}

package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw02.ComplexNumber;

import static hr.fer.zemris.java.hw02.ComplexNumber.*;

public class ComplexNumberTest {

	private static final double EPS = 1e-6;
	
	
	@Test
	void testConstructor() {
		ComplexNumber c = new ComplexNumber(1, 2);
		assertEquals(1, c.getReal());
		assertEquals(2, c.getImaginary());
	}
	
	@Test
	void testEquals() {
		ComplexNumber c1 = new ComplexNumber(1.25, 1.25);
		ComplexNumber c2 = new ComplexNumber(1.25, 1.25);
		ComplexNumber c3 = new ComplexNumber(2, 2);
		
		assertEquals(true, c1.equals(c2));
		assertEquals(false, c1.equals(c3));
	}
	
	@Test
	void testGetReal() {
		ComplexNumber c = new ComplexNumber(2.5, -3.0);
		assertEquals(2.5, c.getReal(), EPS);
	}
	
	@Test
	void testGetImaginary() {
		ComplexNumber c = new ComplexNumber(2.5, -3.0);
		assertEquals(-3.0, c.getImaginary(), EPS);
	}
	
	@Test
	void testGetMagnitude() {
		ComplexNumber c = new ComplexNumber(3.0, -4.0);
		assertEquals(5.0, c.getMagnitude(), EPS);
	}
	
	@Test
	void testGetAngle() {
		ComplexNumber c = new ComplexNumber(2.5, -2.5);
		assertEquals(7 * Math.PI / 4, c.getAngle(), EPS);
	}
	
	@Test
	void testAdd() {
		ComplexNumber c1 = new ComplexNumber(2.0, -3.0);
		ComplexNumber c2 = new ComplexNumber(5.5, -5.2);
		ComplexNumber c3 = c1.add(c2);
		
		assertEquals(new ComplexNumber(7.5, -8.2), c3);
	}
	
	@Test
	void testSub() {
		ComplexNumber c1 = new ComplexNumber(2.0, -3.0);
		ComplexNumber c2 = new ComplexNumber(5.5, -5.2);
		ComplexNumber c3 = c1.sub(c2);
		
		assertEquals(new ComplexNumber(-3.5, 2.2), c3);
	}
	
	@Test
	void testMul() {
		ComplexNumber c1 = new ComplexNumber(5.75, -3.25);
		ComplexNumber c2 = new ComplexNumber(5.5, 3.25);
		ComplexNumber c3 = c1.mul(c2);
		
		assertEquals(new ComplexNumber(42.1875, 0.8125), c3);
	}
	
	@Test
	void testDiv() {
		ComplexNumber c1 = new ComplexNumber(5.75, 10.0);
		ComplexNumber c2 = new ComplexNumber(1.5, -3.25);
		ComplexNumber c3 = c1.div(c2);
		
		assertEquals(new ComplexNumber(-1.863414634, 2.629268293), c3);
	}
	
	@Test
	void testPower() {
		ComplexNumber c = new ComplexNumber(2.1, -5.65);
		ComplexNumber res = c.power(5);
		
		assertEquals(new ComplexNumber(7784.481351, 1646.950952), res);
	}
	
	void testPowerNegative() {
		ComplexNumber c = new ComplexNumber(2.1, -5.65);
		assertThrows(IllegalArgumentException.class, () -> c.power(-1));
	}
	
	@Test
	void testRoot() {
		ComplexNumber c = new ComplexNumber(2.61, -0.22);
		ComplexNumber[] roots = c.root(2);
		
		assertEquals(2, roots.length);
		assertEquals(new ComplexNumber(-1.616981079, 0.0680280069), roots[0]);
		assertEquals(new ComplexNumber(1.616981079, -0.0680280069), roots[1]);
	}
	
	void testRootNonPositive() {
		ComplexNumber c = new ComplexNumber(2.1, -5.65);
		assertThrows(IllegalArgumentException.class, () -> c.root(0));
	}
	
	@Test
	void testFromReal() {
		ComplexNumber c = fromReal(2.0);
		assertEquals(new ComplexNumber(2.0, 0.0), c);
	}
	
	@Test
	void testFromImaginary() {
		ComplexNumber c = fromImaginary(2.0);
		assertEquals(new ComplexNumber(0.0, 2.0), c);
	}
	
	@Test
	void testFromMagnitudeAndAngle() {
		ComplexNumber c = fromMagnitudeAndAngle(10.0, 5 * 30.0 / 180 * Math.PI);
		assertEquals(new ComplexNumber(-8.660254038, 5.0), c);
	}
	
	
	
	@Test
	void testParseOnlyRealBasic() {
		assertEquals(fromReal(0), parse("0"));
		assertEquals(fromReal(1), parse("1"));
		assertEquals(fromReal(10), parse("10"));
	}
	
	
	@Test
	void testParseOnlyRealDecimal() {
		assertEquals(fromReal(3.51), parse("3.51"));
		assertEquals(fromReal(3.51), parse("+3.51"));
		assertEquals(fromReal(-3.17), parse("-3.17"));
	}
	
	
	@Test
	void testParseOnlyImaginaryBasic() {
		assertEquals(fromImaginary(5), parse("5i"));
		assertEquals(fromImaginary(5), parse("+5i"));
		assertEquals(fromImaginary(-5), parse("-5i"));
	}
	
	
	@Test
	void testParseOnlyImaginaryDecimal() {
		assertEquals(fromImaginary(3.17), parse("3.17i"));
		assertEquals(fromImaginary(3.2), parse("+3.2i"));
		assertEquals(fromImaginary(-253.456), parse("-253.456i"));
	}
	
	
	@Test
	void testParseOnlyImaginarySpecial() {
		assertEquals(fromImaginary(0), parse("0i"));
		
		assertEquals(fromImaginary(-1), parse("-i"));
		assertEquals(fromImaginary(1), parse("+i"));
		assertEquals(fromImaginary(1), parse("i"));
	}
	
	
	@Test
	void testParseRealAndImaginary() {
		assertEquals(new ComplexNumber(1, 1), parse("1+1i"));
		assertEquals(new ComplexNumber(1.5, 1.5), parse("1.5+1.5i"));
		
		assertEquals(new ComplexNumber(-2.71, -3.15), parse("-2.71-3.15i"));
		assertEquals(new ComplexNumber(-2.71, -1), parse("-2.71-i"));
		assertEquals(new ComplexNumber(+235.124, 23), parse("235.124+23i"));
		assertEquals(new ComplexNumber(+235.124, -23), parse("+235.124-23i"));
	}
	
	
	@Test
	void testParseInvalid() {
		assertThrows(NumberFormatException.class, () -> parse("štefica"));
	}
	
	@Test
	void testParseEmpty() {
		assertThrows(NumberFormatException.class, () -> parse(""));
	}
	
	@Test
	void testParseWithSpaces() {
		assertThrows(NumberFormatException.class, () -> parse("2 + 3i"));
		assertThrows(NumberFormatException.class, () -> parse("2.5+3 i"));
	}
	
	
	@Test
	void testParseInvalidPlusMinus() {
		assertThrows(NumberFormatException.class, () -> parse("++2"));
		assertThrows(NumberFormatException.class, () -> parse("--2i"));
		assertThrows(NumberFormatException.class, () -> parse("-+2.71"));
		assertThrows(NumberFormatException.class, () -> parse("-2.71+-3.15i"));
	}
	
	@Test
	void testParseInvalidOrder() {
		assertThrows(NumberFormatException.class, () -> parse("i351"));
		assertThrows(NumberFormatException.class, () -> parse("5-i3.17"));
	}
	
	@Test
	void testParseInvalidDuplicateParts() {
		assertThrows(NumberFormatException.class, () -> parse("-i-i"));
		assertThrows(NumberFormatException.class, () -> parse("2.34i-5.21i"));
		assertThrows(NumberFormatException.class, () -> parse("2+2"));
	}
	
	@Test
	void testParseInvalidPeriod() {
		assertThrows(NumberFormatException.class, () -> parse("2.1.0"));
		assertThrows(NumberFormatException.class, () -> parse("2.1+2.5.i"));
		assertThrows(NumberFormatException.class, () -> parse("2,5+3i"));
	}
}

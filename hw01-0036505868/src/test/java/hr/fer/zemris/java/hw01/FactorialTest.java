package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static hr.fer.zemris.java.hw01.Factorial.*;

import org.junit.jupiter.api.Test;


/**
 * Class with tests for class {@code Factorial}
 * @author Patrik
 *
 */
public class FactorialTest {

	
	@Test
	public void testFactorialTen() {
		assertEquals(3628800L, factorial(10));
	}
	

	@Test
	public void testFactorialTwenty() {
		assertEquals(2432902008176640000L, factorial(20));
	}
	

	@Test
	public void testFactorialZero() {
		assertEquals(1L, factorial(0));
	}
	

	@Test
	public void testFactorialOne() {
		assertEquals(1L, factorial(1));
	}


	@Test
	public void testNegative() {
		assertThrows(IllegalArgumentException.class, () -> factorial(-1));
	}
	

	@Test
	public void testFactorialOutside() {
		assertThrows(IllegalArgumentException.class, () -> factorial(21));
	}
	

	@Test
	public void testFactorialBig() {
		assertThrows(IllegalArgumentException.class, () -> factorial(10000));
	}
	

}

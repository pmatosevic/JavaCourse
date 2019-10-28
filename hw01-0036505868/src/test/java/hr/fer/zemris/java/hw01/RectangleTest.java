package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static  hr.fer.zemris.java.hw01.Rectangle.*;

import org.junit.jupiter.api.Test;

/**
 * Class with tests for class {@code Rectangle}
 * @author Patrik
 *
 */
public class RectangleTest {

	@Test
	public void testPerimeter() {
		assertEquals(18, calculatePerimeter(5, 4));
		assertThrows(IllegalArgumentException.class, () -> {calculatePerimeter(-1, 0);});
	}
	
	@Test
	public void testArea() {
		assertEquals(20, calculateArea(5, 4));
		assertThrows(IllegalArgumentException.class, () -> {calculateArea(-1, 0);});
	}
	
	
	
}

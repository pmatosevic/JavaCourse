package hr.fer.zemris.java.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Vector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.math.Vector2D;

public class Vector2DTest {

	
	private Vector2D vec;

	@Test
	void testConstructor() {
		Vector2D vect = new Vector2D(0, 0);
		
		assertEquals(0, vect.getX());
		assertEquals(0, vect.getY());
	}
	
	@Test
	void testConstructor2() {
		Vector2D vect = new Vector2D(5, -3.5);
		
		assertEquals(5, vect.getX());
		assertEquals(-3.5, vect.getY());
	}
	
	@Test
	void testGetX() {
		Vector2D vect = new Vector2D(5, -10.5);
		assertEquals(5, vect.getX());
	}
	
	@Test
	void testGetY() {
		Vector2D vect = new Vector2D(5, -10.5);
		assertEquals(-10.5, vect.getY());
	}
	
	@Test
	void testTranslate() {
		vec.translate(new Vector2D(5, 10));
		assertEquals(new Vector2D(7.75, -5.125), vec);
	}
	
	@Test
	void testTranslated() {
		Vector2D result = vec.translated(new Vector2D(5, 10));
		assertEquals(new Vector2D(7.75, -5.125), result);
	}
	
	@Test
	void testRotate() {
		vec.rotate(Math.PI/4);
		assertEquals(new Vector2D(12.639534, -8.750446), vec);
	}
	
	@Test
	void testRotateNegative() {
		vec.rotate(-1*Math.PI/4);
		assertEquals(new Vector2D(-8.750446, -12.639534), vec);
	}
	
	@Test
	void testRotateBigAngle() {
		vec.rotate(9*Math.PI/4);
		assertEquals(new Vector2D(12.639534, -8.750446), vec);
	}
	
	
	@Test
	void testRotated() {
		Vector2D result = vec.rotated(Math.PI/4);
		assertEquals(new Vector2D(12.639534, -8.750446), result);
	}
	
	@Test
	void testRotatedNegative() {
		Vector2D result = vec.rotated(-1*Math.PI/4);
		assertEquals(new Vector2D(-8.750446, -12.639534), result);
	}
	
	@Test
	void testRotatedBigAngle() {
		Vector2D result = vec.rotated(9*Math.PI/4);
		assertEquals(new Vector2D(12.639534, -8.750446), result);
	}
	
	
	@Test
	void testScale() {
		vec.scale(5);
		assertEquals(new Vector2D(5 * 2.75, -15.125 * 5), vec);
	}
	
	@Test
	void testScaleNegative() {
		vec.scale(-5);
		assertEquals(new Vector2D(-5 * 2.75, 15.125 * 5), vec);
	}
	
	@Test
	void testScaled() {
		Vector2D result = vec.scaled(5);
		assertEquals(new Vector2D(5 * 2.75, -15.125 * 5), result);
	}
	
	@Test
	void testScaledNegative() {
		Vector2D result = vec.scaled(-5);
		assertEquals(new Vector2D(-5 * 2.75, 15.125 * 5), result);
	}
	
	@Test
	void testCopy() {
		Vector2D copy = vec.copy();
		assertEquals(new Vector2D(2.75, -15.125), copy);
	}
	
	@Test
	void testEquals() {
		assertTrue(vec.equals(new Vector2D(2.75, -15.125)));
		assertFalse(vec.equals(new Vector2D(5, 6)));
	}
	
	
	@BeforeEach
	void createVector() {
		vec = new Vector2D(2.75, -15.125);
	}
	
	
	
}

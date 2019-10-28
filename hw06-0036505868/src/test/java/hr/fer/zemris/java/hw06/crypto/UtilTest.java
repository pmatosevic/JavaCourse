package hr.fer.zemris.java.hw06.crypto;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class UtilTest {

	@Test
	void testHextobyte() {
		assertArrayEquals(new byte[] {1, -82, 34}, Util.hextobyte("01aE22"));
	}
	
	@Test
	void testHextobyteLong() {
		assertArrayEquals(new byte[] {1, -82, 34, -1, 127, 16, -128}, Util.hextobyte("01aE22fF7F1080"));
	}
	
	@Test
	void testHextobyteInvalid() {
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("01aE2"));
	}
	
	@Test
	void testBytetohex() {
		assertEquals("01ae22", Util.bytetohex(new byte[] {1, -82, 34}));
	}
	
	@Test
	void testBytetohexLong() {
		assertEquals("01ae22ff7f1080", Util.bytetohex(new byte[] {1, -82, 34, -1, 127, 16, -128}));
	}
	
}

package hr.fer.zemris.java.hw06.shell;

public class Util {

	/**
	 * Converts the given string of hexadecimal characters to a byte array
	 * 
	 * @param keyText string of hexadecimal characters
	 * @return array of bytes
	 */
	public static byte[] hextobyte(String keyText) {
		if (keyText.length() % 2 != 0) {
			throw new IllegalArgumentException("hex-encoded text length should be divisible by 2.");
		}

		int len = keyText.length() / 2;

		byte[] result = new byte[len];
		for (int i = 0; i < len; i++) {
			int val1 = (byte) valueOfChar(keyText.charAt(2 * i));
			int val2 = (byte) valueOfChar(keyText.charAt(2 * i + 1));
			result[i] = (byte) (16 * val1 + val2);
		}

		return result;
	}

	/**
	 * Returns the value of the given hexadecimal character.
	 * 
	 * @param ch given character
	 * @return the value
	 */
	private static int valueOfChar(char ch) {
		if (ch >= '0' && ch <= '9') {
			return ch - '0';
		} else if (ch >= 'A' && ch <= 'F') {
			return ch - 'A' + 10;
		} else if (ch >= 'a' && ch <= 'f') {
			return ch - 'a' + 10;
		} else {
			throw new IllegalArgumentException("Invalid char");
		}
	}

	/**
	 * Converts the given array of bytes to a string of hexadecimal characters.
	 * 
	 * @param bytearray input array
	 * @return string of hexadecimal characters
	 */
	public static String bytetohex(byte[] bytearray) {
		StringBuilder sb = new StringBuilder();

		for (byte b : bytearray) {
			sb.append(byteToHex(b));
		}

		return sb.toString();
	}

	/**
	 * Converts given byte to a hexadecimal representation with leading zeros.
	 * 
	 * @param b byte
	 * @return String of 2 characters as a representation of the byte
	 */
	public static String byteToHex(byte b) {
		char[] chars = new char[2];
		chars[0] = numToChar((b >> 4) & 0xF);
		chars[1] = numToChar(b & 0xF);
		return new String(chars);
	}
	
	/**
	 * Converts the number to a single character in base 16.
	 * @param digit number
	 * @return character
	 */
	private static char numToChar(int digit) {
		if (digit < 10) {
			return (char) ('0' + digit);
		} else {
			return (char) ('a' + (digit - 10));
		}
	}

}

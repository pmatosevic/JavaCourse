package hr.fer.zemris.java.hw15.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.hw06.crypto.Crypto;

/**
 * Class with utility mehods.
 * 
 * @author Patrik
 *
 */
public class Util {

	/**
	 * Valid e-mail pattern
	 */
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(
			"^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE
			);

	/**
	 * Validates an email string
	 * @param emailStr an email string
	 * @return whether the email is valid or not
	 */
	public static boolean validate(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}
	
	/**
	 * Hashes the plaintext using SHA-1 algorithm.
	 * @param plaintext plaintext string
	 * @return hashed text
	 */
	public static String hashPassword(String plaintext) {
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(plaintext.getBytes(StandardCharsets.UTF_8));
			return Crypto.calcDigest(bis);
		} catch (IOException ignorable) {
			return null;
		}
	}

}

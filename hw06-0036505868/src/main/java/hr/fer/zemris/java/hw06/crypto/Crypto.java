package hr.fer.zemris.java.hw06.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Program that supports calculating SHA-256 digests and encrypting and decrypting files.
 * Calculating digests can be run with "chacksha path_to_file_to_encrypt path_to_encypted_file" 
 * command-line arguments, encryption with "encrypt path_to_file" and decyption with 
 * "decrypt path_to_file_to_decrypt path_to_decypted_file". 
 * 
 * @author Patrik
 *
 */
public class Crypto {

	/**
	 * Check SHA command
	 */
	private static final String CHECK_SHA = "checksha";
	
	/**
	 * Encrypt command
	 */
	private static final String ENCRYPT = "encrypt";
	
	/**
	 * Decrypt command
	 */
	private static final String DECRYPT = "decrypt";

	/**
	 * Buffer size when reading files
	 */
	private static final int BUFFER_SIZE = 4096;

	/**
	 * Program entry point
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		if (args.length < 2 || args.length > 3) {
			System.out.println("Please enter the command and the filename.");
			return;
		}
		
		Scanner sc = new Scanner(System.in);
		
		if (args[0].equals(CHECK_SHA)) {
			Path file = Paths.get(args[1]);
			System.out.format("Please provide expected sha-256 digest for %s:%n> ", args[1]);
			String inputDigest = sc.next();
			String realDigest;
			
			try (InputStream is = new BufferedInputStream(Files.newInputStream(file))) {
				realDigest = calcDigest(is);
				System.out.print("Digesting completed. ");
				if (realDigest.equals(inputDigest)) {
					System.out.format("Digest of %s matches expected digest.%n", args[1]);
				} else {
					System.out.format("Digest of %s does not match the expected digest. Digest was: %s%n", args[1], realDigest);
				}
				
			} catch (IOException ex) {
				System.out.println("Error during reading file: " + ex.getMessage());
			} catch (IllegalArgumentException ex) {
				System.out.println("Error during digesting: " + ex.getMessage());
			}
			
		} else if (args[0].equals(ENCRYPT) || args[0].equals(DECRYPT)) {
			if (args.length != 3) {
				System.out.println("Please provide input and output filename.");
				sc.close();
				return;
			}
			
			boolean encrypt = args[0].equals(ENCRYPT);
			
			Path inputFile = Paths.get(args[1]);
			Path outputFile = Paths.get(args[2]);
			
			System.out.format("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):%n> ");
			String keyText = sc.next();
			System.out.format("Please provide initialization vector as hex-encoded text (32 hex-digits):%n> ");
			String ivText = sc.next();
			
			try (InputStream is = new BufferedInputStream(Files.newInputStream(inputFile)); 
					OutputStream os = new BufferedOutputStream(Files.newOutputStream(outputFile))) {
				cryptOperation(is, os, keyText, ivText, encrypt);
				System.out.format("%s completed. Generated file %s based on file %s.%n", 
						encrypt ? "Encryption" : "Decryption", args[2], args[1]);
				
			} catch (IOException ex) {
				System.out.println("Error during reading or writing file: " + ex.getMessage());
			} catch (IllegalArgumentException ex) {
				System.out.println("Error while performing crypt opertion: " + ex.getMessage());
			}
			
		} else {
			System.out.println("Invalid command.");
		}
		
		sc.close();
	}
	
	
	/**
	 * Calculates the SHA-256 digest of given input stream.
	 * 
	 * @param is input stream
	 * @return calculated SHA-256 hex-encoded string
	 * @throws IOException in case of an error during reading
	 */
	private static String calcDigest(InputStream is) throws IOException {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] buf = new byte[BUFFER_SIZE];
			while (true) {
				int read = is.read(buf);
				if (read < 1) {
					break;
				}
				md.update(buf, 0, read);
			}
			byte[] digest = md.digest();
			return Util.bytetohex(digest);
		} catch (NoSuchAlgorithmException ex) {
			throw new IllegalArgumentException("Error during digesting: " + ex.getMessage());
		}
		
	}
	
	
	/**
	 * Performs encryption or decryption with given input and output stream and cipher parameters.
	 * 
	 * @param is input stream
	 * @param os output stream
	 * @param keyText key hex-encoded text
	 * @param ivText initialization vector hex-encoded text
	 * @param encrypt whether to encrypt or decrypt
	 * @throws IOException in case of an error during reading or writing
	 */
	private static void cryptOperation(InputStream is, OutputStream os, String keyText, String ivText, boolean encrypt) throws IOException {
		try {
			SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
			
			byte[] buf = new byte[BUFFER_SIZE];
			while (true) {
				int read = is.read(buf);
				if (read < 1) {
					break;
				}
				os.write(cipher.update(buf, 0, read));
			}
			os.write(cipher.doFinal());
			os.flush();
		} catch (BadPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException | InvalidKeyException e) {
			throw new IllegalArgumentException(e.getMessage());
		} 
		
	}
	
	
	
	
	
}

package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.crypto.Util;
import hr.fer.zemris.java.hw06.shell.ArgumentParser;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * A command that prints the content of a file as a hex-encoded text.
 * 
 * @author Patrik
 *
 */
public class HexdumpShellCommand implements ShellCommand {

	/**
	 * Command description
	 */
	private static final List<String> DESCRIPTION = Collections.unmodifiableList(Arrays.asList(
			"hexdump file",
			"Prints the content of \"file\"",
			"as hex-encoded text"
		));
	
	/**
	 * The length of a line in the hex-view of the file
	 */
	private static final int LINE_LENGTH = 16;
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] parts;
		try {
			parts = ArgumentParser.parseArguments(arguments);
		} catch (IllegalArgumentException ex) {
			env.writeln("Invalid arguments.");
			return ShellStatus.CONTINUE;
		}
		
		if (parts.length != 1) {
			env.writeln("Expected 1 argument.");
			return ShellStatus.CONTINUE;
		}
		
		Path file = Paths.get(parts[0]);
		
		if (!Files.exists(file) || !Files.isRegularFile(file)) {
			env.writeln("The file does not exist.");
			return ShellStatus.CONTINUE;
		}
		
		try (InputStream is = new BufferedInputStream(Files.newInputStream(file))) {
			int position = 0;
			byte[] buf = new byte[LINE_LENGTH];
			while (true) {
				//int read = is.read(buf);
				int read = read(is, buf);
				if (read < 1) break;
				
				String[] hexStrs = byteToStr(buf, read);
				String charsStr = byteToText(buf, read);
				
				env.write(String.format("%08X:", position));
				for (int i = 0; i < LINE_LENGTH / 2; i++) {
					env.write(" ");
					env.write(hexStrs[i] == null ? "  " : hexStrs[i]);
				}
				env.write("|");
				for (int i = LINE_LENGTH / 2; i < LINE_LENGTH; i++) {
					env.write(hexStrs[i] == null ? "  " : hexStrs[i]);
					env.write(" ");
				}
				env.write("| ");
				env.write(charsStr);
				env.writeln("");
				
				position += LINE_LENGTH;
			}
			
			
		} catch (IOException ex) {
			env.writeln("IO Error: " + ex.getMessage());
		}
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * Tries to read {@code buf.length} bytes (since {@link InputStream#read()} does
	 * not have to read all the bytes), and returns what was read.
	 * 
	 * @param is  input stream
	 * @param buf byte array
	 * @return number of read bytes
	 * @throws IOException in case of an IO error
	 */
	private int read(InputStream is, byte[] buf) throws IOException {
		int off = 0;
		int len = buf.length;
		while (off < len) {
			int read = is.read(buf, off, len - off);
			if (read < 1)
				return off;
			off += read;
		}
		return off;
	}
	
	
	/**
	 * Converts given byte array to array of hex-encoded strings
	 * @param bytearray byte array
	 * @param len count of elements in the array
	 * @return array of hex-encoded strings
	 */
	private String[] byteToStr(byte[] bytearray, int len) {
		String[] result = new String[16];
		for (int i=0; i<len; i++) {
			result[i] = Util.byteToHex(bytearray[i]).toUpperCase();
		}
		return result;
	}
	
	/**
	 * Converts the given byte array to plain text (or "." if the byte cannot be printed).
	 * @param bytearray byte array
	 * @param len count of elements in the array
	 * @return plain text
	 */
	private String byteToText(byte[] bytearray, int len) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			if (bytearray[i] < 32 || bytearray[i] > 127) {
				sb.append(".");
			} else {
				sb.append(Character.valueOf((char) bytearray[i]));
			}
		}
		return sb.toString();
	}

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
		return DESCRIPTION;
	}

	
	
}

package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.ArgumentParser;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * A command that copies a file to provided path.
 * 
 * @author Patrik
 *
 */
public class CopyShellCommand implements ShellCommand {

	/**
	 * Command description
	 */
	private static final List<String> DESCRIPTION = Collections.unmodifiableList(Arrays.asList(
			"copy source destination",
			"Copies the source to destination.",
			"Source must be a file, and if destination represents a file",
			"(whether or not it exists) it is copied to that file,",
			"or if a destination is an existing directory",
			"then the source file is copied to that directory with the same name"
	));
	
	/**
	 * Buffer size when reading and writing files
	 */
	private static final int BUFFER = 4096;

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] parts;
		try {
			parts = ArgumentParser.parseArguments(arguments);
		} catch (IllegalArgumentException ex) {
			env.writeln("Invalid arguments.");
			return ShellStatus.CONTINUE;
		}
		
		if (parts.length != 2) {
			env.writeln("Copy expects 2 parameters.");
			return ShellStatus.CONTINUE;
		}
		
		Path src = env.getCurrentDirectory().resolve(parts[0]);
		Path dest = env.getCurrentDirectory().resolve(parts[1]);
		
		if (!Files.exists(src) || !Files.isRegularFile(src)) {
			env.writeln("Source file does not exist.");
			return ShellStatus.CONTINUE;
		}
		
		if (Files.isDirectory(dest)) {
			dest = dest.resolve(src.getFileName());
		}
		
		try {
			if (src.toRealPath().equals(dest.toRealPath())) {
				env.writeln("Cannot copy the file to the same file.");
				return ShellStatus.CONTINUE;
			}
		} catch (IOException ex) {
		}
		
		if (Files.exists(dest)) {
			env.write(dest + " already exists. Overwrite? [Y for yes or N for no]: ");
			String input = env.readLine().trim();
			if (!input.equalsIgnoreCase("Y")) {
				env.writeln("Aborting copy.");
				return ShellStatus.CONTINUE;
			}
		}
		
		try (InputStream is = new BufferedInputStream(Files.newInputStream(src)); 
				OutputStream os = new BufferedOutputStream(Files.newOutputStream(dest))) {
			byte[] buf = new byte[BUFFER];
			while (true) {
				int read = is.read(buf);
				if (read < 1) break;
				os.write(buf, 0, read);
			}
			os.flush();
		} catch (IOException ex) {
			env.writeln("IO error: " + ex.getMessage());
		}
		
		env.writeln("Successfully copied " + src + " to " + dest + ".");
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		return DESCRIPTION;
	}

	
	
}

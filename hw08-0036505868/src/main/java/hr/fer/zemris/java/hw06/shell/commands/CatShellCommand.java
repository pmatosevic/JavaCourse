package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
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
 * A command that prints the content of a file as a text.
 * 
 * @author Patrik
 *
 */
public class CatShellCommand implements ShellCommand {

	/**
	 * Command description
	 */
	private static final List<String> DESCRIPTION = Collections.unmodifiableList(Arrays.asList(
			"cat filename [charset]",
			"Prints the content of the file \"filename\" as text,",
			"using the specified charset (or the default one if not provided)"
	));
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] parts;
		try {
			parts = ArgumentParser.parseArguments(arguments);
		} catch (IllegalArgumentException ex) {
			env.writeln("Invalid arguments.");
			return ShellStatus.CONTINUE;
		}
		
		if (parts.length < 1 || parts.length > 2) {
			env.writeln("Invalid arguments.");
			return ShellStatus.CONTINUE;
		}
		
		Path file = env.getCurrentDirectory().resolve(parts[0]);
		
		if (!Files.exists(file) || !Files.isRegularFile(file)) {
			env.writeln("File does not exist.");
			return ShellStatus.CONTINUE;
		}
		
		Charset charset;
		if (parts.length == 2) {
			try {
				charset = Charset.forName(parts[1]);
			} catch (IllegalCharsetNameException | UnsupportedCharsetException ex) {
				env.writeln("Unsupported charset.");
				return ShellStatus.CONTINUE;
			}
		} else {
			charset = Charset.defaultCharset();
		}
		
		try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
			while (true) {
				String line = reader.readLine();
				if (line == null) break;
				env.writeln(line);
			}
		} catch (IOException ex) {
			env.writeln("IO error: " + ex.getMessage());
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		return DESCRIPTION;
	}

	
		
	

}

package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
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
 * A command that creates a new directory (including all parent directories if they do not exist).
 * @author Patrik
 *
 */
public class MkdirShellCommand implements ShellCommand {

	/**
	 * Command description
	 */
	private static final List<String> DESCRIPTION = Collections.unmodifiableList(Arrays.asList(
			"mkdir path",
			"Creates a new directory (if it does not exist)",
			"and creates all parent directories which are necessary."
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
		
		if (parts.length != 1) {
			env.writeln("Expected 1 argument.");
			return ShellStatus.CONTINUE;
		}
		
		Path newDir = env.getCurrentDirectory().resolve(parts[0]);
		if (Files.exists(newDir)) {
			env.writeln("Directory already exists.");
			return ShellStatus.CONTINUE;
		}
		
		try {
			Files.createDirectories(newDir);
			env.writeln("Successfully created directory: " + newDir);
		} catch (IOException ex) {
			env.writeln("IO Error: " + ex.getMessage());
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		return DESCRIPTION;
	}

}

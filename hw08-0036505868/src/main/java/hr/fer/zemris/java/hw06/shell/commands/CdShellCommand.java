package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.ArgumentParser;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * A command that changes the current working directory.
 * 
 * @author Patrik
 *
 */
public class CdShellCommand implements ShellCommand {

	/**
	 * Command description
	 */
	private static final List<String> DESCRIPTION = Collections.unmodifiableList(Arrays.asList(
			"cd path",
			"Changes the current directory to path"
	));
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] parts = ArgumentParser.tryParseArguments(arguments, 1, 1, env);
		if (parts == null) {
			return ShellStatus.CONTINUE;
		}
		
		Path path = env.getCurrentDirectory().resolve(parts[0]);
		try {
			env.setCurrentDirectory(path);
			env.writeln("Path was changed to: " + env.getCurrentDirectory());
		} catch (IllegalArgumentException ex) {
			env.writeln(ex.getMessage());
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cd";
	}

	@Override
	public List<String> getCommandDescription() {
		return DESCRIPTION;
	}

	
	
}

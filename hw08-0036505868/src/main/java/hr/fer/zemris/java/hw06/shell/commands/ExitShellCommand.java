package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * A command that terminates the shell.
 * @author Patrik
 *
 */
public class ExitShellCommand implements ShellCommand {

	/**
	 * Command description
	 */
	private static final List<String> DESCRIPTION = Collections.unmodifiableList(Arrays.asList(
			"exit",
			"Exits the shell."
			));
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		env.writeln("Goodbye!");
		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return "exit";
	}

	@Override
	public List<String> getCommandDescription() {
		return DESCRIPTION;
	}

}

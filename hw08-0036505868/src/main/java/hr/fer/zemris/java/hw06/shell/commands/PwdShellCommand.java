package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * A command that prints the current working directory.
 * 
 * @author Patrik
 *
 */
public class PwdShellCommand implements ShellCommand {

	/**
	 * Command description
	 */
	private static final List<String> DESCRIPTION = Collections.unmodifiableList(Arrays.asList(
			"pwd",
			"Prints the current working directory"
	));
	
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		env.writeln(env.getCurrentDirectory().toString());
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "pwd";
	}

	@Override
	public List<String> getCommandDescription() {
		return DESCRIPTION;
	}

}

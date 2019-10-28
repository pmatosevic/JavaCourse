package hr.fer.zemris.java.hw17.trazilica.commands;

import hr.fer.zemris.java.hw17.trazilica.Environment;
import hr.fer.zemris.java.hw17.trazilica.ShellCommand;
import hr.fer.zemris.java.hw17.trazilica.ShellStatus;

/**
 * A command that terminates the shell.
 * 
 * @author Patrik
 *
 */
public class ExitShellCommand implements ShellCommand {
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		env.writeln("Goodbye!");
		return ShellStatus.TERMINATE;
	}

}

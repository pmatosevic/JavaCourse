package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * Represents a shell command.
 * 
 * @author Patrik
 *
 */
public interface ShellCommand {

	/**
	 * Executes the command on given environment and with given arguments.
	 * @param env environment
	 * @param arguments command arguments
	 * @return shell status
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * Returns the command name.
	 * @return the command name
	 */
	String getCommandName();
	
	/**
	 * Returns the list of lines that describe the usage of the command.
	 * @return the list of lines that describe the usage of the command
	 */
	List<String> getCommandDescription();
	
}

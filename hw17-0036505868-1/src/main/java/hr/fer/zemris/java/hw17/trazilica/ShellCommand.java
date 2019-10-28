package hr.fer.zemris.java.hw17.trazilica;

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
	
}

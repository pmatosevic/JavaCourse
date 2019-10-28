package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * A command that prints the list of all available charsets on the platform.
 * @author Patrik
 *
 */
public class CharsetsShellCommand implements ShellCommand {

	/**
	 * Command description
	 */
	private static final List<String> DESCRIPTION = Collections.unmodifiableList(Arrays.asList(
			"charsets",
			"Prints all available charsets on this JVM platform."
		));
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		env.writeln("Available charsets: ");
		for (String charset : Charset.availableCharsets().keySet()) {
			env.writeln(charset);
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "charsets";
	}

	@Override
	public List<String> getCommandDescription() {
		return DESCRIPTION;
	}

}

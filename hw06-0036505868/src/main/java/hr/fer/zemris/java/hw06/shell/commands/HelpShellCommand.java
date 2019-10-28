package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * A command that displays the list of all commands or help for the specific command.
 * 
 * @author Patrik
 *
 */
public class HelpShellCommand implements ShellCommand {

	/**
	 * Command description
	 */
	private static final List<String> DESCRIPTION = Collections.unmodifiableList(Arrays.asList(
			"help [command_name]",
			"If command name is given prints help for the command",
			"If not, a list of all commands is printed."
		));
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String name = arguments.trim();
		if (!name.isEmpty()) {
			ShellCommand command = env.commands().get(name);
			if (command == null) {
				env.writeln("Command does not exist.");
				return ShellStatus.CONTINUE;
			}
			
			//env.writeln("Description for command " + name + ":");
			command.getCommandDescription().forEach(line -> env.writeln(line));
		} else {
			env.writeln("All commands:");
			env.commands().forEach((str, comm) -> env.writeln(str));
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		return DESCRIPTION;
	}

	
	
}

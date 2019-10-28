package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * A command that prints all directories from the stack.
 * 
 * @author Patrik
 *
 */
public class ListdShellCommand implements ShellCommand {
	
	/**
	 * Command description
	 */
	private static final List<String> DESCRIPTION = Collections.unmodifiableList(Arrays.asList(
			"listd",
			"Lists all paths on the stack."
	));
	
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!arguments.trim().isEmpty()) {
			env.writeln("Command takes no arguments.");
			return ShellStatus.CONTINUE;
		}
		
		Object obj = env.getSharedData(PushdShellCommand.SHARED_STACK_KEY);
		if (obj == null) {
			env.writeln("There are no stored directories.");
			return ShellStatus.CONTINUE;
		}
		
		@SuppressWarnings("unchecked")
		Deque<Path> stack = (Deque<Path>) obj;
		if (stack.isEmpty()) {
			env.writeln("There are no stored directories.");
			return ShellStatus.CONTINUE;
		}
		
		for (Path p : stack) {
			env.writeln(p.toString());
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "listd";
	}

	@Override
	public List<String> getCommandDescription() {
		return DESCRIPTION;
	}

	
	
}

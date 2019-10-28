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
 * A command that pops the stack, and leaves the current directory.
 * @author Patrik
 *
 */
public class DropdShellCommand implements ShellCommand {

	/**
	 * Command description
	 */
	private static final List<String> DESCRIPTION = Collections.unmodifiableList(Arrays.asList(
			"dropd",
			"Pops the stack and leaves the current directory."
			));
	
	
	@SuppressWarnings("unchecked")
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!arguments.trim().isEmpty()) {
			env.writeln("Command takes no arguments.");
			return ShellStatus.CONTINUE;
		}
		
		Object obj = env.getSharedData(PushdShellCommand.SHARED_STACK_KEY);
		if (obj == null) {
			env.writeln("The stack is empty.");
			return ShellStatus.CONTINUE;
		}
		
		Deque<Path> stack = (Deque<Path>) obj;
		if (stack.isEmpty()) {
			env.writeln("The stack is empty.");
			return ShellStatus.CONTINUE;
		}
		
		stack.pop();
		env.writeln("Removed a directory.");
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "dropd";
	}

	@Override
	public List<String> getCommandDescription() {
		return DESCRIPTION;
	}
	
}

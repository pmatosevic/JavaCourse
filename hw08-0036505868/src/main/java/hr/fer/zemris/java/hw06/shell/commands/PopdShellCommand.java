package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * A command that sets the working directory as the one on top of the stack, and pops it.
 * 
 * @author Patrik
 *
 */
public class PopdShellCommand implements ShellCommand {
	
	/**
	 * Command description
	 */
	private static final List<String> DESCRIPTION = Collections.unmodifiableList(Arrays.asList(
			"popd",
			"Pops the stack and sets the current directory as the one on the top."
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
		
		Path path = stack.pop();
		if (Files.isDirectory(path)) {
			env.setCurrentDirectory(path);
			env.writeln("Changed path to: " + path);
		} else {
			env.writeln(path + " does not exist anymore. Leaving current directory.");
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "popd";
	}

	@Override
	public List<String> getCommandDescription() {
		return DESCRIPTION;
	}

}

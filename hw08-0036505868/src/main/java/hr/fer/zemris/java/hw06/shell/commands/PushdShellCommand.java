package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.ArgumentParser;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * A command that pushes the current directory to a shared stack, and changes it.
 * @author Patrik
 *
 */
public class PushdShellCommand implements ShellCommand {

	/**
	 * Shared key for the stack (a {@link Deque} is used for storing elements)
	 */
	public final static String SHARED_STACK_KEY = "cdstack";
	
	/**
	 * Command description
	 */
	private static final List<String> DESCRIPTION = Collections.unmodifiableList(Arrays.asList(
			"pushd path",
			"Pushes the current path to the stack and sets the path as the current."
			));
	
	
	@SuppressWarnings("unchecked")
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] parts = ArgumentParser.tryParseArguments(arguments, 1, 1, env);
		if (parts == null) {
			return ShellStatus.CONTINUE;
		}
		
		Path path = env.getCurrentDirectory().resolve(parts[0]);
		if (!Files.isDirectory(path)) {
			env.writeln("Directory does not exist.");
			return ShellStatus.CONTINUE;
		}
		
		Object obj = env.getSharedData(SHARED_STACK_KEY);
		Deque<Path> stack;
		if (obj == null) {
			stack = new ArrayDeque<Path>();
			env.setSharedData(SHARED_STACK_KEY, stack);
		} else {
			stack = (Deque<Path>) obj;
		}
		
		stack.push(env.getCurrentDirectory());
		env.setCurrentDirectory(path);
		env.writeln("Path was changed to: " + path.toString());
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "pushd";
	}

	@Override
	public List<String> getCommandDescription() {
		return DESCRIPTION;
	}

}

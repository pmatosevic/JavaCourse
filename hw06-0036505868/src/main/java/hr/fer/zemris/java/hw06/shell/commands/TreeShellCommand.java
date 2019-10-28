package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.ArgumentParser;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * A command that prints the tree structure of a directory.
 * 
 * @author Patrik
 *
 */
public class TreeShellCommand implements ShellCommand {

	/**
	 * Command description
	 */
	private static final List<String> DESCRIPTION = Collections.unmodifiableList(Arrays.asList(
			"tree directory",
			"Prints a tree of child directories and files of \"directory\""
		));
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] parts;
		try {
			parts = ArgumentParser.parseArguments(arguments);
		} catch (IllegalArgumentException ex) {
			env.writeln("Invalid arguments.");
			return ShellStatus.CONTINUE;
		}
		
		if (parts.length != 1) {
			env.writeln("Expected 1 argument.");
			return ShellStatus.CONTINUE;
		}
		
		Path start = Paths.get(parts[0]);
		
		if (!Files.exists(start)) {
			env.writeln("The directory does not exist.");
			return ShellStatus.CONTINUE;
		}
		if (!Files.isDirectory(start)) {
			env.writeln("Given path is not a directory.");
			return ShellStatus.CONTINUE;
		}
		
		try {
			Files.walkFileTree(start, new TreePrinter(env));
		} catch (IOException ex) {
			env.writeln("IO Error: " + ex.getMessage());
		}
		
		return ShellStatus.CONTINUE;
	}
	
	
	
	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		return DESCRIPTION;
	}
	
	
	/**
	 * Visitor for printing file tree
	 * 
	 * @author Patrik
	 *
	 */
	private static class TreePrinter extends SimpleFileVisitor<Path> {
		
		/**
		 * Current level
		 */
		private int level = 0;
		
		/**
		 * The environment
		 */
		private Environment env;
		
		/**
		 * Creates a new {@code TreePainter}
		 * @param env environment
		 */
		private TreePrinter(Environment env) {
			this.env = env;
		}
		
		
		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			env.writeln(" ".repeat(2 * level) + 
					((level == 0) ? dir.toAbsolutePath() : dir.getFileName()));
			level++;
			return FileVisitResult.CONTINUE;
		}
		
		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			level--;
			return FileVisitResult.CONTINUE;
		}
		
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			env.writeln(" ".repeat(2 * level) + file.getFileName());
			return FileVisitResult.CONTINUE;
		}
		
		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}
		
	}

	
	
}

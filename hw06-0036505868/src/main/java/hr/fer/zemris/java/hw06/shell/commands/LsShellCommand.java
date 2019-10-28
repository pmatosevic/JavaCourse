package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.ArgumentParser;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * A command that print the listing of files in a directory including their attributes and size.
 * 
 * @author Patrik
 *
 */
public class LsShellCommand implements ShellCommand {

	/**
	 * Command description
	 */
	private static final List<String> DESCRIPTION = Collections.unmodifiableList(Arrays.asList(
			"ls directory",
			"Prints a directory listing (not recursive) for directory \"directory\""
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
		
		Path dir = Paths.get(parts[0]);
		
		if (!Files.exists(dir)) {
			env.writeln("The directory does not exist.");
			return ShellStatus.CONTINUE;
		}
		if (!Files.isDirectory(dir)) {
			env.writeln("Given path is not a directory.");
			return ShellStatus.CONTINUE;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try (DirectoryStream<Path> children = Files.newDirectoryStream(dir)) {
			env.writeln("Directory listing for: " + dir.toAbsolutePath());
			
			for (Path child : children) {
				BasicFileAttributeView faView = Files.getFileAttributeView(child, BasicFileAttributeView.class,
						LinkOption.NOFOLLOW_LINKS);
				BasicFileAttributes attrs = faView.readAttributes();
				env.write(attrs.isDirectory() ? "d" : "-");
				env.write(Files.isReadable(child) ? "r" : "-");
				env.write(Files.isWritable(child) ? "w" : "-");
				env.write(Files.isExecutable(child) ? "x" : "-");
				env.write(String.format(" %10d ", attrs.size()));
				String formattedDateTime = sdf.format(new Date(attrs.creationTime().toMillis()));
				env.write(formattedDateTime);
				env.write(" " + child.getFileName());
				env.writeln("");
			}
		} catch (IOException ex) {
			env.writeln("IO Error: " + ex.getMessage());
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		return DESCRIPTION;
	}

}

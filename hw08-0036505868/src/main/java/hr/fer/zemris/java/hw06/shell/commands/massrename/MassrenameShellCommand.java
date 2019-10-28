package hr.fer.zemris.java.hw06.shell.commands.massrename;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import hr.fer.zemris.java.hw06.shell.ArgumentParser;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * A command that performs a massive renaming of files selected by a regular expression.
 * 
 * @author Patrik
 *
 */
public class MassrenameShellCommand implements ShellCommand {

	/**
	 * A map of sub commands
	 */
	private static Map<String, SubCommand> subCommands = new HashMap<>();
	
	static {
		subCommands.put("filter", new FilterCommand());
		subCommands.put("groups", new GroupsCommand());
		subCommands.put("show", new ShowCommand());
		subCommands.put("execute", new ExecuteCommand());
	}
	
	/**
	 * Command description
	 */
	private static final List<String> DESCRIPTION = Collections.unmodifiableList(Arrays.asList(
			"massrename dir1 dir2 cmd mask [other]",
			"cmd:",
			"	filter: prints the files selected by the mask",
			"	groups: prints the groups for every selected file",
			"	show: shows selected filenames and new names",
			"	execute: renames selected file"
	));
	
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] parts = ArgumentParser.tryParseArguments(arguments, 4, 5, env);
		if (parts == null) {
			return ShellStatus.CONTINUE;
		}
		
		Path src = env.getCurrentDirectory().resolve(parts[0]);
		Path dest = env.getCurrentDirectory().resolve(parts[1]);
		if (!Files.isDirectory(src) || !Files.isDirectory(dest)) {
			env.writeln("Directory does not exist.");
			return ShellStatus.CONTINUE;
		}
		
		SubCommand subCommand = subCommands.get(parts[2]);
		if (subCommand == null) {
			env.writeln("Invalid action for massrename.");
			return ShellStatus.CONTINUE;
		}
		
		NameBuilder builder = null;
		if (parts[2].equals("show") || parts[2].equals("execute")) {
			if (parts.length != 5) {
				env.writeln("Expected new name pattern.");
				return ShellStatus.CONTINUE;
			}
			try {
				NameBuilderParser parser = new NameBuilderParser(parts[4]);
				builder = parser.getNameBuilder();
			} catch (IllegalArgumentException e) {
				env.writeln("Invalid new name pattern: " + e.getMessage());
				return ShellStatus.CONTINUE;
			}
		}
		
		try {
			List<FilterResult> files = filter(src, parts[3]);
			for(FilterResult file : files) {
				subCommand.accept(file, env, Paths.get(parts[0]), Paths.get(parts[1]), builder);
			}
		} catch (IOException e) {
			env.writeln("IO error: " + e.getMessage());
		} catch (IllegalArgumentException e) {
			env.writeln(e.getMessage());
		}
		
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * Returns a list of results of filtering all files in the directory.
	 * 
	 * @param dir directory
	 * @param pattern regular expression
	 * @return list of results of filtering all files in the directory
	 * @throws IOException in case of an IO error
	 * @throws IllegalArgumentException in case of an invalid expression
	 */
	private static List<FilterResult> filter(Path dir, String pattern) throws IOException {
		List<FilterResult> result = new ArrayList<>();
		Pattern regex;
		try {
			regex = Pattern.compile(pattern, Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
		} catch (PatternSyntaxException e) {
			throw new IllegalArgumentException("Invalid regular expression: " + e.getMessage());
		}
		
		try (DirectoryStream<Path> children = Files.newDirectoryStream(dir)) {
			for (Path child : children) {
				if (!Files.isRegularFile(child)) continue;
				
				Matcher matcher = regex.matcher(child.getFileName().toString());
				if (matcher.find()) {
					int count = matcher.groupCount() + 1;
					String[] groups = new String[count];
					for (int i=0; i<count; i++) {
						groups[i] = matcher.group(i);
					}
					result.add(new FilterResult(groups));
				}
 			}
		}
		return result;
	}

	@Override
	public String getCommandName() {
		return "massrename";
	}

	@Override
	public List<String> getCommandDescription() {
		return DESCRIPTION;
	}
	
	/**
	 * Filter command
	 * @author Patrik
	 *
	 */
	public static class FilterCommand implements SubCommand {
		@Override
		public void accept(FilterResult fresult, Environment env, Path src, Path dest, NameBuilder builder) {
			env.writeln(fresult.toString());
		}
	}
	
	/**
	 * Groups command
	 * @author Patrik
	 *
	 */
	public static class GroupsCommand implements SubCommand {
		@Override
		public void accept(FilterResult fresult, Environment env, Path src, Path dest, NameBuilder builder) {
			int n = fresult.numberOfGroups();
			env.write(fresult.toString());
			for (int i=0; i<n; i++) {
				env.write(" " + i + ": " + fresult.group(i));
			}
			env.writeln("");
		}
	}
	
	/**
	 * Show command
	 * @author Patrik
	 *
	 */
	public static class ShowCommand implements SubCommand {
		@Override
		public void accept(FilterResult fresult, Environment env, Path src, Path dest, NameBuilder builder) {
			StringBuilder sb = new StringBuilder();
			builder.execute(fresult, sb);
			String newName = sb.toString();
			env.writeln(fresult.toString() + " => " + newName);
		}
	}
	
	/**
	 * Execute command
	 * @author Patrik
	 *
	 */
	public static class ExecuteCommand implements SubCommand {
		@Override
		public void accept(FilterResult fresult, Environment env, Path src, Path dest, NameBuilder builder) throws IOException {
			StringBuilder sb = new StringBuilder();
			builder.execute(fresult, sb);
			String newName = sb.toString();
			
			Path srcFile = env.getCurrentDirectory().resolve(src).resolve(fresult.toString());
			Path destFile = env.getCurrentDirectory().resolve(dest).resolve(newName);
			Files.move(srcFile, destFile);
			env.writeln(src.resolve(fresult.toString()) + " => " + dest.resolve(newName));
		}
	}
	
	
	
}

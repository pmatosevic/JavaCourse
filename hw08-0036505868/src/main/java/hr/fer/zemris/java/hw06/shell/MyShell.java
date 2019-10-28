package hr.fer.zemris.java.hw06.shell;

import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.DropdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ListdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.PopdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.PushdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.PwdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.massrename.MassrenameShellCommand;

/**
 * A program that simulates a shell.
 * Supports copying, printing and dumping files in hex format,
 * listing and making new directories.
 * Enter "help" to see a list of all available commands.
 * The shell exists on entering "exit".
 * 
 * @author Patrik
 *
 */
public class MyShell {
	
	/**
	 * A map of all commands
	 */
	private static SortedMap<String, ShellCommand> commands = new TreeMap<>();
	
	static {
		commands.put("exit", new ExitShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("cat", new CatShellCommand());
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("symbol", new SymbolShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("help", new HelpShellCommand());
		
		commands.put("pwd", new PwdShellCommand());
		commands.put("cd", new CdShellCommand());
		commands.put("pushd", new PushdShellCommand());
		commands.put("popd", new PopdShellCommand());
		commands.put("listd", new ListdShellCommand());
		commands.put("dropd", new DropdShellCommand());
		commands.put("massrename", new MassrenameShellCommand());
	}
	
	
	/**
	 * Program entry point
	 * @param args command-line arguments (not used)
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Environment evt = new MyEnvironment(sc, commands);
		ShellStatus status = ShellStatus.CONTINUE;
		
		evt.writeln("Welcome to MyShell v 1.0");
		
		while (status != ShellStatus.TERMINATE) {
			try {
				String input = readLineOrLines(evt).trim();
				String[] parts = input.split("\\s+", 2);
				String commandName = parts[0];
				String arguments = parts.length == 1 ? "" : parts[1];
				
				ShellCommand command = commands.get(commandName);
				
				if (command != null) {
					status = command.executeCommand(evt, arguments);
				} else {
					evt.writeln("Invalid command.");
				}
			} catch (ShellIOException ex) {
				break;
			}
		} 
	}
	
	/**
	 * Reads the next command from user and handles multiple lines.
	 * 
	 * @param env shell environment
	 * @return the whole user input
	 */
	private static String readLineOrLines(Environment env) {
		StringBuilder sb = new StringBuilder();
		env.write(env.getPromptSymbol() + " ");
		while (true) {
			String input = env.readLine();
			if (input.length() > 0 && input.charAt(input.length() - 1) == env.getMorelinesSymbol()) {
				sb.append(input.substring(0, input.length() - 1));
				env.write(env.getMultilineSymbol() + " ");
			} else {
				sb.append(input);
				break;
			}
		}
		return sb.toString();
	}

	
	
}

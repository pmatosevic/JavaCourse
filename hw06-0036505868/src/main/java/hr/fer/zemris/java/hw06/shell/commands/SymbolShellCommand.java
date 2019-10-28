package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;


/**
 * A command that prints current or changes shell symbols (prompt, morelines, multiline).
 * 
 * @author Patrik
 *
 */
public class SymbolShellCommand implements ShellCommand {
	
	/**
	 * Command description
	 */
	private static final List<String> DESCRIPTION = Collections.unmodifiableList(Arrays.asList(
			"symbol PROMPT|MULTILINE|MORELINES [new_symbol]",
			"Prints the current PROMPT, MULTILINE or MORELINES symbol",
			"or if the symbol is provided, changes it with the provided one"
		));
	
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] parts = arguments.split("\\s+");
		if (parts.length == 0) {
			env.writeln("No symbol provided");
			return ShellStatus.CONTINUE;
		}
		
		char oldSym;
		
		switch (parts[0]) {
		case "PROMPT":
			oldSym = env.getPromptSymbol();
			if (parts.length == 1) {
				env.writeln("Symbol for PROMPT is '" + oldSym + "'");
			} else if (parts.length == 2 && parts[1].length() == 1) {
				char newSym = parts[1].charAt(0);
				env.setPromptSymbol(newSym);
				env.writeln(String.format("Symbol for PROMPT changed from '%s' to '%s'", oldSym, newSym));
			} else {
				env.writeln("Invalid character.");
			}
			break;
			
		case "MORELINES":
			oldSym = env.getMorelinesSymbol();
			if (parts.length == 1) {
				env.writeln("Symbol for MORELINES is '" + oldSym + "'");
			} else if (parts.length == 2 && parts[1].length() == 1) {
				char newSym = parts[1].charAt(0);
				env.setMorelinesSymbol(newSym);
				env.writeln(String.format("Symbol for MORELINES changed from '%s' to '%s'", oldSym, newSym));
			} else {
				env.writeln("Invalid character.");
			}
			break;
			
		case "MULTILINE":
			oldSym = env.getMultilineSymbol();
			if (parts.length == 1) {
				env.writeln("Symbol for MULTILINE is '" + oldSym + "'");
			} else if (parts.length == 2 && parts[1].length() == 1) {
				char newSym = parts[1].charAt(0);
				env.setMultilineSymbol(newSym);
				env.writeln(String.format("Symbol for MULTILINE changed from '%s' to '%s'", oldSym, newSym));
			} else {
				env.writeln("Invalid character.");
			}
			break;
		default:
			env.writeln("Invlid symbol name.");
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		return DESCRIPTION;
	}

	
	
}

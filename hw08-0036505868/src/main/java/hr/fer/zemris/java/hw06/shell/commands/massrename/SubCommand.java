package hr.fer.zemris.java.hw06.shell.commands.massrename;

import java.io.IOException;
import java.nio.file.Path;

import hr.fer.zemris.java.hw06.shell.Environment;

/**
 * Represents a sub command.
 * 
 * @author Patrik
 *
 */
@FunctionalInterface
public interface SubCommand {

	/**
	 * Accepts one result.
	 * @param fresult result
	 * @param env environment
	 * @param src source
	 * @param dest destination
	 * @param builder name builder
	 * @throws IOException in case of IO error
	 */
	void accept(FilterResult fresult, Environment env, Path src, Path dest, NameBuilder builder) throws IOException;
	
}

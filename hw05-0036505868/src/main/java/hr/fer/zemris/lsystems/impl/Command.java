package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Represents a command.
 * 
 * @author Patrik
 *
 */
public interface Command {

	/**
	 * Executes this command with given context and on given painter
	 * 
	 * @param ctx     current context
	 * @param painter painter used to draw
	 */
	void execute(Context ctx, Painter painter);
	
}

package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * A command that pushes a copy of the current state to the stack.
 * @author Patrik
 *
 */
public class PushCommand implements Command {

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState().copy();
		ctx.pushState(state);
	}

}

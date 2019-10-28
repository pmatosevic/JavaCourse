package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * A command that translates the current turtle position without drawing a line.
 * @author Patrik
 *
 */
public class SkipCommand implements Command {

	/**
	 * The step length
	 */
	private double step;

	/**
	 * Creates a new {@code SkipCommand}
	 * @param step step length
	 */
	public SkipCommand(double step) {
		this.step = step;
	}
	
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		Vector2D offset = state.getDirection().scaled(state.getUnitLength() * step);
		state.getPosition().translate(offset);
	}

	
	
}

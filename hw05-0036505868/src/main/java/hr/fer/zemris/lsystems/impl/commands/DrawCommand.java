package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * A command that draws a line.
 * @author Patrik
 *
 */
public class DrawCommand implements Command {
	
	/**
	 * Line length in steps
	 */
	private double step;

	/**
	 * Creates a new {@code DrawCommand}
	 * @param step line length
	 */
	public DrawCommand(double step) {
		this.step = step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		Vector2D start = state.getPosition().copy();
		Vector2D offset = state.getDirection().scaled(state.getUnitLength() * step);
		state.getPosition().translate(offset);
		Vector2D end = state.getPosition();
		painter.drawLine(start.getX(), start.getY(), end.getX(), end.getY(), state.getColor(), 1f);
	}
	
}

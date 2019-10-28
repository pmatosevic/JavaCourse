package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * A command that changes the turtle direction
 * @author Patrik
 *
 */
public class RotateCommand implements Command {

	/**
	 * The angle
	 */
	private double angle;
	
	/**
	 * Creates a new {@code RotateCommand}
	 * @param angle angle in radians
	 */
	public RotateCommand(double angle) {
		this.angle = angle;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().getDirection().rotate(angle);
	}
	
	
}

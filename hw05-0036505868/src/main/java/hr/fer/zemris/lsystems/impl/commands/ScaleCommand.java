package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * A command that scales the step length.
 * @author Patrik
 *
 */
public class ScaleCommand implements Command {

	/**
	 * The factor
	 */
	private double factor;

	/**
	 * Creates a new {@code ScaleCommand}
	 * @param factor factor to scale with
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}
	
	@Override
	public void execute(Context ctx, Painter painter) {
		double len = ctx.getCurrentState().getUnitLength() * factor;
		ctx.getCurrentState().setUnitLength(len);
	}

}

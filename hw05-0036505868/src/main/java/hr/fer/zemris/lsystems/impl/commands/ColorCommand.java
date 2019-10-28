package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;


/**
 * A command that changes the color.
 * @author Patrik
 *
 */
public class ColorCommand implements Command {

	/**
	 * The color
	 */
	private Color color;
		
	/**
	 * Creates a new {@code ColorCommand}
	 * @param color color
	 */
	public ColorCommand(Color color) {
		this.color = color;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setColor(color);
	}

}

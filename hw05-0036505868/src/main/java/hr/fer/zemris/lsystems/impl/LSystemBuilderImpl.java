package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;
import hr.fer.zemris.math.Vector2D;

/**
 * An implementation of the {@link LSystemBuilder}. Can create {@link LSystem} objects.
 * 
 * @author Patrik
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder {

	/**
	 * Registered commands
	 */
	private Dictionary<Character, Command> commands = new Dictionary<>();
	
	/**
	 * Registered productions
	 */
	private Dictionary<Character, String> productions = new Dictionary<>();
	
	/**
	 * Starting unit length
	 */
	private double unitLength = 0.1;
	
	/**
	 * Starting unit length scaler
	 */
	private double unitLengthDegreeScaler = 1;
	
	/**
	 * Starting position
	 */
	private Vector2D origin = new Vector2D(0, 0);
	
	/**
	 * Starting angle
	 */
	private double angle = 0;
	
	/**
	 * Starting axiom
	 */
	private String axiom = "";
	
	/**
	 * Returns the new {@link LSystem} with parameters from this builder.
	 * @return the new {@link LSystem} with parameters from this builder
	 */
	@Override
	public LSystem build() {
		return new LSystemImpl();
	}

	/**
	 * Reads given lines and according to them sets the parameters.
	 * 
	 * @param lines lines from the definition of a LSystem
	 * @throws IllegalArgumentException in case of an error during parsing given
	 *                                  lines (includes {@link NumberFormatException})
	 * @return this builder
	 */
	@Override
	public LSystemBuilder configureFromText(String[] lines) {
		for (String line : lines) {
			String trimmed = line.trim();
			if (trimmed.isEmpty()) {
				continue;
			}
			
			String[] parts = trimmed.split("\\s+", 2);
			if (parts.length < 2) {
				throw new IllegalArgumentException("Invalid line: " + line);
			}
			
			String args = parts[1];
			switch(parts[0]) {
			case "origin":
				String[] nums = args.split("\\s+", 2);
				if (nums.length != 2) {
					throw new IllegalArgumentException("Invalid line: " + line);
				}
				double x = Double.parseDouble(nums[0]);
				double y = Double.parseDouble(nums[1]);
				setOrigin(x, y);
				break;
				
			case "angle":
				setAngle(deg2Rad(Double.parseDouble(args)));
				break;
				
			case "unitLength":
				setUnitLength(Double.parseDouble(args));
				break;
				
			case "unitLengthDegreeScaler":
				double scaler;
				if (args.contains("/")) {
					String[] fract = args.split("\\s*/\\s*", 2);
					double nom = Double.parseDouble(fract[0]);
					double denom = Double.parseDouble(fract[1]);
					scaler = nom / denom;
				} else {
					scaler = Double.parseDouble(args);
				}
				
				setUnitLengthDegreeScaler(scaler);
				break;
				
			case "command":
				String[] comm = args.split("\\s+", 2);
				if (comm[0].length() != 1) {
					throw new IllegalArgumentException("Symbol must contain 1 char.");
				}
				registerCommand(comm[0].charAt(0), comm[1]);
				break;
				
			case "axiom":
				setAxiom(args);
				break;
				
			case "production":
				String[] prod = args.split("\\s+");
				if (prod[0].length() != 1) {
					throw new IllegalArgumentException("Symbol must contain 1 char.");
				}
				registerProduction(prod[0].charAt(0), prod[1]);
			}
		}
		
		return this;
	}

	/**
	 * Registers a symbol with a command
	 * 
	 * @param symbol symbol
	 * @param action command to execute
	 * @throws IllegalArgumentException in case of an invalid command or invalid
	 *                                  number format (includes {@link NumberFormatException})
	 * @return this builder
	 */
	@Override
	public LSystemBuilder registerCommand(char symbol, String action) {
		String[] parts = action.split("\\s+");
		
		Command command;
		switch (parts[0]) {
		case "draw":
			command = new DrawCommand(Double.parseDouble(parts[1]));
			break;
		case "skip":
			command = new SkipCommand(Double.parseDouble(parts[1]));
			break;
		case "scale":
			command = new ScaleCommand(Double.parseDouble(parts[1]));
			break;
		case "rotate":
			command = new RotateCommand(deg2Rad(Double.parseDouble(parts[1])));
			break;
		case "push":
			command = new PushCommand();
			break;
		case "pop":
			command = new PopCommand();
			break;
		case "color":
			Color color = Color.decode("0x" + parts[1]);
			command = new ColorCommand(color);
			break;
		default:
			throw new IllegalArgumentException("Illegal command.");
		}
		commands.put(symbol, command);
		
		return this;
	}

	/**
	 * Registers a symbol with given production.
	 * 
	 * @param symbol     symbol
	 * @param production production
	 * @return this builder
	 */
	@Override
	public LSystemBuilder registerProduction(char symbol, String production) {
		productions.put(symbol, production);
		return this;
	}

	/**
	 * Sets the initial angle
	 * 
	 * @param angle initial angle in radians
	 * @return this builder
	 */
	@Override
	public LSystemBuilder setAngle(double angle) {
		this.angle = angle;
		return this;
	}

	/**
	 * Sets the initial axiom
	 * 
	 * @param axiom initial axiom
	 * @return this builder
	 */
	@Override
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = axiom;
		return this;
	}

	/**
	 * Sets the initial position
	 * 
	 * @param x x component
	 * @param y y component
	 * @return this builder
	 */
	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		origin = new Vector2D(x, y);
		return this;
	}

	/**
	 * Sets the initial unit length
	 * 
	 * @param unitLength initial unit length
	 * @return this builder
	 */
	@Override
	public LSystemBuilder setUnitLength(double unitLength) {
		this.unitLength = unitLength;
		return this;
	}

	/**
	 * Sets the unit length degree scaler
	 * 
	 * @param unitLengthDegreeScaler unit length degree scaler
	 * @return this builder
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double unitLengthDegreeScaler) {
		this.unitLengthDegreeScaler = unitLengthDegreeScaler;
		return this;
	}
	
	
	/**
	 * A concrete implementation of a {@link LSystem} that can draw a LSystem on a given {@link Painter}
	 * 
	 * @author Patrik
	 *
	 */
	private class LSystemImpl implements LSystem {

		/**
		 * Draws a LSystem on a given painter and on given level.
		 * 
		 * @param level   level of the LSystem
		 * @param painter {@link Painter} to draw on
		 */
		@Override
		public void draw(int level, Painter painter) {
			Context ctx = new Context();
			
			Vector2D position = origin.copy();
			Vector2D direction = new Vector2D(1, 0);
			direction.rotate(angle);
			double levelUnit = unitLength * Math.pow(unitLengthDegreeScaler, level);
			TurtleState state = new TurtleState(position, direction, Color.BLACK, levelUnit);
			ctx.pushState(state);
			
			char[] sequence = generate(level).toCharArray();
			for (int pos = 0; pos < sequence.length; pos++) {
				Command command = commands.get(sequence[pos]);
				if (command == null) {
					continue;
				}
				
				command.execute(ctx, painter);
 			}
		}

		/**
		 * Generates a string that defines given level of a LSystem
		 * 
		 * @param level level
		 * @return generated string
		 */
		@Override
		public String generate(int level) {
			String generated = axiom;
			
			for (int iter = 0; iter < level; iter++) {
				StringBuilder sb = new StringBuilder();
				
				for (int pos = 0, len = generated.length(); pos < len; pos++) {
					char ch = generated.charAt(pos);
					String production = productions.get(Character.valueOf(ch));
					if (production == null) {
						sb.append(ch);
					} else {
						sb.append(production);
					}
				}
				
				generated = sb.toString();
			}
			
			return generated;
		}
		
	}
	
	/**
	 * Converts degrees to radians
	 * 
	 * @param degrees
	 * @return radians
	 */
	private double deg2Rad(double degrees) {
		return Math.PI * degrees / 180.0;
	}
	
	

}

package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.math.Vector2D;

/**
 * Represent the state of a fictional turtle.
 * 
 * @author Patrik
 *
 */
public class TurtleState {

	/**
	 * Current position
	 */
	private Vector2D position;
	
	/**
	 * Current direction (unit vector in the direction the turtle is heading)
	 */
	private Vector2D direction;
	
	/**
	 * Current color
	 */
	private Color color;
	
	/**
	 * Current unit length
	 */
	private double unitLength;
	
	
	
	/**
	 * Creates a new {@code TurtleState} with given parameters.
	 * 
	 * @param position   initial position
	 * @param direction  unit vector representing a direction
	 * @param color      color
	 * @param stepLength step length
	 */
	public TurtleState(Vector2D position, Vector2D direction, Color color, double unitLength) {
		this.position = position;
		this.direction = direction;
		this.color = color;
		this.unitLength = unitLength;
	}


	/**
	 * Returns a copy of the current state
	 * @return a copy of the current state
	 */
	public TurtleState copy() {
		return new TurtleState(position.copy(), direction.copy(), color, unitLength);
	}

	/**
	 * Returns the positions
	 * @return the positions
	 */
	public Vector2D getPosition() {
		return position;
	}

	/**
	 * Returns the unit vector of the direction
	 * @return the unit vector of the direction
	 */
	public Vector2D getDirection() {
		return direction;
	}

	/**
	 * Returns the color
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Returns the unit length
	 * @return the unit length
	 */
	public double getUnitLength() {
		return unitLength;
	}
	
	/**
	 * Sets the unit length
	 * @param unitLength the unit length
	 */
	public void setUnitLength(double unitLength) {
		this.unitLength = unitLength;
	}

	/**
	 * Sets the color
	 * @param color the color
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	
	
	
	
}

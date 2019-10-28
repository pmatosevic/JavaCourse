package hr.fer.zemris.java.hw17.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * An interface that represents a tool.
 * @author Patrik
 *
 */
public interface Tool {
	
	/**
	 * Called on a mouse press
	 * @param e mouse event
	 */
	public void mousePressed(MouseEvent e);

	/**
	 * Called on a mouse release
	 * @param e mouse event
	 */
	public void mouseReleased(MouseEvent e);

	/**
	 * Called on a mouse click
	 * @param e mouse event
	 */
	public void mouseClicked(MouseEvent e);

	/**
	 * Called on a mouse move
	 * @param e mouse event
	 */
	public void mouseMoved(MouseEvent e);

	/**
	 * Called on a mouse drag
	 * @param e mouse event
	 */
	public void mouseDragged(MouseEvent e);

	/**
	 * Paints current object to the graphics object
	 * @param g2d graphics object
	 */
	public void paint(Graphics2D g2d);
	
}
package hr.fer.zemris.java.hw17;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.function.Supplier;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw17.model.DrawingModel;
import hr.fer.zemris.java.hw17.model.DrawingModelListener;
import hr.fer.zemris.java.hw17.tools.Tool;
import hr.fer.zemris.java.hw17.visitor.GeometricalObjectPainter;

/**
 * The component that draws the geometrical object on screen.
 * 
 * @author Patrik
 *
 */
public class JDrawingCanvas extends JComponent {

	private static final long serialVersionUID = 1L;

	/**
	 * The drawing model
	 */
	private DrawingModel model;
	
	/**
	 * Supplier for current tool
	 */
	private Supplier<Tool> currentTool;
	
	/**
	 * Creates a new canvas
	 * @param drawingModel model
	 * @param currentTool current tool
	 */
	public JDrawingCanvas(DrawingModel drawingModel, Supplier<Tool> currentTool) {
		this.model = drawingModel;
		this.currentTool = currentTool;
		
		drawingModel.addDrawingModelListener(new DrawingModelListener() {
			@Override
			public void objectsRemoved(DrawingModel source, int index0, int index1) {
				repaint();
			}
			@Override
			public void objectsChanged(DrawingModel source, int index0, int index1) {
				repaint();
			}
			@Override
			public void objectsAdded(DrawingModel source, int index0, int index1) {
				repaint();
			}
		});
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		
		GeometricalObjectPainter painter = new GeometricalObjectPainter(g2d);
		int size = model.getSize();
		for (int i=0; i<size; i++) {
			model.getObject(i).accept(painter);
		}
		
		currentTool.get().paint(g2d);
	}
	
}

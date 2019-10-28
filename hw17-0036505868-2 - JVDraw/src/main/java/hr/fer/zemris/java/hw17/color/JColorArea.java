package hr.fer.zemris.java.hw17.color;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

/**
 * Component that draws the selected color.
 * @author Patrik
 *
 */
public class JColorArea extends JComponent implements IColorProvider {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Preferred width
	 */
	private static int WIDTH = 15;
	
	/**
	 * Preferred height
	 */
	private static int HEIGHT = 15;

	/**
	 * Selected color
	 */
	private Color selectedColor;
	
	/**
	 * List of listeners
	 */
	private List<ColorChangeListener> listeners = new CopyOnWriteArrayList<>();
	
	/**
	 * Creates a new component
	 * @param initialColor initial color
	 */
	public JColorArea(Color initialColor) {
		super();
		selectedColor = initialColor;
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Color newColor = JColorChooser.showDialog(JColorArea.this, "Choose color", selectedColor);
				if (newColor != null) {
					Color oldColor = selectedColor;
					selectedColor = newColor;
					listeners.forEach(l -> l.newColorSelected(JColorArea.this, oldColor, newColor));
					repaint();
				}
			}
		});
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(selectedColor);
		Insets ins = getInsets();
		g.fillRect(ins.top, ins.left, getWidth() - ins.left - ins.right, getHeight() - ins.top - ins.bottom);
	}
	
	@Override
	public Dimension getMaximumSize() {
		return new Dimension(WIDTH, HEIGHT);
	}
	
	@Override
	public Dimension getMinimumSize() {
		return new Dimension(WIDTH, HEIGHT);
	}

	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		if (l != null) {
			listeners.add(l);
		}
	}

	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		listeners.remove(l);
	}
	
	
	
}

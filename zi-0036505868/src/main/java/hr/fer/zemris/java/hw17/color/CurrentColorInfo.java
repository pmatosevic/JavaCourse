package hr.fer.zemris.java.hw17.color;

import java.awt.Color;

import javax.swing.JLabel;

/**
 * Label that shows information about the currently selected colors.
 * @author Patrik
 *
 */
public class CurrentColorInfo extends JLabel {

	private static final long serialVersionUID = 1L;

	/**
	 * Foreground color provider
	 */
	private IColorProvider fgColorProvider;
	
	/**
	 * Background color provider
	 */
	private IColorProvider bgColorProvider;
	
	/**
	 * Color change listener
	 */
	private ColorChangeListener l = new ColorChangeListener() {
		@Override
		public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
			update();
		}
	};
	
	/**
	 * Creates a new label
	 * @param fgColorProvider foreground color provider
	 * @param bgColorProvider background color provider
	 */
	public CurrentColorInfo(IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
		this.fgColorProvider = fgColorProvider;
		this.bgColorProvider = bgColorProvider;
		fgColorProvider.addColorChangeListener(l);
		bgColorProvider.addColorChangeListener(l);
		update();
	}
	
	/**
	 * Updates the displayed text.
	 */
	private void update() {
		Color fColor = fgColorProvider.getCurrentColor();
		Color bColor = bgColorProvider.getCurrentColor();
		setText(String.format("Foreground color: (%d, %d, %d), background color: (%d, %d, %d).", 
				fColor.getRed(), fColor.getGreen(), fColor.getBlue(),
				bColor.getRed(), bColor.getGreen(), bColor.getBlue()));
	}
	
}

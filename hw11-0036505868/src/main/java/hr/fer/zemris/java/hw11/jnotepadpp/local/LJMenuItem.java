package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.JMenuItem;

/**
 * A localizable menu item
 * @author Patrik
 *
 */
public class LJMenuItem extends JMenuItem {
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new menu item
	 * @param key translation key
	 * @param flp form localization provider
	 */
	public LJMenuItem(String key, FormLocalizationProvider flp) {
		setText(flp.getString(key));
		flp.addLocalizationListener(() -> setText(flp.getString(key)));
	}
	
}

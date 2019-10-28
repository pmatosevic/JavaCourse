package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.JMenu;

/**
 * A localizable menu.
 * @author Patrik
 *
 */
public class LJMenu extends JMenu {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new menu
	 * @param key translation key
	 * @param flp form localization provider
	 */
	public LJMenu(String key, FormLocalizationProvider flp) {
		setText(flp.getString(key));
		flp.addLocalizationListener(() -> setText(flp.getString(key)));
	}
	
}

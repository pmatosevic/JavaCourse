package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.MissingResourceException;

import javax.swing.AbstractAction;

/**
 * An action that changes its name and description based on current language.
 * @author Patrik
 *
 */
public abstract class LocalizableAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new action
	 * @param nameKey key for the translation
	 * @param lp localization provider
	 */
	public LocalizableAction(String nameKey, ILocalizationProvider lp) {
		change(nameKey, lp);
		lp.addLocalizationListener(() -> change(nameKey, lp));
	}
	
	/**
	 * Changes its name and description based on the language
	 * @param nameKey translation key
	 * @param lp localization provider
	 */
	private void change(String nameKey, ILocalizationProvider lp) {
		putValue(NAME, lp.getString(nameKey));
		try {
			putValue(SHORT_DESCRIPTION, lp.getString(nameKey + "_desc"));
		} catch(MissingResourceException e) { }
	}
	
}

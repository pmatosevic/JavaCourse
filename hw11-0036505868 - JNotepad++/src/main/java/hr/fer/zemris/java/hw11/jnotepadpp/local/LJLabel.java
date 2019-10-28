package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.JLabel;

/**
 * A localizable label that stores the translation for some translation key
 * and on every language change calls the {@link LJLabel#updateText()}.
 * @author Patrik
 *
 */
public abstract class LJLabel extends JLabel {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Translated text
	 */
	protected String translatedText;
	
	public LJLabel(String key, FormLocalizationProvider flp) {
		translatedText = flp.getString(key);
		updateText();
		flp.addLocalizationListener(() -> {translatedText = flp.getString(key); updateText();});
	}
	
	protected abstract void updateText();
	
}

package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Main singleton implementation of the {@link ILocalizationProvider} that can return translations.
 * @author Patrik
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

	/**
	 * Current language
	 */
	private String language;
	
	/**
	 * Resource bundle
	 */
	private ResourceBundle bundle;
	
	/**
	 * Instance of this provider
	 */
	private static LocalizationProvider instance = null;
	
	/**
	 * Returns the instance of this class (and creates it if not already created)
	 * @return the instance
	 */
	public static LocalizationProvider getInstance() {
		if (instance == null) {
			instance = new LocalizationProvider();
		}
		
		return instance;
	}
	
	/**
	 * Creates a new provider
	 */
	private LocalizationProvider() {
		setLanguage("en");
	}
	
	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}
	
	/**
	 * Sets the language
	 * @param lang language code
	 */
	public void setLanguage(String lang) {
		language = lang;
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.translations", locale);
		fire();
	}
	
	@Override
	public String getCurrentLanguage() {
		return language;
	}

}

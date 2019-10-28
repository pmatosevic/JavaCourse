package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Represents a provider that can return translations for the current localization.
 * 
 * @author Patrik
 *
 */
public interface ILocalizationProvider {

	/**
	 * Adds a localization listener
	 * @param l localization listener
	 */
	void addLocalizationListener(ILocalizationListener l);
	
	/**
	 * Removes the localization listener
	 * @param l localization listener
	 */
	void removeLocalizationListener(ILocalizationListener l);
	
	/**
	 * Returns the translation for the given key
	 * @param key key
	 * @return translation
	 */
	String getString(String key);
	
	/**
	 * Returns the current language code.
	 * @return language code
	 */
	String getCurrentLanguage();
	
}

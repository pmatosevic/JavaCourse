package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * An implementation of {@link ILocalizationProvider} that stores its own list of listeners
 * and can connect to the actual localization provider to get the translations.
 * 
 * @author Patrik
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {
	
	/**
	 * Connected flag
	 */
	private boolean connected = false;
	
	/**
	 * Parent localization provider
	 */
	private ILocalizationProvider parent;
	
	/**
	 * Last language
	 */
	private String lastLanguage;
	
	/**
	 * Listener for the parent provider
	 */
	private ILocalizationListener listener = () -> {
		fire();
	};
	
	/**
	 * Creates a new object.
	 * @param parent parent localization provider
	 */
	public LocalizationProviderBridge(ILocalizationProvider parent) {
		this.parent = parent;
		lastLanguage = parent.getCurrentLanguage();
	}
	
	/**
	 * Registers itself as a listener to the parent
	 */
	public void connect() {
		if (connected) return;
		
		connected = true;
		parent.addLocalizationListener(listener);
		
		if (!lastLanguage.equals(parent.getCurrentLanguage())) {
			fire();
		}
	}
	
	/**
	 * Unregisters itself from parent provider.
	 */
	public void disconnect() {
		connected = false;
		parent.removeLocalizationListener(listener);
		lastLanguage = parent.getCurrentLanguage();
	}

	@Override
	public String getString(String key) {
		return parent.getString(key);
	}

	@Override
	public String getCurrentLanguage() {
		return parent.getCurrentLanguage();
	}
}

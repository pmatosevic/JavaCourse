package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * A partial implementation of the {@link ILocalizationProvider} that is capable of storing listeners.
 * @author Patrik
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	/**
	 * List of listeners
	 */
	private List<ILocalizationListener> listeners = new ArrayList<>();
	
	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		if (l != null) {
			listeners.add(l);
		}
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners.remove(l);
	}

	/**
	 * Notifies the listeners
	 */
	public void fire() {
		for (ILocalizationListener l : listeners) {
			l.localizationChanged();
		}
	}
	
	@Override
	public abstract String getString(String key);

}

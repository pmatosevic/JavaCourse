package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;

/**
 * An action that wraps an existing non localized action in a localized action.
 * @author Patrik
 *
 */
public class LocalizableProxyAction extends LocalizableAction {

	private static final long serialVersionUID = 1L;
	
	/**
	 * The actual action
	 */
	private Action action;

	/**
	 * Creates a new action.
	 * @param nameKey key for the translation
	 * @param lp localization provider
	 * @param action actual action
	 */
	public LocalizableProxyAction(String nameKey, ILocalizationProvider lp, Action action) {
		super(nameKey, lp);
		this.action = action;
		setEnabled(action.isEnabled());
		action.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals("enabled")) {
					setEnabled((Boolean) evt.getNewValue());
				}
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		action.actionPerformed(e);
	}
	
}

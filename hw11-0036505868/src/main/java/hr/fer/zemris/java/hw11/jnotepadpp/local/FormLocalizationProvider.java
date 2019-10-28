package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * An implementation of the {@link ILocalizationProvider} that is used in windows.
 * Registers and unregisters with parent on window show and close.
 * @author Patrik
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	/**
	 * Creates a new object
	 * @param parent parent provider
	 * @param window window
	 */
	public FormLocalizationProvider(ILocalizationProvider parent, JFrame window) {
		super(parent);
		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}
		});
	}

	
	
}

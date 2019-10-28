package hr.fer.zemris.java.hw11.jnotepadpp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 * A label that shows date and time.
 * @author Patrik
 *
 */
public class DatetimeLabel extends JLabel {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Stop flag
	 */
	private volatile boolean stopRequested = false;
	
	/**
	 * Date time format
	 */
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY/MM/dd HH:mm:ss");
	
	/**
	 * Creates a new label
	 */
	public DatetimeLabel() {
		updateTime();
		Thread t = new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(100);
				} catch(InterruptedException ex) {}
				if(stopRequested) break;
				SwingUtilities.invokeLater(()->{
					updateTime();
				});
			}
		});
		t.setDaemon(true);
		t.start();
	}
	
	/**
	 * Updates time.
	 */
	private void updateTime() {
		setText(formatter.format(LocalDateTime.now()));
		repaint();
	}
	
	/**
	 * Stops the component
	 */
	public void stop() {
		stopRequested = true;
	}
	
}

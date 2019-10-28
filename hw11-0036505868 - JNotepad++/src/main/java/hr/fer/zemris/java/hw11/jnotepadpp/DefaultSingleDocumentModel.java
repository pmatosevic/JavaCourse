package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * An implementation of the {@link SingleDocumentModel} interface.
 * @author Patrik
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {

	/**
	 * The file path
	 */
	private Path filePath;
	
	/**
	 * GUI component for this document
	 */
	private JTextArea textArea;
	
	/**
	 * List of listeners
	 */
	private List<SingleDocumentListener> listeners = new ArrayList<>();
	
	/**
	 * Modified status
	 */
	private boolean modified = false;
	
	/**
	 * Creates a new model
	 * @param filePath file path (can be {@code null} for new file)
	 * @param text initial text for this model
	 */
	public DefaultSingleDocumentModel(Path filePath, String text) {
		this.filePath = filePath == null ? filePath : filePath.toAbsolutePath();
		this.textArea = new JTextArea(text);
		
		textArea.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				modified = true;
				notifyListeners();
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				modified = true;
				notifyListeners();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				modified = true;
				notifyListeners();
			}
			private void notifyListeners() {
				for (SingleDocumentListener l : listeners) {
					l.documentModifyStatusUpdated(DefaultSingleDocumentModel.this);
				}
			}
		});
	}
	
	@Override
	public JTextArea getTextComponent() {
		return textArea;
	}

	@Override
	public Path getFilePath() {
		return filePath;
	}

	@Override
	public void setFilePath(Path path) {
		if (Objects.equals(path, filePath)) {
			return;
		}
		
		this.filePath = path;
		for (SingleDocumentListener l : listeners) {
			l.documentFilePathUpdated(this);
		}
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		if (this.modified == modified) {
			return;
		}
		
		this.modified = modified;
		for (SingleDocumentListener l : listeners) {
			l.documentModifyStatusUpdated(this);
		}
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		if (l != null) {
			listeners.add(l);
		}
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
	}
	
	
}

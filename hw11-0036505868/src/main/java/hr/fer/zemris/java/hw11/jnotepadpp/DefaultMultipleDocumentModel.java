package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * An implementation of the {@link MultipleDocumentModel} that stores multiple
 * documents and shows them in the {@link JTabbedPane}.
 * 
 * @author Patrik
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	private static final long serialVersionUID = 1L;

	/**
	 * List of opened documents
	 */
	private List<SingleDocumentModel> documents = new ArrayList<>();
	
	/**
	 * Current document
	 */
	private SingleDocumentModel currentDocument = null;
	
	/**
	 * List of listeners
	 */
	private List<MultipleDocumentListener> listeners = new ArrayList<>();
	
	/**
	 * Unmodified document icon
	 */
	private ImageIcon unmodifiedIcon = loadImage("icons/unmodified.png");
	
	/**
	 * Modified document icon
	 */
	private ImageIcon modifiedIcon = loadImage("icons/modified.png");
	
	/**
	 * Creates a new model.
	 */
	public DefaultMultipleDocumentModel() {
		super();
		addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				notifyChanged();
			}
		});
	}
	
	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return documents.iterator();
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel doc = new DefaultSingleDocumentModel(null, "");
		addDocument(doc, "(unnamed)", "(unnamed)");
		return doc;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentDocument;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) throws IOException {
		Objects.requireNonNull(path);
		for (int i=0; i<documents.size(); i++) {
			if (path.equals(documents.get(i).getFilePath())) {
				setSelectedIndex(i);
				return documents.get(i);
			}
		}
		
		String text;
		text = Files.readString(path, StandardCharsets.UTF_8);
		
		SingleDocumentModel doc = new DefaultSingleDocumentModel(path, text);
		addDocument(doc, path.getFileName().toString(), path.toString());
		return doc;
	}
	
	/**
	 * Adds the document to the pane, with title and tip for the tabbed pane.
	 * @param doc document
	 * @param title tab title
	 * @param tip tab tip
	 */
	private void addDocument(SingleDocumentModel doc, String title, String tip) {
		doc.addSingleDocumentListener(new SingleDocumentListener() {
			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				int index = documents.indexOf(model);
				ImageIcon icon = model.isModified() ? modifiedIcon : unmodifiedIcon;
				setIconAt(index, icon);
			}
			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				int index = documents.indexOf(model);
				String title = getTitle(model);
				String tip = getTooltip(model);
				setTitleAt(index, title);
				setToolTipTextAt(index, tip);
			}
		});
		
		documents.add(doc);
		currentDocument = doc;
		addTab(title, unmodifiedIcon, new JScrollPane(doc.getTextComponent()), tip);
		setSelectedIndex(documents.size() - 1);
		
		for (MultipleDocumentListener l : listeners) {
			l.documentAdded(doc);
		}
		notifyChanged();
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) throws IOException {
		if (newPath != null) {
			for (SingleDocumentModel doc : documents) {
				if (model != doc && newPath.equals(doc.getFilePath())) {
					throw new IllegalStateException("Selected file is already opened in another tab.");
				}
			}
		}
		
		Path savePath = newPath == null ? model.getFilePath() : newPath;
		if (savePath == null) {
			throw new IllegalArgumentException("No path given.");
		}
		
		Files.writeString(savePath, model.getTextComponent().getText(), StandardCharsets.UTF_8);
		
		if (newPath != null) {
			model.setFilePath(newPath);
		}
		model.setModified(false);
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		int index = documents.indexOf(model);
		removeTabAt(index);
		documents.remove(index);
		
		for (MultipleDocumentListener l : listeners) {
			l.documentRemoved(model);
		}
		notifyChanged();
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		if (l != null) {
			listeners.add(l);
		}
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return documents.get(index);
	}
	
	/**
	 * Notifies the listeners about the change of the current document and changes it.
	 */
	private void notifyChanged() {
		SingleDocumentModel oldDoc = currentDocument;
		int index = getSelectedIndex();
		currentDocument = index >= 0 ? documents.get(index) : null;
		
		for (MultipleDocumentListener l : listeners) {
			l.currentDocumentChanged(oldDoc, currentDocument);
		}
	}
	
	/**
	 * Loads the image icon from given resource path.
	 * @param resource path
	 * @return image icon
	 */
	private ImageIcon loadImage(String resource) {
		try (InputStream is = this.getClass().getResourceAsStream(resource)) {
			BufferedImage img = ImageIO.read(is);
			Image scaled = img.getScaledInstance(16, 16, 0);
			return new ImageIcon(scaled);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Internal application error.", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
	
	/**
	 * Returns the title based on the single document path.
	 * @param model document
	 * @return title
	 */
	public static String getTitle(SingleDocumentModel model) {
		if (model.getFilePath() == null) return "(unnamed)";
		return model.getFilePath().getFileName().toString();
	}
	
	/**
	 * Returns the tooltip based on the single document path.
	 * @param model document
	 * @return title
	 */
	public static String getTooltip(SingleDocumentModel model) {
		if (model.getFilePath() == null) return "(unnamed)";
		return model.getFilePath().toString();
	}
	
}

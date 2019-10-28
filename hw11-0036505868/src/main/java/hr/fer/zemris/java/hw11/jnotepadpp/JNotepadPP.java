package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;

import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LJLabel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LJMenu;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LJMenuItem;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableProxyAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * An advanced notepad application capable of editing multiple document at the same time.
 * 
 * @author Patrik
 *
 */
@SuppressWarnings("serial")
public class JNotepadPP extends JFrame {
	private static final long serialVersionUID = 1L;

	/**
	 * Model for storing multiple documents
	 */
	private MultipleDocumentModel model;
	
	/**
	 * This frame's form localization provider
	 */
	private FormLocalizationProvider flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
	
	/**
	 * Datetime label
	 */
	private DatetimeLabel datetimeLabel;
	
	/**
	 * Label that shows the length
	 */
	private LengthLabel lengthLabel;
	
	/**
	 * Label that shows the position information
	 */
	private InfoLabel infoLabel;
	
	/**
	 * Creates a new main application frame.
	 */
	public JNotepadPP() {
		setTitle("JNotepad++");
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				checkAndExit();
			}
			@Override
			public void windowClosed(WindowEvent e) {
				datetimeLabel.stop();
			}
		});
		setSize(600, 500);
		
		initGUI();
	}
	
	
	/**
	 * Inits the GUI
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		DefaultMultipleDocumentModel tabbedPane = new DefaultMultipleDocumentModel();
		tabbedPane.addMultipleDocumentListener(mListener);
		cp.add(tabbedPane, BorderLayout.CENTER);
		model = tabbedPane;
		
		createActions();
		createMenus();
		cp.add(createToolbar(), BorderLayout.PAGE_START);
		cp.add(createStatusbar(), BorderLayout.PAGE_END);
	}


	/**
	 * Creates and initializes the actions.
	 */
	private void createActions() {
		createBlank.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		createBlank.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		
		openDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		
		saveDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveDocument.setEnabled(false);
		
		saveDocumentAs.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control A"));
		saveDocumentAs.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		saveDocumentAs.setEnabled(false);
		
		infoAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		infoAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		infoAction.setEnabled(false);
		
		closeDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control D"));
		closeDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
		closeDocument.setEnabled(false);
		
		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
		exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		
		copyTextAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copyTextAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		copyTextAction.setEnabled(false);
		
		cutTextAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		cutTextAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		cutTextAction.setEnabled(false);
		
		pasteTextAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		pasteTextAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
		pasteTextAction.setEnabled(false);
		
		toUpperCase.setEnabled(false);
		toLowerCase.setEnabled(false);
		toggleCase.setEnabled(false);
		ascendingSort.setEnabled(false);
		descendingSort.setEnabled(false);
		uniqueAction.setEnabled(false);
	}


	/**
	 * Creates the menus.
	 */
	private void createMenus() {
		JMenuBar mb = new JMenuBar();
		
		JMenu file = new LJMenu("file", flp);
		mb.add(file);
		file.add(new JMenuItem(createBlank));
		file.add(new JMenuItem(openDocument));
		file.add(new JMenuItem(saveDocument));
		file.add(new JMenuItem(saveDocumentAs));
		file.addSeparator();
		file.add(new JMenuItem(infoAction));
		file.addSeparator();
		file.add(new JMenuItem(closeDocument));
		file.add(new JMenuItem(exitAction));
		
		JMenu edit = new LJMenu("edit", flp);
		mb.add(edit);
		edit.add(new JMenuItem(copyTextAction));
		edit.add(new JMenuItem(cutTextAction));
		edit.add(new JMenuItem(pasteTextAction));
		
		JMenu languages = new LJMenu("languages", flp);
		mb.add(languages);
		JMenuItem langHr = new LJMenuItem("lang_hr", flp);
		langHr.addActionListener(e -> LocalizationProvider.getInstance().setLanguage("hr"));
		JMenuItem langEn = new LJMenuItem("lang_en", flp);
		langEn.addActionListener(e -> LocalizationProvider.getInstance().setLanguage("en"));
		JMenuItem langDe = new LJMenuItem("lang_de", flp);
		langDe.addActionListener(e -> LocalizationProvider.getInstance().setLanguage("de"));
		languages.add(langHr);
		languages.add(langEn);
		languages.add(langDe);
		
		JMenu tools = new LJMenu("tools", flp);
		mb.add(tools);
		JMenu changeCase = new LJMenu("change_case", flp);
		tools.add(changeCase);
		changeCase.add(new JMenuItem(toUpperCase));
		changeCase.add(new JMenuItem(toLowerCase));
		changeCase.add(new JMenuItem(toggleCase));
		JMenu sort = new LJMenu("sort", flp);
		tools.add(sort);
		sort.add(new JMenuItem(ascendingSort));
		sort.add(new JMenuItem(descendingSort));
		JMenuItem unique = new JMenuItem(uniqueAction);
		tools.add(unique);
		
		setJMenuBar(mb);
	}


	/**
	 * Creates the toolbar.
	 * @return the toolbar
	 */
	private Component createToolbar() {
		JToolBar tb = new JToolBar();
		tb.setFloatable(true);
		
		tb.add(new JButton(createBlank));
		tb.add(new JButton(openDocument));
		tb.add(new JButton(saveDocument));
		tb.add(new JButton(saveDocumentAs));
		tb.add(new JButton(infoAction));
		tb.add(new JButton(closeDocument));
		tb.add(new JButton(exitAction));
		
		tb.add(new JButton(copyTextAction));
		tb.add(new JButton(cutTextAction));
		tb.add(new JButton(pasteTextAction));
		
		return tb;
	}
	

	/**
	 * Creates the statusbar
	 * @return the statusbar
	 */
	private Component createStatusbar() {
		JPanel panel = new JPanel(new GridLayout(1, 3));
		panel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
		
		lengthLabel = new LengthLabel(flp);
		infoLabel = new InfoLabel();
		infoLabel.updateInfo();
		infoLabel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.GRAY));
		datetimeLabel = new DatetimeLabel();
		datetimeLabel.setHorizontalAlignment(JLabel.RIGHT);
		datetimeLabel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.GRAY));
		panel.add(lengthLabel);
		panel.add(infoLabel);
		panel.add(datetimeLabel);
		
		return panel;
	}
	
	
	/**
	 * Opens the document and asks the user for path.
	 */
	private void openDocument() {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle(flp.getString("open_file"));
		if (jfc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		
		Path filePath = jfc.getSelectedFile().toPath();
		if (!Files.isReadable(filePath)) {
			JOptionPane.showMessageDialog(
					this,
					String.format(flp.getString("not_readable"), filePath),
					flp.getString("error"),
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		try {
			model.loadDocument(filePath);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(
					this, 
					flp.getString("reading_error"), 
					flp.getString("error"), 
					JOptionPane.ERROR_MESSAGE);
		}
		
	}

	/**
	 * Saves the given document and asks the user for location. 
	 * 
	 * @param saveAs whether to save as or just save
	 * @param currentDoc document to save
	 * @return whether the save was successful
	 */
	private boolean save(boolean saveAs, SingleDocumentModel currentDoc) {
		Path savePath;
		
		if (saveAs || currentDoc.getFilePath() == null) {
			while (true) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle(flp.getString("save_file"));
				if (jfc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
					return false;
				}
				
				savePath = jfc.getSelectedFile().toPath();
				
				boolean saveAllowed = true;
				if (Files.isRegularFile(savePath)) {
					int choice = JOptionPane.showConfirmDialog(
							this, 
							flp.getString("overwrite"), 
							flp.getString("save_file"), 
							JOptionPane.YES_NO_OPTION);
					saveAllowed = (choice == JOptionPane.YES_OPTION);
				}
				
				if (saveAllowed) break;
			}
			
		} else {
			savePath = currentDoc.getFilePath();
		}
		
		try {
			model.saveDocument(currentDoc, savePath);
			return true;
		} catch(IllegalStateException e) {
			JOptionPane.showMessageDialog(
					this, 
					flp.getString("already_open"), 
					flp.getString("error"), 
					JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(
					this, 
					flp.getString("saving_error"), 
					flp.getString("error"), 
					JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}
	
	/**
	 * Shows the document information to the user.
	 */
	private void showInfo() {
		try {
			Document doc = model.getCurrentDocument().getTextComponent().getDocument();
			int len = doc.getLength();
			
			String text = doc.getText(0, doc.getLength());
			int cnt = 0;
			for (int i=0; i<len; i++) {
				if (!Character.isWhitespace(text.charAt(i))) cnt++;
			}
			
			int lines = model.getCurrentDocument().getTextComponent().getLineCount();
			
			JOptionPane.showMessageDialog(
					this, 
					String.format(flp.getString("info_str"), 
							len, cnt, lines));
		} catch (BadLocationException ignorable) {}
	}
	
	/**
	 * Asks the user if he/she wants to save the document before closing and saves it.
	 * @param doc document
	 * @return whether the user decided something or canceled everything
	 */
	private boolean checkAndSave(SingleDocumentModel doc) {
		if (!doc.isModified()) return true;
		
		String docName = DefaultMultipleDocumentModel.getTitle(doc);
		int choice = JOptionPane.showConfirmDialog(
				this, 
				String.format(flp.getString("ask_save"), docName),
				flp.getString("save_file"), 
				JOptionPane.YES_NO_CANCEL_OPTION);
		if (choice == JOptionPane.CANCEL_OPTION || choice == JOptionPane.CLOSED_OPTION) {
			return false;
		}
		
		if (choice == JOptionPane.YES_OPTION) {
			return save(false, doc);
		}
		
		return true;
	}
	
	/**
	 * Checks the modified status and exits the application.
	 */
	public void checkAndExit() {
		for (SingleDocumentModel doc : model) {
			if (!checkAndSave(doc)) {
				return;
			}
		}
		dispose();
	}
	
	/**
	 * Performs the operation on selected text using given operation
	 * @param operation operation to perform
	 */
	private void caseOperation(UnaryOperator<String> operation) {
		JTextArea editor = model.getCurrentDocument().getTextComponent();
		int start = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
		int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
		
		if (len < 1) return;
		
		Document doc = editor.getDocument();
		
		try {
			String text = doc.getText(start, len);
			text = operation.apply(text);
			
			doc.remove(start, len);
			doc.insertString(start, text, null);
		} catch (BadLocationException ignorable) {
		}
	}
	
	/**
	 * Performs the sort operation
	 * @param descending whether to sort in descending order
	 */
	private void sort(boolean descending) {
		lineOperation(lines -> {
			Locale locale = new Locale(flp.getCurrentLanguage());
			Comparator<Object> collator = Collator.getInstance(locale);
			if (descending) collator = collator.reversed();
			
			return lines.stream().sorted(collator::compare).collect(Collectors.toList());
		});
	}
	
	/**
	 * Performs the operation on the selected lines.
	 * @param linesOper operation to perform
	 */
	private void lineOperation(UnaryOperator<List<String>> linesOper) {
		JTextArea editor = model.getCurrentDocument().getTextComponent();
		int startPos = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
		int endPos = Math.max(editor.getCaret().getDot(), editor.getCaret().getMark());
		
		try {
			int lineStart = editor.getLineOfOffset(startPos);
			int lineEnd = editor.getLineOfOffset(endPos);
			
			int start = editor.getLineStartOffset(lineStart);
			int end = editor.getLineEndOffset(lineEnd);
			
			String text = editor.getText(start, end - start);
			List<String> lines = Arrays.asList(text.split("\\r?\\n"));
			lines = linesOper.apply(lines);
			
			StringBuilder sb = new StringBuilder();
			lines.forEach(str -> sb.append(str + "\n"));
			String result = sb.toString();
			
			Document doc = editor.getDocument();
			doc.remove(start, end-start);
			doc.insertString(start, result, null);
		} catch (BadLocationException ignorable) {
			ignorable.printStackTrace();
		}
		
		
	}
	
	/**
	 * Action that creates a new document
	 */
	private Action createBlank = new LocalizableAction("new", flp) {
		@Override
		public void actionPerformed(ActionEvent e) {
			model.createNewDocument();
		}
	};
	
	/**
	 * Action that opens a document
	 */
	private Action openDocument = new LocalizableAction("open", flp) {
		@Override
		public void actionPerformed(ActionEvent e) {
			openDocument();
		}
	};
	
	/**
	 * Action that saves the document
	 */
	private Action saveDocument = new LocalizableAction("save", flp) {
		@Override
		public void actionPerformed(ActionEvent e) {
			save(false, model.getCurrentDocument());
			updateTitle();
		}
	};
	
	/**
	 * Action that saves the document as
	 */
	private Action saveDocumentAs = new LocalizableAction("save_as", flp) {
		@Override
		public void actionPerformed(ActionEvent e) {
			save(true, model.getCurrentDocument());
			updateTitle();
		}
	};
	
	/**
	 * Action that shows document information
	 */
	private Action infoAction = new LocalizableAction("info", flp) {
		@Override
		public void actionPerformed(ActionEvent e) {
			showInfo();
		}
	};
	
	/**
	 * Action that closes the document
	 */
	private Action closeDocument = new LocalizableAction("close", flp) {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (checkAndSave(model.getCurrentDocument())) {
				model.closeDocument(model.getCurrentDocument());
			}
		}
	};
	
	/**
	 * Action that exits the application
	 */
	private Action exitAction = new LocalizableAction("exit", flp) {
		@Override
		public void actionPerformed(ActionEvent e) {
			checkAndExit();
		}
	};
	
	/**
	 * Action that cuts the text
	 */
	private Action cutTextAction = new LocalizableProxyAction("cut", flp, new DefaultEditorKit.CutAction());
	
	/**
	 * Action that copies the text
	 */
	private Action copyTextAction = new LocalizableProxyAction("copy", flp, new DefaultEditorKit.CopyAction());
	
	/**
	 * Action that pastes the text
	 */
	private Action pasteTextAction = new LocalizableProxyAction("paste", flp, new DefaultEditorKit.PasteAction());

	
	/**
	 * Action that cast the text to uppercase
	 */
	private Action toUpperCase = new LocalizableAction("upper_case", flp) {
		@Override
		public void actionPerformed(ActionEvent e) {
			caseOperation(String::toUpperCase);
		}
	};
	
	/**
	 * Action that casts the text to lowercase
	 */
	private Action toLowerCase = new LocalizableAction("lower_case", flp) {
		@Override
		public void actionPerformed(ActionEvent e) {
			caseOperation(String::toLowerCase);
		}
	};
	
	/**
	 * Action that toggles the case of characters
	 */
	private Action toggleCase = new LocalizableAction("toggle_case", flp) {
		@Override
		public void actionPerformed(ActionEvent e) {
			caseOperation(s -> {
				char[] chars = s.toCharArray();
				for (int i=0; i<chars.length; i++) {
					if (Character.isUpperCase(chars[i])) {
						chars[i] = Character.toLowerCase(chars[i]);
					} else if (Character.isLowerCase(chars[i])) {
						chars[i] = Character.toUpperCase(chars[i]);
					}
				}
				return new String(chars);
			});
		}
	};
	
	/**
	 * Action that sorts the lines in ascending order
	 */
	private Action ascendingSort = new LocalizableAction("ascending", flp) {
		@Override
		public void actionPerformed(ActionEvent e) {
			sort(false);
		}
	};
	
	/**
	 * Action that sorts the lines in descending order
	 */
	private Action descendingSort = new LocalizableAction("descending", flp) {
		@Override
		public void actionPerformed(ActionEvent e) {
			sort(true);
		}
	};
	
	/**
	 * Action that removes duplicate lines
	 */
	private Action uniqueAction = new LocalizableAction("unique", flp) {
		@Override
		public void actionPerformed(ActionEvent e) {
			lineOperation(lines -> {
				return lines.stream().distinct().collect(Collectors.toList());
			});
		}
	};
	
	/**
	 * A listener for the multiple document model
	 */
	private MultipleDocumentListener mListener = new MultipleDocumentListener() {
		
		@Override
		public void documentRemoved(SingleDocumentModel model) {
		}
		
		@Override
		public void documentAdded(SingleDocumentModel model) {
		}
		
		@Override
		public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
			boolean opened = currentModel != null;
			saveDocument.setEnabled(opened);
			saveDocumentAs.setEnabled(opened);
			infoAction.setEnabled(opened);
			closeDocument.setEnabled(opened);
			copyTextAction.setEnabled(opened);
			cutTextAction.setEnabled(opened);
			pasteTextAction.setEnabled(opened);
			
			if (previousModel != null) {
				previousModel.getTextComponent().removeCaretListener(cListener);
				previousModel.getTextComponent().getDocument().removeDocumentListener(docListener);
			}
			
			if (currentModel != null) {
				currentModel.getTextComponent().addCaretListener(cListener);
				currentModel.getTextComponent().getDocument().addDocumentListener(docListener);
			}
			
			updateTitle();

			lengthLabel.updateLength();
			updateSelectionInfo();
		}
	};
	
	/**
	 * Updates the title
	 */
	private void updateTitle() {
		if (model.getCurrentDocument() == null) {
			setTitle("JNotepad++");
		} else {
			Path path = model.getCurrentDocument().getFilePath();
			String title = (path == null) ? "(unnamed)" : path.toString();
			setTitle(title + " - JNotepad++");
		}
	}
	
	/**
	 * Updates the information after the selection update
	 */
	private void updateSelectionInfo() {
		infoLabel.updateInfo();
		
		boolean enabled = false;
		if (model.getCurrentDocument() != null) {
			JTextArea editor = model.getCurrentDocument().getTextComponent();
			int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
			enabled = len < 1 ? false : true;
		}
		toUpperCase.setEnabled(enabled);
		toLowerCase.setEnabled(enabled);
		toggleCase.setEnabled(enabled);
		ascendingSort.setEnabled(enabled);
		descendingSort.setEnabled(enabled);
		uniqueAction.setEnabled(enabled);
		
		copyTextAction.setEnabled(enabled);
		cutTextAction.setEnabled(enabled);
	}
	
	/**
	 * A listener for the caret
	 */
	private CaretListener cListener = new CaretListener() {
		@Override
		public void caretUpdate(CaretEvent e) {
			updateSelectionInfo();
		}
	};
	
	/**
	 * A listener for the document
	 */
	private DocumentListener docListener = new DocumentListener() {
		@Override
		public void removeUpdate(DocumentEvent e) {
			lengthLabel.updateLength();
		}
		@Override
		public void insertUpdate(DocumentEvent e) {
			lengthLabel.updateLength();
		}
		@Override
		public void changedUpdate(DocumentEvent e) {
			lengthLabel.updateLength();
		}
	};
	
	
	/**
	 * A label that shows the length of the document
	 * @author Patrik
	 *
	 */
	private class LengthLabel extends LJLabel {

		/**
		 * The length as a string
		 */
		private String lengthString = "";
		
		/**
		 * Creates a new label
		 * @param flp localization provider
		 */
		public LengthLabel(FormLocalizationProvider flp) {
			super("length", flp);
			updateLength();
		}
		
		@Override
		protected void updateText() {
			setText(translatedText + ":" + lengthString);
		}
		
		/**
		 * Updates the length
		 */
		private void updateLength() {
			SingleDocumentModel single = model.getCurrentDocument();
			lengthString = (single == null) ? "" : "" + single.getTextComponent().getDocument().getLength();
			updateText();
		}
	}
	
	/**
	 * A label that shows information about the position.
	 * @author Patrik
	 *
	 */
	private class InfoLabel extends JLabel {
		
		/**
		 * Updates information
		 */
		private void updateInfo() {
			SingleDocumentModel single = model.getCurrentDocument();
			if (single == null) {
				setText("Ln:" + " Col:" + " Sel:");
				return;
			}
			
			JTextComponent c = single.getTextComponent();
			int dot = c.getCaret().getDot();
			int mark = c.getCaret().getMark();
			
			Document doc = c.getDocument(); 
			Element root = doc.getDefaultRootElement();
			int row = root.getElementIndex(dot);
			int col = dot - root.getElement(row).getStartOffset();
			int sel = Math.abs(dot - mark);
			
			setText("Ln:" + (row+1) + " Col:" + (col+1) + " Sel:" + sel);
		}
	}
	
	
	/**
	 * Program entry point
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new JNotepadPP().setVisible(true));
	}
}

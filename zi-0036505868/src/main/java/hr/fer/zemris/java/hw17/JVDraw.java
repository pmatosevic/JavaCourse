package hr.fer.zemris.java.hw17;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw17.color.CurrentColorInfo;
import hr.fer.zemris.java.hw17.color.JColorArea;
import hr.fer.zemris.java.hw17.model.DefaultDrawingModel;
import hr.fer.zemris.java.hw17.model.DrawingModel;
import hr.fer.zemris.java.hw17.model.DrawingObjectListModel;
import hr.fer.zemris.java.hw17.objects.Circle;
import hr.fer.zemris.java.hw17.objects.FilledCircle;
import hr.fer.zemris.java.hw17.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.objects.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.objects.Line;
import hr.fer.zemris.java.hw17.objects.Triangle;
import hr.fer.zemris.java.hw17.tools.CircleTool;
import hr.fer.zemris.java.hw17.tools.FilledCircleTool;
import hr.fer.zemris.java.hw17.tools.LineTool;
import hr.fer.zemris.java.hw17.tools.Tool;
import hr.fer.zemris.java.hw17.tools.TriangleTool;
import hr.fer.zemris.java.hw17.visitor.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw17.visitor.GeometricalObjectFileSaver;
import hr.fer.zemris.java.hw17.visitor.GeometricalObjectPainter;

/**
 * Vector graphics application. Supports drawing lines and circles, saving and loading from files,
 * and exporting the drawing to an image.
 * 
 * @author Patrik
 *
 */
@SuppressWarnings("serial")
public class JVDraw extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Foreground color area
	 */
	private JColorArea fgColorArea;
	
	/**
	 * Background color area
	 */
	private JColorArea bgColorArea;
	
	/**
	 * Current tool
	 */
	private Tool currentTool;
	
	/**
	 * Line tool
	 */
	private LineTool lineTool;
	
	/**
	 * Circle tool
	 */
	private CircleTool circleTool;
	
	/**
	 * Filled circle tool
	 */
	private FilledCircleTool filledCircleTool;
	
	private TriangleTool triangleTool;
	
	/**
	 * The drawing model that holds objects
	 */
	private DrawingModel drawingModel;
	
	/**
	 * The drawing canvas
	 */
	private JDrawingCanvas canvas;
	
	/**
	 * The component that shows the list of objects
	 */
	private JList<GeometricalObject> listComp;
	
	/**
	 * Path to the opened file
	 */
	private Path openedFile = null;
	
	/**
	 * Creates a new frame.
	 */
	public JVDraw() {
		setTitle("JVDraw");
		setSize(800, 600);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				checkAndExit();
			}
		});

		initGUI();
	}
	
	/**
	 * Inits the GUI
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		fgColorArea = new JColorArea(Color.BLACK);
		bgColorArea = new JColorArea(Color.WHITE);
		
		drawingModel = new DefaultDrawingModel();
		
		canvas = new JDrawingCanvas(drawingModel, () -> currentTool);
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				currentTool.mousePressed(e);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				currentTool.mouseReleased(e);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				currentTool.mouseClicked(e);
			}
		});
		canvas.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
				currentTool.mouseMoved(e);
			}
			@Override
			public void mouseDragged(MouseEvent e) {
				currentTool.mouseDragged(e);
			}
		});
		
		listComp = new JList<>(new DrawingObjectListModel(drawingModel));
		listComp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() != 2) return;
				
				GeometricalObject clicked = listComp.getSelectedValue();
				GeometricalObjectEditor editor = clicked.createGeometricalObjectEditor();
				if(JOptionPane.showConfirmDialog(JVDraw.this, editor, "Edit", JOptionPane.OK_CANCEL_OPTION) 
						== JOptionPane.OK_OPTION) {
					try {
						editor.checkEditing();
						editor.acceptEditing();
					} catch (RuntimeException ex) {
						JOptionPane.showMessageDialog(JVDraw.this, "The data was invalid.");
					}
				}
			}
		});
		listComp.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {
				GeometricalObject selected = listComp.getSelectedValue();
				int index = listComp.getSelectedIndex();
				if (selected == null) return;
				
				if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
					drawingModel.remove(selected);
				} else if (evt.getKeyChar() == '+') {
					if (drawingModel.indexOf(selected) != 0) {
						drawingModel.changeOrder(selected, -1);
						listComp.setSelectedIndex(index - 1);
					}
				} else if (evt.getKeyChar() == '-') {
					if (drawingModel.indexOf(selected) != drawingModel.getSize() - 1) {
						drawingModel.changeOrder(selected, +1);
						listComp.setSelectedIndex(index + 1);
					}
				}
			}
		});
		
		lineTool = new LineTool(drawingModel, canvas, fgColorArea);
		circleTool = new CircleTool(drawingModel, canvas, fgColorArea);
		filledCircleTool = new FilledCircleTool(drawingModel, canvas, fgColorArea, bgColorArea);
		triangleTool = new TriangleTool(drawingModel, canvas, fgColorArea, bgColorArea);
		currentTool = lineTool;
		
		cp.add(canvas, BorderLayout.CENTER);
		cp.add(new JScrollPane(listComp), BorderLayout.LINE_END);
		
		cp.add(createToolbar(), BorderLayout.PAGE_START);
		cp.add(new CurrentColorInfo(fgColorArea, bgColorArea), BorderLayout.PAGE_END);
		
		createMenu();
	}
	
	/**
	 * Creates the menu.
	 */
	private void createMenu() {
		JMenuBar mb = new JMenuBar();
		
		JMenu file = new JMenu("File");
		mb.add(file);
		file.add(new JMenuItem(openAction));
		file.add(new JMenuItem(saveAction));
		file.add(new JMenuItem(saveAsAction));
		file.addSeparator();
		file.add(new JMenuItem(exportAction));
		file.addSeparator();
		file.add(new JMenuItem(exitAction));

		setJMenuBar(mb);
	}
	
	/**
	 * Creates the toolbar
	 * @return toolbar
	 */
	private JComponent createToolbar() {
		JToolBar tb = new JToolBar();
		tb.setFloatable(true);
		
		tb.add(fgColorArea);
		tb.add(bgColorArea);
		
		JToggleButton lineBtn = new JToggleButton("Line", true);
		lineBtn.addActionListener(e -> currentTool = lineTool);
		
		JToggleButton circleBtn = new JToggleButton("Circle");
		circleBtn.addActionListener(e -> currentTool = circleTool);
		JToggleButton fillCircleBtn = new JToggleButton("Filled circle");
		fillCircleBtn.addActionListener(e -> currentTool = filledCircleTool);
		
		JToggleButton triangleBtn = new JToggleButton("Triangle");
		triangleBtn.addActionListener(e -> currentTool = triangleTool);
		
		tb.add(lineBtn);
		tb.add(circleBtn);
		tb.add(fillCircleBtn);
		tb.add(triangleBtn);
		
		ButtonGroup group = new ButtonGroup();
		group.add(lineBtn);
		group.add(circleBtn);
		group.add(fillCircleBtn);
		group.add(triangleBtn);
		
		return tb;
	}
	
	/**
	 * Opens the file
	 */
	private void openFile() {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Open");
		FileFilter filter = new FileNameExtensionFilter("JVDraw File", "jvd");
		jfc.setFileFilter(filter);
		
		if (jfc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		
		Path openPath = jfc.getSelectedFile().toPath();
		
		try (BufferedReader reader = Files.newBufferedReader(openPath)) {
			List<GeometricalObject> objects = loadFromFile(reader);
			drawingModel.clear();
			
			objects.forEach(o -> drawingModel.add(o));
			
			drawingModel.clearModifiedFlag();
			openedFile = openPath;
		} catch (IOException | RuntimeException e) {
			JOptionPane.showMessageDialog(this, "Error during reading file.");
		}
	}
	
	/**
	 * Loads the data from the reader and returns the list of geometrical objects.
	 * 
	 * @param reader buffered reader
	 * @return the list of geometrical objects
	 * @throws IOException in case of an IO error
	 */
	public static List<GeometricalObject> loadFromFile(BufferedReader reader) throws IOException {
		List<GeometricalObject> objects = new ArrayList<>();
		while (true) {
			String line = reader.readLine();
			if (line == null) return objects;
			if (line.isBlank()) continue;
			
			String[] parts = line.split("\\s+");
			if (parts[0].equals("LINE")) {
				objects.add(new Line(
						new Color(Integer.parseInt(parts[5]), Integer.parseInt(parts[6]), Integer.parseInt(parts[7])), 
						new Point(Integer.parseInt(parts[1]), Integer.parseInt(parts[2])), 
						new Point(Integer.parseInt(parts[3]), Integer.parseInt(parts[4]))
						));
			} else if (parts[0].equals("CIRCLE")) {
				objects.add(new Circle(
						new Color(Integer.parseInt(parts[4]), Integer.parseInt(parts[5]), Integer.parseInt(parts[6])),
						new Point(Integer.parseInt(parts[1]), Integer.parseInt(parts[2])),
						Integer.parseInt(parts[3])
						));
			} else if (parts[0].equals("FCIRCLE")) {
				objects.add(new FilledCircle(
						new Color(Integer.parseInt(parts[4]), Integer.parseInt(parts[5]), Integer.parseInt(parts[6])),
						new Point(Integer.parseInt(parts[1]), Integer.parseInt(parts[2])),
						Integer.parseInt(parts[3]),
						new Color(Integer.parseInt(parts[7]), Integer.parseInt(parts[8]), Integer.parseInt(parts[9]))
						));
			} else if (parts[0].equals("FTRIANGLE")) {
				Point p1 = new Point(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
				Point p2 = new Point(Integer.parseInt(parts[3]), Integer.parseInt(parts[4]));
				Point p3 = new Point(Integer.parseInt(parts[5]), Integer.parseInt(parts[6]));
				Color outColor = new Color(Integer.parseInt(parts[7]), Integer.parseInt(parts[8]), Integer.parseInt(parts[9]));
				Color fillColor = new Color(Integer.parseInt(parts[10]), Integer.parseInt(parts[11]), Integer.parseInt(parts[12]));
				objects.add(new Triangle(outColor, fillColor, p1, p2, p3));
			} else {
				throw new IllegalArgumentException("Illegal geometrical object.");
			}
		}
	}

	/**
	 * Saves the current document.
	 * 
	 * @param saveAs whether to save as different name
	 * @return whether the save was successful
	 */
	private boolean save(boolean saveAs) {
		Path savePath;
		boolean dialogShown = false;
		
		if (openedFile == null || saveAs) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Save");
			FileFilter filter = new FileNameExtensionFilter("JVDraw File", "jvd");
			jfc.setFileFilter(filter);
			
			if (jfc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
				return false;
			}
			savePath = jfc.getSelectedFile().toPath();
			dialogShown = true;
		} else {
			savePath = openedFile;
		}

		if (Files.isRegularFile(savePath) && dialogShown) {
			int choice = JOptionPane.showConfirmDialog(
					this, 
					"File already exists. Do you want to overwrite it?", 
					"Save file", 
					JOptionPane.YES_NO_OPTION);
			if (choice != JOptionPane.YES_OPTION) return false;
		}
		
		try (BufferedWriter writer = Files.newBufferedWriter(savePath)) {
			GeometricalObjectFileSaver fileSaver = new GeometricalObjectFileSaver();
			for (int i=0; i<drawingModel.getSize(); i++) {
				drawingModel.getObject(i).accept(fileSaver);
			}
			writer.write(fileSaver.getOutputString());
			
			openedFile = savePath;
			drawingModel.clearModifiedFlag();
			return true;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error during saving file.");
		}
		
		return false;
	}
	
	/**
	 * Exports the drawing to an image
	 */
	private void export() {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Export");
		jfc.setMultiSelectionEnabled(false);
		jfc.removeChoosableFileFilter(jfc.getAcceptAllFileFilter());
		
		jfc.addChoosableFileFilter(new FileNameExtensionFilter("JPG file", "jpg"));
		jfc.addChoosableFileFilter(new FileNameExtensionFilter("PNG file", "png"));
		jfc.addChoosableFileFilter(new FileNameExtensionFilter("GIF file", "gif"));
		if (jfc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		
		Path exportPath = jfc.getSelectedFile().toPath();
		FileNameExtensionFilter extFilter = (FileNameExtensionFilter) jfc.getFileFilter();
		String imgType = extFilter.getExtensions()[0];
		if (!extFilter.accept(jfc.getSelectedFile())) {
			String newName = exportPath.getFileName().toString() + "." + imgType;
			exportPath = exportPath.getParent().resolve(newName);
		}
		
		if (Files.isRegularFile(exportPath)) {
			int choice = JOptionPane.showConfirmDialog(
					this, 
					"File already exists. Do you want to overwrite it?", 
					"Save file", 
					JOptionPane.YES_NO_OPTION);
			if (choice != JOptionPane.YES_OPTION) return;
		}
		
		GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();
		for (int i=0; i<drawingModel.getSize(); i++) {
			drawingModel.getObject(i).accept(bbcalc);
		}
		Rectangle box = bbcalc.getBoundingBox();
		
		BufferedImage image = new BufferedImage(
				box.width, box.height, BufferedImage.TYPE_3BYTE_BGR
		);
		Graphics2D g = image.createGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, box.width, box.height);
		
		AffineTransform af = new AffineTransform();
		af.translate(-box.getX(), -box.getY());
		g.setTransform(af);
		
		GeometricalObjectPainter painter = new GeometricalObjectPainter(g);
		for (int i=0; i<drawingModel.getSize(); i++) {
			drawingModel.getObject(i).accept(painter);
		}
		g.dispose();
		
		try {
			ImageIO.write(image, imgType, exportPath.toFile());
			JOptionPane.showMessageDialog(JVDraw.this, "Image is exported.");
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(JVDraw.this, "Error during export.");
		}
	}
	
	/**
	 * Checks the modified flag and exists the application after the user confirms the exit.
	 */
	private void checkAndExit() {
		if (!drawingModel.isModified()) {
			dispose();
			return;
		}
		
		int choice = JOptionPane.showConfirmDialog(
				this, 
				"Do you want to save the drawing?",
				"Exit", 
				JOptionPane.YES_NO_CANCEL_OPTION);
		
		if (choice == JOptionPane.CANCEL_OPTION || choice == JOptionPane.CLOSED_OPTION) {
			return;
		} else if (choice == JOptionPane.NO_OPTION) {
			dispose();
			return;
		}
		
		if (save(false)) {
			dispose();
		}
	}
	
	
	/**
	 * Open action
	 */
	private Action openAction = new AbstractAction("Open") {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			openFile();
		}
	};
	
	/**
	 * Save action
	 */
	private Action saveAction = new AbstractAction("Save") {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			save(false);
		}
	};
	
	/**
	 * Save as action
	 */
	private Action saveAsAction = new AbstractAction("Save as") {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			save(true);
		}
	};
	
	/**
	 * Export action
	 */
	private Action exportAction = new AbstractAction("Export") {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			export();
		}
	};
	
	/**
	 * Exit action
	 */
	private Action exitAction = new AbstractAction("Exit") {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			checkAndExit();
		}
	};
	
	
	/**
	 * Program entry point
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new JVDraw().setVisible(true));
	}
	
}

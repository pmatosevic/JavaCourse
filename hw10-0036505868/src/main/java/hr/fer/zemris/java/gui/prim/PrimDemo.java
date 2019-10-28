package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Program that demonstrates the usage of {@link ListModel} on an example of a list of prime numbers.
 * 
 * @author Patrik
 *
 */
public class PrimDemo extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new object
	 */
	public PrimDemo() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(100, 100);
		setSize(300, 200);
		setTitle("Prim Demo");
		initGUI();
	}
	
	/**
	 * Inits the GUI
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		PrimListModel model = new PrimListModel();
		
		JPanel panel = new JPanel(new GridLayout(1, 2));
		JList<Integer> list1 = new JList<>(model);
		JList<Integer> list2 = new JList<>(model);
		panel.add(new JScrollPane(list1));
		panel.add(new JScrollPane(list2));
		
		JButton button = new JButton("Sljedeći");
		button.addActionListener(e -> {
			model.next();
		});
		
		cp.add(panel, BorderLayout.CENTER);
		cp.add(button, BorderLayout.PAGE_END);
	}
	
	/**
	 * Program entry point
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new PrimDemo().setVisible(true);
		});
	}
	
}

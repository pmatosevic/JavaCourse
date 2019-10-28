package hr.fer.zemris.java.gui.layouts;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Program that demonstrates {@link CalcLayout}.
 * @author Patrik
 *
 */
public class DemoFrame1 extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
//	public DemoFrame1() {
//		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//		initGUI();
//		pack();
//	}
	
	/**
	 * Creates a new object
	 */
	public DemoFrame1() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(500, 500);
		initGUI();
	}

	/**
	 * Inits the GUI
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(3));
		cp.add(l("tekst 1"), new RCPosition(1, 1));
		cp.add(l("tekst 2"), new RCPosition(2, 3));
		cp.add(l("tekst stvarno najdulji"), new RCPosition(2, 7));
		cp.add(l("tekst kraći"), new RCPosition(4, 2));
		cp.add(l("tekst srednji"), new RCPosition(4, 5));
		cp.add(l("tekst"), new RCPosition(4, 7));
		
//		JPanel p = new JPanel(new CalcLayout(3));
//		p.add(new JLabel("x"), "1,1");
//		p.add(new JLabel("y"), "2,3");
//		p.add(new JLabel("z"), "2,7");
//		p.add(new JLabel("w"), "4,2");
//		p.add(new JLabel("a"), "4,5");
//		p.add(new JLabel("b"), "4,7");
//		cp.add(p, new RCPosition(5, 7));
	}

	/**
	 * Creates a new label
	 * @param text text
	 * @return label
	 */
	private JLabel l(String text) {
		JLabel l = new JLabel(text);
		l.setBackground(Color.YELLOW);
		l.setOpaque(true);
		return l;
	}

	/**
	 * Program entry point
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new DemoFrame1().setVisible(true);
		});
	}
}
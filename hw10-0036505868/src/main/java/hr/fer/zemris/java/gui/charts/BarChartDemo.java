package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Program that demonstrates drawing a bar chart using a custom made component.
 * Takes one command-line parameter: a path to the file where the bar chart is defined.
 * 
 * @author Patrik
 *
 */
public class BarChartDemo extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * label for path
	 */
	private JLabel label;
	
	/**
	 * file path
	 */
	private String path;
	
	/**
	 * The bar chart
	 */
	private BarChart barChart;

	/**
	 * Creates a new bar chart
	 * @param path file path
	 */
	public BarChartDemo(String path) {
		this.path = path;
		try {
			barChart = loadFile(Paths.get(path));
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			System.exit(0);
		}
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("BarChart Demo");
		setLocation(100, 100);
		setSize(500, 300);
		initGUI();
	}

	/**
	 * Inits the GUI
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		label = new JLabel(path, JLabel.CENTER);
		cp.add(label, BorderLayout.PAGE_START);
		
		BarChartComponent comp = new BarChartComponent(barChart);
		cp.add(comp, BorderLayout.CENTER);
	}
	
	
	/**
	 * Loads the file path
	 * @param filePath file path
	 * @return a bar chart
	 * @throws in case of an invalid format of the file
	 */
	private static BarChart loadFile(Path filePath) {
		try(BufferedReader reader = Files.newBufferedReader(filePath)) {
			String xText = reader.readLine();
			String yText = reader.readLine();
			
			String[] points = reader.readLine().split("\\s+");
			List<XYValue> values = new ArrayList<>();
			for (String point : points) {
				String[] parts = point.split(",");
				if (parts.length != 2)  {
					throw new IllegalArgumentException("Invalid format.");
				}
				values.add(new XYValue(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
			}
			
			int yMin = Integer.parseInt(reader.readLine());
			int yMax = Integer.parseInt(reader.readLine());
			int yDiff = Integer.parseInt(reader.readLine());
			
			return new BarChart(values, xText, yText, yMin, yMax, yDiff);
		} catch(NumberFormatException e) {
			throw new IllegalArgumentException("Invalid format.");
		} catch(NullPointerException e) {
			throw new IllegalArgumentException("Not enough data.");
		} catch(IOException e) {
			throw new IllegalArgumentException("Error during reading file.");
		}
	}
	
	/**
	 * Program entry point.
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Invalid arguments.");
			return;
		}
		
		SwingUtilities.invokeLater(() -> new BarChartDemo(args[0]).setVisible(true));
	}
	
	
	
}

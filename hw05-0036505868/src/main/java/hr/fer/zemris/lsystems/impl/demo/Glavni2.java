package hr.fer.zemris.lsystems.impl.demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Program that demonstrates drawing L-Systems.
 * 
 * @author Patrik
 *
 */
public class Glavni2 {

	/**
	 * Program entry point
	 * @param args command-line arguments (not used)
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(createKochCurve(LSystemBuilderImpl::new));
	}
	
	
	/**
	 * Creates a L-System for the Koch curve
	 * @param provider provider
	 * @return a L-System for the Koch curve
	 */
	private static LSystem createKochCurve(LSystemBuilderProvider provider) {
		String[] data = new String[] {
			"origin 0.05 0.4",
			"angle 0",
			"unitLength 0.9",
			"unitLengthDegreeScaler 1.0 / 3.0",
			"",
			"command F draw 1",
			"command + rotate 60",
			"command - rotate -60",
			"",
			"axiom F",
			"",
			"production F F+F--F+F"
		};
		return provider.createLSystemBuilder().configureFromText(data).build();
	}
	
}
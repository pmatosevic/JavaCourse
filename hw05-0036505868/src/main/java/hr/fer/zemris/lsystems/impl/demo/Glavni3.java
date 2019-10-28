package hr.fer.zemris.lsystems.impl.demo;

import java.util.Scanner;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Program that demonstrates drawing L-Systems.
 * 
 * @author Patrik
 *
 */
public class Glavni3 {

	/**
	 * Program entry point
	 * @param args command-line arguments (not used)
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(LSystemBuilderImpl::new);
	}
	

}
package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Demonstration program that asks the user to enter complex roots
 * and then draws the Newton-Raphson fractal.
 * 
 * @author Patrik
 *
 */
public class Newton {

	/**
	 * Final string
	 */
	private static final String FINAL_STR = "done";
	
	/**
	 * Program entry point
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		Scanner sc = new Scanner(System.in);
		
		int cnt = 0;
		List<Complex> roots = new ArrayList<>();
		while (true) {
			System.out.print("Root " + (cnt+1) + "> ");
			String input = sc.nextLine().trim();
			if (input.equals(FINAL_STR)) {
				break;
			}
			
			Complex c;
			try {
				c = Complex.parse(input);
			} catch (NumberFormatException e) {
				System.out.println("Invalid number format. Expected complex number in format: x+iy.");
				continue;
			}
			
			roots.add(c);
			cnt++;
		}
		
		sc.close();
		System.out.println("Image of fractal will appear shortly. Thank you.");
		
		NewtonFractalProducer newtonFractal = new NewtonFractalProducer(
				new ComplexRootedPolynomial(Complex.ONE, roots.toArray(new Complex[] {})));
		
		FractalViewer.show(newtonFractal);
	}
	
}

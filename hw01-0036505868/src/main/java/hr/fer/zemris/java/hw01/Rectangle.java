package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program that takes width and height (as real numbers) of a rectangle from
 * command-line arguments (the first argument is width and the second is height)
 * or from standard input (if command line arguments are not provided) and
 * calculates and prints the area and the perimeter of the given rectangle. 
 * If an invalid number of arguments is provided or in case of an invalid format in
 * command-line arguments, an error message is printed and execution ends.
 * 
 * @author Patrik
 *
 */
public class Rectangle {

	/**
	 * Program entry point.
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		double width;
		double height;

		if (args.length == 0) {
			Scanner sc = new Scanner(System.in);

			width = loadNumber(sc, "širinu");
			height = loadNumber(sc, "visinu");

			sc.close();
		} else {
			if (args.length != 2) {
				System.out.println("Broj argumenata je neispravan.");
				return;
			}

			try {
				width = Double.parseDouble(args[0]);
				height = Double.parseDouble(args[1]);
			} catch (NumberFormatException ex) {
				System.out.println("Format argumenata nije ispravan.");
				return;
			}

			if (width < 0 || height < 0) {
				System.out.println("Argumenti moraju biti nenegativni.");
				return;
			}
		}

		double perimeter = calculatePerimeter(width, height);
		double area = calculateArea(width, height);

		System.out.println("Pravokutnik širine " + Double.toString(width) + 
				" i visine " + Double.toString(height) + 
				" ima površinu " + Double.toString(area) +
				" te opseg " + Double.toString(perimeter) + ".");

	}

	
	/**
	 * Returns non-negative decimal number the user has entered. 
	 * Prints "Unesite {@code whatToAsk} > "
	 * and repeats until non-negative number is entered.
	 * 
	 * @param sc Scanner object used to read input
	 * @param whatToAsk name of parameter the user is asked to input
	 * @return non-negative number read from input
	 */
	static double loadNumber(Scanner sc, String whatToAsk) {
		while(true) {
			System.out.println("Unesite " + whatToAsk + " > ");
			String input = sc.next();

			try {
				double number = Double.parseDouble(input);
				if(number >= 0) {
					return number;
				} else {
					System.out.println("Unijeli ste negativnu vrijednost.");
				}
			} catch(NumberFormatException ex) {
				System.out.println("'" + input + "' se ne može protumačiti kao broj.");
			}
		}
	}

	/**
	 * Calculate the perimeter of a rectangle. 
	 * 
	 * @param width width of a rectangle
	 * @param height height of a rectangle
	 * @return perimeter of a rectangle
	 * @throws IllegalArgumentException if width or height is negative
	 */
	static double calculatePerimeter(double width, double height) {
		if(width < 0 || height < 0) {
			throw new IllegalArgumentException("Not a valid rectangle");
		}
		return 2 * (width + height);
	}

	/**
	 * Calculates the area of a rectangle. 
	 * 
	 * @param width width of a rectangle
	 * @param height height of a rectangle
	 * @return area of a rectangle
	 * @throws IllegalArgumentException if width or height is negative
	 */
	static double calculateArea(double width, double height) {
		if(width < 0 || height < 0) {
			throw new IllegalArgumentException("Not a valid rectangle");
		}
		return width * height;
	}

}

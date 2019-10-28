package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program that takes numbers from user and calculates their factorials.
 * If entered numbers are not whole and between 3 and 20, an error message
 * is printed to the user. Execution ends when the user enters "kraj".
 * 
 * 
 * @author Patrik
 *
 */
public class Factorial {
	
	/**
	 * Program entry point
	 * @param args command-line arguments (not used)
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.print("Unesite broj > ");
			String input = sc.next();

			if (input.equals("kraj")) {
				System.out.println("Doviđenja.");
				break;
			}

			try {
				int number = Integer.parseInt(input);

				if (number < 3 || number > 20) {
					System.out.println("'" + number + "' nije broj u dozvoljenom rasponu.");
					continue;
				}

				long result = factorial(number);
				System.out.printf("%d! = %d%n", number, result);

			} catch (NumberFormatException ex) {
				System.out.println("'" + input + "' nije cijeli broj.");
			}
		}
		sc.close();
	}

	
	/**
	 * Calculates the factorial of a number. 
	 * The number must be in range [0, 20] (limited by limits for long type).
	 * 
	 * @param number number whose factorial will be calculated
	 * @return the factorial of a number
	 * @throws IllegalArgumentException if number is not in the allowed range [0, 20]
	 */
	static long factorial(int number) {
		if (number < 0 || number > 20) {
			throw new IllegalArgumentException("Number " + number + " is not in allowed range [0, 20]");
		}
		
		long result = 1;
		for (int factor = 2; factor <= number; factor++) {
			result *= factor;
		}
		return result;
	}
	
}

package hr.fer.zemris.java.hw07.demo2;

/**
 * A program that demonstrates the usage of iterators.
 * 
 * @author Patrik
 *
 */
public class PrimesDemo2 {

	/**
	 * Program entry point
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(2);
		for (Integer prime : primesCollection) {
			for (Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: " + prime + ", " + prime2);
			}
		}
	}
	
}

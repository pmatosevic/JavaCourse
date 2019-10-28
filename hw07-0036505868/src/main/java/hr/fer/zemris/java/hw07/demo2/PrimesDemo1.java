package hr.fer.zemris.java.hw07.demo2;

/**
 * A program that demonstrates the usage of iterators.
 * 
 * @author Patrik
 *
 */
public class PrimesDemo1 {

	/**
	 * Program entry point
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(5); // 5: how many of them
		for (Integer prime : primesCollection) {
			System.out.println("Got prime: " + prime);
		}
	}
	
}

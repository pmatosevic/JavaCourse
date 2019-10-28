package hr.fer.zemris.java.hw07.demo2;

import java.util.Iterator;

/**
 * A virtual collection of prime numbers.
 * Generates prime numbers on request (using iterator).
 * 
 * @author Patrik
 *
 */
public class PrimesCollection implements Iterable<Integer> {

	/**
	 * The number of prime numbers to generate
	 */
	private int length;
	
	/**
	 * Creates a new {@code PrimesCollection}
	 * @param length the number of primes to generate
	 */
	public PrimesCollection(int length) {
		this.length = length;
	}
	
	@Override
	public Iterator<Integer> iterator() {
		return new PrimesIterator(length);
	}
	
	
	/**
	 * An iterator that generates prime numbers.
	 * 
	 * @author Patrik
	 *
	 */
	private class PrimesIterator implements Iterator<Integer> {

		/**
		 * The number of primes to generate
		 */
		private int length;
		
		/**
		 * The number of already generated primes
		 */
		private int count = 0;
		
		/**
		 * The next prime
		 */
		private int nextPrime = 2;
		
		/**
		 * Creates a new {@code PrimesIterator}
		 * @param length the number of primes to generate
		 */
		public PrimesIterator(int length) {
			this.length = length;
		}
		
		
		
		@Override
		public boolean hasNext() {
			return count < length;
		}

		@Override
		public Integer next() {
			if (!hasNext()) {
				throw new IllegalStateException("Reached the end.");
			}
			int result = nextPrime;
			count++;
			
			if (count == length) {
				return result;
			}
			
			while (true) {
				nextPrime++;
				
				boolean isPrime = true;
				for (int divisor = 2; divisor*divisor <= nextPrime; divisor++) {
					if (nextPrime % divisor == 0) {
						isPrime = false;
						break;
					}
				}
				
				if (isPrime) {
					break;
				}
			}
			
			return result;
		}
		
		
		
	}

	
	
}

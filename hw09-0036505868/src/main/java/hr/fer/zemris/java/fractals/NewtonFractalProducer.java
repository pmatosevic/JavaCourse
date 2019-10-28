package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * A class capable of calculation of root indexes used for drawing Newton fractals.
 * 
 * @author Patrik
 *
 */
public class NewtonFractalProducer implements IFractalProducer {

	/**
	 * Dividing factor for parallelization
	 */
	private static final int DIVIDING_FACTOR = 8;
	
	/**
	 * Convergence threshold
	 */
	private static final double CONVERGENCE_THRESHOLD = 1E-3;
	
	/**
	 * Maximum number of iterations
	 */
	private static final int MAX_ITER = 100;
	
	/**
	 * Root threshold
	 */
	private static final double ROOT_THRESHOLD = 2E-3;
	
	/**
	 * Pool of executors
	 */
	private ExecutorService pool;
	
	/**
	 * Polynomial in the rooted form
	 */
	private ComplexRootedPolynomial rootedPoly;
	
	/**
	 * Complex polynomial
	 */
	private ComplexPolynomial polynomial;
	
	/**
	 * Derivation of the polynomial
	 */
	private ComplexPolynomial derived;
	
	
	/**
	 * Creates a new object
	 * @param rootedPoly rooted complex polynomial to draw the fractal for
	 */
	public NewtonFractalProducer(ComplexRootedPolynomial rootedPoly) {
		this.pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), 
				new DaemonicThreadFactory());
		this.rootedPoly = rootedPoly;
		this.polynomial = rootedPoly.toComplexPolynom();
		this.derived = polynomial.derive();
	}
	

	/**
	 * Performs the calculation of convergence for heights between {@code yMin} and {@code yMax}.
	 * 
	 * @param reMin minimal real value
	 * @param reMax maximal real value
	 * @param imMin minimal imaginary value
	 * @param imMax maximal imaginary value
	 * @param width width
	 * @param height height
	 * @param yMin height start
	 * @param yMax height end
	 * @param data data
	 * @param cancel cancel conditional
	 */
	private void calculate(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin, int yMax, short[] data, AtomicBoolean cancel) {
		int offset = yMin * width;
		for (int y = yMin; y <= yMax; y++) {
			for (int x = 0; x < width; x++) {
				if (cancel.get()) {
					return;
				}
				
				double re = reMin + (double)x * (reMax - reMin) / (width - 1);
				double im = imMax - (double)y * (imMax - imMin) / (height - 1);
				Complex zn = new Complex(re, im);
				Complex znold;
				
				int iter = 0;
				do {
					Complex numerator = polynomial.apply(zn);
					Complex denominator = derived.apply(zn);
					
					znold = zn;
					Complex fraction = numerator.divide(denominator);
					zn = zn.sub(fraction);
					
					iter++;
				} while (znold.sub(zn).module() > CONVERGENCE_THRESHOLD && iter < MAX_ITER);
				
				int index = rootedPoly.indexOfClosestRootFor(zn, ROOT_THRESHOLD);
				data[offset++] = (short) (index + 1);
			}
		}
	}


	@Override
	public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height, long requestNo,
			IFractalResultObserver observer, AtomicBoolean cancel) {
		short[] data = new short[width * height];
		int numTracks = Runtime.getRuntime().availableProcessors() * DIVIDING_FACTOR;
		int yByTrack = height / numTracks;
		
		List<Future<Void>> results = new ArrayList<>();
		for (int i = 0; i < numTracks; i++) {
			int yMin = i * yByTrack;
			int yMax = (i == numTracks - 1) ? height-1 : (i+1)*yByTrack;
			CalculationJob job = new CalculationJob(reMin, reMax, imMin, imMax, width, height, yMin, yMax, data, cancel);
			results.add(pool.submit(job));
		}
		
		for (Future<Void> f : results) {
			while (true) {
				try {
					f.get();
				} catch (InterruptedException e) {
					continue;
				} catch (ExecutionException e) {
				}
				break;
			}
		}
		
		observer.acceptResult(data, (short) (polynomial.order() + 1), requestNo);
	}

	
	/**
	 * Represents a job that can be parallelized.
	 * 
	 * @author Patrik
	 *
	 */
	private class CalculationJob implements Callable<Void> {

		/** minimal real value */
		double reMin;
		
		/** maximal real value */
		double reMax;
		
		/** minimal imaginary value*/
		double imMin;
		
		/** maximal imaginary value */
		double imMax;
		
		/** width */
		int width;
		
		/** height */
		int height;
		
		/** height start */
		int yMin;
		
		/** height end */
		int yMax;
		
		/** data */
		short[] data;
		
		/** cancel job conditional */
		AtomicBoolean cancel;

		/**
		 * Creates a new job.
		 * 
		 * @param reMin minimal real value
		 * @param reMax maximal real value
		 * @param imMin minimal imaginary value
		 * @param imMax maximal imaginary value
		 * @param width width
		 * @param height height
		 * @param yMin height start
		 * @param yMax height end
		 * @param data data
		 * @param cancel cancel conditional
		 */
		public CalculationJob(double reMin, double reMax, double imMin,
				double imMax, int width, int height, int yMin, int yMax, 
				short[] data, AtomicBoolean cancel) {
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.data = data;
			this.cancel = cancel;
		}

		
		@Override
		public Void call() throws Exception {
			calculate(reMin, reMax, imMin, imMax, width, height, yMin, yMax, data, cancel);
			return null;
		}
		
	}
	
	
	/**
	 * A thread factory that creates new daemonic threads.
	 * 
	 * @author Patrik
	 *
	 */
	private static class DaemonicThreadFactory implements ThreadFactory {

		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r);
			t.setDaemon(true);
			return t;
		}

	}


	
	
	
}

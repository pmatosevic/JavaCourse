package hr.fer.zemris.java.raytracer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;


/**
 * Program that demonstrates raycasting with parallelized implementation.
 * 
 * @author Patrik
 *
 */
public class RayCasterParallel {
	
	/**
	 * Program entry point
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(),
		new Point3D(10,0,0),
		new Point3D(0,0,0),
		new Point3D(0,0,10),
		20, 20);
	}
	
	/**
	 * Returns a new {@link IRayTracerProducer} implementation
	 * @return a new {@link IRayTracerProducer} implementation
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			
			/**
			 * Executors pool
			 */
			ForkJoinPool pool = new ForkJoinPool();
			
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, 
					double vertical, int width, int height, long requestNo, 
					IRayTracerResultObserver observer, AtomicBoolean cancel) {
				System.out.println("Započinjem izračune...");
				
				short[] red = new short[width*height];
				short[] green = new short[width*height];
				short[] blue = new short[width*height];
				
				Point3D zAxis = view.sub(eye).normalize();
				Point3D yAxis = viewUp.sub(zAxis.scalarMultiply(zAxis.scalarProduct(viewUp))).normalize();
				Point3D xAxis = zAxis.vectorProduct(yAxis).normalize();
				
				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal/2)).add(yAxis.scalarMultiply(vertical/2));
				
				Scene scene = RayTracerViewer.createPredefinedScene();
				
				pool.invoke(new CalculationJob(0, height - 1, eye, horizontal, vertical, width, height, red, green,
						blue, zAxis, yAxis, xAxis, screenCorner, scene, cancel));

				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}

			
		};
	}
	
	/**
	 * Represents a calculation job when calculating pixel colors.
	 * 
	 * @author Patrik
	 *
	 */
	private static class CalculationJob extends RecursiveAction {

		private static final long serialVersionUID = 1L;
		
		/**
		 * Limit for direct computation
		 */
		final static int THRESHOLD = 16;
		
		/** height start */
		int yMin;
		
		/** height end */
		int yMax;
		
		/** eye ray */
		Point3D eye;
		
		/** horizontal size */
		double horizontal;
		
		/** vertical size */
		double vertical;
		
		/** width */
		int width;
		
		/** height */
		int height;
		
		/** red pixel values */
		short[] red;
		
		/** green pixel values */
		short[] green;
		
		/** blue pixel values */
		short[] blue;
		
		/** z axis */
		Point3D zAxis;
		
		/** y axis */
		Point3D yAxis;
		
		/** x axis */
		Point3D xAxis;
		
		/** screen corner */
		Point3D screenCorner;
		
		/** scene */
		Scene scene;
		
		/** cancel conditional */
		AtomicBoolean cancel;
		
		
		
		/**
		 * Creates a new job.
		 * 
		 * @param yMin height start
		 * @param yMax height end
		 * @param eye eye ray
		 * @param horizontal horizontal size
		 * @param vertical vertical size
		 * @param width width
		 * @param height height
		 * @param red red pixel values
		 * @param green green pixel values
		 * @param blue blue pixel values
		 * @param zAxis z axis
		 * @param yAxis y axis
		 * @param xAxis x axis
		 * @param screenCorner screen corner
		 * @param scene scene
		 * @param cancel cancel conditional
		 */
		public CalculationJob(int yMin, int yMax, Point3D eye, double horizontal, double vertical, int width,
				int height, short[] red, short[] green, short[] blue, Point3D zAxis, Point3D yAxis, Point3D xAxis,
				Point3D screenCorner, Scene scene, AtomicBoolean cancel) {
			this.yMin = yMin;
			this.yMax = yMax;
			this.eye = eye;
			this.horizontal = horizontal;
			this.vertical = vertical;
			this.width = width;
			this.height = height;
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.zAxis = zAxis;
			this.yAxis = yAxis;
			this.xAxis = xAxis;
			this.screenCorner = screenCorner;
			this.scene = scene;
			this.cancel = cancel;
		}

		
		@Override
		protected void compute() {
			if (cancel.get()) return;
			
			if(yMax-yMin+1 <= THRESHOLD) {
				computeDirect();
				return;
			}
			
			invokeAll(
					new CalculationJob(yMin, yMin+(yMax-yMin)/2, eye, horizontal, vertical, width, height, red, green, blue, zAxis, yAxis, xAxis, screenCorner, scene, cancel),
					new CalculationJob(yMin+(yMax-yMin)/2+1, yMax, eye, horizontal, vertical, width, height, red, green, blue, zAxis, yAxis, xAxis, screenCorner, scene, cancel)
			);
		}
		
		/**
		 * Performs direct computation.
		 */
		private void computeDirect() {
			RayCasterAlgorithms.calculate(yMin, yMax, eye, horizontal, vertical, width, height, red, green, blue, yAxis, xAxis, screenCorner, scene, cancel);
		}
		
	}
	
	
	
	
	
	
}

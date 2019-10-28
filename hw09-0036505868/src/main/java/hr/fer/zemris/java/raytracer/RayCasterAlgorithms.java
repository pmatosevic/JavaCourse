package hr.fer.zemris.java.raytracer;

import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;

/**
 * A class with raycasting algorithms.
 * 
 * @author Patrik
 *
 */
public class RayCasterAlgorithms {
	
	/**
	 * Allowed numeric imprecision
	 */
	private static double EPS = 1E-10;

	/**
	 * Performs calculations of pixel colors for heights between {@code yMin} and {@code yMax}.
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
	 * @param yAxis y axis
	 * @param xAxis x axis
	 * @param screenCorner screen corner
	 * @param scene scene
	 * @param cancel cancel conditional
	 */
	public static void calculate(int yMin, int yMax, Point3D eye, double horizontal, double vertical, int width, int height, short[] red,
			short[] green, short[] blue, Point3D yAxis, Point3D xAxis, Point3D screenCorner, Scene scene, AtomicBoolean cancel) {
		short[] rgb = new short[3];
		int offset = yMin * width;
		for(int y = yMin; y <= yMax; y++) {
			for(int x = 0; x < width; x++) {
				if (cancel.get()) return;
				
				Point3D screenPoint = screenCorner
						.add(xAxis.scalarMultiply(horizontal * x / (width - 1)))
						.sub(yAxis.scalarMultiply(vertical * y / (height - 1)));
				
				Ray ray = Ray.fromPoints(eye, screenPoint);
				tracer(scene, ray, rgb);
				red[offset] = rgb[0] > 255 ? 255 : rgb[0];
				green[offset] = rgb[1] > 255 ? 255 : rgb[1];
				blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
				offset++;
			}
		}
	}
	
	
	/**
	 * Colors the pixel for given ray from eye.
	 * 
	 * @param scene scene
	 * @param ray eye ray
	 * @param rgb pixel rgb values
	 */
	private static void tracer(Scene scene, Ray ray, short[] rgb) {
		rgb[0] = rgb[1] = rgb[2] = 0;
		RayIntersection closestObj = findClosestIntersection(scene, ray);
		
		if (closestObj == null) {
			return;
		}
		
		rgb[0] = rgb[1] = rgb[2] = 15;
		for (LightSource ls : scene.getLights()) {
			double distToObj = ls.getPoint().sub(closestObj.getPoint()).norm();
			Ray lightRay = Ray.fromPoints(ls.getPoint(), closestObj.getPoint());
			
			RayIntersection isection = findClosestIntersection(scene, lightRay);
			if (isection == null) continue;
			
			double distToIsection = ls.getPoint().sub(isection.getPoint()).norm();
			if (distToIsection < distToObj - EPS) continue;
			
			addDiffuse(rgb, ls, isection, lightRay);			
			addReflected(rgb, ls, ray, lightRay, isection);
		}
	}

	
	/**
	 * Finds the closest intersection of a given ray in the scene.
	 * 
	 * @param scene scene
	 * @param ray ray
	 * @return closest intersection
	 */
	private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
		RayIntersection closest = null;
		
		for (GraphicalObject object : scene.getObjects()) {
			RayIntersection intersection = object.findClosestRayIntersection(ray);
			if (intersection == null || !intersection.isOuter()) continue;
			
			if (closest == null || intersection.getDistance() < closest.getDistance()) {
				closest = intersection;
			}
		}
		
		return closest;
	}
	
	/**
	 * Adds the diffuse component to the pixel.
	 * 
	 * @param rgb pixel rgb values
	 * @param ls light source
	 * @param isection intersection
	 * @param lightRay ray from light source
	 */
	private static  void addDiffuse(short[] rgb, LightSource ls, RayIntersection isection, Ray lightRay) {
		double fact = lightRay.direction.negate().scalarProduct(isection.getNormal());
		double diffFactor = Math.max(fact, 0);
		rgb[0] += ls.getR() * isection.getKdr() * diffFactor;
		rgb[1] += ls.getG() * isection.getKdg() * diffFactor;
		rgb[2] += ls.getB() * isection.getKdb() * diffFactor;
	}
	
	
	/**
	 * Adds reflective component to the pixel.
	 * 
	 * @param rgb pixel rgb values
	 * @param ls light source
	 * @param eyeRay ray from eye
	 * @param lightRay ray from light source
	 * @param isection intersection
	 */
	private static void addReflected(short[] rgb, LightSource ls, Ray eyeRay, Ray lightRay, RayIntersection isection) {
		Point3D lightVector = lightRay.direction.negate();
		double raysScalar = 2 * isection.getNormal().scalarProduct(lightVector);
		Point3D reflected = isection.getNormal().scalarMultiply(raysScalar).sub(lightVector);
		double reflScalar = reflected.scalarProduct(eyeRay.direction.negate());
		
		double reflectFactor = Math.pow(Math.max(0, reflScalar), isection.getKrn());
		rgb[0] += ls.getR() * isection.getKrr() * reflectFactor;
		rgb[1] += ls.getG() * isection.getKrg() * reflectFactor;
		rgb[2] += ls.getB() * isection.getKrb() * reflectFactor;
	}
	
}

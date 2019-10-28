package hr.fer.zemris.java.raytracer.model;

/**
 * Represents a graphical model of a sphere.
 * 
 * @author Patrik
 *
 */
public class Sphere extends GraphicalObject {

	/**
	 * The center
	 */
	private Point3D center;
	
	/**
	 * The radius
	 */
	private double radius;
	
	/**
	 * The diffuse component for red color
	 */
	private double kdr;
	
	/**
	 * The diffuse component for green color
	 */
	private double kdg;
	
	/**
	 * The diffuse component for blue color
	 */
	private double kdb;
	
	/**
	 * The reflective component for red color
	 */
	private double krr;
	
	/**
	 * The reflective component for green color
	 */
	private double krg;
	
	/**
	 * The reflective component for blue color
	 */
	private double krb;
	
	/**
	 * The coefficient {@code n} for reflective component
	 */
	private double krn;
	
	
	
	/**
	 * Creates a new sphere.
	 * 
	 * @param center center
	 * @param radius radius
	 * @param kdr diffuse component for red color
	 * @param kdg diffuse component for green color
	 * @param kdb diffuse component for blue color
	 * @param krr reflective component for red color
	 * @param krg reflective component for green color
	 * @param krb reflective component for blue color
	 * @param krn coefficient {@code n} for reflective component
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}



	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		Point3D startCenter = ray.start.sub(center);
		
		double a = 1;
		double b = 2 * ray.direction.scalarProduct(startCenter);
		double c = startCenter.scalarProduct(startCenter) - radius * radius;
		double disc = b * b - 4 * a * c;
		if (disc < 0) {
			return null;
		}
		
		double l1 = (-b - Math.sqrt(disc))/(2*a);
		double l2 = (-b + Math.sqrt(disc))/(2*a);
		if (l1 < 0 && l2 < 0) {
			return null;
		}
		
		double lambda = l1 >= 0 ? l1 : l2;
		
		Point3D intsc = ray.start.add(ray.direction.scalarMultiply(lambda));
		double dist = intsc.sub(ray.start).norm();
		return new SphereRayIntersection(intsc, dist, true);
	}
	
	
	/**
	 * Represents a ray intersection with a sphere.
	 * 
	 * @author Patrik
	 *
	 */
	private class SphereRayIntersection extends RayIntersection {

		/**
		 * Constructor for intersection.
		 * 
		 * @param point point of intersection between ray and object
		 * @param distance distance between start of ray and intersection
		 * @param outer specifies if intersection is outer
		 */
		protected SphereRayIntersection(Point3D point, double distance, boolean outer) {
			super(point, distance, outer);
		}

		@Override
		public Point3D getNormal() {
			return getPoint().sub(center).normalize();
		}

		@Override
		public double getKdr() {
			return kdr;
		}

		@Override
		public double getKdg() {
			return kdg;
		}

		@Override
		public double getKdb() {
			return kdb;
		}

		@Override
		public double getKrr() {
			return krr;
		}

		@Override
		public double getKrg() {
			return krg;
		}

		@Override
		public double getKrb() {
			return krb;
		}

		@Override
		public double getKrn() {
			return krn;
		}
		
	}
	
}

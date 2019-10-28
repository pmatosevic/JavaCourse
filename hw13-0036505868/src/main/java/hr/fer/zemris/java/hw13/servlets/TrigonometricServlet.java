package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A servlet that shows a page with calculated values of the sin function from a to b (from GET parameters).
 * 
 * @author Patrik
 *
 */
@WebServlet(name="trigonometric", urlPatterns= {"/trigonometric"})
public class TrigonometricServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String a = req.getParameter("a");
		String b = req.getParameter("b");
		int varA = 0;
		int varB = 360;
		try {
			if (a != null) varA = Integer.parseInt(a);
		} catch (NumberFormatException e) { }
		try {
			if (b != null) varB = Integer.parseInt(b);
		} catch (NumberFormatException e) { }
		
		if (varA > varB) {
			int tmp = varA;
			varA = varB;
			varB = tmp;
		}
		if (varB > varA + 720) {
			varB = varA + 720;
		}
		
//		List<Integer> inputs = new ArrayList<>();
//		List<Double> calculationsSin = new ArrayList<>();
//		List<Double> calculationsCos = new ArrayList<>();
//		for (int x = varA; x <= varB; x++) {
//			double sin = Math.sin(Math.toRadians(x));
//			inputs.add(x);
//			calculationsSin.add(sin);
//			calculationsCos.add(Math.cos(Math.toRadians(x)));
//		}
//		req.setAttribute("size", inputs.size());
//		req.setAttribute("inputs", inputs);
//		req.setAttribute("calculations_sin", calculationsSin);
//		req.setAttribute("calculations_cos", calculationsCos);
		
		List<TrigResult> calculations = new ArrayList<>();
		for (int x = varA; x <= varB; x++) {
			double rad = Math.toRadians(x);
			calculations.add(new TrigResult(x, Math.sin(rad), Math.cos(rad)));
		}
		req.setAttribute("calculations", calculations);
		
		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
		
	}
	
	
	
	/**
	 * Static class for storing results of calculations and displaying them later.
	 * 
	 * @author Patrik
	 *
	 */
	public static class TrigResult {
		
		/** Angle */
		private double angle;
		
		/** Sin */
		private double sin;
		
		/** Cos */
		private double cos;

		/**
		 * Creates a new object
		 * @param angle angle
		 * @param sin sin
		 * @param cos cos
		 */
		private TrigResult(double angle, double sin, double cos) {
			this.angle = angle;
			this.sin = sin;
			this.cos = cos;
		}

		/**
		 * @return the angle
		 */
		public double getAngle() {
			return angle;
		}

		/**
		 * @return the sin
		 */
		public double getSin() {
			return sin;
		}

		/**
		 * @return the cos
		 */
		public double getCos() {
			return cos;
		}
		
		
		
	}
	
}

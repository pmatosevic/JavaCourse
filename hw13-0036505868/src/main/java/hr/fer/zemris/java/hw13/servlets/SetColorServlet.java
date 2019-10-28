package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A servlet that sets the color to the one given in the GET parameter.
 * 
 * @author Patrik
 *
 */
@WebServlet(name="setcolor", urlPatterns={"/setcolor"})
public class SetColorServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String color = req.getParameter("color");
		if (color != null && checkColor(color)) {
			req.getSession().setAttribute("pickedBgCol", color);
			resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/index.jsp"));
		} else {
			resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/colors.jsp"));
		}
	}
	
	/**
	 * Checks whether the string represents a color
	 * @param color color string
	 * @return whether the string represents a color
	 */
	private boolean checkColor(String color) {
		if (color.length() != 6) return false;
		for (int i=0; i<6; i++) {
			char c = color.charAt(i);
			if (!Character.isDigit(c) && !(c>='a' && c<='f') && !(c>='A' && c<='F')) return false;
		}
		return true;
	}
	
}

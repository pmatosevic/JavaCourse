package hr.fer.zemris.java.hw16.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A servlet that serves the images to the clients.
 * 
 * @author Patrik
 *
 */
@WebServlet(urlPatterns={"/servlets/image/*"})
public class ImageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String filename = req.getPathInfo().substring(1);
		req.getRequestDispatcher("/WEB-INF/slike/" + filename).forward(req, resp);
	}
	
}

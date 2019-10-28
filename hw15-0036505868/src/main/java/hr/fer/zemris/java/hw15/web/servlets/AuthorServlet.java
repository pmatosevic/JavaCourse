package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A servlet that handles showing and editing posts.
 * Dispatches actual work to other servlets.
 * 
 * @author Patrik
 *
 */
@WebServlet(urlPatterns={"/servleti/author/*"})
public class AuthorServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}

	/**
	 * Processes the request.
	 * @param req request
	 * @param resp response
	 * @throws ServletException in case of an error
	 * @throws IOException in case of an error
	 */
	private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String[] pathParts = req.getPathInfo().substring(1).split("/");
		String nick = pathParts[0];
		req.setAttribute("nick", nick);
		
		if (pathParts.length == 1) {
			req.getServletContext().getNamedDispatcher("BlogList").forward(req, resp);
			return;
		}
		
		if (pathParts.length == 2 && pathParts[1].equals("new")) {
			req.getServletContext().getNamedDispatcher("EditBlog").forward(req, resp);
			return;
		} 
		
		String eidString = pathParts[1];
		Long eid = null;
		try {
			eid = Long.parseLong(eidString);
		} catch (NumberFormatException e) {
			req.setAttribute("message", "Invalid URL.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		req.setAttribute("eid", eid);
		
		if (pathParts.length == 2) {
			req.getServletContext().getNamedDispatcher("ShowBlog").forward(req, resp);
			return;
		} else if (pathParts.length == 3 && pathParts[2].equals("edit")) {
			req.getServletContext().getNamedDispatcher("EditBlog").forward(req, resp);
			return;
		}
		
		req.setAttribute("message", "Invalid URL.");
		req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
	}

}

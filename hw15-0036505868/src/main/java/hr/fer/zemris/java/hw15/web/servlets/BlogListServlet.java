package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * A servlet that shows a list of all blog entries.
 * 
 * @author Patrik
 *
 */
public class BlogListServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nick = (String) req.getAttribute("nick");
		BlogUser user = DAOProvider.getDAO().getBlogUser(nick);
		if (user == null) {
			req.setAttribute("message", "The nick is invalid.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		Collection<BlogEntry> blogEntries = user.getBlogEntries();
		req.setAttribute("entries", blogEntries);
		
		req.getRequestDispatcher("/WEB-INF/pages/blogList2.jsp").forward(req, resp);
	}
	
}

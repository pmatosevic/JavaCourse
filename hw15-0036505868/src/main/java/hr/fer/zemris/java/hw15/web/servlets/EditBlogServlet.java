package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogEntry;

/**
 * A servlet that provides the functionality of creating new and editing existing posts.
 * 
 * @author Patrik
 *
 */
public class EditBlogServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nick = (String) req.getAttribute("nick");
		if (!nick.equals(req.getSession().getAttribute(MainServlet.SESSION_NICK_KEY))) {
			req.setAttribute("message", "Nick is invalid or access denied.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		Long eid = (Long) req.getAttribute("eid");
		if (eid != null) {
			BlogEntry entry = DAOProvider.getDAO().getBlogEntry(eid.longValue());
			if (entry == null || entry.getCreator().getId() != (Integer) req.getSession().getAttribute(MainServlet.SESSION_ID_KEY)) {
				req.setAttribute("message", "EID is invalid or access denied.");
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				return;
			}
			req.setAttribute("title", entry.getTitle());
			req.setAttribute("text", entry.getText());
		}
		
		req.getRequestDispatcher("/WEB-INF/pages/EditBlog.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nick = (String) req.getAttribute("nick");
		if (!nick.equals(req.getSession().getAttribute(MainServlet.SESSION_NICK_KEY))) {
			req.setAttribute("message", "Nick is invalid or access denied.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		Long eid = (Long) req.getAttribute("eid");
		BlogEntry blogEntry;
		if (eid == null) {
			blogEntry = new BlogEntry();
			blogEntry.setCreatedAt(new Date());
			blogEntry.setCreator(DAOProvider.getDAO().getBlogUser(nick));
		} else {
			blogEntry = DAOProvider.getDAO().getBlogEntry(eid);
			if (blogEntry == null || blogEntry.getCreator().getId() != (Integer) req.getSession().getAttribute(MainServlet.SESSION_ID_KEY)) {
				req.setAttribute("message", "EID is invalid or access denied.");
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				return;
			}
		}
		
		if (req.getParameter("title").isBlank()) {
			req.setAttribute("message", "Title cannot be empty.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		blogEntry.setTitle(req.getParameter("title"));
		blogEntry.setText(req.getParameter("text"));
		blogEntry.setLastModifiedAt(new Date());
		
		if (eid == null) {
			DAOProvider.getDAO().saveBlogEntry(blogEntry);
			eid = blogEntry.getId();
		}
		
		resp.sendRedirect(req.getContextPath() + "/servleti/author/"+ nick + "/" + eid);
	}

}

package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.model.Util;

/**
 * Servlet that shows the blog post, and offers adding comments to it.
 * @author Patrik
 *
 */
@WebServlet(name="ShowBlogServlet")
public class ShowBlogServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nick = (String) req.getAttribute("nick");
		long id = (Long) req.getAttribute("eid");
		
		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);
		
		if (entry == null || !entry.getCreator().getNick().equals(nick)) {
			req.setAttribute("message", "EID is invalid or access denied.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		List<BlogComment> comments = entry.getComments();
		
		req.setAttribute("entry", entry);
		req.setAttribute("comments", comments);
		
		req.getRequestDispatcher("/WEB-INF/pages/ShowBlog.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nick = (String) req.getAttribute("nick");
		long id = (Long) req.getAttribute("eid");
		
		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);
		if (entry == null || !entry.getCreator().getNick().equals(nick)) {
			req.setAttribute("message", "EID is invalid or access denied.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		if (req.getParameter("message").isBlank()) { 		// do nothing
			resp.sendRedirect(req.getRequestURI());
			return;
		}
		
		BlogComment comment = new BlogComment();
		comment.setBlogEntry(entry);
		comment.setMessage(req.getParameter("message"));
		comment.setPostedOn(new Date());
		if (req.getSession().getAttribute("current.user.nick") != null) {
			BlogUser user = DAOProvider.getDAO().getBlogUser((String) req.getSession().getAttribute("current.user.nick"));
			comment.setUsersEMail(user.getEmail());
		} else {
			if (!Util.validate(req.getParameter("email"))) {
				resp.sendRedirect(req.getRequestURI());
				return;
			}
			comment.setUsersEMail(req.getParameter("email"));
		}
		
		DAOProvider.getDAO().saveBlogComment(comment);
		
		resp.sendRedirect(req.getRequestURI());
	}
	
}

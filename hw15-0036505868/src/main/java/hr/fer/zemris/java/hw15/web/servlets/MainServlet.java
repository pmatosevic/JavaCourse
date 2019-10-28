package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.model.Util;

/**
 * Servlet that handles the login process, and shows the list of all users.
 * 
 * @author Patrik
 *
 */
@WebServlet(urlPatterns={"/servleti/main"})
public class MainServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Key for the user id
	 */
	public static final String SESSION_ID_KEY = "current.user.id";
	
	/**
	 * Key for the user nick
	 */
	public static final String SESSION_NICK_KEY = "current.user.nick";
	
	/**
	 * Key for the user first name
	 */
	public static final String SESSION_FN_KEY = "current.user.fn";
	
	/**
	 * Key for the user last name
	 */
	public static final String SESSION_LN_KEY = "current.user.ln";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<BlogUser> blogUsers = DAOProvider.getDAO().getAllBlogUsers();
		req.setAttribute("blogUsers", blogUsers);
		
		if ("true".equals(req.getParameter("registered"))) {
			req.setAttribute("message", "You have been registered successfully.");
		}
		
		req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<BlogUser> blogUsers = DAOProvider.getDAO().getAllBlogUsers();
		req.setAttribute("blogUsers", blogUsers);
		
		String nick = req.getParameter("nick");
		String password = req.getParameter("password");
		
		BlogUser blogUser = DAOProvider.getDAO().getBlogUser(nick);
		if (blogUser == null) {
			req.setAttribute("message", "Check your nick and password and try again.");
			req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);		
			return;
		}
		
		if (!blogUser.getPasswordHash().equals(Util.hashPassword(password))) {
			req.setAttribute("message", "Check your nick and password and try again.");
			req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);		
			return;
		}
		
		req.getSession().setAttribute(SESSION_ID_KEY, blogUser.getId());
		req.getSession().setAttribute(SESSION_NICK_KEY, blogUser.getNick());
		req.getSession().setAttribute(SESSION_FN_KEY, blogUser.getFirstName());
		req.getSession().setAttribute(SESSION_LN_KEY, blogUser.getLastName());
		
		resp.sendRedirect(req.getContextPath() + "/servleti/main");
	}
	
}

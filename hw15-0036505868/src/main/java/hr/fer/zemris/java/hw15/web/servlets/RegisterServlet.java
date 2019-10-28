package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.model.Util;

/**
 * Servlet that handles new user registration.
 * 
 * @author Patrik
 *
 */
@WebServlet(urlPatterns={"/servleti/register"})
public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String firstName = req.getParameter("first_name");
		String lastName = req.getParameter("last_name");
		String nick = req.getParameter("nick");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		
		if (firstName.isBlank() || lastName.isBlank() || nick.isBlank() || email.isBlank() || password.isBlank()) {
			req.setAttribute("error_msg", "All fields are required.");
			req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
			return;
		}
		
		BlogUser user = DAOProvider.getDAO().getBlogUser(nick);
		if (user != null) {
			req.setAttribute("error_msg", "Nickname already exists. Try another one.");
			req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
			return;
		}
		
		if (!Util.validate(email)) {
			req.setAttribute("error_msg", "E-mail is in invalid format. Check it and try again.");
			req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
			return;
		}
		
		BlogUser newUser = new BlogUser();
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.setNick(nick);
		newUser.setEmail(email);
		newUser.setPasswordHash(Util.hashPassword(password));
		
		DAOProvider.getDAO().saveBlogUser(newUser);
		
		resp.sendRedirect(req.getContextPath() + "/servleti/main?registered=true");
	}
	
}

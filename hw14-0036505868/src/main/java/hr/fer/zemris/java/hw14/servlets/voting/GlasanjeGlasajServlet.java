package hr.fer.zemris.java.hw14.servlets.voting;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAOProvider;

/**
 * A servlet that handles a vote request.
 * 
 * @author Patrik
 *
 */
@WebServlet(name="voting_vote", urlPatterns={"/servleti/glasanje-glasaj"})
public class GlasanjeGlasajServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long id = Long.parseLong(req.getParameter("id"));
		DAOProvider.getDao().incrementVotes(id);
		
		resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati?pollID=" + req.getParameter("pollID"));
	}
	
}

package hr.fer.zemris.java.hw13.servlets.voting;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A servlet that handles a vote request.
 * 
 * @author Patrik
 *
 */
@WebServlet(name="voting_vote", urlPatterns={"/glasanje-glasaj"})
public class GlasanjeGlasajServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<Integer, Integer> votes = BandRecord.loadVotes(req.getServletContext().getRealPath(BandRecord.RESULTS_FILENAME));
		votes.merge(Integer.parseInt(req.getParameter("id")), 1, Integer::sum);
		BandRecord.saveVotes(votes, req.getServletContext().getRealPath(BandRecord.RESULTS_FILENAME));
		
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}
	
}

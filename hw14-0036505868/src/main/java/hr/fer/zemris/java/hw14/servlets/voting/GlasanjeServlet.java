package hr.fer.zemris.java.hw14.servlets.voting;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.models.Poll;
import hr.fer.zemris.java.hw14.models.PollOption;

/**
 * A servlet that shows a page with the list of bands that the user can vote for.
 * 
 * @author Patrik
 *
 */
@WebServlet(name="voting", urlPatterns={"/servleti/glasanje"})
public class GlasanjeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long pollID = Long.parseLong(req.getParameter("pollID"));
		
		Poll poll = DAOProvider.getDao().loadPoll(pollID);
		List<PollOption> options = DAOProvider.getDao().loadPollOptions(pollID);
		options.sort((o1, o2) -> Long.compare(o1.getId(), o2.getId()));
		req.setAttribute("poll", poll);
		req.setAttribute("options", options);
		
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
	
}

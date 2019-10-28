package hr.fer.zemris.java.hw14.servlets.voting;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.models.PollOption;

/**
 * A servlet that shows the results of the voting in the table and the chart.
 * 
 * @author Patrik
 *
 */
@WebServlet(name="voting_results", urlPatterns={"/servleti/glasanje-rezultati"})
public class GlasanjeRezultatiServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long pollID = Long.parseLong(req.getParameter("pollID"));
		List<PollOption> results = DAOProvider.getDao().loadPollOptions(pollID);
		results.sort((o1, o2) -> Long.compare(o2.getVotesCount(), o1.getVotesCount()));
		
		long topVotes = results.get(0).getVotesCount();
		List<PollOption> winners = results.stream().filter(o -> o.getVotesCount() == topVotes).collect(Collectors.toList());
		
		req.setAttribute("results", results);
		req.setAttribute("winners", winners);
		
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
	
}

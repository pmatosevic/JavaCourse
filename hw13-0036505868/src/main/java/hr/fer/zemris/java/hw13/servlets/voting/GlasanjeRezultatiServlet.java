package hr.fer.zemris.java.hw13.servlets.voting;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A servlet that shows the results of the voting in the table and the chart.
 * 
 * @author Patrik
 *
 */
@WebServlet(name="voting_results", urlPatterns={"/glasanje-rezultati"})
public class GlasanjeRezultatiServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		List<BandRecord> bands = BandRecord.loadBandsWithVotes(
				req.getServletContext().getRealPath(BandRecord.BANDS_FILENAME),
				req.getServletContext().getRealPath(BandRecord.RESULTS_FILENAME));
		
		bands.sort((b1, b2) -> Integer.compare(b2.getVotes(), b1.getVotes()));
		int topVotes = bands.get(0).getVotes();
		List<BandRecord> winners = bands.stream().filter(b -> b.getVotes() == topVotes).collect(Collectors.toList());
		
		req.setAttribute("bands", bands);
		req.setAttribute("winners", winners);
		
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
	
}

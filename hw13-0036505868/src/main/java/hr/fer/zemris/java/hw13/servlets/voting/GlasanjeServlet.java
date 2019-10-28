package hr.fer.zemris.java.hw13.servlets.voting;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A servlet that shows a page with the list of bands that the user can vote for.
 * 
 * @author Patrik
 *
 */
@WebServlet(name="voting", urlPatterns={"/glasanje"})
public class GlasanjeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<BandRecord> bands = BandRecord.loadBands(req.getServletContext().getRealPath(BandRecord.BANDS_FILENAME));
		bands.sort((b1, b2) -> Integer.compare(b1.getId(), b2.getId()));
		req.setAttribute("bands", bands);
		
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
	
}

package hr.fer.zemris.java.hw14.servlets.voting;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.models.PollOption;

/**
 * A servlet that generates a pie chart image with voting results.
 * 
 * @author Patrik
 *
 */
@WebServlet(name="voting_graphics", urlPatterns={"/servleti/glasanje-grafika"})
public class GlasanjeGrafika extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Width of the image
	 */
	private static int WIDTH = 500;
	
	/**
	 * Height of the image
	 */
	private static int HEIGHT = 500;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long pollID = Long.parseLong(req.getParameter("pollID"));
		List<PollOption> options = DAOProvider.getDao().loadPollOptions(pollID);
		
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (PollOption option : options) {
			dataset.setValue(option.getOptionTitle(), option.getVotesCount());
		}

		boolean legend = true;
		boolean tooltips = false;
		boolean urls = false;

		JFreeChart chart = ChartFactory.createPieChart("Glasovi", dataset, legend, tooltips, urls);
		
		resp.setContentType("image/png");
		ChartUtils.writeChartAsPNG(resp.getOutputStream(), chart, WIDTH, HEIGHT);
	}
	
}

package hr.fer.zemris.java.hw13.servlets.voting;

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

/**
 * A servlet that generates a pie chart image with voting results.
 * 
 * @author Patrik
 *
 */
@WebServlet(name="voting_graphics", urlPatterns={"/glasanje-grafika"})
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
		List<BandRecord> bands = BandRecord.loadBandsWithVotes(
				req.getServletContext().getRealPath(BandRecord.BANDS_FILENAME),
				req.getServletContext().getRealPath(BandRecord.RESULTS_FILENAME));
		
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (BandRecord band : bands) {
			dataset.setValue(band.getName(), band.getVotes());
		}

		boolean legend = true;
		boolean tooltips = false;
		boolean urls = false;

		JFreeChart chart = ChartFactory.createPieChart("Glasovi", dataset, legend, tooltips, urls);
		
		resp.setContentType("image/png");
		ChartUtils.writeChartAsPNG(resp.getOutputStream(), chart, WIDTH, HEIGHT);
	}
	
}

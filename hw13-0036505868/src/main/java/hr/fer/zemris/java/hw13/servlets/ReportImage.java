package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.io.OutputStream;

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
 * A servlet that creates an image of a bar chart representinh the OS usage.
 * 
 * @author Patrik
 *
 */
@WebServlet(name="report_image", urlPatterns={"/reportImage"})
public class ReportImage extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * The width of the image
	 */
	private static int WIDTH = 500;
	
	/**
	 * The height of the image
	 */
	private static int HEIGHT = 300;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/png");
		
		OutputStream ostream = resp.getOutputStream();
		
		JFreeChart chart = getChart();
		ChartUtils.writeChartAsPNG(ostream, chart, WIDTH, HEIGHT);
		resp.flushBuffer();
	}
	
	/**
	 * Creates the chart
	 * @return the chart
	 */
	public JFreeChart getChart() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("Windows", 75.47);
		dataset.setValue("MacOS X", 12.33);
		dataset.setValue("Linux", 1.61);
		dataset.setValue("Chrome OS", 1.17);
		dataset.setValue("Others", 9.42);

		boolean legend = true;
		boolean tooltips = false;
		boolean urls = false;

		JFreeChart chart = ChartFactory.createPieChart("OS usage", dataset, legend, tooltips, urls);

		return chart;
	}
	
}

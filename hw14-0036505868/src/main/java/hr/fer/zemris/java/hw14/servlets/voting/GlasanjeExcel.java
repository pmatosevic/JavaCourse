package hr.fer.zemris.java.hw14.servlets.voting;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.models.PollOption;

/**
 * A servlet that generates an XLS file with voting results.
 * 
 * @author Patrik
 *
 */
@WebServlet(name="glasanje_xls", urlPatterns={"/servleti/glasanje-xls"})
public class GlasanjeExcel extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long pollID = Long.parseLong(req.getParameter("pollID"));
		List<PollOption> options = DAOProvider.getDao().loadPollOptions(pollID);
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		HSSFRow rowHead = sheet.createRow(0);
		rowHead.createCell(0).setCellValue("Opcija");
		rowHead.createCell(1).setCellValue("Broj glasova");
		for (int i=0; i<options.size(); i++) {
			HSSFRow row = sheet.createRow(i+1);
			row.createCell(0).setCellValue(options.get(i).getOptionTitle());
			row.createCell(1).setCellValue(options.get(i).getVotesCount());
		}
		
		resp.setHeader("Content-Disposition", "attachment; filename=\"rezultati.xls\"");
		workbook.write(resp.getOutputStream());
		workbook.close();
	}
	
}

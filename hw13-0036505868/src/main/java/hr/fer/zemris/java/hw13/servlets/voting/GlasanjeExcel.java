package hr.fer.zemris.java.hw13.servlets.voting;

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

/**
 * A servlet that generates an XLS file with voting results.
 * 
 * @author Patrik
 *
 */
@WebServlet(name="glasanje_xls", urlPatterns={"/glasanje-xls"})
public class GlasanjeExcel extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<BandRecord> bands = BandRecord.loadBandsWithVotes(
				req.getServletContext().getRealPath(BandRecord.BANDS_FILENAME),
				req.getServletContext().getRealPath(BandRecord.RESULTS_FILENAME));
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		HSSFRow rowHead = sheet.createRow(0);
		rowHead.createCell(0).setCellValue("Bend");
		rowHead.createCell(1).setCellValue("Broj glasova");
		for (int i=0; i<bands.size(); i++) {
			HSSFRow row = sheet.createRow(i+1);
			row.createCell(0).setCellValue(bands.get(i).getName());
			row.createCell(1).setCellValue(bands.get(i).getVotes());
		}
		
		resp.setHeader("Content-Disposition", "attachment; filename=\"rezultati.xls\"");
		workbook.write(resp.getOutputStream());
		workbook.close();
	}
	
}

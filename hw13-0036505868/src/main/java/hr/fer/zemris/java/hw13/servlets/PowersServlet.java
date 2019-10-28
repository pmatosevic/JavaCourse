package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * A servlet that takes arguments a, b, n, from GET request and constructs a XLS file with powers
 * of numbers between a and b up to the power of n.
 * 
 * @author Patrik
 *
 */
@WebServlet(name="powers", urlPatterns={"/powers"})
public class PowersServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int varA, varB, varN;
		try {
			varA = Integer.parseInt(req.getParameter("a"));
			varB = Integer.parseInt(req.getParameter("b"));
			varN = Integer.parseInt(req.getParameter("n"));
		} catch (NullPointerException | NumberFormatException e) {
			req.getRequestDispatcher("/WEB-INF/pages/powers_error.jsp").forward(req, resp);
			return;
		}
		if (varA < -100 || varA > 100
				|| varB < -100 || varB > 100
				|| varN < 1 || varN > 5) {
			req.getRequestDispatcher("/WEB-INF/pages/powers_error.jsp").forward(req, resp);
			return;
		}
		
		HSSFWorkbook workbook = createExcel(varA, varB, varN);
		resp.setHeader("Content-Disposition", "attachment; filename=\"tablica.xls\"");
		workbook.write(resp.getOutputStream());
	}
	
	
	/**
	 * Creates the excel workbook.
	 * 
	 * @param a lower bound
	 * @param b upper bound
	 * @param n power bound
	 * @return the excel workbook
	 */
	private HSSFWorkbook createExcel(int a, int b, int n) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		for (int i=1; i<=n; i++) {
			HSSFSheet sheet = workbook.createSheet();
			
			HSSFRow rowhead = sheet.createRow(0);
			rowhead.createCell(0).setCellValue("x");
			rowhead.createCell(1).setCellValue("x^" + i);
			
			for (int x=a; x<=b; x++) {
				HSSFRow row = sheet.createRow(x - a + 1);
				row.createCell(0).setCellValue(x);
				row.createCell(1).setCellValue(Math.pow(x, i));
			}
		}
		
		return workbook;
	}
}

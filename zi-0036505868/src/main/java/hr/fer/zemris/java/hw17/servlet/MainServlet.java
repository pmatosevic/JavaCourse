package hr.fer.zemris.java.hw17.servlet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/main")
public class MainServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Path imgDir = Paths.get(req.getServletContext().getRealPath("/WEB-INF/images/"));
		
		List<String> files = new ArrayList<String>();
		if (Files.isDirectory(imgDir)) {
			Files.list(imgDir).map(p -> p.getFileName().toString()).forEach(str -> files.add(str));
		}
		
		files.sort((s1, s2) -> s1.compareTo(s2));
		req.setAttribute("files", files);
		
		req.getRequestDispatcher("/WEB-INF/main.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Path imgDir = Paths.get(req.getServletContext().getRealPath("/WEB-INF/images/"));
		
		String filename = req.getParameter("filename");
		String filebody = req.getParameter("filebody");
		
		if (!checkFilename(filename)) {
			req.setAttribute("error_msg", "Ime datoteke nije ispravno.");
			req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, resp);
			return;
		}
		
		if (!Files.isDirectory(imgDir)) {
			Files.createDirectory(imgDir);
		}
		
		Path filepath = imgDir.resolve(filename);
		Files.writeString(filepath, filebody);
		
		resp.sendRedirect(req.getServletContext().getContextPath() + "/main");
	}
	
	private boolean checkFilename(String filename) {
		for (int i=0; i<filename.length(); i++) {
			char ch = filename.charAt(i);
			if (!Character.isLetterOrDigit(ch) && ch != '.') return false;
		}
		
		if (!filename.toLowerCase().endsWith(".jvd")) return false;
		return true;
	}
	
}

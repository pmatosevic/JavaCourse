package hr.fer.zemris.java.hw17.servlet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw17.JVDraw;
import hr.fer.zemris.java.hw17.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.visitor.GeometricalObjectCounter;

@WebServlet("/show")
public class ShowServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Path imgDir = Paths.get(req.getServletContext().getRealPath("/WEB-INF/images/"));
		
		String filename = req.getParameter("img");
		req.setAttribute("filename", filename);
		Path filePath = imgDir.resolve(filename);
		
		try {
			List<GeometricalObject> objs = JVDraw.loadFromFile(Files.newBufferedReader(filePath));
			GeometricalObjectCounter cnt = new GeometricalObjectCounter();
			objs.forEach(o -> o.accept(cnt));
			req.setAttribute("cnt", cnt);
			
			req.getRequestDispatcher("/WEB-INF/show.jsp").forward(req, resp);
			
		} catch (IOException | RuntimeException e) {
			req.setAttribute("error_msg", "Pogreška prilikom učitavanja datoteke.");
			req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, resp);
			return;
		}
	}
	
}

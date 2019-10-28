package hr.fer.zemris.java.hw17.servlet;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw17.JVDraw;
import hr.fer.zemris.java.hw17.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.visitor.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw17.visitor.GeometricalObjectPainter;

@WebServlet("/image")
public class ImageServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Path imgDir = Paths.get(req.getServletContext().getRealPath("/WEB-INF/images/"));
		String filename = req.getParameter("img");
		Path filePath = imgDir.resolve(filename);
		
		try {
			List<GeometricalObject> objs = JVDraw.loadFromFile(Files.newBufferedReader(filePath));
			
			GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();
			objs.forEach(o -> o.accept(bbcalc));
			Rectangle box = bbcalc.getBoundingBox();
			
			BufferedImage image = new BufferedImage(
					box.width, box.height, BufferedImage.TYPE_3BYTE_BGR
			);
			Graphics2D g = image.createGraphics();
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, box.width, box.height);
			
			AffineTransform af = new AffineTransform();
			af.translate(-box.getX(), -box.getY());
			g.setTransform(af);
			
			GeometricalObjectPainter painter = new GeometricalObjectPainter(g);
			objs.forEach(o -> o.accept(painter));
			g.dispose();
			
			resp.setContentType("image/png");
			ImageIO.write(image, "png", resp.getOutputStream());

		} catch (IOException | RuntimeException e) {
			resp.sendError(404);
		}
	}
	
}

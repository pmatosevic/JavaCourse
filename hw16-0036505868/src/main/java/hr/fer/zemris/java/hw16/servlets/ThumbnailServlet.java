package hr.fer.zemris.java.hw16.servlets;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A servlet that creates and serves the thumbnails of images to the client.
 * 
 * @author Patrik
 *
 */
@WebServlet(urlPatterns={"/servlets/thumb/*"})
public class ThumbnailServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Thumbnail width
	 */
	private static int THUMB_WIDTH = 150;
	
	/**
	 * Thumbnail height
	 */
	private static int THUMB_HEIGHT = 150;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//String filename = req.getParameter("filename");
		String filename = req.getPathInfo().substring(1);
		String thumbName = filename.substring(0, filename.lastIndexOf('.')) + ".png";
		Path thumbPath = Paths.get(req.getServletContext().getRealPath("/WEB-INF/thumbnails/" + thumbName));
		Path imagePath = Paths.get(req.getServletContext().getRealPath("/WEB-INF/slike/" + filename));
		
		if (!Files.exists(imagePath)) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		if (!Files.exists(thumbPath.getParent())) {
			Files.createDirectory(thumbPath.getParent());
		}
		
		if (!Files.exists(thumbPath)) {
			createThumbnail(imagePath, thumbPath);
		}
		
		byte[] thumbBytes = Files.readAllBytes(thumbPath);
		resp.setContentType("image/png");
		resp.setContentLength(thumbBytes.length);
		resp.getOutputStream().write(thumbBytes);
	}

	/**
	 * Creates the thumbnail of the orginal image.
	 * 
	 * @param imagePath path to the orginal image
	 * @param thumbPath path to the thmbnail
	 * @throws IOException in case of an error
	 */
	private void createThumbnail(Path imagePath, Path thumbPath) throws IOException {
		try (InputStream is = Files.newInputStream(imagePath); 
				OutputStream os = Files.newOutputStream(thumbPath)) {
			BufferedImage inputImage = ImageIO.read(is);
			BufferedImage scaledImage = new BufferedImage(THUMB_WIDTH, THUMB_HEIGHT, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = scaledImage.createGraphics();
			g2d.drawImage(inputImage, 0, 0, THUMB_WIDTH, THUMB_HEIGHT, null);
			g2d.dispose();
			ImageIO.write(scaledImage, "png", os);
		}
	}	
	
	
}

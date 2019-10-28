package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * A worker that changes the background color of the homepage.
 * 
 * @author Patrik
 *
 */
public class BgColorWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		boolean updated = false;
		String color = context.getParameter("bgcolor");
		if (color != null && checkColor(color)) {
			context.setPersistentParameter("bgcolor", color);
			updated = true;
		}
		
		context.setMimeType("text/html");
		context.write("<html><body>");
		context.write("<h3>" + (updated ? "Color was changed successfully" : "Invalid color provided") + "</h3>");
		context.write("<a href=\"/index2.html\">Click here to return to homepage.</a>");
		context.write("</body></html>");
	}

	/**
	 * Checks whether the string represents a color
	 * @param color color string
	 * @return whether the string represents a color
	 */
	private boolean checkColor(String color) {
		if (color.length() != 6) return false;
		for (int i=0; i<6; i++) {
			char c = color.charAt(i);
			if (!Character.isDigit(c) && !(c>='a' && c<='f') && !(c>='A' && c<='F')) return false;
		}
		return true;
	}
	
	
}

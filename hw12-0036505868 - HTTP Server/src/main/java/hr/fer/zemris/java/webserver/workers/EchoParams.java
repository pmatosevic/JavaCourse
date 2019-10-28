package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * A worker that shows all parameters from the request in a html table.
 * 
 * @author Patrik
 *
 */
public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		context.setMimeType("text/html");
		context.write("<html><body>");
		context.write("<h1>Received parameters:</h1>");
		context.write("<table>");
		for (String name : context.getParameterNames()) {
			context.write("<tr><td>" + name + "</td><td>" 
					+ context.getParameter(name) + "</td></tr>");
		}
		context.write("</table>");
		context.write("</body></html>");
	}

}

package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * A worker that servers the homepage.
 * 
 * @author Patrik
 *
 */
public class Home implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		String color = context.getPersistentParameter("bgcolor");
		if (color == null) color = "7F7F7F";
		
		context.setTemporaryParameter("background", color);
		context.getDispatcher().dispatchRequest("/private/pages/home.smscr");
	}

}

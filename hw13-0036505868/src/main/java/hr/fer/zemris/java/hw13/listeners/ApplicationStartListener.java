package hr.fer.zemris.java.hw13.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * A web listener that remembers the application start time in the application context.
 * 
 * @author Patrik
 *
 */
@WebListener
public class ApplicationStartListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("start_time", System.currentTimeMillis());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}

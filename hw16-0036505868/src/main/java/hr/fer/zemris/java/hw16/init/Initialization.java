package hr.fer.zemris.java.hw16.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import hr.fer.zemris.java.hw16.model.DefaultImageInfoDB;
import hr.fer.zemris.java.hw16.model.ImageInfoProvider;

/**
 * A listener that loads the information about images on context initialization.
 * 
 * @author Patrik
 *
 */
@WebListener
public class Initialization implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ImageInfoProvider
				.setProvider(new DefaultImageInfoDB(sce.getServletContext().getRealPath("/WEB-INF/opisnik.txt")));
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}

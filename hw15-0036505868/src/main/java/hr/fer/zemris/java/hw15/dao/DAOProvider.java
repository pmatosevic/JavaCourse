package hr.fer.zemris.java.hw15.dao;

import hr.fer.zemris.java.hw15.dao.jpa.JPADAOImpl;

/**
 * Singleton class that knows what to return as an object to access the data layer.
 * 
 * @author Patrik
 *
 */
public class DAOProvider {

	/**
	 * The actual {@link DAO}
	 */
	private static DAO dao = new JPADAOImpl();
	
	/**
	 * Returns the instance.
	 * 
	 * @return object that encapsulates access to the data layer.
	 */
	public static DAO getDAO() {
		return dao;
	}
	
}
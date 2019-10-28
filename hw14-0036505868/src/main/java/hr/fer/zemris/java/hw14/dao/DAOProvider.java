package hr.fer.zemris.java.hw14.dao;

import hr.fer.zemris.java.hw14.dao.sql.SQLDAO;

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
	private static DAO dao = new SQLDAO();
	
	/**
	 * Returns the instance.
	 * 
	 * @return object that encapsulates access to the data layer.
	 */
	public static DAO getDao() {
		return dao;
	}
	
}
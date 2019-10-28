package hr.fer.zemris.java.hw14.dao.sql;

import java.sql.Connection;


/**
 * Class that stores and provides SQL connection to and from the thread local map.
 * 
 * @author Patrik
 *
 */
public class SQLConnectionProvider {

	/**
	 * A thread local map of connections
	 */
	private static ThreadLocal<Connection> connections = new ThreadLocal<>();
	
	/**
	 * Sets the connection for the current thread (or remove it if {@code con} is <code>null</code>).
	 * 
	 * @param con SQL connection
	 */
	public static void setConnection(Connection con) {
		if(con==null) {
			connections.remove();
		} else {
			connections.set(con);
		}
	}
	
	/**
	 * Returns the connection that the current thread can use
	 * 
	 * @return SQL connection
	 */
	public static Connection getConnection() {
		return connections.get();
	}
	
}
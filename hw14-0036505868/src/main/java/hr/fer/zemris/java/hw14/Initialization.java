package hr.fer.zemris.java.hw14;

import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

/**
 * A listener that is executed on application start. Initializes the connection pool and creates and
 * populates necessary tables for the voting application.
 * 
 * @author Patrik
 *
 */
@WebListener
public class Initialization implements ServletContextListener {

	/**
	 * Array of definition file paths
	 */
	private static String[] poolDefinitions = {"/WEB-INF/poll-bands.txt", "/WEB-INF/poll-oses.txt"};
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String propsPath = sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties");
		Properties props = new Properties();
		try (InputStream is = Files.newInputStream(Paths.get(propsPath))) {
			props.load(is);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		String host = getOrThrow(props, "host");
		String port = getOrThrow(props, "port");
		String dbName = getOrThrow(props, "name");
		String user = getOrThrow(props, "user");
		String password = getOrThrow(props, "password");
		String connectionURL = "jdbc:derby://" + host + ":" + port + "/" + dbName + 
				";user=" + user + ";password=" + password;

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
		}
		cpds.setJdbcUrl(connectionURL);

		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
		
		createTables(cpds, sce.getServletContext());
	}
	
	/**
	 * Gets the value from properties or throws an exception if it is {@code null}.
	 * 
	 * @param props properties
	 * @param key key
	 * @return value from properties
	 */
	private String getOrThrow(Properties props, String key) {
		String value = props.getProperty(key);
		if (value == null) {
			throw new RuntimeException("Property " + key + "was not defined.");
		}
		return value;
	}

	/**
	 * Creates the necessary tables if they do not exist, and populates them.
	 * @param ds data source
	 * @param ctx servlet context
	 */
	private void createTables(DataSource ds, ServletContext ctx) {
		boolean created = false;
		try (Connection con = ds.getConnection()) {
			Set<String> tableNames = getDBTables(con);
			if (!tableNames.contains("polls")) {
				PreparedStatement pst = con.prepareStatement(
						"CREATE TABLE Polls\r\n" + 
						"(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\r\n" + 
						"title VARCHAR(150) NOT NULL,\r\n" + 
						"message CLOB(2048) NOT NULL\r\n" + 
						")");
				pst.executeUpdate();
				created = true;
			}
			if (!tableNames.contains("polloptions")) {
				PreparedStatement pst = con.prepareStatement(
						"CREATE TABLE PollOptions\r\n" + 
						"(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\r\n" + 
						"optionTitle VARCHAR(100) NOT NULL,\r\n" + 
						"optionLink VARCHAR(150) NOT NULL,\r\n" + 
						"pollID BIGINT,\r\n" + 
						"votesCount BIGINT,\r\n" + 
						"FOREIGN KEY (pollID) REFERENCES Polls(id)\r\n" + 
						")");
				pst.executeUpdate();
				created = true;
			}
			
			PreparedStatement pst = con.prepareStatement("SELECT COUNT(*) FROM Polls");
			ResultSet rset = pst.executeQuery();
			rset.next();
			long rows = rset.getLong(1);
			if (rows == 0 || created) {
				for (String path : poolDefinitions) {
					populateData(con, ctx.getRealPath(path));
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Populates the initial poll data to the database.
	 * 
	 * @param con SQL connection
	 * @param filepath path to the definition file
	 * @throws SQLException in case of an error
	 */
	private void populateData(Connection con, String filepath) throws SQLException {
		String pollSQL = "INSERT INTO Polls(title, message) VALUES (?, ?)";
		String optionSQL = "INSERT INTO PollOptions(optionTitle, optionLink, pollID, votesCount) VALUES (?, ?, ?, ?)";
		
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(filepath))) {
			String title = reader.readLine();
			String msg = reader.readLine();
			PreparedStatement pollSt = con.prepareStatement(pollSQL, Statement.RETURN_GENERATED_KEYS);
			pollSt.setString(1, title);
			pollSt.setString(2, msg);
			
			pollSt.executeUpdate();
			ResultSet rset = pollSt.getGeneratedKeys();
			rset.next();
			long pollID = rset.getLong(1);
			
			while (true) {
				String line = reader.readLine();
				if (line == null || line.isBlank()) break;
				
				String[] parts = line.split("\t");
				PreparedStatement optSt = con.prepareStatement(optionSQL);
				optSt.setString(1, parts[0]);
				optSt.setString(2, parts[1]);
				optSt.setLong(3, pollID);
				optSt.setLong(4, Long.parseLong(parts[2]));
				
				optSt.executeUpdate();
			}
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Returns the set of table names in the DB
	 * 
	 * @param targetDBConn SQL connection
	 * @return the set of table names in the DB
	 * @throws SQLException in case of an error
	 */
	private Set<String> getDBTables(Connection targetDBConn) throws SQLException {
		Set<String> set = new HashSet<String>();
		DatabaseMetaData dbmeta = targetDBConn.getMetaData();
		ResultSet rs = dbmeta.getTables(null, null, null, null);
		while (rs.next()) {
			set.add(rs.getString("TABLE_NAME").toLowerCase());
		}
		return set;
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource)sce.getServletContext().getAttribute("hr.fer.zemris.dbpool");
		if(cpds!=null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
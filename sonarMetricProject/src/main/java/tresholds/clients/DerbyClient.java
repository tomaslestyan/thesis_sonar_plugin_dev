package main.java.tresholds.clients;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Client to access the emmbeded Apache Derby database
 * @author Tomas Lestyan
 */
public class DerbyClient {

	/** The logger object */
    private final Logger log = LoggerFactory.getLogger(this.getClass());
	private static final String RECORDS_TABLE = "records";
	private static final String METRICS_TABLE = "metrics";
	private static final int DEFAULT_TIMEOUT = 1000;
	private static final String EMBEDDED_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	private static final String CREATE = "create=true";
	private static final String JDBC_DERBY = "jdbc:derby:disharmonies;";
	private Connection connection;

	/**
	 * Constructor
	 */
	public DerbyClient() {
		String home = System.getProperty("user.home", ".");
		String system = home + File.separatorChar + ".database";
		System.setProperty("derby.system.home", system);
		try {
			Class.forName(EMBEDDED_DRIVER);
			connection = DriverManager.getConnection(JDBC_DERBY + CREATE);
		} catch (ClassNotFoundException | SQLException e) {
			// could not create connection
			connection = null;
			log.warn("Connection with emmbedded database not established", e);
		}
		//create tables if neccessary
		if (!requiredTablesExists()) {
			createTables();
			fillTables();
		}
	}

	private boolean fillTables() {
		try {
			// check connection
			if (connection == null || !connection.isValid(1000))  {
				return false;
			}
			Statement st = connection.createStatement();
			st.executeUpdate("INSERT INTO metrics (metric_key, name, description) VALUES('loc','Lines of Code', 'The number of the lines of the code')");
			st.executeUpdate("INSERT INTO metrics (metric_key, name, description) VALUES('noav','Number of Variables', 'TODO')");
			st.executeUpdate("INSERT INTO metrics (metric_key, name, description) VALUES('cyclo','Cyclomatic Complexity', 'TODO')");
			st.executeUpdate("INSERT INTO metrics (metric_key, name, description) VALUES('maxnesting','Maximal Nesting in Method', 'TODO')");
			st.close();
		} catch (SQLException e) {
			log.warn("Can't fill the metrics table", e);
			return false;
		}
		return true;

	}

	private boolean requiredTablesExists() {
		boolean result = false;
		// check connection
		try {
			if (connection == null || !connection.isValid(DEFAULT_TIMEOUT)) {
				return false;
			}
			result = tableExists(METRICS_TABLE) && tableExists(RECORDS_TABLE);
		} catch (SQLException e) {
			log.warn("DB error", e);
		}
		return result;
	}

	private boolean tableExists(String name) throws SQLException {
		boolean exists = false;
		String[] types = {"TABLE"};
		ResultSet res = connection.getMetaData().getTables(null, null, "%", types);
		while (res.next()) {
			exists = exists || name.equalsIgnoreCase(res.getString(3));
		}
		return exists;
	}

	private  boolean createTables() {
		try {
			// check connection
			if (connection == null || !connection.isValid(1000)) return false;
			Statement st = connection.createStatement();
			st.executeUpdate("CREATE TABLE  metrics (metric_key VARCHAR(16) NOT NULL CONSTRAINT metrics_PK PRIMARY KEY, name VARCHAR(32), description VARCHAR(256))");
			st.executeUpdate("CREATE TABLE  records (id INT NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT records_PK PRIMARY KEY, metric_key VARCHAR(16) REFERENCES metrics(metric_key), value INT)");
			st.close();
		} catch (SQLException e) {
			log.warn("Can't create the metrics and records table", e);
			return false;
		}
		return true;
	}

	/**
	 * Get the value of the metric
	 * @param key
	 * @param percentile
	 * @return value of the metric or "-1" if not found
	 */
	public int getMetricValue(String key, double percentile) {
		int value = -1;
		try {
			// check connection
			if (connection == null || !connection.isValid(1000)) {
				return -1;
			}
			Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);
			// get count for metric key
			ResultSet countQuery = st.executeQuery(String.format("SELECT COUNT(*) AS count FROM records WHERE metric_key='%s'", key));
			countQuery.first();
			int count = countQuery.getInt("count");
			// get the position of the searched percentile
			int position = (int) (count*percentile);
			countQuery.close();
			// query for the metric value
			ResultSet res = st.executeQuery(String.format("SELECT value FROM records WHERE metric_key='%s' ORDER BY '%s' ", key, key));
			// go into searched position
			res.absolute(position);
			value = res.getInt("value");
			res.close();
			st.close();
		} catch (SQLException e) {
			log.warn("Failed to retrieve the value for metric: " + key, e);
		}
		return  value;
	}

	public void saveMetricValue(String key, int value) {
		try {
			// check connection
			if (connection == null || !connection.isValid(1000)) {
				return;
			}
			Statement st = connection.createStatement();
			st.executeUpdate(String.format("INSERT INTO records (metric_key, value) VALUES('%s',%s)", key, value));
			st.close();
		} catch (SQLException e) {
			log.warn("Can't save the value of the metric :" + key, e);
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			log.warn("Can't close the connection", e);
		}
		super.finalize();
	}

}

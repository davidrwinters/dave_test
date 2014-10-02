package org.bigsnow.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A simple command line program to execute a SQL query against a specified JDBC data source and to print the results.
 *
 * <p>
 * Usage: RunQuery [{jdbcDriver} [{jdbcUrl} [{sqlQuery} [{dbUser} [{dbPassword}]]]]]
 * </p>
 *
 * @author dwinters
 */
public class RunQuery {

	private static String jdbcDriverClass = null;
	private static String jdbcUrl = null;
	private static String sqlQuery = null;
	private static String dbUser = null;
	private static String dbPassword = null;

	private static final String DEFAULT_JDBC_DRIVER_CLASS = "org.apache.derby.jdbc.ClientDriver";
	private static final String DEFAULT_JDBC_URL = "jdbc:splice://localhost:1527/splicedb;create=true";
	private static final String DEFAULT_SQL_QUERY = "select * from sys.systables";
	private static final String DEFAULT_DB_USER = "admin";
	private static final String DEFAULT_DB_PASSWORD = "splice";

	private static Connection conn = null;

	public RunQuery() {
	}

	public static void main(String[] args) {
		parseArguments(args);
		try {
			initConnection();
			printQueryResults(sqlQuery);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Parse the command line arguments.
	 * @param args
	 */
	private static void parseArguments(String[] args) {
		// Usage: RunQuery [{jdbcDriver} [{jdbcUrl} [{sqlQuery} [{dbUser} [{dbPassword}]]]]]
		if (args != null) {
			switch (args.length) {
			case 0:
				jdbcDriverClass = DEFAULT_JDBC_DRIVER_CLASS;
				jdbcUrl = DEFAULT_JDBC_URL;
				sqlQuery = DEFAULT_SQL_QUERY;
				dbUser = DEFAULT_DB_USER;
				dbPassword = DEFAULT_DB_PASSWORD;
				break;
			case 1:
				jdbcDriverClass = args[0];
				jdbcUrl = DEFAULT_JDBC_URL;
				sqlQuery = DEFAULT_SQL_QUERY;
				dbUser = DEFAULT_DB_USER;
				dbPassword = DEFAULT_DB_PASSWORD;
				break;
			case 2:
				jdbcDriverClass = args[0];
				jdbcUrl = args[1];
				sqlQuery = DEFAULT_SQL_QUERY;
				dbUser = DEFAULT_DB_USER;
				dbPassword = DEFAULT_DB_PASSWORD;
				break;
			case 3:
				jdbcDriverClass = args[0];
				jdbcUrl = args[1];
				sqlQuery = args[2];
				dbUser = DEFAULT_DB_USER;
				dbPassword = DEFAULT_DB_PASSWORD;
				break;
			case 4:
				jdbcDriverClass = args[0];
				jdbcUrl = args[1];
				sqlQuery = args[2];
				dbUser = args[3];
				dbPassword = DEFAULT_DB_PASSWORD;
				break;
			default:
				jdbcDriverClass = args[0];
				jdbcUrl = args[1];
				sqlQuery = args[2];
				dbUser = args[3];
				dbPassword = args[4];
				break;
			}
		}
	}

	/**
	 * Initialize the database connection.
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private static void initConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Class.forName(jdbcDriverClass).newInstance();
		conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
	}

	/**
	 * Execute the SQL query and print the results.
	 * @param sql
	 * @throws SQLException
	 */
	private static void printQueryResults(String sql) throws SQLException {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		ResultSetMetaData md = rs.getMetaData();
		int colCount = md.getColumnCount();

		// Print column headers.
		for (int i = 1; i <= colCount; i++) {
			System.out.print(md.getColumnLabel(i));
			System.out.print("\t");
		}
		System.out.println();

		// Print rows.
		while (rs.next()) {
			for (int i = 1; i <= colCount; i++) {
				System.out.print(rs.getString(i));
				System.out.print("\t");
			}
			System.out.println();
		}
		rs.close();
	}
}

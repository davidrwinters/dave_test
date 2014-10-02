/**
 * 
 */
package org.bigsnow.test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author David Winters
 *         Created on: 3/6/14
 */
public class TestProcedures {

	/**
	 * Test stored procedure.
	 *
	 * @param name     name of something
	 * @param rs       output parameter, the result set object containing the result
	 */
	public static void DAVE_TEST_PROC(String name, ResultSet[] rs)
		throws SQLException
	{
		/*
-- Declare the procedure in ij.
CREATE PROCEDURE APP.DAVE_TEST_PROC(IN name VARCHAR(30)) PARAMETER STYLE JAVA READS SQL DATA LANGUAGE JAVA DYNAMIC RESULT SETS 1 EXTERNAL NAME 'org.bigsnow.test.TestProcedures.DAVE_TEST_PROC';
		 */
		System.out.println("DAVE_TEST_PROC: Start");
		System.out.println("name = " + name);
		Connection conn = DriverManager.getConnection("jdbc:default:connection");
		PreparedStatement ps = conn.prepareStatement("select tableid, tablename from SYS.SYSTABLES");
		rs[0] = ps.executeQuery();
		conn.close();
		System.out.println("DAVE_TEST_PROC: Done");
	}

	/**
	 * Test stored procedure.
	 *
	 * @param name     name of something
	 * @param rs       output parameter, the result set object containing the result
	 */
	public static void DAVE_TEST_PROC_NO_ARGS(ResultSet[] rs)
		throws SQLException
	{
		/*
-- Declare the procedure in ij.
CREATE PROCEDURE APP.DAVE_TEST_PROC_NO_ARGS() PARAMETER STYLE JAVA READS SQL DATA LANGUAGE JAVA DYNAMIC RESULT SETS 1 EXTERNAL NAME 'org.bigsnow.test.TestProcedures.DAVE_TEST_PROC_NO_ARGS';
		 */
		System.out.println("DAVE_TEST_PROC_NO_ARGS: Start");
		System.out.println(String.format("Start: rs = %s, rs.length = %s", rs, rs == null ? "null" : rs.length));
		Connection conn = DriverManager.getConnection("jdbc:default:connection");
		PreparedStatement ps = conn.prepareStatement("select tableid, tablename from SYS.SYSTABLES");
		rs[0] = ps.executeQuery();
		System.out.println(String.format("End: rs = %s, rs.length = %s", rs, rs == null ? "null" : rs.length));
		conn.close();
		System.out.println("DAVE_TEST_PROC_NO_ARGS: Done");
	}

	/**
	 * Get the columns for a stored procedure.
	 *
	 * @param name     name of procedure
	 * @param rs       columns for procedure
	 */
	public static void GET_PROC_COLS(String procName, ResultSet[] rs)
		throws SQLException
	{
		/*
-- Declare the procedure ij.
CREATE PROCEDURE APP.GET_PROC_COLS(IN procName VARCHAR(30)) PARAMETER STYLE JAVA READS SQL DATA LANGUAGE JAVA DYNAMIC RESULT SETS 1 EXTERNAL NAME 'org.bigsnow.test.TestProcedures.GET_PROC_COLS';
		 */
		String catalog = null;
		String schemaPattern = null;
		String procedureNamePattern = procName;
		String columnNamePattern = null;
		Connection conn = DriverManager.getConnection("jdbc:default:connection");
		DatabaseMetaData dbMeta = conn.getMetaData();
//		ResultSet rsProcs = dbMeta.getProcedures(catalog, schemaPattern, procedureNamePattern);
		ResultSet rsProcCols = dbMeta.getProcedureColumns(catalog, schemaPattern, procedureNamePattern, columnNamePattern);
		rs[0] = rsProcCols;
		conn.close();
	}
}

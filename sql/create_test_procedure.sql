-- Declare the procedure.
CREATE PROCEDURE APP.DAVE_TEST_PROC(IN name VARCHAR(30))
PARAMETER STYLE JAVA
READS SQL DATA
LANGUAGE JAVA
DYNAMIC RESULT SETS 1
EXTERNAL NAME 'org.bigsnow.test.TestProcedures.DAVE_TEST_PROC'
;

-- Load the jar file.
CALL SQLJ.INSTALL_JAR('/Users/dwinters/Documents/workspace/dave_test/jars/dave_test.jar', 'APP.DAVE_TEST_JAR', 0)
;

-- Update the jar file.
call sqlj.replace_jar('/Users/dwinters/Documents/workspace/dave_test/jars/dave_test.jar', 'APP.DAVE_TEST_JAR')
;

-- Remove the jar file.
call sqlj.remove_jar('APP.DAVE_TEST_JAR', 0)
;

-- Update the CLASSPATH for the database.
CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.database.classpath', 'APP.DAVE_TEST_JAR')
;

-- Execute the procedure.
call APP.DAVE_TEST_PROC('FooBar')
;

-- From DefaultSystemProcedureGenerator.java
                ,
            Procedure.newBuilder().name("SYSCS_DAVE")
                .numOutputParams(0).numResultSets(1).readsSqlData()
                .returnType(null).isDeterministic(false)
                .ownerClass(SYSTEM_PROCEDURES)
                .catalog("procName")
                .catalog("procName2")
                .build()

-- From SystemProcedures.java
	/**
	 * Test stored procedure.
	 *
	 * @param procName name of stored procedure
	 * @param rs       output parameter, the result set object containing the result
	 */
	public static void SYSCS_DAVE(String procName, String procName2, ResultSet[] rs)
		throws SQLException
	{
		System.out.println("SYSCS_DAVE: Start");
		Connection conn = DriverManager.getConnection("jdbc:default:connection");
		PreparedStatement ps = conn.prepareStatement("select tableid, tablename from SYS.SYSTABLES");
		rs[0] = ps.executeQuery();
		conn.close();
		System.out.println("SYSCS_DAVE: Done");
	}


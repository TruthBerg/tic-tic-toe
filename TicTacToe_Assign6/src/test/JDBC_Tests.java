package test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;

import javax.swing.plaf.synth.SynthSeparatorUI;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JDBC_Tests {
	private static final String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	private static final String DBNAME = "JDBC_Tests";
	private static final String connectionURL = "jdbc:derby:" + DBNAME + ";create=true";

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			System.out.println("Setup failure!\nError! Cannot start derby!");
			System.out.println(e.toString());
			throw e;
		}
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		try {
		DriverManager.getConnection("jdbc:derby:;shutdown=true");
		}catch (SQLException e) {
			if(!"Derby system shutdown.".equals(e.getMessage())) throw e;
		}
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testBootDB() {
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(connectionURL);
		} catch (SQLException e) {

			System.out.println(e.toString());
		}
		assertNotNull(conn);
	}

}

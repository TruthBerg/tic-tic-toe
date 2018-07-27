package main;
/*

   Derby - Class SimpleApp

   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Properties;


/**
 * <p>
 * This sample program is a minimal Java application showing JDBC access to a
 * Derby database.</p>
 * <p>
 * Instructions for how to run this program are
 * given in <A HREF=example.html>example.html</A>, by default located in the
 * same directory as this source file ($DERBY_HOME/demo/programs/simple/).</p>
 * <p>
 */
public class Database
{
    /* the default framework is embedded */
    private String framework = "embedded";
    private String protocol = "jdbc:derby:";
    
    /* We will be using Statement and PreparedStatement objects for
     * executing SQL. These objects, as well as Connections and ResultSets,
     * are resources that should be released explicitly after use, hence
     * the try-catch-finally pattern used below.
     * We are storing the Statement and Prepared statement object references
     * in an array list for convenience.
     */
    Connection conn = null;
    ArrayList<Statement> statements = new ArrayList<Statement>(); // list of Statements, PreparedStatements
    PreparedStatement psInsert;
    PreparedStatement psUpdate;
    Statement s;
    ResultSet rs = null;

 
        
    public static void main(String[] args)
    {
       Database db = new Database();
      // db.connectDB();
       db.insertPlayer("X", 0, 0, 0);
       db.insertWinner(1, 1);
        
        System.out.println("SimpleApp finished");
    }

    /**
     * @param args - Optional argument specifying which framework or JDBC driver
     *        to use to connect to Derby. Default is the embedded framework,
     *        see the <code>main()</code> method for details.
     * @see #main(String[])
     */
    void connectDB()
    {
        /* parse the arguments to determine which framework is desired*/
        //parseArguments(args);

        System.out.println("SimpleApp starting in " + framework + " mode");

       
        try
        {
            Properties props = new Properties(); // connection properties
            // providing a user name and password is optional in the embedded
            // and derbyclient frameworks
            props.put("user", "user1");
            props.put("password", "user1");

            String dbName = "derbyDB"; // the name of the database
            conn = DriverManager.getConnection(protocol + dbName
                    + ";create=true", props);

            System.out.println("Connected to and created database " + dbName);

            // We want to control transactions manually. Autocommit is on by
            // default in JDBC.
           conn.setAutoCommit(false);
           

  
        
    	/* Creating a statement object that we can use for running various
            * SQL statements commands against the database.*/
   
           s = conn.createStatement();
           statements.add(s);

           // We create a table...
           s.execute("CREATE TABLE player("
           		+ "id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
           		+ " marker varchar(1), "
           		+ "wins int, losses int, "
           		+ "draws int)"
           		);
           System.out.println("Created table 'player'");
            
           s.execute("CREATE TABLE winner("
           		+ "game_id int PRIMARY KEY,"
           		+ " player_id int)"
           		);
           System.out.println("Created table 'winner'");
    	} catch(SQLException sqle) {
    		printSQLException(sqle);
    	}
    } 	
    
    void insertPlayer(String marker, int wins, int losses, int draws) {


            /* It is recommended to use PreparedStatements when you are
             * repeating execution of an SQL statement. PreparedStatements also
             * allows you to parameterize variables. By using PreparedStatements
             * you may increase performance (because the Derby engine does not
             * have to recompile the SQL statement each time it is executed) and
             * improve security (because of Java type checking).
             */
            // parameter 1 is num (int), parameter 2 is addr (varchar)
    	try {
    		if(conn == null)  connectDB();
            psInsert = conn.prepareStatement(
                        "insert into player "
                        + "(marker, wins, losses, draws) "
                        + "values (?, ?, ?, ?)"
                        );
            statements.add(psInsert);

        
            psInsert.setString(1, marker);
            psInsert.setInt(2, wins);
            psInsert.setInt(3, losses);
            psInsert.setInt(4, draws);
            psInsert.execute();
            System.out.println("Inserted Player " + marker);
      	} catch (SQLException sqle) {
    		printSQLException(sqle);
    	}
    	
    	// Check inserted rows by selecting them
    	
    	
    	/*try {
    		System.out.println("Selecting...");
            rs = s.executeQuery(
                    "SELECT * FROM player ORDER BY wins");
           while(rs.next()) {
            	System.out.println(""
            			+ rs.getString("marker") + ", "
            			+ rs.getInt("wins") + ", "
            			+ rs.getInt("losses") + ", "
            			+ rs.getInt("draws")
            			);
            }
        } catch (SQLException sqle) {
       		printSQLException(sqle);
       	}*/
    	
    }
    
    void insertWinner(int game_id, int player_id) {
    	try {
    		if(conn == null)  connectDB();
            psInsert = conn.prepareStatement(
                        "INSERT INTO winner (game_id, player_id) VALUES (?,?)");
            statements.add(psInsert);
            psInsert.setInt(1, game_id);
            psInsert.setInt(2, player_id);
            psInsert.execute();
            System.out.println("Inserted Winner of game " + game_id);
      	} catch (SQLException sqle) {
    		printSQLException(sqle);
    	}
    	
    	// Check by selecting inserted rows
    	
    }
    
    void selectFromPlayer(int id, String marker, int wins, int losses, int draws) {
    	try {
    		if(conn == null)  connectDB();
            rs = s.executeQuery(
                    "SELECT * FROM player ORDER BY wins");
            while(rs.next()) {
            	System.out.println(
            			rs.getInt("id") + ", " 
            			+ rs.getString("marker") + ", "
            			+ rs.getInt("wins") + ", "
            			+ rs.getInt("losses") + ", "
            			+ rs.getInt("draws")

            			);
            }
    	} catch (SQLException sqle) {
    		printSQLException(sqle);
    	}
    }
    
	void selectFromWinner() {
		try {
			if(conn == null)  connectDB();
			rs = s.executeQuery("SELECT winner.game_id, player.marker FROM winner JOIN player ON winner.player_id = player.id ");
		} catch (SQLException sqle) {
    		printSQLException(sqle);
		}
	}
	
	void updatePlayer(String marker, int wins, int losses, int draws) {
		try {
			if (conn == null)  connectDB();
			psUpdate = conn.prepareStatement("UPDATE player SET wins=?, losses=?, draws=? WHERE marker=?");
			System.out.println("Updating player table...");
			
			psUpdate.setInt(1, wins);
			psUpdate.setInt(2, losses);
			psUpdate.setInt(3, draws);
			psUpdate.setString(4, marker);
			// execute update
			psUpdate.executeUpdate();
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
    	
	void closeDB() {
		try {
			// delete the table
        	s.execute("DROP TABLE player");
        	s.execute("DROP TABLE winner");
        	//System.out.println("Dropped table location");
        	
        	// commit the transaction
        	conn.commit();
        	System.out.println("Committed the transaction");
            	

            /*
             * In embedded mode, an application should shut down the database.
             * If the application fails to shut down the database,
             * Derby will not perform a checkpoint when the JVM shuts down.
             * This means that it will take longer to boot (connect to) the
             * database the next time, because Derby needs to perform a recovery
             * operation.
             *
             * It is also possible to shut down the Derby system/engine, which
             * automatically shuts down all booted databases.
             *
             * Explicitly shutting down the database or the Derby engine with
             * the connection URL is preferred. This style of shutdown will
             * always throw an SQLException.
             *
             * Not shutting down when in a client environment, see method
             * Javadoc.
             */

            if (framework.equals("embedded"))
            {
                try
                {
                    // the shutdown=true attribute shuts down Derby
                    DriverManager.getConnection("jdbc:derby:;shutdown=true");

                    // To shut down a specific database only, but keep the
                    // engine running (for example for connecting to other
                    // databases), specify a database in the connection URL:
                    //DriverManager.getConnection("jdbc:derby:" + dbName + ";shutdown=true");
                }
                catch (SQLException se)
                {
                    if (( (se.getErrorCode() == 50000)
                            && ("XJ015".equals(se.getSQLState()) ))) {
                        // we got the expected exception
                        System.out.println("Derby shut down normally");
                        // Note that for single database shutdown, the expected
                        // SQL state is "08006", and the error code is 45000.
                    } else {
                        // if the error code or SQLState is different, we have
                        // an unexpected exception (shutdown failed)
                        System.err.println("Derby did not shut down normally");
                        printSQLException(se);
                    }
                }
            }
      	} catch(SQLException sqle) {
    		printSQLException(sqle);
    	} finally {
        
            // release all open resources to avoid unnecessary memory usage

            // ResultSet
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
            } catch (SQLException sqle) {
                printSQLException(sqle);
            }

            // Statements and PreparedStatements
            int i = 0;
            while (!statements.isEmpty()) {
                // PreparedStatement extend Statement
                Statement st = (Statement)statements.remove(i);
                try {
                    if (st != null) {
                        st.close();
                        st = null;
                    }
                } catch (SQLException sqle) {
                    printSQLException(sqle);
                }
            }

            //Connection
            try {
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLException sqle) {
                printSQLException(sqle);
            }
    	}
    }
    
    
    /**
     * Reports a data verification failure to System.err with the given message.
     *
     * @param message A message describing what failed.
     */
    private void reportFailure(String message) {
        System.err.println("\nData verification failed:");
        System.err.println('\t' + message);
    }

    /**
     * Prints details of an SQLException chain to <code>System.err</code>.
     * Details included are SQL State, Error code, Exception message.
     *
     * @param e the SQLException from which to print details.
     */
    public static void printSQLException(SQLException e)
    {
        // Unwraps the entire exception chain to unveil the real cause of the
        // Exception.
        while (e != null)
        {
            System.err.println("\n----- SQLException -----");
            System.err.println("  SQL State:  " + e.getSQLState());
            System.err.println("  Error Code: " + e.getErrorCode());
            System.err.println("  Message:    " + e.getMessage());
            // for stack traces, refer to derby.log or uncomment this:
            //e.printStackTrace(System.err);
            e = e.getNextException();
        }
    }

    /**
     * Parses the arguments given and sets the values of this class's instance
     * variables accordingly - that is, which framework to use, the name of the
     * JDBC driver class, and which connection protocol to use. The
     * protocol should be used as part of the JDBC URL when connecting to Derby.
     * <p>
     * If the argument is "embedded" or invalid, this method will not change
     * anything, meaning that the default values will be used.</p>
     * <p>
     * @param args JDBC connection framework, either "embedded" or "derbyclient".
     * Only the first argument will be considered, the rest will be ignored.
     */
    private void parseArguments(String[] args)
    {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("derbyclient"))
            {
                framework = "derbyclient";
                protocol = "jdbc:derby://localhost:1527/";
            }
        }
    }
}

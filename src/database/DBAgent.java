package database;

import java.sql.Connection;
import java.sql.DriverManager;


public class DBAgent {

    /**
    * This class accesses the database
    * - Open connection
    * - Execute SQl queries
    * - Get SQL ResultSet
    * - Close Statements
    * - Close connection 
    */

	private String sqlAddress =  "jdbc:postgresql://52.24.215.108/GetA1";
	private String sqlUsername = "GetA1"; 
	private String sqlPassword = "GetA1"; 
	

	
	Connection cSQL = null; // SQL connection object
	
	// Empty initialiser
	public DBAgent() {		
		
	}
	
	// Open a database connection, return true if success
	public boolean openConnection()
	{
		try {
			// Use postgresql driver
			Class.forName("org.postgresql.Driver");
			cSQL = DriverManager.getConnection(sqlAddress, sqlUsername, sqlPassword);
        	        
	        // Verify that the connection is OK
	        if (cSQL != null)
	        {	        
	        	return true; // valid connection return true, else fall through and return false
	        }
		
		} 
		catch (Exception e) {
			// Print exception information
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }	
        
		return false; // fall through, return false, connection not established
	}

	// Close a database connection
	public void closeConnection()
	{
		// CLose an active SQL connection. OK no-op if already closed
		try {			
				cSQL.close();
		} 
		catch (Exception e) {
			// Print exception information
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }	
	}

	
	// Send a SQL query, return a ResultSet object
	public java.sql.ResultSet sendQuery(String sQuery) {
		java.sql.Statement qState = null; // create a SQL statement object
		java.sql.ResultSet qResult = null;

		try {
			
			qState = cSQL.createStatement(); // create a SQL statement object		
			qResult = qState.executeQuery(sQuery);						
		}
		catch (Exception e) {
			// Print exception information
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }	

		return qResult;
		
	}
	
	// Send a SQL update
	public void sendUpdate(String sUpdate) {
		java.sql.Statement qState = null; // create a SQL statement object
		
		try {
			
			qState = cSQL.createStatement(); 
			qState.executeUpdate(sUpdate);	// send update					
		}
		catch (Exception e) {
//			 Print exception information
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }			
	}

	/*
	 * updateGame method
	 * Adds game information to the database
	 * GameStatus table parameters: GameID, DrawTimes, RoundsPlayed, Winner
	 * PlayerPerformance table parameters: GameID, PlayerName, WinTimes
	 */
	public void updateGameStatus(int nGameID, int nRoundsPlayed, int nDrawTimes, String sWinner) {
		
		String sUpdate;
		
		sUpdate = "INSERT INTO  GAMESTATUS VALUES ('" 
				+ nGameID + "', '" 
				+ nRoundsPlayed + "', '" 
				+ nDrawTimes + "', '{" 
				+ sWinner + "}');"; 
        sendUpdate(sUpdate);
		
	}

	/*
	 * updateGame method
	 * Adds game information to the database
	 * GameStatus table parameters: GameID, DrawTimes, RoundsPlayed, Winner
	 * PlayerPerformance table parameters: GameID, PlayerName, WinTimes
	 */
	public void updatePlayerStatus(int nGameID, String sPlayerName, int nWinTimes) {
		
		String sUpdate;
		
		sUpdate = "INSERT INTO  PLAYERPERFORMANCE VALUES ('" 
				+ nGameID + "', '{" 
				+ sPlayerName + "}', '"
				+ nWinTimes + "');"; 
		
        sendUpdate(sUpdate);
		
	}

	/* Query 1
	 * 1. Get total game played overall:
	 * SELECT COUNT(*) AS TOTAL_GAME_NUMBER
	 * FROM GAMESTATUS
	 */	
	public int getTotalGamesPlayed() {
		
		int nTotal = 0;
		java.sql.ResultSet rResultSet;
		String sQuery = "SELECT COUNT(*) FROM GAMESTATUS";
		
		try {  	      
				rResultSet = sendQuery(sQuery);
				while (rResultSet.next()) {
					nTotal = rResultSet.getInt(1);
				}
		}
		catch (Exception e) {
				// Print exception information
	           e.printStackTrace();
	           System.err.println(e.getClass().getName()+": "+e.getMessage());
	           System.exit(0);
	    }		
		return nTotal;
	
	}

	/* Query 2
	 * 2. Get number of AI wins:
	 * SELECT COUNT(*) AS NUMBER_OF_AI_WINS
	 * FROM GAMESTATUS
	 * WHERE WINNER = AI
	 */
	public int getAIWins() {
		
		int nTotal = 0;
		java.sql.ResultSet rResultSet;
		String sQuery = "SELECT COUNT(*) FROM GAMESTATUS WHERE WINNER != '{YOU}' ";
		
		try {  	      
				rResultSet = sendQuery(sQuery);
				while (rResultSet.next()) {
					nTotal = rResultSet.getInt(1);
				}
		}
		catch (Exception e) {
				// Print exception information
	           e.printStackTrace();
	           System.err.println(e.getClass().getName()+": "+e.getMessage());
	           System.exit(0);
	    }		
		return nTotal;
		
	}
	
	/* Query 3
	 * 3.  Get number of human wins:
	 * SELECT COUNT(*) AS NUMBER_OF_HUMAN_WINS
	 * FROM GAMESTATUS
	 * WHERE WINNER = USER
	 */
	public int getHumanWins() {
		
		int nTotal = 0;
		java.sql.ResultSet rResultSet;
		String sQuery = "SELECT COUNT(*) FROM GAMESTATUS WHERE WINNER = '{You}'";
		
		try {  	      
				rResultSet = sendQuery(sQuery);
				while (rResultSet.next()) {
					nTotal = rResultSet.getInt(1);
				}
		}
		catch (Exception e) {
				// Print exception information
	           e.printStackTrace();
	           System.err.println(e.getClass().getName()+": "+e.getMessage());
	           System.exit(0);
	    }		
		return nTotal;
		
	}
	
	/* Query 4
	 * 4. Get average draws
	 * SELECT Avg(DrawTimes) AS AVERAGE_DRAWS
	 * FROM GAMESTATUS 
	 */
	public double getAvgDraws() {
		
		double rTotal = 0;  //to double
		java.sql.ResultSet rResultSet;
		String sQuery = "SELECT AVG(DRAWTIMES) FROM GAMESTATUS";
		
		try {  	      
				rResultSet = sendQuery(sQuery);
				while (rResultSet.next()) {
					rTotal = rResultSet.getDouble(1); //to double
				}
		}
		catch (Exception e) {
				// Print exception information
	           e.printStackTrace();
	           System.err.println(e.getClass().getName()+": "+e.getMessage());
	           System.exit(0);
	    }		
		return rTotal;
		
	}
	
	/* Query 5
	 * 5. Get the largest number of rounds played in a single game
	 * SELECT Max(RoundsPlayed) AS MOST_ROUNDS_PLAYED
	 * FROM GAMESTATUS 
	 */
	public int getLargestRoundsPlayed() {
		
		int nTotal = 0; 
		java.sql.ResultSet rResultSet; 
		String sQuery = "SELECT MAX(ROUNDSPLAYED) FROM GAMESTATUS";
		
		try {  	      
				rResultSet = sendQuery(sQuery);
				while (rResultSet.next()) {
					nTotal = rResultSet.getInt(1);
				}
		}
		catch (Exception e) {
				// Print exception information
	           e.printStackTrace();
	           System.err.println(e.getClass().getName()+": "+e.getMessage());
	           System.exit(0);
	    }		
		return nTotal;
		
	}

	/* Query 6
	 * 6. Get the maximum Game ID
	 * SELECT Max(GameID) AS MAX_GAME_ID
	 * FROM GAMESTATUS 
	 */

	public int getMaxGameID() {
		
		int nTotal = 0; 
		String sQuery = "SELECT MAX(GAMEID) FROM GAMESTATUS";
		
		try {  	      
				java.sql.ResultSet rResultSet = sendQuery(sQuery);
				if (rResultSet.next()) {
					nTotal = rResultSet.getInt(1);
				}
		}
		catch (Exception e) {
				// Print exception information
	           e.printStackTrace();
	           System.err.println(e.getClass().getName()+": "+e.getMessage());
	           System.exit(0);
	    }	
		
		return nTotal;
		
	}

}
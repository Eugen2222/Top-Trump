package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;


public class DBTest {

    /**
    * Main Method - gets the database username and password and then runs a connection test
    * @param args
    */
	
    public static void main(String args[]) {
        // input from standard in
    	DBAgent dbA = new DBAgent(); // use a single DBAgent instance to connect to a database
    	int nResult = -1;
       
        // Test open connection
        if (dbA.openConnection()) {
        	 System.out.println("Database is online and available,");
        } else {
        	System.out.println("Database is NOT online. Exiting");
        	return;
        }
        
     System.out.print(dbA.getTotalGamesPlayed());
        try {
    	      
           java.sql.ResultSet sResult =  dbA.sendQuery("SELECT * FROM GAMESTATUS");
	       while (sResult.next()) {
				System.out.println("GameID: " + sResult.getString("GAMEID"));
	       }
		}
		catch (Exception e) {
			// Print exception information
           e.printStackTrace();
           System.err.println(e.getClass().getName()+": "+e.getMessage());
           System.exit(0);
       }	
        
   
        dbA.updateGameStatus(12, 5, 3, "YOU");
        dbA.updatePlayerStatus(132, "YOU", 7);
    
        nResult = dbA.getTotalGamesPlayed();
		System.out.println("TOTAL_GAME_NUMBER: " + nResult);
		
        nResult = dbA.getAIWins();
		System.out.println("TOTAL_AI_WINS: " + nResult);
		
        nResult = dbA.getHumanWins();
		System.out.println("TOTAL_HUMAN_WINS: " + nResult);
		
        Double nResultDouble = dbA.getAvgDraws();
		System.out.println("AVERAGE_DRAWS: " + nResultDouble);
				
        nResult = dbA.getLargestRoundsPlayed();
		System.out.println("MOST_ROUNDS_PLAYED: " + nResult);
		
//       Test close connection
        dbA.closeConnection();
        System.out.println("Database is closed.");
    }

}

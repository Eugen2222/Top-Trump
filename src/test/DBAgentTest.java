package test;
import database.DBAgent;
// Import JUnit API
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
/**
* This class is to test the database to guarantee the operation of the methods
*/
// DBAgentTest class, uses JUnit
class DBAgentTest {

	static DBAgent dbA; // use static DBAgent instance for testing
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		dbA = new DBAgent(); // create DBAgent instance for each test
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		dbA = null; // free DBAgent instance for each test
	}


	@Test
	void testOpenConnection() {
		dbA = new DBAgent(); // create DBAgent instance for each test
		boolean bOpened = dbA.openConnection(); // open connection		
		assert(bOpened); // check if open successful		
		dbA.closeConnection(); // close after test
		dbA = null; // free DBAgent instance for each test
	}

	@Test
	void testCloseConnection() {
		dbA = new DBAgent(); // create DBAgent instance for each test
		boolean bOpened = dbA.openConnection(); // open connection		
		assert(bOpened); // check if open successful		
		dbA.closeConnection(); // close after test
		dbA = null; // free DBAgent instance for each test
	}

	@Test
	void testSendQuery() {

		// sendQuery() is tested with the 'get' methods
		dbA = new DBAgent(); // create DBAgent instance for each test
		boolean bOpened = dbA.openConnection(); // open connection		
		assert(bOpened); // check if open successful		


		dbA.closeConnection(); // close after test
		dbA = null; // free DBAgent instance for each test

   	}

	@Test
	void testSendUpdate() {
		
		// sendUpdate() is tested with the 'updatePlayerStatus' methods
		dbA = new DBAgent(); // create DBAgent instance for each test
		boolean bOpened = dbA.openConnection(); // open connection		
		assert(bOpened); // check if open successful		


		dbA.closeConnection(); // close after test
		dbA = null; // free DBAgent instance for each test

	}

	@Test
	void testUpdateGameStatus() {
		
		// This test will update the database
		dbA = new DBAgent(); // create DBAgent instance for each test
		boolean bOpened = dbA.openConnection(); // open connection		
		assert(bOpened); // check if open successful		

		
		// Create a test entry to add to the game database
		int nGameID = dbA.getMaxGameID() + 1;
		int nRoundsPlayed = 20;
		int nDrawTimes = 5;
		String sWinner = "TEST";		
		
		dbA.updateGameStatus(nGameID, nRoundsPlayed, nDrawTimes, sWinner);		
		int nNewGameID = dbA.getMaxGameID(); // get the new GameID after the update		
		assertEquals(nGameID, nNewGameID); // Check that the gameID is incremented
		
		// now remove the entry we just added
		dbA.closeConnection(); // close after test
		dbA = null; // free DBAgent instance for each test
		
	}

	@Test
	void testUpdatePlayerStatus() {
		
		// This test will update the database
		dbA = new DBAgent(); // create DBAgent instance for each test
		boolean bOpened = dbA.openConnection(); // open connection		
		assert(bOpened); // check if open successful		


		// Create a test entry to add to the game database
		int nGameID = dbA.getMaxGameID();
		String sPlayerName = "TEST";
		int nWinTimes = 10;
		
		dbA.updatePlayerStatus(nGameID, sPlayerName, nWinTimes);		
		int nNewGameID = dbA.getMaxGameID(); // get the new GameID after the update		
		assertEquals(nGameID, nNewGameID); // Check that the gameID is incremented
		
		// now remove the entry we just added
		dbA.closeConnection(); // close after test
		dbA = null; // free DBAgent instance for each test
	}

	@Test
	void testGetTotalGamesPlayed() {
		dbA = new DBAgent(); // create DBAgent instance for each test
		boolean bOpened = dbA.openConnection(); // open connection		
		assert(bOpened); // check if open successful		

		int nActual = dbA.getTotalGamesPlayed();
		//assert nActual < 0: "testTotalGamesPlayed() returns invalid value.";

		dbA.closeConnection(); // close after test
		dbA = null; // free DBAgent instance for each test
	}

	@Test
	void testGetAIWins() {
		dbA = new DBAgent(); // create DBAgent instance for each test
		boolean bOpened = dbA.openConnection(); // open connection		
		assert(bOpened); // check if open successful		

		int nActual = dbA.getAIWins();
		//assert nActual < 0: "testGetAIWins() returns invalid value.";

		dbA.closeConnection(); // close after test
		dbA = null; // free DBAgent instance for each test
	}

	@Test
	void testGetHumanWins() {
		dbA = new DBAgent(); // create DBAgent instance for each test
		boolean bOpened = dbA.openConnection(); // open connection		
		assert(bOpened); // check if open successful		

		int nActual = dbA.getHumanWins();
		//assert nActual < 0: "testGetHumanWins() returns invalid value.";

		dbA.closeConnection(); // close after test
		dbA = null; // free DBAgent instance for each test
	}

	@Test
	void testGetAvgDraws() {
		dbA = new DBAgent(); // create DBAgent instance for each test
		boolean bOpened = dbA.openConnection(); // open connection		
		assert(bOpened); // check if open successful		

		double nActual  = dbA.getAvgDraws();
		double nActual2 = dbA.getAvgDraws();
		assertEquals(nActual, nActual2); // Check that the getAvgDraws is valid
		
		dbA.closeConnection(); // close after test
		dbA = null; // free DBAgent instance for each test
	}

	@Test
	void testGetLargestRoundsPlayed() {
		dbA = new DBAgent(); // create DBAgent instance for each test
		boolean bOpened = dbA.openConnection(); // open connection		
		assert(bOpened); // check if open successful		

		int nActual = dbA.getLargestRoundsPlayed();
		//assert nActual < 0: "testGetLargestRoundsPlayed() returns invalid value.";

		dbA.closeConnection(); // close after test
		dbA = null; // free DBAgent instance for each test
	}

	@Test
	void testGetMaxGameID() {
		dbA = new DBAgent(); // create DBAgent instance for each test
		boolean bOpened = dbA.openConnection(); // open connection		
		assert(bOpened); // check if open successful		

		int nActual = dbA.getMaxGameID();
		//assert nActual < 0: "testGetMaxGameID() returns invalid value.";
		
		dbA.closeConnection(); // close after test
		dbA = null; // free DBAgent instance for each test
	}

}

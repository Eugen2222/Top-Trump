package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import database.DBAgent;

public abstract class GameModel {
	protected List<Player> playerList;
	protected List<Card> cardPool;
	protected List<Card> commonPile;
	protected Card[] cardOnDeck;
	protected String[] cardAttribute;
	
	protected int numOfPlayer;
	protected int round;
	protected int numberOfDraws;
	
	//CLI
	protected Card winCard;
	
	protected Player roundWinner;
	protected Player activePlayer;
	
	protected int activePlayerIndex;
	protected int roundWinnerIndex;
	protected int roundSelectIndex;
	protected int finalWinnerIndex;
	
	protected boolean gameIsOver;
	protected boolean humanLose;
	
	// TestLog
	private String testLog;

	
	// Initialise the game
	public void initialiseGame(int num) {
		numOfPlayer = num;
		
		playerList = new ArrayList<>();
		cardPool = new ArrayList<>();
		commonPile = new ArrayList<>();
		
		cardOnDeck = null;
		cardOnDeck = new Card[num];
		
		winCard = null;
		activePlayer = null;
		roundWinner = null;
		testLog = "";
		
		round = 0;
		numberOfDraws = 0;
		roundSelectIndex = 0;
		activePlayerIndex = -1;
		finalWinnerIndex = -1;
		gameIsOver = false;
		humanLose = false;
	
		defaultPlayer();
		defaultCard();
	}
	
	
	
	public String roundString() {
		return "Round " + round + ": ";
	}

	
	// Default player name base on input player numbers
	public void defaultPlayer() {
		for (int i = 0; i < numOfPlayer; i++) {
			if (i == 0) {
				Player player = new Player("You");
				playerList.add(player);
			} else {
				Player player = new Player("AI Player" + i);
				playerList.add(player);
			}
		}
	}
	
	// Import card from txt file
	public void readCard() {
		FileReader fr = null;
		int numLine = 0;
		try {
			String fN = "StarCitizenDeck.txt";
			fr = new FileReader(fN);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Scanner s = new Scanner(fr);

		while (s.hasNextLine()) {
			String line = s.nextLine();
			if (numLine == 0) {
				cardAttribute = line.split(" ");
				numLine++;
			} else {
				testLog += line + "\r";
				String[] card = line.split(" ");
				List<Integer> cardD = new ArrayList<>();

				for (int i = 1; i < card.length; i++) {
					cardD.add(Integer.parseInt(card[i]));
				}
				Card cardArray = new Card(card[0], cardD);
				cardPool.add(cardArray);
			}
		}
		try {
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		testLog += "--------------------------\r";
	}
	
	// Divide cards to a player
	public void defaultCard() {
		readCard();
		Collections.shuffle(cardPool);
		
		// testLog
		for (int i = 0; i < cardPool.size(); i++) {
			testLog += cardPool.get(i).getCardString() + "\r";
		}
		testLog += "--------------------------\r";
		
		// Each player get one card at order
		while (!cardPool.isEmpty()) {
			for (int i = 0; i < numOfPlayer && !cardPool.isEmpty(); i++) {
				playerList.get(i).getCardList().add(cardPool.get(0));
				cardPool.remove(cardPool.get(0));
			}
		} 
		
		// TestLog
		testLog += "defaultCard" + ":\r";
		for (int j = 0; j < numOfPlayer; j++) {
			testLog += playerList.get(j).getPlayerName() + ":\r";
			for (Card card: playerList.get(j).getCardList()) {
				testLog += card.getCardString() + "\r";
			}
			if (j < numOfPlayer - 1)
				testLog += "\r";
		}
		testLog += "--------------------------\r";
	}
	
	// Determine the player who chooses attributes
	public void decideActivePlayers() {
		// Random the active player in the first round
		if (roundWinner == null) {
			Random r = new Random();
			activePlayer = playerList.get(r.nextInt(numOfPlayer));

		} else {
			// Active player is the last round winner
			activePlayer = roundWinner;
		}
		
		for (int i = 1; i < playerList.size(); i++) {
			if (activePlayer.equals(playerList.get(i))) {
				activePlayerIndex = i;
			}
		}
		
		// TestLog
		testLog += "Active player: " + activePlayer.getPlayerName() + "\r";
		testLog += "--------------------------\r";

	}

	
	
	public void draw() {
		round++;
		for (int i = 0; i < numOfPlayer; i++) {
			if (playerList.get(i).aliveJudge()) {
				cardOnDeck[i] = playerList.get(i).getCardList().get(0);

			} else {
				cardOnDeck[i] = null;
			}
		}
		// TestLog
		testLog += "Draw:\r";
		
		for (int j = 0; j < cardOnDeck.length; j++) {
			if (cardOnDeck[j] != null) {
				testLog += playerList.get(j).getPlayerName() + ": " + cardOnDeck[j].getCardString() + "\r";
			}
		}
		testLog += "--------------------------\r";
	}



	// If human is active player, human need to select the attributes
	public boolean humanIsActivePlayer() {
		if (!activePlayer.equals(playerList.get(0))) {	
			return false;
		}
		return true;
	}

	public void humanSelect(int num) {
		roundSelectIndex = num;
		// TestLog
		testLog += "Category selected:\r" + cardAttribute[num] + ": " + cardOnDeck[0].getDescriptions().get(num - 1)
				+ "\r";
		testLog += "--------------------------\r";
		
	}
	
	// When AI is the active player
	public void AISelect() {

		int maxValue = 0;
		int bestChoice = -1;
		// Select the max value in all attributes
		for (int i = 0; i < cardAttribute.length - 1; i++) {
			int currentValue = cardOnDeck[activePlayerIndex].getDescriptions().get(i);

			if (currentValue > maxValue) {
				maxValue = currentValue;
				bestChoice = i;
				
			}
		}
		roundSelectIndex = bestChoice + 1;
		
		// TestLog
		testLog += "Category selected:\r" + cardAttribute[roundSelectIndex] + ": "
				+ cardOnDeck[activePlayerIndex].getDescriptions().get(bestChoice) + "\r";
		testLog += "--------------------------\r";

	}
	
	// Determine the round winner
	public int showWinner() {
		roundWinnerIndex = -1;
		int maxValue = 0;
		boolean draw = false;
		int roundResult = -1;		
		// record the types of the round result
		//0=Draw, 1=Human won,
		
		
		
		// Compare the selected attribute with other players
		for (int i = 0; i < cardOnDeck.length; i++) {
			if (cardOnDeck[i] != null) {
				int currentValue = cardOnDeck[i].getDescriptions().get(roundSelectIndex - 1);
				if (currentValue > maxValue) {
					maxValue = currentValue;
					roundWinnerIndex = i;
					draw = false;
				} else if (currentValue == maxValue) {
					roundWinnerIndex = i;
					draw = true;
				}
			}
		}

		winCard = cardOnDeck[roundWinnerIndex];// For command mode
		
		// If drew, add cards to the common pile
		if (draw) {
			numberOfDraws++;
			for (int i = 0; i < cardOnDeck.length; i++) {
				if (cardOnDeck[i] != null) {
					commonPile.add(cardOnDeck[i]);
					cardOnDeck[i]=null;
					this.playerList.get(i).getCardList().remove(0);
				}

			}
			roundWinner = activePlayer;
			roundResult = 0; //record the result
			
			// TestLog
			testLog += "CommonPile:\r";
			for (int j = 0; j < commonPile.size(); j++) {
				testLog += commonPile.get(j).getCardString() + "\r";
			}
			testLog += "--------------------------\r";
		} else {// Has winner
			playerList.get(roundWinnerIndex).addWin();
			if (roundWinnerIndex == 0) { //human won				
				roundWinner = playerList.get(roundWinnerIndex);
				roundResult = 1; //record the result
			} else {	//AI won
				roundWinner = playerList.get(roundWinnerIndex);
				roundResult = 2;  //record the result
			}
			activePlayer = roundWinner;
			
			// The round winner would acquire all the cards in the common pile
			if (!commonPile.isEmpty()) {
				for (int i = 0; i < commonPile.size(); i++) {
					playerList.get(roundWinnerIndex).getCardList().add(commonPile.get(i));
				}
				commonPile.clear();
			}

			for (int i = 0; i < cardOnDeck.length; i++) {
				if (cardOnDeck[i] != null) {
					playerList.get(roundWinnerIndex).getCardList().add(cardOnDeck[i]);
					cardOnDeck[i]=null;
					playerList.get(i).getCardList().remove(0);
				}
			}

		}
		
		// TestLog
		testLog += "Round" + round + ":\rRound winner:" + roundWinner.getPlayerName() + "\r";
		testLog += "--------------------------\r";
		testLog += "After round" + round + "\r";
		for (int j = 0; j < numOfPlayer; j++) {
			testLog += playerList.get(j).getPlayerName() + "("+playerList.get(j).getNumOfCards()+")"+":\r";
			for (Card card : playerList.get(j).getCardList()) {
				testLog += card.getCardString() + "\r";
			}
			if (j < numOfPlayer - 1)
				testLog += "\r";
		}
		testLog += "--------------------------\r";
		
		
		return roundResult;	//send out result	
	}
	
	// Check if the game is over
	public int checkGameIsOver() {
		int gameResult = -1; 
		// record the game result
		//0=Human lose the game, 
		int aliveNum = 0;
		int winnerIndex = -1;
		
		for (int i = 0; i < numOfPlayer; i++) {	//find how many players are alive
			if(playerList.get(i).aliveJudge()) {
				aliveNum++;
				winnerIndex = i;
			}
		}
		
		if (humanLose==false&&!playerList.get(0).aliveJudge()) { //If human has lost?
			this.humanLose = true;
		}
		
		// If only one player is alive, the game is over
		if (aliveNum == 1) {
			if (winnerIndex == 0) {
				finalWinnerIndex = 0;
				gameResult = 0;
			} else {
				finalWinnerIndex = winnerIndex;
				gameResult = 1;					
			}
			gameIsOver = true;
			// Online Mode
			// TestLog
			testLog += "Game Winner: " + playerList.get(winnerIndex).getPlayerName();
		} else if (aliveNum == 0 || !roundWinner.aliveJudge()) { // Draw all the time......
			gameIsOver = true;
			testLog += "No game Winner: ";
		}
		return gameResult;
	}
	
	// Update the GameStatus and PlayerPeformance databases
	public void updateGameData() {

				// Database access object
				DBAgent dbA = new DBAgent(); // use a single DBAgent instance to connect to the database
								
				if(dbA.openConnection()) {
					
					// Get the maximum gameID from the database	
					
					int nGameID = dbA.getMaxGameID() +1; // get the next game ID
				 
					// Update game data
					dbA.updateGameStatus(nGameID, round, numberOfDraws, playerList.get(finalWinnerIndex).getPlayerName()); 
			
					// Update player data
					for(int i=0;i<playerList.size();i++) {				
						dbA.updatePlayerStatus(nGameID, playerList.get(i).getPlayerName(), playerList.get(i).getWinTimes());
					}
					// Close connection when done
					dbA.closeConnection();		
					System.out.println("\n\nUpdated game data successfully!");
				}else {
					System.err.println("Error, unable to connect database");
				}
		
	}

	// Get the game statistics and return as a String array
	public String[] getGameStats() {
		String [] gameStatistics = new String[5];
	
		DBAgent dbA = new DBAgent(); // use a single DBAgent instance to connect to the database
		if(dbA.openConnection()) {
				
			// Get game statistics
			gameStatistics[0] = " Number of Games: "+dbA.getTotalGamesPlayed();		// Total games played
			gameStatistics[1] = " Number of AI Wins: "+dbA.getAIWins();		 		// Number of AI wins	
		    gameStatistics[2] = " Number of Human Wins: "+dbA.getHumanWins();       // Number of Human wins	
		    DecimalFormat fmt = new DecimalFormat("##0.0");
		    gameStatistics[3] = " Average number of Draws: "+fmt.format(dbA.getAvgDraws());		// Average of draws		
		    gameStatistics[4] = " Longest Game: "+dbA.getLargestRoundsPlayed()+"\r";			// Most round played
				                     			
		    // Close connection when done
			dbA.closeConnection();		
		}else {
			gameStatistics[0] = "Error database is not available.";	// Total games played
			gameStatistics[1] = "";		 		// Number of AI wins	
		    gameStatistics[2] = "";       // Number of Human wins	
		    gameStatistics[3] = "";		// Average of draws		
		    gameStatistics[4] = "";
		}
		
		return gameStatistics;
	}
	
	// Generate TestLog file
	public void createLog() {
		try {
			File file = new File("toptrumps.log");
			if (!file.exists()) {
				file.createNewFile();
			} else {
				file.delete();
				file.createNewFile();

			}
			FileWriter fw = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(testLog);
			bw.close();
			System.out.println("Test log has been created successfully!");
		} catch (IOException e) {
			System.out.println("Exception occurred:");
			e.printStackTrace();
		}
	}
		
	public boolean getHumanIsActivePlayer() {
		if (activePlayer.equals(playerList.get(0))) {
			return true;
		} else {
			return false;
		}
	}

	public boolean getGameIsOver() {
		return gameIsOver;
	}

}
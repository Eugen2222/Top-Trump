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

public class GameModel {
	protected List<Player> playerList;
	protected List<Card> cardList;
	protected List<Card> commonPile;
	protected Card[] cardOnDeck;
	protected String[] cardAttribute;
	
	protected int numOfPlayer;
	protected int round;
	protected int numberOfDraws;
	
	protected Card winCard;
	
	protected Player roundWinner;
	protected Player activePlayer;
	
	protected int activePlayerIndex;
	protected int roundWinnerIndex;
	protected int roundSelectIndex;
	protected int finalWinnerIndex;
	
	protected int gameIsOver;
	protected int humanLose;
	
	// TestLog
	protected String testLog;

	// CLM
	protected String CLIStatus;
	protected int humanAliveCount = 1;
	// OnlineMode
	protected String WebInfo;
	protected String Webstatus;
	
	// Initialise the game
	public void initialiseGame(int num) {
		numOfPlayer = num;
		
		playerList = new ArrayList<>();
		cardList = new ArrayList<>();
		commonPile = new ArrayList<>();
		
		cardOnDeck = null;
		cardOnDeck = new Card[num];
		
		winCard = null;
		activePlayer = null;
		roundWinner = null;
		
		Webstatus = "";
		WebInfo = "";
		testLog = "";
		
		round = 0;
		numberOfDraws = 0;
		roundSelectIndex = 0;
		activePlayerIndex = -1;
		finalWinnerIndex = -1;
		gameIsOver = -1;
		humanLose = -1;
	
		defaultPlayer();
		defaultCard();
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
				cardList.add(cardArray);
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
		Collections.shuffle(cardList);
		
		// testLog
		for (int i = 0; i < cardList.size(); i++) {
			testLog += cardList.get(i).getCardString() + "\r";
		}
		testLog += "--------------------------\r";
		
		// Each player get one card serially
		while (!cardList.isEmpty()) {
			for (int i = 0; i < numOfPlayer && !cardList.isEmpty(); i++) {
				playerList.get(i).getCardList().add(cardList.get(0));
				cardList.remove(cardList.get(0));
			}
		} 
		
		// TestLog
		testLog += "defaultCard" + ":\r";
		for (int j = 0; j < numOfPlayer; j++) {
			testLog += playerList.get(j).getPlayerName() + ":\r";
			for (int jj = 0; jj < playerList.get(j).getCardList().size(); jj++) {
				testLog += playerList.get(j).getCardList().get(jj).getCardString() + "\r";
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
		CLIStatus ="";
	}

	
	
	public void draw() {
		round++;
		for (int i = 0; i < numOfPlayer; i++) {
			if (playerList.get(i).aliveJudge()) {
				cardOnDeck[i] = playerList.get(i).getCardList().get(0);
				playerList.get(i).getCardList().remove(0);
			} else {
				cardOnDeck[i] = null;
			}
		}
		
		// OnlineMode
		WebInfo = "The active player is " + activePlayer.getPlayerName() + ".";
		Webstatus = roundString() + "Players have drawn their cards.";
		
		// CLM
		CLIStatus = "\n"+"Round " + round + "\n" + roundString() + "Players have drawn their cards.";
		CLIStatus += "\n"+ roundString() +"The active player is " + activePlayer.getPlayerName() + ".";
		
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
	public int humanIsActivePlayer() {
		if (!activePlayer.equals(playerList.get(0))) {
			Webstatus = roundString() + "Waiting on " + activePlayer.getPlayerName() + " to select a category ";
			return -1;
		}
		Webstatus = roundString() + "Waiting on you to select a category ~ ";
		return 0;
	}

	public void humanSelect(int num) {
		roundSelectIndex = num;
		
		// CLM
		CLIStatus = "\n"+roundString() + "You selected " + cardAttribute[num] + ".";
		// Online Mode
		Webstatus = roundString() + "You selected " + cardAttribute[num] + ".";
		
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
		
		// CLM
		CLIStatus = "\n"+roundString() + activePlayer.getPlayerName() + " selected " + cardAttribute[roundSelectIndex] + ".";
		
		// Online Mode
		Webstatus = roundString() + activePlayer.getPlayerName() + " selected " + cardAttribute[roundSelectIndex] + ".";
		
		// TestLog
		testLog += "Category selected:\r" + cardAttribute[roundSelectIndex] + ": "
				+ cardOnDeck[activePlayerIndex].getDescriptions().get(bestChoice) + "\r";
		testLog += "--------------------------\r";

	}
	
	// Determine the round winner
	public void showWinner() {
		roundWinnerIndex = -1;
		int maxValue = 0;
		boolean drew = false;
		// Compare the selected attribute with other players
		for (int i = 0; i < cardOnDeck.length; i++) {
			if (cardOnDeck[i] != null) {
				int currentValue = cardOnDeck[i].getDescriptions().get(roundSelectIndex - 1);
				if (currentValue > maxValue) {
					maxValue = currentValue;
					roundWinnerIndex = i;
					drew = false;
				} else if (currentValue == maxValue) {
					roundWinnerIndex = i;
					drew = true;
				}
			}
		}

		winCard = cardOnDeck[roundWinnerIndex];// For command mode
		
		// If drew, add cards to the common pile
		if (drew) {
			numberOfDraws++;
			for (int i = 0; i < cardOnDeck.length; i++) {
				if (cardOnDeck[i] != null) {
					commonPile.add(cardOnDeck[i]);
				}

			}
			roundWinner = activePlayer;
			
			// CLM
			CLIStatus +="\n"+roundString() + "This round was a draw, common pile now has " + commonPile.size() + " cards.";
			
			// Online Mode
			Webstatus = roundString() + "This round was a draw, common pile now has " + commonPile.size() + " cards.";
			
			// TestLog
			testLog += "CommonPile:\r";
			for (int j = 0; j < commonPile.size(); j++) {
				testLog += commonPile.get(j).getCardString() + "\r";
			}
			testLog += "--------------------------\r";
		} else {// Has winner
			playerList.get(roundWinnerIndex).addWin();
			if (roundWinnerIndex == 0) {
				
				roundWinner = playerList.get(roundWinnerIndex);
				
				// CLM
				CLIStatus += "\n"+roundString() + "You won this round!!!";
				// Online Mode
				Webstatus = roundString() + "Congratulation, you won this round!";
			} else {
				
				roundWinner = playerList.get(roundWinnerIndex);
				
				// Online Mode
				Webstatus = roundString() + "Oh, " + playerList.get(roundWinnerIndex).getPlayerName()
						+ " won this round.";
				// CLM
				CLIStatus += "\n" + roundString() + "Player " + playerList.get(roundWinnerIndex).getPlayerName()
						+ " won this round.";
				
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
				}
			}

		}
		// CML
		CLIStatus += "\nThe winning card was '" + winCard.getCardName() + "' :";
		
		for (int i = 0; i < cardAttribute.length - 1; i++) {
			if (i == cardAttribute.length - 1 - 1) {
				CLIStatus += "\n\t> " + cardAttribute[i + 1] + ": "
						+ winCard.getDescriptions().get(cardOnDeck.length - 1);
			} else {
				CLIStatus += "\n\t> " + cardAttribute[i + 1] + ": " + winCard.getDescriptions().get(i);
			}
			if (i + 1 == roundSelectIndex) {
				CLIStatus += "\t<--";
			}

		}


		// TestLog
		testLog += "Round" + round + ":\rRound winner:" + roundWinner.getPlayerName() + "\r";
		testLog += "--------------------------\r";
		testLog += "After round" + round + "\r";
		for (int j = 0; j < numOfPlayer; j++) {
			testLog += playerList.get(j).getPlayerName() + "("+playerList.get(j).getNumOfCards()+")"+":\r";
			for (int jj = 0; jj < playerList.get(j).getCardList().size(); jj++) {
				testLog += playerList.get(j).getCardList().get(jj).getCardString() + "\r";
			}
			if (j < numOfPlayer - 1)
				testLog += "\r";
		}
		testLog += "--------------------------\r";
	}
	
	// Check if the game is over
	public void gameIsOver() {

		CLIStatus = "";
		int aliveNum = 0;
		int winnerIndex = -1;
		
		for (int i = 0; i < numOfPlayer; i++) {	//find how many players are alive
			if(playerList.get(i).aliveJudge()) {
				aliveNum++;
				winnerIndex = i;
			}
		}
		
		if (humanLose!=0&&!playerList.get(0).aliveJudge()) { //If human has lost?
			this.humanLose = 0;
			CLIStatus = "";
			//Webstatus = roundString() + " Sorry, you lose!";
			CLIStatus += "\n"+roundString() + "Sorry, you has lost!";
		}
		
		// CML
		CLIStatus += "\n\n";
		// If only one player is alive, the game is over
		if (aliveNum == 1) {
			if (winnerIndex == 0) {
				Webstatus = roundString() + "Congratulation, you won this game!";
				finalWinnerIndex = 0;
			} else {
				finalWinnerIndex = winnerIndex;
				
				Webstatus = roundString() + "Oh, " + playerList.get(winnerIndex).getPlayerName() + " won the game.";
				
			}
			gameIsOver = 0;
			// Online Mode
			WebInfo = "Sorry, the game is over.";
			// TestLog
			testLog += "Game Winner: " + playerList.get(winnerIndex).getPlayerName();
		} else if (aliveNum == 0 || !roundWinner.aliveJudge()) { // Draw all the time......
			gameIsOver = 0;
			// Online Mode
			Webstatus = roundString() + "Oh, someone won but now he has no card!";
			testLog += "No game Winner: ";
		}
	}
	
	
	
	// When human dies, the game will automatically proceed to the end
	public void autoPlay() {
		if(this.humanLose==0) {
			//System.out.println("autoPlay");
			String temp = "";
			CLIStatus = "";
			while (this.getGameIsOver() != 0) {
				this.decideActivePlayers();
				temp += CLIStatus;
				this.draw();
				temp += CLIStatus;
				this.AISelect();
				this.showWinner();
				temp += CLIStatus;
				this.gameIsOver();
				temp += CLIStatus;
			}
			CLIStatus =temp;
		}else {
			CLIStatus = "";		
		}
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
				}else {
					System.err.println("Error, unable to connect database");
				}
		
	}

	// Get the game statistics and return as a String array
	public String[] getGameStats() {
		String [] gameStatistics = new String[5];
	
		DBAgent dbA = new DBAgent(); // use a single DBAgent instance to connect to the database
		dbA.openConnection();
				
		// Get game statistics
		gameStatistics[0] = " Number of Games: "+dbA.getTotalGamesPlayed();		// Total games played
		gameStatistics[1] = " Number of AI Wins: "+dbA.getAIWins();		 		// Number of AI wins	
	    gameStatistics[2] = " Number of Human Wins: "+dbA.getHumanWins();       // Number of Human wins	
	    DecimalFormat fmt = new DecimalFormat("##0.0");
	    gameStatistics[3] = " Average number of Draws: "+fmt.format(dbA.getAvgDraws());		// Average of draws		
	    gameStatistics[4] = " Longest Game: "+dbA.getLargestRoundsPlayed()+"\r";			// Most round played
			                     			
	    // Close connection when done
		dbA.closeConnection();		
		
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
		
	public int getHumanLose() {
		return humanLose;
	}



	public int getGameIsOver() {
		return gameIsOver;
	}

	public int getNumOfPlayer() {
		return numOfPlayer;
	}

	public List<Player> getPlayerList() {
		return playerList;
	}

	public Card[] getCardOnDeck() {
		return cardOnDeck;
	}

	public int getRound() {
		return round;
	}

	public String getGameStatusWeb() {
		return Webstatus;
	}

	public String getGameInfoWeb() {
		return WebInfo;
	}

	public Card getWinCard() {
		return winCard;
	}

	public Player getRoundWinner() {
		return roundWinner;
	}

	public int getRoundWinnerIndex() {
		return roundWinnerIndex;
	}

	public boolean getHumanIsActivePlayer() {
		if (activePlayer.equals(playerList.get(0))) {
			return true;
		} else {
			return false;
		}
	}

	public List<Card> getCommonPile() {
		return commonPile;
	}

	public int getNumberOfDraws() {
		return numberOfDraws;
	}

	public Player getActivePlayer() {
		return activePlayer;
	}
	
	public String getCMCStatus() {
		return CLIStatus;
	}
		
	public String roundString() {
		return "Round " + round + ": ";
	}

	// Junit test
	public String[] getCardAttribute() {
		return cardAttribute;
	}
	
	public List<Card> getCardList(){
		return cardList;
	}
	
	public int getFinalWinnerIndex() {
		return finalWinnerIndex;
	}

}
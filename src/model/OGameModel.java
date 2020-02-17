package model;

import java.util.ArrayList;

public class OGameModel extends GameModel {
	
	private String WebInfo = "";
	private String Webstatus = "";
	
	public OGameModel() {
		
	}
	
	//Get the active player and show description
	public void draw() {
		super.draw();
		WebInfo = "The active player is " + activePlayer.getPlayerName() + ".";
		Webstatus = roundString() + "Players have drawn their cards.";
	}
	
	
	public boolean humanIsActivePlayer() {
		boolean humanIsActive = super.humanIsActivePlayer();
		// when human's turn
		if(humanIsActive) {
			Webstatus = roundString() + "Waiting on you to select a category ~ ";
			return true;
		}else {// when AI player's turn
			Webstatus = roundString() + "Waiting on " + activePlayer.getPlayerName() + " to select a category ";
			return false;
		}
	}
	
	//show the description when human select a attribute
	public void humanSelect(int num) {
		super.humanSelect(num);
		this.Webstatus = roundString() + "You selected " + cardAttribute[num] + ".";
		
	}
	
	//show the description when AI player selection 
	public void AISelect() {
		super.AISelect();
		this.Webstatus = roundString() + activePlayer.getPlayerName() + " selected " + cardAttribute[roundSelectIndex] + ".";
	}
	
	//show the winner description 
	public int showWinner() {
		int roundResult = super.showWinner();
		if(roundResult == 0) {
			Webstatus = roundString() + "This round was a draw, common pile now has " + commonPile.size() + " cards.";
		}
		else if(roundResult == 1) {
			Webstatus = roundString() + "Congratulation, you won this round!";
		}
		else if(roundResult == 2) {
			Webstatus = roundString() + "Oh, " + playerList.get(roundWinnerIndex).getPlayerName()
					+ " won this round.";
		}
		return roundResult;
	}
	
	public int checkGameIsOver() {
		int gameResult = super.checkGameIsOver();
		if(gameResult == 0) {
			Webstatus = roundString() + "Congratulation, you won this game!";
			WebInfo = "Oh, the game is over.";
		}
		else if(gameResult == 1) {
			Webstatus = roundString() + "Oh, " + playerList.get(finalWinnerIndex).getPlayerName()
					+ " won this round.";
			WebInfo = "Sorry, the game is over.";
		}
		else if(gameResult == 2) {
			Webstatus = roundString() + "Oh, someone won but now he has no card!";
			WebInfo = "Sorry, the game is over.";	
		}
		
		return gameResult;		
	}
	
	
	
	// when human has lost and the game is not over, AI player run the game 
	public void AIAutoPlay() {
		if(humanLose==true&&gameIsOver==false) {
			//System.out.println("autoPlay");
			while (this.getGameIsOver() == false) {
				this.decideActivePlayers();
				this.draw();
				this.AISelect();
				this.showWinner();
				this.checkGameIsOver();
			}
		}
	}
	
	
	public String getGameStatusWeb() {
		return this.Webstatus;
	}
	
	public String getGameInfoWeb() {
		return this.WebInfo;
	}
	// Online Mode (show win card)
	public String[] showWinnerCard() {
		String [] s = new String [2];
			if(commonPile.isEmpty()) {
				s[0]=""+roundWinnerIndex;
				s[1]=getCardStringWeb(playerList.get(roundWinnerIndex).getPlayerName(), 
				playerList.get(roundWinnerIndex).getNumOfCards(), winCard);
			}
		return s;
	}
		
		// Online Mode (show the first card if there are cards in the common pile)
	public String[] getFirstCardInCommonPile() {
		String [] s = new String [2];
		if(!commonPile.isEmpty()) {
			s[0]=""+5;
			s[1]=getCardStringWeb("Common pile", commonPile.size()
				, winCard);
		}
		return s;
	}
		
	// Online Mode (show selected card information)
	private String getCardStringWeb(String name, int numOfCards, Card card) {
		String s = "";
		String temString = "";
		temString += name + "  ";
		temString += "Name:" + card.getCardName() + ",";
		temString += "x" + numOfCards + ","; 
		for (int j = 0; j < cardAttribute.length - 1; j++) {
			if (j == cardAttribute.length - 2) {
					temString += cardAttribute[j + 1] + ": "
							+ card.getDescriptions().get(cardAttribute.length - 2);
			} else {
				temString += cardAttribute[j + 1] + ": " + winCard.getDescriptions().get(j) + ",";
			}
		}
		s = temString;
		return s;
	}		
		// Online Mode (show drew cards each round) 
	public String[] getCardStringOnDeckWeb() {
		String[] s = new String[cardOnDeck.length];
		String temString = "";
		for (int i = 0; i < cardOnDeck.length; i++) {
			if (cardOnDeck[i] != null) {

				temString += playerList.get(i).getPlayerName() + "  ";
				temString += "Name:" + cardOnDeck[i].getCardName() + ",";
				temString += "x" + (playerList.get(i).getNumOfCards()) + ","; // plus the card on deck;
				for (int j = 0; j < cardAttribute.length - 1; j++) {
					if (j == cardAttribute.length - 2) {
						temString += cardAttribute[j + 1] + ": "
								+ cardOnDeck[i].getDescriptions().get(cardAttribute.length - 2);
					} else {
						temString += cardAttribute[j + 1] + ": " + cardOnDeck[i].getDescriptions().get(j) + ",";
					}
				}
				s[i] = temString;
				temString = "";
			} else {
				s[i] = null;
			}
		}
		return s;	
	}
		
		
	
	// Online Mode (show game results)
	public String[] getGameResultWeb() {
		ArrayList<String> s = new ArrayList<String>();
				
		if (finalWinnerIndex == -1) {
			s.add("Oh, no one won!!!");
		} else if (finalWinnerIndex == 0) {
			s.add("Congratulaton, the winner is you!!!");
			s.add("You won " + playerList.get(0).getWinTimes() + " rounds.");
		} else {
			s.add("The winner is:");
			s.add("	"+playerList.get(finalWinnerIndex).getPlayerName() + " won "
					+ playerList.get(finalWinnerIndex).getWinTimes() + " rounds.");
		}
		s.add("The losers are: ");
		for (int i = 0; i < playerList.size(); i++) {
			if (finalWinnerIndex == i) {
				
			} else {
				s.add("	"+playerList.get(i).getPlayerName() + " won " + playerList.get(i).getWinTimes() + " rounds.");
			}
		}
		s.add("Draw rounds: "+ numberOfDraws + " rounds.");
		s.add("Total rounds: "+ this.round + " rounds.");
				
		String[] strr = s.toArray(new String[s.size()]);
		return strr;
	}
	
	
	
}

package model;

import java.util.ArrayList;

public class CLIGameModel extends GameModel {
	private String CLIStatus;
	private boolean displayHumanLose = false;
	public CLIGameModel() {
		// TODO Auto-generated constructor stub
	}
	
	
	public void defaultCard() {
		super.defaultCard();
		CLIStatus ="";
	}
	
	
	public void draw() {
		super.draw();
		CLIStatus = "\n"+"Round " + round + "\n" + roundString() + "Players have drawn their cards.";
		CLIStatus += "\n"+ roundString() +"The active player is " + activePlayer.getPlayerName() + ".";
	}
	
	public void humanSelect(int num) {
		super.humanSelect(num);
		CLIStatus = "\n"+roundString() + "You selected " + cardAttribute[num] + ".";
		
	}
	
	public void AISelect() {
		super.AISelect();
		CLIStatus = "\n"+roundString() + activePlayer.getPlayerName() + " selected " + cardAttribute[roundSelectIndex] + ".";
	}
	
	
	public int showWinner() {
		int roundResult = super.showWinner();
		if(roundResult == 0) {
			CLIStatus +="\n"+roundString() + "This round was a draw, common pile now has " + commonPile.size() + " cards.";
		}
		else if(roundResult == 1) {
			CLIStatus += "\n"+roundString() + "You won this round!!!";
		}
		else if(roundResult == 2) {
			CLIStatus += "\n" + roundString() + "Player " + playerList.get(roundWinnerIndex).getPlayerName()
					+ " won this round.";
		}
		//show the winning card
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
		
		return roundResult;
	}
	
	public int checkGameIsOver() {
		CLIStatus = "";
		int gameResult = super.checkGameIsOver();
		if(displayHumanLose==false && humanLose == true) {
			CLIStatus += "\n"+roundString() + "Sorry, you has lost!";
			displayHumanLose = true;
		}
		CLIStatus += "\n\n";		
		return gameResult;	
	}
	
	
	
	
	// CLM (show game results)
	public String[] getGameResultCLI() {
		ArrayList<String> s = new ArrayList<String>();
		s.add("\n  Game End");
		s.add("----------------------------------");
		if (finalWinnerIndex == -1) {
			s.add("Oh, no one won!!!");
		} else if (finalWinnerIndex == 0) {
			s.add("Congratulaton, the winner is you!!!");
			s.add("You won " + playerList.get(0).getWinTimes() + " rounds.");

		} else {
			s.add("The winner is:");
			s.add("   "+playerList.get(finalWinnerIndex).getPlayerName() + " won "
					+ playerList.get(finalWinnerIndex).getWinTimes() + " rounds.");
		}
		s.add("The losers are: ");
		for (int i = 0; i < playerList.size(); i++) {
			if (finalWinnerIndex == i) {

			} else {
				s.add("   "+playerList.get(i).getPlayerName() + " won " + playerList.get(i).getWinTimes() + " rounds.");
			}
		}
		s.add("----------------------------------");
		s.add("Draw rounds: "+ numberOfDraws + " rounds.");
		s.add("Total rounds: "+ this.round + " rounds.");
		String[] strr = s.toArray(new String[s.size()]);
		return strr;
	}
	
	
	// CLI (show cards each round)
	public String[] getCardStringOnDeckCLI() {
		String[] s = new String[cardOnDeck.length];
		String CMCtemString = "";
		for (int i = 0; i < cardOnDeck.length; i++) {
			if (cardOnDeck[i] != null) {

				CMCtemString += "\nYou drew '" + cardOnDeck[i].getCardName() + "' :";
				for (int j = 0; j < cardAttribute.length - 1; j++) {
					if (j == cardAttribute.length - 1 - 1) {
						CMCtemString += "\n\t> " + cardAttribute[j + 1] + ": "
								+ cardOnDeck[i].getDescriptions().get(cardAttribute.length - 1 - 2);
					} else {
						CMCtemString += "\n\t> " + cardAttribute[j + 1] + ": " + cardOnDeck[i].getDescriptions().get(j);
					}
				}
				CMCtemString += "\nThere are '" + (playerList.get(i).getNumOfCards()+1) + " cards in your deck.";

				s[i] = CMCtemString;
				CMCtemString = "";
			} else {
				s[i] = null;
			}
		}
		return s;
	}
	
	public String getCMCStatus() {
		return this.CLIStatus;
	}
	
	public boolean getHumanLose() {
		return this.humanLose;
	}
	
}

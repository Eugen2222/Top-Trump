package model;

import java.util.ArrayList;

public class CLIGameModel extends GameModel {

	public CLIGameModel() {
		// TODO Auto-generated constructor stub
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
	
	
	
	
}

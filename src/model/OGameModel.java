package model;

import java.util.ArrayList;

public class OGameModel extends GameModel {

	public OGameModel() {
		// TODO Auto-generated constructor stub
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
		// Online Mode (show cards each round)
	public String[] getCardStringOnDeckWeb() {
		String[] s = new String[cardOnDeck.length];
		String temString = "";
		for (int i = 0; i < cardOnDeck.length; i++) {
			if (cardOnDeck[i] != null) {

				temString += playerList.get(i).getPlayerName() + "  ";
				temString += "Name:" + cardOnDeck[i].getCardName() + ",";
				temString += "x" + (playerList.get(i).getNumOfCards() + 1) + ","; // plus the card on deck;
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
	public String[] getGameResult() {
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
				
		String[] strr = s.toArray(new String[s.size()]);			return strr;
	}
}

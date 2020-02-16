package model;


import java.util.LinkedList;
import java.util.List;


public class Player {
	private String playerName;
	private List<Card> cardList = new LinkedList<>();
	private int winTimes;
	private boolean alive = true;

	// constructor
	public Player(String name) {
		playerName = name;
	}
	
    // Getter for the player name
	public String getPlayerName() {
		return playerName;
	}

//	public void addCard(Card card) {
//		cardList.add(card);
//	}
//	public void deleteCard(Card card) {
//		cardList.remove(card);
//	}
	
   
	public void addWin() {
		winTimes++;
	}
	
	// calculate the times of the win 
	public int getWinTimes() {
		return winTimes;
	}
	
	// Get the attributes of the cards
	public List<Card> getCardList(){
		return cardList;
	}
	
	// Get the card number in the deck of each player
	public int getNumOfCards(){
		if(cardList.isEmpty()) {
			return 0;
		}else {
			return cardList.size();
		}
	}
	
	// judge whether this player is alive or not
	public boolean aliveJudge() {
		if(cardList.isEmpty()) {
			alive = false;
		}
		return alive;
	}
}

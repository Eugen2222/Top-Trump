package model;


import java.util.ArrayList;
import java.util.List;


public class Player {
	private String playerName;
	private List<Card> cardList = new ArrayList<>();
	private int winTimes;
	private boolean alive = true;

	
	public Player(String name) {
		playerName = name;
	}
	

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
	public int getWinTimes() {
		return winTimes;
	}
	
	
	public void setCardList(List<Card> c) {
		cardList =c;
	}
	public List<Card> getCardList(){
		return cardList;
	}
	
	
	public int getNumOfCards(){
		if(cardList.isEmpty()) {
			return 0;
		}else {
			return cardList.size();
		}
	}
	
	
	public boolean aliveJudge() {
		if(cardList.isEmpty()) {
			alive = false;
		}
		return alive;
	}
}

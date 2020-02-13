package model;

import java.util.List;

public class TestGameModel extends GameModel{

	public TestGameModel() {
		// TODO Auto-generated constructor stub
	}
	
	// Junit test
	public String[] getCardAttribute() {
		return cardAttribute;
	}
	
	// Junit test	
	public List<Card> getCardPool(){
		return cardPool;
	}
	// Junit test	
	public int getFinalWinnerIndex() {
		return finalWinnerIndex;
	}
	
	// Junit test
	public int getRound() {
		return round;
	}
	
	// Junit test
	public int getNumOfPlayer() {
		return numOfPlayer;
	}
		
	// Junit test
	public List<Player> getPlayerList() {
		return playerList;
	}	
	
	// Junit test
	public List<Card> getCommonPile() {
		return commonPile;
	}
}

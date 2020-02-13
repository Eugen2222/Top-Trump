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
	
	//junit
	public int getRound() {
		return round;
	}
	
	//junit
	public int getNumOfPlayer() {
		return numOfPlayer;
	}
		
		//junit
	public List<Player> getPlayerList() {
		return playerList;
	}	
}

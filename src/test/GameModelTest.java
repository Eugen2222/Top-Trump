package test;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import model.TestGameModel;

public class GameModelTest {
	private TestGameModel model = new TestGameModel();
	private int playerNum;
	
	public GameModelTest() {
		Random r = new Random();
		playerNum = r.nextInt(4)+2;
		model.initialiseGame(playerNum);
	}
	
	@Test
	public void testDefaultPlayer() {
		Assert.assertEquals("numOfPlaryer error",playerNum, model.getNumOfPlayer());
		Assert.assertEquals("playerName error","You", model.getPlayerList().get(0).getPlayerName());
		for(int i=1;i<playerNum;i++) {
			Assert.assertEquals("playerName error","AI Player"+i, model.getPlayerList().get(i).getPlayerName());
		}
		
	}
	
	@Test
	public void testReadCard() {
		model.readCard();
		Assert.assertEquals("cardAttribute error",40,model.getCardPool().size());
	}
	
	@Test
	public void testDefaultCard() {
		//cardAttribute test
		Assert.assertEquals("cardAttribute error",6,model.getCardAttribute().length);
		Assert.assertEquals("cardAttribute error","Description",model.getCardAttribute()[0]);
		Assert.assertEquals("cardAttribute error","Size",model.getCardAttribute()[1]);
		Assert.assertEquals("cardAttribute error","Speed",model.getCardAttribute()[2]);
		Assert.assertEquals("cardAttribute error","Range",model.getCardAttribute()[3]);
		Assert.assertEquals("cardAttribute error","Firepower",model.getCardAttribute()[4]);
		Assert.assertEquals("cardAttribute error","Cargo",model.getCardAttribute()[5]);
		//cardList test
		int cardsize = 0;
		for(int i=0;i<playerNum;i++) {
			cardsize += model.getPlayerList().get(i).getNumOfCards();
		}
		Assert.assertEquals("cardDeafault error",40,cardsize);
		Assert.assertEquals("cardDeafault error",0,model.getCardPool().size());
	}
	
	@Test
	public void testGameover() {
		//player game
		int gameResult = -1;
		while(model.getGameIsOver()== false) {
			model.decideActivePlayers();
			model.draw();
			if (model.humanIsActivePlayer() == true) {
				model.humanSelect(5);	
			} else {
				model.AISelect(); 
			}
			model.showWinner(); 
			gameResult = model.checkGameIsOver();
		}
		
		// final result test
		//if one player won the game
		if(gameResult==0 || gameResult==1) {//1 player survived
			int numOfAlivePlayer=0;
			int zeroCardPlayer =0;
			int totalCards = 0;
			for(int i=0;i<playerNum;i++) {
				totalCards = model.getPlayerList().get(i).getNumOfCards()+model.commonPileSize(); //winner and commonpile cards
				if(model.getPlayerList().get(i).aliveJudge()) numOfAlivePlayer++;
				if(model.getPlayerList().get(i).getNumOfCards()==0) zeroCardPlayer++;
				 //used card number to determine winner
			}
			totalCards =  model.getPlayerList().get(model.getFinalWinnerIndex()).getNumOfCards()+model.commonPileSize();
			
			Assert.assertEquals("numOfAlive error",1,numOfAlivePlayer); // verify num of alive player 
			Assert.assertEquals("numOfPlayerNum error",zeroCardPlayer+numOfAlivePlayer,playerNum); // verify num of player 
			Assert.assertEquals("numOfTotalCards error",40,totalCards); // verify num of cards
		}
		
		//deal with drew to end part, if the active player don't have card, the game will end
		else if(gameResult==2) {//1 or more players survived 
			int numOfAlivePlayer=0;
			int zeroCardPlayer =0;
			int activePlayerLose =0;
			int totalCards = 0;
			for(int i=0;i<playerNum;i++) {
				if(model.getPlayerList().get(i).aliveJudge()) numOfAlivePlayer++;
				if(model.getPlayerList().get(i).getNumOfCards()==0) zeroCardPlayer++;
				if(model.getPlayerList().get(model.getRoundWinnerIndex()).getNumOfCards()==0) activePlayerLose++;
				totalCards += model.getPlayerList().get(i).getNumOfCards();
			}
			totalCards += model.commonPileSize();
			
			Assert.assertEquals("numOfActivePlayerLose error",1,activePlayerLose);
			Assert.assertEquals("numOfPlayerNum error",zeroCardPlayer+numOfAlivePlayer,playerNum);
			Assert.assertEquals("numOfTotalCards error",40,totalCards);
		}
		
	}
	
	@Test
	public void testTestLog() {
		model.createLog();
	}

}

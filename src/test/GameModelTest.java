package test;
import model.TestGameModel;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
/**
*
* This class is to test the game model methods to guarantee the operation of the methods
*/

class GameModelTest {
	TestGameModel model = new TestGameModel();
	int playerNum;
	
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
		while(model.getGameIsOver()== false) {
			model.decideActivePlayers();
			model.draw();
			if (model.humanIsActivePlayer() == true) {
				model.humanSelect(5);	
			} else {
				model.AISelect(); 
			}
			model.showWinner(); 
			model.checkGameIsOver();
		}
		
		// final result test
		int numOfAlivePlayer=0;
		int zeroCardPlayer =0;
		int allCardPlayer =0;
		
		
		for(int i=0;i<playerNum;i++) {
			if(model.getPlayerList().get(i).aliveJudge()) numOfAlivePlayer++;
			if(model.getPlayerList().get(i).getNumOfCards()==0) zeroCardPlayer++;
			if(model.getPlayerList().get(i).getNumOfCards()==40) allCardPlayer++;
		}
		
		Assert.assertEquals("numOfAlive error",1,numOfAlivePlayer);
		Assert.assertEquals("numOfDeath error",zeroCardPlayer+allCardPlayer,playerNum);
		
		if(model.getCommonPile().isEmpty()) {
			Assert.assertEquals("winner error",allCardPlayer,1);
			Assert.assertEquals("total cards error",40,model.getPlayerList().get(model.getFinalWinnerIndex()).getNumOfCards());
		}else {
			int cards = model.getCommonPile().size() + model.getPlayerList().get(model.getFinalWinnerIndex()).getNumOfCards();
			Assert.assertEquals("total cards error",40, cards);
		}
	}
	
	@Test
	public void testTestLog() {
		model.createLog();
	}
}

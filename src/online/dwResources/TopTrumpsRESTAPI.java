package online.dwResources;



import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;



import online.configuration.TopTrumpsJSONConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import model.OGameModel;



@Path("/toptrumps") // Resources specified here should be hosted at http://localhost:7777/toptrumps
@Produces(MediaType.APPLICATION_JSON) // This resource returns JSON content
@Consumes(MediaType.APPLICATION_JSON) // This resource can take JSON content as input
/**
 * This is a Dropwizard Resource that specifies what to provide when a user
 * requests a particular URL. In this case, the URLs are associated to the
 * different REST API methods that you will need to expose the game commands
 * to the Web page.
 * 
 * Below are provided some sample methods that illustrate how to create
 * REST API methods in Dropwizard. You will need to replace these with
 * methods that allow a TopTrumps game to be controled from a Web page.
 */
public class TopTrumpsRESTAPI {

	/** A Jackson Object writer. It allows us to turn Java objects
	 * into JSON strings easily. */
	ObjectWriter oWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();

	/**
	 * Contructor method for the REST API. This is called first. It provides
	 * a TopTrumpsJSONConfiguration from which you can get the location of
	 * the deck file and the number of AI players.
	 * @param conf
	 */
	OGameModel model = new OGameModel();
	TopTrumpsJSONConfiguration webConfiguration;
	
	public TopTrumpsRESTAPI(TopTrumpsJSONConfiguration conf) {
		webConfiguration = conf;
	}
	
	// ----------------------------------------------------
	// Add relevant API methods here
	// ----------------------------------------------------
	
//	@GET
//	@Path("/helloJSONList")
//	/**
//	 * Here is an example of a simple REST get request that returns a String.
//	 * We also illustrate here how we can convert Java objects to JSON strings.
//	 * @return - List of words as JSON
//	 * @throws IOException
//	 */
//	public String helloJSONList() throws IOException {
//		
//		List<String> listOfWords = new ArrayList<String>();
//		listOfWords.add("Hello");
//		listOfWords.add("World!");
//		
//		// We can turn arbatory Java objects directly into JSON strings using
//		// Jackson seralization, assuming that the Java objects are not too complex.
//		String listAsJSONString = oWriter.writeValueAsString(listOfWords);
//		
//		return listAsJSONString;
//	}
//	
//	@GET
//	@Path("/helloWord")
//	/**
//	 * Here is an example of how to read parameters provided in an HTML Get request.
//	 * @param Word - A word
//	 * @return - A String
//	 * @throws IOException
//	 */
//	public String helloWord(@QueryParam("Word") String Word) throws IOException {
//		return "Hello "+Word;
//	}
//	

	
//	@GET
//	@Path("/updateViewGameInfo")
//	public String updateViewGameInfo() {
//		return model.getGameInfoWeb();
//	}
	
	
	
	private int cmd = 0;
	
	//send game stages to view according to game process
	@GET
	@Path("/webMaster")
	public int webMaster() {
		System.out.println(cmd);
		return cmd;	
	}
	// the meaning of cmd 
	//2 = playerSelectEnableStage
	//3 = playerSelectListStage
	//4 = waitAIPlayerStage
	//5 = selectionResultStage
	//6 = showWinnerStage
	//7 = showGameResultStage

	//set game stage that human is a active player or not 
	private void setViewActivePlayer() {
		if(model.getHumanIsActivePlayer()) {
			cmd = 2;
		}else {
			cmd = 4;
		}
	}
	
	
	//receive a event that the user press select button or show ai's selection
	//and set the game stage
	@GET
	@Path("/userPressSelect")
	public void userPressSelect() {
		if(cmd == 3 && cmd == 5) { System.out.println("Error, illegal visit!");}
		else {
			if(model.getHumanIsActivePlayer()) {
				cmd = 3;
			}else {
				model.AISelect();
				cmd = 5;
			}
		}
	}
	
	
	//receive a event that the user select a category
	@GET
	@Path("/userSelect")
	public void userSelectCategory(@QueryParam("Word") int num) {
		if(cmd == 5) { System.out.println("Error, illegal visit!");}
		else {
			 model.humanSelect(num);
			 cmd = 5;
		}
	}
	
	//receive a event that the user press show winner button
	@GET
	@Path("/userPressShowWinner")
	public void userPressShowWinner() {
		if(cmd == 7) { System.out.println("Error, illegal visit!");}
		else {
			model.showWinner();
			model.checkGameIsOver();
			model.AIAutoPlay();
			if(model.getGameIsOver()==true){
				cmd = 7;
				
				//model.createLog();
			}
			else {
				cmd = 6;
			}
			System.out.println(cmd);
		}
	}
	
	
	
	//Initialised game
	@GET
	@Path("/userRequestGameInitialise")
	public void userRequestGameInitialise(@QueryParam("num") int num) {
		webConfiguration.setNumAIPlayers(num-1);
		model.initialiseGame(webConfiguration.getNumAIPlayers()+1);
		model.decideActivePlayers();
		model.draw();
		setViewActivePlayer();
	}

	//receive a event that the user press next turn
	//and set the game stage
	@GET
	@Path("/userPressNewTurn")
	public void userPressNewTurn() {
		if(cmd == 2 && cmd == 4) { System.out.println("Error, illegal visit!");}
		else {
			model.decideActivePlayers();
			model.draw();
			setViewActivePlayer();
		}
	}
	

	//request AI to select
	@GET
	@Path("/requestAISelect")
	public void viewRequestAISelect() {
		model.AISelect();
	}
		

	//request model to update data
	@GET
	@Path("/requestUpdateGameData")
	public void viewRequestUpdateGameData() {
		model.updateGameData();
	}
	
	
	//update the statistic of the game 
	@GET
	@Path("/updateGameStats")
	public String updateGameStats() {
		return arrayTrans(model.getGameStats());
	}
		
	
		
	//update View Game Information
	@GET
	@Path("/updateViewGameInformPackage")
	public String updateViewGameInformPackage() {
		String [] s = new String[2];
		s[0]= model.getGameStatusWeb();
		s[1]= model.getGameInfoWeb();
		return arrayTrans(s);
	}
	
	//update view to show the Winner's card
	@GET
	@Path("/updateViewShowWinnerCard")
	public String updateViewShowWinnerCard() {
		return arrayTrans(model.showWinnerCard());
	}
	
	//update view to show the information of Common Pile 
	@GET
	@Path("/updateViewCommonPileCard")
	public String updateViewCommonPileCard() {
		return arrayTrans(model.getFirstCardInCommonPile());
	}
	
	
	
	//updataViewPlayerResult
	@GET
	@Path("/updataViewPlayerResult")
	public String updataViewPlayerResult() {
		return arrayTrans(model.getGameResultWeb());
	}
	
	
	//update all players' cards drew on deck
	@GET
	@Path("/updateViewCardSOnDeck")
	public String updateViewCardOnDeck() {
		return arrayTrans(model.getCardStringOnDeckWeb());
		
	}
	
	//update human's card drew on deck
	@GET
	@Path("/updateViewHumanCardOnDeck")
	public String updateViewHumanCardOnDeck() {
		System.out.print(model.getCardStringOnDeckWeb()[0]);
		return model.getCardStringOnDeckWeb()[0];
	}
	
	
	
	//encode java array to a string
	public String arrayTrans(String[] in) {
		String s = "";
		s+=in[0]+"|?|";
		for(int i = 1; i<in.length-1;i++) {
			s+=in[i]+"|?|";
		}
		s+=in[in.length-1];
		return s;
	}
	
	
	
}

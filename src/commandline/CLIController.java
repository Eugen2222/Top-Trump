package commandline;
import model.CLIGameModel;

public class CLIController {
	private CLIGameModel model;
	private CLIView view = new CLIView();
	private Boolean gameRestart;


	public CLIController(boolean testlog) {
		startNewGame(testlog);

	}

	public void startNewGame(boolean testlog) {
		gameRestart = false;
		while (!gameRestart) {
			selectModeStage();
			gameRestart = false;
			view.printSpace();
			if (testlog == true) {
				model.createLog();
			}
		}
	}
	// 1 for past game history, and 2 for start a new game
	public void selectModeStage() {
		int mode = view.askPlayerSelectMode();
		if(mode==1) {
			model = new CLIGameModel();
			String[] stats = model.getGameStats();
			view.printStatistics(stats);
			selectModeStage();
		}
		else if(mode==2) {
			selectPlayerNumStage();
		}
		else {
			selectModeStage();
		}
	}
	
	//ask player to select the number of total player 
	public void selectPlayerNumStage() {
		model = new CLIGameModel();
		int numOfPlayer = view.askNumberOfPlayers();
		model.initialiseGame(numOfPlayer);
		playGameStage();
	}
	

	// game process
	public void playGameStage() {
		view.printGameStart();
		while (model.getGameIsOver() == false) {
			model.decideActivePlayers(); // decide active player
			model.draw(); // draw card
			view.print(model.getCLIStatus()); // show draw card instruction when human alive

			if (model.getHumanLose() == false) {
				view.print(model.getCardStringOnDeckCLI()[0]);  // show human's draw card when human alive
			}

			if (model.humanIsActivePlayer() == true) {
				view.printAskSelectCategory();
				int categorySelect = view.askPlayerSelectCategory(); // ask human select a category
				model.humanSelect(categorySelect);	// human select category
			} else {
				model.AISelect(); // ai select category
			}
			model.showWinner();  //show round's winner
			view.print(model.getCLIStatus());
			// show round winner

			model.checkGameIsOver();// check if game ends
			view.print(model.getCLIStatus());
		}
		view.printArray(model.getGameResultCLI()); //print the result of the each game
		model.updateGameData(); // update game result to database
	}

}

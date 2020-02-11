package commandline;


public class CLIController {
	private GameModel model;
	private View view = new View();
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
			model = new GameModel();
			String[] stats = model.getGameStats();
			view.printArray(stats);
			selectModeStage();
		}
		else if(mode==2) {
			selectPlayerNumStage();
		}
		else {
			selectModeStage();
		}
	}
	
	public void selectPlayerNumStage() {
		model = new GameModel();
		int numOfPlayer = view.askNumberOfPlayers();
		model.initialiseGame(numOfPlayer);
		playGameStage();
	}
	

	// game process
	public void playGameStage() {
		view.printGameStart();
		while (model.getGameIsOver() != 0) {
			model.decideActivePlayers(); // decide active player
			model.draw(); // draw card
			view.print(model.getCMCStatus()); // show draw card instruction when human alive
			view.print(model.getCMCInfo()); // show player status when human alive
			if (model.getCardStringOnDeckCML()[0] != null) {
				view.print(model.getCardStringOnDeckCML()[0]);
			}

			if (model.humanIsActivePlayer() == 0) {
				view.printAskSelectCategory();
				int categorySelect = view.askPlayerSelectCategory();
				model.humanSelect(categorySelect);	// human select category
			} else {
				model.AISelect(); // ai select category
			}

			model.showWinner(); 
			view.print(model.getCMCStatus());// show round winner
			model.gameIsOver(); // game end
			view.print(model.getCMCStatus()); // show auto play process
		}
		view.printArray(model.getGameResult());
		model.updateGameData(); // update game result to database
	}

}

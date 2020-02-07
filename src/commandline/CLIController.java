package commandline;

import java.util.Scanner;

public class CLIController {
	private int playerNum;
	private GameModel model;
	private int humanSelect;
	private View view = new View();
	private int selectNum;
	private Boolean gameRestart;
	Scanner s = new Scanner(System.in);

	public CLIController(boolean testlog) {
		startNewGame(testlog);

	}

	public void startNewGame(boolean testlog) {
		gameRestart = false;
		while (!gameRestart) {
			selectIndex();
			model.getResults();
			gameRestart = false;
			view.printSpace();
			if (testlog == true) {
				model.createLog();
			}
		}
	}

	public void selectIndex() { // select 1 or 2
		selectNum = 0; // clear selectNum
		while (selectNum != 1 && selectNum != 2) {
			selectNum();
		}
	}

	// human player input selected category
	public void selectCategory() {
		humanSelect = 0;
		while (!(humanSelect >= 1 && humanSelect <= 5)) { // category 1 to 5
			inputCategory();
		}
	}

	// input seletion of start game index
	// 1 for past game history, and 2 for start a new game
	public void selectNum() {
		view.start();
		selectNum = s.nextInt(); // input via Scanner
		if (selectNum == 1) {
			// Read history
			model = new GameModel(2);
			String[] output = model.getGameStats();
			view.printStatistic();
			for (int i = 0; i < output.length; i++) {
				System.out.println("  " + output[i]);
			}
			selectIndex();
		} else if (selectNum == 2) {
			// Start playing
//			gameRestart = true;
			view.playerNumber(); // instruction of decide player number
			setPlayerNumber(); // input player number
			view.printGameStart(); // instruction of game start
			model = new GameModel(playerNum);
			playGame(); // start playing game
		} else {
			view.printWrongInput(); // catch wrong number
		}
	}

	// game process
	public void playGame() {
		while (model.getGameIsOver() != 0) {
			model.decideActivePlayers(); // decide active player
			model.draw(); // draw card
			System.out.println(model.getCMCStatus()); // show draw card instruction
			System.out.println(model.getCMCInfo()); // show player status
			if (model.getCardStringOnDeckCML()[0] != null) {
				System.out.println(model.getCardStringOnDeckCML()[0]);
			}

			if (model.humanIsActivePlayer() == 0) {
				selectCategory(); // human select category
			} else {
				model.AISelect(); // model select category
			}

			model.showWinner(); // show round winner
			model.gameIsOver(); // game end

		}
//		model.createLog(); // Test Log
//		model.updateGameData(); // update game result to database
	}

	public void setPlayerNumber() { // set player number
		playerNum = s.nextInt();
		s.nextLine();
	}

	public void inputCategory() {
		humanSelect = s.nextInt();
		s.nextLine();
		if (humanSelect >= 1 && humanSelect <= 5) {
			model.humanSelect(humanSelect);
		} else {
			view.printNoOption();
		}
	}

	// Getters and Setters
	public void setPlayerNum(int playerNum) {
		this.playerNum = playerNum;
	}

	public void setHumanSelect(int humanSelect) {
		this.humanSelect = humanSelect;
	}

	public GameModel getModel() {
		return model;
	}

}

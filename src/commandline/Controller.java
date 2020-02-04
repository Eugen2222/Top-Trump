package commandline;

import java.util.Scanner;

public class Controller {
	private int playerNum;
	private GameModel model;
	private int humanSelect;
	private View view;
	private int selectNum;
	private Boolean gameRestart;

	public Controller() {
		startNewGame();
	}

	public void startNewGame(){
		gameRestart = false;
		while(!gameRestart){
			view = new View();
			selectIndex();
			model.getResults();
			gameRestart = false;
			System.out.println("\n");
		}
	}

	public void selectIndex() { // select 1 or 2
		selectNum = 0; // clear selectNum
		while (selectNum != 1 && selectNum != 2) {
			selectNum();
		}
	}

	public void selectCategory() {
		humanSelect = 0;
		while (!(humanSelect >= 1 && humanSelect <= 5)) {
			inputCategory();
		}
	}

	public void selectNum() {
		Scanner s = new Scanner(System.in);
		view.start();
		selectNum = s.nextInt();
		if (selectNum == 1) {
			// Read history
			model = new GameModel(2);
			String[] output = model.getGameStats();
			System.out.println("Game Statistics:");
			for(int i=0;i<output.length;i++) {
				System.out.println("  "+output[i]);
			}
			selectIndex();
		} else if (selectNum == 2) {
			// Start playing
			gameRestart = true;
			view.playerNumber();
			setPlayerNumber();
			System.out.println("\n\n\nGame Start");
			model = new GameModel(playerNum);
			playGame();
		} else {
			System.out.println("wrong input\n");
		}
	}

	public void playGame() {
		while (model.getGameIsOver() != 0) {
			model.decideActivePlayers();
			model.draw();
			System.out.println(model.getGameStatus());
			if (model.getCardStringOnDeckCML()[0] != null) {
				System.out.println(model.getCardStringOnDeckCML()[0]);
			}

			if (model.humanIsActivePlayer() == 0) {
				selectCategory();
			} else {
				model.AISelect();
			}

			model.showWinner();
			model.gameIsOver();
			
			System.out.println("\n\n");
		}
		model.createLog();
		model.updateGameData();
	}

	public void setPlayerNumber() { // set player number
		Scanner s = new Scanner(System.in);
		playerNum = s.nextInt();
		s.nextLine();
	}

	public void inputCategory() {
		Scanner s = new Scanner(System.in);
		humanSelect = s.nextInt();
		s.nextLine();
		if (humanSelect >= 1 && humanSelect <= 5) {
			model.humanSelect(humanSelect);
		} else {
			System.out.println("No option, select again.");
		}
	}

	// Getters and Setters
	public void setPlayerNum(int playerNum) {
		this.playerNum = playerNum;
	}

	public void setHumanSelect(int humanSelect) {
		this.humanSelect = humanSelect;
	}

}
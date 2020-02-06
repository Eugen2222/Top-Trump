package commandline;

import java.util.Scanner;

public class View {

	public View() {

	}

	// instruction for starting game
	public void start() {
		System.out.println("Do you want to see past results or play a game?");
		System.out.println("1: Print Game Statistics\n2: Play game");
		System.out.print("Enter the number for your selection: ");
	}

	// input total player number
	public void playerNumber() {
		System.out.println("\nHow many players are joining the game?");
		System.out.print("(Please enter number 2 to 5): ");

	}

	public void printStatistic() {
		System.out.println("Game Statistics:");
	}

	public void printWrongInput() {
		System.out.println("wrong input\n");
	}

	public void printGameStart() {
		System.out.println("\n\n\nGame Start");
	}

	public void printNoOption() {
		System.out.println("No option, select again.");

	}

	public void printSpace() {
		System.out.println();
	}


}
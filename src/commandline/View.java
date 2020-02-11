package commandline;

import java.util.Scanner;

public class View {
	
	private int selectNum = 0;
	private Scanner s = new Scanner(System.in);
	
	public int askPlayerSelectMode() {
		printStart();
		selectNum = s.nextInt(); // input via Scanner
		if (selectNum == 1 || selectNum == 2) {
			return selectNum;
		}
		else {
			printWrongInput(); // catch wrong number
			return askPlayerSelectMode();
		}
	}
	
	
	// instruction for starting game
	public void printStart() {
		System.out.println("Do you want to see past results or play a game?");
		System.out.println("1: Print Game Statistics\n2: Play game");
		System.out.print("Enter the number for your selection: ");
		
	}

	// ask player number
	public int askNumberOfPlayers() {
		System.out.println("\nHow many players are joining the game?");
		System.out.print("(Please enter number 2 to 5): ");
		int numOfPlayers = s.nextInt();
		s.nextLine();
		if(numOfPlayers>1 && numOfPlayers<6) {
			return numOfPlayers;
		}
		return askNumberOfPlayers();
	}
	
	
	public void printAskSelectCategory() {
		String category = "\t1. Size\n\t2. Speed\n\t3. Range\n\t4. Firepower\n\t5. Cargo";
		String s = "It is your turn to select a category, the categories are:\n" + category;
		System.out.print(s);
	}
	
	public int askPlayerSelectCategory() {
		System.out.print("\nEnter the number for your attribute: ");
		int categorySelect = s.nextInt();
		s.nextLine();
		if (categorySelect >= 1 && categorySelect <= 5) {
			return categorySelect;
		} else {
			printNoOption();
			return askPlayerSelectCategory();
		}
	}
	
	
	public void printArray(String [] input) {
		System.out.println("Game Statistics:");
		for (int i = 0; i < input.length; i++) {
			System.out.println("  " + input[i]);
		}
	}
	
	public void print(String input) {
		System.out.print(input);
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
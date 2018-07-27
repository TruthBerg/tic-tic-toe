package main;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The user (human) Player object class
 * @author emmettgreenberg
 *
 */
public class HumanPlayer extends Player {
	
	public HumanPlayer() {
		super("X"); // Human players use "X"
	}
	
	/**
	 * @override
	 */
	public boolean isHuman() {
		return true;
	}
	
	public int getMove(GameBoard board) {
		Scanner input = new Scanner(System.in);
		int move = -1;
		
		do {
			try {
				System.out.println("Please enter your move (0-8)\n(You may not move to a space that is already taken):");
				move = input.nextInt();
			}
			catch(InputMismatchException e) {
				System.out.println("Invalid input. Please enter an integer");
				input.nextLine(); // clears the buffer
			}
			
			if (!board.isMoveLegal(move)) {
				new SoundThread("/Users/emmettgreenberg/game-sound-wrong.wav").start(); // play "incorrect" sound
			}
			
		} while (!board.isMoveLegal(move)); // Repeat the procedure if the user did not enter a valid move
		return move;	
	}
		
}

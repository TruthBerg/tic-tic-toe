package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * @author emmettgreenberg
 * The computer (AI) Player object class
 */
public class AIPlayer extends Player{

	public AIPlayer() {
		super("O"); // AI Players use "O"
	}
	
	@override
	public int getMove(GameBoard board, Player opponent) {
		/*int move = board.chooseRandomMove();
		return move;*/
		
		 /**
		  * First, check if we can win in the next move
		  */
		  for (int i = 0; i < board.getBoard().length; i++) {
			GameBoard boardCopy = new GameBoard(board.getBoard().clone()); // create copy of board
			//System.arraycopy(board, 0, boardCopy, 0, board.getBoard().length);
			System.out.println("COPY\n" + boardCopy.getBoard().toString());
			System.out.println("ACTUAL\n" + board.getBoard().toString());
			if (boardCopy.isSpaceFree(i)){
				boardCopy.placeMove(this, i);
				if (boardCopy.isWinner(this)) {
					return i;
				}
				//boardCopy.initializeBoard(); // clears the board
			}
			
		}		
		/**
		 * Next, check if our opponent could win on next move, and block them
		 */
		  for (int i = 0; i < board.getBoard().length; i++) {
				GameBoard boardCopy = new GameBoard(board.getBoard().clone()); // create copy of board
				System.out.println("COPY\n" + boardCopy.getBoard().toString());
				System.out.println("ACTUAL\n" + board.getBoard().toString());
				if (boardCopy.isSpaceFree(i)){
					boardCopy.placeMove(opponent, i);
					if (boardCopy.isWinner(opponent)) {
						return i;
					}
					//boardCopy.initializeBoard(); // clears the board
				}
		  }
		
		/**
		 * Try to take center, if free
		 */
		if (board.isSpaceFree(4))
			return 4;
		
		/**
		 * Try to take a corner space, if free
		 */
		int[] corners = new int[] {0,2,6,8};
		Random random = new Random();
		ArrayList<Integer> possMoves = new ArrayList<Integer>();
		for (int i: corners) {
			if (board.isSpaceFree(i))
				possMoves.add(i);
		}
		
		if (!possMoves.isEmpty()) {
			// get random index
			int index = random.nextInt(possMoves.size());
			// return the item with that index
			return possMoves.get(index);
		}
		
		/** Otherwise, if there are no winning moves, choose a random move */
		return board.chooseRandomMove();
		//return move;
	}	
		
	
}

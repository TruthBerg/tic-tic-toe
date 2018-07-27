package test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import main.AIPlayer;
import main.GameBoard;
import main.HumanPlayer;
import main.Player;

class GameBoardTest {

	@Test
	void testEmptyBoardIsFull() {
		GameBoard board = new GameBoard();
		assertFalse(board.isFull());
	}
	
	@Test
	void testFullBoardIsFull() {
		String[] theBoard = {"X", "O", "X"};
		GameBoard board = new GameBoard(theBoard);
		assertTrue(board.isFull());
	}
	
	@Test
	void testPartialEmptyBoardIsFull() {
		String[] theBoard = {"X", "O", " "};
		GameBoard board = new GameBoard(theBoard);
		assertFalse(board.isFull());
	}
	
	@Test
	void drawBoardTest() {
		String[] theBoard = {"X", "O", " ", "X", "O", "X", "X", "O", " "};
		GameBoard board = new GameBoard(theBoard);
		try {	
			board.drawBoard();
		} catch	(ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}
	
	/**
	@Test
	void getHumanPlayerMoveTest() {
		HumanPlayer p = new HumanPlayer();
		String[] theBoard = {"X", "O", " ", "X", "O", "X", "X", "O", " "};
		GameBoard board = new GameBoard(theBoard);
		assertEquals(8, p.getMove(board));
		
	}
	*/
	
	@Test
	void getAIPlayerMoveTest() {
		AIPlayer p = new AIPlayer();
		String[] theBoard = {"X", "", "", "X", "O", "X", "X", "O", ""};
		GameBoard board = new GameBoard(theBoard);
		System.out.print("****" + p.getMove(board));
		
	}

}

package main;
/*

*/

public class GameBoard{
	private String[] board;
	
	
	public GameBoard(String[] board) {
		this.board = board;
	}
	
	// create board of length 9
	public GameBoard() {
		this.board = new String[9];
		this.initializeBoard();
	}
	
	// Resets the board
	public void initializeBoard() {
		for(int i = 0; i < this.board.length; i++) {
			this.board[i] = "" + i;
		}
	}
	
	public void drawBoard() {
		System.out.println("-------------");
		for (int i = 0; i < this.board.length; i+=3) {
			System.out.print("| ");
			for (int j = 0; j < 3; j++ ) {
				System.out.print(this.board[i+j] + " | ");
			}
			System.out.println();
			System.out.println("-------------");
		}			
		
	}
	public boolean isSpaceFree(int i) {
		return (this.board[i].equals("" + i));
	}
	
	public boolean isFull() {
		for (int i = 0; i < this.board.length; i++) {
				if (isSpaceFree(i))
						return false;
		}
		return true;
	}
	
	// Returns true if the player's move is legal (move is in bounds and space is free)
	public boolean isMoveLegal(int move) {
		return(move >= 0 && move < this.board.length && isSpaceFree(move));
	}
	
	// Updates the board with the player move
	public void placeMove(Player player, int move) {
		System.out.println("Move: " + move);
		this.board[move] = player.getMarker();
	}
	
	public int chooseRandomMove() {
		for (int i = 0; i < board.length; i++) {
			if (isSpaceFree(i))
				return i;
		}
		return -1;
	}
	
	public boolean isWinner(Player p) {
		return (
				// check rows
				(board[0] == p.getMarker() && board[1] == p.getMarker() && board[2] == p.getMarker()) ||
				(board[3] == p.getMarker() && board[4] == p.getMarker() && board[5] == p.getMarker()) ||
				(board[6] == p.getMarker() && board[7] == p.getMarker() && board[8] == p.getMarker()) ||
				// check columns
				(board[0] == p.getMarker() && board[3] == p.getMarker() && board[6] == p.getMarker()) ||
				(board[1] == p.getMarker() && board[4] == p.getMarker() && board[7] == p.getMarker()) ||
				(board[2] == p.getMarker() && board[5] == p.getMarker() && board[8] == p.getMarker()) ||
				// check diagonals
				(board[0] == p.getMarker() && board[4] == p.getMarker() && board[8] == p.getMarker()) ||
				(board[2] == p.getMarker() && board[4] == p.getMarker() && board[6] == p.getMarker())
			);	
							
	}
	
	
	
	// Accesses the board array for other classes/methods
	public String[] getBoard() {
		return board;
	}
}
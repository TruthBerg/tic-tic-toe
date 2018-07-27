package main;
/**
 * @author emmettgreenberg
 * An abstract player class
 * Each player is an instance of one of its subclasses
 */
public abstract class Player{
	//String name;
	private String marker; // "X" or "O"
	private int wins; 
	private int losses;
	private int draws;
	
	public Player(String marker) {
		this.marker = marker;
		this.wins = 0;
		this.losses = 0;
		this.draws = 0;
	}
	
	boolean isHuman(){ // concrete method to determine whether the player is human or AI
		return false;
	}
	
	public String getMarker() { // accessor method for the game board
		return this.marker;
	}
	
	public int getMove(GameBoard board) { // gets the player's move on their turn
		return -1;
	}
	
	public int getMove(GameBoard board, Player otherPlayer) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void incrementWins() {
		this.wins ++;
	}
	
	public void incrementLosses() {
		this.losses ++;
	}
	
	public void incrementDraws() {
		this.draws ++;
	}
	
	public int getWins() {
		return this.wins;	
	}
	
	public int getLosses() {
		return this.losses;
	}
	
	public int getDraws() {
		return this.draws;
	}

	
}









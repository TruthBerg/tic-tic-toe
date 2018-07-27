package main;
/**
 * @author emmettgreenberg
 * The game engine class for the Tic Tac Toe game.
 */

import java.util.Scanner;

import main.AIPlayer;

import java.io.*;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Random;

public class TicTacToe {
	private GameBoard board;
	private Player human;
	private Player AI;
	
	
	public TicTacToe() {
		board = new GameBoard();
		human = new HumanPlayer();
		AI = new AIPlayer();
	}	
		
	
	// Runs the game
	public void play() {
		
		// Instructions
		System.out.println("Welcome to Tic Tac Toe V2!");
		System.out.println("You will play 'X', and the computer will play 'O'."); 
		
		// Start the playing procedure
		while(true) {
			
			// NEW: create database object to access DerbyDB
			Database dbObj = new Database();
			//dbObj.connectDB();
			
			// CREATE id for current game
			int gameNum = 1;
		
			// Setup game board
			board.initializeBoard();
			System.out.println("--------------------------");
			System.out.println("Initalizing...");
			board.drawBoard();
			
			// Set starting player
			Player activePlayer = human;
			Player otherPlayer = AI;
			
			// Start playing
			boolean gameOver = false;
			while(!gameOver) {
				
				otherPlayer = (activePlayer.isHuman()) ?  AI : human; // sets the other player
				
				// get active player's move
				int nextMove;
				System.out.println("Player " + activePlayer.getMarker() + ", it is your turn to move.");
				if (activePlayer.isHuman()){
					nextMove = activePlayer.getMove(board);
				} else {
					nextMove = activePlayer.getMove(board, otherPlayer);
				}
				
				
				
				
				// update and display the board
				board.placeMove(activePlayer, nextMove);
			
				board.drawBoard();
				
				// check for win
				if (board.isWinner(activePlayer)){
					System.out.println("Player " + activePlayer.getMarker() + ", you win!");
					gameOver = true;
					if (activePlayer.isHuman()) {
						new SoundThread("/Users/emmettgreenberg/win-sound.wav").start();
					}
					else {
						new SoundThread("/Users/emmettgreenberg/game-over-sound.wav").start();
					}
					activePlayer.incrementWins();
					otherPlayer.incrementLosses();

				} 
				else if (board.isFull()) { // game is a draw
					System.out.println("Game over. It is a draw.");
					gameOver = true;
					activePlayer.incrementDraws();
					otherPlayer.incrementDraws();
				}
				else { // game is not over, so it is the other player's turn
					
					new SoundThread("sounds/game-sound-correct.wav").start(); // create thread to play sound on a correct move
					activePlayer = otherPlayer;
					
				}
					
			}

			// NEW: update each player's wins/losses/draws in the "player" table
			//System.out.println("Updating player");
			//dbObj.updatePlayer(activePlayer.getMarker(), activePlayer.getWins(), activePlayer.getLosses(), activePlayer.getDraws());
			//dbObj.updatePlayer(otherPlayer.getMarker(), otherPlayer.getWins(), otherPlayer.getLosses(), otherPlayer.getDraws());
			
			// NEW: insert new row into "game" table (game-id, winner)
			//dbObj.insertWinner(gameNum, activePlayer.getMarker()); // need player ID from player table
			
			if(!playAgain()) { // terminate the program if user does not want to play again
				break;
		
			}
		}
	}
	
	
	public boolean playAgain() {
		Scanner input = new Scanner(System.in);
		System.out.println("Play again? (yes/no)");
		return(input.next().toLowerCase().startsWith("y"));			
	}
}

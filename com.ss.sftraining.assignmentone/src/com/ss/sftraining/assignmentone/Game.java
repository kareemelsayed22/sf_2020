/**
 * 
 */
package com.ss.sftraining.assignmentone;

/**
 * @author kareemelsayed
 *
 */
public class Game {
   
	private boolean gameOver;
	private int secretNumber;
	private int numberOfGuesses;

	public Game() { 
		gameOver = false;
		secretNumber = (int) (Math.random() * 100 + 1);
		numberOfGuesses = 0;
	}
	public Game(int range) { 
		gameOver = false;
		secretNumber = (int) (Math.random() * range + 1);
		numberOfGuesses = 0;
	}
	public boolean isGameOver() { 
		return gameOver;
	}
	public void guess(int n) { 
		 if (gameOver) {
			    System.out.println("The game is over. " 
			        + "You can not guess again.");
			  } else if (n == secretNumber) {
			    System.out.println("You guessed right!");
			    gameOver = true;
			  } else {
			    if (n <= secretNumber - 10 ) {
			        System.out.println("Your guess is too low.");
			    } else {
			        System.out.println("Your guess is too high.");
			    }
			    numberOfGuesses++;
			    if (numberOfGuesses == 5) {
			      System.out.println("Sorry, You have used up all of your guesses. The number is " + secretNumber);
			      gameOver = true;
			    }
			  }
	}
	
	
}

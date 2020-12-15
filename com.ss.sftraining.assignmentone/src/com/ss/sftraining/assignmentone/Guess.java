package com.ss.sftraining.assignmentone;
import java.util.Scanner;

public class Guess {
	public static void main(String[] args) {
	    
		Scanner input = new Scanner(System.in);

	    Game theGame = new Game();
	    while(theGame.isGameOver() == false){
	        System.out.println("Guess a number between 1 and 100:");
	        int n = input.nextInt();
	        theGame.guess(n);
	    }
	  }
}

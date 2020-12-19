/**
 * 
 */
package com.ss.sftraining.assignment.five;

import java.util.ArrayList;

/**
 * @author kareemelsayed
 * This class sorts a list of strings with letters a
 * 
 */
public class Sorting {

	/**
	 * @param args
	 */
	private static ArrayList<String> wordList = new ArrayList<>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        wordList.add("apex");
        wordList.add("async");
        wordList.add("apex");
        wordList.add("ant");
        
        wordFinder();

        
        
	}
	// returns ant
	public static void wordFinder() {
		boolean found = true;
		
		wordList.stream().filter(e->{
			if(e.charAt(0) == 'a' && e.length() < 4){
                return found;
		}
			else return !found;
	}).forEach(e -> System.out.println(e));
   }
}

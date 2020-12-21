/**
 * 
 */
package com.ss.sftraining.assignment.week.one;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author kareemelsayed
 * This class will contain an List of strings and a method to remove x from the words
 */
public class AssignmentFour {
	 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		   ArrayList<String> words = new ArrayList<>();
	        
		   words.add("xxax");
	       words.add("xxbx");
	       words.add("xxcx");
	       words.add("xxdx");
	       
	       //prints the original list
	       words.forEach(System.out::println);
	       
	       
	       //removes x 
	       words.forEach(e -> {
	            int i = words.indexOf(e);
	            e = (e.replaceAll("x", ""));
	            words.set(i, e);
	        });
	       
            //print new list
	        System.out.println("Printing new list without x");
	        words.forEach(System.out::println);
	       
	       
		
	}
	
}
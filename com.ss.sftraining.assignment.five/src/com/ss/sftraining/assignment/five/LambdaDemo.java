/**
 * 
 */
package com.ss.sftraining.assignment.five;

import java.util.ArrayList;
import java.util.Comparator;


/**
 * @author kareemelsayed
 * This class shows different ways of sorting data with Lambda expressions
 *
 */
public class LambdaDemo {

	/**
	 * @param args
	 * Create a list to be sorted 
	 * Sort with functions
	 * Start with a list (movies)
	 */
    private static ArrayList <String> movies = new ArrayList<>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        
        movies.add("Avengers");
        movies.add("John Wick");
        movies.add("Tennant");
        movies.add("Hunger Games");
        movies.add("Lucy");
        movies.add("Endgame");
        
        System.out.println("->Sorting the list of movies by length (Short to long)");
        shortToLong();
        
        System.out.println("->Sorting the list of movies by length (long to short)");
        longToShort();
        
        
        System.out.println("->Sorting the list of movies in alphabetical order");
        sortMoviesByChar();
        
        
        System.out.println("->Sorting the list of movies starting with e first");
        sortMoviesWithE();
        
        
        
    }
	
	
	//Sorts by length from shortest to longest
	public static void shortToLong() {
        movies.stream().sorted(Comparator.comparingInt(String::length)).forEach(e -> System.out.println(e));

	}
	
	//Sorts by length from longest to shortest
    public static void longToShort() {
        movies.stream().sorted(Comparator.comparingInt(String::length).reversed()).forEach(e -> System.out.println(e));

	}
    
    //Sorts by characters
  	public static void sortMoviesByChar() {
        movies.stream().sorted((s1, s2) -> s1.compareToIgnoreCase(s2)).forEach(e -> System.out.println(e));

  	}
  	
    //Sorts movies with E first 
  	public static void sortMoviesWithE() {
  		 movies.stream().sorted((s1, s2) -> sortWithHelp(s1,s2)
  		        ).forEach(s -> System.out.println(s));
  		
  	}
  	
    //Helper function to sort with e's considering uppercase annd lowercase letters 
  	public static int sortWithHelp(String s1, String s2) {
         char capE = 'e';
  		 char lowE = 'E';
  		 
  		 if(s1.charAt(0) == capE || s1.charAt(0) == lowE) {
  			 return -26;
  		 }
  		 
  		 else if (s1.charAt(0) == capE || s1.charAt(0) == lowE && s2.charAt(0) == capE || s2.charAt(0) == lowE) {
  			 return (s1.compareToIgnoreCase(s2) - 26);
  		 }
  		 
  		 return s1.compareToIgnoreCase(s2);
  	}		
  }
